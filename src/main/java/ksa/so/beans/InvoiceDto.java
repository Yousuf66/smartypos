package ksa.so.beans;

import java.util.List;

import ksa.so.domain.Invoice;
import ksa.so.domain.Item;

public class InvoiceDto {
	List<Item> productsList;
	Invoice invoice;
	Long storeId;
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public List<Item> getProductsList() {
		return productsList;
	}
	public void setProductsList(List<Item> productsList) {
		this.productsList = productsList;
	}
	public InvoiceDto(List<Item> productsList, Invoice invoice, Long storeId) {
		super();
		this.productsList = productsList;
		this.invoice = invoice;
		this.storeId = storeId;
	}
	public InvoiceDto() {
		super();
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
}
