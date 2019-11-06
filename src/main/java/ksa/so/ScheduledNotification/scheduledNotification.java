package ksa.so.ScheduledNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ksa.so.domain.CloudMessageService;
import ksa.so.repositories.CouponUsersRepository;
import ksa.so.repositories.DiscountCouponRepository;
import ksa.so.repositories.UserAppRepository;

@Component
public class scheduledNotification {
	CloudMessageService cms = new CloudMessageService();
	private Logger logger = LoggerFactory.getLogger(scheduledNotification.class);

	@Autowired
	UserAppRepository userAppRepository;

	@Autowired
	DiscountCouponRepository discountCouponRepository;

	@Autowired
	CouponUsersRepository couponUsersRepository;

	/*
	 * @Scheduled(cron = "* * * * * *") // every three days public void
	 * sendNotificationcheck() throws Exception { try { // required properties for
	 * exporting of db JSONObject jsonNotification = new JSONObject(); JSONObject
	 * orderStatus = new JSONObject();
	 *
	 * orderStatus.put("title", "Reminder"); orderStatus.put("body",
	 * "4 days Left for the voucher to expire"); jsonNotification.put("orderStatus",
	 * orderStatus); System.out.println("\uD83D\uDC7D"); String title =
	 * "Title \uD83D\uDE00"; // send notification to user String deviceToken =
	 * "cNnq2LRe8no:APA91bG99MPq69VR_4xU4pcNRtl8osZZ9iWQFk7wW8altQUUr6IDbFQvD754KytTpYw2NohJgo3_1Cd0kBUETLMZvO0gGrGrlDPIY_ZU4yeTuw7fd3vo540VF4LneoHK6EdWpUN6NHAy";
	 * int[] surrogates = { 0xD83D, 0xDC7D }; JSONObject notification = new
	 * JSONObject();
	 *
	 * notification.put("title", title); notification.put("body",
	 * "Enjoy 50% off upto Rs. 5000. Offer expiring in 5 days!");
	 * notification.put("sound", "default"); notification.put("click_action",
	 * "FCM_PLUGIN_ACTIVITY");
	 *
	 * cms.sendMessage(deviceToken, jsonNotification, notification, false);
	 *
	 * } catch (Exception e) { System.out.println("error" + e); } }
	 */

//	@Scheduled(cron = "0/30 * * * * ?")
//	@Scheduled(cron = "0 0 2 * * ?") // every three days
//	public void sendNotification() throws Exception {
//		try {
//			// required properties for exporting of db
//			JSONObject jsonNotification = new JSONObject();
//			JSONObject orderStatus = new JSONObject();
//
//			orderStatus.put("title", "Reminder");
//			orderStatus.put("body", "4 days Left for the voucher to expire");
//			jsonNotification.put("orderStatus", orderStatus);
//
//			// send notification to user
//			String deviceToken = "";
//
//			List<UserApp> userList = userAppRepository.findAll();
//
//			for (UserApp user : userList) {
//				deviceToken = user.getInstallationId();
//				// deviceToken = order.getUser().getInstallationId();
//				DiscountCoupon coupon = discountCouponRepository.findOne((long) 4);
//				CouponUsers userCoupon = couponUsersRepository.findByUserAndDiscountCoupon(user, coupon);
//				if (userCoupon != null) {
//					JSONObject notification = new JSONObject();
//					notification.put("title", "Good News! Monthly Coupon is now available :)");
//					notification.put("body",
//							"Enjoy 30% off with discount limit of Rs. 2000 on your favorite products. Offer valid till 24th Sept 2019.");
//					notification.put("sound", "default");
//					notification.put("click_action", "FCM_PLUGIN_ACTIVITY");
//
//					cms.sendMessage(deviceToken, jsonNotification, notification, false);
//				} else {
//					coupon = discountCouponRepository.findOne((long) 5);
//					userCoupon = couponUsersRepository.findByUserAndDiscountCoupon(user, coupon);
//					if (userCoupon != null) {
//						JSONObject notification = new JSONObject();
//						notification.put("title", "Good News! Monthly Coupon is now available :)");
//						notification.put("body",
//								"Enjoy 30% off with discount limit of Rs. 2000 on your favorite products. Offer valid till 24th Sept 2019.");
//						notification.put("sound", "default");
//						notification.put("click_action", "FCM_PLUGIN_ACTIVITY");
//
//						cms.sendMessage(deviceToken, jsonNotification, notification, false);
//					}
//
//				}
//
//			}
//		} catch (Exception e) {
//			System.out.println("error" + e);
//		}
//	}
//
//	@Scheduled(cron = "0 0 2 * * ?") // every three days
//	public void sendDefenceGStore() throws Exception {
//		try {
//			// required properties for exporting of db
//			JSONObject jsonNotification = new JSONObject();
//			JSONObject orderStatus = new JSONObject();
//
//			orderStatus.put("title", "Reminder");
//			orderStatus.put("body", "4 days Left for the voucher to expire");
//			jsonNotification.put("orderStatus", orderStatus);
//
//			// send notification to user
//			String deviceToken = "";
//
//			List<UserApp> userList = userAppRepository.findAll();
//
//			for (UserApp user : userList) {
//				deviceToken = user.getInstallationId();
//
//				JSONObject notification = new JSONObject();
//				notification.put("title", "Good News!Save More on shopping from Defence G Store :)");
//				notification.put("body", "Enjoy wholesale rates by ordering from Defence G Store. Shop Now!");
//				notification.put("sound", "default");
//				notification.put("click_action", "FCM_PLUGIN_ACTIVITY");
//
//				cms.sendMessage(deviceToken, jsonNotification, notification, false);
//			}
//
//		} catch (Exception e) {
//			System.out.println("error" + e);
//		}
//	}
}
