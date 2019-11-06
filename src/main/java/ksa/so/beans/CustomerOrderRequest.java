package ksa.so.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ksa.so.domain.CustomerOrder;
import ksa.so.domain.Item;
import ksa.so.domain.MetaCancellationReason;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.User;

public class CustomerOrderRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	CustomerOrder orderInfo;
	MetaLanguage languageInfo;
	MetaCancellationReason metaCancellationReasonInfo;
	String timeStamp;
	User user;
	MetaStatus metaStatus;
	ArrayList<String> metaStatusListInfo;
	int listSize;
	int listStart;
	Double amountRecieved;
	List<Item> updatedItemInfoList;
	List<User> operatorList;

	public List<User> getOperatorList() {
		return operatorList;
	}

	public void setOperatorList(List<User> operatorList) {
		this.operatorList = operatorList;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	String searchTerm;

	public List<Item> getUpdatedItemInfoList() {
		return updatedItemInfoList;
	}

	public void setUpdatedItemInfoList(List<Item> updatedItemInfoList) {
		this.updatedItemInfoList = updatedItemInfoList;
	}

	public Double getAmountRecieved() {
		return amountRecieved;
	}

	public void setAmountRecieved(Double amountRecieved) {
		this.amountRecieved = amountRecieved;
	}

	public MetaStatus getMetaStatus() {
		return metaStatus;
	}

	public void setMetaStatus(MetaStatus metaStatus) {
		this.metaStatus = metaStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public CustomerOrder getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(CustomerOrder orderInfo) {
		this.orderInfo = orderInfo;
	}

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	public MetaCancellationReason getMetaCancellationReasonInfo() {
		return metaCancellationReasonInfo;
	}

	public void setMetaCancellationReasonInfo(MetaCancellationReason metaCancellationReasonInfo) {
		this.metaCancellationReasonInfo = metaCancellationReasonInfo;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public ArrayList<String> getMetaStatusListInfo() {
		return metaStatusListInfo;
	}

	public void setMetaStatusListInfo(ArrayList<String> metaStatusListInfo) {
		this.metaStatusListInfo = metaStatusListInfo;
	}

	public int getListStart() {
		return listStart;
	}

	public void setListStart(int listStart) {
		this.listStart = listStart;
	}

}
