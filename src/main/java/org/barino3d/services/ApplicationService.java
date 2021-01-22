package org.barino3d.services;

import org.barino3d.exceptions.ApplicationNotFoundException;
import org.barino3d.exceptions.DuplicateApplicationNameException;
import org.barino3d.models.Application;
import org.barino3d.models.UserEntity;

import java.util.List;

public interface ApplicationService {

    List<Application> findAll();

    List<Application> findAllByUser(UserEntity user);

    Application findById(String id) throws ApplicationNotFoundException;

    void deleteById(String id) throws ApplicationNotFoundException;

    void delete(Application author) throws ApplicationNotFoundException;

    Application findByName(String name) throws ApplicationNotFoundException;

    Application save(Application author) throws DuplicateApplicationNameException;

}
