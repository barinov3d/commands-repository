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

import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class IndexController {

    private final ApplicationService applicationService;
    private final CommandRepository commandRepository;

    @GetMapping("/")
    public String newAuthorPage(Model model) {
        List<Application> applications = applicationService.findAll();
        List<Command> commands = commandRepository.findAll();
        model.addAttribute("applications", applications);
        model.addAttribute("commands", commands);
        return "index";
    }

}
