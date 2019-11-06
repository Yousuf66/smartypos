package ksa.so.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

@Entity
public class BranchCoupon {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;

@ManyToOne
@JoinColumn(name = "FkBranch", nullable=false)
private Branch branch;

@ManyToOne
@JoinColumn(name = "FkCoupon", nullable=false)
private DiscountCoupon discountCoupon;

public Branch getBranch() {
return branch;
}

public void setBranch(Branch branch) {
this.branch = branch;
}

public Long getId() {
return id;
}

public void setId(Long id) {
this.id = id;
}

public DiscountCoupon getDiscountCoupon() {
return discountCoupon;
}

public void setCoupon(DiscountCoupon discountCoupon) {
this.discountCoupon = discountCoupon;
}

public JSONObject getInfo() {
JSONObject json = new JSONObject();

return json;
}

public BranchCoupon() {

}
}