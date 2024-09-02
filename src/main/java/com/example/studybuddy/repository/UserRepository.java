package com.example.studybuddy.repository;

import com.example.studybuddy.model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
