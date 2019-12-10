package com.example.fbcommentdemo.controller;

import com.example.fbcommentdemo.model.FbSecurityInfo;
import com.example.fbcommentdemo.service.FbCommentService;
import com.example.fbcommentdemo.model.FbComment;
import com.example.fbcommentdemo.service.FbSecurityService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class FbCommentController {
    private static final String LATEST_COMMENTS = "latest";
    @Autowired
    private FbCommentService fbCommentService;
    @Autowired
    private FbSecurityService fbSecurityService;

    private List<FbComment> fbComment;
    private FacebookClient facebookClient;

    /**
     * This method is used fetch the security details used for facebook graph api access
     */
    @PostConstruct
    public void init() {
        FbSecurityInfo fbSecurityInfo = fbSecurityService.getSecurityDetails();
        FacebookClient.AccessToken accessToken =
                new DefaultFacebookClient(Version.LATEST).obtainExtendedAccessToken(fbSecurityInfo.getAppId(),
                        fbSecurityInfo.getAppSecret(), fbSecurityInfo.getAccessToken());
        facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
    }

    /**
     * This method is used to fetch all comments
     *
     * @return FbComment Json response
     */
    @GetMapping
    public @ResponseBody
    List<FbComment>
    getComments() {
        fbComment = fbCommentService.getCommentsForPosts(facebookClient, "");
        return fbComment;
    }

    /**
     * This method is used to fetch the latest comments since the last fetch
     *
     * @return FbComment Json response
     */
    @GetMapping("latest")
    public @ResponseBody
    List<FbComment> getLatestComments() {
        fbComment = fbCommentService.getCommentsForPosts(facebookClient, LATEST_COMMENTS);
        return fbComment;
    }


}
