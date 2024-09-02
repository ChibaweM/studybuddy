package com.example.studybuddy.repository;

import com.example.studybuddy.model.Cards;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Cards, Long> {
}
