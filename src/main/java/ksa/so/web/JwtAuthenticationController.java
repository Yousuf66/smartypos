package ksa.so.web;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ksa.so.beans.AccountInformation;
import ksa.so.beans.LoginRequest;
import ksa.so.beans.idTitle;
import ksa.so.beans.token;
import ksa.so.domain.CloudMessageService;
import ksa.so.domain.CouponUsers;
import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.MetaCountry;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaMessage;
import ksa.so.domain.MetaUserType;
import ksa.so.domain.User;
import ksa.so.domain.UserApp;
import ksa.so.domain.Wallet;
import ksa.so.mail.Emails;
import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.CartItemRepository;
import ksa.so.repositories.CategoryLanguageRepository;
import ksa.so.repositories.CouponUsersRepository;
import ksa.so.repositories.DiscountCouponLanguageRepository;
import ksa.so.repositories.DiscountCouponRepository;
import ksa.so.repositories.HqLanguageRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.MetaCountryRepository;
import ksa.so.repositories.MetaDayLanguageRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaMessageLanguageRepository;
import ksa.so.repositories.MetaMessageRepository;
import ksa.so.repositories.MetaStatusLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.repositories.MetaUserTypeRepository;
import ksa.so.repositories.UserAppRepository;
import ksa.so.repositories.UserRepository;
import ksa.so.repositories.WalletRepository;
import ksa.so.security.CustomAuthenticationToken;
import ksa.so.security.JwtRequest;
import ksa.so.security.JwtResponse;
import ksa.so.security.JwtTokenUtil;
import ksa.so.service.CustomUserDetailsServiceImpl;
import ksa.so.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;

	@Autowired
	BranchLanguageRepository branchLanguageRepository;

	@Autowired
	MetaDayLanguageRepository dayLanguageRepository;

	@Autowired
	UserAppRepository userAppRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	MetaStatusRepository statusRepository;

	@Autowired
	MetaStatusLanguageRepository statusLanguageRepository;

	@Autowired
	MetaLanguageRepository languageRepository;

	@Autowired
	MetaMessageRepository messageRepository;

	@Autowired
	MetaUserTypeRepository metaUserTypeRepository;

	@Autowired
	MetaMessageLanguageRepository messageLanguageRepository;

	@Autowired
	MetaCountryRepository countryRepository;

	@Autowired
	LibraryCategoryLanguageRepository libraryCategoryLanguageRepository;

	@Autowired
	CategoryLanguageRepository categoryLanguageRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	Emails sendInvoiceService;

	@Autowired
	ItemLanguageRepository itemLanguageRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	CouponUsersRepository couponUsersRepository;

	@Autowired
	DiscountCouponRepository discountCouponRepository;

	@Autowired
	DiscountCouponLanguageRepository discountCouponLanguageRepository;

	@Autowired
	HqLanguageRepository hqLanguageRepository;
	public token ObjToken = null;

	@Value("${security.jwt.token.expire-length:900000}")
	private long access_token_validity_InMilliseconds = 9000000;// 15 minutes

	private long refresh_token_validity_InMilliseconds = 3600000;// 1 hr

	CloudMessageService cms = new CloudMessageService();

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	
	
	
//	@GetMapping("/api/hello")
//	String joke() {
//		return "joke";
//	}
	@GetMapping("/api/userList")
	@ResponseBody
	List<idTitle> getAllUsers(){
//		return userAppRepository.getAllUsersApp();
		return userAppRepository.getAllUsersApp();
		}

	@RequestMapping(value = "/authenticateUserApp", method = RequestMethod.POST)
	public String createAuthenticationTokenForUserApp(@RequestBody JwtRequest param) throws Exception {
		String warn = "/api/user/srs_uap_001 - login - : {}";
		String apiKey = "srs_uap_001";
		try {
			Boolean register = false;
			String jwtToken = "";
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			UserApp user = new UserApp();

			String loginType = param.getLoginType();
			String token = param.getUserAppInfo().getInstallationId();

			// generate code for sms
			String code = randomCode(6);

			if (loginType.equals("phone")) {
				String phone = param.getUserAppInfo().getPhone();
				MetaCountry country = countryRepository.findOne(param.getUserAppInfo().getCountry().getId());

				// select user using phone
				Optional<UserApp> userOptional = userAppRepository.findByPhoneAndCountry(phone, country);

				// String countryCode = country.getCode().replace("+", "");
				// SMSSender.sendSms(countryCode + phone, code);
				// if(sendSMS(country.getCode() + phone, code)) {
				if (!userOptional.isPresent()) {
					user = new UserApp();
					user.setCountry(country);
					user.setGender("Male");
					user.setFirstName("-");
					user.setLastName("-");
					user.setUsername("-");
					user.setPassword("-");
					user.setPhone(phone);
					user.setInstallationId(token);
					user.setStatus(statusRepository.findByCode("STA001"));
					user.setOtp(code);
					register = true;

				} else {
					user = userOptional.get();
					// setting jwt token along with authenticating user
					CustomAuthenticationToken authRequest = new CustomAuthenticationToken(
							param.getUserAppInfo().getPhone(), param.getUserAppInfo().getOtp());
					authenticateUserApp(authRequest, user);
					jwtToken = jwtTokenUtil.generateTokenForUserApp(user);
					register = false;
					user.setInstallationId(token);
					user.setStatus(statusRepository.findByCode("STA001"));
					user.setOtp(code);

					userAppRepository.save(user);
				}
			} else {
				// select user using username
				Optional<UserApp> userOptional = userAppRepository.findByUsername(param.getUserAppInfo().getUsername());

				if (!userOptional.isPresent()) {
					JSONObject json = setResponse(apiKey, warn, "invalid username", language, "MSG002", "warning");
					return json.toString();
				}

				// for encoded Password in Database
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

				user = userOptional.get();
				if (!passwordEncoder.matches(param.getUserAppInfo().getPassword(), user.getPassword())) {
					JSONObject json = setResponse(apiKey, warn, "invalid password", language, "MSG003", "warning");
					return json.toString();
				}

				if (user.getStatus().getCode().equals("STA004")) {
					JSONObject json = setResponse(apiKey, warn, "user disabled", language, "MSG006", "warning");
					return json.toString();
				}
			}

			JSONObject jsonUserAppInfo = user.getInfo(language, statusLanguageRepository);

			/*
			 * List<CartItem> cartList = cartItemRepository.findByUser(user); JSONArray
			 * jsonCartList = new JSONArray(); JSONObject jsonCartInfo = new JSONObject();
			 * Branch branch = new Branch(); int cartCount = 0; if (cartList != null &&
			 * cartList.size() != 0) { for (CartItem cart : cartList) {
			 * jsonCartList.put(cart.getInfo(language, itemLanguageRepository,
			 * categoryLanguageRepository, branchLanguageRepository, dayLanguageRepository,
			 * libraryCategoryLanguageRepository, itemRepository)); branch =
			 * cart.getBranch(); cartCount++; } jsonCartInfo.put("branchInfo",
			 * branch.getInfo(language, branchLanguageRepository, dayLanguageRepository,
			 * "0.0", "0.0", itemRepository, libraryCategoryLanguageRepository)); }
			 *
			 * jsonCartInfo.put("cartList", jsonCartList);
			 */
			jsonUserAppInfo.put("token", jwtToken);
			jsonUserAppInfo.put("installationId", token);
			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("register", register);
			data.put("userAppInfo", jsonUserAppInfo);

			json.put("data", data);
			json.put("key", "srs_uap_001");

			return json.toString();
		} catch (Exception e) {
			JSONObject json = setResponse(apiKey, warn, e.getMessage(), null, null, "error");
			/*
			 * log.warn("/api/user/srs_uap_001 - login - : {}", e.getMessage());
			 *
			 * JSONObject json = new JSONObject(); JSONObject data = new JSONObject();
			 * JSONObject message = new JSONObject(); message.put("type", "error");
			 * message.put("value", e.getMessage());
			 *
			 * data.put("message", message); json.put("data", data); json.put("key",
			 * "srs_uap_001"); json.put("Server Timestamp",
			 * statusRepository.getCurrentTime());
			 */

			return json.toString();
		}
	}

	/**
	 * command login
	 */
	@RequestMapping(value = "/authenticateOperatorApp", method = RequestMethod.POST)
	public String authenticateOperatorApp(@RequestBody JwtRequest param, HttpSession session) throws Exception {
		String warn = "/api/user/srs_uop_001 - login - : {}";
		String apiKey = "api/user/srs_uop_001";
		try {

			String jwtToken = "";
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			User user = new User();

			String loginType = param.getLoginType();
			String token = param.getUser().getInstallationId();

			List<MetaUserType> metaUserTypeList = metaUserTypeRepository.findByCodeIn(param.getMetaUserTypeInfoList());
			Optional<User> userOptional = userRepository.findByUsernameAndUserTypeIn(param.getUser().getUsername(),
					metaUserTypeList);
			user = userOptional.get();
			/*
			 * if (!userOptional.isPresent()) { JSONObject json = setResponse(apiKey, warn,
			 * "invalid username", language, "MSG005", "warn"); return json.toString(); }
			 */

			authenticate(param.getUser().getUsername(), param.getUser().getPassword());
			// for encoded Password in Database
			/*
			 * PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); user =
			 * userOptional.get(); if
			 * (!passwordEncoder.matches(param.getUser().getPassword(), user.getPassword()))
			 * { JSONObject json = setResponse(apiKey, warn, "invalid password", language,
			 * "MSG005", "warn"); return json.toString(); }
			 *
			 * if (user.getStatus().getCode().equals("STA004")) { JSONObject json =
			 * setResponse(apiKey, warn, "user disabled", language, "MSG005", "error");
			 * return json.toString(); }
			 */
			jwtToken = jwtTokenUtil.generateTokenForUser(user);

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
					jsonUserAppInfo.put("token", jwtToken);
				} else {
					if (user.getUserType().getCode().equals("UT000003")) {
						getSubOperator = true;
					}

					jsonUserAppInfo = user.getInfo(language, statusLanguageRepository, branchLanguageRepository,
							dayLanguageRepository, getSubOperator, itemRepository, libraryCategoryLanguageRepository);
					jsonUserAppInfo.put("token", jwtToken);
				}
				userRepository.save(user);
			}

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

			JSONObject json = setResponse(apiKey, warn, "invalid username", null, null, "error");
			return json.toString();
		}
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

	private void authenticateUserApp(CustomAuthenticationToken authRequest, UserDetails userDetails) throws Exception {
		try {
			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
					AuthorityUtils.createAuthorityList("ROLE_USER"));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			// authenticationManager.authenticate(authRequest);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
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
	 * command update user info
	 */
	@RequestMapping(value = "/registerUserApp", method = RequestMethod.POST)
	public String registerUser(@RequestBody AccountInformation param) throws Exception {
		String warn = "/api/user/srs_uap_003 - updateUserInfo - : {}";
		String apiKey = "srs_uap_003";
		try {
			// incoming params to json
			// UserApp user = (UserApp) authenticate.getPrincipal();

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			// select user using phone
			// select user using phone
			Optional<UserApp> userOptional = userAppRepository.findByPhoneAndCountry(param.getPhone(),
					param.getCountryInfo());

			// String countryCode = country.getCode().replace("+", "");
			// SMSSender.sendSms(countryCode + phone, code);
			// if(sendSMS(country.getCode() + phone, code)) {
			UserApp user = new UserApp();
			if (!userOptional.isPresent()) {
				// user = new UserApp();
				user.setCountry(param.getCountryInfo());

				user.setGender(param.getGender());
				user.setFirstName(param.getFirstName());
				user.setLastName(param.getLastName());
				user.setEmail(param.getEmail());

				user.setPassword("-");
				user.setPhone(param.getPhone());

				user.setInstallationId(param.getToken());
				user.setStatus(statusRepository.findByCode("STA001"));
				user.setOtp(param.getOtp());

				userAppRepository.save(user);

				CustomAuthenticationToken authRequest = new CustomAuthenticationToken(user.getPhone(), user.getOtp());
				authenticateUserApp(authRequest, user);

				Wallet wallet = new Wallet();
				wallet.setWalletAmount(0.0);
				wallet.setUser(user);
				walletRepository.save(wallet);

				DiscountCoupon discountCoupon = discountCouponRepository.findOne((long) 7);

				CouponUsers couponUser = new CouponUsers();
				couponUser.setUsageNumber((long) 0);
				couponUser.setDiscountCoupon(discountCoupon);
				couponUser.setTotalUsage((long) 100);
				couponUser.setUnlimited(true);
				couponUser.setUserApp(user);
				couponUser.setAmountUsed(0);
				couponUser.setMaxAmount(1000);
				couponUsersRepository.save(couponUser);

				// save user object

				sendInvoiceService.accountConfirmationEmail(user);
			} else {
				JSONObject json = new JSONObject();
				JSONObject data = new JSONObject();
				JSONObject message = new JSONObject();
				message.put("type", "error");
				message.put("value", "User Already Exist");
				data.put("message", message);
				json.put("data", data);
				json.put("key", apiKey);
				json.put("Server Timestamp", statusRepository.getCurrentTime());
				return json.toString();
			}

			MetaMessage msg = messageRepository.findByCode("MSG001");
			String jwtToken = jwtTokenUtil.generateTokenForUserApp(user);
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			JSONObject jsonUserAppInfo = user.getInfo(language, statusLanguageRepository);
			jsonUserAppInfo.put("token", jwtToken);
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("userAppInfo", jsonUserAppInfo);
			data.put("message", message);
			json.put("data", data);
			json.put("key", apiKey);

			return json.toString();
		} catch (

		Exception e) {
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

	@RequestMapping(value = "/api/user/signin", method = RequestMethod.POST)
	public token authenticateOperatorAppWeb(@RequestBody JwtRequest param, HttpSession session) throws Exception {
		String warn = "/api/user/srs_uop_001 - login - : {}";
		String apiKey = "api/user/srs_uop_001";
		ObjToken = new token();
		try {

			String jwtToken = "";
			String jwtRefreshToken = "";

			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());

			User user = new User();

			String loginType = param.getLoginType();
//			String token = param.getUser().getInstallationId();

			List<MetaUserType> metaUserTypeList = metaUserTypeRepository.findByCodeIn(param.getMetaUserTypeInfoList());
			Optional<User> userOptional = userRepository.findByUsernameAndUserTypeIn(param.getUser().getUsername(),
					metaUserTypeList);
			user = userOptional.get();

			authenticate(param.getUser().getUsername(), param.getUser().getPassword());


			jwtToken = jwtTokenUtil.generateTokenForUser(user);
			jwtRefreshToken = jwtTokenUtil.createToken(param.getUser().getUsername(),
					refresh_token_validity_InMilliseconds);

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
					jsonUserAppInfo.put("token", jwtToken);
				} else {
					if (user.getUserType().getCode().equals("UT000003")) {
						getSubOperator = true;
					}

					jsonUserAppInfo = user.getInfo(language, statusLanguageRepository, branchLanguageRepository,
							dayLanguageRepository, getSubOperator, itemRepository, libraryCategoryLanguageRepository);
				}
				userRepository.save(user);
			}

			jsonUserAppInfo.put("token", jwtToken);
			jsonUserAppInfo.put("refreshtoken", jwtRefreshToken);

			JSONObject Token = new JSONObject();
			Token.put("id", user.getId());
			Token.put("tokenString", jwtToken);
			Token.put("refreshTokenString", jwtRefreshToken);
			Token.put("fullUsername", param.getUser().getUsername());

			Date dateOfLogin = new Date();

			ObjToken.setFullUsername(param.getUser().getUsername());
			ObjToken.setUserId(user.getId());
			ObjToken.setTokenString(jwtToken);
			ObjToken.setRefreshTokenString(jwtRefreshToken);
			ObjToken.setFullUsername(param.getUser().getUsername());
			ObjToken.setLogin(dateOfLogin);
			// setting authstate to true

			ObjToken.setAuthStat(true);

			MetaMessage msg = messageRepository.findByCode("MSG001");

			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("userAppInfo", jsonUserAppInfo);
//			data.put("Token", ObjToken.toString());
//			data.put("Token", Token);
			// data.put("jsonOrderInfo", jsonOrderList);
			json.put("data", data);
			json.put("key", "srs_uop_001");
			json.put("refreshtoken", jwtRefreshToken);
			json.put("Token", Token);
			return ObjToken;

		} catch (Exception e) {

//			JSONObject json = setResponse(apiKey, warn, "invalid username", null, null, "error");
//			return json.toString();
//			return null;
			throw e;
		}
	}

	
	
	@RequestMapping(value = "/api/branch/signin", method = RequestMethod.POST)
	public token authenticateStore(@RequestBody JwtRequest param, HttpSession session) throws Exception {
		String warn = "/api/user/srs_uop_001 - login - : {}";
		String apiKey = "api/user/srs_uop_001";
		ObjToken = new token();
		try {

			String jwtToken = "";
			String jwtRefreshToken = "";	
			MetaLanguage language = languageRepository.findByCode(param.getLanguageInfo().getCode());
			User user = new User();
			String loginType = param.getLoginType();
//			String token = param.getUser().getInstallationId();
			List<MetaUserType> metaUserTypeList = metaUserTypeRepository.findByCodeIn(param.getMetaUserTypeInfoList());
			Optional<User> userOptional = userRepository.findByUsernameAndUserTypeIn(param.getUser().getUsername(),
					metaUserTypeList);
			user = userOptional.get();
		
			authenticate(param.getUser().getUsername(), param.getUser().getPassword());
			
			jwtToken = jwtTokenUtil.generateTokenForUserForWeb(user);
			jwtRefreshToken = jwtTokenUtil.createToken(param.getUser().getUsername(), refresh_token_validity_InMilliseconds);
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
					jsonUserAppInfo.put("token", jwtToken);
				} else {
					if (user.getUserType().getCode().equals("UT000003")) {
						getSubOperator = true;
					}

					jsonUserAppInfo = user.getInfo(language, statusLanguageRepository, branchLanguageRepository,
							dayLanguageRepository, getSubOperator, itemRepository, libraryCategoryLanguageRepository);
				}
				userRepository.save(user);
			}

			jsonUserAppInfo.put("token", jwtToken);
			jsonUserAppInfo.put("refreshtoken", jwtRefreshToken);
			
			JSONObject Token = new JSONObject();
			Token.put("id", user.getId());
			Token.put("tokenString", jwtToken);
			Token.put("refreshTokenString", jwtRefreshToken);
			Token.put("fullUsername", param.getUser().getUsername());
			
			Date dateOfLogin = new Date();
			List<idTitle> branchList = userRepository.findBranchByUser(user.getId());
			ObjToken.setFullUsername(param.getUser().getUsername());
			ObjToken.setUserId(user.getId());
			ObjToken.setTokenString(jwtToken);
			ObjToken.setRefreshTokenString(jwtRefreshToken);
			ObjToken.setFullUsername(param.getUser().getUsername());
			ObjToken.setLogin(dateOfLogin);
			ObjToken.setBranchList(branchList);
			//setting authstate to true
			
			ObjToken.setAuthStat(true);
			
			MetaMessage msg = messageRepository.findByCode("MSG001");
			
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("userAppInfo", jsonUserAppInfo);
//			data.put("Token", ObjToken.toString());
//			data.put("Token", Token);
			// data.put("jsonOrderInfo", jsonOrderList);
			json.put("data", data);
			json.put("key", "srs_uop_001");
			json.put("refreshtoken", jwtRefreshToken);
			json.put("Token", Token);
			return 	ObjToken;
		
		}catch (Exception e) {


			throw e;
		}
	}
	
	

	@GetMapping("/api/refreshtoken/{id}")
//	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
	@ResponseBody
	public token refreshToken(@PathVariable(value = "id") Long Id) {

		User ObjUser = null;
//		    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
		ObjUser = userRepository.findById(Id).get();
		ObjToken = new token();
//	  	  ObjToken.setAuthStat(true);
//	  	  ObjToken.setLogin(new Date());
//			  ObjToken.setUserId(ObjUser.getId());
//			  ObjToken.setUsername(ObjUser.getFirstName()+' '+ObjUser.getLastName());
		ObjToken.setTokenString(jwtTokenUtil.createToken(ObjUser.getUsername(), access_token_validity_InMilliseconds));
		ObjToken.setRefreshTokenString(
				jwtTokenUtil.createToken(ObjUser.getUsername(), refresh_token_validity_InMilliseconds));
//			  LoginAudit loginAudit = new LoginAudit(objReq.getRemoteAddr() , ObjUser.getEmpId()  , new Date() );
//			  loginAuditRepository.save(loginAudit);
		return ObjToken;
	}

}