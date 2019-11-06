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

import org.json.JSONArray;
import org.json.JSONObject;

import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.LibraryItemLanguageRepository;

@Entity
public class LibraryItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String barcode;
	private String brand;
	private String keyFeature;

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

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	private Long price = (long) 0;

	@ManyToOne
	@JoinColumn(name = "FkStatus")
	private MetaStatus status;

	@ManyToOne
	@JoinColumn(name = "FkLibraryCategory")
	private LibraryCategory libraryCategory;

	@OneToMany(mappedBy = "item")
	private List<LibraryItemLanguage> itemLanguageList;

	@OneToMany(mappedBy = "item")
	private List<ShoppingListItem> shoppingListItemList;

	@OneToMany(mappedBy = "libraryItem")
	private List<Item> itemList;

	@OneToMany(mappedBy = "item")
	private List<LibraryItemOpt> itemOptList;

	@OneToMany(mappedBy = "item")
	private List<LibraryItemImages> itemImageList;

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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public LibraryCategory getLibraryCategory() {
		return libraryCategory;
	}

	public void setLibraryCategory(LibraryCategory libraryCategory) {
		this.libraryCategory = libraryCategory;
	}

	public List<LibraryItemLanguage> getItemLanguageList() {
		return itemLanguageList;
	}

	public void setItemLanguageList(List<LibraryItemLanguage> itemLanguageList) {
		this.itemLanguageList = itemLanguageList;
	}

	public List<ShoppingListItem> getShoppingListItemList() {
		return shoppingListItemList;
	}

	public void setShoppingListItemList(List<ShoppingListItem> shoppingListItemList) {
		this.shoppingListItemList = shoppingListItemList;
	}

	public List<LibraryItemOpt> getItemOptList() {
		return itemOptList;
	}

	public void setItemOptList(List<LibraryItemOpt> itemOptList) {
		this.itemOptList = itemOptList;
	}

	public List<LibraryItemImages> getItemImageList() {
		return itemImageList;
	}

	public void setItemImageList(List<LibraryItemImages> itemImageList) {
		this.itemImageList = itemImageList;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public JSONObject getInfo(MetaLanguage language, LibraryItemLanguageRepository libraryItemLanguageRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository) {
		JSONObject json = new JSONObject();

		//
		LibraryItemLanguage libraryItemLanguage = libraryItemLanguageRepository.findByItemAndLanguage(this, language);
		//

		json.put("id", this.getId());
		if (libraryItemLanguage.getTitle() == null) {
			json.put("title", "-");
		} else {
			json.put("title", libraryItemLanguage.getTitle());
		}
		if (libraryItemLanguage.getDetails() == null) {
			json.put("details", "-");
		} else {
			json.put("details", libraryItemLanguage.getDetails());
		}
		if (this.getPrice() == null) {
			json.put("price", "- ");
		} else {
			json.put("price", this.getPrice());
		}

		if (this.getBrand() == null) {
			json.put("brand", "- ");
		} else {
			json.put("brand", this.getBrand());
		}
		if (this.getKeyFeature() == null) {
			json.put("keyFeature", "- ");
		} else {
			json.put("keyFeature", this.getKeyFeature());
		}
		// images
		JSONArray jsonImageList = new JSONArray();
		if (this.getItemImageList() != null) {
			for (LibraryItemImages libraryItemImage : this.getItemImageList()) {
				jsonImageList.put(libraryItemImage.getInfo());
			}
		}
		json.put("itemImageInfoList", jsonImageList);
		if (this.getLibraryCategory() != null) {
			json.put("category", this.getLibraryCategory().getInfo(language, libraryCategoryLanguageRepository));
		} else
			json.put("category", "-");

		return json;
	}

	public JSONObject getIdAndTitle(MetaLanguage language,
			LibraryItemLanguageRepository libraryItemLanguageRepository) {
		JSONObject json = new JSONObject();
		LibraryItemLanguage itemLanguage = libraryItemLanguageRepository.findByItemAndLanguage(this, language);
		json.put("id", this.getId());
		json.put("title", itemLanguage.getTitle());

		return json;
	}

}
