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

@Data
@EqualsAndHashCode(exclude = "application")
@Document(collection = "commands")
@NoArgsConstructor
@ToString(exclude = "application")
public class Command {

    @Id
    private String id;

    @NotNull(message = "text should not be less than 3 symbols")
    @Size(min = 2)
    @Field(name = "text")
    private String text;
    @NotNull(message = "description should not be less than 3 symbols")
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
