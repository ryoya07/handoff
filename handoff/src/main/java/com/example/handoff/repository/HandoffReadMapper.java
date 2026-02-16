package com.example.handoff.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HandoffReadMapper {
	int upsertRead(
		@Param("handoffId") Long handoffId,
		@Param("userId") Long userId
	);
}
