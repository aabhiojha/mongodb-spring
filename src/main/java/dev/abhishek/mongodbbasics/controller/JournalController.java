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

import java.util.List;

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
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}/{id}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable ObjectId id,
            @RequestBody Journal newJournal,
            @PathVariable String userName) {
            // find the old entry
            Journal oldJournal = journalService.findEntryById(id).orElse(null);
            if (oldJournal != null){
                oldJournal.setTask(newJournal.getTask() != null && !newJournal.getTask().equals("") ? newJournal.getTask() : oldJournal.getTask());
                oldJournal.setDescription(newJournal.getDescription() != null && !newJournal.getDescription().equals("") ? newJournal.getDescription() : oldJournal.getDescription());
                Journal journal1 = journalService.updateEntryById(oldJournal);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{userName}/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId id, @PathVariable String userName) {
        try {
            journalService.deleteById(id, userName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
