package ksa.so.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.MetaStatusLanguageRepository;

@Entity
public class UserAddress {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name = "-";
	private String phone = "-";
	private String zipcode = "-";
	private String address = "-";
	private String manualAddress = "-";
	private String city = "-";
	private String province = "-";
	private boolean isDefault = false;
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	@JsonProperty
	private String lon = "0";
	
	@JsonProperty
	private String lat = "0";
	
	@JsonProperty
	private String placeId = "-";
	//@ManyToOne
    //@JoinColumn(name = "FkUser", nullable=false)
	
    //@OneToOne(mappedBy="userAddress")
    //@MapsId
    //@OneToOne
    //@JoinColumn(name="FkUser")

	//@OneToOne(mappedBy="userAddress")
//	@OneToOne
//	@JoinColumn(name="FkUser", nullable=false)
	@ManyToOne
    @JoinColumn(name = "FkUser", nullable=false)
	private UserApp user;
	
	public UserApp getUserApp() {
		return user;
	}

	public void setUserApp(UserApp user) {
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public String getZipCode() {
		return zipcode;
	}

	public void setZipCode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getManualAddress() {
		return manualAddress;
	}

	public void setManualAddress(String manualAddress) {
		this.manualAddress = manualAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
	public boolean getIsDefault() {
		return isDefault;
	}
	
	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public JSONObject getInfo(){
		
		JSONObject json = new JSONObject();
		
		json.put("id", this.getId());
		json.put("lat", this.getLat());
		json.put("lon", this.getLon());
		json.put("placeId", this.getPlaceId());
		json.put("address", this.getAddress());
		json.put("name", this.getname());
		json.put("manualAddress",this.getManualAddress());
//		json.put("zipcode", this.getZipCode());
//		json.put("phone", this.getPhone());
//		json.put("city", this.getCity());
//		json.put("province", this.getProvince());
//		json.put("isDefault", this.getIsDefault());
		
		return json;
	}

}
