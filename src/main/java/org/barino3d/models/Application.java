package org.barino3d.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "commands")
@Document(collection = "applications")
@ToString(exclude = "commands")
@NoArgsConstructor
public class Application {

    @Id
    private String id;
    @NotNull(message = "name should not be less than 3 symbols")
    @Size(min = 2)
    @Field(name = "name")
    private String name;
    @Field(name = "commands")
    @DBRef
    private List<Command> commands = new ArrayList<>();
    @DBRef
    private UserEntity user;

    public Application(String name) {
        this.name = name;
    }

    public Application(String name, List<Command> commands) {
        this.name = name;
        this.commands = commands;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public void addCommands(List<Command> commandsToAdd) {
        commands.addAll(commandsToAdd);
    }
}
