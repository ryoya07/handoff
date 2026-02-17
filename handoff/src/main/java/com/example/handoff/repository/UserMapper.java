package com.example.handoff.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.handoff.domain.model.User;

@Mapper
public interface UserMapper {
    Long selectIdByLoginId(@Param("loginId") String loginId);
    User selectByLoginId(@Param("loginId") String loginId);

    int insertUser(
        @Param("loginId") String loginId,
        @Param("displayName") String displayName,
        @Param("passwordHash") String passwordHash
    );

    User selectById(@Param("id") Long id);

    int updateProfile(
        @Param("id") Long id,
        @Param("displayName") String displayName
    );

    int updatePasswordHash(
        @Param("id") Long id,
        @Param("passwordHash") String passwordHash
    );

    int softDelete(@Param("id") Long id);
}
