package ksa.so.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ksa.so.domain.MetaAppType;
import ksa.so.domain.MetaCountry;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaMessage;
import ksa.so.domain.MetaStatus;
import ksa.so.repositories.AppVersionRepository;
import ksa.so.repositories.MetaAppTypeRepository;
import ksa.so.repositories.MetaCountryRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaMessageLanguageRepository;
import ksa.so.repositories.MetaMessageRepository;
import ksa.so.repositories.MetaStatusRepository;

@RestController
@RequestMapping("/api/common")
public class CommonController {
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	AppVersionRepository appVersionRepository;
	
	@Autowired
	MetaAppTypeRepository appTypeRepository;
	
	@Autowired
	MetaStatusRepository statusRepository;
	
	@Autowired
	MetaLanguageRepository languageRepository;
	
	@Autowired
	MetaMessageRepository messageRepository;
	
	@Autowired
	MetaMessageLanguageRepository messageLanguageRepository;
	
	@Autowired
	MetaCountryRepository countryRepository;

	@Value("${was.version}")
	private String owasVersion;

	@Value("${was.commit}")
	private String commit;
	
	@Value("${was.messageServer}")
	private String messageServer;
	
	/**
	 * command startup
	 */
	@RequestMapping(value = "/srs_com_001", method = RequestMethod.POST, consumes = "text/plain", produces={"application/json; charset=UTF-8"})
	public String startup(@RequestBody String param, HttpServletRequest req) throws Exception {
		try {
			//incoming params to json
			JSONObject jsonParam = new JSONObject(param);
			//jsonParam.getJSONObject("data");
			JSONObject jsonData = jsonParam.getJSONObject("data");
			// jsonData.getJSONObject("appVersionInfo");
			JSONObject jsonAppVersionInfo = jsonData.getJSONObject("appVersionInfo");
			//jsonAppVersionInfo.getString("version");
			//jsonAppVersionInfo.getString("appTypeInfo");
			JSONObject jsonAppTypeInfo = jsonAppVersionInfo.getJSONObject("appTypeInfo");
			//jsonAppTypeInfo.getString("code");
			
			//country info list
			List<MetaCountry> countryList = countryRepository.findAll();
			JSONArray jsonCountryList = new JSONArray();
			for(MetaCountry country : countryList) {
				jsonCountryList.put(country.getInfo());
			}
			
			//app type
			MetaAppType appType = appTypeRepository.findByCode(jsonAppTypeInfo.getString("code"));
			
			//status enabled
			MetaStatus status = statusRepository.findByCode("STA003");
			
			//latest app version info
			jsonAppVersionInfo = new JSONObject();
			jsonAppVersionInfo = appVersionRepository.findByAppTypeAndStatus(appType, status).get(0).getInfo();
			
			MetaMessage msg = messageRepository.findByCode("MSG001");
			MetaLanguage language = languageRepository.findByCode("LAN001");
			
	        JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "ack");
			message.put("value", messageLanguageRepository.findByMessageAndLanguage(msg, language).getTitle());
			data.put("message", message);
			data.put("countryInfoList", jsonCountryList);
			data.put("appVersionInfo", jsonAppVersionInfo);
			json.put("data", data);
			json.put("key", "srs_com_001");
			
			return json.toString();
			
		} catch (Exception e) {
			log.warn("/api/common/srs_com_001 - startup - : {}", 
					e.getMessage());
			
			JSONObject json = new JSONObject();
			JSONObject data = new JSONObject();
			JSONObject message = new JSONObject();
			message.put("type", "error");
			message.put("value", e.getMessage());
			data.put("message", message);
			json.put("data", data);
			json.put("key", "srs_com_001");
			//add server timestamp
			json.put("Server Timestamp", statusRepository.getCurrentTime());		
		
			return json.toString();
		}
	}
}