package com.example.fbcommentdemo.model;

import lombok.Data;

import java.util.List;

public @Data
class FbComment {
    private String postId;
    private String post;
    private List<CommentDetail> commentDetails;

    public FbComment(String postId, String post, List<CommentDetail> commentDetails) {
        this.postId = postId;
        this.post = post;
        this.commentDetails = commentDetails;
    }
}
