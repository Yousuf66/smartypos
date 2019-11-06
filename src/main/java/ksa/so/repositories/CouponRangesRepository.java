package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.CouponRanges;
import ksa.so.domain.DiscountCoupon;

public interface CouponRangesRepository extends JpaRepository<CouponRanges, Long> {
	void deleteByCoupon(DiscountCoupon discountCoupon);
}
