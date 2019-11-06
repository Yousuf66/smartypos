package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ComboItemSubOpt {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "FkComboItem", nullable=false)
	private ComboItem comboItem;
	
	@ManyToOne
    @JoinColumn(name = "FkSubOpt", nullable=false)
	private SubOpt subOpt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ComboItem getComboItem() {
		return comboItem;
	}

	public void setComboItem(ComboItem comboItem) {
		this.comboItem = comboItem;
	}

	public SubOpt getSubOpt() {
		return subOpt;
	}

	public void setSubOpt(SubOpt subOpt) {
		this.subOpt = subOpt;
	}
}
