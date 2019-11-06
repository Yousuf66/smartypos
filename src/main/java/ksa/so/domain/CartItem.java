package ksa.so.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.json.JSONObject;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.CategoryLanguageRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;

@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "price", columnDefinition = "Decimal(10,2) default '0.00'")
	private double price = 0;

	private int quantity = 0;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date timestamp;

	@ManyToOne
	@JoinColumn(name = "FkItem", nullable = false)
	private Item item;

	@ManyToOne
	@JoinColumn(name = "FkUser", nullable = false)
	private UserApp user;

	@ManyToOne
	@JoinColumn(name = "FkBranch", nullable = false)
	private Branch branch;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public UserApp getUserApp() {
		return user;
	}

	public void setUserApp(UserApp user) {
		this.user = user;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Date getTimeStamp() {
		return timestamp;
	}

	public void setTimeStamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public CartItem() {

	}

	public CartItem(UserApp user, double price, int quantity, Item item, Date timestamp, Branch branch) {
		this.setUserApp(user);
		this.setItem(item);
		this.setBranch(branch);
		this.setPrice(price);
		this.setQuantity(quantity);
		this.setTimeStamp(timestamp);
	}

	public JSONObject getInfo(MetaLanguage language, ItemLanguageRepository itemLanguageRepository,
			CategoryLanguageRepository categoryLanguageRepository, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository, ItemRepository itemRepository) {

		JSONObject json = new JSONObject();

		// json.put("category", this.getItem().getCategory().getInfo(language,
		// categoryLanguageRepository));
		json.put("currency", this.getBranch().getCurrency().getInfo());
		json.put("price", this.getPrice());
		json.put("quantity", this.getQuantity());

		return json;
	}
}
