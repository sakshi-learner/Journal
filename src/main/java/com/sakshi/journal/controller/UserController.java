package com.sakshi.journal.controller;

import com.sakshi.journal.entity.User;
import com.sakshi.journal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping
    public  void createUser(@RequestBody User user){
       try{
           userService.saveEntry(user);
       } catch (Exception e) {
           System.out.println("An exception accure whiling save user");
           throw new RuntimeException(e);
       }
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateUSer(@RequestBody User user,@PathVariable String username){
      User userInDb =  userService.findByUserName(username);
      if(userInDb != null){
          userInDb.setUserName(user.getUserName());
          userInDb.setPassword(user.getPassword());
          userService.saveEntry(userInDb);
      }

        return new ResponseEntity<>(NO_CONTENT);
    }



}
