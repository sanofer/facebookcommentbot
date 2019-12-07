package com.example.fbcommentdemo.service;

import com.example.fbcommentdemo.model.FbComment;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FbCommentService {

    public List<FbComment> getCommentsForPosts(FacebookClient facebookClient) {
        List<FbComment> fbComment = new ArrayList<>();
        Connection<Post> pagePosts = facebookClient.fetchConnection("me/feed", Post.class);
        for (List<Post> posts : pagePosts)
            for (Post post : posts) {

                List<String> comments = getComment(facebookClient, post.getId());
                fbComment.add(new FbComment(post.getId(), comments));
            }
        return fbComment;
    }

    public List<String> getComment(FacebookClient client, String post_id) {
        List<String> comments = new ArrayList<String>();

        Connection<Comment> allComments = client.fetchConnection(post_id + "/comments", Comment.class);
        for (List<Comment> postcomments : allComments) {
            for (Comment comment : postcomments) {
                comments.add(comment.getMessage());
            }
        }
        return comments;
    }
}
