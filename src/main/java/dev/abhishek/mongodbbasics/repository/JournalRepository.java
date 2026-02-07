package dev.abhishek.mongodbbasics.repository;

import dev.abhishek.mongodbbasics.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JournalRepository extends MongoRepository<Journal, ObjectId> {


    Optional<Journal> findById(ObjectId id);

}
