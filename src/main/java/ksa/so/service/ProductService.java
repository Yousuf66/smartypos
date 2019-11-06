package ksa.so.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;

public interface ProductService {

	public List<Map<String, Object>> report();

	public List<Map<String, Object>> solditems(@RequestBody String param) throws ParseException;

	public List<Map<String, Object>> topSoldItemByCategory(@RequestBody String param) throws ParseException;
	
	public List<Map<String, Object>> reportStacked(@RequestBody String param) throws ParseException;


}