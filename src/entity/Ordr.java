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
@Table(name="ordr")
public class Ordr implements Serializable {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="ready", unique=false, nullable=false)
	private Boolean ready;
	
	@Column(name="user", unique=false, nullable=false)
	private Integer user;
	
	@Column(name="reservation", unique=false, nullable=false)
	private Integer reservation;
	
	@Column(name="cancelled", unique=false, nullable=false)
	private Boolean cancelled;

	public Ordr() {}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Boolean getReady() {
		return ready;
	}


	public void setReady(Boolean ready) {
		this.ready = ready;
	}


	public Integer getUser() {
		return user;
	}


	public void setUser(Integer user) {
		this.user = user;
	}


	public Integer getReservation() {
		return reservation;
	}


	public void setReservation(Integer reservation) {
		this.reservation = reservation;
	}


	public Boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	private static final long serialVersionUID = -5303448656679186123L;
}
