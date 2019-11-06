package ksa.so.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.MetaStatusLanguageRepository;

@Entity
public class MetaStatus {
	public MetaStatus() {
		super();
	}

	public MetaStatus(Long id, String code, List<AppVersion> appVersionList, List<Hq> hqList, List<Branch> branchList,
			List<User> userList, List<UserApp> userAppList, List<CustomerOrder> orderList, List<Item> itemList,
			List<MetaStatusLanguage> metaStatusLanguageList, List<AppUserReport> appUserReportList) {
		super();
		this.id = id;
		this.code = code;
		this.appVersionList = appVersionList;
		this.hqList = hqList;
		this.branchList = branchList;
		this.userList = userList;
		this.userAppList = userAppList;
		this.orderList = orderList;
		this.itemList = itemList;
		this.metaStatusLanguageList = metaStatusLanguageList;
		this.appUserReportList = appUserReportList;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<AppVersion> appVersionList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<Hq> hqList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<Branch> branchList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<User> userList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<UserApp> userAppList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<CustomerOrder> orderList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<Item> itemList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<MetaStatusLanguage> metaStatusLanguageList;
	
	@JsonIgnore
	@OneToMany(mappedBy = "status")
	private List<AppUserReport> appUserReportList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<AppVersion> getAppVersionList() {
		return appVersionList;
	}

	public void setAppVersionList(List<AppVersion> appVersionList) {
		this.appVersionList = appVersionList;
	}

	public List<Hq> getHqList() {
		return hqList;
	}

	public void setHqList(List<Hq> hqList) {
		this.hqList = hqList;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public List<UserApp> getUserAppList() {
		return userAppList;
	}

	public void setUserAppList(List<UserApp> userAppList) {
		this.userAppList = userAppList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<CustomerOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<CustomerOrder> orderList) {
		this.orderList = orderList;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public List<MetaStatusLanguage> getMetaStatusLanguageList() {
		return metaStatusLanguageList;
	}

	public void setMetaStatusLanguageList(List<MetaStatusLanguage> metaStatusLanguageList) {
		this.metaStatusLanguageList = metaStatusLanguageList;
	}

	public List<AppUserReport> getAppUserReportList() {
		return appUserReportList;
	}

	public void setAppUserReportList(List<AppUserReport> appUserReportList) {
		this.appUserReportList = appUserReportList;
	}
	
	public JSONObject getInfo(MetaLanguage language, MetaStatusLanguageRepository statusLanguageRepository){
		JSONObject json = new JSONObject();
		
		MetaStatusLanguage metaStatusLanguage = statusLanguageRepository.findByStatusAndLanguage(this, language);
		json.put("code", this.getCode());
		json.put("title", metaStatusLanguage.getTitle());
		json.put("text", metaStatusLanguage.getText());
		
		return json;
	}
}
