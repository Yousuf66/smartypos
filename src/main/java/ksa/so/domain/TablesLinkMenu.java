package ksa.so.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity

@JsonIgnoreProperties(ignoreUnknown = true, value ={"hibernateLazyInitializer", "handler", "createdBy" , "updatedBy" , "created" , "updated"}
,allowSetters=true)
public class TablesLinkMenu 
{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="LINKAGE_ID")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="MENU_ID",referencedColumnName="MENU_ID")
	Menu menu;
	
	@ManyToOne
	@JoinColumn(name="TABLE_ID",referencedColumnName="TABLE_ID")
	Tables tables;

	@ManyToOne
	@JoinColumn(name="LINKEDBY")
	User linkedBy;

	@Column(name="LINKEDON")
	private Date linkedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Tables getTables() {
		return tables;
	}

	public void setTables(Tables tables) {
		this.tables = tables;
	}

	public User getLinkedBy() {
		return linkedBy;
	}

	public void setLinkedBy(User linkedBy) {
		this.linkedBy = linkedBy;
	}

	public Date getLinkedOn() {
		return linkedOn;
	}

	public void setLinkedOn(Date linkedOn) {
		this.linkedOn = linkedOn;
	}

}
