package dev.abhishek.mongodbbasics.controller;

import dev.abhishek.mongodbbasics.entity.Journal;
import dev.abhishek.mongodbbasics.service.JournalService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/journals")
@RequiredArgsConstructor
public class JournalController extends Object{

    private final JournalService journalService;

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournals() {
        List<Journal> journals = journalService.getAllJournals();
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journal> getEntryById(@PathVariable ObjectId id) {
        Optional<Journal> journal = journalService.findEntryById(id);
        if (journal.isPresent()) {
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Journal> saveEntry(@RequestBody Journal journal) {
        try {
            journal.setDate(LocalDateTime.now());
            journalService.saveEntry(journal);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journal> updateJournalById(@PathVariable ObjectId id, @RequestBody Journal journal) {
        try {
            Journal journal1 = journalService.updateEntryById(id, journal);
            return new ResponseEntity<>(journal1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id) {
        try {
            journalService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
