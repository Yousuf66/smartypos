package ksa.so.beans;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class UtilityFunctions {
	

	
    public static class Constants {
    	public static final String NVARCHAR_MAX = "nvarchar(max)";
    }

	public boolean convertToBoolean(String value) {
	    boolean returnValue = false;
	    if ("1".equalsIgnoreCase(value) || "yes".equalsIgnoreCase(value) || 
	        "true".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value))
	        returnValue = true;
	    return returnValue;
	}
	

}
