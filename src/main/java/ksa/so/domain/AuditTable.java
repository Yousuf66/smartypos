package ksa.so.domain;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="LOG_AUDITTABLE")
public class AuditTable {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="RECORD_ID")
	private Integer recordId;
	
	@ManyToOne
	@JoinColumn(name="MENU_ID",referencedColumnName="MENU_ID")
	Menu menu;
	@Column(name="ACTION")
	private char action;
	
	@Column(name="DATEON")
	private Date dateOn;
	
	@ManyToOne
//	@JoinColumn(name="UPDATEDBY",referencedColumnName="USER_ID")
	User updatedBy;
	
	@Column(name="IPADDRESS", length=100)
	private String ipAddress;
	
	@Lob
	@Column(name="OLDVALUE",length=1000)
	private String oldValue;
	@Lob
	@Column(name="NEWVALUE",length=1000)
	private String newValue;
	public AuditTable() {
		super();
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public char getAction() {
		return action;
	}

	public void setAction(char action) {
		this.action = action;
	}

	public Date getDateOn() {
		return dateOn;
	}

	public void setDateOn(Date dateOn) {
		this.dateOn = dateOn;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public AuditTable(long id, Integer recordId, Menu menu, char action, Date dateOn, User updatedBy, String ipAddress,
			String oldValue, String newValue) {
		super();
		this.id = id;
		this.recordId = recordId;
		this.menu = menu;
		this.action = action;
		this.dateOn = dateOn;
		this.updatedBy = updatedBy;
		this.ipAddress = ipAddress;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}


	
}
