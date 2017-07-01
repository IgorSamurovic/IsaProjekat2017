package entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static javax.persistence.GenerationType.IDENTITY;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="visit")
public class Visit implements Serializable {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="coming", unique=false, nullable=false)
	private Boolean coming;
	
	@Column(name="user", unique=false, nullable=false)
	private Integer user;
	
	@Column(name="reservation", unique=false, nullable=false)
	private Integer reservation;
	
	@JsonInclude
	@Transient
	private boolean canCancel;

	@JsonInclude
	@Transient
	private String restaurantName;
	
	@JsonInclude
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp dateFrom;
	
	@JsonInclude
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp dateTo;
	
	public Visit() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getComing() {
		return coming;
	}

	public void setComing(Boolean coming) {
		this.coming = coming;
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
	
	public boolean isCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Timestamp getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Timestamp dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Timestamp getDateTo() {
		return dateTo;
	}

	public void setDateTo(Timestamp dateTo) {
		this.dateTo = dateTo;
	}

	private static final long serialVersionUID = -5303448656679186123L;
}
