package ksa.so.beans;

import java.io.Serializable;
import java.util.List;

import ksa.so.domain.Item;
import ksa.so.domain.MetaLanguage;

public class Cart implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	List<Item> itemListInfo;

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	String timeStamp;

	MetaLanguage languageInfo;

	public List<Item> getItemListInfo() {
		return itemListInfo;
	}

	public void setItemListInfo(List<Item> itemListInfo) {
		this.itemListInfo = itemListInfo;
	}

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

}
