package ksa.so.beans;

import java.util.List;

import ksa.so.domain.Invoice;

public class InvoicePaging {

	List<Invoice> invoiceList;
	int totalRecords;
	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}
	public InvoicePaging() {
		super();
	}
	public InvoicePaging(List<Invoice> invoiceList, int totalRecords) {
		super();
		this.invoiceList = invoiceList;
		this.totalRecords = totalRecords;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
}
