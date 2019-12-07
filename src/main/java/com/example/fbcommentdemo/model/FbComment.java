package com.example.fbcommentdemo.model;

import lombok.Data;

import java.util.List;

public @Data class FbComment {
    private String postId;
    private List<String> comments;

    public FbComment(String postId, List<String> comments) {
        this.postId=postId;
        this.comments=comments;
    }
}
