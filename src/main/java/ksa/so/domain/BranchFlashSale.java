package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

@Entity
public class BranchFlashSale {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "FkBranch", nullable = false)
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "FkFlashSale", nullable = false)
	private FlashSale flashSale;

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Long getId() {
		return id;
	}

	public FlashSale getFlashSale() {
		return flashSale;
	}

	public void setFlashSale(FlashSale flashSale) {
		this.flashSale = flashSale;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JSONObject getInfo() {
		JSONObject json = new JSONObject();

		return json;
	}

}