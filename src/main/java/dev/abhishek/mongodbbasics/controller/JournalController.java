package dev.abhishek.mongodbbasics.controller;

import dev.abhishek.mongodbbasics.entity.Journal;
import dev.abhishek.mongodbbasics.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journals")
@RequiredArgsConstructor
public class JournalController {

    private final JournalService journalService;

    @GetMapping
    public List<Journal> getAllEntries() {
        return journalService.getAllJournals();
    }

    @GetMapping("/{id}")
    public Journal getEntryById(@PathVariable ObjectId id) {
        return journalService.getEntryById(id).orElse(null);
    }

    @PostMapping
    public void saveEntry(@RequestBody Journal journal) {
        journal.setDate(LocalDateTime.now());
        journalService.saveEntry(journal);
    }

    @PutMapping("/{id}")
    public Journal updateJournalById(@PathVariable ObjectId id, @RequestBody Journal journal) {
        return journalService.updateEntryById(id, journal);
    }

    @DeleteMapping("/{id}")
    public boolean deleteEntry(@PathVariable ObjectId id) {
        journalService.deleteById(id);
        return true;
    }


}
