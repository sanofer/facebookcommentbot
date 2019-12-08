package com.example.fbcommentdemo.model;

import lombok.Data;

public @Data class CommentDetail {
private String name;
private String comment;

    public CommentDetail(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }


}
