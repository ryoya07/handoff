package com.example.handoff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.handoff.repository.HandoffMapper;

@Controller
public class HandoffController {
	
	private final HandoffMapper handoffMapper;
	
	public HandoffController(HandoffMapper handoffMapper) {
		this.handoffMapper = handoffMapper;
	}
	
	@GetMapping("/handoff")
	public String list(Model model) {
		model.addAttribute("handoffs", handoffMapper.selectAll());
		return "handoff/list";
	}
}
