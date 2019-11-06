package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

@Entity
public class BranchPayment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "FkBranch", nullable=false)
	private Branch branch;
	
	@ManyToOne
    @JoinColumn(name = "FkPayment", nullable=false)
	private MetaPayment payment;
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Branch getBranch() {
		return branch;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public MetaPayment getPayment() {
		return payment;
	}

	public void setPayment(MetaPayment payment) {
		this.payment = payment;
	}

	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("code",this.getPayment());
		
		
		return json;
	}
	
}
