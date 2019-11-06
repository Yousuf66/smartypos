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
public class CouponRanges {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "FkDiscountCoupon", nullable=false)
	private DiscountCoupon coupon;
	
	private Double startRange=0.0;
	private Double endRange=0.0;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DiscountCoupon getCoupon() {
		return coupon;
	}
	public void setCoupon(DiscountCoupon coupon) {
		this.coupon = coupon;
	}
	public Double getStartRange() {
		return startRange;
	}
	public void setStartRange(Double startRange) {
		this.startRange = startRange;
	}
	public Double getEndRange() {
		return endRange;
	}
	public void setEndRange(Double endRange) {
		this.endRange = endRange;
	} 

	
}
