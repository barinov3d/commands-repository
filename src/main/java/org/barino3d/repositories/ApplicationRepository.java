package org.barino3d.repositories;

import org.barino3d.models.Application;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationRepository extends MongoRepository<Application, String>, ApplicationCustomizeRepository<Application, String> {
}
