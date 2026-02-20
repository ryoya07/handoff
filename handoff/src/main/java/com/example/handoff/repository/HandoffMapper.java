package com.example.handoff.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.handoff.domain.HandoffEdit;
import com.example.handoff.domain.view.HandoffView;

@Mapper
public interface HandoffMapper {
	
	int insert(
	    @Param("title") String title,
	    @Param("content") String content,
	    @Param("createdBy") Long createdBy
		);
	
    // 既存：一覧
    // List<HandoffView> selectAll(@Param("userId") Long userId);

    // ★編集画面用：1件取得（投稿者IDも取る）
    HandoffEdit selectById(@Param("id") Long id);

    // ★更新（投稿者本人のみ更新できるよう WHERE に created_by）
    int updateByIdAndUserId(
        @Param("id") Long id,
        @Param("title") String title,
        @Param("content") String content,
        @Param("userId") Long userId
    );

    // ★削除（投稿者本人のみ削除できるよう WHERE に created_by）
    int deleteByIdAndUserId(
        @Param("id") Long id,
        @Param("userId") Long userId
    );
    
    // ★既読フラグも含めて1件取得（投稿者IDは不要）
    HandoffView selectByIdWithReadFlag(
    	    @Param("id") Long id,
    	    @Param("userId") Long userId
    );

    // ★一覧取得も既読フラグを含める（投稿者IDは不要）
List<HandoffView> selectAll(@Param("loginUserId") Long loginUserId);

}