package org.barino3d.repositories;

import org.barino3d.models.Application;
import org.barino3d.models.Command;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommandRepository extends MongoRepository<Command, String> {
    void deleteAllByApplication(Application application);
}
