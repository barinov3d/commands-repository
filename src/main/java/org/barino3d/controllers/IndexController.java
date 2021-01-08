package org.barino3d.controllers;

import lombok.AllArgsConstructor;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.models.User;
import org.barino3d.services.ApplicationService;
import org.barino3d.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class IndexController {

    private final ApplicationService applicationService;
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        User user = userService.findByEmail("test1@gmail.com");
        final String userId = user.getId();
        List<Application> applications = applicationService.findAllByUser(userService.findById(userId));
        Application application;
        if (applications.size() > 0) {
            application = applications.get(0);
        } else {
            application = applicationService.save(new Application("Test application"));
        }
        List<Command> commands = application.getCommands();
        model.addAttribute("commands", commands);
        return "redirect:/" + userId + "/application/" + application.getId();
    }
}
