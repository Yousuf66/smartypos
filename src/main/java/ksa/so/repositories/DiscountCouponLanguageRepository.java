package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.DiscountCouponLanguage;
import ksa.so.domain.MetaLanguage;

public interface DiscountCouponLanguageRepository extends JpaRepository<DiscountCouponLanguage, Long> {
	DiscountCouponLanguage findByDiscountCouponAndLanguage(DiscountCoupon discountCoupon, MetaLanguage language);
	List<DiscountCouponLanguage> findByDiscountCoupon(DiscountCoupon coupon);
	void deleteByDiscountCoupon(DiscountCoupon coupon);
}
