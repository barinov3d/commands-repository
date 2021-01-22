package org.barino3d.controllers;

import lombok.AllArgsConstructor;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.models.UserDto;
import org.barino3d.services.ApplicationService;
import org.barino3d.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class ApplicationController {

    private final ApplicationService applicationService;
    private final UserService userService;

    @PostMapping("user/{userId}/application")
    public String addApplication(@PathVariable String userId, @ModelAttribute(value = "newApp") Application application) {
        application.setUser(userService.findById(userId));
        applicationService.save(application);
        return "redirect:/user/" + userId + "/application/" + application.getId();
    }

    @GetMapping("user/{userId}/application/{id}")
    public String getApplication(@PathVariable String userId, @PathVariable String id, Model model) {
        UserDto user = userService.findById(userId);
        List<Application> applications = applicationService.findAllByUser(user);
        final Application currentApplication = applicationService.findById(id);
        List<Command> commands = currentApplication.getCommands();
        model.addAttribute("user", user);
        model.addAttribute("currentApplication", currentApplication);
        model.addAttribute("applications", applications);
        model.addAttribute("commands", commands);
        model.addAttribute("newApp", new Application());
        model.addAttribute("newCommand", new Command());
        return "index";
    }

    @PostMapping("user/{userId}/application/{id}/delete")
    public String deleteApplication(@PathVariable String userId, @PathVariable String id) {
        if (applicationService.findAllByUser(userService.findById(userId)).size() == 1) {
            return "redirect:/";
        }
        applicationService.delete(applicationService.findById(id));
        return "redirect:/user/" + userId + "/application/" + applicationService.findAllByUser(userService.findById(userId)).get(0).getId();
    }


}
