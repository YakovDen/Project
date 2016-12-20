package user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Custom Authentication User
 *
 */
public class AuthUser extends User {

	private static final long serialVersionUID = 111L;
	
	private String realName;
	private String realSecondName;
	private int id;

	public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities, 
			String realName, String realSecondName, int id) {
		super(username, password, authorities);
		this.realName = realName;
		this.realSecondName = realSecondName;
		this.id = id;
	}

	public AuthUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, String realName, String realSecondName, int id) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.realName = realName;
		this.realSecondName = realSecondName;
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealSecondName() {
		return realSecondName;
	}

	public void setRealSecondName(String realSecondName) {
		this.realSecondName = realSecondName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + id;
		result = prime * result + ((realName == null) ? 0 : realName.hashCode());
		result = prime * result + ((realSecondName == null) ? 0 : realSecondName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AuthUser)) {
			return false;
		}
		AuthUser other = (AuthUser) obj;
		if (id != other.id) {
			return false;
		}
		if (realName == null) {
			if (other.realName != null) {
				return false;
			}
		} else if (!realName.equals(other.realName)) {
			return false;
		}
		if (realSecondName == null) {
			if (other.realSecondName != null) {
				return false;
			}
		} else if (!realSecondName.equals(other.realSecondName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "AuthUser [realName=" + realName + ", realSecondName=" + realSecondName + ", id=" + id + "]";
	}

}