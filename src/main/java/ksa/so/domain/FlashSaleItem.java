package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FlashSaleItem {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getFlashSalePrice() {
		return flashSalePrice;
	}

	public void setFlashSalePrice(Double flashSalePrice) {
		this.flashSalePrice = flashSalePrice;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public FlashSale getFlashSale() {
		return flashSale;
	}

	public void setFlashSale(FlashSale flashSale) {
		this.flashSale = flashSale;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Double flashSalePrice;

	@ManyToOne
	@JoinColumn(name = "FkItem", nullable = false)
	private Item item;

	@ManyToOne
	@JoinColumn(name = "FkSale", nullable = false)
	private FlashSale flashSale;

}
