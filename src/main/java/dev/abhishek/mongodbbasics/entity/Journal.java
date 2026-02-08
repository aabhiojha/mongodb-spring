package dev.abhishek.mongodbbasics.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class Journal {
    @Id
    private ObjectId id;
    @NonNull
    private String task;
    private String description;
    private LocalDateTime date;
}
