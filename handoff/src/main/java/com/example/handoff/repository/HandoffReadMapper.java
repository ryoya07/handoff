package com.example.handoff.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

// 既読情報を管理するテーブルへのアクセスを行うMapper。HandoffReadテーブルは、ユーザが特定のHandoffを既読か未読かを管理するためのテーブルで、主キーは (handoff_id, user_id) の複合キーとなる。
@Mapper
public interface HandoffReadMapper {
	int upsertRead(
		@Param("handoffId") Long handoffId,
		@Param("userId") Long userId
	);
	
	int resetReadByHandoffId(
		    @Param("handoffId") Long handoffId
		);
	
	int deleteByHandoffId(
		    @Param("handoffId") Long handoffId
		);
}
