package ksa.so.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;

import ksa.so.beans.BranchDto;
import ksa.so.beans.BranchInventoryPaging;
import ksa.so.beans.BranchItemPojo;
import ksa.so.beans.BranchRecords;
import ksa.so.beans.ItemListDto;
import ksa.so.beans.Paging;
import ksa.so.beans.UserParam;
import ksa.so.domain.Branch;
import ksa.so.domain.BranchLanguage;
import ksa.so.domain.MenuRights;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.PhoneVerification;
import ksa.so.domain.User;
import ksa.so.domain.UserProfiles;
import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.BranchRepository;
import ksa.so.repositories.CurrencyRepository;
import ksa.so.repositories.HqRepository;
import ksa.so.repositories.InvoiceRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryItemRepository;
import ksa.so.repositories.MenuRepository;
import ksa.so.repositories.MenuRightsRepository;
import ksa.so.repositories.MetaCountryRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.repositories.MetaUserTypeRepository;
import ksa.so.repositories.PhoneVerificationRepository;
import ksa.so.repositories.UserProfilesRepository;
import ksa.so.repositories.UserRepository;
import ksa.so.service.AuditLog;
import ksa.so.service.MenuService;



@RestController
public class BranchController {
	@Value("${twilio.sid}")
	private String twilioSid;

	@Value("${twilio.token}")
	private String twilioToken;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	MetaLanguageRepository metaLanguageRepository;
	@Autowired
	BranchLanguageRepository branchLanguageRepository;
	@Autowired
	MetaStatusRepository metaStatusRepository;
	@Autowired
	MetaCountryRepository metaCountryRepository;
	@Autowired
	MetaUserTypeRepository metaUserTypeRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	MenuService menuService;
	@Autowired
	LibraryItemRepository libraryItemRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	AuditLog auditLog;
	@Autowired
	MenuRepository menuRepository;
	@Autowired
	MenuRightsRepository menuRightsRepository;
	@Autowired
	UserProfilesRepository userProfilesRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	HqRepository hqRepository;
	@Autowired
	PhoneVerificationRepository phoneVerificationRepository;
	
	
	@GetMapping("/api/totalbranches")
	public Long getTotalbranches() {
		
		return branchRepository.getTotalRecordsByTitle("bm canteen");

	}
	
	@PostMapping("/api/branches")
	public BranchRecords getBranches(@RequestBody Paging param,Authentication authentication, HttpServletRequest objReq){
		Pageable listing = new PageRequest(param.getPage(), param.getSize(), null);
//		Pageable listing = PageRequest.of(param.getPage(), param.getSize(), null);
		BranchRecords branchrecords = new BranchRecords();
		if(param.getTitle()==("")||param.getTitle() ==null) {
			branchrecords.setBranchResponseList(branchRepository.getAllBranches(listing));
			branchrecords.setTotalRecords(branchRepository.getTotalRecords());
		} else {
			branchrecords.setBranchResponseList(branchRepository.findByTitle(param.getTitle().toLowerCase(), listing));
			branchrecords.setTotalRecords(branchRepository.getTotalRecordsByTitle(param.getTitle().toLowerCase()));
		}	
		return branchrecords;
	}
	
	
	//get branch inventory
	@PostMapping("/api/branchinventory")
	public BranchInventoryPaging getBranchInventory(@RequestBody Paging param) {
		Pageable listing = new PageRequest(param.getPage(), param.getSize(), null);
//		Pageable listing = PageRequest.of(param.getPage(),  param.getSize(), null);
		
		BranchInventoryPaging branchInventoryPaging = new BranchInventoryPaging();
		if(param.getTitle()==("")||param.getTitle() ==null) {
			branchInventoryPaging.setBranchItems(branchRepository.getBranchInventory(param.getId(),listing));
			branchInventoryPaging.setTotalRecords(branchRepository.getTotalBranchInventoryRecords(param.getId()));
		}
		else {
			branchInventoryPaging.setBranchItems(branchRepository.findBranchInventoryByTitle(param.getId(),param.getTitle().toLowerCase(), listing));
			branchInventoryPaging.setTotalRecords(branchRepository.getBranchInventoryTotalRecordsByTitle(param.getId(),param.getTitle().toLowerCase()));
		} 
		return branchInventoryPaging;
//		return branchRepository.getBranchInventory(param.getId());
		
		
	}
	@PostMapping("/api/branchinventorybarcode")
	public BranchItemPojo getBranchInventoryByBarcode(@RequestBody Paging param) {

		return branchRepository.getBranchInventoryByBarcode(param.getId(), param.getBarcode());
		
		
	}
	
	
	//save a single branch
	@PostMapping("/api/branch")
	public Branch saveBranch(@RequestBody BranchDto branchDto,Authentication authentication, HttpServletRequest objReq) {
		List<MetaLanguage> languageList = metaLanguageRepository.findAll();
		
		MetaStatus metaStatus = metaStatusRepository.findById(branchDto.getStatus().getId()).get(); 
		
		branchDto.getBranch().setStatus(metaStatus);
//		branchDto.getBranch().setCountry(metaCountryRepository.(branchDto.getMetaStatus().getCode()));
		branchDto.getBranch().setCountry(metaCountryRepository.getOne(branchDto.getMetaCountry().getId()));
		branchDto.getBranch().setCurrency(currencyRepository.getOne(branchDto.getMetaCountry().getId()));
		branchDto.getBranch().setHq(hqRepository.getOne(10L));
		Class<?> c = branchDto.getBranch().getClass();
		Branch savedBranch = branchRepository.save(branchDto.getBranch());
		

		for(int i=0;i<languageList.size();i++) {
			
			BranchLanguage branchLanguage = new BranchLanguage();
			branchLanguage.setDetails(branchDto.getBranchLanguage().getDetails());
			branchLanguage.setTitle(branchDto.getBranchLanguage().getTitle());
			branchLanguage.setLanguage(languageList.get(i));
			branchLanguage.setBranch(savedBranch);
			branchLanguageRepository.save(branchLanguage);
		}
//		ModelMapper mapper = new ModelMapper();
		
		Optional<User> useropt = userRepository.findByUsername(authentication.getName());

		
		auditLog.createAuditEntry(useropt.get().getId(), 'A',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), null, savedBranch);
		
		return savedBranch;
	}
	
	//check if username already exists in database
	@PostMapping("/api/checkuser")
	public Boolean checkuser(@RequestBody UserParam userParam) {
		Boolean flag;
		List<User> userList = userRepository.findByusername(userParam.getUsername());
	if(userList.size()>0) {
		flag = false;
	}else {
		flag = true;
	}
	return flag;
	}
	
	
	//register both user and branch at once
	@PostMapping("/api/registerbranch")
	public User registerBranch(@RequestBody BranchDto branchDto,Authentication authentication, HttpServletRequest objReq) {
		List<MetaLanguage> languageList = metaLanguageRepository.findAll();
		
// get status from api		
//		MetaStatus metaStatus = metaStatusRepository.findById(branchDto.getStatus().getId()); 
		
//		branchDto.getBranch().setStatus(metaStatus);

		//set intermediate status from branch
//		MetaStatus metaStatus = metaStatusRepository.findById(12L); 
		MetaStatus metaStatus = metaStatusRepository.getOne(12L); 
		
		branchDto.getBranch().setStatus(metaStatus);
	
		branchDto.getBranch().setCountry(metaCountryRepository.getOne(branchDto.getMetaCountry().getId()));
		branchDto.getBranch().setCurrency(currencyRepository.getOne(branchDto.getMetaCountry().getId()));
		branchDto.getBranch().setHq(hqRepository.getOne(10L));
		Class<?> c = branchDto.getBranch().getClass();
		Branch savedBranch = branchRepository.save(branchDto.getBranch());
		
		for(int i=0;i<languageList.size();i++) {
			
			BranchLanguage branchLanguage = new BranchLanguage();
			branchLanguage.setDetails(branchDto.getBranchLanguage().getDetails());
			branchLanguage.setTitle(branchDto.getBranchLanguage().getTitle());
			branchLanguage.setLanguage(languageList.get(i));
			branchLanguage.setBranch(savedBranch);
			branchLanguageRepository.save(branchLanguage);
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		branchDto.getUser().setPassword(passwordEncoder.encode(branchDto.getUser().getPassword()));
		branchDto.getUser().setCountry(metaCountryRepository.getOne(branchDto.getMetaCountry().getId()));
		branchDto.getUser().setBranch(savedBranch);
		branchDto.getUser().setHq(null);
		branchDto.getUser().setStatus(metaStatus);
		branchDto.getUser().setUserType(metaUserTypeRepository.findByCode("UT000002"));
		String code = randomCode(6);
		branchDto.getUser().setOtp(code);
		PhoneVerification verification = new PhoneVerification();
		
	
		
		
		

		User savedUser = userRepository.save(branchDto.getUser());
		verification.setCode(code);
		verification.setPhone(branchDto.getUser().getPhone());
		verification.setUserId(savedUser);
		//give menu rights to user
		UserProfiles userProfile = new UserProfiles();
		userProfile.setMetaUserType(savedUser.getUserType());
		userProfile.setUserId(savedUser);
		userProfilesRepository.save(userProfile);


		auditLog.createAuditEntry(savedUser.getId(), 'A',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), null, savedBranch);
	 
		return savedUser;
	
	}
	
	//returns id's of all items present in the branch
	@GetMapping("/api/branchitems/{branchId}")
	public List<Object> getItemsInBranch(@PathVariable(name = "branchId") Long branchId ) {
		return itemRepository.findLibraryItemIdByBranchId(branchId);
	}
	
	@PostMapping("/api/branchitem")
	void saveBranchItems(@RequestBody ItemListDto itemDto) {

		for(int i=0;i<itemDto.getItemList().size();i++) {
			itemDto.getItemList().get(i).setBranch(branchRepository.getOne(itemDto.getBranchId()));
			//still unclear
			itemDto.getItemList().get(i).setStatus(metaStatusRepository.findByCode("STA003"));
			itemDto.getItemList().get(i).setLibraryItem(libraryItemRepository.getOne(itemDto.getLibraryItemIds().get(i)));
			itemRepository.save(itemDto.getItemList().get(i));
			
		}
	}
	
	//important *
	@GetMapping("/api/branchitem/{id}")
	public List<BranchItemPojo> getBranchItems(@PathVariable(name="id") Long id) {
		
		return branchRepository.getBranchItems(id);
	}
	
	//edit branch details
	@PutMapping("/api/branch")
	public Branch updateBranch(@RequestBody BranchDto branchDto,Authentication authentication, HttpServletRequest objReq) {
		List<MetaLanguage> languageList = metaLanguageRepository.findAll();
//		Branch oldBranch = branchRepository.findOne(branchDto.getBranch().getId());
		Branch oldBranch = branchRepository.getOne(branchDto.getBranch().getId());
		MetaStatus metaStatus = metaStatusRepository.findById(branchDto.getStatus().getId()).get(); 
		
		branchDto.getBranch().setStatus(metaStatus);
//		branchDto.getBranch().setCountry(metaCountryRepository.(branchDto.getMetaStatus().getCode()));
		branchDto.getBranch().setCountry(metaCountryRepository.getOne(branchDto.getMetaCountry().getId()));
		branchDto.getBranch().setCurrency(currencyRepository.getOne(branchDto.getMetaCountry().getId()));
		branchDto.getBranch().setHq(hqRepository.getOne(10L));
		Class<?> c = branchDto.getBranch().getClass();
		Branch savedBranch = branchRepository.save(branchDto.getBranch());
		
		List<BranchLanguage> branchLanguageList = branchLanguageRepository.findByBranchId(branchDto.getBranch().getId());
		
		for(int i=0;i<languageList.size();i++) {
			
			branchLanguageList.get(i).setDetails(branchDto.getBranchLanguage().getDetails());
			branchLanguageList.get(i).setTitle(branchDto.getBranchLanguage().getTitle());
			branchLanguageList.get(i).setLanguage(languageList.get(i));
			branchLanguageList.get(i).setBranch(savedBranch);
			branchLanguageRepository.save(branchLanguageList.get(i));
			
		}

		Optional<User> useropt = userRepository.findByUsername(authentication.getName());
		auditLog.createAuditEntry(useropt.get().getId(), 'E',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), oldBranch, savedBranch);
		return savedBranch;
	}
	
	@GetMapping("/api/billnumber/{branchid}")
	public Long getBillNumber(@PathVariable(name="branchid") Long id) {
	return invoiceRepository.getBranchInvoiceCount(id);
		
	}
//	@PostMapping("/api/billnumber")
//	public Long getBillNumber(Authentication authentication) {
//		User user
//	return invoiceRepository.getBranchInvoiceCount(id);
//		
//	}

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
}
