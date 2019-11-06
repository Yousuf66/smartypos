package ksa.so.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.FlashSaleItemRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryCategoryLanguageRepository;
import ksa.so.repositories.LibraryItemLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;

@Entity
public class ShoppingListItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "price", columnDefinition = "Decimal(10,2) default '0.00'")
	private double price = 0;

	private int quantity = 0;

	@ManyToOne
	@JoinColumn(name = "FkShoppingList", nullable = false)
	private UserShoppingList shoppingList;

	@ManyToOne
	@JoinColumn(name = "FkLibraryItem", nullable = false)
	private LibraryItem item;

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public UserShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(UserShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

	public LibraryItem getLibraryItem() {
		return item;
	}

	public void setLibraryItem(LibraryItem libraryItem) {
		this.item = libraryItem;
	}

	public ShoppingListItem(double price, int quantity, LibraryItem libraryItem, UserShoppingList shoppingList) {

		this.setLibraryItem(libraryItem);
		this.setShoppingList(shoppingList);
//		this.setPrice(price);
		this.setQuantity(quantity);
	}

	public ShoppingListItem() {
		// TODO Auto-generated constructor stub
	}

	public JSONObject getInfo(MetaLanguage language, LibraryItemLanguageRepository libraryItemLanguageRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository) {

		JSONObject json = new JSONObject();

		json.put("item", this.getLibraryItem().getInfo(language, libraryItemLanguageRepository,
				libraryCategoryLanguageRepository));
//		json.put("price", this.getPrice());
		json.put("quantity", this.getQuantity());

		return json;
	}

	public JSONObject getInfo(MetaLanguage language, LibraryItemLanguageRepository libraryItemLanguageRepository,
			Item item, ItemLanguageRepository itemLanguageRepository, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository, ItemRepository itemRepository,
			Boolean isFlashSale, FlashSale flashSale, FlashSaleItemRepository flashSaleItemRepository) {

		JSONObject json = new JSONObject();

		if (item != null)
			json.put("item",
					item.getInfo(language, itemLanguageRepository, branchLanguageRepository, dayLanguageRepository,
							libraryCategoryLanguageRepository, itemRepository, isFlashSale, flashSale,
							flashSaleItemRepository, false));
		else
			json.put("item", "-");

		return json;
	}

	public JSONObject getShoppingListInfo(MetaLanguage language,
			LibraryItemLanguageRepository libraryItemLanguageRepository, Item item,
			ItemLanguageRepository itemLanguageRepository, BranchLanguageRepository branchLanguageRepository,
			MetaDayLanguageRepository dayLanguageRepository,
			LibraryCategoryLanguageRepository libraryCategoryLanguageRepository, ItemRepository itemRepository,
			Boolean isFlashSale, FlashSale flashSale, FlashSaleItemRepository flashSaleItemRepository) {

		JSONObject json = new JSONObject();

		if (item != null)
			json.put("item",
					item.getInfo(language, itemLanguageRepository, branchLanguageRepository, dayLanguageRepository,
							libraryCategoryLanguageRepository, itemRepository, isFlashSale, flashSale,
							flashSaleItemRepository, false));
		else
			json.put("item", "-");

		return json;
	}
}