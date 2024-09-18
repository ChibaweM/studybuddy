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

import com.example.studybuddy.model.Cards;
import com.example.studybuddy.model.Topics;
import com.example.studybuddy.model.Users;
import com.example.studybuddy.repository.CardRepository;
import com.example.studybuddy.repository.TopicRepository;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardRepository cardRepository;
    private final TopicRepository topicRepository;

    public CardController(TopicRepository topicRepository, CardRepository cardRepository){
        this.cardRepository = cardRepository;
        this.topicRepository = topicRepository;
    }

    @PostMapping("/{topicsId}/register")
    private ResponseEntity<String> register(@RequestBody Cards card, @PathVariable Long topicsId){
        Optional<Topics> optionalTopic = topicRepository.findById(topicsId);
        if(!optionalTopic.isPresent()){
            return ResponseEntity.badRequest().body("Failed to create Card: Topic not found");
        }else if(card == null){
            return ResponseEntity.badRequest().body("Failed to create Card: Card not created properly");
        }
        card.setTopicId(optionalTopic.get().getId());
        cardRepository.save(card);
        return ResponseEntity.ok().body("Card added to Application");
    }

    @PostMapping("/{requestedId}")
    private ResponseEntity<Cards> findById(@PathVariable Long requestedId) {
        Optional<Cards> card = cardRepository.findById(requestedId);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAllTopicCards/{requestedId}")
    private Iterable<Cards> findAllbyUser(@PathVariable Long requestedId){
        Optional<Topics> topicOptional = topicRepository.findById(requestedId);
        if(!topicOptional.isPresent()){
            return null;
        }
        return topicOptional.get().getCards();
    }

    @GetMapping("/getAllTopics")
    private ResponseEntity<Iterable<Cards>> findAll(){
        return ResponseEntity.ok().body(cardRepository.findAll());
    }

    @DeleteMapping("/deleteAllByUser/{requestedId}")
    private ResponseEntity<String> deleteAllByUser(@PathVariable Long requestedId){
        Optional<Topics> topicOptional = topicRepository.findById(requestedId);
        if(!topicOptional.isPresent()){
            return ResponseEntity.badRequest().body("Couldn't find user");
        }
        cardRepository.deleteAll(topicOptional.get().getCards());
        return ResponseEntity.ok().body("Deleted Cards for Topic: " + topicOptional.get().getTopicName());
    }

    @DeleteMapping("/deleteAll")
    private ResponseEntity<String> deleteAll(){
        cardRepository.deleteAll();
        return ResponseEntity.ok().body("Deleted all cards in Database");
    }

    @DeleteMapping("/delete/{requestedId}")
    private ResponseEntity<String> deleteById(@PathVariable Long requestedId){
        Optional<Cards> deleteCard = cardRepository.findById(requestedId);

        if(deleteCard.isPresent()){
            return ResponseEntity.badRequest().body("Failed to delete");
        }
        cardRepository.delete(deleteCard.get());
        return ResponseEntity.ok().body("Deleted Successfully");     
    }
}
