package ksa.so.beans;

import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;

public class StatusLanguage {

@Override
	public String toString() {
		return "StatusLanguage [metaLanguage=" + metaLanguage + ", metaStatus=" + metaStatus + "]";
	}
	//	StatusLanguage statusLanguage;
//	public StatusLanguage getstatusLanguage() {
//		return statusLanguage;
//	}
//	public void setstatusLanguage(StatusLanguage statusLanguage) {
//		this.statusLanguage = statusLanguage;
//	}
	MetaLanguage metaLanguage;
	MetaStatus metaStatus;
	public MetaLanguage getMetaLanguage() {
		return metaLanguage;
	}
	public void setMetaLanguage(MetaLanguage metaLanguage) {
		this.metaLanguage = metaLanguage;
	}
	public MetaStatus getMetaStatus() {
		return metaStatus;
	}
	public void setMetaStatus(MetaStatus metaStatus) {
		this.metaStatus = metaStatus;
	}
	
}
