package ksa.so.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.HqLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;
import ksa.so.repositories.MetaStatusLanguageRepository;

@Entity
@Table(name="User")

public class User implements UserDetails {
	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", phone=" + phone + ", name=" + name + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", token=" + token + ", otp=" + otp + ", sessionid=" + sessionid
				+ ", userType=" + userType + ", country=" + country + ", status=" + status + ", hq=" + hq + ", branch="
				+ branch + "]";
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name="MENU_ID")
	private Long id;

	public User(Long id, String phone, String name, String email, String username, String password, String token,
			String otp, String sessionid, MetaUserType userType, MetaCountry country, MetaStatus status, Hq hq,
			Branch branch) {
		super();
		this.id = id;
		this.phone = phone;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.token = token;
		this.otp = otp;
		this.sessionid = sessionid;
		this.userType = userType;
		this.country = country;
		this.status = status;
		this.hq = hq;
		this.branch = branch;
	}

	private String phone = "-";

	private String name = "-";



	private String email = "-";

	private String username = "-";

	private String password = "-";

	private String token = "-";

	private String otp = "-";

	private String sessionid = "-";

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "FkUserType", nullable = false)
	private MetaUserType userType;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "FkCountry", nullable = false)
	private MetaCountry country;

	@ManyToOne
	@JoinColumn(name = "FkStatus", nullable = false)
	private MetaStatus status;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "FkHq", nullable = true)
	private Hq hq;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "FkBranch", nullable = true)
	private Branch branch;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getSessionId() {
		return sessionid;
	}

	public void setSessionId(String sessionid) {
		this.sessionid = sessionid;
	}

	public void setPassword(String password) {
		// this.password = password;
		this.password = password;
		/*
		 * if (password.equals("-")) { this.password = password; } else { // for encoded
		 * Password in Database PasswordEncoder passwordEncoder = new
		 * BCryptPasswordEncoder(); this.password = passwordEncoder.encode(password); }
		 */
	}

	public MetaUserType getUserType() {
		return userType;
	}

	public void setUserType(MetaUserType userType) {
		this.userType = userType;
	}

	public MetaCountry getCountry() {
		return country;
	}

	public void setCountry(MetaCountry country) {
		this.country = country;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public Hq getHq() {
		return hq;
	}

	public void setHq(Hq hq) {
		this.hq = hq;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getInstallationId() {
		return token;
	}

	public void setInstallationId(String token) {
		this.token = token;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	@JsonIgnore
	public JSONObject getInfo(MetaLanguage language, MetaStatusLanguageRepository statusLanguageRepository,
			BranchLanguageRepository branchLanguageRepository, MetaDayLanguageRepository dayLanguageRepository,
			Boolean getSubOperator, ItemRepository itemRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository) {

		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("username", this.getUsername());
		json.put("name", this.getName());
		json.put("email", this.getEmail());
		json.put("userType", this.getUserType().getCode());
		json.put("countryInfo", this.getCountry().getInfo());
		json.put("branchInfo", this.getBranch().getBasicInfo(language, branchLanguageRepository, null, libraryCategoryLanguageRepository, true));
		json.put("sessionid", this.getSessionId());
		return json;
	}
	@JsonIgnore

	public JSONObject getInfo(MetaLanguage language, HqLanguageRepository hqLanguageRepository) {

		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("username", this.getUsername());
		json.put("name", this.getName());
		json.put("email", this.getEmail());
		json.put("otp", this.getOtp());
		json.put("phone", this.getPhone());
		json.put("userType", this.getUserType().getCode());
		if (this.getCountry() != null) {
			json.put("countryInfo", this.getCountry().getInfo());
		}
		json.put("sessionid", this.getSessionId());
		if (this.getHq() != null) {
			json.put("storeInfo", this.getHq().getInfo(language, hqLanguageRepository));
		}

		return json;
	}
	@JsonIgnore
	public JSONObject getInfo(MetaLanguage language) {

		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("username", this.getUsername());
		json.put("name", this.getName());
		json.put("email", this.getEmail());
		json.put("userType", this.getUserType().getCode());
		if (this.getCountry() != null) {
			json.put("countryInfo", this.getCountry().getInfo());
		}
		json.put("sessionid", this.getSessionId());

		return json;
	}
	@JsonIgnore
	public JSONObject getInfo(MetaLanguage language, MetaStatusLanguageRepository statusLanguageRepository) {

		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("username", this.getUsername());
		json.put("name", this.getName());
		json.put("email", this.getEmail());
		json.put("otp", this.getOtp());
		json.put("phone", this.getPhone());
		json.put("userType", this.getUserType().getCode());
		json.put("countryInfo", this.getCountry().getInfo());
		json.put("sessionid", this.getSessionId());
		return json;
	}
	@JsonIgnore
	public JSONObject getInfo() {

		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("username", this.getUsername());
		json.put("name", this.getName());
		json.put("email", this.getEmail());
		json.put("phone", this.getPhone());
		json.put("userType", this.getUserType().getCode());
		json.put("countryInfo", this.getCountry().getInfo());

		return json;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
}
