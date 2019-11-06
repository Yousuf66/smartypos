package ksa.so.web;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import ksa.so.beans.BranchRecords;
import ksa.so.beans.LibraryCategoryDto;
import ksa.so.beans.LibraryCategoryLanguageDto;
import ksa.so.beans.LibraryCategoryPagingResponse;
import ksa.so.beans.LibraryCategoryResponse;
import ksa.so.beans.LibraryItemDto;
import ksa.so.beans.LibraryItemPagingResponse;
import ksa.so.beans.LibraryItemPojo;
import ksa.so.beans.Paging;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.LibraryCategoryLanguage;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemImages;
import ksa.so.domain.LibraryItemLanguage;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.User;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.LibraryCategoryRepository;
import ksa.so.repositories.LibraryItemImagesRepository;
import ksa.so.repositories.LibraryItemLanguageRepository;
import ksa.so.repositories.LibraryItemRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.repositories.UserRepository;
import ksa.so.service.AuditLog;
import ksa.so.service.MenuService;


@RestController
public class LibraryController {
	@Autowired
	LibraryCategoryRepository libraryCategoryRepository;
	@Autowired 
	LibraryCategoryLanguageRepository libraryCategoryLanguageRepository;
	@Autowired
	MetaStatusRepository metaStatusRepository;
	@Autowired
	MetaLanguageRepository metaLanguageRepository;
	@Autowired
	MenuService menuService;
	@Autowired
	AuditLog auditLog;
	@Autowired
	UserRepository userRepository;
	@Autowired
	LibraryItemLanguageRepository libraryItemLanguageRepository;
	@Autowired
	LibraryItemRepository libraryItemRepository;
	@Autowired
	LibraryItemImagesRepository libraryItemImagesRepository;
	
	@JsonView(DataTablesOutput.View.class)
	@PostMapping("/api/librarycategorylanguage")
	public DataTablesOutput<LibraryCategoryLanguageDto> getLibrarayCategoryLanguage(@Valid @RequestBody DataTablesInput input,Function<LibraryCategoryLanguage,LibraryCategoryLanguageDto> converter){
		return libraryCategoryLanguageRepository.findAll(input,converter);
	}
	 
	@PostMapping("/api/libraryitems")
	public LibraryItemPagingResponse getAllLibraryItems(@RequestBody Paging param) {
		
		Pageable listing = new PageRequest(param.getPage(), param.getSize());
		LibraryItemPagingResponse libraryItemPagingResponse = new LibraryItemPagingResponse();

		
		if(param.getTitle()==("")||param.getTitle() ==null) {

			libraryItemPagingResponse.setLibraryItemList(libraryItemRepository.getAllLibraryItems(listing));

			libraryItemPagingResponse.setTotalRecords(libraryItemRepository.getTotalRecords());
		} else {
			libraryItemPagingResponse.setLibraryItemList(libraryItemRepository.findByTitle(param.getTitle().toLowerCase(), listing));
			libraryItemPagingResponse.setTotalRecords(libraryItemRepository.getTotalRecordsByTitle(param.getTitle().toLowerCase()));
		}	
		
		return libraryItemPagingResponse;
	}
	
	@PostMapping("/api/approvelibraryitems")
	public LibraryItemPagingResponse getAllPendingLibraryItems(@RequestBody Paging param) {
		
		Pageable listing = new PageRequest(param.getPage(), param.getSize());
		LibraryItemPagingResponse libraryItemPagingResponse = new LibraryItemPagingResponse();

		
		if(param.getTitle()==("")||param.getTitle() ==null) {

			libraryItemPagingResponse.setLibraryItemList(libraryItemRepository.getRequestedLibraryItems(listing));

			libraryItemPagingResponse.setTotalRecords(libraryItemRepository.getRequestedLibraryItemsCount());
		} else {
//			libraryItemPagingResponse.setLibraryItemList(libraryItemRepository.findByTitle(param.getTitle().toLowerCase(), listing));
			libraryItemPagingResponse.setLibraryItemList(libraryItemRepository.getRequestedLibraryItemsByTitle(listing, param.getTitle().toLowerCase()));
			libraryItemPagingResponse.setTotalRecords(libraryItemRepository.getRequestedLibraryItemsByTitleCount(param.getTitle().toLowerCase()));
		}	
		
		return libraryItemPagingResponse;
	}
	
	@PostMapping("/api/requestedlibraryitems")
	public LibraryItemPagingResponse getRequestedLibraryItems(@RequestBody Paging param,Authentication authentication, HttpServletRequest objReq) {
		Optional<User> useropt = userRepository.findByUsername(authentication.getName());
		User user = useropt.get();
		Pageable listing = new PageRequest(param.getPage(), param.getSize());
		LibraryItemPagingResponse libraryItemPagingResponse = new LibraryItemPagingResponse();
		
		Long id = user.getBranch().getId();
		
		if(param.getTitle()==("")||param.getTitle() ==null) {

			libraryItemPagingResponse.setLibraryItemList(libraryItemRepository.getRequestedLibraryItemsOfBranch(id,listing));

			libraryItemPagingResponse.setTotalRecords(libraryItemRepository.getRequestedLibraryItemsOfBranchCount(id));
		} else {
//			libraryItemPagingResponse.setLibraryItemList(libraryItemRepository.findByTitle(param.getTitle().toLowerCase(), listing));
			libraryItemPagingResponse.setLibraryItemList(libraryItemRepository.getRequestedLibraryItemsOfBranchByTitle(listing, param.getTitle().toLowerCase()));
			libraryItemPagingResponse.setTotalRecords(libraryItemRepository.getRequestedLibraryItemsOfBranchByTitle(param.getTitle().toLowerCase()));
		}	
		
		return libraryItemPagingResponse;
	}
	
	@PostMapping("/api/librarycategories")
	public LibraryCategoryPagingResponse getAllLibraryCategories(@RequestBody Paging param ){
		
		Pageable listing = new PageRequest(param.getPage(), param.getSize());
		LibraryCategoryPagingResponse libraryCategoryPagingResponse = new LibraryCategoryPagingResponse();
		
		if(param.getTitle()==("")||param.getTitle() ==null) {
			libraryCategoryPagingResponse.setLibraryCategoryList(libraryCategoryRepository.findAllLibraryCategories(listing));
			libraryCategoryPagingResponse.setTotalRecords(libraryCategoryRepository.getTotalLibraryCategories());
		} else {
			libraryCategoryPagingResponse.setLibraryCategoryList(libraryCategoryRepository.findByTitle(param.getTitle().toLowerCase(), listing));
			libraryCategoryPagingResponse.setTotalRecords(libraryCategoryRepository.getTotalRecordsByTitle(param.getTitle().toLowerCase()));
		}	
		
		return libraryCategoryPagingResponse;
	}
	
	@PostMapping("/api/librarycategory")
	public LibraryCategoryLanguage saveLibraryCategory(@RequestBody LibraryCategoryDto librarayCategoryDto,Authentication authentication, HttpServletRequest objReq) {
		LibraryCategory savedLibraryCategory = new LibraryCategory();
		LibraryCategoryLanguage libraryCategoryLanguage = new LibraryCategoryLanguage();
		User user = userRepository.findByUsername(authentication.getName()).get();
		if(user.getUserType().getId() == 1)
			{
			System.out.println("asd123");
//			LibraryCategoryLanguage libraryCategoryLanguage = new LibraryCategoryLanguage();
			LibraryCategory libraryCategory = new LibraryCategory();
			Class<?> c =libraryCategory.getClass();
			Optional<User> useropt = userRepository.findByUsername(authentication.getName());
			MetaStatus metaStatus = metaStatusRepository.findById(librarayCategoryDto.getStatus().getId()).get();
			librarayCategoryDto.getLibraryCategory().setStatus(metaStatus);
			libraryCategory = librarayCategoryDto.getLibraryCategory();
			libraryCategory.setCreatedAt(new Date());
			 savedLibraryCategory = libraryCategoryRepository.save(libraryCategory);
			
			MetaLanguage metaLangauge = metaLanguageRepository.findOne(1L);
		
			libraryCategoryLanguage = librarayCategoryDto.getLibraryCategoryLanguage();
			libraryCategoryLanguage.setLanguage(metaLangauge);

			libraryCategoryLanguage.setLibraryCategory(savedLibraryCategory);
			
			 libraryCategoryLanguageRepository.save(libraryCategoryLanguage);
			auditLog.createAuditEntry(useropt.get().getId(), 'A',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), null, savedLibraryCategory);
			
			} 
return libraryCategoryLanguage;
	}
	
	

	
	@PutMapping("/api/librarycategory")
	public LibraryCategoryLanguage updateLibraryCategory(@RequestBody LibraryCategoryDto libraryCategoryDto,Authentication authentication, HttpServletRequest objReq) {
		
		LibraryCategoryLanguage libraryCategoryLanguage = libraryCategoryLanguageRepository.findOne(libraryCategoryDto.getLibraryCategoryLanguage().getId());
		LibraryCategory libraryCategory = libraryCategoryRepository.findOne(libraryCategoryDto.getLibraryCategory().getId());
		LibraryCategory oldLibraryCategory = libraryCategoryRepository.findOne(libraryCategoryDto.getLibraryCategory().getId());
		Class<?> c =libraryCategory.getClass();
		Optional<User> useropt = userRepository.findByUsername(authentication.getName());
		MetaStatus metaStatus = metaStatusRepository.findById(libraryCategoryDto.getStatus().getId()).get();
		libraryCategory.setStatus(metaStatus);
		libraryCategory.setFileName(libraryCategoryDto.getLibraryCategory().getFileName());
		libraryCategory.setFileUrl(libraryCategoryDto.getLibraryCategory().getFileUrl());
		LibraryCategory savedLibraryCategory = libraryCategoryRepository.save(libraryCategory);
		
		libraryCategoryLanguage.setTitle(libraryCategoryDto.getLibraryCategoryLanguage().getTitle());
		libraryCategoryLanguage.setDetails(libraryCategoryDto.getLibraryCategoryLanguage().getDetails());
		libraryCategoryLanguageRepository.save(libraryCategoryLanguage);
		auditLog.createAuditEntry(useropt.get().getId(), 'E',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), oldLibraryCategory, savedLibraryCategory);
		
		return libraryCategoryDto.getLibraryCategoryLanguage();
	}
	
	
	@PostMapping("/api/libraryitem")
	public Long addLibraryItem(@RequestBody LibraryItemDto libraryItemDto,Authentication authentication, HttpServletRequest objReq) {

		Optional<User> useropt = userRepository.findByUsername(authentication.getName());
		User user = useropt.get();
		Class<?> c =libraryItemDto.getLibraryItem().getClass();
		Long metaUserid;
		
		LibraryItem savedLibraryItem = new LibraryItem();
		
		
		libraryItemDto.getLibraryItem().setCreatedBy(user);
	
		libraryItemDto.getLibraryItem().setStatus(metaStatusRepository.findById(libraryItemDto.getMetaStatus().getId()).get());
		libraryItemDto.getLibraryItem().setLibraryCategory(libraryCategoryRepository.findById(libraryItemDto.getLibraryCategory().getId()).get());		
		libraryItemDto.getLibraryItem().setCreatedAt(new Date());	
		
//		LibraryItem oldLibraryItem = libraryItemRepository.findOne(libraryItemDto.getLibraryItem().getId());
		

		
	 savedLibraryItem = libraryItemRepository.save(libraryItemDto.getLibraryItem());
		List<MetaLanguage> metaLanguageList = metaLanguageRepository.findAll();
		for(int i=0;i<metaLanguageList.size();i++) {
			
			LibraryItemLanguage libraryItemLanguage = new LibraryItemLanguage();
			libraryItemLanguage.setDetails(libraryItemDto.getLibraryItemLanguage().getDetails());
			libraryItemLanguage.setTitle(libraryItemDto.getLibraryItemLanguage().getTitle());
			libraryItemLanguage.setLanguage(metaLanguageList.get(i));
			libraryItemLanguage.setItem(savedLibraryItem);
			libraryItemLanguageRepository.save(libraryItemLanguage);
			
		}
		libraryItemDto.getLibraryItemImages().setItem(savedLibraryItem);
		
		libraryItemImagesRepository.save(libraryItemDto.getLibraryItemImages());
//		auditLog.createAuditEntry(useropt.get().getId(), 'A',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), null, savedLibraryItem);
		
		return savedLibraryItem.getId();
	}
	
	
	
	@PutMapping("/api/libraryitem")
	public Long updateLibraryItem(@RequestBody LibraryItemDto libraryItemDto,Authentication authentication, HttpServletRequest objReq) {
		Optional<User> useropt = userRepository.findByUsername(authentication.getName());
		Class<?> c =libraryItemDto.getLibraryItem().getClass();
		LibraryItem oldLibraryItem = libraryItemRepository.findOne(libraryItemDto.getLibraryItem().getId());
		libraryItemDto.getLibraryItem().setStatus(metaStatusRepository.findById(libraryItemDto.getMetaStatus().getId()).get());
		libraryItemDto.getLibraryItem().setCreatedBy(oldLibraryItem.getCreatedBy());
//		libraryItemDto.getLibraryItem().setStatus(libraryItemDto.getMetaStatus());
		
		libraryItemDto.getLibraryItem().setLibraryCategory(libraryCategoryRepository.findById(libraryItemDto.getLibraryCategory().getId()).get());		
//		libraryItemDto.getLibraryItem().setLibraryCategory(libraryItemDto.getLibraryCategory());
		
		
		LibraryItem savedLibraryItem = libraryItemRepository.save(libraryItemDto.getLibraryItem());
		List<MetaLanguage> metaLanguageList = metaLanguageRepository.findAll();
		
		
		List<LibraryItemLanguage> libraryItemLanguageList = libraryItemLanguageRepository.findByItem(savedLibraryItem);
		
		for(int i=0;i<metaLanguageList.size();i++) {
			
			libraryItemLanguageList.get(i).setDetails(libraryItemDto.getLibraryItemLanguage().getDetails());
			libraryItemLanguageList.get(i).setTitle(libraryItemDto.getLibraryItemLanguage().getTitle());
			libraryItemLanguageList.get(i).setLanguage(metaLanguageList.get(i));
			libraryItemLanguageRepository.save(libraryItemLanguageList.get(i));
			
			
		}
		
		libraryItemDto.getLibraryItemImages().setItem(savedLibraryItem);
		libraryItemImagesRepository.save(libraryItemDto.getLibraryItemImages());
//		auditLog.createAuditEntry(useropt.get().getId(), 'E',  objReq.getRemoteAddr(), 	menuService.getMenuID(c), oldLibraryItem, savedLibraryItem);
		return savedLibraryItem.getId();
	}

	
}
