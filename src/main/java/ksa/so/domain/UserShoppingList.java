package ksa.so.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

import ksa.so.repositories.ShoppingListItemRepository;

@Entity
public class UserShoppingList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonProperty
	@Column(columnDefinition = "text")
	private String title = "-";

	@ManyToOne
	@JoinColumn(name = "FkUser", nullable = false)
	private UserApp user;

	@Transient
	private Long totalItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserApp getUserApp() {
		return user;
	}

	public void setUserApp(UserApp user) {
		this.user = user;
	}

	private Timestamp tsClient;
	private Timestamp tsServer;
	private Timestamp tsUpd;

	public Timestamp getTsClient() {
		return tsClient;
	}

	public void setTsClient(Timestamp tsClient) {
		this.tsClient = tsClient;
	}

	public Timestamp getTsServer() {
		return tsServer;
	}

	public void setTsServer(Timestamp tsServer) {
		this.tsServer = tsServer;
	}

	public Timestamp getTsUpd() {
		return tsUpd;
	}

	public void setTsUpd(Timestamp tsUpd) {
		this.tsUpd = tsUpd;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public UserShoppingList(Long id, String title, Long totalItems, Date tsUpd) {
		super();
		this.id = id;
		this.title = title;
		this.totalItems = totalItems;
		this.tsUpd = (Timestamp) tsUpd;
	}

	public UserShoppingList() {
		// TODO Auto-generated constructor stub
	}

	public JSONObject getInfo(ShoppingListItemRepository shoppingListItemRepository) {

		JSONObject json = new JSONObject();

		json.put("id", this.getId());
		json.put("title", this.getTitle());
		json.put("createdTime", this.getTsClient());
		json.put("updatedTime", this.getTsUpd());
		json.put("totalItems", shoppingListItemRepository.getTotalItemsInShoppingList(this.getId()));
//		json.put("branchInfo",  branch.getInfo(language, branchLanguageRepository, dayLanguageRepository, "0.0", "0.0"));

		return json;
	}
}