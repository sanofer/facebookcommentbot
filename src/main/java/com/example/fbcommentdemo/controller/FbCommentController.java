package com.example.fbcommentdemo.controller;

import com.example.fbcommentdemo.service.FbCommentService;
import com.example.fbcommentdemo.model.FbComment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class FbCommentController {
    private static final String LATEST_COMMENTS = "latest";
    @Autowired
    private FbCommentService fbCommentService;
    @Value("${app.id}")
    private String appId;
    @Value("${app.secret}")
    private String appSecret;
    private String myAccessToken = "EAAHYCmtWqV8BALgUrxirg4li88ynX21LzBZCJNdj3ixzgyskcA97PWqea7MP2OvxyZByMhYM7qzRbCGyUA2YJOs9ZBCEIS5ratoMh16WPQZAr8jrgCn3fCyO0lYZA3xELnWDC9MsDUcWX2M09Oya9cfJ5Q4dMdCIpY5uYNWjYI9XlpIoZCAvNn";


    @GetMapping("/comments")
    public @ResponseBody
    List<FbComment>
    getComments() throws IOException {

        FacebookClient.AccessToken accessToken =
                new DefaultFacebookClient(Version.LATEST).obtainExtendedAccessToken(appId,
                        appSecret, myAccessToken);
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
        List<FbComment> fbComment = fbCommentService.getCommentsForPosts(facebookClient,"");
        return fbComment;
    }

    @GetMapping("/comments/latest")
    public @ResponseBody
    List<FbComment> getLatestComments() {
        FacebookClient.AccessToken accessToken =
                new DefaultFacebookClient(Version.LATEST).obtainExtendedAccessToken(appId,
                        appSecret, myAccessToken);
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
        List<FbComment> fbComment = fbCommentService.getCommentsForPosts(facebookClient,LATEST_COMMENTS);
        return fbComment;
    }


}
