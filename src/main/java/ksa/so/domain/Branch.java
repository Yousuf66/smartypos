package ksa.so.domain;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;

@Entity
@Table(name="branch")
public class Branch {


	private static final String DEFAULT = null;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonProperty
	private long orderCompletionTime;
	@JsonProperty
	private String address = "-";

	@JsonProperty
	private String phone1 = "-";

	@JsonProperty
	private String phone2 = "-";

	@JsonProperty
	private String lon = "0";

	@JsonProperty
	private String lat = "0";

	@JsonProperty
	private String maxKm = "0";
	

	@JsonProperty
	@JsonIgnore
	@Column(nullable = true, columnDefinition = "Decimal(10,0) default '100'")
	private long orderNumber = 0;

	@JsonProperty
	private boolean is24hours = true;

	@JsonProperty
	private boolean hasDelivery = true;

	@JsonProperty
	private boolean hasTakeaway = true;

	@JsonProperty
	private String timeOpen = "00:00";

	@JsonProperty
	private String timeClose = "23:59";

	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";

	@ManyToOne
	
	@JoinColumn(name = "FkStatus", nullable = false)
	private MetaStatus status;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "FkCurrency", nullable = false)
	private MetaCurrency currency;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "FkHq", nullable = false)
	private Hq hq;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "FkCountry", nullable = false)
	private MetaCountry country;

	@OneToMany(mappedBy = "branch")
	@JsonIgnore
	private List<CustomerOrder> orderList;

	@OneToMany(mappedBy = "branch")
	@JsonIgnore
	private List<BranchDay> branchDayList;

	@OneToMany(mappedBy = "branch")
	@JsonIgnore
	private List<MetaTime> metaTimeList;

	@OneToMany(mappedBy = "branch")
	@JsonIgnore
	private List<BranchPayment> paymentList;
	
	@OneToMany(mappedBy = "branch")
	private List<BranchLanguage> branchLanguageList;

	@JsonIgnore
	@OneToMany(mappedBy = "branch")
	private List<User> operatorList;

	@JsonIgnore
	@OneToMany(mappedBy = "branch")
	private List<Category> categoryList;

	@JsonIgnore
	@OneToMany(mappedBy = "branch")
	private List<Opt> optList;

	@JsonProperty
	private double rating;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "CreatedBy")
	private User CreatedBy;

	@JsonIgnore
	@Column(name = "CreatedAt")
	private Date created;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "UpdatedBy")
	private User UpdatedBy;

	@JsonIgnore
	@Column(name = "UpdatedAt")
	private Date updated;


	

	public Branch(Long id, long orderCompletionTime, String address, String phone1, String phone2, String lon,
			String lat, String maxKm, long orderNumber, boolean is24hours, boolean hasDelivery, boolean hasTakeaway,
			String timeOpen, String timeClose, String fileName, String fileUrl, MetaStatus status,
			MetaCurrency currency, Hq hq, MetaCountry country, List<CustomerOrder> orderList,
			List<BranchDay> branchDayList, List<MetaTime> metaTimeList, List<BranchPayment> paymentList,
			List<BranchLanguage> branchLanguageList, List<User> operatorList, List<Category> categoryList,
			List<Opt> optList, double rating, User createdBy, Date created, User updatedBy, Date updated,
			String maxOrderTime, Double shippingFees) {
		super();
		this.id = id;
		this.orderCompletionTime = orderCompletionTime;
		this.address = address;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.lon = lon;
		this.lat = lat;
		this.maxKm = maxKm;
		this.orderNumber = orderNumber;
		this.is24hours = is24hours;
		this.hasDelivery = hasDelivery;
		this.hasTakeaway = hasTakeaway;
		this.timeOpen = timeOpen;
		this.timeClose = timeClose;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.status = status;
		this.currency = currency;
		this.hq = hq;
		this.country = country;
		this.orderList = orderList;
		this.branchDayList = branchDayList;
		this.metaTimeList = metaTimeList;
		this.paymentList = paymentList;
		this.branchLanguageList = branchLanguageList;
		this.operatorList = operatorList;
		this.categoryList = categoryList;
		this.optList = optList;
		this.rating = rating;
		CreatedBy = createdBy;
		this.created = created;
		UpdatedBy = updatedBy;
		this.updated = updated;
		this.maxOrderTime = maxOrderTime;
		this.shippingFees = shippingFees;
	}


	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Branch() {
		super();
	}

	public List<Opt> getOptList() {
		return optList;
	}

	public void setOptList(List<Opt> optList) {
		this.optList = optList;
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

	public Double getShippingFees() {
		return shippingFees;
	}

	public void setShippingFees(Double shippingFees) {
		this.shippingFees = shippingFees;
	}

	public static String getDefault() {
		return DEFAULT;
	}
	public String getMaxKm() {
		return maxKm;
	}

	public void setMaxKm(String maxKm) {
		this.maxKm = maxKm;
	}

	public String getMaxOrderTime() {
		return maxOrderTime;
	}

	public void setMaxOrderTime(String maxOrderTime) {
		this.maxOrderTime = maxOrderTime;
	}

	@JsonProperty
	private String maxOrderTime = "1";

	@JsonProperty
	private Double shippingFees = 0.0;

	public Double getShippingfees() {
		return shippingFees;
	}

	public void setShippingfees(Double shippingFees) {
		this.shippingFees = shippingFees;
	}


	public List<MetaTime> getMetaTimeList() {
		return metaTimeList;
	}

	public void setMetaTimeList(List<MetaTime> metaTimeList) {
		this.metaTimeList = metaTimeList;
	}

	public List<BranchPayment> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<BranchPayment> paymentList) {
		this.paymentList = paymentList;
	}


	public User getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(User createdBy) {
		CreatedBy = createdBy;
	}

	public User getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		UpdatedBy = updatedBy;
	}

	public Date getCreatedAt() {
		return created;
	}

	public void setCreatedAt(Date created) {
		this.created = created;
	}

	public Date getUpdatedAt() {
		return updated;
	}

	public void setUpdatedAt(Date updated) {
		this.updated = updated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
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

	public boolean isIs24hours() {
		return is24hours;
	}

	public void setIs24hours(boolean is24hours) {
		this.is24hours = is24hours;
	}

	public boolean getHasDelivery() {
		return hasDelivery;
	}

	public void setHasDelivery(boolean hasDelivery) {
		this.hasDelivery = hasDelivery;
	}

	public boolean getHasTakeaway() {
		return hasTakeaway;
	}

	public void setHasTakeaway(boolean hasTakeaway) {
		this.hasTakeaway = hasTakeaway;
	}

	public String getTimeOpen() {
		return timeOpen;
	}

	public void setTimeOpen(String timeOpen) {
		this.timeOpen = timeOpen;
	}

	public String getTimeClose() {
		return timeClose;
	}

	public void setTimeClose(String timeClose) {
		this.timeClose = timeClose;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public MetaCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(MetaCurrency currency) {
		this.currency = currency;
	}

	public Hq getHq() {
		return hq;
	}

	public void setHq(Hq hq) {
		this.hq = hq;
	}

	public MetaCountry getCountry() {
		return country;
	}

	public void setCountry(MetaCountry country) {
		this.country = country;
	}

	public List<CustomerOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<CustomerOrder> orderList) {
		this.orderList = orderList;
	}

	public List<BranchDay> getBranchDayList() {
		return branchDayList;
	}

	public void setBranchDayList(List<BranchDay> branchDayList) {
		this.branchDayList = branchDayList;
	}

	public List<BranchLanguage> getBranchLanguageList() {
		return branchLanguageList;
	}

	public void setBranchLanguageList(List<BranchLanguage> branchLanguageList) {
		this.branchLanguageList = branchLanguageList;
	}

	public List<User> getOperatorList() {
		return operatorList;
	}

	public void setOperatorList(List<User> operatorList) {
		this.operatorList = operatorList;
	}

	public long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public long getOrderCompletionTime() {
		return orderCompletionTime;
	}

	public void setOrderCompletionTime(long orderCompletionTime) {
		this.orderCompletionTime = orderCompletionTime;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public JSONObject getInfo(MetaLanguage language, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository, String lat, String lon, ItemRepository itemRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository) {
		JSONObject json = new JSONObject();

		//
		BranchLanguage branchLanguage = branchLanguageRepository.findByBranchAndLanguage(this, language);
		//

		json.put("id", this.getId());
		if (branchLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", branchLanguage.getTitle());
		}
		if (branchLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", branchLanguage.getDetails());
		}
		if (this.getAddress() == null) {
			json.put("address", "-");
		} else {
			json.put("address", this.getAddress());
		}
		if (this.getFileName() == null) {
			json.put("fileName", "-");
		} else {
			json.put("fileName", this.getFileName());
		}
		if (this.getFileUrl() == null) {
			json.put("fileUrl", "-");
		} else {
			json.put("fileUrl", this.getFileUrl());
		}
		if (this.getLat() == null) {
			json.put("lat", "-");
		} else {
			json.put("lat", this.getLat());
		}
		if (this.getMaxKm() == null) {
			json.put("maxKm", "-");
		} else {
			json.put("maxKm", this.getMaxKm());
		}
		if (this.getLon() == null) {
			json.put("lon", "-");
		} else {
			json.put("lon", this.getLon());
		}
		if (this.getPhone1() == null) {
			json.put("phone1", "-");
		} else {
			json.put("phone1", this.getPhone1());
		}
		if (this.getPhone2() == null) {
			json.put("phone2", "-");
		} else {
			json.put("phone2", this.getPhone2());
		}
		if (this.getTimeOpen() == null) {
			json.put("timeOpen", "-");
		} else {
			json.put("timeOpen", this.getTimeOpen());
		}
		if (this.getTimeClose() == null) {
			json.put("timeClose", "-");
		} else {
			json.put("timeClose", this.getTimeClose());
		}
		if (this.getStatus().getCode() == null) {
			json.put("status", "-");
		} else {
			json.put("status", this.getStatus().getCode());
		}

		json.put("maxOrderTime", this.getMaxOrderTime());
		json.put("shippingFees", this.getShippingfees());
		json.put("is24Hours", this.isIs24hours());
		json.put("rating", this.getRating());
		json.put("hasTakeaway", this.getHasTakeaway());
		json.put("hasDelivery", this.getHasDelivery());
		json.put("currencyInfo", this.getCurrency().getInfo());
		json.put("orderCompletionTime", this.getOrderCompletionTime());
		JSONArray jsonTimeSlots = new JSONArray();

		for (MetaTime time : this.getMetaTimeList()) {
			if (time.getStatus().getCode().equals("STA003"))
				jsonTimeSlots.put(time.getInfo());
		}

		json.put("timeSlots", jsonTimeSlots);

		JSONArray jsonDays = new JSONArray();
		for (BranchDay branchDay : this.getBranchDayList()) {

			jsonDays.put(branchDay.getDay().getInfo());
		}
		json.put("days", jsonDays);
		/*
		 * JSONArray jsonPaymentOptions = new JSONArray(); for(BranchPayment
		 * branchPayment : this.getPaymentList()) {
		 * if(branchPayment.getStatus().getCode().equals("STA003"))
		 * jsonPaymentOptions.put(branchPayment.getInfo()); }
		 *
		 * json.put("paymentOptions", jsonPaymentOptions);
		 */
		/* StringBuilder sb = new StringBuilder(); */
		/*
		 * for (BranchDay branchDay : this.getBranchDayList()) { if (sb.length() > 0) {
		 * sb.append(','); }
		 * sb.append(dayLanguageRepository.findByOpeningDayAndLanguage(branchDay.getDay(
		 * ), language).getTitle()); }
		 */
		/*
		 * String openingDays = sb.toString(); json.put("openingDays", openingDays);
		 */

		// TO get open closed status
		/*
		 * String currentDay = LocalDate.now().getDayOfWeek().name(); String[]
		 * openingDay = openingDays.split(","); Boolean isOpen = false; for (String day
		 * : openingDay) { if ((currentDay.equals(day.toUpperCase()) &&
		 * this.isIs24hours())) { isOpen = true; break; } else if
		 * (currentDay.equals(day.toUpperCase())) { LocalTime timeOpen =
		 * LocalTime.parse(this.getTimeOpen()); LocalTime timeClose =
		 * LocalTime.parse(this.getTimeClose()); LocalTime currentTime =
		 * LocalTime.now(); isOpen = ((!currentTime.isBefore(timeOpen) &&
		 * currentTime.isBefore(timeClose))); break; } } if (isOpen) json.put("isOpen",
		 * "Open"); else json.put("isOpen", "Closed");
		 */

		/*
		 * try { URL url = new
		 * URL("http://maps.googleapis.com/maps/api/directions/json?origin=" +
		 * this.getLat() + "," + this.getLon() + "&destination=" + lat + "," + lon +
		 * "&sensor=false&units=metric&mode=driving&key=AIzaSyCrJ7WS_yQbb_Bl9beoBA7z5JH6Y74BLU0"
		 * );
		 *
		 * final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 * conn.setRequestMethod("GET"); InputStream in = new
		 * BufferedInputStream(conn.getInputStream());
		 *
		 * BufferedReader rd = new BufferedReader(new InputStreamReader(in), 4096);
		 * String line; sb = new StringBuilder(); String contents = "{}";
		 *
		 * while ((line = rd.readLine()) != null) { sb.append(line); } rd.close();
		 * contents = sb.toString(); JSONObject jsonObject = new JSONObject(contents);
		 *
		 * JSONArray array = jsonObject.getJSONArray("routes"); JSONObject routes =
		 * array.getJSONObject(0); JSONArray legs = routes.getJSONArray("legs");
		 * JSONObject steps = legs.getJSONObject(0); JSONObject distance =
		 * steps.getJSONObject("distance");
		 *
		 * json.put("distance", distance.get("text")); } catch (Exception e) {
		 * json.put("distance", "-"); }
		 */

		return json;
	}

	public JSONObject getInfo(MetaLanguage language, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository, String lat, String lon, Optional<UserBranch> userBranch,
			Boolean getSubOperator, ItemRepository itemRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository) {
		JSONObject json = new JSONObject();

		//
		BranchLanguage branchLanguage = branchLanguageRepository.findByBranchAndLanguage(this, language);
		//

		json.put("id", this.getId());
		if (branchLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", branchLanguage.getTitle());
		}
		if (branchLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", branchLanguage.getDetails());
		}
		if (this.getAddress() == null) {
			json.put("address", "-");
		} else {
			json.put("address", this.getAddress());
		}
		if (this.getFileName() == null) {
			json.put("fileName", "-");
		} else {
			json.put("fileName", this.getFileName());
		}
		if (this.getFileUrl() == null) {
			json.put("fileUrl", "-");
		} else {
			json.put("fileUrl", this.getFileUrl());
		}
		if (this.getLat() == null) {
			json.put("lat", "-");
		} else {
			json.put("lat", this.getLat());
		}
		if (this.getLon() == null) {
			json.put("lon", "-");
		} else {
			json.put("lon", this.getLon());
		}
		if (this.getPhone1() == null) {
			json.put("phone1", "-");
		} else {
			json.put("phone1", this.getPhone1());
		}
		if (this.getPhone2() == null) {
			json.put("phone2", "-");
		} else {
			json.put("phone2", this.getPhone2());
		}
		if (this.getTimeOpen() == null) {
			json.put("timeOpen", "-");
		} else {
			json.put("timeOpen", this.getTimeOpen());
		}
		if (this.getTimeClose() == null) {
			json.put("timeClose", "-");
		} else {
			json.put("timeClose", this.getTimeClose());
		}
		if (this.getMaxKm() == null) {
			json.put("maxKm", "-");
		} else {
			json.put("maxKm", this.getMaxKm());
		}
		if (this.getStatus().getCode() == null) {
			json.put("status", "-");
		} else {
			json.put("status", this.getStatus().getCode());
		}

		json.put("maxOrderTime", this.getMaxOrderTime());
		json.put("shippingFees", this.getShippingfees());
		json.put("is24Hours", this.isIs24hours());
		json.put("hasTakeaway", this.getHasTakeaway());
		json.put("hasDelivery", this.getHasDelivery());
		json.put("rating", this.getRating());
		json.put("currencyInfo", this.getCurrency().getInfo());
		json.put("orderCompletionTime", this.getOrderCompletionTime());
		JSONArray jsonTimeSlots = new JSONArray();
		for (MetaTime time : this.getMetaTimeList()) {
			if (time.getStatus().getCode().equals("STA003"))
				jsonTimeSlots.put(time.getInfo());
		}
		json.put("timeSlots", jsonTimeSlots);
		JSONArray jsonDays = new JSONArray();
		for (BranchDay branchDay : this.getBranchDayList()) {

			jsonDays.put(branchDay.getDay().getInfo());
		}
		json.put("days", jsonDays);
		JSONArray jsonPaymentOptions = new JSONArray();
		for (BranchPayment branchPayment : this.getPaymentList()) {
			if (branchPayment.getStatus().getCode().equals("STA003"))
				jsonPaymentOptions.put(branchPayment.getInfo());
		}

		json.put("paymentOptions", jsonPaymentOptions);
		StringBuilder sb = new StringBuilder();
		JSONArray openingDays = new JSONArray();
		for (BranchDay branchDay : this.getBranchDayList()) {
//			if(sb.length() > 0){
//		        sb.append(',');
//		    }
			openingDays.put(branchDay.getDay().getCode());

			// sb.append(branchDay.getDay().getCode());
			// sb.append(dayLanguageRepository.findByOpeningDayAndLanguage(branchDay.getDay(),
			// language).getTitle());
		}
		// String openingDays = sb.toString();
		json.put("openingDays", openingDays);

		// TO get open closed status
		String currentDay = LocalDate.now().getDayOfWeek().name();

//		String[] openingDay = openingDays.split(",");
		Boolean isOpen = true;

//		for(String day : openingDay) {
//			if((currentDay.equals(day.toUpperCase()) && this.isIs24hours())) {
//				isOpen = true;
//				break;
//			}
//			else if (currentDay.equals(day.toUpperCase())) {
//				LocalTime timeOpen = LocalTime.parse(this.getTimeOpen());
//				LocalTime timeClose = LocalTime.parse(this.getTimeClose());
//				LocalTime currentTime = LocalTime.now();
//				isOpen = ((!currentTime.isBefore(timeOpen) && currentTime.isBefore(timeClose))) ;
//				break;
//			}
//		}
		if (isOpen)
			json.put("isOpen", "Open");
		else
			json.put("isOpen", "Closed");

		// isFavorite
		if (userBranch != null && userBranch.isPresent())
			json.put("isFavorite", "true");
		else
			json.put("isFavorite", "false");

		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + this.getLat() + ","
					+ this.getLon() + "&destination=" + lat + "," + lon
					+ "&sensor=false&units=metric&mode=driving&key=AIzaSyCrJ7WS_yQbb_Bl9beoBA7z5JH6Y74BLU0");

			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream in = new BufferedInputStream(conn.getInputStream());

			BufferedReader rd = new BufferedReader(new InputStreamReader(in), 4096);
			String line;
			sb = new StringBuilder();
			String contents = "{}";

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			contents = sb.toString();
			JSONObject jsonObject = new JSONObject(contents);

			JSONArray array = jsonObject.getJSONArray("routes");
			JSONObject routes = array.getJSONObject(0);
			JSONArray legs = routes.getJSONArray("legs");
			JSONObject steps = legs.getJSONObject(0);
			JSONObject distance = steps.getJSONObject("distance");
			JSONObject duration = steps.getJSONObject("duration");
			int dist = (int) distance.get("value");
			json.put("distance", dist);
			json.put("distanceInKm", distance.get("text"));
			json.put("duration", duration.get("text"));
		} catch (Exception e) {
			json.put("distance", "-");
		}

		if (getSubOperator) {
			// suboperators
			JSONArray jsonSubOperatorList = new JSONArray();
			if (this.getOperatorList() != null) {
				for (User operator : this.getOperatorList()) {
					if (operator.getUserType().getCode().equals("UT000004"))
						jsonSubOperatorList.put(operator.getInfo());
				}
			}
			json.put("subOperatorList", jsonSubOperatorList);
		}

		return json;
	}

	public JSONObject getBasicInfo(MetaLanguage language, BranchLanguageRepository branchLanguageRepository,
			ItemRepository itemRepository, LibraryCategoryLanguageRepository libraryCategoryLanguageRepository,
			boolean getSubOperatorList) {
		JSONObject json = new JSONObject();

		//
		BranchLanguage branchLanguage = branchLanguageRepository.findByBranchAndLanguage(this, language);
		// double totalProduct =
		// branchLanguageRepository.findTotalProducts(this.getId());

		json.put("id", this.getId());
		json.put("currencyInfo", this.getCurrency().getInfo());
		if (branchLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", branchLanguage.getTitle());
		}
		if (branchLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", branchLanguage.getDetails());
		}
		if (this.getAddress() == null) {
			json.put("address", "-");
		} else {
			json.put("address", this.getAddress());
		}

		if (this.getFileUrl() == null) {
			json.put("fileUrl", "-");
		} else {
			json.put("fileUrl", this.getFileUrl());
		}

		if (this.getPhone1() == null) {
			json.put("phone1", "-");
		} else {
			json.put("phone1", this.getPhone1());
		}
		if (this.getPhone2() == null) {
			json.put("phone2", "-");
		} else {
			json.put("phone2", this.getPhone2());
		}

		if (this.getStatus().getCode() == null) {
			json.put("status", "-");
		} else {
			json.put("status", this.getStatus().getCode());
		}

		json.put("rating", this.getRating());

		json.put("orderCompletionTime", this.getOrderCompletionTime());
		json.put("shippingFees", this.getShippingfees());
		if (getSubOperatorList) {
			// suboperators
			JSONArray jsonSubOperatorList = new JSONArray();
			if (this.getOperatorList() != null) {
				for (User operator : this.getOperatorList()) {
					if (operator.getUserType().getCode().equals("UT000004"))
						jsonSubOperatorList.put(operator.getInfo());
				}
			}
			json.put("subOperatorList", jsonSubOperatorList);
		}
//		json.put("totalProducts", totalProduct);

		/*
		 * Object[] shippingRate = branchLanguageRepository.shippingRate(this.getId());
		 * json.put("shippingRate", shippingRate[0]); // TO get open closed status
		 *
		 * Pageable listing = new PageRequest(0, 1); List<Item> items =
		 * itemRepository.findMostSoldCategory(this.getId(), listing);
		 *
		 * LibraryCategoryLanguage libraryCategoryLanguage = new
		 * LibraryCategoryLanguage(); for (Item item : items) { libraryCategoryLanguage
		 * = libraryCategoryLanguageRepository
		 * .findByLibraryCategoryAndLanguage(item.getLibraryItem().getLibraryCategory(),
		 * language); break; } if (libraryCategoryLanguage != null)
		 * json.put("mostSoldCategory", libraryCategoryLanguage.getTitle()); else
		 * json.put("mostSoldCategory", "-");
		 */
		// String[] openingDay = openingDays.split(",");
		// Boolean isOpen = true;

		return json;
	}

	public JSONObject getInfo(MetaLanguage language, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository, Optional<UserBranch> userBranch, Boolean getSubOperator,
			ItemRepository itemRepository, LibraryCategoryLanguageRepository libraryCategoryLanguageRepository) {
		JSONObject json = new JSONObject();

		//
		BranchLanguage branchLanguage = branchLanguageRepository.findByBranchAndLanguage(this, language);
		double totalProduct = branchLanguageRepository.findTotalProducts(this.getId());

		json.put("id", this.getId());
		if (branchLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", branchLanguage.getTitle());
		}
		if (branchLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", branchLanguage.getDetails());
		}
		if (this.getAddress() == null) {
			json.put("address", "-");
		} else {
			json.put("address", this.getAddress());
		}
		if (this.getFileName() == null) {
			json.put("fileName", "-");
		} else {
			json.put("fileName", this.getFileName());
		}
		if (this.getFileUrl() == null) {
			json.put("fileUrl", "-");
		} else {
			json.put("fileUrl", this.getFileUrl());
		}
		if (this.getLat() == null) {
			json.put("lat", "-");
		} else {
			json.put("lat", this.getLat());
		}
		if (this.getLon() == null) {
			json.put("lon", "-");
		} else {
			json.put("lon", this.getLon());
		}
		if (this.getPhone1() == null) {
			json.put("phone1", "-");
		} else {
			json.put("phone1", this.getPhone1());
		}
		if (this.getPhone2() == null) {
			json.put("phone2", "-");
		} else {
			json.put("phone2", this.getPhone2());
		}
		if (this.getTimeOpen() == null) {
			json.put("timeOpen", "-");
		} else {
			json.put("timeOpen", this.getTimeOpen());
		}
		if (this.getTimeClose() == null) {
			json.put("timeClose", "-");
		} else {
			json.put("timeClose", this.getTimeClose());
		}
		if (this.getMaxKm() == null) {
			json.put("maxKm", "-");
		} else {
			json.put("maxKm", this.getMaxKm());
		}
		if (this.getStatus().getCode() == null) {
			json.put("status", "-");
		} else {
			json.put("status", this.getStatus().getCode());
		}
		json.put("maxOrderTime", this.getMaxOrderTime());
		json.put("shippingFees", this.getShippingfees());
		json.put("is24Hours", this.isIs24hours());
		json.put("hasTakeaway", this.getHasTakeaway());
		json.put("hasDelivery", this.getHasDelivery());
		json.put("rating", this.getRating());
		json.put("currencyInfo", this.getCurrency().getInfo());
		json.put("orderCompletionTime", this.getOrderCompletionTime());
		json.put("totalProducts", totalProduct);
		JSONArray jsonTimeSlots = new JSONArray();
		for (MetaTime time : this.getMetaTimeList()) {
			if (time.getStatus().getCode().equals("STA003"))
				jsonTimeSlots.put(time.getInfo());
		}
		json.put("timeSlots", jsonTimeSlots);

		JSONArray jsonDays = new JSONArray();
		for (BranchDay branchDay : this.getBranchDayList()) {

			jsonDays.put(branchDay.getDay().getInfo());
		}
		json.put("days", jsonDays);
		JSONArray jsonPaymentOptions = new JSONArray();
		for (BranchPayment branchPayment : this.getPaymentList()) {
			if (branchPayment.getStatus().getCode().equals("STA003"))
				jsonPaymentOptions.put(branchPayment.getInfo());
		}

		json.put("paymentOptions", jsonPaymentOptions);
		StringBuilder sb = new StringBuilder();
		JSONArray openingDays = new JSONArray();
		for (BranchDay branchDay : this.getBranchDayList()) {
//
			openingDays.put(branchDay.getDay().getCode());

		}
		// String openingDays = sb.toString();
		json.put("openingDays", openingDays);

		// TO get open closed status
		String currentDay = LocalDate.now().getDayOfWeek().name();

//		String[] openingDay = openingDays.split(",");
		Boolean isOpen = true;
//		for(String day : openingDay) {
//			if((currentDay.equals(day.toUpperCase()) && this.isIs24hours())) {
//				isOpen = true;
//				break;
//			}
//			else if (currentDay.equals(day.toUpperCase())) {
//				LocalTime timeOpen = LocalTime.parse(this.getTimeOpen());
//				LocalTime timeClose = LocalTime.parse(this.getTimeClose());
//				LocalTime currentTime = LocalTime.now();
//				isOpen = ((!currentTime.isBefore(timeOpen) && currentTime.isBefore(timeClose))) ;
//				break;
//			}
//		}
		if (isOpen)
			json.put("isOpen", "Open");
		else
			json.put("isOpen", "Closed");

		// isFavorite

		if (getSubOperator) {
			// suboperators
			JSONArray jsonSubOperatorList = new JSONArray();
			if (this.getOperatorList() != null) {
				for (User operator : this.getOperatorList()) {
					if (operator.getUserType().getCode().equals("UT000004"))
						jsonSubOperatorList.put(operator.getInfo());
				}
			}
			json.put("subOperatorList", jsonSubOperatorList);
		}

		return json;
	}

	public long getDistance(String lat, String lon) {
		int dist = 0;
		JSONObject json = new JSONObject();
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + this.getLat() + ","
					+ this.getLon() + "&destination=" + lat + "," + lon
					+ "&sensor=false&units=metric&mode=driving&key=AIzaSyCrJ7WS_yQbb_Bl9beoBA7z5JH6Y74BLU0");

			final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			InputStream in = new BufferedInputStream(conn.getInputStream());

			BufferedReader rd = new BufferedReader(new InputStreamReader(in), 4096);
			String line;
			sb = new StringBuilder();
			String contents = "{}";

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			contents = sb.toString();
			JSONObject jsonObject = new JSONObject(contents);

			JSONArray array = jsonObject.getJSONArray("routes");
			JSONObject routes = array.getJSONObject(0);
			JSONArray legs = routes.getJSONArray("legs");
			JSONObject steps = legs.getJSONObject(0);
			JSONObject distance = steps.getJSONObject("distance");
			dist = (int) distance.get("value");

			json.put("distance", distance.get("text"));
		} catch (Exception e) {
			json.put("distance", "-");
			dist = 0;
		}

		return dist;
	}


}