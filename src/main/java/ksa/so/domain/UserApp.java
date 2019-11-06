package ksa.so.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ksa.so.repositories.MetaStatusLanguageRepository;

@Entity
public class UserApp implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String phone = "-";

	private String username = "-";
	private String password = "-";

	private String firstName = "-";
	private String lastName = "-";
	private String email = "-";

	private String gender = "-";

	private String otp = "-";

	private String installationId = "-";

	@ManyToOne
	@JoinColumn(name = "FkCountry", nullable = false)
	private MetaCountry country;

	@ManyToOne
	@JoinColumn(name = "FkStatus", nullable = false)
	private MetaStatus status;

	@OneToMany(mappedBy = "user")
	private List<CustomerOrder> orderList;

	// @OneToMany(mappedBy = "user")

	// @OneToOne
	// @JoinColumn(name="user")
	// @PrimaryKeyJoinColumn

	// @OneToOne
	// @JoinColumn(name="user")
	@OneToMany(mappedBy = "user")
	private List<UserAddress> userAddress;
	// private UserAddress userAddress;

	/*
	 * @OneToOne(mappedBy = "user") private UserCard userCard;
	 */

	@OneToMany(mappedBy = "user")
	private List<AppUserReport> appUserReportList;

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public MetaCountry getCountry() {
		return country;
	}

	public void setCountry(MetaCountry country) {
		this.country = country;
	}

	public List<UserAddress> getAddress() {
		return userAddress;
	}

	public void setAddress(List<UserAddress> userAddress) {
		this.userAddress = userAddress;
	}

//	public UserAddress getAddress() {
//		return userAddress;
//	}
//
//	public void setAddress(UserAddress userAddress) {
//		this.userAddress = userAddress;
//	}

	/*
	 * public UserCard getCard() { return userCard; }
	 *
	 * public void setCard(UserCard userCard) { this.userCard = userCard; }
	 */

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public List<CustomerOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<CustomerOrder> orderList) {
		this.orderList = orderList;
	}

	public List<AppUserReport> getAppUserReportList() {
		return appUserReportList;
	}

	public void setAppUserReportList(List<AppUserReport> appUserReportList) {
		this.appUserReportList = appUserReportList;
	}

	public String getInstallationId() {
		return installationId;
	}

	public void setInstallationId(String installationId) {
		this.installationId = installationId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public void setPassword(String password) {
		if (password.equals("-")) {
			this.password = password;
		} else {
			// for encoded Password in Database
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			this.password = passwordEncoder.encode(password);
		}
	}

	public JSONObject getInfo() {

		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("username", this.getFirstName());
		json.put("firstName", this.getFirstName());
		json.put("lastName", this.getLastName());
		json.put("email", this.getEmail());
		json.put("gender", this.getGender());
		json.put("otp", this.getOtp());
		json.put("phone", this.getPhone());

		return json;
	}

	public JSONObject getInfo(MetaLanguage language, MetaStatusLanguageRepository statusLanguageRepository) {
		MetaStatusLanguage statusLanguage = statusLanguageRepository.findByStatusAndLanguage(this.getStatus(),
				language);
//		Wallet wallet = walletRepository.findByUser(this);
		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("username", this.getFirstName());
		json.put("firstName", this.getFirstName());
		json.put("lastName", this.getLastName());
		json.put("email", this.getEmail());
		json.put("gender", this.getGender());
		json.put("otp", this.getOtp());
		json.put("phone", this.getPhone());

//		json.put("WalletAmount", wallet.getWalletAmount());
		json.put("statusLanguageInfo", statusLanguage.getInfo());
		json.put("countryInfo", this.getCountry().getInfo());

		// address
		/*
		 * List<UserAddress> userAddress = this.getAddress(); if (userAddress != null &&
		 * userAddress.size() > 0) { json.put("addressInfo",
		 * userAddress.get(0).getInfo()); }
		 */

//		json.put("userAddressInfo", jsonAddressList);
//		if(this.getAddress() != null)
//			json.put("userAddressInfo", this.getAddress().getInfo());
//		else json.put("userAddressInfo","-");
//		if(this.getCard() != null)
//			json.put("userCardInfo", this.getCard().getInfo());
//		else json.put("userCardInfo","-");
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
