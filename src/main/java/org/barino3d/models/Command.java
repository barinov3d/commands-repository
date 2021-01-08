package org.barino3d.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "commands")
@NoArgsConstructor
@ToString(exclude = "application")
public class Command {

    @Id
    private String id;
    @Field(name = "text")
    private String text;
    @Field(name = "description")
    private String description;
    @Field(name = "application")
    @DBRef
    private Application application;

    public Command(String text, String description, Application application) {
        this.text = text;
        this.description = description;
        this.application = application;
    }
}
