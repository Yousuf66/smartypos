package ksa.so.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.BranchCoupon;


public interface BranchCouponRepository extends JpaRepository<BranchCoupon, Long>{
	
}
