package com.example.fbcommentdemo.repository;

import com.example.fbcommentdemo.model.FbSecurityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FbSecurityRepository extends JpaRepository<FbSecurityInfo,Long> {
    FbSecurityInfo findFirstByOrderById();
}
