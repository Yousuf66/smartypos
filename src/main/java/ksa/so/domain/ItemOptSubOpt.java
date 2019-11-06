package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ItemOptSubOpt {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "FkItem", nullable=false)
	private Item item;
	
	@ManyToOne
    @JoinColumn(name = "FkOpt")
	private Opt opt;
	
	@ManyToOne
    @JoinColumn(name = "FkSubOpt")
	private SubOpt subOpt;
	
	long price = (long)0;

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

	public Opt getOpt() {
		return opt;
	}

	public void setOpt(Opt opt) {
		this.opt = opt;
	}

	public SubOpt getSubOpt() {
		return subOpt;
	}

	public void setSubOpt(SubOpt subOpt) {
		this.subOpt = subOpt;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
}
