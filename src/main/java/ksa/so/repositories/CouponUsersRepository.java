package ksa.so.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.Branch;
import ksa.so.domain.CouponUsers;
import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.DiscountCouponLanguage;
import ksa.so.domain.Hq;
import ksa.so.domain.UserApp;
import ksa.so.domain.UserShoppingList;

public interface CouponUsersRepository extends JpaRepository<CouponUsers, Long>{
	List<CouponUsers> findByUser(UserApp user);
	CouponUsers findByUserAndDiscountCoupon(UserApp user,DiscountCoupon discountCoupon);
}
