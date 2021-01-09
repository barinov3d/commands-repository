package org.barino3d.controllers;

import lombok.AllArgsConstructor;
import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.barino3d.services.ApplicationService;
import org.barino3d.services.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("user/{userId}/application/{appId}/command")
    public String addCommand(@PathVariable String userId, @PathVariable String appId, @ModelAttribute(value = "newCommand") Command command) {
        Command newCommand = new Command();
        newCommand.setDescription(command.getDescription());
        newCommand.setText(command.getText());
        final Application application = applicationService.findById(appId);
        newCommand.setApplication(application);
        application.addCommand(commandService.save(newCommand));
        applicationService.save(application);
        return "redirect:/user/" + userId + "/application/" + appId;
    }

    @PostMapping("user/{userId}/application/{appId}/command/{cmdId}/delete")
    public String deleteCommand(@PathVariable String userId, @PathVariable String appId, @PathVariable String cmdId) {
        commandService.delete(commandService.findById(cmdId));
        Application app = applicationService.findById(appId);
        //TODO dirty workaround. NULL element
        if ((app.getCommands().size() == 1) && app.getCommands().get(0) == null) {
            app.setCommands(new ArrayList<>());
            applicationService.save(app);
        }
        return "redirect:/user/" + userId + "/application/" + appId;
    }


}
