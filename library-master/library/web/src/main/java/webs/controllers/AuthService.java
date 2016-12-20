package webs.controllers;

import java.util.HashSet;
import java.util.Set;


import org.apache.log4j.Logger;
import by.library.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import by.library.entities.User;
import by.library.services.UserServices;
import webs.user.AuthUser;

/**
 * Custom Authentication Service 
 *
 */
@Service("authService")
public class AuthService implements UserDetailsService{
	final Logger log = Logger.getLogger(AuthService.class);

	@Autowired
	private UserServices userServices;
	
	public AuthService() {
		super();
	}
	
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = null;
		
		try {
			user = userServices.getUserByEmail(email);
		} catch (ServiceException e) {
			log.error(e);
		}
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found. Your e-mail is incorrect or this user is not exist.<br>"
					+ "Пользователь не найден. Ваш e-mail некорректен или пользователя с такими данными не существует.");
		}
		
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
		
		boolean lock = (user.getBlack()== 0);
		
		UserDetails ud = new AuthUser(user.getEmail(), user.getPassword(), true , true, true, lock, roles, user.getName(),
				user.getSecondName(), user.getUser_id());
		
		return ud;
	}
}
