package org.barino3d.services;

import org.barino3d.exceptions.ApplicationNotFoundException;
import org.barino3d.exceptions.DuplicateApplicationNameException;
import org.barino3d.models.Application;

import java.util.List;

public interface ApplicationService {

    List<Application> findAll();

    Application findById(String id) throws ApplicationNotFoundException;

    void deleteById(String id) throws ApplicationNotFoundException;

    void delete(Application author) throws ApplicationNotFoundException;

    Application findByName(String name) throws ApplicationNotFoundException;

    Application save(Application author) throws DuplicateApplicationNameException;

}