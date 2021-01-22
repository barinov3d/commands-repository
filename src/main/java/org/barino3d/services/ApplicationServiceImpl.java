package org.barino3d.services;

import org.barino3d.exceptions.ApplicationNotFoundException;
import org.barino3d.exceptions.DuplicateApplicationNameException;
import org.barino3d.models.Application;
import org.barino3d.models.UserEntity;
import org.barino3d.repositories.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    @Override
    public List<Application> findAllByUser(UserEntity user) {
        return applicationRepository.findAllByUser(user);
    }

    @Override
    public Application findById(String id) throws ApplicationNotFoundException {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(String.format("Application with id: %s not found", id)));
    }

    @Override
    public void deleteById(String id) throws ApplicationNotFoundException {
        final Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(String.format("Application with id: %s not found", id)));
        applicationRepository.deleteById(id);
    }

    @Override
    public void delete(Application application) throws ApplicationNotFoundException {
        applicationRepository.delete(application);
    }

    @Override
    public Application findByName(String name) throws ApplicationNotFoundException {
        final Application application = applicationRepository.findByName(name);
        if (application == null) {
            throw new ApplicationNotFoundException(String.format("Application with name: %s not found", name));
        }
        return application;
    }

    @Override
    public Application save(Application application) throws DuplicateApplicationNameException {
        if (application.getId() == null && (applicationRepository.findByName(application.getName()) != null)) {
            throw new DuplicateApplicationNameException(
                    "Application with name '" + application.getName() + "' is already define in the scope");
        }
        return applicationRepository.save(application);
    }

}
