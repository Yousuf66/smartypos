package ksa.so.beans;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

	 
	  private static final long serialVersionUID = 1L;

	  private final String message;
	  private final HttpStatus httpStatus;
	  private  Throwable cause;
	  private String statusCode;
	  private int customerrorcode;
	  private int customerrordefid;

	  public CustomException(String message, HttpStatus httpStatus, Throwable cause , String code) {
	    this.message = message;
	    this.httpStatus = httpStatus;
	    this.cause = cause;
	    this.statusCode = code;
	  }
		 
	  public CustomException(String message, HttpStatus httpStatus) {
	    this.message = message;
	    this.httpStatus = httpStatus;
	  }
	  
	  public CustomException(String message, HttpStatus httpStatus,int customerrorcode,int customerrordefid) {
		  this.message = message;
		  this.httpStatus = httpStatus;
		  this.customerrorcode =customerrorcode;
		  this.customerrordefid=customerrordefid;
	  }
	  
	  public CustomException(String message, HttpStatus httpStatus,int customerrorcode) {
	    this.message = message;
	    this.httpStatus = httpStatus;
	    this.customerrorcode = customerrorcode;
	  }
		
	  @Override
	  public String getMessage() {
	    return message;
	  }

	  public HttpStatus getHttpStatus() {
	    return httpStatus;
	  }
		  
	  public Throwable getCause() {
	    return cause;
	  }

	  public String toString() {
		return httpStatus.toString();
	  }
	  
	  public int getcustomerrorcode() {
	    return customerrorcode;
	  }
	  
	  public int getcustomerrordefid() {
		  return customerrordefid;
	  }
	  
	  public String getStatusCode() {
    	return statusCode;
	  }
  
}

