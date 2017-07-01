package entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static javax.persistence.GenerationType.IDENTITY;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="reservation")
public class Reservation implements Serializable {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	
	@JsonInclude
	@Transient
	private ArrayList<Long> tables;

	@JsonInclude
	@Transient
	private ArrayList<Long> invites;
	
	@Column(name="restaurant", unique=false, nullable=false)
	private long restaurant;
	
	@Column(name="user", unique=false, nullable=false)
	private Integer user;
	
	@Column(name="timeFrom", unique=false, nullable=true)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp timeFrom;
	
	@Column(name="timeTo", unique=false, nullable=true)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private Timestamp timeTo;
	
	@Override
	public String toString() {
		return "Reservation [id=" + id + ", restaurant=" + restaurant + ", user=" + user + ", timeFrom=" + timeFrom + ", timeTo=" + timeTo + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(long restaurant) {
		this.restaurant = restaurant;
	}

	public Integer getUser() {
		return user;
	}

	public void setUser(Integer user) {
		this.user = user;
	}

	public Timestamp getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(Timestamp timeFrom) {
		this.timeFrom = timeFrom;
	}

	public Timestamp getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(Timestamp timeTo) {
		this.timeTo = timeTo;
	}

	public ArrayList<Long> getTables() {
		return tables;
	}

	public void setTables(ArrayList<Long> tables) {
		this.tables = tables;
	}
	
	public ArrayList<Long> getInvites() {
		return invites;
	}

	public void setInvites(ArrayList<Long> invites) {
		this.invites = invites;
	}
	
	public Reservation() {}

	private static final long serialVersionUID = -5303433656679186126L;
}
