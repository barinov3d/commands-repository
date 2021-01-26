package org.barino3d.controllers;

import org.barino3d.models.ConfirmationToken;
import org.barino3d.models.UserDto;
import org.barino3d.models.UserEntity;
import org.barino3d.repositories.ConfirmationTokenRepository;
import org.barino3d.repositories.UserRepository;
import org.barino3d.services.EmailService;
import org.barino3d.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, UserDto userDto) {
        modelAndView.addObject("userDto", userDto);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, UserDto userDto) {
        UserEntity existingUser = userRepository.findByEmailIgnoreCase(userDto.getEmail());
        if (existingUser != null) {
            modelAndView.addObject("message", "This email already exists!");
            modelAndView.setViewName("error");
        } else {
            //TODO move to UserAccountService
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            final UserDto user = userService.createUser(userDto);
            UserEntity userEntity = modelMapper.map(user, UserEntity.class);
            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);
            userEntity.setConfirmationToken(confirmationToken);
            confirmationTokenRepository.save(confirmationToken);
            userRepository.save(userEntity);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEntity.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("commandsrepository@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "https://commands-repository.herokuapp.com/confirm-account?token=" + confirmationToken.getConfirmationToken());
            try {
                emailService.sendEmail(mailMessage);
            } catch (Exception e) {
                confirmationTokenRepository.delete(confirmationToken);
                userService.delete(userDto);
            }
            modelAndView.addObject("email", userEntity.getEmail());
            modelAndView.setViewName("successfulRegisteration");
        }

        return modelAndView;
    }


    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            userService.verify(token);
            modelAndView.setViewName("accountVerified");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
