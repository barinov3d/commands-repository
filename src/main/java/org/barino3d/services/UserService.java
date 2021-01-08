package org.barino3d.services;

import org.barino3d.exceptions.UserNotFoundException;
import org.barino3d.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();

    User findById(String id) throws UserNotFoundException;

    void deleteById(String id) throws UserNotFoundException;

    void delete(User author) throws UserNotFoundException;

    User save(User user);

    User findByEmail(String email);

    User createUser(User userDetails);

}
