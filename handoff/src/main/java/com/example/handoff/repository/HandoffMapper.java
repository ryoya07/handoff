package com.example.handoff.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.handoff.domain.view.HandoffView;

@Mapper
public interface HandoffMapper {
	List<HandoffView> selectAll();

int insert(
	    @Param("title") String title,
	    @Param("content") String content,
	    @Param("createdBy") Long createdBy
		);

List<HandoffView> selectAll(@Param("loginUserId") Long loginUserId);

}