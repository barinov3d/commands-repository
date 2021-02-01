package org.barino3d.controllers;

import lombok.AllArgsConstructor;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.models.UserEntity;
import org.barino3d.services.ApplicationService;
import org.barino3d.services.CommandService;
import org.barino3d.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class CommandController {

    private final ApplicationService applicationService;
    private final CommandService commandService;
    private final UserService userService;

    @PostMapping("application/{id}/command")
    public String addCommand(@PathVariable String id, @ModelAttribute(value = "newCommand") Command command) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = userService.findByEmail(authentication.getName()).getId();
        UserEntity currentUser = userService.findById(userId);
        UserEntity appOwnerUser = applicationService.findById(id).getUser();
        if (!currentUser.equals(appOwnerUser)) {
            throw new RuntimeException(String.format("Application with id=%s not owned by the current user", id));
        }
        Command newCommand = new Command();
        newCommand.setDescription(command.getDescription());
        newCommand.setText(command.getText());
        final Application application = applicationService.findById(id);
        newCommand.setApplication(application);
        application.addCommand(commandService.save(newCommand));
        applicationService.save(application);
        return "redirect:/" + "application/" + id;
    }

    @PostMapping("application/{id}/command/{cmdId}/delete")
    public String deleteCommand(@PathVariable String id, @PathVariable String cmdId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = userService.findByEmail(authentication.getName()).getId();
        UserEntity currentUser = userService.findById(userId);
        UserEntity appOwnerUser = applicationService.findById(id).getUser();
        if (!currentUser.equals(appOwnerUser)) {
            throw new RuntimeException(String.format("Application with id=%s not owned by the current user", id));
        }
        commandService.delete(commandService.findById(cmdId));
        Application app = applicationService.findById(id);
        //TODO dirty workaround. NULL element
        if ((app.getCommands().size() == 1) && app.getCommands().get(0) == null) {
            app.setCommands(new ArrayList<>());
            applicationService.save(app);
        }
        return "redirect:/" + "application/" + id;
    }


}
