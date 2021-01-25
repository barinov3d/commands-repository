package org.barino3d.models;

import lombok.Data;
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
@NoArgsConstructor
@Document(collection = "users")
@ToString(exclude = "applications")
public class UserEntity {

    @DBRef
    List<Application> applications = new ArrayList<>();
    @Id
    private String id;

    @NotNull(message = "User's email must not be null")
    @Size(min = 7, max = 120)
    @Field(name = "email")
    private String email;
    @NotNull()
    @Field(name = "encryptedPassword")
    private String encryptedPassword;
    @DBRef
    @Field(name = "confirmationToken")
    private ConfirmationToken confirmationToken;
    @Field(name = "isEnabled")
    private boolean isEnabled;

    public UserEntity(String email, String encryptedPassword) {
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }


    public void addApplication(Application application) {
        applications.add(application);
    }

    public void addApplications(List<Application> applicationsToAdd) {
        applications.addAll(applicationsToAdd);
    }

}
