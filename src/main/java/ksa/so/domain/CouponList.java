package ksa.so.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CouponList {
	
	private Long id;
	private String code;
	private Long percentage = (long) 0;

	

	private Date expiryDate;
	private Date startingDate;
	private String promocode;


	private Double maxAmountPerUse = (double) 0;

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

	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
			


	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";
	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";
	
	@ManyToOne
	@JoinColumn(name="CreatedBy")
	private User CreatedBy;

	

	@ManyToOne
	@JoinColumn(name="UpdatedBy")
	private User UpdatedBy;

	@Column(name="UpdatedAt")
	private Date updated;

	@Column(name="CreatedAt")
	private Date created;
	
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code=code;
	}
	
	public String getPromoCode() {
		return promocode;
	}
	
	public void setPromoCode(String promocode) {
		this.promocode=promocode;
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

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}
	public CouponList(Long id, String code, Long percentage, Date expiryDate, Date startingDate, String promocode,
			Double maxAmountPerUse, double maxAmount, double amountUsed, Long usageNumber, Long totalUsage,
			boolean unlimited, MetaStatus status, String fileName, String fileUrl, String title, String details,
			Branch branch, UserApp user) {
		super();
		this.id = id;
		this.code = code;
		this.percentage = percentage;
		this.expiryDate = expiryDate;
		this.startingDate = startingDate;
		this.promocode = promocode;
		this.maxAmountPerUse = maxAmountPerUse;
		this.maxAmount = maxAmount;
		this.amountUsed = amountUsed;
		this.usageNumber = usageNumber;
		this.totalUsage = totalUsage;
		this.unlimited = unlimited;
		this.status = status;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.title = title;
		this.details = details;
		this.branch = branch;
		this.user = user;
	}


	public String getPromocode() {
		return promocode;
	}


	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}


	public double getAmountUsed() {
		return amountUsed;
	}


	public void setAmountUsed(double amountUsed) {
		this.amountUsed = amountUsed;
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


	public UserApp getUser() {
		return user;
	}


	public void setUser(UserApp user) {
		this.user = user;
	}


	public void setStartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}


	public void setMaxAmountPerUse(Double maxAmountPerUse) {
		this.maxAmountPerUse = maxAmountPerUse;
	}


	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmountPerUse(double maxAmountPerUse) {
		this.maxAmountPerUse = maxAmountPerUse;
	}

	
	public Date getStartingDate() {
		return this.startingDate;
	}

	public void setEtartingDate(Date startingDate) {
		this.startingDate = startingDate;
	}

	@JsonProperty
	@Column(columnDefinition = "text")
	private String title = "-";
	
	@JsonProperty
	@Column(columnDefinition = "text")
	private String details = "-";
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@ManyToOne
	@JoinColumn(name = "FkBranch", nullable=false)
	private Branch branch;
	
	public Branch getBranch() {
		return branch;
		}

		public void setBranch(Branch branch) {
		this.branch = branch;
		}

		@OneToOne
		@JoinColumn(name="FkUser", nullable=false)
		private UserApp user;
		
		public UserApp getUserApp() {
			return user;
		}

		public void setUserApp(UserApp user) {
			this.user = user;
		}
		
	public CouponList(Long id,String code,String promocode,Long Percentage,Double maxAmountPerUse 
		, double maxAmount,Double amountUsed, Long usageNumber,Long totalUsage,
boolean unlimited,String fileName,String fileUrl,Branch branch,String title,String details) {
		super();
		this.id = id;
		this.code = code;
		this.promocode = promocode;
		this.maxAmountPerUse = maxAmountPerUse;
		this.percentage = Percentage;
		this.maxAmount = maxAmount;
		this.amountUsed = amountUsed;
		this.usageNumber = usageNumber;
		this.totalUsage = totalUsage;
		this.unlimited = unlimited;
		this.fileName= fileName;
		this.fileUrl = fileUrl;
		this.branch = branch;
		
		this.title = title;
		this.details = details;
	}
	
	public JSONObject getInfo() {
		JSONObject json = new JSONObject();
		json.put("id",this.getId());
		json.put("code",this.getCode());
		json.put("promocode",this.getPromoCode());
		json.put("maxAmountPerUse",this.getMaxAmountPerUse());
		json.put("percentage",this.getPercentage());
		json.put("maxAmount",this.getMaxAmount());
		json.put("amountUsed",this.getAmountUsed());
		json.put("usageNumber",this.getUsageNumber());
		json.put("totalUsage",this.getTotalUsage());
		json.put("unlimited", this.isUnlimited());
		json.put("fileURL", this.getFileUrl());
		json.put("fileName", this.getFileName());
		json.put("title",this.getTitle());
		json.put("title",this.getDetails());
		json.put("branch",this.getBranch().getId());
		return json;
	}
	
}
