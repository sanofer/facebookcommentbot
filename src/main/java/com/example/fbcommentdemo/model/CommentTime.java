package com.example.fbcommentdemo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
public @Data
class CommentTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date commentFetchedDate;

    public void setDate(Date date) {
        this.commentFetchedDate = date;
    }
}
