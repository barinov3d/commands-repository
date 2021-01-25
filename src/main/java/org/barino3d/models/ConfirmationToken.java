package org.barino3d.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "confirmationToken")
@EqualsAndHashCode(exclude = "userEntity")
public class ConfirmationToken {

    @Id
    private String id;

    @Field(name = "confirmationToken")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @DBRef
    @Field(name = "userEntity")
    private UserEntity userEntity;

    public ConfirmationToken(UserEntity userEntity) {
        this.userEntity = userEntity;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}