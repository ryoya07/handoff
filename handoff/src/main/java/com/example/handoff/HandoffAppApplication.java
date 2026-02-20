package com.example.handoff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// HandoffAppApplicationは、Spring Bootアプリケーションのエントリーポイントとなるクラス。@SpringBootApplicationアノテーションを付与することで、Spring Bootの自動構成やコンポーネントスキャンなどの機能が有効になる。
@SpringBootApplication
public class HandoffAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HandoffAppApplication.class, args);
	}

}
