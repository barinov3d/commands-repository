package org.barino3d.services;

import lombok.AllArgsConstructor;
import org.barino3d.exceptions.DuplicateEmailException;
import org.barino3d.exceptions.UserNotFoundException;
import org.barino3d.models.ConfirmationToken;
import org.barino3d.models.UserDto;
import org.barino3d.models.UserEntity;
import org.barino3d.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    Environment environment;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserEntity findById(String id) throws UserNotFoundException {
        final UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %s not found", id)));
        return userEntity;
    }

    @Override
    public void deleteById(String id) throws UserNotFoundException {
        final UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %s not found", id)));
        userRepository.deleteById(id);
    }

    @Override
    public void delete(UserDto user) throws UserNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userRepository.delete(userEntity);
    }

    @Override
    public UserDto save(UserDto user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final UserEntity userEntity = userRepository.save(modelMapper.map(user, UserEntity.class));
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto findByEmail(String email) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(userRepository.findByEmail(email), UserDto.class);
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        if (userDto.getId() == null && (userRepository.findByEmail(userDto.getEmail()) != null)) {
            throw new DuplicateEmailException(
                    "User with name '" + userDto.getEmail() + "' is already define in the scope");
        }
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public boolean verify(ConfirmationToken confirmationToken) {
        UserEntity user = userRepository.findByEmailIgnoreCase(confirmationToken.getUserEntity().getEmail());
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setConfirmationToken(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userModel = userRepository.findByEmail(username);
        if (userModel == null) {
            throw new UsernameNotFoundException(username);
        } else {
            if (!userModel.isEnabled()) {
                throw new UsernameNotFoundException(username);
            }
        }
        return new User(userModel.getEmail(), userModel.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
