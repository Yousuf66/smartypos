package ksa.so.sms;

import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.io.OutputStreamWriter; 
import java.net.URL; 
import java.net.URLConnection; 
import java.net.URLEncoder; 

public class SMSSender {
	 public static String sendSms(String sToPhoneNo,String sMessage) { 
		 try { 
			 // Construct data 
//			 String data = "id=" + URLEncoder.encode("92300xxxxxxx", "UTF-8");
//			 data += "&pass=" + URLEncoder.encode("xxxxxxxxxxxx", "UTF-8"); 
			 String data = "id=" + URLEncoder.encode("test1", "UTF-8"); 
			 data += "&pass=" + URLEncoder.encode("test1234", "UTF-8");
			 data += "&msg=" + URLEncoder.encode("Your verification code for Smarty signup is: "+sMessage, "UTF-8"); 
			 data += "&lang=" + URLEncoder.encode("English", "UTF-8"); 
			 data += "&to=" + URLEncoder.encode(sToPhoneNo, "UTF-8"); 
			 data += "&mask=" + URLEncoder.encode("Outreach", "UTF-8"); 
			 data += "&type=" + URLEncoder.encode("xml", "UTF-8"); 
			 // Send data 
			 URL url = new URL("http://www.outreach.pk/api/sendsms.php/sendsms/url"); 
			 URLConnection conn = url.openConnection(); 
			 conn.setDoOutput(true); 
			 OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			 wr.write(data); 
			 wr.flush(); 
			 // Get the response 
			 BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
			 String line; 
			 String sResult=""; 
			 while ((line = rd.readLine()) != null) { 
				 // Process line... 
				 sResult=sResult+line+" "; 
			 } 
			 wr.close(); 
			 rd.close(); 
			 return sResult; 
			 } catch (Exception e) { 
				 System.out.println("Error SMS "+e); 
				 return "Error "+e; 
			 	} 
			 } 
	 
//			 public static void main(String[] args) { 
//				 String result = sendSms("92300xxxxxxx","Hello this is a test with a 5 note and an ampersand (&) symbol"); 
//				 System.out.println(result); 
//		 	} 
} 
