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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class CommandController {

    private final ApplicationService applicationService;
    private final CommandRepository commandRepository;

    @PostMapping("{id}/command")
    public String addCommand(@PathVariable String id, @ModelAttribute(value = "newCommand") Command command) {
        Command newCommand = new Command();
        newCommand.setDescription(command.getDescription());
        newCommand.setText(command.getText());
        final Application application = applicationService.findById(id);
        application.getCommands().add(commandRepository.save(newCommand));
        applicationService.save(application);
        return "redirect:/" + id;
    }

    @PostMapping("/command/{id}/delete")
    public String deleteCommand(@PathVariable String id, @ModelAttribute(value = "currentApplication") Application currentApplication) {
        commandRepository.delete(commandRepository.findById(id).get());
        return "redirect:/" + currentApplication.getId();
    }



}
