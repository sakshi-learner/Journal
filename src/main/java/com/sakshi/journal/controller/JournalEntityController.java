package com.sakshi.journal.controller;
import  java.util.*;

import com.sakshi.journal.entity.JournalEntity;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("_/journal")
public class JournalEntityController {

    private Map<ObjectId, JournalEntity> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntity> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public  boolean createEntry(@RequestBody JournalEntity Entry){
           journalEntries.put(Entry.getId(), Entry);
           return true;
    }

    @GetMapping("/id/{myId}")
    public  JournalEntity getJournalEntityById(@PathVariable ObjectId myId){
     return journalEntries.get(myId);

    }
    @DeleteMapping("/id/{myId}")
    public  JournalEntity removeJournalEntityById(@PathVariable ObjectId myId){
        return journalEntries.remove(myId);

    }

    @PutMapping("/id/{myId}")
    public  JournalEntity updateJournalEntityById(@PathVariable ObjectId myId, @RequestBody JournalEntity Entry){
        return journalEntries.put(myId, Entry);

    }
}
