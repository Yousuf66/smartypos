package ksa.so.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ksa.so.domain.Menu;
import ksa.so.domain.MenuRights;
//import ksa.so.domain.Module;
//import ksa.so.domain.ModuleRights;



public interface MenuRepository extends JpaRepository<Menu, Long>{

	@Query("select mr from Menu m, MenuRights mr, UserProfiles ur "
			+ " WHERE m.id = mr.menu.id "
			+ " AND ur.metaUserType.id = mr.profile.id "
			+ " AND ur.userId.id = ?1 order by Sequence asc ")
	public  List<MenuRights> searchByempId(Long userId);  
	
//	@Query("SELECT m.menu.id from TablesLinkMenu m "
//			+ "	INNER JOIN Tables tab ON m.tables = tab "
//			+ " WHERE  UPPER(TRIM(m.tables.tableName)) = ?1")
	
	@Query("select m.menu.id from TablesLinkMenu m, Tables tab where UPPER(TRIM(m.tables.tableName)) = ?1")
	Long getMenuIdByTableName(String tableName);
	

//	
//	@Query("select m.menu.id from TablesLinkMenu m "
//			+ "	inner join Tables tab ON m.tables = tab "
//			+ " where  UPPER(TRIM(m.tables.tableName)) = ?1")
//	Integer getMenuIdByTableName(String tableName);

	public Optional<Menu> findById(Long menuId);

}
