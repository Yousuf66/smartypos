package ksa.so.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.json.JSONObject;

import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.CategoryLanguageRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.MetaDayLanguageRepository;

@Entity
public class SaleInvoice {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String comment;
	
	private String saleInvoiceNo;

	@Column(name="totalPrice", columnDefinition="Decimal(10,2) default '0.00'")
	private double totalPrice = (double)0;
	
	
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date date;
	
	@ManyToOne
    @JoinColumn(name = "createdBy", nullable=true)
	private User createdBy;
	
	private Date createdOn;
	
	@ManyToOne
    @JoinColumn(name = "FkBranch", nullable=false)
	private Branch branch;
	
	@ManyToOne
    @JoinColumn(name = "FkHq", nullable=false)
	private Hq hq;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Hq getHq() {
		return hq;
	}

	public void setHq(Hq hq) {
		this.hq = hq;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	public String getSaleInvoiceNo() {
		return saleInvoiceNo;
	}

	public void setSaleInvoiceNo(String saleInvoiceNo) {
		this.saleInvoiceNo = saleInvoiceNo;
	}
	
	public SaleInvoice() {
		
	}
	
	public SaleInvoice(User createdBy, double price, Date timestamp, Branch branch, Hq hq, String comment, Date createdOn, String saleInvoiceNo) {
		this.setBranch(branch);
		this.setComment(comment);
		this.setCreatedBy(createdBy);
		this.setCreatedOn(createdOn);
		this.setHq(hq);
		this.setDate(timestamp);
		this.setTotalPrice(price);
		this.setSaleInvoiceNo(saleInvoiceNo);
	}
}

