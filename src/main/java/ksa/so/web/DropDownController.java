package ksa.so.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ksa.so.beans.CountryDto;
import ksa.so.beans.CurrencyDto;
import ksa.so.beans.LibraryCategoryResponse;
import ksa.so.beans.StatusLanguage;
import ksa.so.beans.idTitle;
import ksa.so.domain.MetaLanguage;
import ksa.so.repositories.CurrencyRepository;
import ksa.so.repositories.LibraryCategoryRepository;
import ksa.so.repositories.MetaCountryRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;

@RestController
public class DropDownController {
	@Autowired
	MetaCountryRepository metaCountryRepository;
	@Autowired
	MetaLanguageRepository metaLanguageRepository;
	@Autowired
	MetaStatusRepository metaStatusRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	LibraryCategoryRepository libraryCategoryRepository;
	

	@PostMapping("/api/getstatus")
	List<idTitle> getAllMetaStatus(@RequestBody StatusLanguage statusLanguage){
		
		ArrayList<String> codes = new ArrayList<String>();
		codes.add("STA003");
		codes.add("STA004");
		codes.add("STA001");
		codes.add("STA002");
		codes.add("STA101");
		codes.add("STA102");
		codes.add("STA103");
		codes.add("STA104");
		codes.add("STA105");
		codes.add("STA106");
		codes.add("STA107");
		codes.add("STA108");
		codes.add("STA109");
		codes.add("STA110");
		codes.add("STA111");
		String metaLanguage = statusLanguage.getMetaLanguage().getCode();
		MetaLanguage language= metaLanguageRepository.findByCode(metaLanguage);
		System.out.println(language.getId());
		return metaStatusRepository.getIdTitle(language, codes);

	}
	
	@GetMapping("api/librarycategory")
	List<LibraryCategoryResponse> getLibraryCategories(){
		
		return libraryCategoryRepository.findAllLibraryCategories();
	}
	
	@GetMapping("/api/librarysubcategory")
	List<LibraryCategoryResponse> getLibrarySubCategories(){
		return libraryCategoryRepository.findAllLibrarySubCategories();
	}
	@GetMapping("/api/countries")
	List<CountryDto> getCountries(){
		return metaCountryRepository.findAllCountries();
	}
	
	@GetMapping("/api/currency")
	List<CurrencyDto> getCurrency(){
		return currencyRepository.getAllCurrencies();
	}
}
