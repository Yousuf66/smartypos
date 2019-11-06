package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.json.JSONObject;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.DiscountCouponLanguageRepository;
import ksa.so.repositories.DiscountCouponRepository;
import ksa.so.repositories.MetaDayLanguageRepository;

@Entity
public class CouponUsers {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;


	private double maxAmount = (long)0; 	
	private double amountUsed = (long)0;
	private Long usageNumber = (long)1;
	private Long totalUsage = (long)1;
	private boolean unlimited = false;
	
	public Long getUsageNumber() {
		return usageNumber;
	}


	public void setUsageNumber(Long usageNumber) {
		this.usageNumber = usageNumber;
	}

	public Long getTotalUsage() {
		return totalUsage;
	}

	public void setTotalUsage(Long totalUsage) {
		this.totalUsage = totalUsage;
	}

	public boolean isUnlimited() {
		return unlimited;
	}

	public void setUnlimited(boolean unlimited) {
		this.unlimited = unlimited;
	}

	@OneToOne
	@JoinColumn(name="FkDiscountCoupon", nullable=false)
	private DiscountCoupon discountCoupon;
	

	@OneToOne
	@JoinColumn(name="FkUser", nullable=false)
	private UserApp user;
	
	public UserApp getUserApp() {
		return user;
	}

	public void setUserApp(UserApp user) {
		this.user = user;
	}
	
	public DiscountCoupon getDiscountCoupon() {
		return discountCoupon;
	}

	public void setDiscountCoupon(DiscountCoupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}
	
	public double getAmountUsed() {
		return amountUsed;
	}

	public void setAmountUsed(double amountUsed) {
		this.amountUsed = amountUsed;
	}
	
	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JSONObject getInfo(){
		JSONObject json = new JSONObject();	
		return json;
	}
	
	public JSONObject getInfo(DiscountCouponRepository discountCouponRepository,MetaLanguage language,DiscountCouponLanguageRepository discountCouponLanguageRepository,BranchLanguageRepository branchLanguageRepository,
		MetaDayLanguageRepository dayLanguageRepository) {
	
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("maxAmount", this.getMaxAmount());
		json.put("AmountUsed",this.getAmountUsed());
		json.put("totalUsage", this.getTotalUsage());
		json.put("usageNumber", this.getUsageNumber());
		json.put("unlimited", this.isUnlimited());
		//json.put("totalItems", discountCouponRepository.getInfo();
		json.put("discountCoupon",  this.getDiscountCoupon().getInfo(language,discountCouponLanguageRepository,branchLanguageRepository,dayLanguageRepository));
		
		return json;
	}



}

