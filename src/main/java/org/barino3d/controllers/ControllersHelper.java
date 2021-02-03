package org.barino3d.controllers;

import org.barino3d.models.Application;
import org.barino3d.models.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class ControllersHelper {

    /**
     * Gets email for current user from security context
     *
     * @return current user email (equivalent 'name' for security context)
     */
    public static String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Check that user own application. Throws exception otherwise
     *
     * @param currentUser current user entity
     * @param application application to check
     */
    public static void checkUserIsOwnerOfTheApplication(UserEntity currentUser, Application application) {
        UserEntity appOwnerUser = application.getUser();
        if (!currentUser.equals(appOwnerUser)) {
            throw new RuntimeException(String.format("Application with id=%s not owned by the current user", application.getId()));
        }
    }

}
