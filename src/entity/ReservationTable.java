package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import static javax.persistence.GenerationType.IDENTITY;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="reservationtable")
public class ReservationTable implements Serializable {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;

	@Column(name="reservation", unique=false, nullable=false)
	private long reservation;
	
	@Column(name="tabla", unique=false, nullable=false)
	private long tabla;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public long getReservation() {
		return reservation;
	}

	public void setReservation(long reservation) {
		this.reservation = reservation;
	}

	public long getTabla() {
		return tabla;
	}

	public void setTabla(long tabla) {
		this.tabla = tabla;
	}

	public ReservationTable() {}

	private static final long serialVersionUID = -5303433656679186126L;
}
