package ksa.so.domain;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;
import ksa.so.repositories.ShoppingListItemRepository;

@Entity
public class WalletOrder {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	@ManyToOne
    @JoinColumn(name = "FkWallet", nullable=false)
	private Wallet wallet;

	@ManyToOne
    @JoinColumn(name = "FkCustomerOrder", nullable=false)
	private CustomerOrder order;
	
	Double amountPayable=0.0;
	Double amountRecieved=0.0;
    Double difference= 0.0;
	

	
	
	
public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public Wallet getWallet() {
		return wallet;
	}





	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}





	public CustomerOrder getOrder() {
		return order;
	}





	public void setOrder(CustomerOrder order) {
		this.order = order;
	}





	public Double getAmountPayable() {
		return amountPayable;
	}





	public void setAmountPayable(Double amountPayable) {
		this.amountPayable = amountPayable;
	}





	public Double getAmountRecieved() {
		return amountRecieved;
	}





	public void setAmountRecieved(Double amountRecieved) {
		this.amountRecieved = amountRecieved;
	}





	public Double getDifference() {
		return difference;
	}





	public void setDifference(Double difference) {
		this.difference = difference;
	}





public JSONObject getInfo() {
		
		JSONObject json = new JSONObject();
		
		
	
		return json;
	}
}