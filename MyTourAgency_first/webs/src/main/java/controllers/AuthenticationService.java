package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import agency.User;
import service.IUserService;
import user.AuthUser;

/**
 * Custom Authentication Service
 *
 */ 
@Service("authService")
public class  AuthenticationService implements UserDetailsService {
	final Logger log = Logger.getLogger( AuthenticationService.class);

	@Autowired
	private IUserService userService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("== DEB");
		int id = userService.authenticate(username);
		User user = userService.getUserById(id);

		System.out.println("User : " + user);
		if (user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ROLE_" + Integer.toString(user.getRole_id())));
		

		UserDetails ud = new AuthUser(user.getUserName(), user.getPassword(),true, true, true, true, roles, user.getFirstName(),
				user.getLastName(), user.getId_user());
		return ud;
	}
	/* @Autowired
	    private IUserService userService;

	    @Transactional(readOnly = true)
	    public UserDetails loadUserByUsername(String username)
	            throws UsernameNotFoundException {
	    	int id = userService.authenticate(username);
			User user = userService.getUserById(id);
	        System.out.println("User : " + user);
	        
	        boolean enabled = true;
	        boolean accountNonExpired = true;
	        boolean credentialsNonExpired = true;
	        boolean accountNonLocked = true;
	 
	        return new org.springframework.security.core.userdetails.User(
	        		user.getUserName(), 
	        		user.getPassword(), 
	                enabled, 
	                accountNonExpired, 
	                credentialsNonExpired, 
	                accountNonLocked,
	                getGrantedAuthorities(user)
	        );
	        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
	                true, true, true, true, getGrantedAuthorities(user));
	    }


	    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
	    	
	        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
	        return authList;
	    }
	    private Set<GrantedAuthority> getGrantedAuthorities(User user) {
	        //List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole_id()));
	        
	        System.out.print("authorities :" + authorities);
	        return authorities;
	    }
*/
	
}
