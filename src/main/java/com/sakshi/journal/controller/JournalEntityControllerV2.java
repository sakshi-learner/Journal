package com.sakshi.journal.controller;

import com.sakshi.journal.entity.JournalEntity;
import com.sakshi.journal.entity.User;
import com.sakshi.journal.service.JournalEntityService;
import com.sakshi.journal.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntityControllerV2 {

    @Autowired
    private JournalEntityService journalEntityService;
    @Autowired
    private UserService userService;

//    @GetMapping("{username}")
//    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
//        User user = userService.findByUserName(username);
//        List<JournalEntity> all = user.getJournalEntries();
//        if(all != null && !all.isEmpty()){
//            return  new ResponseEntity<>(all, HttpStatus.OK);
//        }
//      return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(username);
        List<JournalEntity> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping("{username}")
//    public  ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity Entry, @PathVariable String username){
//       try{
//
//           journalEntityService.saveEntry(Entry,username);
//           return new ResponseEntity<>(Entry,HttpStatus.CREATED);
//       } catch (Exception e) {
//           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//       }
//
//
//    }

    @PostMapping
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity entry) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            journalEntityService.saveEntry(entry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/id/{myId}")
//    public  JournalEntity getJournalEntityById(@PathVariable ObjectId myId){
//        return journalEntityService.findById(myId).orElse(null);
//
//    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId myId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(username);
        boolean owns = user.getJournalEntries().stream().anyMatch(x -> x.getId().equals(myId));
        if (owns) {
            return journalEntityService.findById(myId)
                    .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//    @DeleteMapping("/id/{username}/{myId}")
//    public  boolean removeJournalEntityById(@PathVariable ObjectId myId, @PathVariable String username){
//        journalEntityService.delete(myId, username);
//        return true;
//    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> delete(@PathVariable ObjectId myId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        journalEntityService.delete(myId, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("/id/{username}/{myId}")
//    public  ResponseEntity<?> updateJournalEntityById(@PathVariable ObjectId myId,@PathVariable String username, @RequestBody JournalEntity newEntry){
//    JournalEntity old = journalEntityService.findById(myId).orElse(null);
//    if(old != null){
//        old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")?newEntry.getTitle(): old.getTitle());
//        old.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
//        journalEntityService.saveEntry(old);
//        return  new ResponseEntity<>(old, HttpStatus.OK);
//
//    }
//    return new ResponseEntity<>(HttpStatus.NOT_FOUND);}

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> update(@PathVariable ObjectId myId, @RequestBody JournalEntity newEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(username);
        boolean owns = user.getJournalEntries().stream().anyMatch(x -> x.getId().equals(myId));
        if (owns) {
            JournalEntity old = journalEntityService.findById(myId).orElse(null);
            if (old != null) {
                if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) old.setTitle(newEntry.getTitle());
                if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) old.setContent(newEntry.getContent());
                journalEntityService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
