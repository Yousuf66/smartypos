package ksa.so.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ksa.so.beans.InitialPackage;
import ksa.so.beans.LanguageTermFile;
import ksa.so.beans.Term;
import ksa.so.domain.LanguageTermDefinition;
import ksa.so.domain.LanguageTerms;
import ksa.so.domain.MetaLanguage;
import ksa.so.repositories.LanguageTermDefinitionRepository;
import ksa.so.repositories.LanguageTermsRepository;
import ksa.so.repositories.LanguagesRepository;
//import ksa.so.repositories.MenuRepository;
//import ksa.so.service.MenuService;


@Controller
public class security {
	
//
//	@Autowired
//	private UserService userService;
////
//	@Autowired
//	private MenuService menuService;
	
	
	@Autowired 
	LanguageTermsRepository languageTermsRepository;
	@Autowired
	LanguagesRepository languagesRepository;
	@Autowired
	LanguageTermDefinitionRepository languageTermDefinitionRepository;
//	@Autowired
//	MenuRepository menuRepository;
//	
	
	

	@GetMapping("/api/ipackage")
	@ResponseBody
	public InitialPackage getPackage() {
		try {
			//	List<LanguageTerms> objLangTerms = languageTermsRepository.findAllByOrderByIdAsc();
				List<LanguageTerms> objLangTerms = languageTermsRepository.findByTermDefinitionIsNotNull();
				List<MetaLanguage> objLang = languagesRepository.findAllByOrderByIdAsc();
				List<LanguageTermDefinition> objLangTermDef = languageTermDefinitionRepository.findAllByOrderByIdAsc();

				InitialPackage objPackage = new InitialPackage();
				objPackage.setLanguages(objLang);
				List<LanguageTermFile> objLangTermFile = new ArrayList<LanguageTermFile>();

				for (LanguageTermDefinition langTermDef : objLangTermDef) {
					LanguageTermFile langTermFile = new LanguageTermFile();
					langTermFile.setId(langTermDef.getKey());
					
					List<Term> terms = new ArrayList<Term>();

					for (MetaLanguage lang : objLang) {
						LanguageTerms langTerm = getLangTerm(langTermDef, lang, objLangTerms);
						if (langTerm == null)
							terms.add(new Term(0L, ""));
						else
							terms.add(new Term(langTerm.getLanguage().getId(), langTerm.getTerm()));
						// System.out.println(langTerm.getTerm());
					}

					langTermFile.setTerm(terms);
					objLangTermFile.add(langTermFile);

				}
				objPackage.setTerms(objLangTermFile);
//				objPackage.setLanguages(objLang);
				return objPackage;
			} catch (Exception e) {

				return null;
			}
    }
	private LanguageTerms getLangTerm(LanguageTermDefinition langDef, MetaLanguage language,
			List<LanguageTerms> objLangTerms) {
		
		try {
			LanguageTerms languageTerm = null;
			
			for (LanguageTerms languageTerms : objLangTerms) {
				if(languageTerms.getTermDefinition()==null)
				continue;
			else if(languageTerms.getLanguage()==null)
			continue;
			else if (languageTerms.getLanguage().equals(language) && languageTerms.getTermDefinition().equals(langDef))
				{
					languageTerm = languageTerms;
				}
			}
			return languageTerm;
		} catch (Exception e) {
			return null;
		}

	}


}
//
