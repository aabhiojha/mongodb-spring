package dev.abhishek.mongodbbasics.service;

import dev.abhishek.mongodbbasics.entity.Journal;
import dev.abhishek.mongodbbasics.entity.User;
import dev.abhishek.mongodbbasics.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;
    private final UserService userService;

    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }

    @Transactional
    public void saveEntry(Journal journal, String userName) {
        User userObjDb = userService.findByUserName(userName);
        journal.setDate(LocalDateTime.now());
        Journal save = journalRepository.save(journal);
        userObjDb.getJournalEntries().add(save);
        userService.saveEntry(userObjDb);
    }

    public Optional<Journal> findEntryById(ObjectId id) {
        Optional<Journal> entry = journalRepository.findById(id);
        System.out.println(entry);
        return entry;
    }

    @Transactional
    public void deleteById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        journalRepository.deleteById(id);
        userService.saveEntry(user);
    }

    @Transactional
    public Journal updateEntryById(ObjectId id, Journal journal) {
        // first get the object
        Journal journalEntry = journalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Journal not found"));

        journalEntry.setTask(journal.getTask());
        journalEntry.setDescription(journal.getDescription());

        return journalRepository.save(journalEntry);
    }

    @Transactional
    public Journal updateEntryById(Journal oldJournal) {
//        Journal journalEntry = journalRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Journal not found"));
        return journalRepository.save(oldJournal);

    }
}
