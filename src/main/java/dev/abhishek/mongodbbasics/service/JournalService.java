package dev.abhishek.mongodbbasics.service;

import dev.abhishek.mongodbbasics.entity.Journal;
import dev.abhishek.mongodbbasics.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;

    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }

    public void saveEntry(Journal journal) {
        journalRepository.save(journal);
    }

    public Optional<Journal> getEntryById(ObjectId id) {
        Optional<Journal> entry = journalRepository.findById(id);
        System.out.println(entry);
        return entry;
    }

    public void deleteById(ObjectId id) {
        journalRepository.deleteById(id);
    }

    public Journal updateEntryById(ObjectId id, Journal journal) {
        // first get the object
        Journal journalEntry = journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal not found"));

        journalEntry.setTask(journal.getTask());
        journalEntry.setDescription(journal.getDescription());

        return journalRepository.save(journalEntry);
    }
}
