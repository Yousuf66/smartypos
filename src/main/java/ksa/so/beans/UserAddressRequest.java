package ksa.so.beans;

import java.io.Serializable;

import ksa.so.domain.MetaLanguage;
import ksa.so.domain.UserAddress;

public class UserAddressRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	UserAddress userAddressInfo;

	private MetaLanguage languageInfo;

	public UserAddress getUserAddressInfo() {
		return userAddressInfo;
	}

	public void setUserAddressInfo(UserAddress userAddressInfo) {
		this.userAddressInfo = userAddressInfo;
	}

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

}
