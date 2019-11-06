package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class BranchDeliveryChargesRange {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonProperty
	private Double startRange = 0.0;

	@JsonProperty
	private Double endRange = 0.0;

	@JsonProperty
	private Double deliveryCharges = 0.0;

	@ManyToOne
	@JoinColumn(name = "FkBranch", nullable = false)
	private Branch branch;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Double getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(Double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

}
