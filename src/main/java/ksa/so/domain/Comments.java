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
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.MetaStatusLanguageRepository;

@Entity
public class Comments {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String commentText = "-";
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public MetaRating getRating() {
		return rating;
	}

	public void setRating(MetaRating rating) {
		this.rating = rating;
	}

	public Timestamp getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Timestamp commentTime) {
		this.commentTime = commentTime;
	}

	@ManyToOne
    @JoinColumn(name = "FKUser", nullable=false)
	private User user;
	
	@ManyToOne
    @JoinColumn(name = "FKItem", nullable=false)
	private Item item;

	@ManyToOne
    @JoinColumn(name = "FKRating", nullable=false)
	private MetaRating rating;
	
	private Timestamp commentTime;
	
	public JSONObject getInfo(){
		
		JSONObject json = new JSONObject();
		
		
		return json;
	}

}
