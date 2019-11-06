package ksa.so.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.json.JSONObject;

@Entity
public class UserCard {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String cardNumber = "-";
	private String cardHolderName = "-";
	private String expiryDate = "-";
	private String cvv = "-";

	@OneToOne
	@JoinColumn(name="FkUser", nullable=false)
	private UserApp user;
	
	public UserApp getUserApp() {
		return user;
	}

	public void setUserApp(UserApp user) {
		this.user = user;
	}

	
	@OneToOne
	@JoinColumn(name="FkCard", nullable=false)
	private MetaCard card;
	
	public MetaCard getCard() {
		return card;
	}

	public void setCard(MetaCard card) {
		this.card = card;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public JSONObject getInfo(){
		
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("cardNumber", this.getCardNumber());
		json.put("cardHolderName", this.getCardHolderName());
		json.put("expiryDate", this.getExpiryDate());
		json.put("cvv", this.getCvv());
		json.put("cardType", this.getCard().getInfo());
		
		return json;
	}

}

