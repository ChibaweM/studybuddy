package com.example.studybuddy.controller;


import com.example.studybuddy.model.Users;
import com.example.studybuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping("/auth/signup")
    private ResponseEntity<String> register(@RequestBody Users user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent() || user.getUsername()==null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        userRepository.save(user);
        return ResponseEntity.ok().body("User registered nicely tbh");
    }

    @GetMapping("/auth/signin")
    private ResponseEntity<String> login(@RequestBody Users user) {
        Users existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if(existingUser == null) {
            return ResponseEntity.badRequest().body("Username not found");
        }
        if(!user.getPassword().equals(existingUser.getPassword())) {
            return ResponseEntity.badRequest().body("Not the password for this user");
        }
        return ResponseEntity.ok().body("got the correct shit my bro");
    }

    @GetMapping("/getUser/{requestedId}")
    private ResponseEntity<Users> findById(@PathVariable Long requestedId) {
        Optional<Users> user = userRepository.findById(requestedId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllUsers")
    private Iterable<Users> findAll() {
        return userRepository.findAll();
    }
}
