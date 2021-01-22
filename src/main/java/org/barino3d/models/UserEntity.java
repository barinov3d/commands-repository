package org.barino3d.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
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
    @Column(nullable = false, length = 120, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String encryptedPassword;

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
