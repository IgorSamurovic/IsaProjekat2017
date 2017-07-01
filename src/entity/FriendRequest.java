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
@Table(name="friendrequest")
public class FriendRequest implements Serializable {

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

	@Column(name="requester", unique=false, nullable=false)
	private long requester;
	
	@Column(name="requested", unique=false, nullable=false)
	private long requested;
	
	public FriendRequest() {}

	public long getRequester() {
		return requester;
	}

	public void setRequester(long requester) {
		this.requester = requester;
	}

	public long getRequested() {
		return requested;
	}

	public void setRequested(long requested) {
		this.requested = requested;
	}

	private static final long serialVersionUID = -5303448656679186126L;
}
