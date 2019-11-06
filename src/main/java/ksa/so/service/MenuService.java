package ksa.so.service;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.Cacheable;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ksa.so.beans.InitialPackage;
import ksa.so.beans.LanguageTermFile;
import ksa.so.beans.Term;
import ksa.so.domain.LanguageTermDefinition;
import ksa.so.domain.LanguageTerms;
import ksa.so.domain.Menu;
import ksa.so.domain.MenuRights;
import ksa.so.domain.MetaLanguage;

import ksa.so.repositories.LanguageTermDefinitionRepository;
import ksa.so.repositories.LanguageTermsRepository;
import ksa.so.repositories.LanguagesRepository;
import ksa.so.repositories.MenuRepository;
import ksa.so.repositories.MetaLanguageRepository;

import ksa.so.repositories.UserRepository;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;
//
//	@Autowired
//	private NotificationLogRepostitory notificationLogRepository;

//	@Autowired
//	private UserRepository usersRepository;

//	@Autowired
//	private ConfigMenuRepository configMenuRepository;
//
//	@Autowired
//	private ModuleRepository moduleRepository;

	@Autowired
	private MetaLanguageRepository languagesRepository;

	@Autowired
	private LanguageTermsRepository languageTermsRepository;

	@Autowired
	private LanguageTermDefinitionRepository languageTermDefinitionRepository;

//	public List<ConfigurationMenu> getMenu(int userId) {
//		return configMenuRepository.searchByempId(userId); 
//	}

	public List<MenuRights> getMainMenu(Long userId) {
		return menuRepository.searchByempId(userId);
	}

//	public List<Menu> getMainMenu(int userId, Module module) {
//		Module moduleId = new Module();
//		if (module.getId() == 0) {
//			moduleId = moduleRepository.findByKey(module.getKey());
//		} else {
//			moduleId = module;
//		}
//		return menuRepository.searchByempIdAndModule(userId, moduleId);
//	}

//	public List<ModuleRightsByProfile> getMainMenu(int userId,List<Integer> modulesintList) {
//		List<Module> moduleslist=  new ArrayList<>();
//		for (int i = 0; i < modulesintList.size(); i++) {
//		moduleslist.add(moduleRepository.findById(modulesintList.get(i)));
//		}
////		return menuRepository.searchByempIdAndModules(userId, moduleslist);
//	}

//	public List<Module> getModules() {
//		return moduleRepository.findAll();
//	}

	@Cacheable("package")
	public InitialPackage getInitialPackage() {
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
						terms.add(new Term(0L, null));
					else
						terms.add(new Term(langTerm.getLanguage().getId(), langTerm.getTerm()));
					// System.out.println(langTerm.getTerm());
				}

				langTermFile.setTerm(terms);
				objLangTermFile.add(langTermFile);

			}
			objPackage.setTerms(objLangTermFile);
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
	
	

	public Long getMenuID(Class<?> c)
	{
		Long menuId = null;
//		 if (c.isAnnotationPresent(javax.persistence.Table.class)) {
//		        Annotation[] table =  c.getAnnotationsByType(javax.persistence.Table.class);
//		        for(Annotation tab :table)
//		        {
//		        	String tableName = ((javax.persistence.Table) tab).name();
//		        	tableName.trim();
//		        	tableName.toUpperCase();
////		        	System.out.println(tableName);
//		        	menuId = menuRepository.getMenuIdByTableName(tableName);
//		        }
//		 }
		
		Table table = c.getAnnotation(Table.class);
		String tableName = table.name();
		menuId = menuRepository.getMenuIdByTableName(tableName);
		return menuId;
	}
	
}
