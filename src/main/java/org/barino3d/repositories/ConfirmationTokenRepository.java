package org.barino3d.repositories;

import org.barino3d.models.ConfirmationToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
