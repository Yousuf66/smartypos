package ksa.so.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class MediaIcons {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileName = "-";
	@JsonProperty
	@Column(columnDefinition = "TEXT")
	private String fileUrl = "-";
	
	@ManyToOne
	@JoinColumn(name="CreatedBy")
	private User CreatedBy;

	@Column(name="CreatedAt")
	private Date created;
	
	@ManyToOne
	@JoinColumn(name="UpdatedBy")
	private User UpdatedBy;

	@Column(name="UpdatedAt")
	private Date updated;
	
	public User getCreatedBy() {
		return CreatedBy;
		}

		public void setCreatedBy(User createdBy) {
		CreatedBy = createdBy;
		}

		public User getUpdatedBy() {
		return UpdatedBy;
		}

		public void setUpdatedBy(User updatedBy) {
		UpdatedBy = updatedBy;
		}

		public Date getCreatedAt() {
		return created;
		}

		public void setCreatedAt(Date created) {
		this.created = created;
		}

		public Date getUpdatedAt() {
		return updated;
		}

		public void setUpdatedAt(Date updated) {
		this.updated = updated;
		}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
