package org.barino3d.repositories;

import org.barino3d.models.ConfirmationToken;
import org.barino3d.models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByEmail(String email);

    UserEntity findByEmailIgnoreCase(String email);

    UserEntity findByConfirmationToken(ConfirmationToken confirmationToken);
}
