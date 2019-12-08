package com.example.fbcommentdemo.service;

import com.example.fbcommentdemo.model.CommentDetail;
import com.example.fbcommentdemo.model.CommentTime;
import com.example.fbcommentdemo.model.FbComment;
import com.example.fbcommentdemo.repository.CommentTimeRepository;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Comment;
import com.restfb.types.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FbCommentService {
    private static final String LATEST_COMMENTS = "latest";
    @Autowired
    private CommentTimeRepository commentTimeRepository;

    public List<FbComment> getCommentsForPosts(FacebookClient facebookClient, String from) {
        List<FbComment> fbComment = new ArrayList<>();
        int commentsCounter = 0;
        Connection<Post> pagePosts = facebookClient.fetchConnection("me/feed", Post.class);
        for (List<Post> posts : pagePosts)
            for (Post post : posts) {
                List<CommentDetail> comments = getComment(facebookClient, post.getId(), from);
                fbComment.add(new FbComment(post.getId(), comments));
                if (comments.size() > 0) {
                    commentsCounter++;
                }
            }
        if (commentsCounter > 0) {
            CommentTime commentTime = new CommentTime();
            commentTime.setDate(new Date());
            commentTimeRepository.save(commentTime);
        }
        return fbComment;
    }

    public List<CommentDetail> getComment(FacebookClient client, String post_id, String from) {
        List<CommentDetail> commentsDetails = new ArrayList<CommentDetail>();
        Date lastFetchedDate = commentTimeRepository.findFirstByOrderByIdDesc().getCommentFetchedDate();
        Connection<Comment> allComments = client.fetchConnection(post_id + "/comments", Comment.class);
        if (from.equals(LATEST_COMMENTS)) {
            for (List<Comment> postComment : allComments) {
                for (Comment comment : postComment) {
                    if (comment.getCreatedTime().after(lastFetchedDate)) {
                        CommentDetail commentDetail = new CommentDetail(comment.getFrom().getName(), comment.getMessage());
                        commentsDetails.add(commentDetail);

                    }
                }
                return commentsDetails;
            }
        }
        for (List<Comment> postComment : allComments) {
            for (Comment comment : postComment) {
                CommentDetail commentDetail = new CommentDetail(comment.getFrom().getName(), comment.getMessage());
                commentsDetails.add(commentDetail);
            }
        }

        return commentsDetails;
    }

}
