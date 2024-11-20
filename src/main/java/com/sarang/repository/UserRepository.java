package com.sarang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sarang.model.UserVO;

public interface UserRepository extends JpaRepository<UserVO,Long>{
    UserVO findByUserId(String userId);
}
