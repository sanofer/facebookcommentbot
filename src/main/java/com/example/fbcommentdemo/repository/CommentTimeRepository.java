package com.example.fbcommentdemo.repository;

import com.example.fbcommentdemo.model.CommentTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentTimeRepository extends JpaRepository<CommentTime, Long> {
    CommentTime findFirstByOrderByIdDesc();
}
