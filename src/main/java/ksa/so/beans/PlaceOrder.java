package ksa.so.beans;

import java.io.Serializable;
import java.util.List;

import ksa.so.domain.Branch;
import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.MetaDay;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaTime;
import ksa.so.domain.OrderItem;
import ksa.so.domain.UserAddress;

public class PlaceOrder implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;

	String instructionComment;
	String instructionAudio;
	Branch branchInfo;
	UserAddress userAddressInfo;
	DiscountCoupon discountCouponInfo;
	MetaLanguage languageInfo;
	List<OrderItem> orderItemListInfo;

	public Double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(Double walletAmount) {
		this.walletAmount = walletAmount;
	}

	public Double getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Double discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	Double actualAmount;
	Double totalAmount;
	Double walletAmount;
	Double discountedAmount;

	public List<OrderItem> getOrderItemListInfo() {
		return orderItemListInfo;
	}

	public void setOrderItemListInfo(List<OrderItem> orderItemListInfo) {
		this.orderItemListInfo = orderItemListInfo;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	String timeStamp;

	public String getInstructionComment() {
		return instructionComment;
	}

	public MetaLanguage getLanguageInfo() {
		return languageInfo;
	}

	public void setLanguageInfo(MetaLanguage languageInfo) {
		this.languageInfo = languageInfo;
	}

	public void setInstructionComment(String instructionComment) {
		this.instructionComment = instructionComment;
	}

	public String getInstructionAudio() {
		return instructionAudio;
	}

	public void setInstructionAudio(String instructionAudio) {
		this.instructionAudio = instructionAudio;
	}

	public Branch getBranchInfo() {
		return branchInfo;
	}

	public void setBranchInfo(Branch branchInfo) {
		this.branchInfo = branchInfo;
	}

	public UserAddress getUserAddressInfo() {
		return userAddressInfo;
	}

	public void setUserAddressInfo(UserAddress userAddressInfo) {
		this.userAddressInfo = userAddressInfo;
	}

	public DiscountCoupon getDiscountCouponInfo() {
		return discountCouponInfo;
	}

	public void setDiscountCouponInfo(DiscountCoupon discountCouponInfo) {
		this.discountCouponInfo = discountCouponInfo;
	}

	public Boolean getIsTakeaway() {
		return isTakeaway;
	}

	public void setIsTakeaway(Boolean isTakeaway) {
		this.isTakeaway = isTakeaway;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public MetaTime getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(MetaTime timeSlots) {
		this.timeSlots = timeSlots;
	}

	public MetaDay getDay() {
		return day;
	}

	public void setDay(MetaDay day) {
		this.day = day;
	}

	Boolean isTakeaway;
	String pickupTime;
	MetaTime timeSlots;
	MetaDay day;

}
