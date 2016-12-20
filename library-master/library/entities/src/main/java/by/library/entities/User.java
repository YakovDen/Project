package by.library.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/** A class describes the user's identity. 
 * It has properties - name, email, password, role ID (u - user, a - administrator), 
 * the identifier of black list (0 - blacklisted, 1 - not blacklisted), 
 * a general list of books (ordered and already in use) */

@Entity
@Table(name="T_USER", catalog="PerfectLibrary")
@GenericGenerator(name="increment", strategy = "increment")
/*@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class User implements Serializable{
	private static final long serialVersionUID = 2L;
	
	@Id
	@GeneratedValue(generator="increment")
	private int user_id;
	
	private String name;
	private String secondName;
	
	@Size(min = 2, max = 20, 
			message="E-mail must be between 2 and 20 characters long.")
	private String email;
	
	@Size(min = 2, max = 20, 
			message="Password must be between 2 and 20 characters long.")
	private String password;
	
	/** users role: u - reader, a - administrator */
	@Column(nullable = false)
	private char role;
	
	/** user on the black list: 0 - no, 1 - yes */
	@Column(nullable = false)
	private int black;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY /* не подгружать коллекцию при каждом запросе*/, cascade = CascadeType.ALL)
	private Set<Order> books;
	
	public User(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int id) {
		this.user_id = id;
	}

	public char getRole() {
		return role;
	}

	public void setRole(char role) {
		this.role = role;
	}

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public Set<Order> getBooks() {
		return books;
	}

	public void setBooks(Set<Order> books) {
		this.books = books;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + black;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + user_id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + role;
		result = prime * result + ((secondName == null) ? 0 : secondName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (black != other.black) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (user_id != other.user_id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (role != other.role) {
			return false;
		}
		if (secondName == null) {
			if (other.secondName != null) {
				return false;
			}
		} else if (!secondName.equals(other.secondName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", secondName=" + secondName + ", email=" + email +
				", role=" + role + ", black=" + black + "]";
	}

	
}
