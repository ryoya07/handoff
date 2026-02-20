package com.example.handoff.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.handoff.repository.UserMapper;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AccountController(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // アカウント編集画面はログインユーザのみアクセス可能。ユーザの表示名を変更できるようにする。
    @GetMapping("/edit")
    public String editForm(Model model, Authentication auth) {

        Long userId = userMapper.selectIdByLoginId(auth.getName());
        var user = userMapper.selectById(userId);

        model.addAttribute("user", user);
        return "account/edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@RequestParam String displayName, Authentication auth) {

        Long userId = userMapper.selectIdByLoginId(auth.getName());
        userMapper.updateProfile(userId, displayName);

        return "redirect:/account/edit?success";
    }
    
    // パスワード変更はログインユーザのみアクセス可能。新しいパスワードと確認用のパスワードを入力させ、両方が一致することを確認してから保存する。パスワードはハッシュ化して保存する。
    @PostMapping("/password")
    public String updatePassword(
            @RequestParam String newPassword,
            @RequestParam String newPasswordConfirm,
            Authentication auth
    ) {
    	// パスワードと確認用パスワードが一致することを確認する。必要に応じて、パスワードの強度チェックも追加できる。
        if (!newPassword.equals(newPasswordConfirm)) {
            return "redirect:/account/edit?pw_mismatch";
        }

        // 最低限のチェックとして、パスワードの長さが8文字以上であることを確認する。
        if (newPassword.length() < 8) {
            return "redirect:/account/edit?pw_short";
        }

        Long userId = userMapper.selectIdByLoginId(auth.getName());
        String hash = passwordEncoder.encode(newPassword);
        userMapper.updatePasswordHash(userId, hash);

        return "redirect:/account/edit?pw_success";
    }
    
    // 退会処理はログインユーザのみアクセス可能。ユーザのアカウントを論理削除（ソフトデリート）する。退会後はセッションを無効化してログイン画面にリダイレクトする。
    @PostMapping("/withdraw")
    public String withdraw(Authentication auth) {

        Long userId = userMapper.selectIdByLoginId(auth.getName());
        userMapper.softDelete(userId);

        SecurityContextHolder.clearContext();
        return "redirect:/login?deleted";
    }
}