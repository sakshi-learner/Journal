package com.sakshi.journal.service;

import com.sakshi.journal.entity.JournalEntity;
import com.sakshi.journal.entity.User;
import com.sakshi.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntityService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private  UserService userService;

    @Transactional
    public  void saveEntry(JournalEntity journalEntry, String username){
        try{
            User user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntity saved= journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error accure while save"+ e);
        }
    }

    public  void saveEntry(JournalEntity journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntity> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void delete(ObjectId id, String username){
        User user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }


}
