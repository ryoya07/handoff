package com.example.handoff.repository;

import com.example.handoff.domain.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectByLoginId(@Param("loginId") String loginId);
}