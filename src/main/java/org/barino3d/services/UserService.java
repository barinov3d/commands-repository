package org.barino3d.services;

import org.barino3d.exceptions.UserNotFoundException;
import org.barino3d.models.UserDto;
import org.barino3d.models.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserEntity findById(String id) throws UserNotFoundException;

    void deleteById(String id) throws UserNotFoundException;

    void delete(UserDto author) throws UserNotFoundException;

    UserDto save(UserDto user);

    UserDto findByEmail(String email);

    UserDto createUser(UserDto userDetails);

}
