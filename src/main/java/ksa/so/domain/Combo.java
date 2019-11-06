package ksa.so.domain;

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
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.ComboItemRepository;
import ksa.so.repositories.ComboItemSubOptRepository;
import ksa.so.repositories.ComboLanguageRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.SubOptLanguageRepository;
import ksa.so.repositories.SubOptRepository;

@Entity
public class Combo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long price = (long)0;
	
	boolean isSpecial = false;
	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";
	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
	
	@ManyToOne
    @JoinColumn(name = "FkBranch", nullable=false)
	private Branch branch;
	
	@OneToMany(mappedBy = "combo")
	private List<ComboItem> comboItemList;
	
	@OneToMany(mappedBy = "combo")
	private List<ComboLanguage> comboLanguageList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public boolean isSpecial() {
		return isSpecial;
	}

	public void setSpecial(boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
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

	public List<ComboItem> getComboItemList() {
		return comboItemList;
	}

	public void setComboItemList(List<ComboItem> comboItemList) {
		this.comboItemList = comboItemList;
	}

	public List<ComboLanguage> getComboLanguageList() {
		return comboLanguageList;
	}

	public void setComboLanguageList(List<ComboLanguage> comboLanguageList) {
		this.comboLanguageList = comboLanguageList;
	}
	
	public JSONObject getInfo(MetaLanguage language, ComboLanguageRepository comboLanguageRepository, MetaCurrency currency) {
		JSONObject json = new JSONObject();
		
		//
		ComboLanguage comboLanguage = comboLanguageRepository.findByComboAndLanguage(this, language);
		//
		
		json.put("id", this.getId());
		if(comboLanguage.getTitle() == null){json.put("title", "-");} else {json.put("title", comboLanguage.getTitle());}
		if(comboLanguage.getDetails() == null){json.put("details", "-");} else {json.put("details", comboLanguage.getDetails());}
		if(this.getFileName() == null) {json.put("fileName", "-");} else {json.put("fileName", this.getFileName());}
		if(this.getFileUrl() == null) {json.put("fileUrl", "-");} else {json.put("fileUrl", this.getFileUrl());}
		if(this.getPrice() == null) {json.put("price", "- " + currency.getSymbol());} else {json.put("price", this.getPrice() + " " + currency.getSymbol());}
		
		return json;
	}
	
	public JSONObject getComboItemInfo(MetaLanguage language, MetaStatus status, 
			ComboItemRepository comboItemRepository, 
			ComboLanguageRepository comboLanguageRepository, 
			ItemLanguageRepository itemLanguageRepository, 
			SubOptRepository subOptRepository, 
			SubOptLanguageRepository subOptLanguageRepository, 
			ComboItemSubOptRepository comboItemSubOptrepository) {
		JSONObject json = new JSONObject();
		
		//
		ComboLanguage comboLanguage = comboLanguageRepository.findByComboAndLanguage(this, language);
		//
		
		json.put("id", this.getId());
		if(comboLanguage.getTitle() == null){json.put("title", "-");} else {json.put("title", comboLanguage.getTitle());}
		
		List<ComboItem> comboItemList = comboItemRepository.findByComboAndStatus(this, status);
		
		List<String> details = new ArrayList<String>();
		
		for(ComboItem comboItem : comboItemList) {
			if(itemLanguageRepository.findByItemAndLanguage(comboItem.getItem(), language).getTitle() != null){
				List<String> options = new ArrayList<String>();
				
				List<ComboItemSubOpt> comboItemSubOptList = comboItemSubOptrepository.findByComboItem(comboItem);
				
				for(ComboItemSubOpt comboItemSubOpt : comboItemSubOptList) {
					options.add(comboItemSubOpt.getSubOpt().getTitleLanguage(language, subOptLanguageRepository));
				}
				
				if(options.isEmpty()) {
					details.add(itemLanguageRepository.findByItemAndLanguage(comboItem.getItem(), language).getTitle());
				} else {
					details.add(
								itemLanguageRepository.findByItemAndLanguage(comboItem.getItem(), language).getTitle()
								+ "(" + StringUtils.join(options, ", ") + ")"
							);
				}
			}
		}
		if(details.isEmpty()){
			json.put("details", "-");
		} else {
			json.put("details", StringUtils.join(details, ", "));
		}
		
		if(this.getFileName() == null) {json.put("fileName", "-");} else {json.put("fileName", this.getFileName());}
		if(this.getFileUrl() == null) {json.put("fileUrl", "-");} else {json.put("fileUrl", this.getFileUrl());}
		if(this.getPrice() == null) {json.put("price", "-");} else {json.put("price", this.getPrice());}
				
		//item status for branch
		JSONObject statusInfoJson = new JSONObject();
		statusInfoJson.put("code", "STA003");
		
		json.put("statusInfo", statusInfoJson);
		
		return json;
	}
}
