package com.example.memoria.repository;

import com.example.memoria.entity.User;
import com.example.memoria.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
