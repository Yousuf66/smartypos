package ksa.so.beans;

import java.util.List;

import ksa.so.domain.CouponUsers;
import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.DiscountCouponLanguage;
import ksa.so.domain.MetaStatus;

public class CouponDto {

	DiscountCoupon discountCoupon;

	DiscountCouponLanguage discountCouponLanguage; 
	MetaStatus status;
	CouponUsers couponUsers;
	List<Long> userApp;
	
	
	public CouponDto(DiscountCoupon discountCoupon, DiscountCouponLanguage discountCouponLanguage, MetaStatus status,
			CouponUsers couponUsers, List<Long> userApp) {
		super();
		this.discountCoupon = discountCoupon;
		this.discountCouponLanguage = discountCouponLanguage;
		this.status = status;
		this.couponUsers = couponUsers;
		this.userApp = userApp;
	}
	public CouponUsers getCouponUsers() {
		return couponUsers;
	}
	public void setCouponUsers(CouponUsers couponUsers) {
		this.couponUsers = couponUsers;
	}
	
	public List<Long> getUserApp() {
		return userApp;
	}
	public void setUserApp(List<Long> userApp) {
		this.userApp = userApp;
	}
	public MetaStatus getStatus() {
		return status;
	}
	public void setStatus(MetaStatus status) {
		this.status = status;
	}


	public DiscountCoupon getDiscountCoupon() {
		return discountCoupon;
	}
	public void setDiscountCoupon(DiscountCoupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}
	public DiscountCouponLanguage getDiscountCouponLanguage() {
		return discountCouponLanguage;
	}
	public void setDiscountCouponLanguage(DiscountCouponLanguage discountCouponLanguage) {
		this.discountCouponLanguage = discountCouponLanguage;
	}
	public CouponDto(DiscountCoupon discountCoupon, DiscountCouponLanguage discountCouponLanguage, MetaStatus status,
			CouponUsers couponUsers) {
		super();
		this.discountCoupon = discountCoupon;
		this.discountCouponLanguage = discountCouponLanguage;
		this.status = status;
		this.couponUsers = couponUsers;
	}
	public CouponDto() {
	
	}
	

}
