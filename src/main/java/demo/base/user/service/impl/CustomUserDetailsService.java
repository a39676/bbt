package demo.base.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import demo.base.user.pojo.bo.MyUserPrincipal;
import demo.base.user.service.UsersService;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UsersService usersService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUserPrincipal userDetail = usersService.buildMyUserPrincipalByUserName(username);
		if(userDetail == null || userDetail.getUser() == null) {
			throw new UsernameNotFoundException(username);
		}
		return userDetail;
	}

}
