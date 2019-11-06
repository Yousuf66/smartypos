
package ksa.so.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ksa.so.domain.Category;
import ksa.so.domain.Item;
import ksa.so.domain.LibraryCategory;
import ksa.so.domain.MetaLanguage;
import ksa.so.beans.CategoryResponse;
import ksa.so.domain.Branch;
import ksa.so.domain.MetaStatus;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	List<Category> findByStatus(MetaStatus status);

	List<Category> findByBranchAndStatus(Branch branch, MetaStatus status);

	@Query("SELECT c FROM LibraryCategory c "
			+ "INNER JOIN c.libraryCategoryLanguageList cl "
			+ "INNER JOIN c.status s "
			+ "WHERE s.id=:statusID AND "
			+ "cl.language=:language order by cl.title")
	List<LibraryCategory> findByStatusOrderByTitle(Long id, MetaLanguage language);
}
