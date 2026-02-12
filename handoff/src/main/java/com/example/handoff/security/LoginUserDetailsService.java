package com.example.handoff.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.handoff.domain.model.User;
import com.example.handoff.repository.UserMapper;

	@Service
	public class LoginUserDetailsService implements UserDetailsService {
	    private final UserMapper userMapper;
	    public LoginUserDetailsService(UserMapper userMapper) {
	        this.userMapper = userMapper;
	    }
	    
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userMapper.selectByLoginId(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getPasswordHash())
                .roles(user.getRole())
                .build();
    }
}
