package ksa.so.beans;

import java.util.List;

import ksa.so.domain.Invoice;

public class InvoiceItemPojo {

	Invoice invoice;
	List<ItemBasic> item;
	
	
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public List<ItemBasic> getItem() {
		return item;
	}
	public void setItem(List<ItemBasic> item) {
		this.item = item;
	}
	public InvoiceItemPojo(Invoice invoice, List<ItemBasic> item) {
		super();
		this.invoice = invoice;
		this.item = item;
	}
	

}
