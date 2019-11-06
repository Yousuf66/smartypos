package ksa.so.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ksa.so.beans.ItemBasic;
import ksa.so.beans.ItemResponse;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryItemLanguageRepository;

@Service
public class UserService {

	@Autowired
	LibraryItemLanguageRepository libraryItemLanguageRepository;

	@Autowired
	ItemRepository itemRepository;

	// @Cacheable("searchProducts")
	public ArrayList<ItemBasic> searchList(String searchPhrase, Long categoryId, Long fk_language, Long branchId,
			Long statusId) {

		return libraryItemLanguageRepository.customSearch("" + searchPhrase.toLowerCase() + "%",
				"% " + searchPhrase.toLowerCase() + "%", fk_language, categoryId, branchId, statusId,
				new PageRequest(0, 50));
	}

	public ArrayList<ItemResponse> searchItemList(String searchPhrase, Long fk_language, Long branchId) {

		return itemRepository.customSearchItems("" + searchPhrase.toLowerCase() + "%",
				"% " + searchPhrase.toLowerCase() + "%", fk_language, branchId, (long) 3, new PageRequest(0, 50));
	}

}