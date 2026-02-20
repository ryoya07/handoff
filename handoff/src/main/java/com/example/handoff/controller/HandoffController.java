package com.example.handoff.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.handoff.repository.HandoffMapper;
import com.example.handoff.repository.HandoffReadMapper;
import com.example.handoff.repository.UserMapper;

@Controller
public class HandoffController {
	
	private final HandoffMapper handoffMapper;
	private final UserMapper userMapper;
	private final HandoffReadMapper handoffReadMapper;
	
	public HandoffController(
			HandoffMapper handoffMapper,
			UserMapper userMapper, 
			HandoffReadMapper handoffReadMapper
		) {
		this.handoffMapper = handoffMapper;
		this.userMapper = userMapper;
		this.handoffReadMapper = handoffReadMapper;
	}
	
	// ハンドオフの一覧画面。ログインユーザのIDをもとに、ユーザが作成した投稿と他人が作成した投稿の両方を取得して表示する。
	@GetMapping("/handoff")
	public String list(Model model) {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		Long userId = userMapper.selectIdByLoginId(loginId);
		model.addAttribute("loginUserId", userId);
		model.addAttribute("handoffs", handoffMapper.selectAll(userId));
		return "handoff/list";
	}
	
	// 投稿作成は全ユーザがアクセス可能。
	@GetMapping("/handoff/new")
	public String newForm() {
	    return "handoff/new";
	}
	
	// 投稿作成は全ユーザがアクセス可能。投稿者のIDはセッションから取得して保存する。
	@PostMapping("/handoff")
	public String create(
			@RequestParam String title,
			@RequestParam String content
	) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loginId = auth.getName();
		
		Long userId = userMapper.selectIdByLoginId(loginId);
		
		handoffMapper.insert(title, content, userId);
		
		return "redirect:/handoff";
	}
	
	// 投稿を既読にする。既読はユーザごとに管理されるため、ログインユーザのIDも渡す必要がある。
	@PostMapping("/handoff/{id}/read")
	public String markRead(@PathVariable Long id) {
		String loinId = SecurityContextHolder.getContext().getAuthentication().getName();
		Long userId = userMapper.selectIdByLoginId(loinId);
		
		handoffReadMapper.upsertRead(id, userId);
		
		return "redirect:/handoff";
	}
	
	// 投稿編集は投稿者のみがアクセス可能。サーバ側でも投稿者チェックを行う。
	@GetMapping("/handoff/{id}/edit")
	public String editForm(@PathVariable Long id, Model model, Authentication auth) {
	    Long userId = userMapper.selectIdByLoginId(auth.getName());

	    var handoff = handoffMapper.selectById(id);

	    // サーバ側でも投稿者チェック（必須）
	    if (handoff == null || !handoff.getCreatedBy().equals(userId)) {
	        return "redirect:/handoff?forbidden";
	    }

	    model.addAttribute("handoff", handoff);
	    return "handoff/edit";
	}

	@PostMapping("/handoff/{id}/edit")
	public String update(
	    @PathVariable Long id,
	    @RequestParam String title,
	    @RequestParam String content,
	    Authentication auth
	) {
	    Long userId = userMapper.selectIdByLoginId(auth.getName());

	    int updated = handoffMapper.updateByIdAndUserId(id, title, content, userId);
	    if (updated == 0) {
	        return "redirect:/handoff?forbidden";
	    }
	    
	    handoffReadMapper.deleteByHandoffId(id);
	    
	    return "redirect:/handoff";
	}

	// 投稿削除も投稿者のみがアクセス可能。サーバ側でも投稿者チェックを行う。
	@PostMapping("/handoff/{id}/delete")
	public String delete(@PathVariable Long id, Authentication auth) {
	    Long userId = userMapper.selectIdByLoginId(auth.getName());

	    int deleted = handoffMapper.deleteByIdAndUserId(id, userId);
	    if (deleted == 0) {
	        return "redirect:/handoff?forbidden";
	    }
	    return "redirect:/handoff";
	}
	
	// 投稿の詳細画面。投稿者以外もアクセス可能。既読フラグはユーザごとに管理されるため、ログインユーザのIDも渡す必要がある。
	@GetMapping("/handoff/{id}")
	public String show(@PathVariable Long id, Model model, Authentication auth) {
	    Long userId = userMapper.selectIdByLoginId(auth.getName());

	    var handoff = handoffMapper.selectByIdWithReadFlag(id, userId);
	    if (handoff == null) {
	        return "redirect:/handoff";
	    }

	    model.addAttribute("handoff", handoff);
	    model.addAttribute("loginUserId", userId);
	    return "handoff/show";
	}
	
	// すべてのハンドオフ関連の画面でログインユーザの表示名を利用できるようにするため、@ModelAttributeで共通処理として追加する。
	@ModelAttribute
	public void addLoginUserName(Model model, Authentication auth) {
	    if (auth != null && auth.isAuthenticated()) {
	        String loginId = auth.getName();
	        var user = userMapper.selectByLoginId(loginId);
	        if (user != null) {
	            model.addAttribute("loginUserName", user.getDisplayName());
	            model.addAttribute("loginUserId", user.getId());
	        }
	    }
	}

}
