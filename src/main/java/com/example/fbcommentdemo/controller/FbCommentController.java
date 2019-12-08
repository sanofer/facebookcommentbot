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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class FbCommentController {
    private static final String LATEST_COMMENTS = "latest";
    @Autowired
    private FbCommentService fbCommentService;
    @Autowired
    private FbSecurityService fbSecurityService;

    private List<FbComment> fbComment;


    @GetMapping("/comments")
    public @ResponseBody
    List<FbComment>
    getComments() {
        FbSecurityInfo fbSecurityInfo = fbSecurityService.getSecurityDetails();
        FacebookClient.AccessToken accessToken =
                new DefaultFacebookClient(Version.LATEST).obtainExtendedAccessToken(fbSecurityInfo.getAppId(),
                        fbSecurityInfo.getAppSecret(), fbSecurityInfo.getAccessToken());
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
        fbComment = fbCommentService.getCommentsForPosts(facebookClient, "");
        return fbComment;
    }

    @GetMapping("/comments/latest")
    public @ResponseBody
    List<FbComment> getLatestComments() {
        FbSecurityInfo fbSecurityInfo = fbSecurityService.getSecurityDetails();
        FacebookClient.AccessToken accessToken =
                new DefaultFacebookClient(Version.LATEST).obtainExtendedAccessToken(fbSecurityInfo.getAppId(),
                        fbSecurityInfo.getAppSecret(), fbSecurityInfo.getAccessToken());
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken.getAccessToken(), Version.LATEST);
        fbComment = fbCommentService.getCommentsForPosts(facebookClient, LATEST_COMMENTS);
        return fbComment;
    }


}
