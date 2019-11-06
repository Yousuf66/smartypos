package ksa.so.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ksa.so.domain.LibraryItem;
import ksa.so.domain.LibraryItemImages;

public interface LibraryItemImagesRepository extends JpaRepository<LibraryItemImages, Long> {

	LibraryItemImages findByItem(LibraryItem libraryItem);

}
