package org.barino3d.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
@ToString(exclude = "applications")
public class User {

    @Id
    private String id;
    private String email;
    @DBRef
    List<Application> applications = new ArrayList<>();

    public User(String email) {
        this.email = email;
    }

    public void addApplication(Application application) {
        applications.add(application);
    }

    public void addApplications(List<Application> applicationsToAdd) {
        applications.addAll(applicationsToAdd);
    }

}
