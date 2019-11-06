package ksa.so.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMail {

	private static Logger log = LoggerFactory.getLogger(ScheduledMail.class);

	@Autowired
	private EmailService emailService;

	@Scheduled(cron = "0 0 9 * * ?") // 10 pm PST
	// @Scheduled(cron = "0/20 * * * * ?")
	public void sendMail() throws Exception {
		try {
			log.info("Spring Mail - Sending Email Attachment Configuration Example");

			// String[] toArray = {"irfan.fazli@benchmatrix.com"};
			String[] toArray = { "marium.hashmi@benchmatrix.ca" };
			String[] ccArray = { "marium.hashmi@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Report");
			mail.setContent("PFA");

			emailService.sendSimpleMessage(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}

	@Scheduled(cron = "0 0 9 * * ?") // 10 pm PST
	// @Scheduled(cron = "0/20 * * * * ?")
	public void sendReport() throws Exception {
		try {
			log.info("Spring Mail - Sending Email Attachment Configuration Example");

			// String[] toArray = {"irfan.fazli@benchmatrix.com"};
			String[] toArray = { "youusf.siddiqui@benchmatrix.ca","irfan.fazli@benchmatrix.com","farzeen.ahmed@benchmatrix.ca" };
			String[] ccArray = { "yousuf.siddiqui@benchmatrix.ca","bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Report");
			mail.setContent("PFA");

			emailService.sendConsolidatedReport(mail);
			
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}
	
	@Scheduled(cron = "0 0 9 * * ?") // 10 pm PST
	// @Scheduled(cron = "0/20 * * * * ?")
	public void sendItemsReport() throws Exception {
		try {
			log.info("Spring Mail - Sending Email Attachment Configuration Example");

			// String[] toArray = {"irfan.fazli@benchmatrix.com"};
			String[] toArray = { "yousf.siddiqui@benchmatrix.ca","irfan.fazli@benchmatrix.com","farzeen.ahmed@benchmatrix.ca" };
			String[] ccArray = { "yousuf.siddiqui@benchmatrix.ca","bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Report");
			mail.setContent("PFA");

			emailService.sendItemSalesReport(mail);
			
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}


	@Scheduled(cron = "0 0 9 * * ?") // 10 pm PST
	public void sendRegisteredUserMail() throws Exception {
		try {
			log.info("Spring Mail - Sending Registered User Email");

			// String[] toArray = {"irfan.fazli@benchmatrix.com"};
			String[] toArray = { "rohail.khan@benchmatrix.ca" };
			String[] ccArray = { "bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Registered User Report");
			mail.setContent("PFA");

			emailService.sendRegistedUserMessage(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}

	@Scheduled(cron = "0 0 9 * * ?") // 10 pm PST
	public void sendDiscountedAmountReport() throws Exception {
		try {
			log.info("Spring Mail - Sending Daily Sales Report Email");

			// String[] toArray = {"irfan.fazli@benchmatrix.com"};
			String[] toArray = { "humair.kazim@benchmatrix.com", "irfan.fazli@benchmatrix.com",
					"rohail.khan@benchmatrix.ca", "farzeen.ahmed@benchmatrix.ca" };
			String[] ccArray = { "bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("User Discount Report");
			mail.setContent("PFA");

			emailService.sendDiscountedAmountReport(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}

	@Scheduled(cron = "0 0 9 * * ?") // 10 pm PST
	public void sendCustomerFeedbackMail() throws Exception {
		try {
			log.info("Spring Mail - Sending Customer Feedback Email");

			// String[] toArray = {"irfan.fazli@benchmatrix.com"};
			String[] toArray = { "rohail.khan@benchmatrix.ca" };
			String[] ccArray = { "bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Customer FeedBack Report");
			mail.setContent("PFA");

			emailService.sendCustomerFeedbackReport(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}

	@Scheduled(cron =  "0 0 9 * * ?") // 10 pm PST
	public void sendDailySalesReport() throws Exception {
		try {
			log.info("Spring Mail - Sending Daily Sales Report");

			String[] toArray = { "humair.kazim@benchmatrix.com", "rohail.khan@benchmatrix.ca",
					"farzeen.ahmed@benchmatrix.ca" };
			String[] ccArray = { "bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Daily Sales Report");
			mail.setContent("PFA");

			emailService.sendDailySalesReport(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}
	

	@Scheduled(cron = "0 0 9 * * ?") // 10 pm PST
	// @Scheduled(cron = "0/20     ?")
	public void reportB() throws Exception {
		try {
			log.info("Spring Mail - Sending Email Attachment Configuration Example");

			// String[] toArray = {"irfan.fazli@benchmatrix.com"};
			String[] toArray = { "hammad.ali@benchmatrix.ca","irfan.fazli@benchmatrix.com",	"farzeen.ahmed@benchmatrix.ca" };
			String[] ccArray = { "hammad.ali@benchmatrix.ca","bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Report");
			mail.setContent("PFA");

emailService.sendReportB(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}
	
	
	//hammad
	@Scheduled(cron = "0 0 9 * * ?") // 10 pm PST
	// @Scheduled(cron = "0/20     ?")
	public void reportE() throws Exception {
		try {
			log.info("Spring Mail - Sending Email Attachment Configuration Example");

			// String[] toArray = {"irfan.fazli@benchmatrix.com"};
			String[] toArray = { "hammad.ali@benchmatrix.ca" };
			String[] ccArray = { "hammad.ali@benchmatrix.ca","bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Report");
			mail.setContent("PFA");

	emailService.sendReportE(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}
	
	
	


}
