package dev.abhishek.mongodbbasics.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")
@Getter
@Setter
public class Journal {
    @Id
    private ObjectId id;
    private String task;
    private String description;
    private LocalDateTime date;
}
