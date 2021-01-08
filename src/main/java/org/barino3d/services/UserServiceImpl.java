package org.barino3d.services;

import lombok.AllArgsConstructor;
import org.barino3d.exceptions.DuplicateEmailException;
import org.barino3d.exceptions.UserNotFoundException;
import org.barino3d.models.User;
import org.barino3d.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    Environment environment;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %s not found", id)));
    }

    @Override
    public void deleteById(String id) throws UserNotFoundException {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id: %s not found", id)));
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User user) throws UserNotFoundException {
        userRepository.delete(user);
    }


    @Override
    public User save(User user) throws DuplicateEmailException {
        if (user.getId() == null && (userRepository.findByEmail(user.getEmail()) != null)) {
            throw new DuplicateEmailException(
                    "User with name '" + user.getEmail() + "' is already define in the scope");
        }
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User createUser(User user) {
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userModel = userRepository.findByEmail(username);
        if (userModel == null) throw new UsernameNotFoundException(username);
        return new org.springframework.security.core.userdetails.User(userModel.getEmail(), userModel.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
