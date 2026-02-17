package com.example.handoff.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/handoff")
	public String list(Model model) {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		Long userId = userMapper.selectIdByLoginId(loginId);
		model.addAttribute("handoffs", handoffMapper.selectAll(userId));
		return "handoff/list";
	}
	
	@GetMapping("/handoff/new")
	public String newForm() {
	    return "handoff/new";
	}
	
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
	
	@PostMapping("/handoff/{id}/read")
	public String markRead(@PathVariable Long id) {
		String loinId = SecurityContextHolder.getContext().getAuthentication().getName();
		Long userId = userMapper.selectIdByLoginId(loinId);
		
		handoffReadMapper.upsertRead(id, userId);
		
		return "redirect:/handoff";
	}
	
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
	    return "redirect:/handoff";
	}

	@PostMapping("/handoff/{id}/delete")
	public String delete(@PathVariable Long id, Authentication auth) {
	    Long userId = userMapper.selectIdByLoginId(auth.getName());

	    int deleted = handoffMapper.deleteByIdAndUserId(id, userId);
	    if (deleted == 0) {
	        return "redirect:/handoff?forbidden";
	    }
	    return "redirect:/handoff";
	}

}
