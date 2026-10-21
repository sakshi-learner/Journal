package com.sakshi.journal.service;

import com.sakshi.journal.entity.User;
import com.sakshi.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
 private UserRepository userRepository;

    public void saveEntry(User user){
        userRepository.save(user);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new ArrayList<>(List.of("USER")));   // role server decide karta hai
        userRepository.save(user);
    }

    public List<User>   getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId  id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

}
