package ksa.so.service;


import java.math.BigDecimal;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
//import org.hibernate.annotations.common.util.impl.Log_.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ibm.icu.text.SimpleDateFormat;

import ksa.so.repositories.SalesReportRepository;;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private SalesReportRepository reportRepository;
	

	@Override
	public List<Map<String, Object>> report() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Object[]> userList = reportRepository.findUser();
		
		List<Object[]> usersList = reportRepository.ClientServerTime();
		for(int i=0; i<userList.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", (Long)userList.get(i)[0]);
			item.put("name", (String)userList.get(i)[1]);
			item.put("price", (Long)userList.get(i)[2]);
			result.add(item);
		}
		return result;
	
	}
	
	@Override
	public List<Map<String, Object>> solditems(@RequestBody String param) throws ParseException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		JSONObject jsonParam = new JSONObject(param);
		JSONObject jsonData = jsonParam.getJSONObject("data");
		int lang = jsonData.getInt("lang");
		String scode = jsonData.getString("scode");
		int lim = jsonData.getInt("lim");
		String timeperiod= jsonData.getString("timeperiod");
		Date sdate = null;
		Date edate = null;
		if(timeperiod.equals("C")) {
		String sdatestring= jsonData.getString("sdate");
		String edatestring= jsonData.getString("edate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdate = sdf.parse(sdatestring);
		edate = sdf.parse(edatestring);}
		List<Object[]> userList = reportRepository.topsolditems(lang, scode, lim,timeperiod,sdate,edate);
		for(int i=0; i<userList.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", (BigInteger)userList.get(i)[0]);
			item.put("name", (String)userList.get(i)[2]);
			item.put("price", (BigDecimal)userList.get(i)[3]);
			result.add(item);
		}
		return result;
	
	}

	@Override
	public List<Map<String, Object>> topSoldItemByCategory(@RequestBody String param) throws ParseException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		JSONObject jsonParam = new JSONObject(param);
		JSONObject jsonData = jsonParam.getJSONObject("data");
		String scode = jsonData.getString("scode");
		int limit = jsonData.getInt("limit");
		int language = jsonData.getInt("language");
		int category = jsonData.getInt("category");
		String codecase= jsonData.getString("codecase");
		Date sdate = null;
		Date edate = null;
		if(codecase.equals("C")) {
		String sdatestring= jsonData.getString("sdate");
		String edatestring= jsonData.getString("edate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdate = sdf.parse(sdatestring);
		edate = sdf.parse(edatestring);}
		List<Object[]> userList = reportRepository.topmostcategory(language,category,limit,scode,codecase,sdate,edate);
		for(int i=0; i<userList.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("fk_item", (BigInteger)userList.get(i)[0]);
			item.put("occurance", (BigDecimal)userList.get(i)[1]);
			item.put("catname", (String)userList.get(i)[2]);
			item.put("itemname", (String)userList.get(i)[3]);
			result.add(item);
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> reportStacked(@RequestBody String param) throws ParseException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		BigDecimal  minValue ;
		JSONObject jsonParam = new JSONObject(param);
		JSONObject jsonData = jsonParam.getJSONObject("data");
		int times= jsonData.getInt("times");
		int limit= jsonData.getInt("limit");
		String codecase= jsonData.getString("codecase");
		Date sdate = null;
		Date edate = null;
		if(codecase.equals("C")) {
		String sdatestring= jsonData.getString("sdate");
		String edatestring= jsonData.getString("edate");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdate = sdf.parse(sdatestring);
		edate = sdf.parse(edatestring);}
		String labely;
		if(times == 60) labely= "TIME IN MINUTES";
		else labely="TIME IN HOURS";
		List<Object[]> userList = reportRepository.stackedGraph(times,limit,codecase,sdate,edate);
		if(!userList.isEmpty()) {
		  minValue = (BigDecimal)userList.get(0)[4];
		  for(int j=0; j<userList.size(); j++) {
			  BigDecimal abc = (BigDecimal)userList.get(j)[4];
		    if(abc.compareTo(minValue) > 0){
			  minValue = abc;
			}
		  }
		  
		for(int i=0; i<userList.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("fk_order", (BigInteger)userList.get(i)[0]);
			item.put("A", (BigDecimal)userList.get(i)[1]);
			item.put("B", (BigDecimal)userList.get(i)[2]);
			item.put("C", (BigDecimal)userList.get(i)[3]);
			item.put("D", (BigDecimal)userList.get(i)[4]);
			item.put("E", minValue );
			item.put("F", labely );
			result.add(item);
		}
		}
		return result;
	}
}