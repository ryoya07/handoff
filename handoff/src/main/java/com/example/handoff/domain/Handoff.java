package com.example.handoff.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Handoff {
    private Long id;
    private String title;
    private String content;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
