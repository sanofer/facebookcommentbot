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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FbCommentService {
    private static final String LATEST_COMMENTS = "latest";
    @Autowired
    private CommentTimeRepository commentTimeRepository;

    /**
     * This method is used to fetch all comments for various posts in a given page
     *
     * @return List<FbComment>
     */
    public List<FbComment> getCommentsForPosts(FacebookClient facebookClient, String from) {
        List<FbComment> fbComment = new ArrayList<>();
        List<CommentDetail> comments;
        int commentsCounter = 0;
        Connection<Post> pagePosts = facebookClient.fetchConnection("me/feed", Post.class);
        for (List<Post> posts : pagePosts)
            for (Post post : posts) {
                if (from.equals(LATEST_COMMENTS)) {
                    comments = getLatestComments(facebookClient, post.getId());
                } else {
                    comments = getComments(facebookClient, post.getId());
                }
                fbComment.add(new FbComment(post.getId(), post.getMessage(), comments));
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

    /**
     * This method is used to fetch latest comments on the page, since the last fetch
     *
     * @return List<CommentDetail>
     */
    public List<CommentDetail> getLatestComments(FacebookClient client, String post_id) {
        List<CommentDetail> commentsDetails = new ArrayList<CommentDetail>();
        CommentDetail commentDetail = null;
        Date lastFetchedDate = commentTimeRepository.findFirstByOrderByIdDesc().getCommentFetchedDate();
        Connection<Comment> allComments = client.fetchConnection(post_id + "/comments", Comment.class);

        for (List<Comment> postComment : allComments) {
            for (Comment comment : postComment) {
                if (comment.getCreatedTime().after(lastFetchedDate)) {
                    commentDetail = buildComments(client, comment);
                    commentsDetails.add(commentDetail);
                }
            }
        }
        return commentsDetails;
    }

    /**
     * This method is used to fetch all comments on the page
     *
     * @return List<CommentDetail>
     */
    public List<CommentDetail> getComments(FacebookClient client, String post_id) {
        List<CommentDetail> commentsDetails = new ArrayList<CommentDetail>();
        CommentDetail commentDetail = null;
        Connection<Comment> allComments = client.fetchConnection(post_id + "/comments", Comment.class);
        for (List<Comment> postComment : allComments) {
            for (Comment comment : postComment) {
                commentDetail = buildComments(client, comment);
                commentsDetails.add(commentDetail);
            }
        }
        return commentsDetails;
    }

    /**
     * This method is used to fetch a comment with all the replies
     *
     * @return CommentDetail
     */
    private CommentDetail buildComments(FacebookClient client, Comment comment) {
        CommentDetail commentDetail = new CommentDetail(comment.getFrom().getName(), comment.getMessage());
        List<String> replies = new ArrayList<>();
        Connection<Comment> commentConnection = client.fetchConnection(comment.getId() + "/comments", Comment.class);
        for (List<Comment> replyComment : commentConnection) {
            for (Comment commentReply : replyComment) {
                replies.add(commentReply.getMessage());
            }
            commentDetail.setReplies(replies);
        }
        return commentDetail;
    }

}

