package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.api.services.sqladmin.SQLAdmin.Users;
import com.google.cloud.Date;
import com.google.protobuf.DescriptorProtos.FieldOptions;

@Entity
@Table(name="CONFIG_MENU")
@JsonIgnoreProperties(ignoreUnknown = true, value ={"hibernateLazyInitializer", "handler","createdBy" , "updatedBy" ,"created" , "updated"}
,allowSetters=true)
public class Menu {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="MENU_ID")
	private Long id;

	@Column(name="SEQUENCE")
	private Integer sequence;

	@Column(name="DISPLAYNAME", length =100)
	private String displayName;

	@ManyToOne
	@JoinColumn(name="PARENTID",referencedColumnName="MENU_ID")
	private Menu parentID;

//	@ManyToOne
//	@JoinColumn(name="MODULE_ID",referencedColumnName="MODULE_ID")
//	private Module module;

	@Column(name="LINK",  length =250)
	private String link;

	@Column(name="ICON_CLASS", length =250)
	private String iconClass;

	@Column(name="TRANSLATIONID", length = 50)
	private Integer translationId;
	
//	@ManyToOne
//	@JoinColumn(name = "OPTIONS_ID" , referencedColumnName = "OPTIONS_ID")
//	FieldOptions fieldOptions;

	@ManyToOne
//	@JoinColumn(name="CREATEDBY",referencedColumnName="USER_ID")
	User createdBy;

	@ManyToOne
//	@JoinColumn(name="UPDATEDBY",referencedColumnName="USER_ID")
	User updatedBy;

	@Column(name="CREATEDON")
	private Date created;

	@Column(name="UPDATEDON")
	private Date updated;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Menu getParentID() {
		return parentID;
	}

	public void setParentID(Menu parentID) {
		this.parentID = parentID;
	}

}
