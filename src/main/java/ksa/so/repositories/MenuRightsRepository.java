package ksa.so.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.Menu;
import ksa.so.domain.MenuRights;


public interface MenuRightsRepository  extends JpaRepository<MenuRights, Long>{

//	void save(MenuRights menuRights);
//	List<MenuRights> findByProfileId(Profiles userProfile);
//
//@Query(value = "INSERT INTO config_menu_rights (`viewrights`, `menu_id`, `profile_id`) VALUES (TRUE, 2, ?1)", nativeQuery =true)	
//void setBranchUserMenuRights(Long id);
//	
}
