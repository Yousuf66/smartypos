package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

@Entity
public class BundlesItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long quantity;
	
	public BundlesItem() {
		super();
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	@ManyToOne
    @JoinColumn(name = "FKItem", nullable=false)
	private Item item;
	
	@ManyToOne
    @JoinColumn(name = "FKBranch", nullable=false)
	private Branch branch;
	
	@ManyToOne
    @JoinColumn(name = "FKBundle", nullable=false)
	private Item bundleItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Item getBundleItem() {
		return bundleItem;
	}

	public void setBundleItem(Item bundleItem) {
		this.bundleItem = bundleItem;
	}

	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("Item",this.getItem());
		json.put("BundleItem",this.getBundleItem());
		json.put("Branch",this.getBranch());
		return json;
	}
	public BundlesItem(Item item,Branch branch,Item bundleItem,Long quantity){
		this.setItem(item);
		this.setBranch(branch);
		this.setBundleItem(bundleItem);
		this.setQuantity(quantity);
	}
}
