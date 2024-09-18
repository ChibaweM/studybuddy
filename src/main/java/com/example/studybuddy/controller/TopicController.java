package com.example.studybuddy.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.studybuddy.model.Topics;
import com.example.studybuddy.model.Users;
import com.example.studybuddy.repository.TopicRepository;
import com.example.studybuddy.repository.UserRepository;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;


    public TopicController(UserRepository userRepository,  TopicRepository topicRepository){
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}/register")
    private ResponseEntity<String> register(@RequestBody Topics topic, @PathVariable Long userId){
        Optional<Users> optionalUser = userRepository.findById(userId);
        if(!optionalUser.isPresent()){
            return ResponseEntity.badRequest().body("Failed to create topic: User not found");
        }else if(topic == null){
            return ResponseEntity.badRequest().body("Failed to create topic: Topic not created properly");
        }

        topic.setUserId(optionalUser.get().getId());
        topicRepository.save(topic);
        return ResponseEntity.ok().body("Topic added to Application");
    }

    @PostMapping("/{requestedId}")
    private ResponseEntity<Topics> findById(@PathVariable Long requestedId) {
        Optional<Topics> topic = topicRepository.findById(requestedId);
        return topic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllTopics")
    private Iterable<Topics> findAll(){
        return topicRepository.findAll();
    }

    @DeleteMapping("/deleteAll")
    private void deleteAll(){
        topicRepository.deleteAll();
    }
}
