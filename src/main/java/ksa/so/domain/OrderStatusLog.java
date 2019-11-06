package ksa.so.domain;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.json.JSONObject;

import ksa.so.repositories.MetaStatusLanguageRepository;

@Entity
public class OrderStatusLog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Timestamp tsClient;
	
	private Timestamp tsServer;
	
	@OneToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
	
	@OneToOne
    @JoinColumn(name = "FkOrder", nullable=false)
	private CustomerOrder order;
	
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
	
	public MetaStatus getStatus() {
		return status;
	}

	public void setStatus(MetaStatus status) {
		this.status = status;
	}

	public CustomerOrder getOrder() {
		return order;
	}

	public void setOrder(CustomerOrder order) {
		this.order = order;
	}
	
	public JSONObject getInfo(MetaLanguage language, MetaStatusLanguageRepository statusLanguageRepository) {
		
		JSONObject json = new JSONObject();
		json.put("time", this.getTsClient());
		json.put("status", this.getStatus().getInfo(language, statusLanguageRepository));
		
		return json;
	}
}
