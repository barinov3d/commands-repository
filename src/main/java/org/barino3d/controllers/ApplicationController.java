package org.barino3d.controllers;

import lombok.AllArgsConstructor;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.repositories.CommandRepository;
import org.barino3d.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Book;
import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class ApplicationController {

    private final ApplicationService applicationService;
    private final CommandRepository commandRepository;

    @PostMapping("/application")
    public String addApplication(@ModelAttribute(value = "newApp") Application app) {
        applicationService.save(app);
        return "redirect:/" + app.getId();
    }

    @GetMapping("/{id}")
    public String getApplication(@PathVariable String id, Model model) {
        List<Application> applications = applicationService.findAll();
        final Application currentApplication = applicationService.findById(id);
        List<Command> commands = currentApplication.getCommands();
        model.addAttribute("currentApplication", currentApplication);
        model.addAttribute("applications", applications);
        model.addAttribute("commands", commands);
        model.addAttribute("newApp", new Application());
        model.addAttribute("newCommand", new Command());
        return "index";
    }

    @PostMapping("{id}/delete")
    public String deleteCommand(@PathVariable String id) {
        applicationService.delete(applicationService.findById(id));
        return "redirect:/" + applicationService.findAll().get(0).getId();
    }


}
