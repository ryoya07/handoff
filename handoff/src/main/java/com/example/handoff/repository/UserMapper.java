package com.example.handoff.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.handoff.domain.model.User;

@Mapper
public interface UserMapper {
    User selectByLoginId(@Param("loginId") String loginId);
    
    Long selectIdByLoginId(@Param("loginId") String loginId);
}