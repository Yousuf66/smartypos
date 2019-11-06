package ksa.so.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.net.ssl.SSLEngineResult.Status;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.http.HttpHeaders;

import ksa.so.beans.CouponDto;
import ksa.so.beans.StatusLanguage;
import ksa.so.beans.idTitle;
import ksa.so.domain.CouponUsers;
import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.DiscountCouponLanguage;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.MetaStatusLanguage;
import ksa.so.domain.User;
import ksa.so.domain.UserApp;
import ksa.so.repositories.CouponUsersRepository;
import ksa.so.repositories.DiscountCouponLanguageRepository;
import ksa.so.repositories.DiscountCouponRepository;
import ksa.so.repositories.MenuRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaStatusLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.repositories.UserAppRepository;
import ksa.so.repositories.UserRepository;
import ksa.so.service.AuditLog;
import ksa.so.service.MenuService;



@RestController
public class CouponController {

	@Autowired
	DiscountCouponRepository discountCouponRepository;
	@Autowired
	DiscountCouponLanguageRepository discountCouponLanguageRepository;
	@Autowired
	MetaLanguageRepository metaLanguageRepository; 
//	@Autowired
//	MetaStatusRepository metaStatusRepository;
	@Autowired 
	MetaStatusRepository ms;
	@Autowired 
	MetaStatusLanguageRepository msl;
	@Autowired
	UserRepository  userRepository;
	@Autowired 
	CouponUsersRepository couponUsersRepository;
	@Autowired
	private AuditLog auditLog;
	@Autowired
	private MenuService menuService;
	
	@Autowired
	MenuRepository menuRepository;
	

	@Autowired 
	UserAppRepository userAppRepository;
	

	@PostMapping("/api/coupon")
	public ResponseEntity<DiscountCoupon> createCoupon(@RequestBody CouponDto coupon,Authentication authentication, HttpServletRequest objReq){
		DiscountCoupon discountCoupon = new DiscountCoupon();
		List<UserApp> userApp = new ArrayList<UserApp>();
		List<Long> userIds = coupon.getUserApp();
		Class<?> c =discountCoupon.getClass();
		MetaStatus metaStatus = ms.findById(coupon.getStatus().getId()).get();
	
		coupon.getDiscountCoupon().setStatus(metaStatus);
		discountCoupon =  coupon.getDiscountCoupon();

		DiscountCoupon savedDiscountCoupon  = discountCouponRepository.save(discountCoupon);
		
//		userApp = userAppRepository.findAll(userIds);
		userApp = userAppRepository.findAll(userIds);
		for(int i=0;i<userApp.size();i++) {
			CouponUsers couponUsers = new CouponUsers();
			couponUsers.setUserApp(userApp.get(i));
			couponUsers.setDiscountCoupon(savedDiscountCoupon);
			couponUsers.setTotalUsage(savedDiscountCoupon.getUsageLimitPerCustomer());
			couponUsers.setMaxAmount(savedDiscountCoupon.getMaxAmountPerUse());
			couponUsersRepository.save(couponUsers);
		}
		
		List<MetaLanguage> metaLanguage = metaLanguageRepository.findAll();

		for(int i=0;i<metaLanguage.size();i++) {
			DiscountCouponLanguage discountCouponLanguage = new DiscountCouponLanguage();
			discountCouponLanguage.setDetails(coupon.getDiscountCouponLanguage().getDetails());
			discountCouponLanguage.setTitle(coupon.getDiscountCouponLanguage().getTitle());
			discountCouponLanguage.setDiscountCoupon(savedDiscountCoupon);
			discountCouponLanguage.setLanguage(metaLanguage.get(i));
			discountCouponLanguageRepository.save(discountCouponLanguage);
			
		}
		
		
		Optional<User> useropt = userRepository.findByUsername(authentication.getName());
//		User userid = userRepository.findById(useropt.get().getId());
		
//		auditLog.createAuditEntry(usrID, action, ipAddress, menuId, oldValue, newValue);
		auditLog.createAuditEntry(useropt.get().getId(), 'A',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), null, discountCoupon);
		return ResponseEntity.ok(discountCoupon);
	}
	
	
	@PutMapping("/api/coupon")
	public ResponseEntity<DiscountCoupon> updateCoupon(@RequestBody CouponDto coupon,Authentication authentication, HttpServletRequest objReq){
		DiscountCoupon discountCoupon = new DiscountCoupon();
		discountCoupon = discountCouponRepository.getOne(coupon.getDiscountCoupon().getId());
		DiscountCoupon oldCoupon = discountCouponRepository.getOne(coupon.getDiscountCoupon().getId());
		List<UserApp> userApp = new ArrayList<UserApp>();
		List<Long> userIds = coupon.getUserApp();
		Class<?> c =discountCoupon.getClass();
		MetaStatus metaStatus = ms.findById(coupon.getStatus().getId()).get();
		coupon.getDiscountCoupon().setStatus(metaStatus);
		discountCoupon =  coupon.getDiscountCoupon();
		DiscountCoupon savedDiscountCoupon  = discountCouponRepository.save(discountCoupon);
		userApp = userAppRepository.findAll(userIds);
		
		for(int i=0;i<userApp.size();i++) {
			CouponUsers couponUsers = new CouponUsers();
			couponUsers.setUserApp(userApp.get(i));
			couponUsers.setDiscountCoupon(savedDiscountCoupon);
			couponUsers.setTotalUsage(savedDiscountCoupon.getUsageLimitPerCustomer());
			couponUsers.setMaxAmount(savedDiscountCoupon.getMaxAmountPerUse());
			couponUsersRepository.save(couponUsers);
		}

		List<MetaLanguage> metaLanguage = metaLanguageRepository.findAll();
		List<DiscountCouponLanguage> discountCouponLanguageList = discountCouponLanguageRepository.findByDiscountCoupon(discountCoupon);
	
		for(int i=0;i<metaLanguage.size();i++) {
//			DiscountCouponLanguage discountCouponLanguage = new DiscountCouponLanguage();
			discountCouponLanguageList.get(i).setDetails(coupon.getDiscountCouponLanguage().getDetails());
			discountCouponLanguageList.get(i).setTitle(coupon.getDiscountCouponLanguage().getTitle());
			discountCouponLanguageList.get(i).setDiscountCoupon(savedDiscountCoupon);
			discountCouponLanguageList.get(i).setLanguage(metaLanguage.get(i));
			discountCouponLanguageRepository.save(discountCouponLanguageList.get(i));
		}
		
		Optional<User> useropt = userRepository.findByUsername(authentication.getName());
//		User userid = userRepository.findById(useropt.get().getId()).get();
//		auditLog.createAuditEntry(usrID, action, ipAddress, menuId, oldValue, newValue);
		auditLog.createAuditEntry(useropt.get().getId(), 'E',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), oldCoupon, discountCoupon);
		return ResponseEntity.ok(discountCoupon);
	}
	

}
