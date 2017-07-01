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
@Table(name="article")
public class Article implements Serializable {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="name", unique=false, nullable=false)
	private String name;
	
	@Column(name="stock", unique=false, nullable=false)
	private Integer stock;
	
	@Column(name="price", unique=false, nullable=false)
	private Integer price;
	
	@Column(name="restaurant", unique=false, nullable=false)
	private Integer restaurant;
	
	@Column(name="type", unique=false, nullable=false)
	private Integer type;
	
	public Article() {}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getStock() {
		return stock;
	}


	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public Integer getRestaurant() {
		return restaurant;
	}


	public void setRestaurant(Integer restaurant) {
		this.restaurant = restaurant;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	private static final long serialVersionUID = -5303448656679186123L;
}
