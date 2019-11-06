package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class InvoiceItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	Item item;
	Long quantity;
	String barcode;
	Double price = (double) 0;
	Double cost = (double) 0;
	Double discountAmount = (double) 0;
	Double discountPercentage = 0.0;
	Double netSalePrice = (double) 0;

	String title;
	Long itemsId;
	
	
	public InvoiceItem(Long id, Item item, Long quantity, String barcode, Double price, Double cost,
			Double discountAmount, Double discountPercentage, Double netSalePrice ,String title,Long itemsId) {
		super();
		this.id = id;
		this.item = item;
		this.quantity = quantity;
		this.barcode = barcode;
		this.price = price;
		this.cost = cost;
		this.discountAmount = discountAmount;
		this.discountPercentage = discountPercentage;
		this.netSalePrice = netSalePrice;
		this.title = title;
		this.itemsId = itemsId;
	}
	
	public Long getItemsId() {
		return itemsId;
	}

	public void setItemsId(Long itemsId) {
		this.itemsId = itemsId;
	}

	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Double getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public Double getNetSalePrice() {
		return netSalePrice;
	}
	public void setNetSalePrice(Double netSalePrice) {
		this.netSalePrice = netSalePrice;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
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
	public InvoiceItem() {
		super();
	}
	public InvoiceItem(Long id, Item item, Long quantity) {
		super();
		this.id = id;
		this.item = item;
		this.quantity = quantity;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
}
