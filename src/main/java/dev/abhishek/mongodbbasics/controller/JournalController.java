package dev.abhishek.mongodbbasics.controller;

import dev.abhishek.mongodbbasics.entity.Journal;
import dev.abhishek.mongodbbasics.entity.User;
import dev.abhishek.mongodbbasics.service.JournalService;
import dev.abhishek.mongodbbasics.service.UserService;
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
public class JournalController {

    private final JournalService journalService;
    private final UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<Journal>> getAllJournals(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<Journal> journals = user.getJournalEntries();
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Journal> getEntryById(@PathVariable ObjectId id) {
//        List<Journal> journals = journalService.findEntryById(id).orElseThrow(()-> new Exception("The journals were not found"));
//        return new ResponseEntity<>(journals,HttpStatus.OK);
//    }

    @PostMapping("{userName}")
    public ResponseEntity<Journal> saveEntry(@RequestBody Journal journal, @PathVariable String userName) {
        try {
            journalService.saveEntry(journal,userName);
//            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Journal> updateJournalById(@PathVariable ObjectId id, @RequestBody Journal journal) {
//        try {
//            Journal journal1 = journalService.updateEntryById(id, journal);
//            return new ResponseEntity<>(journal1, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

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
