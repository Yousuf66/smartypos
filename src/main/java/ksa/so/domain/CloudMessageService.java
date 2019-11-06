package ksa.so.domain;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.windowsazure.messaging.Notification;
import com.windowsazure.messaging.NotificationHub;
import com.windowsazure.messaging.NotificationHubsException;
import com.windowsazure.messaging.NotificationOutcome;
import com.windowsazure.messaging.NotificationStatus;
import com.windowsazure.messaging.NotificationTelemetry;

import ksa.so.service.AndroidPushNotificationsService;

import org.springframework.http.ResponseEntity;

public class CloudMessageService {
	@Autowired
	AndroidPushNotificationsService androidPushNotificationsService;
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	private static final String FIREBASE_API_URL_NOTIFICATION = "https://fcm.googleapis.com/fcm/notification";
    //user fcm
	private final String SENDER_ID_USER = "363417436233";
    private final String FIREBASE_SERVER_KEY = "AAAAVJ1eAEk:APA91bFowQw3UJ8lSUf5sjkjgRNK1GYBLvzXf6672o-OuBn9vBX5bWX2kd_8p_W6PjoUOp2x_4hQsSoF5oxiw_zWKxhQAfcduHuZ1lRrzLjXLHW1gGLaWFLXfmRm2PkDFUDI2gT9VzJ3";
    //operator fcm
    private final String SENDER_ID_OPERATOR = "211608049659";
    private final String FIREBASE_SERVER_KEY_OPERATOR = "AAAAMUTSl_s:APA91bGTnQi0cDqHfxqTPDYH0bCh-Fvnobq9qGXFrnDHf26RNDD1qttjcHY6w-pz6IGU_gt-sXo7jfmD5Yq1J1Gk940L_CCtZemKsSNdP_Uj-zgFyKmzCaS25xTZMiob245S9sKCZPK0"; 
    
	private static final Logger log = LoggerFactory.getLogger(CloudMessageService.class);
			
	public String sendMessage(String messageContent, JSONObject json, String installationId, String receiverType, String apiKey) throws NotificationHubsException{		
		try{
			String hubName = "SwiftOrderNotificationHub";
	
			String connString = "Endpoint=sb://swiftordernotificationhubns.servicebus.windows.net/;SharedAccessKeyName=DefaultFullSharedAccessSignature;SharedAccessKey=aBaH2KTwcykBGUi9xDe7QqCZ/x94f4pzKQnii4vaqLI=";

			if(receiverType.equals("operator")) {
				hubName = "SwiftOrderOperatorNotificationHub";
				connString = "Endpoint=sb://swiftordernotificationhubns.servicebus.windows.net/;SharedAccessKeyName=DefaultFullSharedAccessSignature;SharedAccessKey=igq3+Tw+DYBvdXqNCywNHDAAxpY7tQ/r0xQkadtTyV4=";
			} 
			
			NotificationHub hub = new NotificationHub(connString, hubName);
			Map<String, String> templateParams = new HashMap<String, String>();
			
			String data  = json.toString();
			data = data.replace('\"', '\'');
			
			templateParams.put("messageData", data);
			templateParams.put("messageParam", messageContent);
			templateParams.put("apiKey", apiKey);
									
			Notification n = Notification.createTemplateNotification(templateParams);
						
			NotificationOutcome outcome = hub.sendNotification(n, "$InstallationId:{"+ installationId +"}");
			
			System.out.println("Tracking id:" + outcome.getTrackingId());
			
			NotificationTelemetry telemetry = hub.getNotificationTelemetry(outcome.getNotificationId());
			
			NotificationStatus status = telemetry.getNotificationStatus();	
			
			return status.name();
		} catch (Exception ex) {
			return "error";
		}
	}

	public void sendMessage(String deviceToken, JSONObject json, JSONObject notification, boolean forOperator) {

		try {
			   RestTemplate restTemplate = new RestTemplate();
			   MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<String, String>();  
			   
			   if(forOperator) {
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY_OPERATOR);
			   }
			   else {
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
			   }
			   httpHeaders.set("Content-Type", "application/json");
			   	JSONObject body = new JSONObject();
				body.put("to", deviceToken);
				body.put("priority", "high");

				body.put("notification", notification);
				body.put("data", json);

			   HttpEntity<String> httpEntity = new HttpEntity<String>(body.toString(),httpHeaders);
			   String response = restTemplate.postForObject(FIREBASE_API_URL,httpEntity,String.class);
			   System.out.println("1");
			   System.out.println(response);
			
			} catch (JSONException e) {
				e.printStackTrace();
		}
	}

	
	public void sendConfirmationMessage(String deviceToken, JSONObject notification) {

		try {
			   RestTemplate restTemplate = new RestTemplate();
			   MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<String, String>();  
			   
			 
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
			   
			   httpHeaders.set("Content-Type", "application/json");
			   	JSONObject body = new JSONObject();
				body.put("to", deviceToken);
				body.put("priority", "high");

				body.put("notification", notification);
				

			   HttpEntity<String> httpEntity = new HttpEntity<String>(body.toString(),httpHeaders);
			   String response = restTemplate.postForObject(FIREBASE_API_URL,httpEntity,String.class);
			   System.out.println("1");
			   System.out.println(response);
			
			} catch (JSONException e) {
				e.printStackTrace();
		}
	}

	public String createDeviceGroup(String notification_key_name, String[] registration_ids, boolean forOperator) {

		try {
			   RestTemplate restTemplate = new RestTemplate();
			   MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<String, String>();
			   
			   if(forOperator) {
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY_OPERATOR);
			   }
			   else {
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
			   }
			   
			   httpHeaders.set("Content-Type", "application/json");
			   
			   if(forOperator) {
				   httpHeaders.set("project_id", SENDER_ID_OPERATOR);
			   }
			   else {
				   httpHeaders.set("project_id", SENDER_ID_USER);
			   }
			   
		   		JSONObject body = new JSONObject();
				body.put("operation", "create");
				body.put("notification_key_name", notification_key_name);
				body.put("registration_ids", registration_ids);

			   HttpEntity<String> httpEntity = new HttpEntity<String>(body.toString(),httpHeaders);
			   String responseString = restTemplate.postForObject(FIREBASE_API_URL_NOTIFICATION,httpEntity,String.class);
				
			   System.out.println(responseString);
			   
			   JSONObject response = new JSONObject(responseString);
			   return response.getString("notification_key");

		} catch (Exception e) {
		   e.printStackTrace();
		   return null;
		}
	}

	public String retrieveDeviceGroup(String notification_key_name, boolean forOperator) {

		try {
			   RestTemplate restTemplate = new RestTemplate();
			   MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<String, String>();
			   
			   if(forOperator) {
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY_OPERATOR);
			   }
			   else {
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
			   }
			   
			   httpHeaders.set("Content-Type", "application/json");
			   
			   if(forOperator) {
				   httpHeaders.set("project_id", SENDER_ID_OPERATOR);
			   }
			   else {
				   httpHeaders.set("project_id", SENDER_ID_USER);
			   }
			   
		   	   //JSONObject body = new JSONObject();

			   HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
			   ResponseEntity<String> responseString = restTemplate.exchange(
					   FIREBASE_API_URL_NOTIFICATION+"?notification_key_name="+notification_key_name, HttpMethod.GET, httpEntity, String.class);
//			   String responseString = restTemplate.getForObject(FIREBASE_API_URL_NOTIFICATION+"?notification_key_name="+notification_key_name,httpEntity,String.class);
			   System.out.println(responseString);
			   
			   JSONObject response = new JSONObject(responseString);
			   String body = response.getString("body");
			   JSONObject jsonObj = new JSONObject(body); 
			   
			   return jsonObj.getString("notification_key");

		} catch (JSONException e) {
		   e.printStackTrace();
		   return null;
		}
	}

	public String addDeviceToGroup(String notification_key_name, String notification_key, String[] registration_ids, boolean forOperator) {

		try {
			   RestTemplate restTemplate = new RestTemplate();
			   MultiValueMap<String, String> httpHeaders = new LinkedMultiValueMap<String, String>();
			   
			   if(forOperator) {
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY_OPERATOR);
			   }
			   else {
				   httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
			   }
			   
			   httpHeaders.set("Content-Type", "application/json");
			   
			   if(forOperator) {
				   httpHeaders.set("project_id", SENDER_ID_OPERATOR);
			   }
			   else {
				   httpHeaders.set("project_id", SENDER_ID_USER);
			   }
			   
		   		JSONObject body = new JSONObject();
				body.put("operation", "add");
				body.put("notification_key_name", notification_key_name);
				body.put("notification_key", notification_key);
				body.put("registration_ids", registration_ids);

			   HttpEntity<String> httpEntity = new HttpEntity<String>(body.toString(),httpHeaders);
			   String responseString = restTemplate.postForObject(FIREBASE_API_URL_NOTIFICATION,httpEntity,String.class);
			   System.out.println(responseString);
			   
			   JSONObject response = new JSONObject(responseString);
			   return response.getString("notification_key");

		} catch (JSONException e) {
		   e.printStackTrace();
		   return null;
		}	
	}

}