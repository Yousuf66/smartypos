package ksa.so.beans;
import java.util.List;


import ksa.so.domain.MetaLanguage;

public class InitialPackage {
	List<MetaLanguage> languages;
	List<LanguageTermFile> terms;
	
	public List<MetaLanguage> getLanguages() {
		return languages;
	}
	public void setLanguages(List<MetaLanguage> languages) {
		this.languages = languages;
	}
	public List<LanguageTermFile> getTerms() {
		return terms;
	}
	public void setTerms(List<LanguageTermFile> terms) {
		this.terms = terms;
	}
	

}