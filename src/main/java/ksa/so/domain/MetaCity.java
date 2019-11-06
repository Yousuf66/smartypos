package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class MetaCity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String code;
	
	@ManyToOne
	private MetaCountry metaCountry;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MetaCountry getMetaCountry() {
		return metaCountry;
	}

	public void setMetaCountry(MetaCountry metaCountry) {
		this.metaCountry = metaCountry;
	}

	public MetaCity(Long id, String name, MetaCountry metaCountry) {
		super();
		this.id = id;
		this.name = name;
		this.metaCountry = metaCountry;
	}

}
