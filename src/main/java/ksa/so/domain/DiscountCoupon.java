package ksa.so.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.DiscountCouponLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;

@Entity
@Table(name="discount_coupon")
public class DiscountCoupon {
	@Id
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String code;
	private Long percentage = (long) 0;
	private String couponType = "C";
	private Date expiryDate;
	private Date startingDate;
	private String promocode;
	private Double maxAmountPerUse = (double) 0;
	@ManyToOne
	@JoinColumn(name = "FkStatus", nullable = false)
	private MetaStatus status;
	@OneToMany(mappedBy = "discountCoupon")
	private List<DiscountCouponLanguage> discountCouponLanguageList;
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";

	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";

	

	@ManyToOne
	@JoinColumn(name = "CreatedBy")
	private User CreatedBy;

	@ManyToOne
	@JoinColumn(name = "UpdatedBy")
	private User UpdatedBy;

	@Column(name = "UpdatedAt")
	private Date updated;

	@Column(name = "CreatedAt")
	private Date created;

	//new attributes
		private String nature;
		private Long minOrderValue;
		private Long maxOrderValue;
		private Long totalVouchersIssued;
		private Long usageLimitPerCustomer;
		private String applyTo;


	public String getPromocode() {
		return promocode;
	}

	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public Long getMinOrderValue() {
		return minOrderValue;
	}

	public void setMinOrderValue(Long minOrderValue) {
		this.minOrderValue = minOrderValue;
	}

	public Long getMaxOrderValue() {
		return maxOrderValue;
	}

	public void setMaxOrderValue(Long maxOrderValue) {
		this.maxOrderValue = maxOrderValue;
	}

	public Long getTotalVouchersIssued() {
		return totalVouchersIssued;
	}

	public void setTotalVouchersIssued(Long totalVouchersIssued) {
		this.totalVouchersIssued = totalVouchersIssued;
	}

	public Long getUsageLimitPerCustomer() {
		return usageLimitPerCustomer;
	}

	public void setUsageLimitPerCustomer(Long usageLimitPerCustomer) {
		this.usageLimitPerCustomer = usageLimitPerCustomer;
	}

	public String getApplyTo() {
		return applyTo;
	}

	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}

	public List<DiscountCouponLanguage> getDiscountCouponLanguageList() {
		return discountCouponLanguageList;
	}

	public void setDiscountCouponLanguageList(List<DiscountCouponLanguage> discountCouponLanguageList) {
		this.discountCouponLanguageList = discountCouponLanguageList;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setMaxAmountPerUse(Double maxAmountPerUse) {
		this.maxAmountPerUse = maxAmountPerUse;
	}

	public User getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(User createdBy) {
		CreatedBy = createdBy;
	}

	public User getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		UpdatedBy = updatedBy;
	}

	public Date getCreatedAt() {
		return created;
	}

	public void setCreatedAt(Date created) {
		this.created = created;
	}

	public Date getUpdatedAt() {
		return updated;
	}

	public void setUpdatedAt(Date updated) {
		this.updated = updated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPromoCode() {
		return promocode;
	}

	public void setPromoCode(String promocode) {
		this.promocode = promocode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public Long getPercentage() {
		return percentage;
	}

	public void setPercentage(Long percentage) {
		this.percentage = percentage;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getMaxAmountPerUse() {
		return maxAmountPerUse;
	}

	public void setMaxAmount(double maxAmountPerUse) {
		this.maxAmountPerUse = maxAmountPerUse;
	}

	public Date getStartingDate() {
		return this.startingDate;
	}

	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	public JSONObject getInfo(MetaLanguage language, DiscountCouponLanguageRepository discountCouponLanguageRepository,
			BranchLanguageRepository branchLanguageRepository, MetaDayLanguageRepository dayLanguageRepository) {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		DiscountCouponLanguage discountCouponLanguage = discountCouponLanguageRepository
				.findByDiscountCouponAndLanguage(this, language);
		if (discountCouponLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", discountCouponLanguage.getTitle());
		}
		if (discountCouponLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", discountCouponLanguage.getDetails());
		}

		json.put("maxamount", this.getMaxAmountPerUse());
		json.put("fileURL", this.getFileUrl());

		json.put("fileName", this.getFileName());
		json.put("code", this.getCode());
		json.put("promocode", this.getPromoCode());
		if (this.getExpiryDate() == null) {
			json.put("expiryDate", "- ");
		} else {
			json.put("expiryDate", this.getExpiryDate());
		}
		if (this.getStartingDate() == null) {
			json.put("startingDate", "- ");
		} else {
			json.put("startingDate", this.getStartingDate());
		}
		return json;
	}



	public DiscountCoupon() {
		super();
	}

	public DiscountCoupon(Long id, String code, Long percentage, String couponType, Date expiryDate, Date startingDate,
			String promocode, Double maxAmountPerUse, MetaStatus status,
			List<DiscountCouponLanguage> discountCouponLanguageList, String fileName, String fileUrl, User createdBy,
			User updatedBy, Date updated, Date created, String nature, Long minOrderValue, Long maxOrderValue,
			Long totalVouchersIssued, Long usageLimitPerCustomer, String applyTo) {
		super();
		this.id = id;
		this.code = code;
		this.percentage = percentage;
		this.couponType = couponType;
		this.expiryDate = expiryDate;
		this.startingDate = startingDate;
		this.promocode = promocode;
		this.maxAmountPerUse = maxAmountPerUse;
		this.status = status;
		this.discountCouponLanguageList = discountCouponLanguageList;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		CreatedBy = createdBy;
		UpdatedBy = updatedBy;
		this.updated = updated;
		this.created = created;
		this.nature = nature;
		this.minOrderValue = minOrderValue;
		this.maxOrderValue = maxOrderValue;
		this.totalVouchersIssued = totalVouchersIssued;
		this.usageLimitPerCustomer = usageLimitPerCustomer;
		this.applyTo = applyTo;
	}

}
