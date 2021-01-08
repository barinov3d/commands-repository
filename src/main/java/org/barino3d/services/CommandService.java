package org.barino3d.services;

import org.barino3d.exceptions.CommandNotFoundException;
import org.barino3d.models.Command;

import java.util.List;

public interface CommandService {

    List<Command> findAll();

    Command findById(String id) throws CommandNotFoundException;

    void deleteById(String id) throws CommandNotFoundException;

    void delete(Command author) throws CommandNotFoundException;

    Command save(Command command);

}
