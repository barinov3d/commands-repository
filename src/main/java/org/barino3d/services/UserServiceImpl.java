package org.barino3d.services;

import org.barino3d.exceptions.UserNotFoundException;
import org.barino3d.exceptions.DuplicateEmailException;
import org.barino3d.models.User;
import org.barino3d.models.User;
import org.barino3d.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

}
