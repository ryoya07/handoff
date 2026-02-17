package com.example.handoff.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordChangeForm {
	
	@NotBlank
	private String currentPassword;
	
	@NotBlank
	@Size(min = 8,max = 72)
	private String newPassword;
	
	@NotBlank
	private String newPasswordConfirm;
	
	//getter/setter
	public String getCurrentPassword() {
		return currentPassword;
	}
	
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getNewPasswordConfirm() {
		return newPasswordConfirm;
	}
	
	public void setNewPasswordConfirm(String newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}
}
