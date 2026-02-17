package com.example.handoff.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.handoff.domain.HandoffEdit;
import com.example.handoff.domain.view.HandoffView;

@Mapper
public interface HandoffMapper {
	//List<HandoffView> selectAll();

	HandoffEdit selectByID(@Param("id") Long id);
	
	//更新機能
	int updateByIdAndUserId(
			@Param("id") Long id,
			@Param("title") String title,
			@Param("content") String content,
			@Param("userId") Long userId
		);
	
	//削除機能
	int insert(
	    @Param("title") String title,
	    @Param("content") String content,
	    @Param("createdBy") Long createdBy
		);

List<HandoffView> selectAll(@Param("loginUserId") Long loginUserId);

}