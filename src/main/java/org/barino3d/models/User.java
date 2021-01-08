package org.barino3d.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
@ToString(exclude = "applications")
public class User {

    @DBRef
    List<Application> applications = new ArrayList<>();
    @Id
    private String id;
    @Column(nullable = false, length = 120, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String encryptedPassword;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public void addApplication(Application application) {
        applications.add(application);
    }

    public void addApplications(List<Application> applicationsToAdd) {
        applications.addAll(applicationsToAdd);
    }

}
