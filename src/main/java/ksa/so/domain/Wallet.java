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
public class Wallet {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	
	@ManyToOne
    @JoinColumn(name = "FkUser", nullable=false)
	private UserApp user;

	Double walletAmount=0.0;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

	public Double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}
	
	
public JSONObject getInfo() {
		
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("User", this.getUser());
		json.put("walletAmount", this.getWalletAmount());
	
		return json;
	}
}