package com.example.handoff.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.handoff.domain.view.HandoffView;

@Mapper
public interface HandoffMapper {
	List<HandoffView> selectAll();
}