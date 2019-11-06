package ksa.so.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.Branch;
import ksa.so.domain.CouponList;
import ksa.so.domain.DiscountCoupon;
import ksa.so.domain.Hq;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.SalesReport;
import ksa.so.domain.ShoppingListItem;
import ksa.so.domain.UserApp;
import ksa.so.domain.UserShoppingList;

public interface DiscountCouponRepository extends JpaRepository<DiscountCoupon, Long>{
	List<DiscountCoupon> findById(DiscountCoupon discountCoupon);
	@Query("SELECT new ksa.so.domain.CouponList(dc.id,dc.code,dc.promocode, coalesce(dc.percentage,0), coalesce(dc.maxAmountPerUse,0)" + 
			", coalesce(cu.maxAmount,0) ,coalesce(cu.amountUsed,0) , coalesce(cu.usageNumber,0) ,coalesce(cu.totalUsage,0), " + 
			"cu.unlimited, dc.fileName,dc.fileUrl, bc.branch,dcl.title,dcl.details)  from DiscountCoupon dc,BranchCoupon bc," + 
			"CouponUsers cu,DiscountCouponLanguage dcl," + 
			"			MetaLanguage ml" + 
			" where bc.branch =:branch and bc.discountCoupon = dc.id and cu.discountCoupon = dc.id and dcl.discountCoupon = dc.id and ml.id = dcl.language"
			+ " and cu.user =:userApp and dcl.language=:language and dc.expiryDate > now() and cu.maxAmount > cu.amountUsed and cu.usageNumber < cu.totalUsage")
	List<CouponList> findByUserAndBranch( @Param("branch") Branch branch,@Param("userApp") UserApp userApp,@Param("language") MetaLanguage language);


}
