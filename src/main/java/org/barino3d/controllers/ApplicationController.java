package org.barino3d.controllers;

import lombok.AllArgsConstructor;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.models.UserEntity;
import org.barino3d.services.ApplicationService;
import org.barino3d.services.CommandService;
import org.barino3d.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static org.barino3d.controllers.ControllersHelper.checkUserIsOwnerOfTheApplication;
import static org.barino3d.controllers.ControllersHelper.getCurrentUserEmail;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CommandService commandService;
    private final UserService userService;

    @PostMapping("application")
    public String addApplication(@ModelAttribute(value = "newApp") Application application) {
        String userId = userService.findByEmail(getCurrentUserEmail()).getId();
        application.setUser(userService.findById(userId));
        applicationService.save(application);
        return "redirect:/" + "application/" + application.getId();
    }

    @GetMapping("application/{id}")
    public String getApplication(@PathVariable String id, Model model) {
        String userId = userService.findByEmail(getCurrentUserEmail()).getId();
        UserEntity currentUser = userService.findById(userId);
        checkUserIsOwnerOfTheApplication(currentUser, applicationService.findById(id));
        UserEntity libraryUser = userService.findById("600eb29748058538a3274fee");
        List<Application> applications = applicationService.findAllByUser(currentUser);
        List<Application> libraryApplications = applicationService.findAllByUser(libraryUser);
        final Application currentApplication = applicationService.findById(id);
        List<Command> commands = currentApplication.getCommands();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentApplication", currentApplication);
        model.addAttribute("applications", applications);
        model.addAttribute("libraryApplications", libraryApplications);
        model.addAttribute("libraryApplication", new Application());
        model.addAttribute("commands", commands);
        model.addAttribute("newApp", new Application());
        model.addAttribute("newCommand", new Command());
        return "index";
    }

    @PostMapping("application/{id}/delete")
    public String deleteApplication(@PathVariable String id) {
        String userId = userService.findByEmail(getCurrentUserEmail()).getId();
        UserEntity currentUser = userService.findById(userId);
        final Application application = applicationService.findById(id);
        checkUserIsOwnerOfTheApplication(currentUser, application);
        if (applicationService.findAllByUser(userService.findById(userId)).size() == 1) {
            return "redirect:/";
        }
        commandService.deleteAllByApplication(application);
        applicationService.delete(application);
        return "redirect:/" + "application/" + applicationService.findAllByUser(userService.findById(userId)).get(0).getId();
    }

    @PostMapping("application/{id}/library")
    public String getCommandsFromLibraryApplication(@PathVariable String id, @ModelAttribute(value = "libraryApplication") Application libraryApplication) {
        final Application userApplication = applicationService.findById(id);
        final Application libraryApplicationFromRepo = applicationService.findById(libraryApplication.getId());
        libraryApplicationFromRepo.getCommands().forEach(
                c -> {
                    userApplication.addCommand(commandService.save(new Command(c.getText(), c.getDescription(), userApplication)));
                }
        );
        applicationService.save(userApplication);
        return "redirect:/" + "application/" + id;
    }

}
