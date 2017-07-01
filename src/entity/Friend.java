package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static javax.persistence.GenerationType.IDENTITY;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="friend")
public class Friend implements Serializable {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="user1", unique=false, nullable=false)
	private long user1;
	
	@Column(name="user2", unique=false, nullable=false)
	private long user2;
	
	public Friend() {}

	public long getUser1() {
		return user1;
	}

	public void setUser1(long user1) {
		this.user1 = user1;
	}

	public long getUser2() {
		return user2;
	}

	public void setUser2(long user2) {
		this.user2 = user2;
	}

	private static final long serialVersionUID = -5303448656679186126L;
}
