SET NAMES utf8mb4;
USE handoff_app;

INSERT INTO handoffs (title, content, created_by)
VALUES ("テスト", 'test', 4);

SELECT title, content FROM handoffs;