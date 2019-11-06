package ksa.so.beans;

import java.io.Serializable;
import java.util.List;

import ksa.so.domain.Branch;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.ShoppingListItem;
import ksa.so.domain.UserShoppingList;

public class ShoppingListRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	MetaLanguage languageInfo;

	UserShoppingList userShoppingListInfo;

	String timeStamp;

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	List<ShoppingListItem> shoppingListItemInfo;

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	public UserShoppingList getUserShoppingListInfo() {
		return userShoppingListInfo;
	}

	public void setUserShoppingListInfo(UserShoppingList userShoppingListInfo) {
		this.userShoppingListInfo = userShoppingListInfo;
	}

	public List<ShoppingListItem> getShoppingListItemInfo() {
		return shoppingListItemInfo;
	}

	public void setShoppingListItemInfo(List<ShoppingListItem> shoppingListItemInfo) {
		this.shoppingListItemInfo = shoppingListItemInfo;
	}

	public List<ShoppingListItem> getRemovedShoppingListItemInfo() {
		return removedShoppingListItemInfo;
	}

	public void setRemovedShoppingListItemInfo(List<ShoppingListItem> removedShoppingListItemInfo) {
		this.removedShoppingListItemInfo = removedShoppingListItemInfo;
	}

	List<ShoppingListItem> removedShoppingListItemInfo;

	List<Branch> branchListInfo;

	public List<Branch> getBranchListInfo() {
		return branchListInfo;
	}

	public void setBranchListInfo(List<Branch> branchListInfo) {
		this.branchListInfo = branchListInfo;
	}

}
