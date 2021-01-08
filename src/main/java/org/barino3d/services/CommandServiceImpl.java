package org.barino3d.services;

import org.barino3d.exceptions.CommandNotFoundException;
import org.barino3d.models.Command;
import org.barino3d.repositories.CommandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandServiceImpl implements CommandService {
    final CommandRepository commandRepository;

    public CommandServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public List<Command> findAll() {
        return commandRepository.findAll();
    }

    @Override
    public Command findById(String id) throws CommandNotFoundException {
        return commandRepository.findById(id)
                .orElseThrow(() -> new CommandNotFoundException(String.format("Command with id: %s not found", id)));
    }

    @Override
    public void deleteById(String id) throws CommandNotFoundException {
        commandRepository.findById(id)
                .orElseThrow(() -> new CommandNotFoundException(String.format("Command with id: %s not found", id)));
        commandRepository.deleteById(id);
    }

    @Override
    public void delete(Command command) throws CommandNotFoundException {
        commandRepository.delete(command);
    }

    @Override
    public Command save(Command command) {
        return commandRepository.save(command);
    }

}
