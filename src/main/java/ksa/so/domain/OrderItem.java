
package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.FlashSaleItemRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.LibraryItemLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;

@Entity
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "price", columnDefinition = "Decimal(10,2) default '0.00'")
	private double price = 0;

	@Column(name = "cost", columnDefinition = "Decimal(10,2) default '0.00'")
	private double cost = 0;

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	private int quantity = 0;

	@ManyToOne
	@JoinColumn(name = "FkOrder", nullable = false)
	private CustomerOrder order;

	@ManyToOne
	@JoinColumn(name = "FkItem", nullable = false)
	private Item item;

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

	public CustomerOrder getOrder() {
		return order;
	}

	public void setOrder(CustomerOrder order) {
		this.order = order;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public OrderItem() {

	}

	public OrderItem(double price, double cost, int quantity, Item item, CustomerOrder order) {
		this.setItem(item);
		this.setOrder(order);
		this.setPrice(price);
		this.setCost(cost);
		this.setQuantity(quantity);
	}

	public JSONObject getInfo(MetaLanguage language, LibraryItemLanguageRepository libraryItemLanguageRepository,
			Item item, ItemLanguageRepository itemLanguageRepository, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository, ItemRepository itemRepository,
			Boolean isFlashSale, FlashSale flashSale, FlashSaleItemRepository flashSaleItemRepository) {
		JSONObject json = new JSONObject();
		json.put("listItem", this.getItem().getLibraryItem().getInfo(language, libraryItemLanguageRepository,
				libraryCategoryLanguageRepository));
		json.put("quantity", this.getQuantity());
		if (item != null)
			json.put("item",
					item.getInfo(language, itemLanguageRepository, branchLanguageRepository, dayLanguageRepository,
							libraryCategoryLanguageRepository, itemRepository, isFlashSale, flashSale,
							flashSaleItemRepository, false));
		else
			json.put("item", "-");

		return json;
	}

}
