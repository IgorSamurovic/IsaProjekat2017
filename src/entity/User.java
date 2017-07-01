package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static javax.persistence.GenerationType.IDENTITY;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="user")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	@Column(name="username", unique=false, nullable=false)
	private String username;

	@Column(name="password", unique=false, nullable=false)
	private String password;

	@Column(name="firstName", unique=false, nullable=false)
	private String firstName;

	@Column(name="lastName", unique=false, nullable=false)
	private String lastName;

	@Column(name="email", unique=false, nullable=false)
	private String email;

	@Column(name="active", unique=false, nullable=true)
	private boolean active;

	@Column(name="activationString", unique=false, nullable=true)
	private String activationString;

	public User() {}

	public static User getLoggedUser(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null) {
			return null;
		} else {
			User u = (User) session.getAttribute("user");
			if (u == null) {
				return null;
			} else {
				return u;
			}
		} 
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", email=" + email + ", active=" + active + ", activationString="
				+ activationString + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getActivationString() {
		return activationString;
	}

	public void setActivationString(String activationString) {
		this.activationString = activationString;
	}
	
	private static final long serialVersionUID = -5303448656679186126L;
}
