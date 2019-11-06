
package ksa.so.web;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import ksa.so.beans.CustomerOrderRequest;
import ksa.so.beans.ItemRequest;
import ksa.so.domain.Branch;
import ksa.so.domain.BranchDay;
import ksa.so.domain.BranchLanguage;
import ksa.so.domain.BundlesItem;
import ksa.so.domain.CloudMessageService;
import ksa.so.domain.CouponRanges;
import ksa.so.domain.CouponUsers;
import ksa.so.domain.CustomerOrder;
import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.DiscountCouponLanguage;
import ksa.so.domain.FlashSale;
import ksa.so.domain.Hq;
import ksa.so.domain.HqLanguage;
import ksa.so.domain.Item;
import ksa.so.domain.ItemLanguage;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemImages;
import ksa.so.domain.LibraryItemLanguage;
import ksa.so.domain.MetaCountry;
import ksa.so.domain.MetaCurrency;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaMessage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.MetaTime;
import ksa.so.domain.MetaUserType;
import ksa.so.domain.OrderItem;
import ksa.so.domain.OrderItemUpdated;
import ksa.so.domain.OrderStatusLog;
import ksa.so.domain.SaleInvoice;
import ksa.so.domain.User;
import ksa.so.domain.Wallet;
import ksa.so.domain.WalletOrder;
import ksa.so.helper.UniqueIdGenerator;
import ksa.so.repositories.BranchDayRepository;
import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.BranchRepository;
import ksa.so.repositories.BundlesItemRepository;
import ksa.so.repositories.CancellationReasonLanguageRepository;
import ksa.so.repositories.CancellationReasonRepository;
import ksa.so.repositories.CartItemRepository;
import ksa.so.repositories.CategoryLanguageRepository;
import ksa.so.repositories.CategoryRepository;
import ksa.so.repositories.ComboItemRepository;
import ksa.so.repositories.ComboItemSubOptRepository;
import ksa.so.repositories.ComboLanguageRepository;
import ksa.so.repositories.ComboRepository;
import ksa.so.repositories.CouponRangesRepository;
import ksa.so.repositories.CouponUsersRepository;
import ksa.so.repositories.CurrencyRepository;
import ksa.so.repositories.CustomerOrderHistoryRepository;
import ksa.so.repositories.CustomerOrderRepository;
import ksa.so.repositories.DiscountCouponLanguageRepository;
import ksa.so.repositories.DiscountCouponRepository;
import ksa.so.repositories.FlashSaleItemRepository;
import ksa.so.repositories.FlashSaleLanguageRepository;
import ksa.so.repositories.FlashSaleRepository;
import ksa.so.repositories.HqLanguageRepository;
import ksa.so.repositories.HqRepository;
import ksa.so.repositories.ItemImagesRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemOptSubOptRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.LibraryCategoryRepository;
import ksa.so.repositories.LibraryItemImagesRepository;
import ksa.so.repositories.LibraryItemLanguageRepository;
import ksa.so.repositories.LibraryItemRepository;
import ksa.so.repositories.MetaCardRepository;
import ksa.so.repositories.MetaCountryRepository;
import ksa.so.repositories.MetaDayLanguageRepository;
import ksa.so.repositories.MetaDayRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaMessageLanguageRepository;
import ksa.so.repositories.MetaMessageRepository;
import ksa.so.repositories.MetaRatingRepository;
import ksa.so.repositories.MetaStatusLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.repositories.MetaTimeRepository;
import ksa.so.repositories.MetaUserTypeRepository;
import ksa.so.repositories.OptLanguageRepository;
import ksa.so.repositories.OrderComboRepository;
import ksa.so.repositories.OrderItemRepository;
import ksa.so.repositories.OrderItemSubOptRepository;
import ksa.so.repositories.OrderItemUpdatedRepository;
import ksa.so.repositories.OrderStatusLogRepository;
import ksa.so.repositories.SaleInvoiceRepository;
import ksa.so.repositories.SalesReportRepository;
import ksa.so.repositories.SubOptLanguageRepository;
import ksa.so.repositories.SubOptRepository;
import ksa.so.repositories.UserAddressRepository;
import ksa.so.repositories.UserAppRepository;
import ksa.so.repositories.UserBranchRepository;
import ksa.so.repositories.UserCardRepository;
import ksa.so.repositories.UserRepository;
import ksa.so.repositories.WalletOrderRepository;
import ksa.so.repositories.WalletRepository;
import ksa.so.service.FileStorageService;

@RestController
@RequestMapping("/api/operator")
public class OperatorController {
	private static final Logger log = LoggerFactory.getLogger(OperatorController.class);

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	LibraryCategoryLanguageRepository libraryCategoryLanguageRepository;

	@Autowired
	BranchLanguageRepository branchLanguageRepository;

	@Autowired
	MetaDayLanguageRepository dayLanguageRepository;

	@Autowired
	OrderStatusLogRepository orderStatusLogRepository;

	@Autowired
	UserAppRepository userAppRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	HqRepository hqRepository;

	@Autowired
	HqLanguageRepository hqLanguageRepository;

	@Autowired
	MetaUserTypeRepository metaUserTypeRepository;

	@Autowired
	UserAddressRepository userAddressRepository;

	@Autowired
	UserCardRepository userCardRepository;

	@Autowired
	UserBranchRepository userBranchRepository;

	@Autowired
	MetaStatusRepository statusRepository;

	@Autowired
	MetaStatusLanguageRepository statusLanguageRepository;

	@Autowired
	MetaLanguageRepository languageRepository;

	@Autowired
	MetaMessageRepository messageRepository;

	@Autowired
	MetaMessageLanguageRepository messageLanguageRepository;

	@Autowired
	MetaCountryRepository countryRepository;

	@Autowired
	MetaCardRepository cardRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryLanguageRepository categoryLanguageRepository;

	@Autowired
	ComboRepository comboRepository;

	@Autowired
	ComboLanguageRepository comboLanguageRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	ComboItemRepository comboItemRepository;

	@Autowired
	ItemLanguageRepository itemLanguageRepository;

	@Autowired
	SubOptRepository subOptRepository;

	@Autowired
	OptLanguageRepository optLanguageRepository;

	@Autowired
	SubOptLanguageRepository subOptLanguageRepository;

	@Autowired
	ComboItemSubOptRepository comboItemSubOptrepository;

	@Autowired
	ItemOptSubOptRepository itemOptSubOptRepository;

	@Autowired
	MetaRatingRepository ratingRepository;

	@Autowired
	MetaDayRepository metaDayRepository;

	@Autowired
	BranchDayRepository branchDayRepository;

	@Autowired
	OrderComboRepository orderComboRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	SaleInvoiceRepository saleInvoiceRepository;

	@Autowired
	OrderItemSubOptRepository orderItemSubOptRepository;

	@Autowired
	CustomerOrderRepository customerOrderRepository;

	@Autowired
	CustomerOrderHistoryRepository customerOrderHistoryRepository;

	@Autowired
	ItemImagesRepository itemImagesRepository;

	@Autowired
	LibraryItemRepository libraryItemRepository;

	@Autowired
	LibraryItemLanguageRepository libraryItemLanguageRepository;

	@Autowired
	OrderItemUpdatedRepository orderItemUpdatedRepository;

	@Autowired
	CancellationReasonLanguageRepository cancellationReasonLanguageRepository;

	@Autowired
	CancellationReasonRepository cancellationReasonRepository;

	@Autowired
	MetaStatusRepository metaStatusRepository;

	@Autowired
	SalesReportRepository salesReportRepository;

	@Autowired
	CouponUsersRepository couponUsersRepository;

	@Autowired
	WalletOrderRepository walletOrderRepository;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	MetaTimeRepository metaTimeRepository;

	@Autowired
	LibraryCategoryRepository libraryCategoryRepository;

	@Autowired
	BundlesItemRepository bundlesItemRepository;

	@Autowired
	DiscountCouponRepository couponRepository;

	@Autowired
	DiscountCouponLanguageRepository couponLanguageRepository;

	@Autowired
	CouponRangesRepository couponRangesRepository;

	@Autowired
	FlashSaleRepository flashSaleRepository;

	@Autowired
	FlashSaleLanguageRepository flashSaleLanguageRepository;

	@Autowired
	FlashSaleItemRepository flashSaleItemRepository;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private LibraryItemImagesRepository libraryItemImagesRepository;

	CloudMessageService cms = new CloudMessageService();

	@Value("${was.version}")
	private String owasVersion;

	@Value("${was.commit}")
	private String commit;

	@Value("${was.messageServer}")
	private String messageServer;

	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.token}")
	private String twilioToken;

	@Value("${twilio.from}")
	private String twilioFrom;

	/**
	 * command signup
	 */
	@RequestMapping(value = "/srs_uop_009", method = RequestMethod.POST)
	public String signup(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/operator/srs_uop_009 - signup - : {}";
		String apiKey = "srs_uop_009";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonUserTypeInfo = jsonData.getJSONObject("userTypeInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");

			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			MetaUserType metaUserType = metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));
			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));

			String phone = jsonUserInfo.getString("phone");
			String token = jsonUserInfo.getString("token");
			// generate code for sms
			String code = randomCode(6);

			User user = new User();

			// select user using phone
			Optional<User> userOptional = userRepository.findByPhoneAndCountry(jsonUserInfo.getString("phone"),
					country);
			JSONObject jsonUserInfoResp = new JSONObject();

			// if (sendSMS(country.getCode() + phone, code)) {
			if (!userOptional.isPresent()) {
				jsonUserInfoResp.put("phone", phone);
				jsonUserInfoResp.put("otp", code);
				jsonUserInfoResp.put("countryInfo", country.getInfo());
				jsonUserInfoResp.put("password", "-");
				jsonUserInfoResp.put("username", "-");
				jsonUserInfoResp.put("userTypeInfo", metaUserType.getCode());
				// user = new UserApp();
				// user.setCountry(country);
				// user.setGender("Male");
				// user.setFirstName("-");
				// user.setLastName("-");
				// user.setUsername("-");
				// user.setPassword("-");
				// user.setPhone(phone);
			} else {
				user = userOptional.get();

				if (user.getStatus().getCode().equals("STA004")) {
					JSONObject json = validateUserObject(apiKey, warn, "user disabled", language, "MSG006", "error");
					return json.toString();
				}
			}

			user.setPhone(phone);
			user.setCountry(country);
			user.setUserType(metaUserType);
			user.setInstallationId(token);
			user.setStatus(statusRepository.findByCode("STA001"));
			user.setOtp(code);
			userRepository.save(user);

			jsonUserInfoResp = user.getInfo(language, hqLanguageRepository);

			/*
			 * } else { JSONObject json = validateUserObject(apiKey, warn,
			 * "error sending sms", language, "MSG007", "warn"); return json.toString(); }
			 */

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("userAppInfo", jsonUserInfoResp);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command add user and store
	 */
	@RequestMapping(value = "/srs_uop_010", method = RequestMethod.POST)
	public String addUserAndStore(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate,
			HttpSession session) throws Exception {
		String warn = "/api/user/srs_uop_010 - addUserAndStore - : {}";
		String apiKey = "srs_uop_010";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonUserTypeInfo = jsonData.getJSONObject("userTypeInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonStoreInfo = jsonData.getJSONObject("storeInfo");

			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			MetaUserType metaUserType = metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));
			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));

			String phone = jsonUserInfo.getString("phone");
			String token = jsonUserInfo.getString("token");
			String otp = jsonUserInfo.getString("otp");
			String email = jsonUserInfo.getString("email");
			String username = jsonUserInfo.getString("username");
			String password = jsonUserInfo.getString("password");

			String address = jsonStoreInfo.getString("address");
			String title = jsonStoreInfo.getString("title");

			// add store
			Hq hq = new Hq();
			hq.setStatus(statusRepository.findByCode("STA003"));
			hq.setAddress(address);
			hqRepository.save(hq);

			// add store details
			List<MetaLanguage> languageList = languageRepository.findAll();
			hq = hqRepository.findOne(hq.getId());
			for (MetaLanguage lang : languageList) {
				HqLanguage hqLanguage = new HqLanguage();
				hqLanguage.setHq(hq);
				hqLanguage.setTitle(title);
				hqLanguage.setLanguage(lang);
				hqLanguageRepository.save(hqLanguage);
			}

			// add user
			User user = new User();
			String sessionId = session.getId();
			user.setInstallationId(token);
			user.setStatus(statusRepository.findByCode("STA001"));
			user.setOtp(otp);
			user.setHq(hq);
			user.setPhone(phone);
			user.setUserType(metaUserType);
			user.setEmail(email);
			user.setCountry(country);
			user.setUsername(username);
			user.setPassword(password);
			user.setSessionId(sessionId);
			userRepository.save(user);

			JSONObject jsonUserInfoResp = user.getInfo(language, statusLanguageRepository);
			jsonUserInfoResp.put("storeInfo", hq.getInfo(language, hqLanguageRepository));

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("userAppInfo", jsonUserInfoResp);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command add branch
	 */
	@RequestMapping(value = "/srs_uop_011", method = RequestMethod.POST)
	public String addBranch(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/operator/srs_uop_011 - addBranch - : {}";
		String apiKey = "srs_uop_011";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonStoreInfo = jsonUserInfo.getJSONObject("storeInfo");
			JSONObject jsonbranchInfo = jsonData.getJSONObject("branchInfo");
			JSONObject jsonCurrencyInfo = jsonbranchInfo.getJSONObject("currencyInfo");
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));
			User user = new User();

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			user = userOptional.get();

			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));
			MetaCurrency currency = currencyRepository.findOne(jsonCurrencyInfo.getLong("id"));
			Hq hq = hqRepository.findOne(jsonStoreInfo.getLong("id"));

			Long id = jsonbranchInfo.getLong("id");
			String address = jsonbranchInfo.getString("address");
			Boolean hasDelivery = jsonbranchInfo.getBoolean("hasDelivery");
			Boolean hasTakeaway = jsonbranchInfo.getBoolean("hasTakeaway");
			String timeOpen = jsonbranchInfo.getString("timeOpen");
			String timeClose = jsonbranchInfo.getString("timeClose");
			String phone1 = jsonbranchInfo.getString("phone1");
			String phone2 = jsonbranchInfo.getString("phone2");
			Long orderCompletionTime = jsonbranchInfo.getLong("orderCompletionTime");
			Boolean is24Hours = Boolean.valueOf(jsonbranchInfo.getString("is24Hours"));
			String lat = jsonbranchInfo.getString("lat");
			String lon = jsonbranchInfo.getString("lon");
			String fileName = jsonbranchInfo.getString("fileName");
			String fileUrl = jsonbranchInfo.getString("fileUrl");
			String title = jsonbranchInfo.getString("title");
			String details = jsonbranchInfo.getString("details");
			Double shippingFees = jsonbranchInfo.getDouble("shippingFees");
			String maxOrderTime = jsonbranchInfo.getString("maxOrderTime");
			String maxKm = jsonbranchInfo.getString("maxKm");
			String metaTimeSlotStart = jsonbranchInfo.getString("metaTimeSlotStart");
			String metaTimeSlotEnd = jsonbranchInfo.getString("metaTimeSlotEnd");
			JSONArray jsonBranchDaysAdded = jsonbranchInfo.getJSONArray("daysInfoListAdded");
			JSONArray jsonBranchDaysRemoved = jsonbranchInfo.getJSONArray("daysInfoListRemoved");

			// add branch
			Branch branch = new Branch();
			if (id != 0) {
				branch = branchRepository.findOne(id);
			}
			branch.setStatus(statusRepository.findByCode("STA110"));
			branch.setAddress(address);
			branch.setHasDelivery(hasDelivery);
			branch.setHasTakeaway(hasTakeaway);
			branch.setIs24hours(is24Hours);
			branch.setCountry(country);
			branch.setCurrency(currency);
			branch.setCreatedBy(user);
			branch.setCreatedAt(statusRepository.getCurrentTime());
			branch.setHq(hq);
			branch.setLat(lat);
			branch.setLon(lon);
			branch.setTimeClose(timeClose);
			branch.setTimeOpen(timeOpen);
			branch.setOrderCompletionTime(orderCompletionTime);
			branch.setPhone1(phone1);
			branch.setPhone2(phone2);
			branch.setFileName(fileName);
			branch.setFileUrl("http://ca1.risknucleus.com:47/SmartCart/Images/Stores/" + fileUrl);
			branch.setShippingfees(shippingFees);
			branch.setRating(1);
			branch.setMaxOrderTime(maxOrderTime);
			branch.setMaxKm(maxKm);
			branchRepository.save(branch);

			branch = branchRepository.findOne(branch.getId());

			// add branch details
			List<MetaLanguage> languageList = languageRepository.findAll();
			List<BranchLanguage> branchLanguageList = new ArrayList<BranchLanguage>();
			for (MetaLanguage lang : languageList) {
				BranchLanguage branchLanguage;
				if (id == 0) {
					branchLanguage = new BranchLanguage();
					branchLanguage.setBranch(branch);
					branchLanguage.setLanguage(lang);
				} else {
					branchLanguage = branchLanguageRepository.findByBranchAndLanguage(branch, language);
				}
				branchLanguage.setTitle(title);
				branchLanguage.setDetails(details);
				branchLanguageRepository.save(branchLanguage);
				branchLanguageList.add(branchLanguage);
			}
			branch.setBranchLanguageList(branchLanguageList);

			for (int i = 0; i < jsonBranchDaysRemoved.length(); i++) {
				BranchDay branchDay = branchDayRepository.findByBranchAndDay(branch,
						metaDayRepository.findByCode(((JSONObject) jsonBranchDaysRemoved.get(i)).getString("code")));
				branchDayRepository.delete(branchDay.getId());
			}
			// add branch days
			for (int i = 0; i < jsonBranchDaysAdded.length(); i++) {
				BranchDay branchDay = new BranchDay();
				branchDay.setBranch(branch);
				branchDay.setDay(
						metaDayRepository.findByCode(((JSONObject) jsonBranchDaysAdded.get(i)).getString("code")));
				branchDayRepository.save(branchDay);
			}

			//
			List<BranchDay> branchDayList = branchDayRepository.findByBranch(branch);
			branch.setBranchDayList(branchDayList);
			// add branch operators
			MetaTime branchTime = new MetaTime();
			branchTime.setStartTime(metaTimeSlotStart);
			branchTime.setEndTime(metaTimeSlotEnd);
			branchTime.setBranch(branch);
			branchTime.setStatus(statusRepository.findByCode("STA003"));
			metaTimeRepository.save(branchTime);
			List<MetaTime> branchTimeList = metaTimeRepository.findByBranch(branch);
			branch.setMetaTimeList(branchTimeList);
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("branchInfo", branch.getInfo(language, branchLanguageRepository, dayLanguageRepository, "0.0",
					"0.0", itemRepository, libraryCategoryLanguageRepository));
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command get branch list in store
	 */
	@RequestMapping(value = "/srs_uop_012", method = RequestMethod.POST)
	public String getBranchList(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uop_012 - getBranchList - : {}";
		String apiKey = "srs_uop_012";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			// JSONObject jsonUserTypeInfo = jsonData.getJSONObject("userTypeInfo");
			// JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonStoreInfo = jsonData.getJSONObject("storeInfo");

			// MetaCountry country =
			// countryRepository.findOne(jsonCountryInfo.getLong("id"));
			Hq hq = hqRepository.findOne(jsonStoreInfo.getLong("id"));

			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			// select user using username
			Optional<User> userOptionals = userRepository.findBySessionidAndUsername(
					jsonUserInfo.getString("sessionid"), jsonUserInfo.getString("username"));

			if (!userOptionals.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			User user = userOptionals.get();

			if (user.getStatus().getCode().equals("STA004")) {
				JSONObject json = validateUserObject(apiKey, warn, "user disabled", language, "MSG006", "error");
				return json.toString();
			}

			// get the list of stores
			MetaStatus status = statusRepository.findByCode("STA003");
			// List<Branch> branchList = branchRepository.findByStatusAndHq(status, hq);
			List<Branch> branchList = branchRepository.findByHq(hq);
			JSONArray branchListJson = new JSONArray();
			for (Branch branch : branchList) {
				branchListJson.put(branch.getInfo(language, branchLanguageRepository, dayLanguageRepository, "0.0",
						"0.0", null, false, itemRepository, libraryCategoryLanguageRepository));
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("branchInfoList", branchListJson);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	@RequestMapping(value = "/srs_uop_022", method = RequestMethod.POST)
	public String getIntermediateBranchList(@RequestBody ItemRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_022 - getBranchList - : {}";
		String apiKey = "srs_uop_022";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			// JSONObject jsonUserTypeInfo = jsonData.getJSONObject("userTypeInfo");
			// JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");

			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			// select user using username
			Optional<User> userOptionals = userRepository.findBySessionidAndUsername(
					jsonUserInfo.getString("sessionid"), jsonUserInfo.getString("username"));

			if (!userOptionals.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			User user = userOptionals.get();

			if (user.getStatus().getCode().equals("STA004")) {
				JSONObject json = validateUserObject(apiKey, warn, "user disabled", language, "MSG006", "error");
				return json.toString();
			}

			// get the list of stores
			List<MetaStatus> metaStatusList = new ArrayList<MetaStatus>();
			metaStatusList.add(statusRepository.findByCode("STA108"));
			metaStatusList.add(statusRepository.findByCode("STA110"));
			// MetaStatus status = statusRepository.findByCode("STA108");
			List<Branch> branchList = branchRepository.findByStatusIn(metaStatusList);
			JSONArray branchListJson = new JSONArray();
			for (Branch branch : branchList) {
				branchListJson.put(branch.getInfo(language, branchLanguageRepository, dayLanguageRepository, "0.0",
						"0.0", null, false, itemRepository, libraryCategoryLanguageRepository));
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("branchInfoList", branchListJson);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command login
	 */
	@RequestMapping(value = "/srs_uop_001", method = RequestMethod.POST)
	public String login(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate,
			HttpSession session) throws Exception {
		String warn = "/api/user/srs_uop_001 - login - : {}";
		String apiKey = "api/user/srs_uop_001";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONArray jsonUserTypeInfoList = jsonData.getJSONArray("userTypeInfoList");

			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			String password = jsonUserInfo.getString("password");
			String token = jsonUserInfo.getString("token");

			List<MetaUserType> metaUserTypeList = new ArrayList<MetaUserType>();
			for (int i = 0; i < jsonUserTypeInfoList.length(); i++) {
				String code = jsonUserTypeInfoList.getJSONObject(i).getString("code");
				metaUserTypeList.add(metaUserTypeRepository.findByCode(code));
			}

			// MetaUserType userType =
			// metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));
			// select user using username
			// Optional<User> userOptional =
			// userRepository.findByUsernameAndUserType(jsonUserInfo.getString("username"),
			// userType);
			Optional<User> userOptional = userRepository.findByUsernameAndUserTypeIn(jsonUserInfo.getString("username"),
					metaUserTypeList);

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			// for encoded Password in Database
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			User user = new User();
			user = userOptional.get();
			if (!passwordEncoder.matches(password, user.getPassword())) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid password", language, "MSG005", "warn");
				return json.toString();
			}

			if (user.getStatus().getCode().equals("STA004")) {
				JSONObject json = validateUserObject(apiKey, warn, "user disabled", language, "MSG005", "error");
				return json.toString();
			}

			if (user.getUserType().getCode().equals("UT000003")) {
				String notification_key = "";
				String[] registration_ids = { token };

				if (user.getInstallationId() == null || user.getInstallationId().equals("-")) {
					notification_key = cms.createDeviceGroup(user.getId().toString(), registration_ids, true);
					if (notification_key == null) {
						notification_key = cms.retrieveDeviceGroup(user.getId().toString(), true);
						notification_key = cms.addDeviceToGroup(user.getId().toString(), notification_key,
								registration_ids, true);
					}
				}

				else {
					notification_key = cms.addDeviceToGroup(user.getId().toString(), user.getInstallationId(),
							registration_ids, true);
				}

				if (notification_key == null) {
					user.setInstallationId(token);
				} else {
					user.setInstallationId(notification_key);
				}
			} else {
				user.setInstallationId(token);
			}
			JSONObject jsonUserAppInfo = new JSONObject();
			user.setStatus(statusRepository.findByCode("STA001"));
			if (user.getUserType().getCode().equals("UT000001")) {
				jsonUserAppInfo = user.getInfo(language);
				userRepository.save(user);
			} else {
				if (!user.getUserType().getCode().equals("UT000003")) {
					user.setSessionId(session.getId());
				}

				else if (user.getSessionId() == null || user.getSessionId().equals("-")) {
					user.setSessionId(session.getId());
				}

				Boolean getSubOperator = false;

				if (user.getUserType().getCode().equals("UT000002")) {
					jsonUserAppInfo = user.getInfo(language, hqLanguageRepository);
				} else {
					if (user.getUserType().getCode().equals("UT000003")) {
						getSubOperator = true;
					}

					jsonUserAppInfo = user.getInfo(language, statusLanguageRepository, branchLanguageRepository,
							dayLanguageRepository, getSubOperator, itemRepository, libraryCategoryLanguageRepository);
				}
				userRepository.save(user);
			}

			// JSONArray jsonOrderList = new JSONArray();
			// List<CustomerOrder> orderList =
			// customerOrderRepository.findByBranchAndStatus(user.getBranch(),
			// statusRepository.findByCode("STA101"));
			// for(CustomerOrder order : orderList) {
			// jsonOrderList.put(order.getInfo(language, statusRepository,
			// statusLanguageRepository, itemLanguageRepository, branchLanguageRepository,
			// dayLanguageRepository));
			// }

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("userAppInfo", jsonUserAppInfo);
			// data.put("jsonOrderInfo", jsonOrderList);
			json.put("data", data);
			json.put("key", "srs_uop_001");

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uop_001");
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command get order list
	 */
	@RequestMapping(value = "/srs_uop_002", method = RequestMethod.POST)
	public String getOrderList(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_002 - get order list - : {}";
		String apiKey = "api/user/srs_uop_002";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			User user = (User) authenticate.getPrincipal();
			Boolean isOperator = true;
			if (user.getUserType().getCode().equals("UT000004"))
				isOperator = false;

			Pageable listing = new PageRequest(param.getListStart(), param.getListSize());

			boolean ammendedOrders = false;

			// JSONObject jsonUserAppInfo = user.getInfo(language, statusLanguageRepository,
			// branchLanguageRepository, dayLanguageRepository);
			JSONArray jsonOrderList = new JSONArray();
			List<MetaStatus> metaStatusList = statusRepository.findByCodeIn(param.getMetaStatusListInfo());
			for (MetaStatus metaStatus : metaStatusList) {
				if (metaStatus.getCode().equals("STA107")) {
					ammendedOrders = true;
				}
			}

			List<CustomerOrder> orderList = new ArrayList<CustomerOrder>();
			List<Branch> branchList = branchRepository.findByStatusAndHq(statusRepository.findByCode("STA003"),
					user.getBranch().getHq());

			if (isOperator) {
				if (param.getSearchTerm() != null)
orderList = customerOrderRepository.findByBranchInAndStatusInAndOrderNumberOrderByTsServerDesc(
								branchList, metaStatusList, "%" + param.getSearchTerm() + "%", param.getOperatorList(),
								listing);
				else
					orderList = customerOrderRepository.findByBranchInAndStatusInOrderByTsServerDesc(branchList,
							metaStatusList, listing);
			} else {

				if (param.getSearchTerm() != null)
					orderList = customerOrderRepository
							.findByBranchInAndSubOperatorAndStatusInAndOrderNumberOrderByTsServerDesc(branchList,
									user.getId(), metaStatusList, "%" + param.getSearchTerm() + "%", listing);
				else
					orderList = customerOrderRepository.findByBranchInAndSubOperatorAndStatusInOrderByTsServerDesc(
							branchList, user, metaStatusList, listing);
			}
			String timeStamp = param.getTimeStamp();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(timeStamp);
			String sdate = dateFormat.format(date);
			Date stime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp);
			String time = new SimpleDateFormat("H:mm:ss").format(stime);

			FlashSale flashSale = new FlashSale();
			flashSale = flashSaleRepository.findSale((long) 3, time, sdate);
			Boolean isFlashSale = false;
			if (flashSale != null) {
				isFlashSale = true;
			}
			for (CustomerOrder order : orderList) {
				jsonOrderList.put(order.getInfo(language, statusRepository, statusLanguageRepository,
						itemLanguageRepository, branchLanguageRepository, dayLanguageRepository, false, true,
						orderStatusLogRepository, orderItemRepository, ammendedOrders, orderItemUpdatedRepository,
						itemRepository, libraryCategoryLanguageRepository, cancellationReasonLanguageRepository,
						walletRepository, isFlashSale, flashSale, flashSaleItemRepository));
			}
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("jsonOrderInfo", jsonOrderList);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uop_001");
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command get order info
	 */
	@RequestMapping(value = "/srs_uop_008", method = RequestMethod.POST)
	public String getOrderInfo(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_008 - get order info - : {}";
		String apiKey = "api/user/srs_uop_008";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			String timeStamp = param.getTimeStamp();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(timeStamp);
			String sdate = dateFormat.format(date);
			Date stime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp);
			String time = new SimpleDateFormat("H:mm:ss").format(stime);

			FlashSale flashSale = new FlashSale();
			flashSale = flashSaleRepository.findSale((long) 3, time, sdate);
			Boolean isFlashSale = false;
			if (flashSale != null) {
				isFlashSale = true;
			}
			CustomerOrder order = customerOrderRepository.findOne(param.getOrderInfo().getId());
			JSONArray jsonOrderList = new JSONArray();
			jsonOrderList.put(order.getInfo(language, statusRepository, statusLanguageRepository,
					itemLanguageRepository, branchLanguageRepository, dayLanguageRepository, true, true,
					orderStatusLogRepository, orderItemRepository,
					(order.getStatus().getCode().equals("STA107") ? true : false), orderItemUpdatedRepository,
					itemRepository, libraryCategoryLanguageRepository, cancellationReasonLanguageRepository,
					walletRepository, isFlashSale, flashSale, flashSaleItemRepository));

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("jsonOrderInfo", jsonOrderList);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command search order list
	 */
	@RequestMapping(value = "/srs_uop_003", method = RequestMethod.POST)
	public String searchOrderList(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uop_003 - searchOrderList - : {}";
		String apiKey = "api/user/srs_uop_003";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("operatorAppInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonUserTypeInfo = jsonUserInfo.getJSONObject("usertype");
			JSONObject jsonStatusInfo = jsonData.getJSONObject("statusInfo");
			JSONObject jsonSearchTerm = jsonData.getJSONObject("searchTerm");

			MetaUserType userType = metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			String searchTerm = jsonSearchTerm.getString("searchTerm");
			String regex = "\\d+";

			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			User user = new User();
			user = userOptional.get();

			// JSONObject jsonUserAppInfo = user.getInfo(language, statusLanguageRepository,
			// branchLanguageRepository, dayLanguageRepository);

			// List<MetaStatus> metaStatusList = new ArrayList<MetaStatus>();
			// for(int i = 0 ; i < jsonStatusInfoList.length() ; i++) {
			// String code = jsonStatusInfoList.getJSONObject(i).getString("code");
			// metaStatusList.add(statusRepository.findByCode(code));
			// }
			// List<CustomerOrder> orderList =
			// customerOrderRepository.findByBranchAndStatusIn(user.getBranch(),
			// metaStatusList);

			MetaStatus metaStatus = statusRepository.findByCode(jsonStatusInfo.getString("code"));

			List<CustomerOrder> orderList = new ArrayList<CustomerOrder>();
			if (searchTerm.matches(regex))
				orderList = customerOrderRepository.findByBranchAndStatusInAndIdContaining(Long.parseLong(searchTerm),
						user.getBranch().getId(), metaStatus.getId());
			else
				orderList = customerOrderRepository.findByBranchAndStatusInAndAndUserContaining("%" + searchTerm + "%",
						user.getBranch().getId(), metaStatus.getId());
			JSONArray jsonOrderList = new JSONArray();
			String timeStamp = jsonData.getString("timeStamp");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(timeStamp);
			String sdate = dateFormat.format(date);
			Date stime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp);
			String time = new SimpleDateFormat("H:mm:ss").format(stime);

			FlashSale flashSale = new FlashSale();
			flashSale = flashSaleRepository.findSale((long) 3, time, sdate);
			Boolean isFlashSale = false;
			if (flashSale != null) {
				isFlashSale = true;
			}
			for (CustomerOrder order : orderList) {
				jsonOrderList.put(order.getInfo(language, statusRepository, statusLanguageRepository,
						itemLanguageRepository, branchLanguageRepository, dayLanguageRepository, true, true,
						orderStatusLogRepository, orderItemRepository, false, null, itemRepository,
						libraryCategoryLanguageRepository, cancellationReasonLanguageRepository, walletRepository,
						isFlashSale, flashSale, flashSaleItemRepository));
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("jsonOrderInfo", jsonOrderList);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command assign order
	 */
	@RequestMapping(value = "/srs_uop_006", method = RequestMethod.POST)
	public String assignOrder(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_006 - assign order - : {}";
		String apiKey = "api/user/srs_uop_006";
		String key = "srs_uop_006";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			MetaStatus status = metaStatusRepository.findByCode(param.getMetaStatus().getCode());
			User subOperator = userRepository.findOne(param.getUser().getId());
			if (subOperator == null) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid sub operator", language, "MSG005", "warn");
				return json.toString();
			}

			CustomerOrder order = customerOrderRepository.findOne(param.getOrderInfo().getId());

			if (order.getStatus().equals(status)) {
				JSONObject json = setResponse(apiKey, warn, "order already assigned", language, "MSG601", "warn");
				return json.toString();
			}

			order.setStatus(status);
			order.setSubOperator(subOperator);
			customerOrderRepository.save(order);

			OrderStatusLog orderStatusLog = new OrderStatusLog();
			orderStatusLog.setOrder(order);
			orderStatusLog.setStatus(status);
			orderStatusLog.setTsClient(Timestamp.valueOf(param.getTimeStamp()));
			orderStatusLog.setTsServer(statusRepository.getCurrentTime());
			orderStatusLogRepository.save(orderStatusLog);

			MetaMessage msg = messageRepository.findByCode("MSG001");
			/*
			 * MetaMessage notificationMsg = messageRepository.findByCode("MSG011"); String
			 * title = messageLanguageRepository.findByMessageAndLanguage(notificationMsg,
			 * language).getTitle(); /* MetaMessage notificationMsgBoby = new MetaMessage();
			 * if (order.getStatus().getCode().equals("STA102")) notificationMsgBoby =
			 * messageRepository.findByCode("MSG101"); else if
			 * (order.getStatus().getCode().equals("STA103")) notificationMsgBoby =
			 * messageRepository.findByCode("MSG102"); else if
			 * (order.getStatus().getCode().equals("STA104")) notificationMsgBoby =
			 * messageRepository.findByCode("MSG103"); else if
			 * (order.getStatus().getCode().equals("STA106")) notificationMsgBoby =
			 * messageRepository.findByCode("MSG106");
			 *
			 * String body =
			 * messageLanguageRepository.findByMessageAndLanguage(notificationMsgBoby,
			 * language).getTitle() .replace(":orderid", order.getId().toString());
			 *
			 * JSONObject jsonNotification = new JSONObject(); JSONObject orderStatus = new
			 * JSONObject(); orderStatus.put("orderId", order.getId());
			 * orderStatus.put("statusLog", orderStatusLog.getInfo(language,
			 * statusLanguageRepository)); orderStatus.put("status",
			 * order.getStatus().getInfo(language, statusLanguageRepository));
			 * orderStatus.put("key", "srs_uop_006"); orderStatus.put("status",
			 * order.getStatus().getCode()); orderStatus.put("title", title);
			 * orderStatus.put("body", body); jsonNotification.put("orderStatus",
			 * orderStatus);
			 *
			 * JSONObject notification = new JSONObject(); notification.put("title", title);
			 * notification.put("body", body); notification.put("sound", "default");
			 * notification.put("click_action", "FCM_PLUGIN_ACTIVITY");
			 *
			 * String deviceToken = ""; if (order.getStatus().getCode().equals("STA106"))
			 * deviceToken = order.getSubOperator().getInstallationId(); else { //
			 * deviceToken = operator.getInstallationId(); deviceToken =
			 * order.getUser().getInstallationId(); } cms.sendMessage(deviceToken,
			 * jsonNotification, notification, (order.getStatus().getCode().equals("STA106")
			 * ? true : false));
			 */

			sendChangeOrderStatusNotification(order, orderStatusLog, language, key);
			JSONObject data = new JSONObject();
			JSONObject json = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			MetaLanguage language = languageRepository.findByCode("LANG0001");
			JSONObject catchjson = validateUserObject(apiKey, warn, e.getMessage(), language, "MSG003", "error");
			return catchjson.toString();
		}
	}

	/**
	 * command cancel order
	 */
	@RequestMapping(value = "/srs_uop_007", method = RequestMethod.POST)
	public String CancelOrder(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_007 - cancel order - : {}";
		String apiKey = "api/user/srs_uop_007";
		String key = "srs_uop_007";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			MetaStatus status = statusRepository.findByCode(param.getMetaStatus().getCode());

			CustomerOrder order = customerOrderRepository.findOne(param.getOrderInfo().getId());
			if (!order.getStatus().getCode().equals("STA105")) {
				CouponUsers couponUser = new CouponUsers();
				couponUser = couponUsersRepository.findByUserAndDiscountCoupon(order.getUser(),
						order.getDiscountCoupon());
				if (couponUser != null) {
					double newAmountUsed = 0.0;
					newAmountUsed = couponUser.getAmountUsed() - order.getDiscountedAmount();
					couponUser.setAmountUsed(newAmountUsed);
				}

				Wallet wallet = walletRepository.findByUser(order.getUser());
				if (wallet != null) {
					wallet.setWalletAmount(wallet.getWalletAmount() + order.getWalletAmountUsed());
					walletRepository.save(wallet);
				}
			}
			order.setStatus(status);
			// order.setCancellationReason(cancellationReason);
			// order.setCancelledBy(userOptional.get());
			customerOrderRepository.save(order);
			List<OrderItem> orderItem = orderItemRepository.findByOrder(order);
			List<Item> items = new ArrayList<Item>();
			for (int i = 0; i < orderItem.size(); i++) {
				Item item = orderItem.get(i).getItem();
				item.setQuantity(item.getQuantity() + orderItem.get(i).getQuantity());
				item.setUpdated(Timestamp.valueOf(param.getTimeStamp()));
				items.add(item);
			}
			if (items.size() > 0) {
				itemRepository.save(items);
			}

			OrderStatusLog orderStatusLog = new OrderStatusLog();
			orderStatusLog.setOrder(order);
			orderStatusLog.setStatus(status);
			orderStatusLog.setTsClient(Timestamp.valueOf(param.getTimeStamp()));
			orderStatusLog.setTsServer(statusRepository.getCurrentTime());
			orderStatusLogRepository.save(orderStatusLog);

			MetaMessage msg = messageRepository.findByCode("MSG001");

			sendChangeOrderStatusNotification(order, orderStatusLog, language, key);
			JSONObject data = new JSONObject();
			JSONObject json = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command add operator and suboperator
	 */
	@RequestMapping(value = "/srs_uop_013", method = RequestMethod.POST)
	public String addOperatorandsuboperator(@RequestBody ItemRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/operators/srs_uop_013 - addOperatorandSuboperator - : {}";
		String apiKey = "srs_uop_013";
		try {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonOperatorInfo = jsonData.getJSONObject("operatorInfo");
			JSONObject jsonUserTypeInfo = jsonOperatorInfo.getJSONObject("userTypeInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonBranchInfo = jsonData.getJSONObject("branchInfo");
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			MetaUserType metaUserType = metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));

			Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));

			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			MetaCountry country = branch.getCountry();
			String password;
			String username = jsonOperatorInfo.getString("username");
			String phone = jsonOperatorInfo.getString("phone");
			String name = jsonOperatorInfo.getString("name");
			String email = jsonOperatorInfo.getString("email");
			Long id = jsonOperatorInfo.getLong("id");
			if (id == 0) {
				password = jsonOperatorInfo.getString("password");
			}

			// add user
			User user = new User();

			if (id != 0) {
				user = userRepository.findOne(id);
			}
			userOptional = null;
			userOptional = userRepository.findByUsername(username);
			if (!userOptional.isPresent() || id != 0) {
				user.setStatus(statusRepository.findByCode("STA001"));
				user.setUserType(metaUserType);
				Long userid = user.getId();
				if (id != userid && userOptional.isPresent()) {
					log.warn("/api/dashboard/srs_uop_013 - addOperatorandSuboperator - : {}",
							"Username already exists");
					MetaMessage msg = messageRepository.findByCode("MSG503");

					JSONObject json = new JSONObject();
					JSONObject data = new JSONObject();
					JSONObject message = new JSONObject();
					message.put("type", "warn");
					message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
					data.put("message", message);
					json.put("data", data);
					json.put("key", apiKey);
					return json.toString();
				} else {
					user.setUsername(username);
				}
				if (jsonOperatorInfo.has("password")) {
					password = jsonOperatorInfo.getString("password");
					user.setPassword(password);
				}
				user.setName(name);
				user.setPhone(phone);
				user.setBranch(branch);
				user.setCountry(country);
				user.setEmail(email);
				userRepository.save(user);
				MetaMessage msg = messageRepository.findByCode("MSG001");

				JSONArray BranchopandsubopListJson = new JSONArray();
				List<MetaUserType> metaUserTypeList = new ArrayList<MetaUserType>();
				JSONArray jsonUserTypeInfoList = jsonData.getJSONArray("userTypeInfoList");
				for (int i = 0; i < jsonUserTypeInfoList.length(); i++) {
					String code = jsonUserTypeInfoList.getJSONObject(i).getString("code");
					metaUserTypeList.add(metaUserTypeRepository.findByCode(code));
				}

				List<User> userList = userRepository.findByUserTypeInAndBranch(metaUserTypeList, branch);

				for (User userL : userList) {
					BranchopandsubopListJson.put(userL.getInfo(language, statusLanguageRepository));
				}

				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "ack");
				message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
				data.put("users", BranchopandsubopListJson);
				data.put("message", message);

				json.put("data", data);
				json.put("key", apiKey);
				return json.toString();
			} else {
				log.warn("/api/dashboard/srs_uop_013 - addOperatorandSuboperator - : {}", "Username already exists");
				MetaMessage msg = messageRepository.findByCode("MSG503");

				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "warn");
				message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
				data.put("message", message);
				json.put("data", data);
				json.put("key", apiKey);

				return json.toString();
			}

		} catch (Exception e) {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			MetaLanguage language = languageRepository.findByCode("LANG0001");
			JSONObject catchjson = validateUserObject(apiKey, warn, e.getMessage(), language, "MSG003", "error");
			return catchjson.toString();
		}
	}

	/**
	 * GET Operators and Sub Operators w.r.t branch
	 */
	@RequestMapping(value = "/srs_uop_014", method = RequestMethod.POST)
	public String getoperatorandsuboperator(@RequestBody ItemRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "api/operators/srs_uop_014 - getoperatorandsuboperator - : {}";
		String apiKey = "srs_uop_014";
		try {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonBranchInfo = jsonUserInfo.getJSONObject("branchInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));
			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));
			JSONArray BranchopandsubopListJson = new JSONArray();
			List<MetaUserType> metaUserTypeList = new ArrayList<MetaUserType>();
			JSONArray jsonUserTypeInfoList = jsonData.getJSONArray("userTypeInfoList");
			for (int i = 0; i < jsonUserTypeInfoList.length(); i++) {
				String code = jsonUserTypeInfoList.getJSONObject(i).getString("code");
				metaUserTypeList.add(metaUserTypeRepository.findByCode(code));
			}

			List<User> userList = userRepository.findByUserTypeInAndBranch(metaUserTypeList, branch);

			MetaMessage msg = messageRepository.findByCode("MSG001");
			for (User users : userList) {
				BranchopandsubopListJson.put(users.getInfo(language, statusLanguageRepository));
			}

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("users", BranchopandsubopListJson);
			json.put("data", data);
			json.put("key", apiKey);
			return json.toString();
		} catch (Exception e) {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			MetaLanguage language = languageRepository.findByCode("LANG0001");
			JSONObject catchjson = validateUserObject(apiKey, warn, e.getMessage(), language, "MSG003", "error");
			return catchjson.toString();
		}
	}

	/**
	 * SAVE AND EDIT PRODUCT
	 */
	@RequestMapping(value = "/srs_uop_021", method = RequestMethod.POST)
	public String SaveProducts(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "api/operators//srs_uop_021 - getoperatorandsuboperator - : {}";
		String apiKey = "/srs_uop_021";
		try {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonBranchInfo = jsonUserInfo.getJSONObject("branchInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonItemInfo = jsonData.getJSONObject("itemInfo");
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));
			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));

			String title = jsonItemInfo.getString("title");
			String details = jsonItemInfo.getString("details");
			Double price = jsonItemInfo.getDouble("price");
			Long quantity = jsonItemInfo.getLong("quantity");
			Double cost = jsonItemInfo.getDouble("cost");
			String sku = jsonItemInfo.getString("sku");
			String keyFeatures = jsonItemInfo.getString("keyFeatures");
			String brand = jsonItemInfo.getString("brand");
			LibraryItem libraryItem = new LibraryItem();
			if (jsonItemInfo.has("libraryId"))
				libraryItem = libraryItemRepository.findOne(jsonItemInfo.getLong("libraryId"));
			if (jsonItemInfo.has("libraryId")) {
				libraryItem.setStatus(statusRepository.findByCode("STA003"));
			} else {
				libraryItem.setStatus(statusRepository.findByCode("STA004"));
				libraryItem.setLibraryCategory(
						libraryCategoryRepository.findOne(jsonItemInfo.getLong("libraryCategoryId")));
			}
			libraryItemRepository.save(libraryItem);

			List<MetaLanguage> languageList = languageRepository.findAll();

			List<LibraryItemLanguage> libraryItemLanguageList = new ArrayList<LibraryItemLanguage>();
			for (MetaLanguage lang : languageList) {

				LibraryItemLanguage libraryItemLanguage = new LibraryItemLanguage();
				if (jsonItemInfo.has("libraryId")) {
					libraryItemLanguage = libraryItemLanguageRepository.findByItemAndLanguage(libraryItem, language);
				}
				libraryItemLanguage.setTitle(title);
				libraryItemLanguage.setDetails(details);
				libraryItemLanguage.setItem(libraryItem);
				libraryItemLanguage.setLanguage(lang);
				libraryItemLanguageRepository.save(libraryItemLanguage);
				libraryItemLanguageList.add(libraryItemLanguage);
			}
			libraryItem.setItemLanguageList(libraryItemLanguageList);
			String fileName = jsonItemInfo.getString("fileName");

			LibraryItemImages libraryItemImage = new LibraryItemImages();
			if (jsonItemInfo.has("libraryId")) {
				libraryItemImage = libraryItemImagesRepository.findByItem(libraryItem);
			}
			libraryItemImage.setFileName(fileName);
			libraryItemImage.setFileUrl("http://ca1.risknucleus.com:47/SmartCart/Images/LibraryItemImages/" + fileName);
			libraryItemImage.setSortOrder(0);
			libraryItemImage.setItem(libraryItem);

			libraryItemImagesRepository.save(libraryItemImage);

			Double DiscountAmount = 0.0;
			Double DiscountPercentage = 0.0;
			if (jsonItemInfo.has("DiscountAmount")) {
				DiscountAmount = jsonItemInfo.getDouble("DiscountAmount");
				DiscountPercentage = (jsonItemInfo.getDouble("Price") - DiscountAmount)
						/ jsonItemInfo.getDouble("Price");
			}
			if (jsonItemInfo.has("DiscountPercentage")) {
				DiscountPercentage = jsonItemInfo.getDouble("DiscountPercentage");
				DiscountAmount = jsonItemInfo.getDouble("Price")
						- (jsonItemInfo.getDouble("Price") * (DiscountPercentage / 100));

			}

			Item item = new Item();
			if (jsonItemInfo.has("id"))
				item = itemRepository.findOne(jsonItemInfo.getLong("id"));
			item.setPrice(price);
			item.setQuantity(quantity);
			item.setCost(cost);
			item.setDiscountAmount(DiscountAmount);
			item.setStatus(statusRepository.findByCode("STA109"));
			item.setBranch(branch);
			item.setLibraryItem(libraryItem);
			item.setDiscountPercentage(DiscountPercentage);
			item.setNetSalePrice(price);
			item.setBrand(brand);
			item.setKeyFeature(keyFeatures);
			item.setSku(sku);
			itemRepository.save(item);

			List<ItemLanguage> itemLanguageList = new ArrayList<ItemLanguage>();
			for (MetaLanguage lang : languageList) {

				ItemLanguage itemLanguage = new ItemLanguage();
				if (jsonItemInfo.has("id"))
					itemLanguage = itemLanguageRepository.findByItemAndLanguage(item, lang);
				itemLanguage.setTitle(title);
				itemLanguage.setDetails(details);
				itemLanguage.setItem(item);
				itemLanguage.setLanguage(lang);
				itemLanguageRepository.save(itemLanguage);
				itemLanguageList.add(itemLanguage);
			}
			item.setItemLanguageList(itemLanguageList);

			MetaMessage msg = messageRepository.findByCode("MSG001");
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			return json.toString();
		} catch (Exception e) {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			MetaLanguage language = languageRepository.findByCode("LANG0001");
			JSONObject catchjson = validateUserObject(apiKey, warn, e.getMessage(), language, "MSG003", "error");
			return catchjson.toString();
		}
	}

	/*
	 * command delete operator and suboperator
	 */
	@RequestMapping(value = "/srs_uop_015", method = RequestMethod.POST)
	public String deleteOperatorandsuboperator(@RequestBody ItemRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/operators/srs_uop_015 - deleteOperatorandSuboperator - : {}";
		String apiKey = "srs_uop_015";
		try {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonOperatorInfo = jsonData.getJSONObject("operatorInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}
			double order = 0;
			order = customerOrderRepository.findbyOperator(jsonOperatorInfo.getLong("id"));

			User user = userRepository.findOne(jsonOperatorInfo.getLong("id"));

			userOptional = null;
			userOptional = userRepository.findById(jsonOperatorInfo.getLong("id"));
			if (userOptional.isPresent()) {
				if (order == 0) {
					userRepository.delete(user);
				} else {
					user.setStatus(statusRepository.findByCode("STA004"));
					userRepository.save(user);
				}
				MetaMessage msg = messageRepository.findByCode("MSG001");
				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "ack");
				message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
				data.put("message", message);
				json.put("data", data);
				json.put("key", apiKey);
				return json.toString();
			} else {
				log.warn("/api/operators/srs_uop_015 -deleteOperatorandSuboperator - : {}", "No User Exist");
				MetaMessage msg = messageRepository.findByCode("MSG504");

				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "warn");
				message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
				data.put("message", message);
				json.put("data", data);
				json.put("key", apiKey);

				return json.toString();
			}

		} catch (Exception e) {
			MetaLanguage language = languageRepository.findByCode("LANG0001");
			JSONObject catchjson = validateUserObject(apiKey, warn, e.getMessage(), language, "MSG003", "error");
			return catchjson.toString();
		}

	}

	private JSONObject validateUserObject(String apiKey, String warn, String warnMsg, MetaLanguage language,
			String msgCode, String type) {
		log.warn(warn, warnMsg);

		MetaMessage msg = messageRepository.findByCode(msgCode);

		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject message = new JSONObject();
		message.put("type", type);
		message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
		data.put("message", message);
		json.put("data", data);
		json.put("key", apiKey);

		// add server timestamp
		json.put("Server Timestamp", statusRepository.getCurrentTime());

		return json;
	}

	public static String randomCode(int length) {
		int index = 0;
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			index = (int) (charSet.length * Math.random());
			sb.append(charSet[index]);
		}
		return sb.toString();
	}

	private boolean sendSMS(String phoneNumber, String code) {
		try {
			String ACCOUNT_SID = twilioSid.trim();
			String AUTH_TOKEN = twilioToken.trim();
			String PHONE_FROM = twilioFrom.trim();

			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			Message message = Message.creator(new PhoneNumber(phoneNumber), // to
					new PhoneNumber(PHONE_FROM), // from
					"Your verification code is " + code).create();

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * command change branch status
	 */
	@RequestMapping(value = "/srs_uop_023", method = RequestMethod.POST)
	public String changeBranchStatus(@RequestBody ItemRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_023 - change order status - : {}";
		String apiKey = "api/user/srs_uop_023";
		String key = "srs_uop_023";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonOperatorAppInfo = jsonData.getJSONObject("operatorAppInfo");
			JSONObject jsonBranchInfo = jsonData.getJSONObject("branchInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonStatusInfo = jsonData.getJSONObject("statusInfo");
			JSONObject jsonUserTypeInfo = jsonOperatorAppInfo.getJSONObject("usertype");
			MetaUserType userType = metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			MetaStatus status = statusRepository.findByCode(jsonStatusInfo.getString("code"));

			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(
					jsonOperatorAppInfo.getString("sessionid"), jsonOperatorAppInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));

			branch.setStatus(status);
			branchRepository.save(branch);

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject data = new JSONObject();
			JSONObject json = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());

			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	@RequestMapping(value = "/srs_uop_025", method = RequestMethod.POST)
	public String getLibraryItem(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uop_025 - get Library Item - : {}";
		String apiKey = "api/user/srs_uop_025";
		String key = "srs_uop_025";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonOperatorAppInfo = jsonData.getJSONObject("operatorAppInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonListInfo = jsonData.getJSONObject("listInfo");
			JSONObject jsonBranchInfo = jsonData.getJSONObject("branchInfo");
			JSONObject jsonUserTypeInfo = jsonOperatorAppInfo.getJSONObject("usertype");
			MetaUserType userType = metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));
			JSONObject jsonSearchTerm;
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			String searchTerm = "";
			if (jsonData.has("searchTerm")) {
				jsonSearchTerm = jsonData.getJSONObject("searchTerm");
				searchTerm = jsonSearchTerm.getString("searchTerm");
			}
			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(
					jsonOperatorAppInfo.getString("sessionid"), jsonOperatorAppInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			int listSize = 0;
			if (jsonData.has("listInfo")) {
				listSize = jsonListInfo.getInt("listInfo");
			}
			Pageable listing = new PageRequest(0, listSize);
			JSONArray libraryItemListJson = new JSONArray();
			Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));
			if (searchTerm == "") {
				List<LibraryItem> libraryItemList = libraryItemRepository
						.findByStatus(statusRepository.findByCode("STA003"), listing);

				for (LibraryItem libraryItem : libraryItemList) {
					List<Item> item = itemRepository.findByLibraryItemAndBranch(libraryItem, branch);
					if (item == null || item.size() == 0)
						libraryItemListJson.put(libraryItem.getInfo(language, libraryItemLanguageRepository,
								libraryCategoryLanguageRepository));
				}
			} else {
				List<LibraryItemLanguage> libraryItemLanguageList = libraryItemLanguageRepository
						.findByLanguageAndTitleContainingIgnoreCase(language, searchTerm, listing);

				for (LibraryItemLanguage libraryItemLanguage : libraryItemLanguageList) {
					List<Item> item = itemRepository.findByLibraryItemAndBranch(libraryItemLanguage.getItem(), branch);
					if (item == null || item.size() == 0)
						libraryItemListJson.put(libraryItemLanguage.getItem().getInfo(language,
								libraryItemLanguageRepository, libraryCategoryLanguageRepository));
				}
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject data = new JSONObject();
			JSONObject json = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("LibraryItem", libraryItemListJson);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	private void sendChangeOrderStatusNotification(CustomerOrder order, OrderStatusLog orderStatusLog,
			MetaLanguage language, String key) {
		// Header
		MetaMessage notificationMsg = messageRepository.findByCode("MSG011");
		String title = messageLanguageRepository.findByMessageAndLanguage(notificationMsg, language).getTitle();

		// body user
		MetaMessage notificationMsgBoby = new MetaMessage();
		// body operator
		MetaMessage notificationMsgBobyOp = new MetaMessage();

		String deviceToken = "";
		/*
		 * // order assigned (send status to only sub operator) if
		 * (order.getStatus().getCode().equals("STA106")) { String body =
		 * messageLanguageRepository.findByMessageAndLanguage(notificationMsgBoby,
		 * language).getTitle() .replace(":orderid", order.getId().toString());
		 *
		 * JSONObject jsonNotification = new JSONObject(); JSONObject orderStatus = new
		 * JSONObject(); orderStatus.put("orderId", order.getId());
		 * orderStatus.put("statusLog", orderStatusLog.getInfo(language,
		 * statusLanguageRepository)); orderStatus.put("status",
		 * order.getStatus().getCode()); orderStatus.put("title", title);
		 * orderStatus.put("body", body); orderStatus.put("key", key);
		 * jsonNotification.put("orderStatus", orderStatus);
		 *
		 * notificationMsgBoby = messageRepository.findByCode("MSG106");
		 *
		 * deviceToken = order.getSubOperator().getInstallationId();
		 *
		 * JSONObject notification = new JSONObject(); notification.put("title", title);
		 * notification.put("body", body); notification.put("sound", "default");
		 * notification.put("click_action", "FCM_PLUGIN_ACTIVITY");
		 *
		 * cms.sendMessage(deviceToken, jsonNotification, notification, true); } else {
		 */
		// set notification body according to order status and user/operator
		if (order.getStatus().getCode().equals("STA102")) {
			notificationMsgBoby = messageRepository.findByCode("MSG101");
			notificationMsgBobyOp = messageRepository.findByCode("MSG107");
		} else if (order.getStatus().getCode().equals("STA103")) {
			notificationMsgBoby = messageRepository.findByCode("MSG102");
			notificationMsgBobyOp = messageRepository.findByCode("MSG108");
		} else if (order.getStatus().getCode().equals("STA104")) {
			notificationMsgBoby = messageRepository.findByCode("MSG103");
			notificationMsgBobyOp = messageRepository.findByCode("MSG109");
		} else if (order.getStatus().getCode().equals("STA105")) {
			notificationMsgBoby = messageRepository.findByCode("MSG105");
			notificationMsgBobyOp = messageRepository.findByCode("MSG110");
		} else if (order.getStatus().getCode().equals("STA107")) {
			notificationMsgBoby = messageRepository.findByCode("MSG505");
			notificationMsgBobyOp = messageRepository.findByCode("MSG505");
		} else if (order.getStatus().getCode().equals("STA106")) {
			notificationMsgBoby = messageRepository.findByCode("MSG106");
			notificationMsgBobyOp = messageRepository.findByCode("MSG111");
		}

		String body = messageLanguageRepository.findByMessageAndLanguage(notificationMsgBoby, language).getTitle()
				.replace(":orderid", order.getOrderNumber().toString());
		String bodyOp = messageLanguageRepository.findByMessageAndLanguage(notificationMsgBobyOp, language).getTitle()
				.replace(":orderid", order.getOrderNumber().toString());

		JSONObject jsonNotification = new JSONObject();
		JSONObject orderStatus = new JSONObject();
		orderStatus.put("orderId", order.getId());
		orderStatus.put("statusLog", orderStatusLog.getInfo(language, statusLanguageRepository));
		orderStatus.put("status", order.getStatus().getCode());
		orderStatus.put("title", title);
		orderStatus.put("body", body);
		orderStatus.put("key", key);
		jsonNotification.put("orderStatus", orderStatus);

		// send notification to user

		deviceToken = order.getUser().getInstallationId();

		JSONObject notification = new JSONObject();
		notification.put("title", title);
		notification.put("body", body);
		notification.put("sound", "default");
		notification.put("click_action", "FCM_PLUGIN_ACTIVITY");

		cms.sendMessage(deviceToken, jsonNotification, notification, false);

		JSONObject jsonNotificationOp = new JSONObject();
		JSONObject orderStatusOp = new JSONObject();
		orderStatusOp.put("orderId", order.getId());
		orderStatusOp.put("statusLog", orderStatusLog.getInfo(language, statusLanguageRepository));
		orderStatusOp.put("status", order.getStatus().getCode());
		orderStatusOp.put("title", title);
		orderStatusOp.put("body", bodyOp);
		orderStatusOp.put("key", key);
		jsonNotificationOp.put("orderStatus", orderStatusOp);

		// send notification to operator
		if (order.getStatus().getCode().equals("STA106")) {
			deviceToken = order.getSubOperator().getInstallationId();
		} else {
			User operator = userRepository.findByUserTypeAndBranch(metaUserTypeRepository.findByCode("UT000003"),
					order.getBranch());
			deviceToken = operator.getInstallationId();
		}

		JSONObject notificationOp = new JSONObject();
		notificationOp.put("title", title);
		notificationOp.put("body", bodyOp);
		notificationOp.put("sound", "default");
		notificationOp.put("click_action", "FCM_PLUGIN_ACTIVITY");

		cms.sendMessage(deviceToken, jsonNotificationOp, notificationOp, true);
		// }
	}

	private void sendChangeBranchStatusNotification(MetaLanguage language, Branch branch) {
		// Header

		// body user
		MetaMessage notificationMsgBoby = new MetaMessage();
		// body operator
		MetaMessage notificationMsgBobyOp = new MetaMessage();

		String deviceToken = "";
		notificationMsgBobyOp = messageRepository.findByCode("MSG506");

		String title = messageLanguageRepository.findByMessageAndLanguage(notificationMsgBobyOp, language).getTitle();

		String bodyOp = messageLanguageRepository.findByMessageAndLanguage(notificationMsgBobyOp, language).getTitle();

		Hq hq = branch.getHq();
		User user = userRepository.findByHq(hq);
		deviceToken = user.getInstallationId();

		JSONObject notificationOp = new JSONObject();
		notificationOp.put("title", title);
		notificationOp.put("body", bodyOp);
		notificationOp.put("sound", "default");
		notificationOp.put("click_action", "FCM_PLUGIN_ACTIVITY");

		cms.sendConfirmationMessage(deviceToken, notificationOp);
		// }
	}

	@RequestMapping(value = "/srs_uop_020", method = RequestMethod.POST)
	public String timeAndOrders(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate,
			HttpSession session) throws Exception {
		String warn = "/api/operator/srs_uop_020 - addUserAndStore - : {}";
		String apiKey = "srs_uop_020";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonUserTypeInfo = jsonData.getJSONObject("userTypeInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonStoreInfo = jsonData.getJSONObject("storeInfo");

			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			MetaUserType metaUserType = metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));
			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));

			// String token = jsonUserInfo.getString("token");
			String username = jsonUserInfo.getString("username");
			String password = jsonUserInfo.getString("password");
			Long branchId = jsonStoreInfo.getLong("id");
			Branch branch = branchRepository.findOne(branchId);

			MetaStatus status = statusRepository.findByCode("STA104");

			int lim = jsonUserInfo.getInt("lim");
			int spoint = jsonUserInfo.getInt("spoint");

			MetaMessage msg = messageRepository.findByCode("MSG001");
			MetaUserType userType = metaUserTypeRepository.findByCode(jsonUserTypeInfo.getString("code"));
			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			List<Object[]> stackedGraph = salesReportRepository.stackedGraph(60, lim, spoint);
			for (int i = 0; i < stackedGraph.size(); i++) {
				Map<String, Object> item = new HashMap<String, Object>();
				item.put("fk_order", stackedGraph.get(i)[0]);
				item.put("A", stackedGraph.get(i)[1]);
				item.put("B", stackedGraph.get(i)[2]);
				item.put("C", stackedGraph.get(i)[3]);
				item.put("D", stackedGraph.get(i)[4]);

				result.add(item);
			}
			JSONArray stackedGraphList = new JSONArray();

			for (Map category : result) {
				stackedGraphList.put(category);
			}

			/*
			 * List<Map<String, Object>> nun = new ArrayList<Map<String, Object>>();
			 * List<Object[]> nummerTimes= salesReportRepository.nummerTimes(60); for(int
			 * i=0; i<nummerTimes.size(); i++) { Map<String, Object> item = new
			 * HashMap<String, Object>(); item.put("fk_order",
			 * (BigInteger)nummerTimes.get(i)[0]);
			 *
			 * nun.add(item); } JSONArray nummerTimesList = new JSONArray();
			 *
			 * for (Map category : nun) { nummerTimesList.put(category); }
			 */

			List<Object[]> title = branchLanguageRepository.storeTitle(branch);

			int numberOfOrders = salesReportRepository.getOrderNummer(branch, status);
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("stackedGraphListInfo", stackedGraphList);
			data.put("storeTitle", title);
			// data.put("nummerTimesListInfo", nummerTimesList);
			data.put("numberOfOrdersInfo", numberOfOrders);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command change order status
	 */
	@RequestMapping(value = "/srs_uop_005", method = RequestMethod.POST)
	public String changeOrderStatus(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_005 - change order status - : {}";
		String apiKey = "api/user/srs_uop_005";
		String key = "srs_uop_005";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			MetaStatus status = statusRepository.findByCode(param.getMetaStatus().getCode());
			CustomerOrder order = customerOrderRepository.findOne(param.getOrderInfo().getId());

			order.setStatus(status);
			customerOrderRepository.save(order);

			double actual_amount = 0.0;
			if (order.getStatus().getCode().equals("STA107")) {
				List<OrderItemUpdated> orderItemList = new ArrayList<OrderItemUpdated>();
				List<Item> ItemList = new ArrayList<Item>();
				for (Item updatedItem : param.getUpdatedItemInfoList()) {
					Item item = itemRepository.findOne(updatedItem.getId());
					boolean isavailable = updatedItem.getIsAvailable();
					long quantity = isavailable ? updatedItem.getQuantity() : 0;
					double price = updatedItem.getPrice();

					OrderItemUpdated OrderItemUpdated = new OrderItemUpdated(price, quantity, item, order, isavailable);
					orderItemUpdatedRepository.save(OrderItemUpdated);
					OrderItem orderItem = orderItemRepository.findByOrderAndItem(order, item);
					item.setQuantity((item.getQuantity() + orderItem.getQuantity()) - quantity);
					item.setUpdated(Timestamp.valueOf(param.getTimeStamp()));
					ItemList.add(item);

					orderItemList.add(OrderItemUpdated);

					if (isavailable || quantity != 0) {
						actual_amount = actual_amount + (quantity * price);
					}
				}
				order.setActualAmount(actual_amount);
				itemRepository.save(ItemList);

				Wallet wallet = walletRepository.findByUser(order.getUser());
				if (wallet != null && order.getWalletAmountUsed() != 0) {
					wallet.setWalletAmount(wallet.getWalletAmount() + order.getWalletAmountUsed());
					wallet = walletRepository.save(wallet);
				}

				CouponUsers userCouponList = couponUsersRepository.findByUserAndDiscountCoupon(order.getUser(),
						order.getDiscountCoupon());
				if (userCouponList != null) {

					double amountUsed = userCouponList.getAmountUsed();

					double newAmountUsed = amountUsed - order.getDiscountedAmount();
					userCouponList.setAmountUsed(newAmountUsed);
					couponUsersRepository.save(userCouponList);

					double amount = actual_amount * order.getDiscountCoupon().getPercentage() / 100;
					double totalamount = actual_amount - amount;
					double totalamountperuse;
					if (totalamount > order.getDiscountCoupon().getMaxAmountPerUse()) {
						totalamountperuse = order.getDiscountCoupon().getMaxAmountPerUse();
					} else {
						totalamountperuse = totalamount;
					}
					if (amount > (userCouponList.getMaxAmount() - userCouponList.getAmountUsed())) {
						double a = userCouponList.getMaxAmount() - userCouponList.getAmountUsed();

						amount = amount - a;
						totalamountperuse = actual_amount - a;
					}
					order.setDiscountedAmount(actual_amount - totalamountperuse);
					// Wallet wallet = walletRepository.findByUser(order.getUser());
					if (totalamountperuse <= wallet.getWalletAmount()) {
						order.setTotalAmount(0.0);
						order.setWalletAmountUsed(wallet.getWalletAmount() - actual_amount);
						wallet.setWalletAmount(wallet.getWalletAmount() - totalamountperuse);
						walletRepository.save(wallet);
						customerOrderRepository.save(order);
					} else {
						order.setTotalAmount(totalamountperuse - wallet.getWalletAmount());
						order.setWalletAmountUsed(wallet.getWalletAmount());
						wallet.setWalletAmount(0.0);

						walletRepository.save(wallet);
						customerOrderRepository.save(order);

					}
					// customerOrderRepository.save(order);
					newAmountUsed = newAmountUsed + (actual_amount - totalamountperuse);
					userCouponList.setAmountUsed(newAmountUsed);
					couponUsersRepository.save(userCouponList);

				} else {
					// Wallet wallet = walletRepository.findByUser(order.getUser());
					if (actual_amount <= wallet.getWalletAmount()) {
						order.setTotalAmount(0.0);
						order.setWalletAmountUsed(wallet.getWalletAmount() - actual_amount);
						wallet.setWalletAmount(wallet.getWalletAmount() - actual_amount);
						walletRepository.save(wallet);
						customerOrderRepository.save(order);
					} else {
						order.setTotalAmount(actual_amount - wallet.getWalletAmount());
						order.setWalletAmountUsed(wallet.getWalletAmount());
						wallet.setWalletAmount(0.0);
						walletRepository.save(wallet);
						customerOrderRepository.save(order);
					}
				}

			}

			if (order.getStatus().getCode().equals("STA104")) {
				Wallet wallet = walletRepository.findByUser(order.getUser());
				if (wallet != null) {
					Double amountRecieved = order.getTotalAmount();
					if (param.getAmountRecieved() != null) {
						amountRecieved = param.getAmountRecieved();
					}
					WalletOrder walletOrder = new WalletOrder();
					walletOrder.setOrder(order);
					walletOrder.setAmountPayable(order.getTotalAmount());
					walletOrder.setAmountRecieved(amountRecieved);

					walletOrder.setDifference(amountRecieved - order.getTotalAmount());

					walletOrder.setDifference(amountRecieved - order.getTotalAmount());

					walletOrder.setWallet(wallet);
					walletOrderRepository.save(walletOrder);
					wallet.setWalletAmount(wallet.getWalletAmount() + walletOrder.getDifference());
					walletRepository.save(wallet);
				}
			}

			if (order.getStatus().getCode().equals("STA104")) {
				SaleInvoice saleInvoice = new SaleInvoice();
				Branch branch = order.getBranch();
				Hq hq = order.getBranch().getHq();
				saleInvoice.setBranch(branch);
				saleInvoice.setComment("");
				saleInvoice.setCreatedOn(Timestamp.valueOf(param.getTimeStamp()));
				saleInvoice.setDate(Timestamp.valueOf(param.getTimeStamp()));
				saleInvoice.setHq(hq);
				saleInvoice.setSaleInvoiceNo("SI-" + UniqueIdGenerator.generateLongId().toString());
				saleInvoice = saleInvoiceRepository.save(saleInvoice);

				List<OrderItem> orderItemList = order.getOrderItemList();
				Double totalPrice = 0.0;
				for (int i = 0; i < orderItemList.size(); i++) {
					Item item = orderItemList.get(i).getItem();
					totalPrice += orderItemList.get(i).getPrice();
					double amount = orderItemList.get(i).getQuantity() * orderItemList.get(i).getPrice();
					double netAmt = amount - item.getDiscountAmount();
					/*
					 * saleInvoiceRepository.sp_sale_invoice(saleInvoice.getId(), item.getId(),
					 *
					 * orderItemList.get(i).getQuantity(), orderItemList.get(i).getPrice(), amount,
					 * item.getDiscountPercentage(), item.getDiscountAmount(), netAmt, "",
					 * branch.getId(), hq.getId());
					 */
				}

				saleInvoice.setTotalPrice(totalPrice);
				saleInvoiceRepository.save(saleInvoice);
			}

			OrderStatusLog orderStatusLog = new OrderStatusLog();
			orderStatusLog.setOrder(order);
			orderStatusLog.setStatus(status);
			orderStatusLog.setTsClient(Timestamp.valueOf(param.getTimeStamp()));
			orderStatusLog.setTsServer(statusRepository.getCurrentTime());
			orderStatusLogRepository.save(orderStatusLog);

			MetaMessage msg = messageRepository.findByCode("MSG001");

			sendChangeOrderStatusNotification(order, orderStatusLog, language, key);
			JSONObject data = new JSONObject();
			JSONObject json = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command getSearchResultsItemsLibrary
	 */
	@RequestMapping(value = "/srs_uop_027", method = RequestMethod.POST)
	public String getSearchResultsItemsLibrary(@RequestBody ItemRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_027 - getSearchResultsItemsBranchWise - : {}";
		String apiKey = "srs_uop_027";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			// jsonParam.getJSONObject("data");
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonSearchTerm = jsonData.getJSONObject("searchTerm");
			JSONObject jsonListSizeInfo = jsonData.getJSONObject("listSizeInfo");

			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			int listSize = 0;
			if (jsonData.has("listSizeInfo")) {
				listSize = jsonListSizeInfo.getInt("listSizeInfo");
			}
			Pageable listing = new PageRequest(0, listSize + 5);

			JSONArray itemListJson = new JSONArray();
			String searchTerm = jsonSearchTerm.getString("searchTerm");
			// List<Item> itemList = findWithTitle(searchTerm);
			List<LibraryItemLanguage> libraryItemLanguageList = libraryItemLanguageRepository
					.findByLanguageAndTitleContainingIgnoreCase(language, searchTerm, listing);
			for (LibraryItemLanguage libraryItemLanguage : libraryItemLanguageList) {
				itemListJson.put(libraryItemLanguage.getItem().getInfo(language, libraryItemLanguageRepository,
						libraryCategoryLanguageRepository));
			}

			// get categories
			// List<Category> categoryList =
			// categoryRepository.findByBranchAndStatus(branch, status);
			// for(Category category : categoryList) {
			// //get items
			// List<Item> itemList = itemRepository.findByCategoryAndStatus(category,
			// status, listing);
			//
			// for(Item item : itemList) {
			// ItemLanguage itemLanguage =
			// itemLanguageRepository.findByItemAndLanguage(item, language);
			// if (itemLanguage.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
			// {
			// itemListJson.put(item.getInfo(language, status, itemLanguageRepository,
			// subOptRepository, subOptLanguageRepository, optLanguageRepository,
			// itemOptSubOptRepository));
			// }
			// }
			// }
			//

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("itemInfoList", itemListJson);
			json.put("data", data);
			json.put("key", "srs_uop_027");

			return json.toString();

		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command add and Edit Bundles
	 */
	@RequestMapping(value = "/srs_uop_028", method = RequestMethod.POST)
	public String AddBundles(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uop_028 - AddBundles - : {}";
		String apiKey = "srs_uop_028";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			// jsonParam.getJSONObject("data");
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("operatorAppInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonbranchInfo = jsonData.getJSONObject("branchInfo");
			JSONArray jsonItemList = jsonData.getJSONArray("itemInfoList");
			JSONObject jsonDealInfo = jsonData.getJSONObject("DealInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonStatusInfo = jsonUserInfo.getJSONObject("statusInfo");
			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			Branch branch = branchRepository.findOne(jsonbranchInfo.getLong("id"));
			MetaStatus status = statusRepository.findByCode(jsonStatusInfo.getString("code"));
			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}
			String dealName = jsonDealInfo.getString("title");
			String dealDetails = jsonDealInfo.getString("details");
			Double price = jsonDealInfo.getDouble("price");
			Long quantity = jsonDealInfo.getLong("quantity");

			Double cost = 0.0;

			for (int i = 0; i < jsonItemList.length(); i++) {

				long itemId = jsonItemList.getJSONObject(i).getLong("id");
				Item item = itemRepository.findOne(itemId);
				cost = cost + item.getPrice();

			}
			LibraryItem libraryItem = new LibraryItem();
			if (jsonDealInfo.has("id")) {
				libraryItem = itemRepository.findOne(jsonDealInfo.getLong("id")).getLibraryItem();
			}

			libraryItem.setStatus(statusRepository.findByCode("STA003"));
			libraryItem.setLibraryCategory(libraryCategoryRepository.findById((long) 2).get());
			libraryItemRepository.save(libraryItem);

			if (jsonDealInfo.has("id"))
				libraryItemLanguageRepository.deleteByItem(libraryItem);
			List<MetaLanguage> languageList = languageRepository.findAll();

			List<LibraryItemLanguage> libraryItemLanguageList = new ArrayList<LibraryItemLanguage>();
			for (MetaLanguage lang : languageList) {

				LibraryItemLanguage libraryItemLanguage = new LibraryItemLanguage();

				libraryItemLanguage.setTitle(dealName);
				libraryItemLanguage.setDetails(dealDetails);
				libraryItemLanguage.setItem(libraryItem);
				libraryItemLanguage.setLanguage(lang);
				libraryItemLanguageRepository.save(libraryItemLanguage);
				libraryItemLanguageList.add(libraryItemLanguage);
			}
			libraryItem.setItemLanguageList(libraryItemLanguageList);

			Item item = new Item();
			if (jsonDealInfo.has("id")) {
				item = itemRepository.findOne(jsonDealInfo.getLong("id"));
			}
			item.setPrice(price);
			item.setQuantity(quantity);
			item.setCost(cost);
			item.setDiscountAmount(0);
			item.setStatus(status);
			item.setBranch(branch);
			item.setLibraryItem(libraryItem);
			item.setDiscountPercentage(0);
			item.setNetSalePrice(price);
			itemRepository.save(item);
			if (jsonDealInfo.has("id"))
				itemLanguageRepository.deleteByItem(item);
			List<ItemLanguage> itemLanguageList = new ArrayList<ItemLanguage>();
			for (MetaLanguage lang : languageList) {
				LibraryItemLanguage libraryItemLanguage = libraryItemLanguageRepository
						.findByItemAndLanguage(libraryItem, lang);

				ItemLanguage itemLanguage = new ItemLanguage();
				itemLanguage.setTitle(libraryItemLanguage.getTitle());
				itemLanguage.setDetails(libraryItemLanguage.getDetails());
				itemLanguage.setItem(item);
				itemLanguage.setLanguage(lang);
				itemLanguageRepository.save(itemLanguage);
				itemLanguageList.add(itemLanguage);
			}
			item.setItemLanguageList(itemLanguageList);
			if (jsonDealInfo.has("id"))
				bundlesItemRepository.deleteByItem(item);
			for (int i = 0; i < jsonItemList.length(); i++) {

				long itemId = jsonItemList.getJSONObject(i).getLong("id");
				Item itemAdd = itemRepository.findOne(itemId);
				Long quantityAdd = jsonItemList.getJSONObject(i).getLong("quantity");

				BundlesItem bundles = new BundlesItem(itemAdd, branch, item, quantityAdd);
				bundlesItemRepository.save(bundles);

			}
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uop_027");

			return json.toString();

		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command getSearchProducts
	 */
	@RequestMapping(value = "/srs_uop_029", method = RequestMethod.POST)
	public String getSearchProducts(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uop_029 - getSearchProducts - : {}";
		String apiKey = "srs_uop_029";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			// jsonParam.getJSONObject("data");
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonSearchTerm = jsonData.getJSONObject("searchTerm");
			JSONObject jsonListSizeInfo = jsonData.getJSONObject("listSizeInfo");
			JSONObject jsonBranchInfo = jsonUserInfo.getJSONObject("branchInfo");
			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			int listSize = 0;
			if (jsonData.has("listSizeInfo")) {
				listSize = jsonListSizeInfo.getInt("listSizeInfo");
			}
			Pageable listing = new PageRequest(0, listSize + 5);

			Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));

			JSONArray itemListJson = new JSONArray();
			String searchTerm = jsonSearchTerm.getString("searchTerm");
			// List<Item> itemList = findWithTitle(searchTerm);

			SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
			String timeStamp = jsonData.getString("timeStamp");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String sdate = dateFormat.format(timeStamp);

			String time = formatter.format(timeStamp);

			FlashSale flashSale = new FlashSale();
			flashSale = flashSaleRepository.findSale((long) 3, time, sdate);
			Boolean isFlashSale = false;
			if (flashSale != null) {
				isFlashSale = true;
			}
			List<Item> itemList = itemRepository.findByBranchAndStatusAndTitleContaining("%" + searchTerm + "%",
					searchTerm, searchTerm + "%", jsonBranchInfo.getLong("id"),
					statusRepository.findByCode("STA003").getId(), language, listing);
			for (Item item : itemList) {

				itemListJson.put(item.getInfo(language, statusRepository.findByCode("STA003"), itemLanguageRepository,
						subOptRepository, subOptLanguageRepository, optLanguageRepository, itemOptSubOptRepository,
						categoryLanguageRepository, branchLanguageRepository, dayLanguageRepository,
						libraryCategoryLanguageRepository, itemRepository, isFlashSale, flashSale,
						flashSaleItemRepository));
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("itemInfoList", itemListJson);
			json.put("data", data);
			json.put("key", "srs_uop_029");

			return json.toString();

		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command Add and Update RangesCoupon
	 */
	@RequestMapping(value = "/srs_uop_030", method = RequestMethod.POST)
	public String AddRangesCoupon(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uop_030 - AddRangesCoupon - : {}";
		String apiKey = "srs_uop_030";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			// jsonParam.getJSONObject("data");
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("operatorAppInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonbranchInfo = jsonData.getJSONObject("branchInfo");
			JSONObject jsonCouponInfo = jsonData.getJSONObject("couponInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONArray jsonRangesInfoList = jsonData.getJSONArray("rangesInfoList");
			JSONObject jsonStatusInfo = jsonData.getJSONObject("statusInfo");
			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			Branch branch = branchRepository.findOne(jsonbranchInfo.getLong("id"));
			MetaStatus status = statusRepository.findByCode(jsonStatusInfo.getString("code"));
			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}
			String expiryDate = jsonCouponInfo.getString("expiryDate");
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date eDate = format.parse(expiryDate);

			String startDate = jsonCouponInfo.getString("startDate");
			format = new SimpleDateFormat("yyyy-MM-dd");
			Date sDate = format.parse(startDate);

			String fileName = jsonCouponInfo.getString("fileName");
			String title = jsonCouponInfo.getString("title");
			String details = jsonCouponInfo.getString("details");

			User user = userOptional.get();
			DiscountCoupon coupon = new DiscountCoupon();
			if (jsonCouponInfo.has("id")) {
				coupon = couponRepository.findOne(jsonCouponInfo.getLong("id"));
			}

			if (!jsonCouponInfo.has("id")) {
				coupon.setCreatedBy(user);
			} else {
				coupon.setUpdatedBy(user);
			}
			coupon.setCouponType("R");
			coupon.setPromoCode(RandomStringUtils.randomAlphanumeric(6));
			coupon.setFileName(fileName);
			coupon.setFileUrl("http://ca1.risknucleus.com:47/SmartCart/Images/Stores/" + fileName);
			coupon.setPercentage((long) 0);
			coupon.setStatus(status);
			coupon.setExpiryDate(eDate);
			coupon.setMaxAmount(0);
			coupon.setStartingDate(sDate);
			couponRepository.save(coupon);

			String result = "";
			int num = 0000;
			StringBuilder builder = new StringBuilder();
			Long generatedNum = num + coupon.getId();
			String finalNum = String.format("%05d", generatedNum);
			builder.append("P");
			result = builder.append(finalNum).toString();

			coupon.setCode(result);
			couponRepository.save(coupon);

			// add store details

			if (jsonCouponInfo.has("id"))
				couponLanguageRepository.deleteByDiscountCoupon(coupon);

			List<MetaLanguage> languageList = languageRepository.findAll();

			for (MetaLanguage lang : languageList) {
				DiscountCouponLanguage discountCouponLanguage = new DiscountCouponLanguage();
				discountCouponLanguage.setTitle(title);
				discountCouponLanguage.setDetails(details);
				discountCouponLanguage.setDiscountCoupon(coupon);
				discountCouponLanguage.setLanguage(lang);
				couponLanguageRepository.save(discountCouponLanguage);
			}
			if (jsonCouponInfo.has("id")) {
				couponRangesRepository.deleteByCoupon(coupon);
			}

			for (int i = 0; i < jsonRangesInfoList.length(); i++) {
				Double start = jsonRangesInfoList.getJSONObject(i).getDouble("startRange");
				Double end = jsonRangesInfoList.getJSONObject(i).getDouble("endRange");
				Double percentage = jsonRangesInfoList.getJSONObject(i).getDouble("percentage");
				CouponRanges couponRanges = new CouponRanges();
				couponRanges.setStartRange(start);
				couponRanges.setEndRange(end);
				couponRanges.setCoupon(coupon);
				couponRangesRepository.save(couponRanges);
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uop_027");

			return json.toString();

		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command getFuzzyMatchProducts
	 */
	@RequestMapping(value = "/srs_uop_034", method = RequestMethod.POST)
	public String getFuzzyMatchProducts(@RequestBody ItemRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uop_030 - getFuzzyMatchProducts - : {}";
		String apiKey = "srs_uop_030";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			// jsonParam.getJSONObject("data");
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonSearchTerm = jsonData.getJSONObject("searchTerm");

			String title = jsonSearchTerm.getString("title");
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			float score = 0;
			float finalScore = 0;

			// List<LibraryItem> libraryItemList = libraryItemRepository.findAll();
			List<Object[]> libraryItemList = libraryItemLanguageRepository.findByLanguage(language);

			String str1 = title;
			LibraryItem libraryItem = new LibraryItem();

			for (int j = 0; j < libraryItemList.size(); j++) {
//	        		String str2 = libraryItemList.get(j).getItemLanguageList().get(0).getTitle();
				String str2 = (String) libraryItemList.get(j)[1];
				score = fileStorageService.LevenshteinDistanceCaseInsensitive(str1, str2);
				if (score > finalScore) {
					finalScore = score;
					libraryItem = libraryItemRepository.findOne((Long) libraryItemList.get(j)[0]);
				}
			}
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			JSONArray categoryListJson = new JSONArray();
			if (finalScore > 0.9) {
				data.put("LibraryItem", libraryItem.getInfo(language, libraryItemLanguageRepository,
						libraryCategoryLanguageRepository));
			} else {
				data.put("LibraryItem", "-");

				List<LibraryCategory> categoryList = libraryCategoryRepository
						.findByStatusOrderByTitle(statusRepository.findByCode("STA003").getId(), language);

				for (LibraryCategory category : categoryList) {
					if (category.getLibraryCategory() != null)

						categoryListJson.put(category.getInfo(language, libraryCategoryLanguageRepository));
				}

				data.put("categoryInfoList", categoryListJson);
			}

			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uop_029");

			return json.toString();

		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command getSearchProducts
	 */
	@RequestMapping(value = "/srs_uop_033", method = RequestMethod.POST)
	public String getProducts(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uop_029 - getSearchProducts - : {}";
		String apiKey = "srs_uop_029";
		try {
			// incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			// jsonParam.getJSONObject("data");
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONObject jsonSearchTerm;
			JSONObject jsonListSizeInfo = jsonData.getJSONObject("listSizeInfo");
			JSONObject jsonBranchInfo = jsonUserInfo.getJSONObject("branchInfo");
			JSONObject jsonStatusInfo = jsonData.getJSONObject("statusInfo");
			MetaCountry country = countryRepository.findOne(jsonCountryInfo.getLong("id"));
			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			String searchTerm = "";
			if (jsonData.has("searchTerm")) {
				jsonSearchTerm = jsonData.getJSONObject("searchTerm");
				searchTerm = jsonSearchTerm.getString("searchTerm");
			}
			// select user using username
			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));

			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			int listSize = 0;
			if (jsonData.has("listSizeInfo")) {
				listSize = jsonListSizeInfo.getInt("listSizeInfo");
			}
			Pageable listing = new PageRequest(0, listSize + 30);

			Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));

			JSONArray itemListJson = new JSONArray();
			String timeStamp = jsonData.getString("timeStamp");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = dateFormat.parse(timeStamp);
			String sdate = dateFormat.format(date);
			Date stime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp);
			String time = new SimpleDateFormat("H:mm:ss").format(stime);
			FlashSale flashSale = new FlashSale();
			flashSale = flashSaleRepository.findSale((long) 3, time, sdate);
			Boolean isFlashSale = false;
			if (flashSale != null) {
				isFlashSale = true;
			}
			List<Item> itemList = new ArrayList<Item>();
			if (searchTerm != "")
				itemList = itemRepository.findByBranchAndStatusAndTitleContaining("%" + searchTerm + "%", searchTerm,
						searchTerm + "%", jsonBranchInfo.getLong("id"),
						statusRepository.findByCode(jsonStatusInfo.getString("code")).getId(), language, listing);
			else
				itemList = itemRepository.findByBranchAndStatus(jsonBranchInfo.getLong("id"),
						statusRepository.findByCode(jsonStatusInfo.getString("code")).getId(), language, listing);
			for (Item item : itemList) {

				itemListJson.put(item.getInfo(language, statusRepository.findByCode("STA003"), itemLanguageRepository,
						subOptRepository, subOptLanguageRepository, optLanguageRepository, itemOptSubOptRepository,
						categoryLanguageRepository, branchLanguageRepository, dayLanguageRepository,
						libraryCategoryLanguageRepository, itemRepository, isFlashSale, flashSale,
						flashSaleItemRepository));
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("itemInfoList", itemListJson);
			json.put("data", data);
			json.put("key", "srs_uop_029");

			return json.toString();

		} catch (Exception e) {
			log.warn(warn, e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * QuickEdit
	 */
	@RequestMapping(value = "/srs_uop_032", method = RequestMethod.POST)
	public String QuickEdit(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "api/operators/srs_uop_032 - getoperatorandsuboperator - : {}";
		String apiKey = "/srs_uop_032";
		try {
			User user = (User) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			Branch branch = branchRepository.findOne(param.getBranchInfo().getId());

			Double price = param.getItem().getPrice();
			Long quantity = param.getItem().getQuantity();
			MetaStatus status = metaStatusRepository.findByCode(param.getItem().getStatus().getCode());

			Item item = itemRepository.findOne(param.getItem().getId());
			item.setPrice(price);
			item.setQuantity(quantity);
			item.setStatus(status);
			itemRepository.save(item);

			MetaMessage msg = messageRepository.findByCode("MSG001");
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			return json.toString();
		} catch (Exception e) {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			MetaLanguage language = languageRepository.findByCode("LANG0001");
			JSONObject catchjson = validateUserObject(apiKey, warn, e.getMessage(), language, "MSG003", "error");
			return catchjson.toString();
		}
	}

	/**
	 * SAVE PRODUCT WITH CHECK
	 */
	@RequestMapping(value = "/srs_uop_035", method = RequestMethod.POST)
	public String SaveProductsWithCheck(@RequestBody ItemRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "api/operators//srs_uop_021 - getoperatorandsuboperator - : {}";
		String apiKey = "/srs_uop_021";
		try {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
			JSONObject jsonBranchInfo = jsonUserInfo.getJSONObject("branchInfo");
			JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			JSONArray jsonItemInfoList = jsonData.getJSONArray("itemInfoList");

			MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));

			Optional<User> userOptional = userRepository.findBySessionidAndUsername(jsonUserInfo.getString("sessionid"),
					jsonUserInfo.getString("username"));
			if (!userOptional.isPresent()) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid username", language, "MSG005", "warn");
				return json.toString();
			}

			Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));

			for (int i = 0; i < jsonItemInfoList.length(); i++) {

				long LibraryItemId = jsonItemInfoList.getJSONObject(i).getLong("libraryId");
				LibraryItem libraryItem = libraryItemRepository.findOne(LibraryItemId);
				LibraryItemLanguage libraryItemLanguage = libraryItemLanguageRepository
						.findByItemAndLanguage(libraryItem, language);
				String title = libraryItemLanguage.getTitle();
				String details = libraryItemLanguage.getDetails();
				Double price = jsonItemInfoList.getJSONObject(i).getDouble("price");
				Long quantity = jsonItemInfoList.getJSONObject(i).getLong("quantity");
				Double cost = jsonItemInfoList.getJSONObject(i).getDouble("cost");

				Double DiscountAmount = 0.0;
				Double DiscountPercentage = 0.0;
				if (jsonItemInfoList.getJSONObject(i).has("DiscountAmount")) {
					DiscountAmount = jsonItemInfoList.getJSONObject(i).getDouble("DiscountAmount");
					DiscountPercentage = (jsonItemInfoList.getJSONObject(i).getDouble("Price") - DiscountAmount)
							/ jsonItemInfoList.getJSONObject(i).getDouble("Price");
				}
				if (jsonItemInfoList.getJSONObject(i).has("DiscountPercentage")) {
					DiscountPercentage = jsonItemInfoList.getJSONObject(i).getDouble("DiscountPercentage");
					DiscountAmount = jsonItemInfoList.getJSONObject(i).getDouble("Price")
							- (jsonItemInfoList.getJSONObject(i).getDouble("Price") * (DiscountPercentage / 100));

				}

				Item item = new Item();

				item.setPrice(price);
				item.setQuantity(quantity);
				item.setCost(cost);
				item.setDiscountAmount(DiscountAmount);
				item.setStatus(statusRepository.findByCode("STA109"));
				item.setBranch(branch);
				item.setLibraryItem(libraryItem);
				item.setDiscountPercentage(DiscountPercentage);
				item.setNetSalePrice(price);
				// item.setBrand(brand);
				// item.setKeyFeature(keyFeatures);
				// item.setSku(sku);
				itemRepository.save(item);

				List<MetaLanguage> languageList = languageRepository.findAll();
				List<ItemLanguage> itemLanguageList = new ArrayList<ItemLanguage>();
				for (MetaLanguage lang : languageList) {

					ItemLanguage itemLanguage = new ItemLanguage();
					itemLanguage.setTitle(title);
					itemLanguage.setDetails(details);
					itemLanguage.setItem(item);
					itemLanguage.setLanguage(lang);
					itemLanguageRepository.save(itemLanguage);
					itemLanguageList.add(itemLanguage);
				}
				item.setItemLanguageList(itemLanguageList);

			}

			MetaMessage msg = messageRepository.findByCode("MSG001");
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);
			return json.toString();
		} catch (Exception e) {
			JSONObject jsonParam = new JSONObject(param);
			JSONObject jsonData = jsonParam.getJSONObject("data");
			MetaLanguage language = languageRepository.findByCode("LANG0001");
			JSONObject catchjson = validateUserObject(apiKey, warn, e.getMessage(), language, "MSG003", "error");
			return catchjson.toString();
		}
	}

	private JSONObject setResponse(String apiKey, String warn, String warnMsg, MetaLanguage language, String msgCode,
			String type) {
		log.warn(warn, warnMsg);
		MetaMessage msg = new MetaMessage();
		String value = "";
		if (msgCode != null && language != null) {
			msg = messageRepository.findByCode(msgCode);
			value = messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle();
		}

		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject message = new JSONObject();
		message.put("type", type);
		message.put("value", value);
		data.put("message", message);
		json.put("data", data);
		json.put("key", apiKey);

		// add server timestamp
		json.put("Server Timestamp", statusRepository.getCurrentTime());

		return json;
	}
}
