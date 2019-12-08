package com.example.fbcommentdemo.model;

import lombok.Data;

import java.util.List;

public @Data class CommentDetail {
private String name;
private String comment;
private List<String> Replies;

    public CommentDetail(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }


}
