package ksa.so.domain;



import javax.jdo.annotations.Column;
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

@Entity
@Table(name="CONFIG_MENU_RIGHTS")
@JsonIgnoreProperties(ignoreUnknown = true, value ={"hibernateLazyInitializer", "handler","createdBy" , "updatedBy" ,"created" , "updated"}
,allowSetters=true)
public class MenuRights {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name="MENU_ID",referencedColumnName="MENU_ID")
	Menu menu;

	@ManyToOne
//	@JoinColumn(name="USERPROFILE_ID",referencedColumnName="USERPROFILE_ID")
	MetaUserType  profile;

	@Column(name="ADDRIGHT")
	private Boolean addrights = false;

	@Column(name="EDITRIGHT")
	private Boolean editrights = false;

	@Column(name="DELETERIGHT")
	private Boolean deleterights = false;

	@Column(name="VIEWRIGHT")
	private Boolean viewrights = false;

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

	
	public MenuRights()
	{
		
	}
	
	public MenuRights(Menu menu,Boolean viewrights,Boolean addrights,Boolean editrights,Boolean deleterights,Long profileid)
	{
		MetaUserType pobj= new MetaUserType();
		pobj.setId(profileid);
		this.menu = menu;
		this.viewrights=viewrights;
		this.addrights=addrights;
		this.editrights=editrights;
		this.deleterights=deleterights;
		this.profile=pobj;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

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
	public MetaUserType getProfile() {
		return profile;
	}

	public void setProfile(MetaUserType profile) {
		this.profile = profile;
	}
	public Boolean getAddrights() {
		return addrights;
	}

	public void setAddrights(Boolean addrights) {
		this.addrights = addrights;
	}

	public Boolean getEditrights() {
		return editrights;
	}

	public void setEditrights(Boolean editrights) {
		this.editrights = editrights;
	}

	public Boolean getDeleterights() {
		return deleterights;
	}

	public void setDeleterights(Boolean deleterights) {
		this.deleterights = deleterights;
	}

	public Boolean getViewrights() {
		return viewrights;
	}

	public void setViewrights(Boolean viewrights) {
		this.viewrights = viewrights;
	}

}
