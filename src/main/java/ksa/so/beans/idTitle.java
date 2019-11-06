package ksa.so.beans;

import com.fasterxml.jackson.annotation.JsonView;

import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.MetaStatusLanguage;

public class idTitle {

	Long id;
	String title;
	String name;
	String phone;
	

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		phone = phone;
	}
	public Long getid() {
		return id;
	}
	public void setid(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	public idTitle(Long id, String title, String name, String phone) {
		super();
		this.id = id;
		this.title = title;
		this.name = name;
		this.phone = phone;
	}

	public idTitle(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public idTitle(Long id, String name,String phone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
	}
	




}
