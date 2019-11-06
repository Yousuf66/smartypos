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
@Table(name="CONFIG_PAGES")
@JsonIgnoreProperties(ignoreUnknown = true, value ={"hibernateLazyInitializer", "handler", "createdBy" , "updatedBy" , "created" , "updated"}
,allowSetters=true)
public class Pages 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PAGE_ID")
	private Long id;
	
	@Column(name="PAGENAME", length = 250)
	private String pageName;
	
	@Column(name="TITLE", length = 250)
	private String title;
	
	@Column(name="TABLEKEY", length = 50)
	private String tableKey;
	
	@Column(name="PAGEKEY", length = 50)
	private String pageKey;
	
	@Column(name="TYPE", length = 250)
	private String type;
	
	@Column(name="MC")
	private Boolean mc;
	
	@ManyToOne
//   	@JoinColumn(name="STATUS_ID",referencedColumnName="STATUS_ID")
	MetaStatus status;
	
//	@ManyToOne
//   	@JoinColumn(name="CREATEDBY",referencedColumnName="USER_ID")
//   	 User createdBy;
//    
//    @ManyToOne
//   	@JoinColumn(name="UPDATEDBY",referencedColumnName="USER_ID")
//   	 User updatedBy;
//    
	@Column(name="CREATEDON")
	private Date created;
	
	@Column(name="UPDATEDON")
	private Date updated;

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

//	public Users getCreatedBy() {
//		return createdBy;
//	}

//	public void setCreatedBy(Users createdBy) {
//		this.createdBy = createdBy;
//	}

//	public Users getUpdatedBy() {
//		return updatedBy;
//	}

//	public void setUpdatedBy(Users updatedBy) {
//		this.updatedBy = updatedBy;
//	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getMc() {
		return mc;
	}

	public void setMc(Boolean mc) {
		this.mc = mc;
	}

	public String getTableKey() {
		return tableKey;
	}

	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
	}

	public String getPageKey() {
		return pageKey;
	}

	public void setPageKey(String pageKey) {
		this.pageKey = pageKey;
	}

}
