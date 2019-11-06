package ksa.so.service;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.google.gson.Gson;

import ksa.so.domain.AuditTable;
import ksa.so.domain.User;
import ksa.so.repositories.AuditTableRepository;
import ksa.so.repositories.MenuRepository;

//import systemx.api.model.config.Users;
//import systemx.api.model.logs.AuditTable;
//import systemx.api.model.repositories.config.AuditTableRepository;
//import systemx.api.model.repositories.config.MenuRepository;

@Service
public class AuditLog {
	@Autowired
	private AuditTableRepository AuditRepository;
	
	@Autowired
	private EntityManager entityManager;
	

	@Autowired
	private MenuRepository menuRepository;
	
	public void createAuditEntry(Long usrID,char action,String ipAddress,Long menuId, Object oldValue,Object newValue) {
		try {
		AuditTable objAudit = new AuditTable();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Jdk8Module());
		Integer recordid=null;
		
		if(oldValue!=null) {
		JSONObject mJSONObject = new JSONObject(mapper.writeValueAsString(oldValue));
		recordid=mJSONObject.getInt("id");
		}
		else if(newValue!=null)
		{
		JSONObject mJSONObject = new JSONObject(mapper.writeValueAsString(newValue));
		recordid=mJSONObject.getInt("id");
		}

		objAudit.setRecordId(recordid);
		objAudit.setUpdatedBy(entityManager.getReference(User.class, usrID));
		objAudit.setAction(action);
		objAudit.setIpAddress(ipAddress);
		objAudit.setDateOn(new Date());
		objAudit.setMenu(menuRepository.findById(menuId).orElse(null));
		objAudit.setOldValue(mapper.writeValueAsString(oldValue));
		objAudit.setNewValue(mapper.writeValueAsString(newValue));
		
		
		AuditRepository.save(objAudit);
		}catch(Exception ex){
		
		}
		
		
	}
	
	
}
