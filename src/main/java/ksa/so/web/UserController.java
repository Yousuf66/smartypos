package ksa.so.web;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;

import ksa.so.beans.AccountInformation;
import ksa.so.beans.BranchRequest;
import ksa.so.beans.Cart;
import ksa.so.beans.CategoryResponse;
import ksa.so.beans.CustomerOrderRequest;
import ksa.so.beans.ItemBasic;
import ksa.so.beans.ItemRequest;
import ksa.so.beans.ItemResponse;
import ksa.so.beans.LibraryItemResponse;
import ksa.so.beans.ListRequest;
import ksa.so.beans.PlaceOrder;
import ksa.so.beans.ProductRequest;
import ksa.so.beans.SearchResult;
import ksa.so.beans.ShoppingListRequest;
import ksa.so.beans.UserAddressRequest;
import ksa.so.beans.idTitle;
import ksa.so.domain.Branch;
import ksa.so.domain.CloudMessageService;
import ksa.so.domain.CouponList;
import ksa.so.domain.CouponUsers;
import ksa.so.domain.CustomerOrder;
import ksa.so.domain.CustomerOrderHistory;
import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.FlashSale;
import ksa.so.domain.FlashSaleItem;
import ksa.so.domain.Item;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryCategoryLanguage;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemLanguage;
import ksa.so.domain.MetaCancellationReason;
import ksa.so.domain.MetaCancellationReasonLanguage;
import ksa.so.domain.MetaCountry;
import ksa.so.domain.MetaDay;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaMessage;
import ksa.so.domain.MetaRating;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.MetaTime;
import ksa.so.domain.OrderItem;
import ksa.so.domain.OrderItemUpdated;
import ksa.so.domain.OrderStatusLog;
import ksa.so.domain.ShoppingListItem;
import ksa.so.domain.User;
import ksa.so.domain.UserAddress;
import ksa.so.domain.UserApp;
import ksa.so.domain.UserShoppingList;
import ksa.so.domain.Wallet;
import ksa.so.mail.Emails;
import ksa.so.repositories.BranchDayRepository;
import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.BranchRepository;
import ksa.so.repositories.CancellationReasonLanguageRepository;
import ksa.so.repositories.CancellationReasonRepository;
import ksa.so.repositories.CartItemRepository;
import ksa.so.repositories.CategoryLanguageRepository;
import ksa.so.repositories.CategoryRepository;
import ksa.so.repositories.ComboItemRepository;
import ksa.so.repositories.ComboItemSubOptRepository;
import ksa.so.repositories.ComboLanguageRepository;
import ksa.so.repositories.ComboRepository;
import ksa.so.repositories.CouponUsersRepository;
import ksa.so.repositories.CustomerOrderHistoryRepository;
import ksa.so.repositories.CustomerOrderRepository;
import ksa.so.repositories.DiscountCouponLanguageRepository;
import ksa.so.repositories.DiscountCouponRepository;
import ksa.so.repositories.EmailErrorsRepository;
import ksa.so.repositories.FlashSaleItemRepository;
import ksa.so.repositories.FlashSaleLanguageRepository;
import ksa.so.repositories.FlashSaleRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemOptSubOptRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.LibraryCategoryRepository;
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
import ksa.so.repositories.ShoppingListItemRepository;
import ksa.so.repositories.SubOptLanguageRepository;
import ksa.so.repositories.SubOptRepository;
import ksa.so.repositories.UserAddressRepository;
import ksa.so.repositories.UserAppRepository;
import ksa.so.repositories.UserBranchRepository;
import ksa.so.repositories.UserCardRepository;
import ksa.so.repositories.UserRepository;
import ksa.so.repositories.UserShoppingListRepository;
import ksa.so.repositories.WalletRepository;
import ksa.so.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	MetaDayRepository metaDayRepository;

	@Autowired
	CancellationReasonRepository cancellationReasonRepository;

	@Autowired
	BranchDayRepository branchDayRepository;

	@Autowired
	EmailErrorsRepository emailErrorsRepository;

	@Autowired
	BranchLanguageRepository branchLanguageRepository;

	@Autowired
	MetaDayLanguageRepository dayLanguageRepository;

	@Autowired
	LibraryItemRepository libraryItemRepository;

	@Autowired
	LibraryItemLanguageRepository libraryItemLanguageRepository;

	@Autowired
	UserService userService;

	@Autowired
	UserAppRepository userAppRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	MetaUserTypeRepository metaUserTypeRepository;

	@Autowired
	UserAddressRepository userAddressRepository;

	@Autowired
	UserCardRepository userCardRepository;

	@Autowired
	UserBranchRepository userBranchRepository;

	@Autowired
	UserShoppingListRepository userShoppingListRepository;

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
	LibraryCategoryRepository libraryCategoryRepository;

	@Autowired
	LibraryCategoryLanguageRepository libraryCategoryLanguageRepository;

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
	OrderComboRepository orderComboRepository;

	@Autowired
	OrderItemRepository orderItemRepository;

	@Autowired
	OrderItemUpdatedRepository orderItemUpdatedRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	OrderStatusLogRepository orderStatusLogRepository;

	@Autowired
	ShoppingListItemRepository shoppingListItemRepository;

	@Autowired
	OrderItemSubOptRepository orderItemSubOptRepository;

	@Autowired
	CustomerOrderRepository customerOrderRepository;

	@Autowired
	CustomerOrderHistoryRepository customerOrderHistoryRepository;

	@Autowired
	CouponUsersRepository couponUsersRepository;

	@Autowired
	DiscountCouponRepository discountCouponRepository;

	@Autowired
	DiscountCouponLanguageRepository discountCouponLanguageRepository;

	@Autowired
	CancellationReasonLanguageRepository cancellationReasonLanguageRepository;

	@Autowired
	FlashSaleRepository flashSaleRepository;

	@Autowired
	FlashSaleLanguageRepository flashSaleLanguageRepository;

	@Autowired
	FlashSaleItemRepository flashSaleItemRepository;

	@Autowired
	MetaTimeRepository metaTimeRepository;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	Emails sendInvoiceService;

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
	 * command changeNumber
	 */


	@RequestMapping(value = "/srs_uap_014", method = RequestMethod.POST)
	public String changeNumber(@RequestBody AccountInformation param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		try {
			UserApp user = (UserApp) authenticate.getPrincipal();
			// incoming params to json
			/*
			 * JSONObject jsonParam = new JSONObject(param); JSONObject jsonData =
			 * jsonParam.getJSONObject("data"); JSONObject jsonUserInfo =
			 * jsonData.getJSONObject("userAppInfo"); JSONObject jsonUserInfoNew =
			 * jsonData.getJSONObject("userAppInfoNew"); JSONObject jsonLanguageInfo =
			 * jsonData.getJSONObject("languageInfo");
			 *
			 * MetaLanguage language =
			 * languageRepository.findByCode(jsonLanguageInfo.getString("code"));
			 *
			 * UserApp user = new UserApp();
			 *
			 * String token = jsonUserInfo.getString("token");
			 *
			 * // generate code for sms
			 *
			 * JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
			 * JSONObject jsonCountryInfoNew =
			 * jsonUserInfoNew.getJSONObject("countryInfoNew"); String phoneNew =
			 * jsonUserInfoNew.getString("phone");
			 */
			// MetaCountry country =
			// countryRepository.findOne(jsonCountryInfo.getLong("id"));

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			MetaCountry countryNew = countryRepository.findOne(param.getCountryInfo().getId());

			// select user using phone
			// Optional<UserApp> userOptional =
			// userAppRepository.findByPhoneAndCountry(jsonUserInfo.getString("phone"),
			// country);
			String code = randomCode(6);
			if (sendSMS(countryNew.getCode() + param.getPhone(), code)) {
//****			because we are not allowing user to input his old number in mobile application
//				if(!userOptional.isPresent()){
//					user = new UserApp();
//					user.setCountry(country);
//					user.setGender("Male");
//					user.setFirstName("-");
//					user.setLastName("-");
//					user.setUsername("-");
//					user.setPassword("-");
//					user.setPhone(phone);
//				} else {

				user.setPhone(param.getPhone());
				user.setCountry(countryNew);
//					userAppRepository.save(user);
//				}
			} else {
				log.warn("/api/user/srs_uap_014 - changeNumber - : {}", "error sending sms");

				MetaMessage msg = messageRepository.findByCode("MSG007");

				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "warning");
				message.put("code", "MSG007");
				message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
				data.put("message", message);
				json.put("data", data);
				json.put("key", "srs_uap_014");

				return json.toString();
			}

			user.setInstallationId(param.getUser().getInstallationId());
			// String jwtToken = jwtTokenUtil.generateTokenForUserApp(user);
			user.setStatus(statusRepository.findByCode("STA001"));
			user.setOtp(code);

			userAppRepository.save(user);

			JSONObject jsonUserAppInfo = user.getInfo(language, statusLanguageRepository);

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("userAppInfo", jsonUserAppInfo);
			json.put("data", data);
			json.put("key", "srs_uap_014");

			return json.toString();
		} catch (Exception e) {
			log.warn("/api/user/srs_uap_014 - changeNumber - : {}", e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uap_014");
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command update user info
	 */
	@RequestMapping(value = "/srs_uap_003", method = RequestMethod.POST)
	public String updateUserInfo(@RequestBody AccountInformation param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_003 - updateUserInfo - : {}";
		String apiKey = "srs_uap_003";
		try {
			// incoming params to json
			UserApp user = (UserApp) authenticate.getPrincipal();
//			int register = 0;
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
//			if (user.getEmail().equals("-") && user.getFirstName().equals("-")) {
//				register = 1;
//			}
			// select user using phone

			user.setGender(param.getGender());
			user.setFirstName(param.getFirstName());
			user.setLastName(param.getLastName());
			user.setEmail(param.getEmail());

			// save user object
			userAppRepository.save(user);
			// save user object
//			if (register == 1) {
//
//				sendInvoiceService.accountConfirmationEmail(user);
//			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			JSONObject jsonUserAppInfo = user.getInfo(language, statusLanguageRepository);
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("userAppInfo", jsonUserAppInfo);
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
	 * command get branch by id
	 */
	@RequestMapping(value = "/srs_uap_041", method = RequestMethod.POST)
	public String getBranchById(@RequestBody BranchRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uap_041 - getBranchById - : {}";
		String apiKey = "srs_uap_041";
		try {
			// incoming params to json

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			Branch branch = branchRepository.findOne(param.getBranchInfo().getId());
			if (branch == null) {
				JSONObject json = setResponse(apiKey, warn, "Branch doesnot Exist", language, "MSG003", "warning");
				return json.toString();
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("branchInfo", branch.getInfo(language, branchLanguageRepository, dayLanguageRepository, "0.0",
					"0.0", null, false, itemRepository, libraryCategoryLanguageRepository));
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
	 * command add user address
	 */
	@RequestMapping(value = "/srs_uap_036", method = RequestMethod.POST)
	public String addUserAddress(@RequestBody UserAddressRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_036 - addUserAddress - : {}";
		String apiKey = "srs_uap_036";
		try {
			// incoming params to json
			UserApp user = (UserApp) authenticate.getPrincipal();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			String lat = param.getUserAddressInfo().getLat();
			String lon = param.getUserAddressInfo().getLon();
			String placeId = param.getUserAddressInfo().getPlaceId();
			String name = param.getUserAddressInfo().getname();
			String address = param.getUserAddressInfo().getAddress();
		String manualAddress = param.getUserAddressInfo().getManualAddress();

			UserAddress userAddress = new UserAddress();
			if (param.getUserAddressInfo().getId() != null) {
				userAddress = userAddressRepository.findOne(param.getUserAddressInfo().getId());
			}

			userAddress.setUserApp(user);
			userAddress.setAddress(address);
			userAddress.setname(name);
			userAddress.setLat(lat);
			userAddress.setLon(lon);
			userAddress.setPlaceId(placeId);

			userAddress.setStatus(statusRepository.findByCode("STA003"));
			// save user object
			userAddressRepository.save(userAddress);
			// save user object

			List<UserAddress> userAddressList = userAddressRepository.findByUserAndStatus(user,
					statusRepository.findByCode("STA003"));
			JSONArray jsonUserAddressList = new JSONArray();
			for (UserAddress userAddressInfo : userAddressList) {
				jsonUserAddressList.put(userAddressInfo.getInfo());
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();

			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("userAddressInfo", jsonUserAddressList);
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
	 *
	 *
	 * /** command get user address
	 */
	@RequestMapping(value = "/srs_uap_031", method = RequestMethod.POST)
	public String getUserAddressInfo(@RequestBody ListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_031 - getUserAddressInfo - : {}";
		String apiKey = "srs_uap_031";
		try {
			// incoming params to json
			UserApp user = (UserApp) authenticate.getPrincipal();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			// select user address using user
			List<UserAddress> userAddressList = userAddressRepository.findByUserAndStatus(user,
					statusRepository.findByCode("STA003"));

			JSONArray jsonUserAddressInfo = new JSONArray();
			for (UserAddress userAddress : userAddressList) {
				jsonUserAddressInfo.put(userAddress.getInfo());
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("userAddressInfo", jsonUserAddressInfo);
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
	 * command place order (newer)
	 */
	@RequestMapping(value = "/srs_uap_040", method = RequestMethod.POST)
	public String placeOrderFromUser(@RequestBody PlaceOrder param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		// String warn = "/api/user/srs_uap_040 - placeOrder - : {}";
		try {

			UserApp user = (UserApp) authenticate.getPrincipal();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			UserAddress address = userAddressRepository.findOne(param.getUserAddressInfo().getId());

			DiscountCoupon discountCoupon = discountCouponRepository.findOne(param.getDiscountCouponInfo().getId());

			Timestamp pickupTime = Timestamp.valueOf(param.getPickupTime());

			MetaTime time = metaTimeRepository.findOne(param.getTimeSlots().getId());
			MetaDay day = metaDayRepository.findByCode(param.getDay().getCode());

			String instructionAudio = "-";
			String instructionComment = "-";
			// select user using phone

			Branch branch = branchRepository.getOne(param.getBranchInfo().getId());
			if (branch != null) {
				if (branch.getStatus().getCode().equals("STA004")) {
					log.warn("/api/user/srs_uap_007 - placeOrder - : {}", "branch disabled");

					MetaMessage msg = messageRepository.findByCode("MSG008");

					JSONObject json = new JSONObject();
					JSONObject data = new JSONObject();
					JSONObject message = new JSONObject();
					message.put("type", "warning");
					message.put("code", "MSG008");
					message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
					data.put("message", message);
					json.put("data", data);
					json.put("key", "srs_uap_007");

					return json.toString();
				}
			}

			MetaRating rating = ratingRepository.findByCode("RAT001");
			MetaStatus status = statusRepository.findByCode("STA101");

			// order Number
			String orderNumber = "";
			long orderNum = 0;
			if (branch.getOrderNumber() > 998) {
				branch.setOrderNumber(1);
				orderNumber = "ORD-1";
			} else {
				orderNum = branch.getOrderNumber() + 1;
				branch.setOrderNumber(branch.getOrderNumber() + 1);
				orderNumber = "ORD-" + orderNum;
			}
			branchRepository.save(branch);
			// order Number

			// long estimatedTime = getEstimatedTime(branch, lat, lon, arrivalTime);
			User operator = userRepository.findByUserTypeAndBranch(metaUserTypeRepository.findByCode("UT000003"),
					branch);

			if (param.getInstructionAudio() != null) {
				instructionAudio = param.getInstructionAudio();
			}
			if (param.getInstructionComment() != null) {
				instructionComment = param.getInstructionComment();
			}

			Double finaltotal = 0.0;
			if (param.getWalletAmount() < (param.getActualAmount() - param.getDiscountedAmount())
					+ branch.getShippingfees()) {
				finaltotal = ((param.getActualAmount() - param.getDiscountedAmount()) + branch.getShippingfees())
						- param.getWalletAmount();
			} else {
				finaltotal = 0.0;

			}
			CustomerOrder order = new CustomerOrder(0, Timestamp.valueOf(param.getTimeStamp()),
					statusRepository.getCurrentTime(), branch, rating, status, user, 0, 0, orderNumber,
					param.getIsTakeaway(), operator, pickupTime, instructionAudio, instructionComment, address,
					param.getActualAmount(), finaltotal, param.getDiscountedAmount(), discountCoupon, day, time,
					param.getWalletAmount());

			customerOrderRepository.save(order);

			if (param.getWalletAmount() != null) {
				Double walletAmount = param.getWalletAmount();
				Wallet wallet = walletRepository.findByUser(order.getUser());
				if (wallet != null) {
					wallet.setWalletAmount(wallet.getWalletAmount() - walletAmount);
					walletRepository.save(wallet);
				}
			}
			// history
			CustomerOrderHistory orderHistory = new CustomerOrderHistory(statusRepository.getCurrentTime(), order,
					rating, status, "-", false, false, 0, 0, param.getIsTakeaway());
			customerOrderHistoryRepository.save(orderHistory);
			// history

			// item and sub options
			List<OrderItem> orderItemList = new ArrayList<OrderItem>();

			for (int i = 0; i < param.getOrderItemListInfo().size(); i++) {
				Item item = itemRepository.findOne(param.getOrderItemListInfo().get(i).getId());
				OrderItem orderItem = new OrderItem(param.getOrderItemListInfo().get(i).getPrice(),
						param.getOrderItemListInfo().get(i).getCost(),
						param.getOrderItemListInfo().get(i).getQuantity(), item, order);
				orderItemRepository.save(orderItem);

				orderItemList.add(orderItem);

				item.setQuantity(item.getQuantity() - param.getOrderItemListInfo().get(i).getQuantity());
				item.setUpdated(Timestamp.valueOf(param.getTimeStamp()));
				itemRepository.save(item);
			}

			//
			order.setOrderItemList(orderItemList);
			//

			OrderStatusLog orderStatusLog = new OrderStatusLog();
			orderStatusLog.setOrder(order);
			orderStatusLog.setStatus(status);
			orderStatusLog.setTsClient(Timestamp.valueOf(param.getTimeStamp()));
			orderStatusLog.setTsServer(statusRepository.getCurrentTime());
			orderStatusLogRepository.save(orderStatusLog);
			double amountUsed = 0.0;
			double amountrem = 0.0;
			CouponUsers userCouponList = couponUsersRepository.findByUserAndDiscountCoupon(user, discountCoupon);
			if (userCouponList != null) {
				long totalUsage = userCouponList.getUsageNumber();
				amountUsed = userCouponList.getAmountUsed();
				amountrem = userCouponList.getMaxAmount() - amountUsed;
				userCouponList.setUsageNumber(totalUsage + 1);
				double newAmountUsed = amountUsed + param.getDiscountedAmount();
				userCouponList.setAmountUsed(newAmountUsed);
				couponUsersRepository.save(userCouponList);
			}
			MetaMessage msg = messageRepository.findByCode("MSG011");

			JSONObject json = new JSONObject();
			JSONObject orderInfoJson = new JSONObject();
			orderInfoJson.put("id", order.getId());
			json.put("orderInfo", orderInfoJson);// order.getInfo(language, statusRepository, statusLanguageRepository,
													// comboLanguageRepository, orderItemSubOptRepository,
													// subOptLanguageRepository, itemOptSubOptRepository,
													// itemLanguageRepository, branchLanguageRepository,
													// dayLanguageRepository));

			String notiStatus = "-";

			MetaMessage notificationMsg = messageRepository.findByCode("MSG011");
			String title = messageLanguageRepository.findByMessageAndLanguage(notificationMsg, language).getTitle();

			MetaMessage notificationMsgBoby = messageRepository.findByCode("MSG104");
			String body = messageLanguageRepository.findByMessageAndLanguage(notificationMsgBoby, language).getTitle()
					.replace(":orderid", order.getOrderNumber().toString());

			JSONObject notification = new JSONObject();
			notification.put("title", title);
			notification.put("body", body);
			notification.put("sound", "default");
			notification.put("click_action", "FLUTTER_NOTIFICATION_CLICK");

			JSONObject jsonNotification = new JSONObject();
			JSONObject orderStatus = new JSONObject();
			orderStatus.put("orderId", order.getOrderNumber());
			orderStatus.put("key", "srs_uap_007");
			orderStatus.put("statusLog", orderStatusLog.getInfo(language, statusLanguageRepository));
			orderStatus.put("status", order.getStatus().getInfo(language, statusLanguageRepository));
			orderStatus.put("status", order.getStatus().getCode());
			orderStatus.put("title", title);
			orderStatus.put("body", body);
			jsonNotification.put("orderStatus", orderStatus);
			jsonNotification.put("data", notification);
			String deviceToken = "";
			deviceToken = operator.getInstallationId();

			cms.sendMessage(deviceToken, jsonNotification, notification, true);

			sendInvoiceService.sendInvoice(order, language, amountrem, itemLanguageRepository, branchLanguageRepository,
					discountCouponLanguageRepository, emailErrorsRepository);

			msg = messageRepository.findByCode("MSG001");

			json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("notiStatus", notiStatus);
			json.put("jsonCartList", new JSONArray());
			json.put("cartCount", 0);
			json.put("data", data);
			json.put("key", "srs_uap_007");
			return json.toString();

		} catch (

		Exception e) {
			log.warn("/api/user/srs_uap_007 - placeOrder - : {}", e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uap_007");
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	@RequestMapping(value = "/srs_uap_026", method = RequestMethod.POST)
	public String getShoppingList(@RequestBody ListRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uap_026 - getShoppingList - : {}";
		String apiKey = "/api/user/srs_uap_026";
		try {
			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			List<UserShoppingList> userShoppingList = new ArrayList<UserShoppingList>();
			if (param.getSearchTerm() != null) {
				userShoppingList = userShoppingListRepository.findByUserAndTitleContainingIgnoreCase(user,
						"%" + param.getSearchTerm() + "%");
			} else {

				userShoppingList = userShoppingListRepository.findByUser(user);
			}
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("shoppingListInfo", userShoppingList);
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
	 * /** command save shopping list
	 */
	@RequestMapping(value = "/srs_uap_028", method = RequestMethod.POST)
	public String saveShoppingList(@RequestBody ShoppingListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_028 - saveShoppingList - : {}";
		String apiKey = "/api/user/srs_uap_028";
		try {
			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			UserShoppingList userShoppingList = new UserShoppingList();
			if (param.getUserShoppingListInfo() != null && param.getUserShoppingListInfo().getId() != null) {
				userShoppingList = userShoppingListRepository.findOne(param.getUserShoppingListInfo().getId());
			} else {
				String title = param.getUserShoppingListInfo().getTitle();
				// userShoppingList.setBranch(branch);
				userShoppingList.setUserApp(user);
				userShoppingList.setTitle(title);
				userShoppingList.setTsClient(Timestamp.valueOf(param.getTimeStamp()));
				userShoppingList.setTsServer(statusRepository.getCurrentTime());
				userShoppingList.setTsUpd(Timestamp.valueOf(param.getTimeStamp()));
				userShoppingListRepository.save(userShoppingList);
			}

			ArrayList<ShoppingListItem> shoppingListItemList = new ArrayList<ShoppingListItem>();
			for (int i = 0; i < param.getShoppingListItemInfo().size(); i++) {
				LibraryItem libraryItem = libraryItemRepository.findOne(param.getShoppingListItemInfo().get(i).getId());
				Optional<ShoppingListItem> shoppingListItemOptional = shoppingListItemRepository
						.findByItemAndShoppingList(libraryItem, userShoppingList);
				if (shoppingListItemOptional.isPresent()) {
					ShoppingListItem shoppingListItem = shoppingListItemOptional.get();
					shoppingListItem.setQuantity(param.getShoppingListItemInfo().get(i).getQuantity());
					shoppingListItemList.add(shoppingListItem);
				} else {
					ShoppingListItem shoppingListItem = new ShoppingListItem();
					shoppingListItem.setQuantity(param.getShoppingListItemInfo().get(i).getQuantity());
					shoppingListItem.setLibraryItem(libraryItem);
					shoppingListItem.setShoppingList(userShoppingList);
					shoppingListItemList.add(shoppingListItem);
				}
				shoppingListItemRepository.save(shoppingListItemList);
			}

			List<ShoppingListItem> removedShoppingListItemList = new ArrayList<ShoppingListItem>();
			for (int i = 0; i < param.getRemovedShoppingListItemInfo().size(); i++) {
				LibraryItem libraryItem = libraryItemRepository
						.findOne(param.getRemovedShoppingListItemInfo().get(i).getId());
				Optional<ShoppingListItem> shoppingListItem = shoppingListItemRepository
						.findByItemAndShoppingList(libraryItem, userShoppingList);
				if (shoppingListItem.isPresent()) {
					removedShoppingListItemList.add(shoppingListItem.get());
				}
			}
			shoppingListItemRepository.delete(removedShoppingListItemList);
			userShoppingList.setTsUpd(Timestamp.valueOf(param.getTimeStamp()));
			userShoppingListRepository.save(userShoppingList);
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("listInfo", userShoppingList.getInfo(shoppingListItemRepository));
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
	 * /** command save shopping list
	 */
	@RequestMapping(value = "/srs_uap_052", method = RequestMethod.POST)
	public String saveShoppingListCart(@RequestBody ShoppingListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_028 - saveShoppingList - : {}";
		String apiKey = "/api/user/srs_uap_028";
		try {
			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			UserShoppingList userShoppingList = new UserShoppingList();

			String title = param.getUserShoppingListInfo().getTitle();
			// userShoppingList.setBranch(branch);
			userShoppingList.setUserApp(user);
			userShoppingList.setTitle(title);
			userShoppingList.setTsClient(Timestamp.valueOf(param.getTimeStamp()));
			userShoppingList.setTsServer(statusRepository.getCurrentTime());
			userShoppingList.setTsUpd(Timestamp.valueOf(param.getTimeStamp()));
			userShoppingListRepository.save(userShoppingList);

			ArrayList<ShoppingListItem> shoppingListItemList = new ArrayList<ShoppingListItem>();
			for (int i = 0; i < param.getShoppingListItemInfo().size(); i++) {

				Item item = itemRepository.findOne(param.getShoppingListItemInfo().get(i).getId());
				LibraryItem libraryItem = item.getLibraryItem();
				Optional<ShoppingListItem> shoppingListItemOptional = shoppingListItemRepository
						.findByItemAndShoppingList(libraryItem, userShoppingList);
				if (shoppingListItemOptional.isPresent()) {
					ShoppingListItem shoppingListItem = shoppingListItemOptional.get();
					shoppingListItem.setQuantity(param.getShoppingListItemInfo().get(i).getQuantity());
					shoppingListItemList.add(shoppingListItem);
				} else {
					ShoppingListItem shoppingListItem = new ShoppingListItem();
					shoppingListItem.setQuantity(param.getShoppingListItemInfo().get(i).getQuantity());
					shoppingListItem.setLibraryItem(libraryItem);
					shoppingListItem.setShoppingList(userShoppingList);
					shoppingListItemList.add(shoppingListItem);
				}
				shoppingListItemRepository.save(shoppingListItemList);
			}

			List<ShoppingListItem> removedShoppingListItemList = new ArrayList<ShoppingListItem>();
			for (int i = 0; i < param.getRemovedShoppingListItemInfo().size(); i++) {
				LibraryItem libraryItem = libraryItemRepository
						.findOne(param.getRemovedShoppingListItemInfo().get(i).getId());
				Optional<ShoppingListItem> shoppingListItem = shoppingListItemRepository
						.findByItemAndShoppingList(libraryItem, userShoppingList);
				if (shoppingListItem.isPresent()) {
					removedShoppingListItemList.add(shoppingListItem.get());
				}
			}
			shoppingListItemRepository.delete(removedShoppingListItemList);
			userShoppingList.setTsUpd(Timestamp.valueOf(param.getTimeStamp()));
			userShoppingListRepository.save(userShoppingList);
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("listInfo", userShoppingList.getInfo(shoppingListItemRepository));
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
	 * command get shopping list details
	 */
	@RequestMapping(value = "/srs_uap_029", method = RequestMethod.POST)
	public String getShoppingListDetails(@RequestBody ShoppingListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_029 - getShoppingListDetails - : {}";
		String apiKey = "/api/user/srs_uap_029";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			List<ItemBasic> shoppingListItem = shoppingListItemRepository
					.findDetailsByShoppingList(param.getUserShoppingListInfo(), language);

			MetaMessage msg = messageRepository.findByCode("MSG001");
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("listItemInfo", shoppingListItem);
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
	 * command delete shopping list
	 */
	@RequestMapping(value = "/srs_uap_030", method = RequestMethod.POST)
	public String deleteShoppingList(@RequestBody ShoppingListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_030 - deleteShoppingList - : {}";
		String apiKey = "/api/user/srs_uap_030";
		try {
			// incoming params to json
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			UserShoppingList userShoppingList = userShoppingListRepository
					.findOne(param.getUserShoppingListInfo().getId());
			List<ShoppingListItem> shoppingListItem = shoppingListItemRepository.findByShoppingList(userShoppingList);
			shoppingListItemRepository.delete(shoppingListItem);
			userShoppingListRepository.delete(userShoppingList);

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

	@GetMapping("/srs_uap_search")
	@ResponseBody
	public SearchResult searchList(@RequestParam(name = "searchPhrase", required = false) String searchPhrase,
			@RequestParam(name = "categoryId", required = false) Long categoryId,
			@RequestParam(name = "fk_language") Long fk_language, @RequestParam(name = "tag") String tag,
			@RequestParam(name = "branchId", required = false) Long branchId) {

		SearchResult searchResult = new SearchResult();
		searchResult.setItems(userService.searchList(searchPhrase, categoryId, fk_language, branchId, (long) 3));
		searchResult.setTag(tag);
		return searchResult;
	}

	@GetMapping("/srs_uap_searchItem")
	@ResponseBody
	public SearchResult searchList(@RequestParam(name = "searchPhrase", required = false) String searchPhrase,

			@RequestParam(name = "fk_language") Long fk_language, @RequestParam(name = "tag") String tag,
			@RequestParam(name = "branchId", required = false) Long branchId) {

		SearchResult searchResult = new SearchResult();
		searchResult.setItemResponse(userService.searchItemList(searchPhrase, fk_language, branchId));
		searchResult.setTag(tag);
		return searchResult;
	}

	@GetMapping("/srs_uap_006")
	@ResponseBody
	public ArrayList<ItemResponse> getProductsList(@RequestParam(name = "branchId", required = false) Long branchId,
			@RequestParam(name = "categoryId", required = false) Long categoryId,
			@RequestParam(name = "fk_language") Long fk_language, @RequestParam(name = "orderBy") String orderBy) {

		MetaLanguage language = languageRepository.findOne(fk_language);
		Branch branch = null;
		if (branchId != null)
			branch = branchRepository.findOne(branchId);
		LibraryCategory libraryCategory = libraryCategoryRepository.findOne(categoryId);
		// return itemRepository.getItemsList(branchId, (long) 3, language, orderBy);
		if (orderBy.equals("ASC"))
			return itemRepository.getItemsList(branch, libraryCategory, (long) 3, language, null);
		else
			return itemRepository.getItemsListDesc(branch, libraryCategory, (long) 3, language, null);

	}

	@GetMapping("/srs_uap_017")
	@ResponseBody
	public ArrayList<LibraryItemResponse> getLibraryProductList(
			@RequestParam(name = "categoryId", required = false) Long categoryId,
			@RequestParam(name = "fk_language") Long fk_language) {

		MetaLanguage language = languageRepository.findOne(fk_language);

		LibraryCategory libraryCategory = libraryCategoryRepository.findOne(categoryId);
		// return itemRepository.getItemsList(branchId, (long) 3, language, orderBy);

		return libraryItemRepository.getItemsList(libraryCategory, (long) 3, language);

	}

	@GetMapping("/srs_uap_016")
	@ResponseBody
	public Double getWalletInfo(Authentication authenticate) {
		UserApp user = (UserApp) authenticate.getPrincipal();
		Wallet wallet = walletRepository.findByUser(user);
		return wallet.getWalletAmount();

	}

	@GetMapping("/srs_uap_043")
	@ResponseBody
	public ArrayList<ItemResponse> getTopProductList(@RequestParam(name = "branchId") Long branchId,
			@RequestParam(name = "fk_language") Long fk_language) {

		MetaLanguage language = languageRepository.findOne(fk_language);
		// return itemRepository.getItemsList(branchId, (long) 3, language, orderBy);
		Pageable listing = new PageRequest(0, 6);
		return itemRepository.findMostSoldProduct(branchId, (long) 3, language, listing);

	}

	@GetMapping("/srs_uap_044")
	@ResponseBody
	public ArrayList<ItemResponse> getRelatedProductList(@RequestParam(name = "branchId") Long branchId,
			@RequestParam(name = "categoryId") Long categoryId, @RequestParam(name = "fk_language") Long fk_language) {

		MetaLanguage language = languageRepository.findOne(fk_language);
		// return itemRepository.getItemsList(branchId, (long) 3, language, orderBy);
		Pageable listing = new PageRequest(0, 6);
		return itemRepository.findProductsByCategory(branchId, categoryId, (long) 3, language, listing);

	}

	@GetMapping("/srs_uap_018")
	@ResponseBody
	public ArrayList<LibraryItemResponse> getRelatedProductList(@RequestParam(name = "categoryId") Long categoryId,
			@RequestParam(name = "fk_language") Long fk_language) {

		MetaLanguage language = languageRepository.findOne(fk_language);
		// return itemRepository.getItemsList(branchId, (long) 3, language, orderBy);
		Pageable listing = new PageRequest(0, 6);
		return libraryItemRepository.findProductsByCategory(categoryId, (long) 3, language, listing);

	}

	/**
	 * command getSearchResultsItemsLibrary
	 */
	@RequestMapping(value = "/srs_uap_027", method = RequestMethod.POST)
	public String getSearchResultsItemsLibrary(@RequestBody ListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_027 - getSearchResultsItemsBranchWise - : {}";
		String apiKey = "srs_uap_027";
		try {
			String searchTerm = "";
			if (param.getSearchTerm() != null)
				searchTerm = param.getSearchTerm();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			JSONArray itemListJson = new JSONArray();
			List<LibraryItemLanguage> libraryItemLanguageList = libraryItemLanguageRepository
					.findByLanguageAndTitleContainingIgnoreCase(language, searchTerm);
			for (LibraryItemLanguage libraryItemLanguage : libraryItemLanguageList) {
				itemListJson.put(libraryItemLanguage.getItem().getInfo(language, libraryItemLanguageRepository,
						libraryCategoryLanguageRepository));
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
			json.put("key", "srs_uap_022");

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

	/* Category */
	@RequestMapping(value = "/srs_uap_005", method = RequestMethod.POST)
	public String getCategoryList(@RequestBody ListRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			Branch branch = new Branch();
			if (param.getBranchInfo() != null && param.getBranchInfo().getId() != 0) {
				branch = branchRepository.findOne(param.getBranchInfo().getId());
			}

			MetaStatus status = statusRepository.findByCode("STA003");
			if (param.getBranchInfo() == null || param.getBranchInfo().getId() == 0) {
				List<CategoryResponse> mainCategoryList = new ArrayList<CategoryResponse>();
				List<CategoryResponse> categoryList = libraryCategoryRepository
						.findCategoryResponseByStatusOrderByTitle(status.getId(), language);
				for (int i = 0; i < categoryList.size(); i++) {
					List<CategoryResponse> subCategoryList = new ArrayList<CategoryResponse>();
					if (categoryList.get(i).getParentId() == null) {
						mainCategoryList.add(categoryList.get(i));
						for (int j = 0; j < categoryList.size(); j++) {
							if (categoryList.get(i).getId().equals(categoryList.get(j).getParentId())) {
								CategoryResponse subCategory = new CategoryResponse();
								subCategory.setId(categoryList.get(j).getId());
								subCategory.setFileUrl(categoryList.get(j).getFileUrl());
								subCategory.setTitle(categoryList.get(j).getTitle());
								subCategoryList.add(subCategory);
							}
						}

						categoryList.get(i).setSubCategory(subCategoryList);
					}
				}
				MetaMessage msg = messageRepository.findByCode("MSG001");

				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "ack");
				message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
				data.put("message", message);
				data.put("categoryInfoList", mainCategoryList);

				json.put("data", data);
				json.put("key", "srs_uap_005");

				return json.toString();
			} else {
				if (branch.getStatus().getCode().equals("STA004")) {
					log.warn("/api/user/srs_uap_005 - getCategoryList - : {}", "branch disabled");

					MetaMessage msg = messageRepository.findByCode("MSG008");

					JSONObject json = new JSONObject();
					JSONObject data = new JSONObject();
					JSONObject message = new JSONObject();
					message.put("type", "warning");
					message.put("code", "MSG008");
					message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
					data.put("message", message);
					json.put("data", data);
					json.put("key", "srs_uap_005");

					return json.toString();

				}

				List<CategoryResponse> categoryList = libraryCategoryRepository
						.findCategoryResponseByStatusAndBranchOrderByTitle(status.getId(), language, branch.getId());

				List<CategoryResponse> mainCategoryList = new ArrayList<CategoryResponse>();
				for (int i = 0; i < categoryList.size(); i++) {
					Boolean isPresent = false;
					for (int j = 0; j < mainCategoryList.size(); j++) {
						if (mainCategoryList.get(j).getId() == categoryList.get(i).getParentId()) {
							isPresent = true;
							break;
						}
					}
					if (!isPresent) {
						CategoryResponse mainCategory = new CategoryResponse();
						mainCategory.setId(categoryList.get(i).getParentId());
						mainCategory.setFileUrl(categoryList.get(i).getParentFileUrl());
						mainCategory.setTitle(categoryList.get(i).getParentTitle());
						mainCategoryList.add(mainCategory);
					}

				}

				for (int j = 0; j < mainCategoryList.size(); j++) {
					List<CategoryResponse> subCategoryList = new ArrayList<CategoryResponse>();
					for (int i = 0; i < categoryList.size(); i++) {

						if (categoryList.get(i).getParentId() == mainCategoryList.get(j).getId()) {

							CategoryResponse subCategory = new CategoryResponse();
							subCategory.setId(categoryList.get(i).getId());
							subCategory.setFileUrl(categoryList.get(i).getFileUrl());
							subCategory.setTitle(categoryList.get(i).getTitle());
							subCategoryList.add(subCategory);
						}

					}
					mainCategoryList.get(j).setSubCategory(subCategoryList);
				}
//	List<CategoryResponse> subCategoryList = libraryCategoryRepository
//	.findCategoryResponseByStatusAndBranchOrderByTitle(status.getId(), language, branch.getId());

//	for (CategoryResponse category : mainCategoryList)
//	categoryListJson.put(category);
//	for (int i = 0; i < categoryListJson.length(); i++) {
//	JSONArray subCategoryListJson = new JSONArray();
//
//	JSONObject row = categoryListJson.getJSONObject(i);
//	Long ab = row.getLong("id");
//	for (CategoryResponse category : subCategoryList) {
//
////	if (category.getSubCategory() != null)
////	if (ab == category.getSubCategory().getId()) {
////	subCategoryListJson.put(category.getInfo(language, libraryCategoryLanguageRepository));
////
////	}
//
//	}
//	categoryListJson.getJSONObject(i).put("subCategory", subCategoryListJson);
//	}

//	for(int i=0;i<categoryList.size();i++)
//	{
//	for(int j=0;j<mainCategoryList.size();j++) {
//	int k=0;
//	if (mainCategoryList.get(i).getId() != categoryList.get(i).getParentId())
//	{
//	mainCategoryList.add(categoryList.get(i));
//	}
//
//	}
//	}
//	for (int i = 0; i < categoryList.size(); i++) {
//	List<CategoryResponse> subCategoryList = new ArrayList<CategoryResponse>();
//	if (categoryList.get(i).getParentId() == null) {
//	mainCategoryList.add(categoryList.get(i));
//	for (int j = 0; j < categoryList.size(); j++) {
//	if (categoryList.get(i).getId().equals(categoryList.get(j).getParentId())) {
//	CategoryResponse subCategory = new CategoryResponse();
//	subCategory.setId(categoryList.get(j).getId());
//	subCategory.setFileUrl(categoryList.get(j).getFileUrl());
//	subCategory.setTitle(categoryList.get(j).getTitle());
//	subCategoryList.add(subCategory);
//	}
//	}
//	}
//	categoryList.get(i).setSubCategory(subCategoryList);
//
//	}
				MetaMessage msg = messageRepository.findByCode("MSG001");

				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "ack");
				message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
				data.put("message", message);
				data.put("categoryInfoList", mainCategoryList);

				json.put("data", data);
				json.put("key", "srs_uap_005");

				return json.toString();

			}

		}

		catch (

		Exception e) {

			log.warn("/api/user/srs_uap_005 - getCategoryList - : {}", e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uap_005");
// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * /** command Request Product
	 */
	@RequestMapping(value = "/srs_uap_055", method = RequestMethod.POST)
	public String RequestProduct(@RequestBody ProductRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		try {
			UserApp user = (UserApp) authenticate.getPrincipal();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			sendInvoiceService.RequestProduct(param.getProductName(), user);

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);

			json.put("data", data);
			json.put("key", "srs_uap_005");

			return json.toString();
		} catch (Exception e) {
			log.warn("/api/user/srs_uap_005 - getCategoryList - : {}", e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uap_005");
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command cancel order
	 */
	@RequestMapping(value = "/srs_uap_008", method = RequestMethod.POST)
	public String cancelOrder(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_008 - cancelOrder - : {}";
		String apiKey = "srs_uap_008";
		try {
			// incoming params to json
			UserApp user = (UserApp) authenticate.getPrincipal();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			CustomerOrder order = customerOrderRepository.findByUserAndId(user, param.getOrderInfo().getId());

			if (order == null) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid order", language, "MSG009");
				return json.toString();
			}

			if (!(order.getStatus().getCode().equals("STA101") || order.getStatus().getCode().equals("STA107"))) {
				MetaMessage msg = messageRepository.findByCode("MSG010");
				String statusTitle = statusLanguageRepository.findByStatusAndLanguage(order.getStatus(), language)
						.getTitle();

				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "warn");
				message.put("value",
						messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle() + statusTitle);
				data.put("message", message);
				json.put("data", data);
				json.put("key", "srs_uap_008");

				return json.toString();
			}

			if (!order.getStatus().getCode().equals("STA105")) {
				CouponUsers couponUser = new CouponUsers();
				couponUser = couponUsersRepository.findByUserAndDiscountCoupon(user, order.getDiscountCoupon());
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
			String statusCode = "STA105";
			MetaStatus status = statusRepository.findByCode(statusCode);
			if (param.getMetaCancellationReasonInfo() != null) {

				MetaCancellationReason metaCancellationReason = cancellationReasonRepository
						.findByCode(param.getMetaCancellationReasonInfo().getCode());
				order.setCancellationReason(metaCancellationReason);
			}

			order.setStatus(status);
			customerOrderRepository.save(order);

			List<OrderItem> orderItem = orderItemRepository.findByOrder(order);
			for (int i = 0; i < orderItem.size(); i++) {
				Item item = orderItem.get(i).getItem();
				item.setQuantity(item.getQuantity() + orderItem.get(i).getQuantity());
				item.setUpdated(Timestamp.valueOf(param.getTimeStamp()));
				itemRepository.save(item);
			}

			// history
			CustomerOrderHistory orderHistory = new CustomerOrderHistory(statusRepository.getCurrentTime(), order,
					order.getRating(), status, order.getReview(), order.isReviewed(), order.hasCustomerArrived(),
					order.getLat(), order.getLon(), false);
			customerOrderHistoryRepository.save(orderHistory);
			// history

			OrderStatusLog orderStatusLog = new OrderStatusLog();
			orderStatusLog.setOrder(order);
			orderStatusLog.setStatus(status);
			orderStatusLog.setTsClient(Timestamp.valueOf(param.getTimeStamp()));
			orderStatusLog.setTsServer(statusRepository.getCurrentTime());
			orderStatusLogRepository.save(orderStatusLog);

			MetaMessage msg = messageRepository.findByCode("MSG011");

			JSONObject json = new JSONObject();
			JSONObject orderInfoJson = new JSONObject();

			orderInfoJson.put("id", order.getId());
			orderInfoJson.put("statusInfo",
					statusRepository.findByCode(statusCode).getInfo(language, statusLanguageRepository));
			json.put("orderInfo", orderInfoJson);

			String notiStatus = "-";

			MetaMessage notificationMsg = messageRepository.findByCode("MSG011");
			String title = messageLanguageRepository.findByMessageAndLanguage(notificationMsg, language).getTitle();

			MetaMessage notificationMsgBoby = messageRepository.findByCode("MSG105");
			String body = messageLanguageRepository.findByMessageAndLanguage(notificationMsgBoby, language).getTitle()
					.replace(":orderid", order.getOrderNumber().toString());

			JSONObject notification = new JSONObject();
			notification.put("title", title);
			notification.put("body", body);
			notification.put("sound", "default");
			notification.put("click_action", "FLUTTER_NOTIFICATION_CLICK");

			JSONObject jsonNotification = new JSONObject();
			JSONObject orderStatus = new JSONObject();
			orderStatus.put("orderId", order.getId());
			orderStatus.put("key", "srs_uap_008");
			orderStatus.put("statusLog", orderStatusLog.getInfo(language, statusLanguageRepository));
			orderStatus.put("status", order.getStatus().getInfo(language, statusLanguageRepository));
			orderStatus.put("status", order.getStatus().getCode());
			orderStatus.put("title", title);
			orderStatus.put("body", body);
			jsonNotification.put("orderStatus", orderStatus);
			jsonNotification.put("data", notification);
			User operator = userRepository.findByUserTypeAndBranch(metaUserTypeRepository.findByCode("UT000003"),
					order.getBranch());
			String deviceToken = "";
			deviceToken = operator.getInstallationId();

			cms.sendMessage(deviceToken, jsonNotification, notification, true);
			sendInvoiceService.sendCancelInvoice(order, language, itemLanguageRepository);
			msg = messageRepository.findByCode("MSG001");

			json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("notiStatus", notiStatus);
			data.put("orderInfo", orderInfoJson);
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
	 * command get order list
	 */

	@RequestMapping(value = "/srs_uap_009", method = RequestMethod.POST)
	public String getOrderList(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_009 - getOrderList - : {}";
		String apiKey = "srs_uap_009";
		try {
			// incoming params to json
			// incoming params to json
			UserApp user = (UserApp) authenticate.getPrincipal();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			List<MetaStatus> metaStatusList = statusRepository.findByCodeIn(param.getMetaStatusListInfo());

			//// Pageable listing = new PageRequest(param.getListStart(),
			//// param.getListSize());

			List<CustomerOrder> orderList = customerOrderRepository.findByUserAndStatusInOrderByTsClientDesc(user,
					metaStatusList);
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
			JSONArray orderInfoList = new JSONArray();
			for (CustomerOrder order : orderList) {
				orderInfoList.put(order.getInfo(language, statusRepository, statusLanguageRepository,
						itemLanguageRepository, branchLanguageRepository, dayLanguageRepository, false, true,
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
			data.put("orderInfoList", orderInfoList);
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
	 * command get order info
	 */
	@RequestMapping(value = "/srs_uap_010", method = RequestMethod.POST)
	public String getOrderInfo(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_010 - getOrderInfo - : {}";
		String apiKey = "srs_uap_010";
		try {
			// incoming params to json
			UserApp user = (UserApp) authenticate.getPrincipal();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			CustomerOrder order = customerOrderRepository.findByUserAndId(user, param.getOrderInfo().getId());

			if (order == null) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid order", language, "MSG009");
				return json.toString();
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");
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
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("orderInfo",
					order.getInfo(language, statusRepository, statusLanguageRepository, itemLanguageRepository,
							branchLanguageRepository, dayLanguageRepository, true, true, orderStatusLogRepository,
							orderItemRepository, (order.getStatus().getCode().equals("STA107") ? true : false),
							orderItemUpdatedRepository, itemRepository, libraryCategoryLanguageRepository,
							cancellationReasonLanguageRepository, walletRepository, isFlashSale, flashSale,
							flashSaleItemRepository));
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
	 * command rate order
	 */
	@RequestMapping(value = "/srs_uap_011", method = RequestMethod.POST)
	public String rateOrder(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_011 - rateOrder - : {}";
		String apiKey = "srs_uap_011";
		try {
			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			CustomerOrder order = customerOrderRepository.findByUserAndId(user, param.getOrderInfo().getId());

			String feedback = "-";

			if (param.getOrderInfo().getFeedback() != null)
				feedback = param.getOrderInfo().getFeedback();

			if (order == null) {
				JSONObject json = validateUserObject(apiKey, warn, "invalid order", language, "MSG009");
				return json.toString();
			}

			MetaRating rating = ratingRepository.findByCode(param.getOrderInfo().getRating().getCode());
			order.setRating(rating);
			order.setReview(param.getOrderInfo().getReview());
			order.setReviewed(true);
			order.setFeedback(feedback);
			customerOrderRepository.save(order);

			// history
			CustomerOrderHistory orderHistory = new CustomerOrderHistory(statusRepository.getCurrentTime(), order,
					rating, order.getStatus(), param.getOrderInfo().getReview(), true, order.hasCustomerArrived(),
					order.getLat(), order.getLon(), false);
			customerOrderHistoryRepository.save(orderHistory);
			// history

			Branch branch = order.getBranch();
			branch.setRating(customerOrderRepository.getAverageRating(branch.getId()));
			branchRepository.save(branch);
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

	private boolean sendSMS(String phoneNumber, String code) {
		try {
			String ACCOUNT_SID = twilioSid.trim();
			String AUTH_TOKEN = twilioToken.trim();
			// String PHONE_FROM = twilioFrom.trim();

			Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

			/*
			 * Message message = Message.creator(new PhoneNumber(phoneNumber), // to new
			 * PhoneNumber(PHONE_FROM), // from "Your verification code is " +
			 * code).create();
			 */

			return true;
		} catch (Exception e) {
			return false;
		}
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

	private JSONObject validateUserObject(String apiKey, String warn, String warnMsg, MetaLanguage language,
			String msgCode) {
		log.warn(warn, warnMsg);

		MetaMessage msg = messageRepository.findByCode(msgCode);

		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject message = new JSONObject();
		message.put("type", "error");
		message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
		data.put("message", message);
		json.put("data", data);
		json.put("key", apiKey);

		// add server timestamp
		json.put("Server Timestamp", statusRepository.getCurrentTime());

		return json;
	}

	/**
	 * command getBranchList
	 */
	@RequestMapping(value = "/srs_uap_039", method = RequestMethod.POST)
	public String getBranchList(@RequestBody ListRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uap_039 - getBranchList - : {}";
		String apiKey = "srs_uap_039";
		try {

			// incoming params to json
			// JSONObject jsonParam = new JSONObject(param);
			// jsonParam.getJSONObject("data");
			// JSONObject jsonData = jsonParam.getJSONObject("data");
			/* JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo"); */
			/* JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo"); */
			// JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
			// JSONObject jsonSearchTerm = new JSONObject();
			String searchTerm = "";
			if (param.getSearchTerm() != null)
				searchTerm = param.getSearchTerm();

			/*
			 * MetaCountry country =
			 * countryRepository.findOne(jsonCountryInfo.getLong("id")); String phone =
			 * jsonUserInfo.getString("phone"); String otp = jsonUserInfo.getString("otp");
			 */

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			// select user using phone
			/*
			 * Optional<UserApp> userOptional =
			 * userAppRepository.findByPhoneAndCountry(phone, country);
			 */
			// Validate user object
			/*
			 * if (!userOptional.isPresent()) { JSONObject json = validateUserObject(apiKey,
			 * warn, "invalid phone", language, "MSG005"); return json.toString(); }
			 *
			 * UserApp user = userOptional.get(); if (!user.getOtp().equals(otp)) {
			 * JSONObject json = validateUserObject(apiKey, warn, "invalid otp", language,
			 * "MSG004"); return json.toString(); }
			 *
			 * if (user.getStatus().getCode().equals("STA004")) { JSONObject json =
			 * validateUserObject(apiKey, warn, "user disabled", language, "MSG006"); return
			 * json.toString(); }
			 */
			// get the list of stores
			List<Branch> branchList;
			MetaStatus status = statusRepository.findByCode("STA003");
			if (!searchTerm.equals("")) {
				branchList = branchRepository.findBranchByTitle(status.getId(), language, "%" + searchTerm + "%");

			} else {
				branchList = branchRepository.findByStatus(status);
			}

			JSONArray branchListJson = new JSONArray();

			for (Branch branch : branchList) {
				branchListJson.put(branch.getBasicInfo(language, branchLanguageRepository, itemRepository,
						libraryCategoryLanguageRepository, false));
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

	/*
	 * command re order amended order
	 */
	@RequestMapping(value = "/srs_uap_037", method = RequestMethod.POST)
	public String reOrderAmendedOrder(@RequestBody CustomerOrderRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_037 - reOrderAmendedOrder - : {}";
		String apiKey = "/api/user/srs_uap_037";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			CustomerOrder order = customerOrderRepository.findOne(param.getOrderInfo().getId());

			String statusCode = "STA101";
			MetaStatus status = statusRepository.findByCode(statusCode);
			order.setStatus(status);
			customerOrderRepository.save(order);

			OrderStatusLog orderStatusLog = new OrderStatusLog();
			orderStatusLog.setOrder(order);
			orderStatusLog.setStatus(status);
			orderStatusLog.setTsClient(Timestamp.valueOf(param.getTimeStamp()));
			orderStatusLog.setTsServer(statusRepository.getCurrentTime());
			orderStatusLogRepository.save(orderStatusLog);

			// update order items
			List<OrderItem> orderItemList = orderItemRepository.findByOrder(order);
			List<OrderItem> orderItemUpdatedList = new ArrayList<>();
			for (int i = 0; i < orderItemList.size(); i++) {
				OrderItem orderItem = orderItemList.get(i);
				OrderItemUpdated orderItemUpdated = orderItemUpdatedRepository.findByOrderAndItem(order,
						orderItem.getItem());
				if (orderItemUpdated.getIsAvailable()) {
					orderItem.setQuantity(orderItemUpdated.getQuantity());
					orderItemUpdatedList.add(orderItem);
				} else {
					orderItemRepository.delete(orderItem);
				}
			}

			orderItemRepository.save(orderItemUpdatedList);
			//
			order.setOrderItemList(orderItemList);
			//
			List<OrderItemUpdated> deleteOrderItemUpdatedList = orderItemUpdatedRepository.findByOrder(order);
			orderItemUpdatedRepository.delete(deleteOrderItemUpdatedList);

			MetaMessage msg = messageRepository.findByCode("MSG011");

			JSONObject json = new JSONObject();
			JSONObject orderInfoJson = new JSONObject();
			orderInfoJson.put("id", order.getId());
			json.put("orderInfo", orderInfoJson);// order.getInfo(language, statusRepository, statusLanguageRepository,
													// comboLanguageRepository, orderItemSubOptRepository,
													// subOptLanguageRepository, itemOptSubOptRepository,
													// itemLanguageRepository, branchLanguageRepository,
													// dayLanguageRepository));

			String notiStatus = "-";
			JSONObject jsonNotification = new JSONObject();
			JSONObject orderStatus = new JSONObject();
			orderStatus.put("orderId", order.getId());
			orderStatus.put("key", "srs_uap_007");
			jsonNotification.put("orderStatus", orderStatus);

			MetaMessage notificationMsgBoby = messageRepository.findByCode("MSG104");

			MetaMessage notificationMsg = messageRepository.findByCode("MSG011");
			JSONObject notification = new JSONObject();
			notification.put("title",
					messageLanguageRepository.findByMessageAndLanguage(notificationMsg, language).getTitle());
			notification.put("body", messageLanguageRepository.findByMessageAndLanguage(notificationMsgBoby, language)
					.getTitle().replace(":orderid", order.getOrderNumber().toString()));
			notification.put("sound", "default");
			notification.put("click_action", "FLUTTER_NOTIFICATION_CLICK");
			jsonNotification.put("data", notification);
			String deviceToken = "";
			User operator = userRepository.findByUserTypeAndBranch(metaUserTypeRepository.findByCode("UT000003"),
					order.getBranch());
			deviceToken = operator.getInstallationId();

			cms.sendMessage(deviceToken, jsonNotification, notification, true);

			msg = messageRepository.findByCode("MSG001");

			json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("notiStatus", notiStatus);
			json.put("jsonCartList", new JSONArray());
			json.put("cartCount", 0);
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

	/*
	 * command delete user address
	 */
	@RequestMapping(value = "/srs_uap_042", method = RequestMethod.POST)
	public String deleteUserAddress(@RequestBody UserAddressRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_042 - addUserAddress - : {}";
		String apiKey = "srs_uap_036";
		try {
			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			UserAddress userAddress = new UserAddress();
			userAddress = userAddressRepository.findOne(param.getUserAddressInfo().getId());
			List<CustomerOrder> customerOrder = customerOrderRepository.findByAddress(userAddress);
			if (customerOrder != null && customerOrder.size() != 0) {
				userAddress.setStatus(statusRepository.findByCode("STA004"));
				userAddressRepository.save(userAddress);
			} else {
				userAddressRepository.delete(userAddress);
			}
			// save user object

			// List<UserAddress> userAddressList = user.getAddress();
			List<UserAddress> userAddressList = userAddressRepository.findByUserAndStatus(user,
					statusRepository.findByCode("STA003"));
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			JSONArray jsonUserAddressList = new JSONArray();
			for (UserAddress useraddress : userAddressList) {

				jsonUserAddressList.put(useraddress.getInfo());

			}
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("userAddressInfo", jsonUserAddressList);
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

	/*
	 * command get Coupons
	 */
	@RequestMapping(value = "/srs_uap_045", method = RequestMethod.POST)
	public String getCoupon(@RequestBody ListRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uap_045 - getCoupon - : {}";
		String apiKey = "/api/user/srs_uap_045";
		try {
			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			JSONArray couponListInfo = new JSONArray();

			List<CouponUsers> userCouponList = couponUsersRepository.findByUser(user);
			for (CouponUsers list : userCouponList) {

				couponListInfo.put(list.getInfo(discountCouponRepository, language, discountCouponLanguageRepository,
						branchLanguageRepository, dayLanguageRepository));

			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("couponListInfo", couponListInfo);
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

	@RequestMapping(value = "/srs_uap_047", method = RequestMethod.POST)
	public String checkoutBC(@RequestBody ListRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uap_046 - getCoupon - : {}";
		String apiKey = "/api/user/srs_uap_046";
		try {
			// incoming params to json
			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			Branch branch = branchRepository.findOne(param.getBranchInfo().getId());

			List<CouponList> userCouponList = discountCouponRepository.findByUserAndBranch(branch, user, language);
			JSONArray couponListInfo = new JSONArray();
			for (CouponList cart : userCouponList) {
				couponListInfo.put(cart.getInfo());
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			Wallet wallet = walletRepository.findByUser(user);
			data.put("walletAmount", wallet.getWalletAmount());
			data.put("couponListInfo", couponListInfo);
			data.put("branchInfo", branch.getInfo(language, branchLanguageRepository, dayLanguageRepository, "0.0",
					"0.0", null, false, itemRepository, libraryCategoryLanguageRepository));
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
	 * command update shopping list name
	 */
	@RequestMapping(value = "/srs_uap_048", method = RequestMethod.POST)
	public String updateShoppingListName(@RequestBody ShoppingListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_048 - updateShoppingListName - : {}";
		String apiKey = "/api/user/srs_uap_048";
		try {
			// incoming params to json
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			UserShoppingList userShoppingList = new UserShoppingList();
//

			userShoppingList = userShoppingListRepository.findOne(param.getUserShoppingListInfo().getId());

			String title = param.getUserShoppingListInfo().getTitle();

			userShoppingList.setTitle(title);
			userShoppingList.setTsServer(statusRepository.getCurrentTime());
			userShoppingList.setTsUpd(Timestamp.valueOf(param.getTimeStamp()));
			userShoppingListRepository.save(userShoppingList);

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("listInfo", userShoppingList.getInfo(shoppingListItemRepository));
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
	 * Cancel Message Responce
	 */
	@RequestMapping(value = "/srs_uap_049", method = RequestMethod.POST)
	public String cancelmessage(@RequestBody ShoppingListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_049 - cancelmessage - : {}";
		String apiKey = "api/user/srs_uap_049";
		try {

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			List<MetaCancellationReasonLanguage> cancellationReasonResponce = cancellationReasonLanguageRepository
					.findByLanguage(language);
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("Responce", cancellationReasonResponce);
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
			json.put("key", apiKey);
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command updateCartPrices
	 */
	@RequestMapping(value = "/srs_uap_038", method = RequestMethod.POST)
	public String updateCartPrices(@RequestBody Cart param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uap_038 - updateCartPrices - : {}";
		String apiKey = "srs_uap_038";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			List<Item> jsonCartList = new ArrayList<Item>();
			if (param.getItemListInfo() != null && param.getItemListInfo().size() != 0) {
				for (int i = 0; i < param.getItemListInfo().size(); i++) {

					jsonCartList.add(itemRepository.findById(param.getItemListInfo().get(i).getId()).get());
				}
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
			JSONArray JsonCartUpdatedList = new JSONArray();
			for (Item item : jsonCartList) {
				JsonCartUpdatedList.put(item.getPriceInfo(isFlashSale, flashSale, flashSaleItemRepository));
			}
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());

			data.put("cartListInfo", JsonCartUpdatedList);
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

	@RequestMapping(value = "/srs_uap_050", method = RequestMethod.POST)
	public String getFlashSaleItemsList(@RequestBody ListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		try {
			String searchTerm = "";
			if (param.getSearchTerm() != null)
				searchTerm = param.getSearchTerm();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			MetaStatus status = statusRepository.findByCode("STA003");
			// LibraryCategory libraryCategory =
			// libraryCategoryRepository.findOne(jsonCategoryeInfo.getLong("id"));
			List<FlashSaleItem> itemList = new ArrayList<FlashSaleItem>();
			JSONArray itemListJson = new JSONArray();

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
				if (searchTerm == "")
					itemList = flashSaleItemRepository.findByFlashSale(flashSale);
				else
					itemList = flashSaleItemRepository.findFlashSaleItemBySearch("%" + searchTerm + "%", searchTerm,
							searchTerm + "%", (long) 3, flashSale.getId(), language);
				for (FlashSaleItem item : itemList) {

					itemListJson.put(item.getItem().getInfo(language, status, itemLanguageRepository, subOptRepository,
							subOptLanguageRepository, optLanguageRepository, itemOptSubOptRepository,
							categoryLanguageRepository, branchLanguageRepository, dayLanguageRepository,
							libraryCategoryLanguageRepository, itemRepository, isFlashSale, flashSale,
							flashSaleItemRepository));
				}
			}
//

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("flashSaletemInfoList", itemListJson);
			json.put("data", data);
			json.put("key", "srs_uap_006");

			return json.toString();
		} catch (Exception e) {
			log.warn("/api/user/srs_uap_006 - getItemList - : {}", e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uap_006");
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command get items availability
	 */
	@RequestMapping(value = "/srs_uap_051", method = RequestMethod.POST)
	public String getItemsAvailabilityInBranch(@RequestBody ShoppingListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_051 - getItemsAvailability - : {}";
		String apiKey = "srs_uap_032";
		try {

			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());

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

			JSONArray ShoppingItemListWithBranch = new JSONArray();

			// UserShoppingList shoppingList =
			// userShoppingListRepository.findOne(param.getUserShoppingListInfo().getId());
			List<ShoppingListItem> shoppingListItems = shoppingListItemRepository
					.findByShoppingList(param.getUserShoppingListInfo());

			for (int i = 0; i < param.getBranchListInfo().size(); i++) {
				Branch branch = branchRepository.findOne(param.getBranchListInfo().get(i).getId());

				List<Object> ShoppingItemList = new ArrayList<>();

				for (ShoppingListItem listItem : shoppingListItems) {
					JSONObject shoppingItem = new JSONObject();
					LibraryItemLanguage libraryItemLanguage = libraryItemLanguageRepository
							.findByItemAndLanguage(listItem.getLibraryItem(), language);
					shoppingItem.put("quantity", listItem.getQuantity());
					shoppingItem.put("libraryItemID", listItem.getLibraryItem().getId());
					shoppingItem.put("title", libraryItemLanguage.getTitle());
					shoppingItem.put("branchInfo", branch.getBasicInfo(language, branchLanguageRepository,
							itemRepository, libraryCategoryLanguageRepository, false));
					Item item = itemRepository.findByLibraryItemAndBranchAndStatus(listItem.getLibraryItem(), branch,
							statusRepository.findByCode("STA003"));
					System.out.println(listItem.getId());
					if (item != null)
						System.out.println(item.getId());
					shoppingItem.put("itemInfo",
							listItem.getInfo(language, libraryItemLanguageRepository, item, itemLanguageRepository,
									branchLanguageRepository, dayLanguageRepository, libraryCategoryLanguageRepository,
									itemRepository, isFlashSale, flashSale, flashSaleItemRepository));

					List<CouponList> userCouponList = discountCouponRepository.findByUserAndBranch(branch, user,
							language);
					JSONArray couponListInfo = new JSONArray();
					for (CouponList cart : userCouponList) {
						couponListInfo.put(cart.getInfo());
					}
					shoppingItem.put("couponListInfo", couponListInfo);
					ShoppingItemList.add(shoppingItem);
				}

				ShoppingItemListWithBranch.put(ShoppingItemList);
			}

//

			data.put("shoppingListInfo", ShoppingItemListWithBranch);
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
	 * command get items availability
	 */
	@RequestMapping(value = "/srs_uap_019", method = RequestMethod.POST)
	public String getCartBranch(@RequestBody ShoppingListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_019 - getItemsAvailability - : {}";
		String apiKey = "srs_uap_032";
		try {

			UserApp user = (UserApp) authenticate.getPrincipal();
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());

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

			JSONArray ShoppingItemListWithBranch = new JSONArray();

			// UserShoppingList shoppingList =
			// userShoppingListRepository.findOne(param.getUserShoppingListInfo().getId());
			List<ShoppingListItem> shoppingListItems = new ArrayList<>();
			for (int i = 0; i < param.getShoppingListItemInfo().size(); i++) {
				ShoppingListItem shoppingListItem = new ShoppingListItem();
				LibraryItem libraryItem = libraryItemRepository.findOne(param.getShoppingListItemInfo().get(i).getId());
				shoppingListItem.setLibraryItem(libraryItem);

				shoppingListItem.setQuantity(param.getShoppingListItemInfo().get(i).getQuantity());
				shoppingListItems.add(shoppingListItem);
			}

			for (int i = 0; i < param.getBranchListInfo().size(); i++) {
				Branch branch = branchRepository.findOne(param.getBranchListInfo().get(i).getId());

				List<Object> ShoppingItemList = new ArrayList<>();

				for (ShoppingListItem listItem : shoppingListItems) {
					JSONObject shoppingItem = new JSONObject();
					LibraryItemLanguage libraryItemLanguage = libraryItemLanguageRepository
							.findByItemAndLanguage(listItem.getLibraryItem(), language);
					shoppingItem.put("quantity", listItem.getQuantity());
					shoppingItem.put("title", libraryItemLanguage.getTitle());
					shoppingItem.put("libraryItemID", listItem.getLibraryItem().getId());
					shoppingItem.put("branchInfo", branch.getBasicInfo(language, branchLanguageRepository,
							itemRepository, libraryCategoryLanguageRepository, false));
					Item item = itemRepository.findByLibraryItemAndBranchAndStatus(listItem.getLibraryItem(), branch,
							statusRepository.findByCode("STA003"));
					System.out.println(listItem.getId());
					if (item != null)
						System.out.println(item.getId());
					shoppingItem.put("itemInfo",
							listItem.getInfo(language, libraryItemLanguageRepository, item, itemLanguageRepository,
									branchLanguageRepository, dayLanguageRepository, libraryCategoryLanguageRepository,
									itemRepository, isFlashSale, flashSale, flashSaleItemRepository));

					List<CouponList> userCouponList = discountCouponRepository.findByUserAndBranch(branch, user,
							language);
					JSONArray couponListInfo = new JSONArray();
					for (CouponList cart : userCouponList) {
						couponListInfo.put(cart.getInfo());
					}
					shoppingItem.put("couponListInfo", couponListInfo);
					ShoppingItemList.add(shoppingItem);
				}

				ShoppingItemListWithBranch.put(ShoppingItemList);
			}

//

			data.put("shoppingListInfo", ShoppingItemListWithBranch);
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

	@RequestMapping(value = "/srs_uap_002", method = RequestMethod.POST)
	public String getSearchItemNameList(@RequestBody ListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		try {
			String searchTerm = "";
			if (param.getSearchTerm() != null)
				searchTerm = param.getSearchTerm();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			MetaStatus status = statusRepository.findByCode("STA003");
			List<LibraryItem> itemList = new ArrayList<LibraryItem>();
			JSONArray itemListJson = new JSONArray();
			itemList = libraryItemRepository.getItemTitle(searchTerm + "%", language, status.getId());
			for (LibraryItem item : itemList) {
				itemListJson.put(item.getIdAndTitle(language, libraryItemLanguageRepository));
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
			json.put("key", "srs_uap_006");

			return json.toString();
		} catch (Exception e) {
			log.warn("/api/user/srs_uap_006 - getItemList - : {}", e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uap_006");
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}

	/**
	 * command getBranchList
	 */
	@RequestMapping(value = "/srs_uap_004", method = RequestMethod.POST)
	public String getTopStores(@RequestBody ListRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		String warn = "/api/user/srs_uap_004 - getBranchList - : {}";
		String apiKey = "srs_uap_039";
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			List<Branch> branchList;
			MetaStatus status = statusRepository.findByCode("STA003");

			// Pageable listing = new PageRequest(0, 2);

			branchList = branchRepository.findByStatusOrderByOrderNumberDesc(status);

			JSONArray branchListJson = new JSONArray();

			for (Branch branch : branchList) {

				branchListJson.put(branch.getBasicInfo(language, branchLanguageRepository, itemRepository,
						libraryCategoryLanguageRepository, false));
			}
			//

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
	 * command getBranchList
	 */
	@RequestMapping(value = "/srs_uap_012", method = RequestMethod.POST)
	public String getStoreDetailsPageInfo(@RequestBody ListRequest param, HttpServletRequest req,
			Authentication authenticate) throws Exception {
		String warn = "/api/user/srs_uap_004 - getBranchList - : {}";
		String apiKey = "srs_uap_039";
		try {

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			Branch branch = branchRepository.findOne(param.getBranchInfo().getId());
			if (branch == null) {
				JSONObject json = setResponse(apiKey, warn, "Branch doesnot Exist", language, "MSG003", "warning");
				return json.toString();
			}
			//

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			// Pageable listing = new PageRequest(0, 1);
			List<Item> items = itemRepository.findMostSoldCategory(branch.getId(),(long) 3);
			LibraryCategoryLanguage libraryCategoryLanguage = new LibraryCategoryLanguage();
			for (Item item : items) {
				libraryCategoryLanguage = libraryCategoryLanguageRepository
						.findByLibraryCategoryAndLanguage(item.getLibraryItem().getLibraryCategory(), language);
				break;
			}
			if (libraryCategoryLanguage != null)
				json.put("mostSoldCategory", libraryCategoryLanguage.getTitle());
			else
				json.put("mostSoldCategory", "-");
			Object[] shippingRate = branchLanguageRepository.shippingRate(branch.getId());
			json.put("shippingRate", shippingRate[0]);

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

	/**
	 * command get category item list
	 */
	@RequestMapping(value = "/srs_uap_013", method = RequestMethod.POST)
	public String getSearchItemList(@RequestBody ItemRequest param, HttpServletRequest req, Authentication authenticate)
			throws Exception {
		try {
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			String orderBy = "";
			Branch branch = new Branch();
			if (param.getOrderBy() != null) {
				orderBy = param.getOrderBy();
			}

			if (param.getBranchInfo() != null && param.getBranchInfo().getId() != 0) {

				branch = branchRepository.findOne(param.getBranchInfo().getId());
				if (branch == null) {
					log.warn("/api/user/srs_uap_006 - getItemList - : {}", "invalid branch");

					MetaMessage msg = messageRepository.findByCode("MSG008");

					JSONObject json = new JSONObject();
					JSONObject data = new JSONObject();
					JSONObject message = new JSONObject();
					message.put("type", "warning");
					message.put("code", "MSG008");
					message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
					data.put("message", message);
					json.put("data", data);
					json.put("key", "srs_uap_006");

					return json.toString();
				}

				if (branch.getStatus().getCode().equals("STA004")) {
					log.warn("/api/user/srs_uap_006 - getItemList - : {}", "branch disabled");

					MetaMessage msg = messageRepository.findByCode("MSG008");

					JSONObject json = new JSONObject();
					JSONObject data = new JSONObject();
					JSONObject message = new JSONObject();
					message.put("type", "warning");
					message.put("code", "MSG008");
					message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
					data.put("message", message);
					json.put("data", data);
					json.put("key", "srs_uap_006");

					return json.toString();
				}
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

			MetaStatus status = statusRepository.findByCode("STA003");
			// LibraryCategory libraryCategory =
			// libraryCategoryRepository.findOne(jsonCategoryeInfo.getLong("id"));
			List<ItemResponse> itemList = new ArrayList<ItemResponse>();
			JSONArray itemListJson = new JSONArray();

			LibraryItem libraryItem = libraryItemRepository.findOne(param.getLibraryItem().getId());
			if (param.getBranchInfo() != null && param.getBranchInfo().getId() != 0) {

				if (orderBy.equals("") || orderBy.equals("ASC"))
					itemList = itemRepository.getItemsList(branch, null, status.getId(), language, libraryItem);
				else
					itemList = itemRepository.getItemsListDesc(branch, null, status.getId(), language, libraryItem);
			} else {
				if (orderBy.equals("") || orderBy.equals("ASC"))
					itemList = itemRepository.getItemsListWithBranch(null, null, status.getId(), language, libraryItem);
				else
					itemList = itemRepository.getItemsListWithBranchDesc(null, null, status.getId(), language,
							libraryItem);
			}

			/*
			 * for (Item item : itemList) {
			 *
			 * itemListJson.put(item.getInfo(language, status, itemLanguageRepository,
			 * subOptRepository, subOptLanguageRepository, optLanguageRepository,
			 * itemOptSubOptRepository, categoryLanguageRepository,
			 * branchLanguageRepository, dayLanguageRepository,
			 * libraryCategoryLanguageRepository, itemRepository, isFlashSale, flashSale,
			 * flashSaleItemRepository)); }
			 */
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("itemInfoList", itemList);
			json.put("data", data);
			json.put("key", "srs_uap_006");

			return json.toString();
		} catch (Exception e) {
			log.warn("/api/user/srs_uap_006 - getItemList - : {}", e.getMessage());

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_uap_006");
			// add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());

			return json.toString();
		}
	}
}
