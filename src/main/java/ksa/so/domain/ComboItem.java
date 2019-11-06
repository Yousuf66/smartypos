package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ComboItem {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "FkCombo", nullable=false)
	private Combo combo;
	
	@ManyToOne
    @JoinColumn(name = "FkItem", nullable=false)
	private Item item;
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Combo getCombo() {
		return combo;
	}

	public void setCombo(Combo combo) {
		this.combo = combo;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}
}
