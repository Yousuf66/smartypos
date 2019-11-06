package ksa.so.beans;

import java.io.Serializable;

import ksa.so.domain.MetaLanguage;

public class ProductRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	MetaLanguage languageInfo;
	String productName;

	public String getProductName() {
		return productName;
	}

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
