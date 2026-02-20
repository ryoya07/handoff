package com.example.handoff.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AccountProfileForm {

	// 表示名は必須で、最大100文字までとする。
    @NotBlank
    @Size(max = 100)
    private String displayName;

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}
