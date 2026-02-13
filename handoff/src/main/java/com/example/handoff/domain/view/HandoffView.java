package com.example.handoff.domain.view;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HandoffView {
	private Long id;
	private String title;
	private String content;
	private String createdByName;
	private LocalDateTime createdAt;
    private int readFlag;              // 0:未読 / 1:既読
    private java.time.LocalDateTime readAt;      // 既読日時（未読ならnull）
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreatedByName() {
		return createdByName;
	}
	
	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public int getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(int readFlag) {
		this.readFlag = readFlag;
	}

	public LocalDateTime getReadAt() {
		return readAt;
	}

	public void setReadAt(LocalDateTime readAt) {
		this.readAt = readAt;
	}
	

}
