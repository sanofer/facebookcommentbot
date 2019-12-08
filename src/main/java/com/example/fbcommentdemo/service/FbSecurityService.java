package com.example.fbcommentdemo.service;

import com.example.fbcommentdemo.model.FbSecurityInfo;
import com.example.fbcommentdemo.repository.FbSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FbSecurityService {
    @Autowired
    private FbSecurityRepository fbSecurityRepository;

    public FbSecurityInfo getSecurityDetails() {
        FbSecurityInfo fbSecurityInfo = fbSecurityRepository.findFirstByOrderById();
        return fbSecurityInfo;
    }

}
