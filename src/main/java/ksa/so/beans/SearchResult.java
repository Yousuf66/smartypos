package ksa.so.beans;

import java.util.List;

public class SearchResult {
	private List<ItemBasic> items;
	private List<ItemResponse> itemsResponse;
	private String tag;

	public List<ItemBasic> getItems() {
		return items;
	}

	public void setItems(List<ItemBasic> items) {
		this.items = items;
	}

	public void setItemResponse(List<ItemResponse> items) {
		this.itemsResponse = items;
	}

	public List<ItemResponse> getItemsResponse() {
		return itemsResponse;
	}

	public void setItemsResponse(List<ItemResponse> itemsResponse) {
		this.itemsResponse = itemsResponse;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
