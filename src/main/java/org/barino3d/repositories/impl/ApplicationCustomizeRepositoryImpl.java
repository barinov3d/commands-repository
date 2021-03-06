package org.barino3d.repositories.impl;

import org.barino3d.models.Application;
import org.barino3d.models.UserEntity;
import org.barino3d.repositories.ApplicationCustomizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;


public class ApplicationCustomizeRepositoryImpl<T, ID> implements ApplicationCustomizeRepository<T, ID> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Application findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.findOne(query, Application.class);
    }

    @Override
    public Application findByNameAndUserId(String name, String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        query.addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.findOne(query, Application.class);
    }

    @Override
    public List<Application> findAllByUser(UserEntity user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user.id").is(user.getId()));
        return mongoTemplate.find(query, Application.class);
    }
}
