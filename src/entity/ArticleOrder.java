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
@Table(name="articleorder")
public class ArticleOrder implements Serializable {

	@Override
	public String toString() {
		return "ArticleOrder [id=" + id + ", article=" + article + ", ordr=" + ordr + ", amount=" + amount + "]";
	}

	@Id
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Integer id;
	
	@Column(name="article", unique=false, nullable=false)
	private Integer article;
	
	@Column(name="ordr", unique=false, nullable=false)
	private Integer ordr;
	
	@Column(name="amount", unique=false, nullable=false)
	private Integer amount;
	
	public ArticleOrder() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticle() {
		return article;
	}

	public void setArticle(Integer article) {
		this.article = article;
	}

	public Integer getOrder() {
		return ordr;
	}

	public void setOrder(Integer order) {
		this.ordr = order;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	private static final long serialVersionUID = -5303448656679186123L;
}
