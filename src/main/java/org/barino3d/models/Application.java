package org.barino3d.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "applications")
@ToString(exclude = "commands")
@NoArgsConstructor
public class Application {

    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "commands")
    @DBRef
    private List<Command> commands = new ArrayList<>();

    public Application(String name) {
        this.name = name;
    }

    public Application(String name, List<Command> commands) {
        this.name = name;
        this.commands = commands;
    }

    public void addCommand(Command book) {
        commands.add(book);
    }

    public void addCommands(List<Command> commandsToAdd) {
        commands.addAll(commandsToAdd);
    }
}
