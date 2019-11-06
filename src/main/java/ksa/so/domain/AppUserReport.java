package ksa.so.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class AppUserReport {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(columnDefinition = "text")
	private String details = "-";
	
	@ManyToOne
    @JoinColumn(name = "FkStatus", nullable=false)
	private MetaStatus status;
	
	@ManyToOne
    @JoinColumn(name = "FkUser", nullable=false)
	private UserApp user;
	
	@ManyToOne
    @JoinColumn(name = "FkOperator", nullable=false)
	private User operator;
	
	@ManyToOne
    @JoinColumn(name = "FkMessage", nullable=false)
	private MetaMessage message;
	
}
