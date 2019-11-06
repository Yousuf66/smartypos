
package ksa.so.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class LibraryItemKeywords {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "FkLibraryItem")
	private LibraryItem libraryItem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LibraryItem getLibraryItem() {
		return libraryItem;
	}

	public void setLibraryItem(LibraryItem libraryItem) {
		this.libraryItem = libraryItem;
	}

	public MetaKeyword getKeyword() {
		return keyword;
	}

	public void setKeyword(MetaKeyword keyword) {
		this.keyword = keyword;
	}

	@OneToOne
	@JoinColumn(name = "FkKeyword")
	private MetaKeyword keyword;
}
