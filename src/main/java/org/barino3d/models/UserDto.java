package org.barino3d.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "applications")
@NoArgsConstructor
public class UserDto {

    List<Application> applications = new ArrayList<>();
    private String id;
    private String email;
    private String password;
    private String encryptedPassword;

    public UserDto(String email, String password) {
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
