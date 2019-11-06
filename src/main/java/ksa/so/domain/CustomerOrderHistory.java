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

@Entity
public class CustomerOrderHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
		
	@Column(columnDefinition = "text")
	private String review = "-";
	
	private Long lon = (long) 0;
	
	private Long lat = (long) 0;
	
	@Column(nullable=true)
	private boolean isReviewed = false;
	
	@Column(nullable=true)
	private boolean customerArrived = false;
	
	@Column(nullable=true)
	private boolean isTakeaway = false;
	
	@ManyToOne
    @JoinColumn(name = "FkRating", nullable=false)
	private MetaRating rating;
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
		
	@ManyToOne
    @JoinColumn(name = "FkCustomerOrder", nullable=false)
	private CustomerOrder order;
	
	private Timestamp tsServer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Long getLon() {
		return lon;
	}

	public void setLon(Long lon) {
		this.lon = lon;
	}

	public Long getLat() {
		return lat;
	}

	public void setLat(Long lat) {
		this.lat = lat;
	}

	public MetaRating getRating() {
		return rating;
	}

	public void setRating(MetaRating rating) {
		this.rating = rating;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public boolean isReviewed() {
		return isReviewed;
	}

	public void setReviewed(boolean isReviewed) {
		this.isReviewed = isReviewed;
	}

	public boolean hasCustomerArrived() {
		return customerArrived;
	}

	public void setCustomerArrived(boolean customerArrived) {
		this.customerArrived = customerArrived;
	}
	
	public boolean getIsTakeaway() {
		return isTakeaway;
	}

	public void setIsTakeaway(boolean isTakeaway) {
		this.isTakeaway = isTakeaway;
	}

	public CustomerOrder getOrder() {
		return order;
	}

	public void setOrder(CustomerOrder order) {
		this.order = order;
	}

	public Timestamp getTsServer() {
		return tsServer;
	}

	public void setTsServer(Timestamp tsServer) {
		this.tsServer = tsServer;
	}

	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
				
		return json;
	}
	
	public CustomerOrderHistory() {
		
	}
	
	public CustomerOrderHistory(Timestamp tsServer, 
			CustomerOrder order, 
			MetaRating rating, 
			MetaStatus status,
			String review,
			boolean isReviewed,
			boolean hasCustomerArrived,
			long lat, 
			long lon,
			boolean isTakeaway){
		this.setOrder(order);
		this.setStatus(status);
		this.setRating(rating);
		this.setLat(lat);
		this.setLon(lon);
		this.setReview(review);
		this.setTsServer(tsServer);
		this.setReviewed(isReviewed);
		this.setCustomerArrived(hasCustomerArrived);
		this.setIsTakeaway(isTakeaway);
	}
}
