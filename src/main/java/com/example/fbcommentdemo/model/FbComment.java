package com.example.fbcommentdemo.model;

import lombok.Data;

import java.util.List;

public @Data class FbComment {
    private String postId;
    private List<CommentDetail> commentDetails;

    public FbComment(String postId, List<CommentDetail> commentDetails) {
        this.postId=postId;
        this.commentDetails=commentDetails;
    }
}
