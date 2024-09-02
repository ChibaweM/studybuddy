package com.example.studybuddy.repository;

import com.example.studybuddy.model.Topics;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topics, Long> {
}
