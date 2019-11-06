package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.CancellationReasonLanguageRepository;
import ksa.so.repositories.CategoryLanguageRepository;

@Entity
public class MetaCancellationReason {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(unique=true, nullable=false)
	@JsonProperty
	private String code = "-";
	
	@Column(nullable=true)
	@JsonProperty
	private String reasonFor = "-";
	
	public Long getId() {
		return id;
	}

	public String getReasonFor() {
		return reasonFor;
	}

	public void setReasonFor(String reasonFor) {
		this.reasonFor = reasonFor;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	public JSONObject getInfo(MetaLanguage language, CancellationReasonLanguageRepository metaCancellationReasonLanguageRepository) {
		JSONObject json = new JSONObject();
		
		//
		MetaCancellationReasonLanguage metaCancellationReasonLanguage = metaCancellationReasonLanguageRepository.findByCancellationReasonAndLanguage(this, language);
		//
		
		json.put("id", this.getId());
		
		if(metaCancellationReasonLanguage.getTitle() == null){json.put("title", "-");} else {json.put("title", metaCancellationReasonLanguage.getTitle());}
		
		return json;
	}
}
