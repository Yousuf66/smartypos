package ksa.so.beans;

import ksa.so.domain.Branch;
import ksa.so.domain.BranchLanguage;
import ksa.so.domain.MetaCountry;
import ksa.so.domain.MetaCurrency;
import ksa.so.domain.MetaStatus;
import ksa.so.domain.User;

public class BranchDto {

Branch branch;
BranchLanguage branchLanguage;
MetaStatus status;
MetaCountry metaCountry;
MetaCurrency metaCurrency;
User user;
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public BranchDto(Branch branch, BranchLanguage branchLanguage, MetaStatus status, MetaCountry metaCountry,
		MetaCurrency metaCurrency) {
	super();
	this.branch = branch;
	this.branchLanguage = branchLanguage;
	this.status = status;
	this.metaCountry = metaCountry;
	this.metaCurrency = metaCurrency;
	
}
public BranchDto(Branch branch, BranchLanguage branchLanguage, MetaStatus status, MetaCountry metaCountry,
		MetaCurrency metaCurrency,User user) {
	super();
	this.branch = branch;
	this.branchLanguage = branchLanguage;
	this.status = status;
	this.metaCountry = metaCountry;
	this.metaCurrency = metaCurrency;
	this.user = user;
	
}
public Branch getBranch() {
	return branch;
}
public void setBranch(Branch branch) {
	this.branch = branch;
}
public BranchLanguage getBranchLanguage() {
	return branchLanguage;
}
public void setBranchLanguage(BranchLanguage branchLanguage) {
	this.branchLanguage = branchLanguage;
}
public MetaStatus getStatus() {
	return status;
}
public void setStatus(MetaStatus status) {
	this.status = status;
}
public MetaCountry getMetaCountry() {
	return metaCountry;
}
public void setMetaCountry(MetaCountry metaCountry) {
	this.metaCountry = metaCountry;
}
public MetaCurrency getMetaCurrency() {
	return metaCurrency;
}
public void setMetaCurrency(MetaCurrency metaCurrency) {
	this.metaCurrency = metaCurrency;
}
public BranchDto() {
	super();
}

}
