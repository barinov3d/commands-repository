package org.barino3d.controllers;

import lombok.AllArgsConstructor;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.models.UserDto;
import org.barino3d.services.ApplicationService;
import org.barino3d.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class IndexController {

    private final ApplicationService applicationService;
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByEmail(authentication.getName());
        final String userId = user.getId();
        List<Application> applications = applicationService.findAllByUser(userService.findById(userId));
        Application application;
        if (applications.size() > 0) {
            application = applications.get(0);
        } else {
            application = applicationService.save(new Application("Test application"));
        }
        List<Command> commands = application.getCommands();
        model.addAttribute("commands", commands);
        return "redirect:/user/" + userId + "/application/" + application.getId();
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(Model model) {
        return "redirect:/";
    }

}
