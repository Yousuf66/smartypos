package ksa.so.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class OrderCombo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long price = (long)0;
	
	private int quantity = 0;
	
	@ManyToOne
    @JoinColumn(name = "FkOrder", nullable=false)
	private CustomerOrder order;
	
	@ManyToOne
    @JoinColumn(name = "FkCombo", nullable=false)
	private Combo combo;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getPrice() {
		return price;
	}


	public void setPrice(Long price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public CustomerOrder getOrder() {
		return order;
	}


	public void setOrder(CustomerOrder order) {
		this.order = order;
	}


	public Combo getCombo() {
		return combo;
	}


	public void setCombo(Combo combo) {
		this.combo = combo;
	}
	
	public OrderCombo() {
		
	}
	
	public OrderCombo(long price, int quantity, Combo combo, CustomerOrder order) {
		this.setCombo(combo);
		this.setOrder(order);
		this.setPrice(price);
		this.setQuantity(quantity);
	}
}
