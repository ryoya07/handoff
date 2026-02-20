package com.example.handoff.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.handoff.domain.model.User;
import com.example.handoff.repository.UserMapper;

	// Spring SecurityのUserDetailsServiceを実装するクラス。ユーザのログインIDをもとに、UserMapperを使ってユーザ情報をデータベースから取得し、Spring Securityが認証処理に使用できる形式で返す。
	@Service
	public class LoginUserDetailsService implements UserDetailsService {
	    private final UserMapper userMapper;
	    public LoginUserDetailsService(UserMapper userMapper) {
	        this.userMapper = userMapper;
	    }
	    
	    // loadUserByUsernameメソッドは、ユーザのログインIDを引数に取り、UserMapperを使ってデータベースからユーザ情報を取得する。ユーザが見つからない場合はUsernameNotFoundExceptionをスローする。ユーザが見つかった場合は、Spring Securityが認証処理に使用できる形式でUserDetailsオブジェクトを返す。
	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userMapper.selectByLoginId(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("User not found: " + username);
        }
        
	    // Spring SecurityのUserDetailsオブジェクトを作成して返す。ユーザのログインIDをusername、パスワードハッシュをpassword、ユーザの役割をrolesとして設定する。
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getPasswordHash())
                .roles(user.getRole())
                .build();
    }
}
