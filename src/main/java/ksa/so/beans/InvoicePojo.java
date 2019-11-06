package ksa.so.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ksa.so.domain.Branch;
import ksa.so.domain.InvoiceItem;


public class InvoicePojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonIgnore
	@ManyToOne
	private Branch branch;
	private String billNumber;
	private String remarks;
	@Column(name = "bill_date", columnDefinition="TIMESTAMP(6)")
	@Temporal(TemporalType.TIMESTAMP)
	private Date billDate;
	
	private Long totalItems;
	private Double totalAmount;
	private Double totalDiscount;
	private Double billDiscount;
	private Double netAmount;
	private Double paidAmount;
	private Double amountReturned;
	private String referenceNumber;

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Branch getBranch() {
		return branch;
	}

	public InvoicePojo(Long id, Branch branch, String billNumber, String remarks, Date billDate, Long totalItems,
			Double totalAmount, Double totalDiscount, Double billDiscount, Double netAmount, Double paidAmount,
			Double amountReturned, List<InvoiceItem> listOfInvoiceItems,String referenceNumber) {
		super();
		this.id = id;
		this.branch = branch;
		this.billNumber = billNumber;
		this.remarks = remarks;
		this.billDate = billDate;
		this.totalItems = totalItems;
		this.totalAmount = totalAmount;
		this.totalDiscount = totalDiscount;
		this.billDiscount = billDiscount;
		this.netAmount = netAmount;
		this.paidAmount = paidAmount;
		this.amountReturned = amountReturned;
		this.listOfInvoiceItems = listOfInvoiceItems;
		this.referenceNumber = referenceNumber;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	
	

	@ManyToMany
	private List<InvoiceItem> listOfInvoiceItems;
	
	public InvoicePojo() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public InvoicePojo(Long id, String billNumber, String remarks, Date billDate, Long totalItems, Double totalAmount,
			Double totalDiscount, Double billDiscount, Double netAmount, Double paidAmount, Double amountReturned,
			List<InvoiceItem> listOfInvoiceItems) {
		super();
		this.id = id;
		this.billNumber = billNumber;
		this.remarks = remarks;
		this.billDate = billDate;
		this.totalItems = totalItems;
		this.totalAmount = totalAmount;
		this.totalDiscount = totalDiscount;
		this.billDiscount = billDiscount;
		this.netAmount = netAmount;
		this.paidAmount = paidAmount;
		this.amountReturned = amountReturned;
		this.listOfInvoiceItems = listOfInvoiceItems;
	}

	public Double getAmountReturned() {
		return amountReturned;
	}

	public void setAmountReturned(Double amountReturned) {
		this.amountReturned = amountReturned;
	}



	public List<InvoiceItem> getListOfInvoiceItems() {
		return listOfInvoiceItems;
	}

	public void setListOfInvoiceItems(List<InvoiceItem> listOfInvoiceItems) {
		this.listOfInvoiceItems = listOfInvoiceItems;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Long getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Double getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(Double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	public Double getBillDiscount() {
		return billDiscount;
	}
	public void setBillDiscount(Double billDiscount) {
		this.billDiscount = billDiscount;
	}
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	public Double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}
	
	
	
	
	
	
	
}
