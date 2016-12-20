package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import user.AuthUser;

/**
 * Custom Authentication Provider
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private AuthenticationService authService;

	public CustomAuthenticationProvider() {
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        AuthUser user = null;
        
        try{
        	user = (AuthUser) authService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e){
        	throw e;
        }
        
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password.<br> Неправильный пароль");
        }
        
        if(user.isAccountNonLocked() == false){
			throw new LockedException ("User Account is locked!<br>Аккаунт заблокирован!", new Exception());
		}
		
        java.util.Collection<GrantedAuthority> authorities = user.getAuthorities();
        
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
