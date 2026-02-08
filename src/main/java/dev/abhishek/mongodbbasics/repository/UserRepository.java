package dev.abhishek.mongodbbasics.repository;

import dev.abhishek.mongodbbasics.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    public User findByUserName(String userName);
}
