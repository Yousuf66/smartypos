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
public class OrderItemSubOpt {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "FkOrderItem", nullable=false)
	private OrderItem orderItem;
	
	@ManyToOne
    @JoinColumn(name = "FkSubOpt", nullable=false)
	private SubOpt subOpt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public SubOpt getSubOpt() {
		return subOpt;
	}

	public void setSubOpt(SubOpt subOpt) {
		this.subOpt = subOpt;
	}
	
	public OrderItemSubOpt() {
		
	}
	
	public OrderItemSubOpt(OrderItem orderItem, SubOpt subOpt) {
		this.setOrderItem(orderItem);
		this.setSubOpt(subOpt);
	}
}
