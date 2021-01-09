package org.barino3d.security;

import org.barino3d.models.User;
import org.barino3d.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Guard {
    @Autowired
    private UserRepository repo;

    public boolean checkUserId(Authentication authentication, String id) {
        String email = authentication.getName();
        System.out.println(email + " at " + id);
        User result = repo.findByEmail(email);
        return result != null && result.getId().equals(id);
    }
}