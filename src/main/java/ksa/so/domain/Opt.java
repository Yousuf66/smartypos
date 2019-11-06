package ksa.so.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.ItemOptSubOptRepository;
import ksa.so.repositories.OptLanguageRepository;
import ksa.so.repositories.SubOptLanguageRepository;
import ksa.so.repositories.SubOptRepository;

@Entity
public class Opt {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	boolean isMultiSelect = false;
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
	
	@ManyToOne
    @JoinColumn(name = "FkBranch")
	private Branch branch;
	
	@OneToMany(mappedBy = "opt")
	private List<OptLanguage> optLanguageList;
	
	@OneToMany(mappedBy = "opt")
	private List<SubOpt> subOptList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isMultiSelect() {
		return isMultiSelect;
	}

	public void setMultiSelect(boolean isMultiSelect) {
		this.isMultiSelect = isMultiSelect;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<OptLanguage> getOptLanguageList() {
		return optLanguageList;
	}

	public void setOptLanguageList(List<OptLanguage> optLanguageList) {
		this.optLanguageList = optLanguageList;
	}

	public List<SubOpt> getSubOptList() {
		return subOptList;
	}

	public void setSubOptList(List<SubOpt> subOptList) {
		this.subOptList = subOptList;
	}
	
	public JSONObject getInfo(MetaLanguage language, MetaStatus status, OptLanguageRepository optLanguageRepository, SubOptRepository subOptRepository, SubOptLanguageRepository subOptLanguageRepository, ItemOptSubOptRepository itemOptSubOptRepository, Item item) {
		JSONObject json = new JSONObject();
		JSONArray subOptInfoListJson = new JSONArray();
		
		OptLanguage optLanguage = optLanguageRepository.findByOptAndLanguage(this, language);
		
		json.put("id", this.getId());
		json.put("isMultiSelect", this.isMultiSelect());
		if(optLanguage.getTitle() == null){json.put("title", "-");} else {json.put("title", optLanguage.getTitle());}
		if(optLanguage.getDetails() == null){json.put("details", "-");} else {json.put("details", optLanguage.getDetails());}
		
		//sub options
		List<SubOpt> subOptList = subOptRepository.findByOptAndStatus(this, status);
		for(SubOpt subOpt : subOptList) {
			subOptInfoListJson.put(subOpt.getInfo(language, subOptLanguageRepository, itemOptSubOptRepository, item, this));
		}
		//sub option
		
		json.put("subOptInfoList", subOptInfoListJson);
		
		return json;
	}
	
	public String getSubOptions(MetaLanguage language, MetaStatus status, SubOptRepository subOptRepository, SubOptLanguageRepository subOptLanguageRepository) {		
		String subOptions = "-";
		
		//sub options
		List<SubOpt> subOptList = subOptRepository.findByOptAndStatus(this, status);
		List<String> SubOptionList = new ArrayList<String>();
		for(SubOpt subOpt : subOptList) {
			SubOptionList.add(subOpt.getTitleLanguage(language, subOptLanguageRepository));
		}
		subOptions = StringUtils.join(SubOptionList, ", ");
		//sub option
		
		return subOptions;
	}
}
