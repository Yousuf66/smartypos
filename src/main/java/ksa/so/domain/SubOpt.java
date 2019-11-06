package ksa.so.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.ItemOptSubOptRepository;
import ksa.so.repositories.SubOptLanguageRepository;

@Entity
public class SubOpt {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
		
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
	
	@ManyToOne
    @JoinColumn(name = "FkOpt")
	private Opt opt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public Opt getOpt() {
		return opt;
	}

	public void setOpt(Opt opt) {
		this.opt = opt;
	}

	public JSONObject getInfo(MetaLanguage language, SubOptLanguageRepository subOptLanguageRepository, ItemOptSubOptRepository itemOptSubOptRepository, Item item, Opt opt) {
		JSONObject json = new JSONObject();
				
		//
		SubOptLanguage subOptLanguage = subOptLanguageRepository.findBySubOptAndLanguage(this, language);
		//
		
		//
		ItemOptSubOpt itemOptSubOpt = itemOptSubOptRepository.findByItemAndOpt(item, opt).get(0);
		//
		
		json.put("id", this.getId());
		json.put("price", itemOptSubOpt.getPrice());
		if(subOptLanguage.getTitle() == null){json.put("title", "-");} else {json.put("title", subOptLanguage.getTitle());}
		if(subOptLanguage.getDetails() == null){json.put("details", "-");} else {json.put("details", subOptLanguage.getDetails());}
		
		return json;
	}
	
	public String getTitleLanguage(MetaLanguage language, SubOptLanguageRepository subOptLanguageRepository) {	
		//
		SubOptLanguage subOptLanguage = subOptLanguageRepository.findBySubOptAndLanguage(this, language);
		//
		
		if(subOptLanguage.getTitle() == null){return "-";} else {return subOptLanguage.getTitle();}		
	}
}
