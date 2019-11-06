package ksa.so.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ksa.so.beans.BranchItemsDto;
import ksa.so.beans.ItemDto;
import ksa.so.beans.ItemListDto;
import ksa.so.domain.Item;
import ksa.so.domain.ItemLanguage;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemImages;
import ksa.so.domain.LibraryItemLanguage;
import ksa.so.domain.MetaLanguage;
import ksa.so.repositories.BranchRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryRepository;
import ksa.so.repositories.LibraryItemImagesRepository;
import ksa.so.repositories.LibraryItemLanguageRepository;
import ksa.so.repositories.LibraryItemRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;

@RestController
public class ItemController {
	@Autowired
	MetaStatusRepository metaStatusRepository;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	LibraryItemRepository libraryItemRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	MetaLanguageRepository metaLanguageRepository;
	@Autowired
	ItemLanguageRepository itemLanguageRepository;
	@Autowired
	LibraryCategoryRepository libraryCategoryRepository;
	@Autowired
	LibraryItemLanguageRepository libraryItemLanguageRepository;
	@Autowired
	LibraryItemImagesRepository libraryItemImagesRepository;

	@PostMapping("/api/branch/item")
	public Long saveItem(@RequestBody ItemDto itemObj) {
		
		itemObj.getItem().setStatus(metaStatusRepository.findById(itemObj.getMetaStatus().getId()).get());
		itemObj.getItem().setBranch(branchRepository.getOne(itemObj.getBranch().getId()));
		itemObj.getItem().setLibraryItem(libraryItemRepository.getOne(itemObj.getLibraryItem().getId()));
		itemObj.getItem().setCreated(new Date());
		Item savedItem = itemRepository.save(itemObj.getItem());
		List<MetaLanguage> metaLanguageList = metaLanguageRepository.findAll();
		
		for(int i=0;i<metaLanguageList.size();i++) {
		ItemLanguage itemLanguage = new ItemLanguage();
		itemLanguage.setDetails(itemObj.getItemLanguage().getDetails());
		itemLanguage.setTitle(itemObj.getItemLanguage().getTitle());
		itemLanguage.setLanguage(metaLanguageList.get(i));
		itemLanguage.setItem(savedItem);
		itemLanguageRepository.save(itemLanguage);
		}
		return savedItem.getId();
		
	}
	

//	@RequestMapping(method = RequestMethod.POST, value = "api/items")
	@PostMapping("/api/admin/item")
	List<Item> saveAllItems(@RequestBody ItemListDto itemDto) {
		//Saving Library Item
		LibraryItem libraryItem = libraryItemRepository.save(itemDto.getLibraryItem());
		
		List<MetaLanguage> metaLanguage =metaLanguageRepository.findAll();
		//set libraryItemLangauge MetaLanguage, Title and details
		for(int i=0;i<metaLanguage.size();i++) {
			LibraryItemLanguage libraryItemLanguage = new LibraryItemLanguage();
			libraryItemLanguage.setDetails(itemDto.getLibraryItemLanguage().getDetails());
			libraryItemLanguage.setTitle(itemDto.getLibraryItemLanguage().getTitle());
			libraryItemLanguage.setItem(libraryItem);
			libraryItemLanguage.setLanguage(metaLanguage.get(i));
			libraryItemLanguageRepository.save(libraryItemLanguage);
		}
		//save libraryItem image
		LibraryItemImages libraryItemImages = itemDto.getLibraryItemImage();
		libraryItemImages.setItem(libraryItem);
		libraryItemImagesRepository.save(libraryItemImages);
		

	//set libraryItem over each item in the itemList
		List<Item> itemList = new ArrayList<Item>();
		 itemList = itemDto.getItemList();
	
		itemList.forEach((item)->{
			item.setLibraryItem(libraryItem);
			item = itemRepository.save(item);
			for(int i=0;i<metaLanguage.size();i++) {
//				
				ItemLanguage itemLanguages = new ItemLanguage();

			itemLanguages.setItem(item);
			itemLanguages.setDetails(itemDto.getItemLanguages().getDetails());
			itemLanguages.setTitle(itemDto.getItemLanguages().getTitle());
			
			itemLanguages.setLanguage(metaLanguage.get(i));
			itemLanguageRepository.save(itemLanguages);
			}
		});

		
		return itemList;
	}
	@PostMapping("/api/saveitemlist")
	void saveBranchItems(@RequestBody BranchItemsDto itemDto) {


		for(int i=0;i<itemDto.getBranchItems().size();i++) {
			itemDto.getBranchItems().get(i).getItem().setBranch(branchRepository.getOne(itemDto.getBranchId()));
			itemDto.getBranchItems().get(i).getItem().setStatus(metaStatusRepository.findByCode("STA003"));
			itemDto.getBranchItems().get(i).getItem().setLibraryItem(libraryItemRepository.
					findOne(itemDto.getBranchItems().get(i).getLibraryItemId()));
			
			Item savedItem = itemRepository.save(itemDto.getBranchItems().get(i).getItem());
			List<LibraryItemLanguage> libraryItemLanguageList = libraryItemLanguageRepository.
					findByItem(libraryItemRepository.findOne(itemDto.getBranchItems().get(i).getLibraryItemId()));
			
			for(int j=0;j<libraryItemLanguageList.size();j++) {
				ItemLanguage itemLanguage = new ItemLanguage();
				itemLanguage.setDetails(libraryItemLanguageList.get(j).getDetails());
				itemLanguage.setTitle(libraryItemLanguageList.get(j).getTitle());
				itemLanguage.setItem(savedItem);
				itemLanguage.setLanguage(libraryItemLanguageList.get(j).getLanguage());
				itemLanguageRepository.save(itemLanguage);
			}
		}
		
//		return itemList;

	}
	
	
	
	@PutMapping("/api/saveitemlist")
	Object updateBranchItems(@RequestBody BranchItemsDto itemDto) {


		List<Item> itemList = new ArrayList<>();
		for(int i=0;i<itemDto.getBranchItems().size();i++) {
			Item item = itemRepository.findById(itemDto.getBranchItems().get(i).getItem().getId()).get();

			item.setBarcode(itemDto.getBranchItems().get(i).getItem().getBarcode());
			item.setCost(itemDto.getBranchItems().get(i).getItem().getCost());
			item.setQuantity(itemDto.getBranchItems().get(i).getItem().getQuantity());
			item.setPrice(itemDto.getBranchItems().get(i).getItem().getPrice());
			item.setDiscountAmount(itemDto.getBranchItems().get(i).getItem().getDiscountAmount());
			item.setNetSalePrice(itemDto.getBranchItems().get(i).getItem().getNetSalePrice());
			
			Item savedItem = itemRepository.save(item);
			itemList.add(savedItem);

		}
		
		return null;

	}
	
	
}
