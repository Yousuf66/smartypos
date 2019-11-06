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
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.Timestamp;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.CategoryLanguageRepository;
import ksa.so.repositories.FlashSaleItemRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemOptSubOptRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;
import ksa.so.repositories.OptLanguageRepository;
import ksa.so.repositories.SubOptLanguageRepository;
import ksa.so.repositories.SubOptRepository;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long minQuantity = (long) 0;
	private Long maxQuantity = (long) 0;
	private Long expiredQuantity = (long) 0;
	private Long quantity = (long) 0;
	private String discountType;
	private Timestamp expiryDate;
	private String barcode;
	private String brand;
	private String keyFeature;
	private String sku;
	private Timestamp discountStart;
	private Timestamp discountEnd;
	@Column(name = "price", columnDefinition = "Decimal(10,2) default '0.00'")
	private double price = 0;

	@Column(name = "cost", columnDefinition = "Decimal(10,2) default '0.00'")
	private double cost = 0;

	@Column(name = "discountAmount", columnDefinition = "Decimal(10,2) default '0.00'")
	private double discountAmount = 0;

	@Column(name = "discountPercentage", columnDefinition = "Decimal(10,2) default '0.00'")
	private Double discountPercentage = 0.0;

	@Column(name = "netSalePrice", columnDefinition = "Decimal(10,2) default '0.00'")
	private double netSalePrice = 0;
	
	
	@OneToMany(mappedBy = "item")
	private List<ItemLanguage> itemLanguageList;

	@OneToMany(mappedBy = "item")
	private List<OrderItem> orderItemList;

	@OneToMany(mappedBy = "item")
	private List<ItemOpt> itemOptList;

	@OneToMany(mappedBy = "item")
	private List<ItemImages> itemImageList;

	@ManyToOne
	@JoinColumn(name = "FkStatus", nullable = false)
	private MetaStatus status;

	@ManyToOne
	@JoinColumn(name = "FkCategory")
	private Category category;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "FkBranch")
	private Branch branch;

	@ManyToOne
	@JoinColumn(name = "FkHq")
	private Hq hq;

	@OneToOne
	@JoinColumn(name = "FkLibraryItem")
	private LibraryItem libraryItem;

	@ManyToOne
	@JoinColumn(name = "CreatedBy")
	private User CreatedBy;

	@Column(name = "CreatedAt")
	private Date created;

	@ManyToOne
	@JoinColumn(name = "UpdatedBy")
	private User UpdatedBy;

	@Column(name = "UpdatedAt")
	private Date updated;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getKeyFeature() {
		return keyFeature;
	}

	public void setKeyFeature(String keyFeature) {
		this.keyFeature = keyFeature;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}


	@Transient
	private Boolean isAvailable;

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Timestamp getDiscountStart() {
		return discountStart;
	}

	public void setDiscountStart(Timestamp discountStart) {
		this.discountStart = discountStart;
	}

	public Timestamp getDiscountEnd() {
		return discountEnd;
	}

	public void setDiscountEnd(Timestamp discountEnd) {
		this.discountEnd = discountEnd;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getMinQuantity() {
		return minQuantity;
	}

	public void setMinQuantity(Long minQuantity) {
		this.minQuantity = minQuantity;
	}

	public Long getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Long maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public Long getExpiredQuantity() {
		return expiredQuantity;
	}

	public void setExpiredQuantity(Long expiredQuantity) {
		this.expiredQuantity = expiredQuantity;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public List<ItemLanguage> getItemLanguageList() {
		return itemLanguageList;
	}

	public void setItemLanguageList(List<ItemLanguage> itemLanguageList) {
		this.itemLanguageList = itemLanguageList;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<ItemOpt> getItemOptList() {
		return itemOptList;
	}

	public void setItemOptList(List<ItemOpt> itemOptList) {
		this.itemOptList = itemOptList;
	}

	public List<ItemImages> getItemImageList() {
		return itemImageList;
	}

	public void setItemImageList(List<ItemImages> itemImageList) {
		this.itemImageList = itemImageList;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Hq getHq() {
		return hq;
	}

	public void setHq(Hq hq) {
		this.hq = hq;
	}

	public LibraryItem getLibraryItem() {
		return libraryItem;
	}

	public void setLibraryItem(LibraryItem libraryItem) {
		this.libraryItem = libraryItem;
	}

	public User getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(User createdBy) {
		CreatedBy = createdBy;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public User getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		UpdatedBy = updatedBy;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public double getNetSalePrice() {
		return netSalePrice;
	}

	public void setNetSalePrice(double netSalePrice) {
		this.netSalePrice = netSalePrice;
	}

	public JSONObject getInfo(MetaLanguage language, MetaStatus status, ItemLanguageRepository itemLanguageRepository,
			SubOptRepository subOptRepository, SubOptLanguageRepository subOptLanguageRepository,
			OptLanguageRepository optLanguageRepository, ItemOptSubOptRepository itemOptSubOptRepository,
			CategoryLanguageRepository categoryLanguageRepository, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository, ItemRepository itemRepository,
			Boolean isFlashSale, FlashSale flashSale, FlashSaleItemRepository flashSaleItemRepository) {
		JSONObject json = new JSONObject();

		//
		ItemLanguage itemLanguage = itemLanguageRepository.findByItemAndLanguage(this, language);
		//
		json.put("category", this.getLibraryItem().getLibraryCategory().getId());

		LibraryCategoryLanguage libraryCategoryLanguage = libraryCategoryLanguageRepository
				.findByLibraryCategoryAndLanguage(this.getLibraryItem().getLibraryCategory(), language);
		json.put("category title", libraryCategoryLanguage.getTitle());
		json.put("id", this.getId());
		// json.put("category", this.getCategory().getInfo(language,
		// categoryLanguageRepository));
		if (itemLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", itemLanguage.getTitle());
		}
		if (itemLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", itemLanguage.getDetails());
		}
		json.put("price", this.getPrice());
		if (this.getQuantity() == null) {
			json.put("quantity", "0");
		} else {
			json.put("quantity", this.getQuantity());
		}
		json.put("discountAmount", this.getDiscountAmount());
		json.put("discountPercentage", this.getDiscountPercentage());
		json.put("branchInfo", this.getBranch().getBasicInfo(language, branchLanguageRepository, itemRepository,
				libraryCategoryLanguageRepository, false));

		if (this.getSku() == null) {
			json.put("sku", "-");
		} else {
			json.put("sku", this.getSku());
		}
		if (this.getBrand() == null) {
			json.put("brand", "-");
		} else {
			json.put("brand", this.getBrand());
		}
		if (this.getKeyFeature() == null) {
			json.put("keyFeatures", "-");
		} else {
			json.put("keyFeatures", this.getKeyFeature());
		}

		json.put("cost", this.getCost());

		// images
		JSONArray jsonImageList = new JSONArray();
		if (this.getLibraryItem() != null) {
			// System.out.println(this.getLibraryItem());
			if (this.getLibraryItem().getItemImageList() != null) {
				for (LibraryItemImages libraryItemImage : this.getLibraryItem().getItemImageList()) {
					jsonImageList.put(libraryItemImage.getInfo());
				}
			}
		}
		json.put("itemImageInfoList", jsonImageList);

		if (isFlashSale) {
			FlashSaleItem flashSaleItem = flashSaleItemRepository.findByFlashSaleAndItem(flashSale, this);
			if (flashSaleItem != null) {
				json.put("FlashSaleDiscountPrice", flashSaleItem.getFlashSalePrice());
			} else
				json.put("FlashSaleDiscountPrice", "0");

		} else
			json.put("FlashSaleDiscountPrice", "0");

		// opt
//		JSONArray itemOptInfoListJson = new JSONArray();
//		for(ItemOpt itemOpt : this.getItemOptList()) {
//			itemOptInfoListJson.put(itemOpt.getOpt().getInfo(language, status, optLanguageRepository, subOptRepository, subOptLanguageRepository, itemOptSubOptRepository, this));
//		}
//		//opt
//		json.put("itemOptInfoList", itemOptInfoListJson);

		// item status for branch
		JSONObject statusInfoJson = new JSONObject();
		statusInfoJson.put("code", "STA003");

		json.put("statusInfo", statusInfoJson);

		return json;
	}

	public JSONObject getInfo(MetaLanguage language, ItemLanguageRepository itemLanguageRepository,
			BranchLanguageRepository branchLanguageRepository, MetaDayLanguageRepository dayLanguageRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository, ItemRepository itemRepository,
			Boolean isFlashSale, FlashSale flashSale, FlashSaleItemRepository flashSaleItemRepository,
			Boolean getBranchInfo) {
		JSONObject json = new JSONObject();

		//
		ItemLanguage itemLanguage = itemLanguageRepository.findByItemAndLanguage(this, language);
		//
		json.put("category", this.getLibraryItem().getLibraryCategory().getId());
		LibraryCategoryLanguage libraryCategoryLanguage = libraryCategoryLanguageRepository
				.findByLibraryCategoryAndLanguage(this.getLibraryItem().getLibraryCategory(), language);
		json.put("category title", libraryCategoryLanguage.getTitle());
		json.put("id", this.getId());

		if (itemLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", itemLanguage.getTitle());
		}
		if (itemLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", itemLanguage.getDetails());
		}
		if (this.getPrice() == 0.0) {
			json.put("price", "- ");
		} else {
			json.put("price", this.getPrice());
		}
		json.put("discountAmount", this.getDiscountAmount());
		json.put("discountPercentage", this.getDiscountPercentage());

		if (this.getQuantity() == null) {
			json.put("quantity", "0");
		} else {
			json.put("quantity", this.getQuantity());
		}

		if (this.getSku() == null) {
			json.put("sku", "-");
		} else {
			json.put("sku", this.getSku());
		}
		if (this.getBrand() == null) {
			json.put("brand", "-");
		} else {
			json.put("brand", this.getBrand());
		}
		if (this.getKeyFeature() == null) {
			json.put("keyFeatures", "-");
		} else {
			json.put("keyFeatures", this.getKeyFeature());
		}
		// json.put("libraryItemID", this.getLibraryItem().getId());
		json.put("cost", this.getCost());
		if (getBranchInfo) {
			json.put("branchInfo", this.getBranch().getBasicInfo(language, branchLanguageRepository, itemRepository,
					libraryCategoryLanguageRepository, false));
		}
		if (isFlashSale) {
			FlashSaleItem flashSaleItem = flashSaleItemRepository.findByFlashSaleAndItem(flashSale, this);
			if (flashSaleItem != null) {
				json.put("FlashSaleDiscountPrice", flashSaleItem.getFlashSalePrice());
			} else
				json.put("FlashSaleDiscountPrice", "0");
		} else
			json.put("FlashSaleDiscountPrice", "0");

		JSONArray jsonImageList = new JSONArray();
		if (this.getLibraryItem() != null) {
			if (this.getLibraryItem().getItemImageList() != null) {
				for (LibraryItemImages libraryItemImage : this.getLibraryItem().getItemImageList()) {
					jsonImageList.put(libraryItemImage.getInfo());
				}
			}
		}
		json.put("itemImageInfoList", jsonImageList);

		return json;
	}

	public JSONObject getPriceInfo(Boolean isFlashSale, FlashSale flashSale,
			FlashSaleItemRepository flashSaleItemRepository) {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("price", this.getPrice());
		if (isFlashSale) {
			FlashSaleItem flashSaleItem = flashSaleItemRepository.findByFlashSaleAndItem(flashSale, this);
			if (flashSaleItem != null) {
				json.put("FlashSaleDiscountPrice", flashSaleItem.getFlashSalePrice());
			} else
				json.put("FlashSaleDiscountPrice", "0");
		} else
			json.put("FlashSaleDiscountPrice", "0");

		return json;
	}

	public JSONObject getIdAndTitle(MetaLanguage language, ItemLanguageRepository itemLanguageRepository) {
		JSONObject json = new JSONObject();
		ItemLanguage itemLanguage = itemLanguageRepository.findByItemAndLanguage(this, language);
		json.put("id", this.getId());
		json.put("title", itemLanguage.getTitle());

		return json;
	}
}
