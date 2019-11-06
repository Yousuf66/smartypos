package ksa.so.mail;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.mail.MessagingException;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ksa.so.domain.BranchLanguage;
import ksa.so.domain.CustomerOrder;
import ksa.so.domain.DiscountCouponLanguage;
import ksa.so.domain.EmailErrors;
import ksa.so.domain.ItemLanguage;
import ksa.so.domain.MetaLanguage;
import ksa.so.domain.OrderItem;
import ksa.so.domain.UserApp;
import ksa.so.repositories.BranchLanguageRepository;
import ksa.so.repositories.DiscountCouponLanguageRepository;
import ksa.so.repositories.EmailErrorsRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.repositories.SalesReportRepository;
import ksa.so.web.UserController;

@Service
public class Emails {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	MetaLanguageRepository languageRepository;

	@Autowired
	SalesReportRepository salesReportRepository;

	@Autowired
	MetaStatusRepository statusRepository;

	@Autowired
	private EmailService emailService;

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	public void RequestProduct(String productname, UserApp user) throws MessagingException, IOException {

		try {

			String[] toArray = { "rohail.khan@benchmatrix.ca" };
			String[] ccArray = { "bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("New Product Request");

			String html = user.getFirstName() + " requested for the product: " + productname;
			mail.setContent(html);

			emailService.sendSimpleMessage(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}

	public void sendInvoice(CustomerOrder order, MetaLanguage language, Double amountUsed,
			ItemLanguageRepository itemLanguageRepository, BranchLanguageRepository branchLanguageRepository,
			DiscountCouponLanguageRepository discountCouponLanguageRepository,
			EmailErrorsRepository emailErrorsRepository) throws MessagingException, IOException {
		String[] toArrayC = new String[1];
		try {
			NumberFormat anotherFormat = NumberFormat.getNumberInstance(Locale.US);
			if (anotherFormat instanceof DecimalFormat) {
				DecimalFormat anotherDFormat = (DecimalFormat) anotherFormat;
				anotherDFormat.applyPattern("#.00");
				anotherDFormat.setGroupingUsed(true);
				anotherDFormat.setGroupingSize(3);
			}

			String address;
			String deliveryType;
			Double dc = 0.0;
			String dcs;
			if (order.getAddress() != null) {
				address = order.getAddress().getAddress();
				deliveryType = "Delivery";
			} else {
				address = "No Address Available";
				deliveryType = "Takeaway";
			}
			String discountTitle = "";
			if (order.getDiscountCoupon() != null) {

				if (order.getDiscountCoupon().getPercentage() != null)
					dc = order.getActualAmount() * (order.getDiscountCoupon().getPercentage()) / 100;
				dcs = dc.toString();
				DiscountCouponLanguage discountCouponLanguage = discountCouponLanguageRepository
						.findByDiscountCouponAndLanguage(order.getDiscountCoupon(), language);
				discountTitle = discountCouponLanguage.getTitle();
			}

			else {
				dcs = "0.0";
				discountTitle = "-";
			}

			Double TotalAmountLeft = amountUsed - order.getDiscountedAmount();

			BranchLanguage branchLanguage = branchLanguageRepository.findByBranchAndLanguage(order.getBranch(),
					language);

			String[] toArray = { "usama.rafiq@benchmatrix.ca", "irfan.fazli@benchmatrix.com",
					"farzeen.ahmed@benchmatrix.ca", "rohail.khan@benchmatrix.ca" };
			// String[] toArray = { "bisma.ayaz@benchmatrix.ca" };
			String[] ccArray = { "bisma.ayaz@benchmatrix.ca" };
			// String[] toArray = { "bisma.ayaz@benchmatrix.ca" };
			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("New Order Placed");
			String a = "";
			String discountPercentage;
			if (order.getDiscountCoupon() != null)
				discountPercentage = order.getDiscountCoupon().getPercentage() + "%";
			else
				discountPercentage = "-";
			String currency = order.getCurrency().getTitle();
			int i = 0;
			for (OrderItem orderItem : order.getOrderItemList()) {
				i++;
				ItemLanguage itemLanguage = itemLanguageRepository.findByItemAndLanguage(orderItem.getItem(), language);

				/*
				 * a = a + "            <tr class=\"item\">\r\n" + " <td>\r\n" +
				 * "                   " + orderItem.getItem().getId() + "\r\n" +
				 * "                </td>\r\n" + "                <td>\r\n" +
				 * "                   " + itemLanguage.getTitle() + "\r\n" +
				 * "                </td>\r\n" + "                \r\n" +
				 * "                <td>\r\n" + orderItem.getQuantity() + "\r\n" +
				 * "                </td>\r\n" + "<td>\r\n" + orderItem.getPrice() + "\r\n" +
				 * "                </td>\r\n" + orderItem.getPrice() * orderItem.getQuantity()
				 * + "\r\n" + "                </td>\r\n" + "            </tr>\r\n" +
				 * "            \r\n";
				 */
				a = a + "  <tr>\r\n"
						+ "                                        <td width=\"10%\" class=\"purchase_item\" align=\"left\"><span class=\"f-fallback\"> "
						+ orderItem.getItem().getId() + "</span></td>\r\n"
						+ "                                        <td width=\"20%\" class=\"purchase_item\" align=\"left\"><span class=\"f-fallback\">"
						+ itemLanguage.getTitle() + "</span></td>\r\n"
						+ "                                        <td width=\"10%\" class=\"purchase_item\" align=\"center\"><span class=\"f-fallback\">"
						+ orderItem.getQuantity() + "</span></td>\r\n"
						+ "                                        <td width=\"20%\" class=\"purchase_item\" align=\"center\"><span class=\"f-fallback\">"
						+ anotherFormat.format(orderItem.getPrice()) + "</span></td>\r\n"
						+ "                                        <td width=\"15%\" class=\"purchase_item\" align=\"right\"><span class=\"f-fallback\">"
						+ anotherFormat.format(orderItem.getPrice() * orderItem.getQuantity()) + "</span></td>\r\n"
						+ "                                      \r\n" + "                                    </tr>";
				/*
				 * a = a + "<tr>\r\n" + "            <td class=\"no\">" + i + "</td>\r\n" +
				 * "            <td class=\"unit\">" + orderItem.getItem().getId() + "</td>\r\n"
				 * + "            <td class=\"desc\"><h3>" + itemLanguage.getTitle() +
				 * "</h3></td>\r\n" + "            <td class=\"unit\">" +
				 * orderItem.getQuantity() + "</td>\r\n" + "            <td class=\"qty\">" +
				 * currency + " " + orderItem.getPrice() + "</td>\r\n" +
				 * "            <td class=\"total\">" + currency + " " + orderItem.getPrice() *
				 * orderItem.getQuantity() + "</td>\r\n" + " </tr>\r\n";
				 */
			}
			SimpleDateFormat dateformater_yyyyMMdd = new SimpleDateFormat("dd-MM-yyyy - hh:mm a");
			String ShippingFees = "0.00";
			String DiscountedAmount = "0.00";
			if (order.getBranch().getShippingfees() != 0)
				ShippingFees = anotherFormat.format(order.getBranch().getShippingfees());

			if (order.getDiscountedAmount() != 0)
				DiscountedAmount = anotherFormat.format(order.getDiscountedAmount());

			String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
					+ "<html>\r\n" + "  <head>\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
					+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"
					+ "    <title></title>\r\n" + "    <style type=\"text/css\" rel=\"stylesheet\" media=\"all\">\r\n"
					+ "    /* Base ------------------------------ */\r\n" + "    \r\n"
					+ "    @import url(\"https://fonts.googleapis.com/css?family=Nunito+Sans:400,700&display=swap\");\r\n"
					+ "    body {\r\n" + "      width: 100% !important;\r\n" + "      height: 100%;\r\n"
					+ "      margin: 0;\r\n" + "      -webkit-text-size-adjust: none;\r\n" + "    }\r\n" + "    \r\n"
					+ "    a {\r\n" + "      color: #3869D4;\r\n" + "    }\r\n" + "    \r\n" + "    a img {\r\n"
					+ "      border: none;\r\n" + "    }\r\n" + "    \r\n" + "    td {\r\n"
					+ "      word-break: break-word;\r\n" + "    }\r\n" + "    \r\n" + "    .preheader {\r\n"
					+ "      display: none !important;\r\n" + "      visibility: hidden;\r\n"
					+ "      mso-hide: all;\r\n" + "      font-size: 1px;\r\n" + "      line-height: 1px;\r\n"
					+ "      max-height: 0;\r\n" + "      max-width: 0;\r\n" + "      opacity: 0;\r\n"
					+ "      overflow: hidden;\r\n" + "    }\r\n" + "    /* Type ------------------------------ */\r\n"
					+ "    \r\n" + "    body,\r\n" + "    td,\r\n" + "    th {\r\n"
					+ "      font-family: \"Nunito Sans\", Helvetica, Arial, sans-serif;\r\n" + "    }\r\n" + "    \r\n"
					+ "    h1 {\r\n" + "      margin-top: 0;\r\n" + "      color: #333333;\r\n"
					+ "      font-size: 22px;\r\n" + "      font-weight: bold;\r\n" + "      text-align: left;\r\n"
					+ "    }\r\n" + "    \r\n" + "    h2 {\r\n" + "      margin-top: 0;\r\n"
					+ "      color: #333333;\r\n" + "      font-size: 16px;\r\n" + "      font-weight: bold;\r\n"
					+ "      text-align: left;\r\n" + "    }\r\n" + "    \r\n" + "    h3 {\r\n"
					+ "      margin-top: 0;\r\n" + "      color: #333333;\r\n" + "      font-size: 14px;\r\n"
					+ "      font-weight: bold;\r\n" + "      text-align: left;\r\n" + "    }\r\n" + "    \r\n"
					+ "    td,\r\n" + "    th {\r\n" + "      font-size: 16px;\r\n" + "    }\r\n" + "    \r\n"
					+ "    p,\r\n" + "    ul,\r\n" + "    ol,\r\n" + "    blockquote {\r\n"
					+ "      margin: .4em 0 1.1875em;\r\n" + "      font-size: 16px;\r\n"
					+ "      line-height: 1.625;\r\n" + "    }\r\n" + "    \r\n" + "    p.sub {\r\n"
					+ "      font-size: 13px;\r\n" + "    }\r\n"
					+ "    /* Utilities ------------------------------ */\r\n" + "    \r\n" + "    .align-right {\r\n"
					+ "      text-align: right;\r\n" + "    }\r\n" + "    \r\n" + "    .align-left {\r\n"
					+ "      text-align: left;\r\n" + "    }\r\n" + "    \r\n" + "    .align-center {\r\n"
					+ "      text-align: center;\r\n" + "    }\r\n"
					+ "    /* Buttons ------------------------------ */\r\n" + "    \r\n" + "    .button {\r\n"
					+ "      background-color: #3869D4;\r\n" + "      border-top: 10px solid #3869D4;\r\n"
					+ "      border-right: 18px solid #3869D4;\r\n" + "      border-bottom: 10px solid #3869D4;\r\n"
					+ "      border-left: 18px solid #3869D4;\r\n" + "      display: inline-block;\r\n"
					+ "      color: #FFF;\r\n" + "      text-decoration: none;\r\n" + "      border-radius: 3px;\r\n"
					+ "      box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16);\r\n"
					+ "      -webkit-text-size-adjust: none;\r\n" + "      box-sizing: border-box;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .button--green {\r\n" + "      background-color: #22BC66;\r\n"
					+ "      border-top: 10px solid #22BC66;\r\n" + "      border-right: 18px solid #22BC66;\r\n"
					+ "      border-bottom: 10px solid #22BC66;\r\n" + "      border-left: 18px solid #22BC66;\r\n"
					+ "      text-align: justify !important;\r\n" + "    }\r\n" + "    \r\n" + "    .button--red {\r\n"
					+ "      background-color: #FF6136;\r\n" + "      border-top: 10px solid #FF6136;\r\n"
					+ "      border-right: 18px solid #FF6136;\r\n" + "      border-bottom: 10px solid #FF6136;\r\n"
					+ "      border-left: 18px solid #FF6136;\r\n" + "    }\r\n" + "    \r\n"
					+ "    @media only screen and (max-width: 500px) {\r\n" + "      .button {\r\n"
					+ "        width: 100% !important;\r\n" + "        text-align: center !important;\r\n"
					+ "      }\r\n" + "    }\r\n" + "    /* Attribute list ------------------------------ */\r\n"
					+ "    \r\n" + "    .attributes {\r\n" + "      margin: 0 0 21px;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .attributes_content {\r\n" + "      background-color: #F4F4F7;\r\n"
					+ "      padding: 16px;\r\n" + "    }\r\n" + "    \r\n" + "    .attributes_item {\r\n"
					+ "      padding: 0;\r\n" + "    }\r\n .attributess {\r\n" + "      margin: 0 0 21px;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .attributess_content {\r\n" + "      background-color: plum;\r\n"
					+ "      padding: 16px;\r\n" + "    }\r\n" + "    \r\n" + "    .attributess_item {\r\n"
					+ "      padding: 0;\r\n" + "    }"

					+ "    /* Related Items ------------------------------ */\r\n" + "    \r\n" + "    .related {\r\n"
					+ "      width: 100%;\r\n" + "      margin: 0;\r\n" + "      padding: 25px 0 0 0;\r\n"
					+ "      -premailer-width: 100%;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "    }\r\n" + "    \r\n" + "    .related_item {\r\n"
					+ "      padding: 10px 0;\r\n" + "      color: #CBCCCF;\r\n" + "      font-size: 15px;\r\n"
					+ "      line-height: 18px;\r\n" + "    }\r\n" + "    \r\n" + "    .related_item-title {\r\n"
					+ "      display: block;\r\n" + "      margin: .5em 0 0;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .related_item-thumb {\r\n" + "      display: block;\r\n" + "      padding-bottom: 10px;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .related_heading {\r\n"
					+ "      border-top: 1px solid #CBCCCF;\r\n" + "      text-align: center;\r\n"
					+ "      padding: 25px 0 10px;\r\n" + "    }\r\n"
					+ "    /* Discount Code ------------------------------ */\r\n" + "    \r\n" + "    .discount {\r\n"
					+ "      width: 100%;\r\n" + "      margin: 0;\r\n" + "      padding: 24px;\r\n"
					+ "      -premailer-width: 100%;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "      background-color: #F4F4F7;\r\n"
					+ "      border: 2px dashed #CBCCCF;\r\n" + "    }\r\n" + "    \r\n" + "    .discount_heading {\r\n"
					+ "      text-align: center;\r\n" + "    }\r\n" + "    \r\n" + "    .discount_body {\r\n"
					+ "      text-align: center;\r\n" + "      font-size: 15px;\r\n" + "    }\r\n"
					+ "    /* Social Icons ------------------------------ */\r\n" + "    \r\n" + "    .social {\r\n"
					+ "      width: auto;\r\n" + "    }\r\n" + "    \r\n" + "    .social td {\r\n"
					+ "      padding: 0;\r\n" + "      width: auto;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .social_icon {\r\n" + "      height: 20px;\r\n" + "      margin: 0 8px 10px 8px;\r\n"
					+ "      padding: 0;\r\n" + "    }\r\n" + "    /* Data table ------------------------------ */\r\n"
					+ "    \r\n" + "    .purchase {\r\n" + "      width: 100%;\r\n" + "      margin: 0;\r\n"
					+ "      padding: 35px 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .purchase_content {\r\n" + "      width: 100%;\r\n" + "      margin: 0;\r\n"
					+ "      padding: 10px 0 0 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .purchase_item {\r\n" + "      padding: 10px 0;\r\n"
					+ "      color: #51545E;\r\n" + "      font-size: 15px;\r\n" + "      line-height: 18px;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .purchase_heading {\r\n" + "      padding-bottom: 8px;\r\n"
					+ "      border-bottom: 1px solid #EAEAEC;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .purchase_heading p {\r\n" + "      margin: 0;\r\n" + "      color: #85878E;\r\n"
					+ "      font-size: 12px;\r\n" + "    }\r\n" + "    \r\n" + "    .purchase_footer {\r\n"
					+ "      padding-top: 1px;\r\n" + "      border-top: 1px solid #EAEAEC;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .purchase_total {\r\n" + "      margin: 0;\r\n" + "      text-align: left;\r\n"
					+ "      font-weight: bold;\r\n" + "      color: #333333;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .purchase_total--label {\r\n" + "      padding: 0 15px 0 0;\r\n" + "    }\r\n" + "    \r\n"
					+ "    body {\r\n" + "      background-color: #FFF;\r\n" + "      color: #333;\r\n" + "    }\r\n"
					+ "    \r\n" + "    p {\r\n" + "      color: #333;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .email-wrapper {\r\n" + "      width: 100%;\r\n" + "      margin: 0;\r\n"
					+ "      padding: 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .email-content {\r\n" + "      width: 100%;\r\n" + "      margin: 0;\r\n"
					+ "      padding: 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n" + "    }\r\n"
					+ "    /* Masthead ----------------------- */\r\n" + "    \r\n" + "    .email-masthead {\r\n"
					+ "      padding: 25px 0;\r\n" + "      text-align: center;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .email-masthead_logo {\r\n" + "      width: 94px;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .email-masthead_name {\r\n" + "      font-size: 16px;\r\n" + "      font-weight: bold;\r\n"
					+ "      color: black;\r\n" + "      text-decoration: none;\r\n"
					+ "      text-shadow: 0 1px 0 white;\r\n" + "    }\r\n"
					+ "    /* Body ------------------------------ */\r\n" + "    \r\n" + "    .email-body {\r\n"
					+ "      width: 100%;\r\n" + "      margin: 0;\r\n" + "      padding: 0;\r\n"
					+ "      -premailer-width: 100%;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "    }\r\n" + "    \r\n" + "    .email-body_inner {\r\n"
					+ "      width: 570px;\r\n" + "      margin: 0 auto;\r\n" + "      padding: 0;\r\n"
					+ "      -premailer-width: 570px;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "    }\r\n" + "    \r\n" + "    .email-footer {\r\n"
					+ "      width: 570px;\r\n" + "      margin: 0 auto;\r\n" + "      padding: 0;\r\n"
					+ "      -premailer-width: 570px;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "      text-align: center;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .email-footer p {\r\n" + "      color: #A8AAAF;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .body-action {\r\n" + "      width: 100%;\r\n" + "      margin: 30px auto;\r\n"
					+ "      padding: 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n"
					+ "      text-align: center;\r\n" + "    }\r\n" + "    \r\n" + "    .body-sub {\r\n"
					+ "      margin-top: 25px;\r\n" + "      padding-top: 25px;\r\n"
					+ "      border-top: 1px solid #EAEAEC;\r\n" + "    }\r\n" + "    \r\n" + "    .content-cell {\r\n"
					+ "      padding: 35px;\r\n" + "    }\r\n"
					+ "    /*Media Queries ------------------------------ */\r\n" + "    \r\n"
					+ "    @media only screen and (max-width: 600px) {\r\n" + "      .email-body_inner,\r\n"
					+ "      .email-footer {\r\n" + "        width: 100% !important;\r\n" + "      }\r\n" + "    }\r\n"
					+ "    \r\n" + "    @media (prefers-color-scheme: dark) {\r\n" + "      body {\r\n"
					+ "        background-color: #333333 !important;\r\n" + "        color: #FFF !important;\r\n"
					+ "      }\r\n" + "      p,\r\n" + "      ul,\r\n" + "      ol,\r\n" + "      blockquote,\r\n"
					+ "      h1,\r\n" + "      h2,\r\n" + "      h3 {\r\n" + "        color: #FFF !important;\r\n"
					+ "      }\r\n" + "      .attributes_content,\r\n" + "      .discount {\r\n"
					+ "        background-color: #222 !important;\r\n" + "      }\r\n"
					+ "      .email-masthead_name {\r\n" + "        text-shadow: none !important;\r\n" + "      \r\n"
					+ "      \r\n" + "      }\r\n" + "    }\r\n" + "    </style>\r\n" + "    <!--[if mso]>\r\n"
					+ "    <style type=\"text/css\">\r\n" + "      .f-fallback  {\r\n"
					+ "        font-family: Arial, sans-serif;\r\n" + "      }\r\n" + "    </style>\r\n"
					+ "  <![endif]-->\r\n" + "  </head>\r\n" + "  <body>\r\n"
					+ "    <span class=\"preheader\">This is an invoice for your purchase on "
					+ dateformater_yyyyMMdd.format(order.getTsServer()) + ". Please submit payment by "
					+ dateformater_yyyyMMdd.format(DateUtils.addDays(order.getTsServer(), 1)) + "</span>\r\n"
					+ "    <table class=\"email-wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "      <tr>\r\n" + "        <td align=\"center\">\r\n"
					+ "          <table class=\"email-content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "            <tr>\r\n" + "              <td class=\"email-masthead\">\r\n"
					+ "                <img src=\"https://firebasestorage.googleapis.com/v0/b/swiftcoffeeproject.appspot.com/o/Library%2Fic_launcher.png?alt=media&token=7325ddf8-4e34-4d2d-8179-3e4e53f0f663\" alt=\"Smiley face\" >\r\n"
					+ "              </td>\r\n" + "              \r\n" + "            </tr>\r\n"
					+ "            <tr>\r\n" + "              <td class=\"email-masthead\">\r\n"
					+ "                <p class=\"f-fallback email-masthead_name\" >\r\n" + "              Order # "
					+ order.getOrderNumber() + "\r\n" + "              </p>\r\n" + "              </td>\r\n"
					+ "            </tr>\r\n" + "            <!-- Email Body -->\r\n" + "            <tr>\r\n"
					+ "              <td class=\"email-body\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "                <table class=\"email-body_inner\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "                  <!-- Body content -->\r\n" + "                  <tr>\r\n"
					+ "                    <td class=\"content-cell\">\r\n"
					+ "                      <div class=\"f-fallback\">\r\n" + "                        <h1>Dear "
					+ order.getUser().getFirstName() + ",</h1>\r\n"
					+ "                        <p>Thanks for using Smarty. This is an invoice for your recent purchase.</p>\r\n"
					+ "                        <table class=\"attributes\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "                          <tr>\r\n"
					+ "                            <td class=\"attributes_content\">\r\n"
					+ "                              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "                                <tr>\r\n"
					+ "                                  <td class=\"attributes_item\">\r\n"
					+ "                                    <span class=\"f-fallback\">\r\n"
					+ "              <strong>Store Name:</strong> " + branchLanguage.getTitle() + "\r\n"
					+ "            </span>\r\n" + "                                  </td>\r\n"
					+ "                                </tr>\r\n" + "                               \r\n"
					+ "                                <tr>\r\n"
					+ "                                    <td class=\"attributes_item\">\r\n"
					+ "                                      <span class=\"f-fallback\">\r\n"
					+ "                <strong>Created: </strong> " + dateformater_yyyyMMdd.format(order.getTsClient())
					+ "\r\n" + "              </span>\r\n" + "                                    </td>\r\n"
					+ "                                  </tr>\r\n" + "\r\n"
					+ "                                  <tr>\r\n"
					+ "                                    <td class=\"attributes_item\">\r\n"
					+ "                                      <span class=\"f-fallback\">\r\n"
					+ "                <strong>Delivery Type:</strong> " + deliveryType + "\r\n"
					+ "              </span>\r\n" + "                                    </td>\r\n"
					+ "                                  </tr>\r\n" + "\r\n"
					+ "                                  <tr>\r\n"
					+ "                                    <td class=\"attributes_item\">\r\n"
					+ "                                      <span class=\"f-fallback\">\r\n"
					+ "                <strong>Payment Method:</strong> Cash On Delivery\r\n"
					+ "              </span>\r\n" + "                                    </td>\r\n"
					+ "                                  </tr>\r\n" + "\r\n" + "                               \r\n"
					+ "                              </table>\r\n" + "                            </td>\r\n"
					+ "                          </tr>\r\n" + "                        </table>\r\n"
					+ "                       <!-- Action -->\r\n"
					+ "                        <table class=\"attributess\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "                            <tr>\r\n"
					+ "                              <td class=\"attributess_content\">\r\n"
					+ "                                <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "  <tr>\r\n" + "                                    <td class=\"attributess_item\">\r\n"
					+ "                                      <span class=\"f-fallback\">\r\n"
					+ "                <strong>Customer Name:</strong> " + order.getUser().getFirstName() + "\r\n"
					+ "              </span>\r\n" + "                                    </td>\r\n"
					+ "                                  </tr>\r\n" + "                                 \r\n"
					+ "                                  <tr>\r\n"
					+ "                                    <td class=\"attributess_item\">\r\n"
					+ "                                      <span class=\"f-fallback\">\r\n"
					+ "                <strong>Delivery Address:</strong> " + address + "\r\n"
					+ "              </span>\r\n" + "                                    </td>\r\n"
					+ "                                  </tr>\r\n" + "                                 \r\n"
					+ "                                  <tr>\r\n"
					+ "                                      <td class=\"attributess_item\">\r\n"
					+ "                                        <span class=\"f-fallback\">\r\n"
					+ "                  <strong>Contact Number: </strong> " + order.getUser().getPhone() + "\r\n"
					+ "                </span>\r\n" + "                                      </td>\r\n"
					+ "                                    </tr>\r\n" + "  \r\n"
					+ "                                    <tr>\r\n"
					+ "                                      <td class=\"attributess_item\">\r\n"
					+ "                                        <span class=\"f-fallback\">\r\n"
					+ "                  <strong>Order Instruction:</strong> " + order.getInstructionComment() + "\r\n"
					+ "                </span>\r\n" + "                                      </td>\r\n"
					+ "                                    </tr>\r\n" + "  \r\n"
					+ "                                    \r\n" + "  \r\n" + "                                 \r\n"
					+ "                                </table>\r\n" + "                              </td>\r\n"
					+ "                            </tr>\r\n" + "                          </table>"
					+ "                        <table class=\"purchase\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "                         \r\n" + "                          <tr>\r\n"
					+ "                            <td colspan=\"5\">\r\n"
					+ "                                <table class=\"purchase_content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "                                   <tr>\r\n"
					+ "                                        <th class=\"purchase_heading\" align=\"left\" width=\"10%\">Item ID</th>\r\n"
					+ "                                        <th class=\"purchase_heading\" align=\"left\" width=\"20%\">Item Name</th>\r\n"
					+ "                                        <th class=\"purchase_heading\" align=\"center\" width=\"10%\">Qty</th>\r\n"
					+ "                                        <th class=\"purchase_heading\" align=\"center\" width=\"20%\">Price</th>\r\n"
					+ "                                        <th class=\"purchase_heading\" align=\"right\" width=\"15%\">Total</th>\r\n"
					+ "                                    </tr>" + "                                   " + a
					+ "                              \r\n" + "                              \r\n"
					+ "                             \r\n" + "                              \r\n"
					+ "                              </table>\r\n" + "\r\n"
					+ "             <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">                 <tr>\r\n"
					+ "                                  <td width=\"60%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                    <p class=\"f-fallback purchase_total purchase_total--label\" align=\"left\"><b>Grand Total </b></p>\r\n"
					+ "                                  </td>\r\n"
					+ "                                  <td width=\"40%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                    <p class=\"f-fallback purchase_total\" align=\"left\"><b>"
					+ currency + " " + anotherFormat.format(order.getActualAmount()) + "</b></p>\r\n"
					+ "                                  </td>\r\n" + "                                </tr>\r\n"
					+ "                                <tr>\r\n"
					+ "                                    <td width=\"60%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                      <p class=\"f-fallback purchase_total purchase_total--label\" align=\"left\"><b>Shipping Charges</b></p>\r\n"
					+ "                                    </td>\r\n"
					+ "                                    <td width=\"40%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                      <p class=\"f-fallback purchase_total\" align=\"left\"><b>"
					+ currency + " " + ShippingFees + "</b></p>\r\n" + "                                    </td>\r\n"
					+ "                                  </tr>\r\n" + "\r\n"
					+ "                                  <tr>\r\n"
					+ "                                      <td width=\"60%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                        <p class=\"f-fallback purchase_total purchase_total--label\" align=\"left\"><b>Voucher Discount</b></p>\r\n"
					+ "                                      </td>\r\n"
					+ "                                      <td width=\"40%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                        <p class=\"f-fallback purchase_total\" align=\"left\"><b> "
					+ currency + " " + DiscountedAmount + "</b></p>\r\n"
					+ "                                      </td>\r\n"
					+ "                                    </tr>\r\n" + "\r\n"
					+ "                                    <tr>\r\n"
					+ "                                        <td width=\"60%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                          <p class=\"f-fallback purchase_total purchase_total--label\" align=\"left\"><b>Discount Percentage</b></p>\r\n"
					+ "                                        </td>\r\n"
					+ "                                        <td width=\"40%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                          <p class=\"f-fallback purchase_total\" align=\"left\"><b>"
					+ discountPercentage + "</b></p>\r\n" + "                                        </td>\r\n"
					+ "                                      </tr>\r\n " + "<tr>\r\n"
					+ "                                        <td width=\"60%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                          <p class=\"f-fallback purchase_total purchase_total--label\" align=\"left\"><b>Remaining Discount Amount</b></p>\r\n"
					+ "                                        </td>\r\n"
					+ "                                        <td width=\"40%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                          <p class=\"f-fallback purchase_total\" align=\"left\"><b>"
					+ currency + " " + anotherFormat.format(TotalAmountLeft) + "</b></p>\r\n"
					+ "                                        </td>\r\n"
					+ "                                      </tr>\r\n " + "<tr>\r\n"
					+ "                                        <td width=\"60%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                          <p class=\"f-fallback purchase_total purchase_total--label\" align=\"left\"><b>Amount Payable</b></p>\r\n"
					+ "                                        </td>\r\n"
					+ "                                        <td width=\"40%\" class=\"purchase_footer\" valign=\"middle\" align=\"left\">\r\n"
					+ "                                          <p class=\"f-fallback purchase_total\" align=\"left\"><b>"
					+ currency + " " + anotherFormat.format(order.getTotalAmount()) + "</b></p>\r\n"
					+ "                                        </td>\r\n"
					+ "                                      </tr>\r\n " + "</table>"
					+ "                            </td>\r\n" + "                          </tr>\r\n"
					+ "                        </table>\r\n"
					+ "                        <p>If you have any questions about this invoice, simply reach out to our <a href=\"tel:02135620944\">support team</a> for help.</p>\r\n"
					+ "                        <p>Assalam-O-Alaikum,\r\n" + "                          <br>The Smarty Team</p>\r\n"
					+ "                        <!-- Sub copy -->\r\n" + "                       \r\n"
					+ "                      </div>\r\n" + "                    </td>\r\n"
					+ "                  </tr>\r\n" + "                </table>\r\n" + "              </td>\r\n"
					+ "            </tr>\r\n" + "              </table>\r\n" + "        </td>\r\n" + "      </tr>\r\n"
					+ "    </table>\r\n" + "  </body>\r\n" + "</html>";
			mail.setContent(html);

			emailService.sendSimpleMessage(mail);
//Sending Email to Customer

			toArrayC[0] = order.getUser().getEmail();
			String[] ccArrayC = {};
			// String[] toArray = { "bisma.ayaz@benchmatrix.ca" };
			Mail mailC = new Mail();
			mailC.setFrom("no-reply@memorynotfound.com");
			mailC.setToArray(toArrayC);
			mailC.setCcArray(ccArrayC);
			mailC.setSubject("Your Order has been Placed");
			mailC.setContent(html);

			emailService.sendSimpleMessage(mailC);

		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
			if (toArrayC.length != 0) {
				EmailErrors emailErrors = new EmailErrors();
				emailErrors.setError(e.getMessage());
				emailErrors.setEmailId(toArrayC[0]);
				emailErrors.setOrder(order);
				emailErrorsRepository.save(emailErrors);
			}
		}
	}

	public void accountConfirmationEmail(UserApp user) throws MessagingException, IOException {
		String[] toArrayC = new String[1];
		try {

			String[] toArray = { "marium.hashmi@benchmatrix.ca", "irfan.fazli@benchmatrix.com" };
			// String[] toArray = { "bisma.ayaz@benchmatrix.ca" };
			String[] ccArray = { "bisma.ayaz@benchmatrix.ca" };
			// String[] toArray = { "bisma.ayaz@benchmatrix.ca" };
			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Account Confirmation Email");
			String a = "";

			String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
					+ "<html>\r\n" + "  <head>\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
					+ "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n"
					+ "    <title></title>\r\n" + "    <style type=\"text/css\" rel=\"stylesheet\" media=\"all\">\r\n"
					+ "    /* Base ------------------------------ */\r\n" + "    \r\n"
					+ "    @import url(\"https://fonts.googleapis.com/css?family=Nunito+Sans:400,700&display=swap\");\r\n"
					+ "    body {\r\n" + "      width: 100% !important;\r\n" + "      height: 100%;\r\n"
					+ "      margin: 0;\r\n" + "      -webkit-text-size-adjust: none;\r\n" + "    }\r\n" + "    \r\n"
					+ "    a {\r\n" + "      color: #3869D4;\r\n" + "    }\r\n" + "    \r\n" + "    a img {\r\n"
					+ "      border: none;\r\n" + "    }\r\n" + "    \r\n" + "    td {\r\n"
					+ "      word-break: break-word;\r\n" + "    }\r\n" + "    \r\n" + "    .preheader {\r\n"
					+ "      display: none !important;\r\n" + "      visibility: hidden;\r\n"
					+ "      mso-hide: all;\r\n" + "      font-size: 1px;\r\n" + "      line-height: 1px;\r\n"
					+ "      max-height: 0;\r\n" + "      max-width: 0;\r\n" + "      opacity: 0;\r\n"
					+ "      overflow: hidden;\r\n" + "    }\r\n" + "    /* Type ------------------------------ */\r\n"
					+ "    \r\n" + "    body,\r\n" + "    td,\r\n" + "    th {\r\n"
					+ "      font-family: \"Nunito Sans\", Helvetica, Arial, sans-serif;\r\n" + "    }\r\n" + "    \r\n"
					+ "    h1 {\r\n" + "      margin-top: 0;\r\n" + "      color: #333333;\r\n"
					+ "      font-size: 22px;\r\n" + "      font-weight: bold;\r\n" + "      text-align: left;\r\n"
					+ "    }\r\n" + "    \r\n" + "    h2 {\r\n" + "      margin-top: 0;\r\n"
					+ "      color: #333333;\r\n" + "      font-size: 16px;\r\n" + "      font-weight: bold;\r\n"
					+ "      text-align: left;\r\n" + "    }\r\n" + "    \r\n" + "    h3 {\r\n"
					+ "      margin-top: 0;\r\n" + "      color: #333333;\r\n" + "      font-size: 14px;\r\n"
					+ "      font-weight: bold;\r\n" + "      text-align: left;\r\n" + "    }\r\n" + "    \r\n"
					+ "    td,\r\n" + "    th {\r\n" + "      font-size: 16px;\r\n" + "    }\r\n" + "    \r\n"
					+ "    p,\r\n" + "    ul,\r\n" + "    ol,\r\n" + "    blockquote {\r\n"
					+ "      margin: .4em 0 1.1875em;\r\n" + "      font-size: 16px;\r\n"
					+ "      line-height: 1.625;\r\n" + "    }\r\n" + "    \r\n" + "    p.sub {\r\n"
					+ "      font-size: 13px;\r\n" + "    }\r\n"
					+ "    /* Utilities ------------------------------ */\r\n" + "    \r\n" + "    .align-right {\r\n"
					+ "      text-align: right;\r\n" + "    }\r\n" + "    \r\n" + "    .align-left {\r\n"
					+ "      text-align: left;\r\n" + "    }\r\n" + "    \r\n" + "    .align-center {\r\n"
					+ "      text-align: center;\r\n" + "    }\r\n"
					+ "    /* Buttons ------------------------------ */\r\n" + "    \r\n" + "    .button {\r\n"
					+ "      background-color: #3869D4;\r\n" + "      border-top: 10px solid #3869D4;\r\n"
					+ "      border-right: 18px solid #3869D4;\r\n" + "      border-bottom: 10px solid #3869D4;\r\n"
					+ "      border-left: 18px solid #3869D4;\r\n" + "      display: inline-block;\r\n"
					+ "      color: #FFF;\r\n" + "      text-decoration: none;\r\n" + "      border-radius: 3px;\r\n"
					+ "      box-shadow: 0 2px 3px rgba(0, 0, 0, 0.16);\r\n"
					+ "      -webkit-text-size-adjust: none;\r\n" + "      box-sizing: border-box;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .button--green {\r\n" + "      background-color: #22BC66;\r\n"
					+ "      border-top: 10px solid #22BC66;\r\n" + "      border-right: 18px solid #22BC66;\r\n"
					+ "      border-bottom: 10px solid #22BC66;\r\n" + "      border-left: 18px solid #22BC66;\r\n"
					+ "      text-align: justify !important;\r\n" + "    }\r\n" + "    \r\n" + "    .button--red {\r\n"
					+ "      background-color: #FF6136;\r\n" + "      border-top: 10px solid #FF6136;\r\n"
					+ "      border-right: 18px solid #FF6136;\r\n" + "      border-bottom: 10px solid #FF6136;\r\n"
					+ "      border-left: 18px solid #FF6136;\r\n" + "    }\r\n" + "    \r\n"
					+ "    @media only screen and (max-width: 500px) {\r\n" + "      .button {\r\n"
					+ "        width: 100% !important;\r\n" + "        text-align: center !important;\r\n"
					+ "      }\r\n" + "    }\r\n" + "    /* Attribute list ------------------------------ */\r\n"
					+ "    \r\n" + "    .attributes {\r\n" + "      margin: 0 0 21px;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .attributes_content {\r\n" + "      background-color: #F4F4F7;\r\n"
					+ "      padding: 16px;\r\n" + "    }\r\n" + "    \r\n" + "    .attributes_item {\r\n"
					+ "      padding: 0;\r\n" + "    }\r\n .attributess {\r\n" + "      margin: 0 0 21px;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .attributess_content {\r\n" + "      background-color: plum;\r\n"
					+ "      padding: 16px;\r\n" + "    }\r\n" + "    \r\n" + "    .attributess_item {\r\n"
					+ "      padding: 0;\r\n" + "    }"

					+ "    /* Related Items ------------------------------ */\r\n" + "    \r\n" + "    .related {\r\n"
					+ "      width: 100%;\r\n" + "      margin: 0;\r\n" + "      padding: 25px 0 0 0;\r\n"
					+ "      -premailer-width: 100%;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "    }\r\n" + "    \r\n" + "    .related_item {\r\n"
					+ "      padding: 10px 0;\r\n" + "      color: #CBCCCF;\r\n" + "      font-size: 15px;\r\n"
					+ "      line-height: 18px;\r\n" + "    }\r\n" + "    \r\n" + "    .related_item-title {\r\n"
					+ "      display: block;\r\n" + "      margin: .5em 0 0;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .related_item-thumb {\r\n" + "      display: block;\r\n" + "      padding-bottom: 10px;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .related_heading {\r\n"
					+ "      border-top: 1px solid #CBCCCF;\r\n" + "      text-align: center;\r\n"
					+ "      padding: 25px 0 10px;\r\n" + "    }\r\n"
					+ "    /* Discount Code ------------------------------ */\r\n" + "    \r\n" + "    .discount {\r\n"
					+ "      width: 100%;\r\n" + "      margin: 0;\r\n" + "      padding: 24px;\r\n"
					+ "      -premailer-width: 100%;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "      background-color: #F4F4F7;\r\n"
					+ "      border: 2px dashed #CBCCCF;\r\n" + "    }\r\n" + "    \r\n" + "    .discount_heading {\r\n"
					+ "      text-align: center;\r\n" + "    }\r\n" + "    \r\n" + "    .discount_body {\r\n"
					+ "      text-align: center;\r\n" + "      font-size: 15px;\r\n" + "    }\r\n"
					+ "    /* Social Icons ------------------------------ */\r\n" + "    \r\n" + "    .social {\r\n"
					+ "      width: auto;\r\n" + "    }\r\n" + "    \r\n" + "    .social td {\r\n"
					+ "      padding: 0;\r\n" + "      width: auto;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .social_icon {\r\n" + "      height: 20px;\r\n" + "      margin: 0 8px 10px 8px;\r\n"
					+ "      padding: 0;\r\n" + "    }\r\n" + "    /* Data table ------------------------------ */\r\n"
					+ "    \r\n" + "    .purchase {\r\n" + "      width: 100%;\r\n" + "      margin: 0;\r\n"
					+ "      padding: 35px 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .purchase_content {\r\n" + "      width: 100%;\r\n" + "      margin: 0;\r\n"
					+ "      padding: 10px 0 0 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .purchase_item {\r\n" + "      padding: 10px 0;\r\n"
					+ "      color: #51545E;\r\n" + "      font-size: 15px;\r\n" + "      line-height: 18px;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .purchase_heading {\r\n" + "      padding-bottom: 8px;\r\n"
					+ "      border-bottom: 1px solid #EAEAEC;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .purchase_heading p {\r\n" + "      margin: 0;\r\n" + "      color: #85878E;\r\n"
					+ "      font-size: 12px;\r\n" + "    }\r\n" + "    \r\n" + "    .purchase_footer {\r\n"
					+ "      padding-top: 1px;\r\n" + "      border-top: 1px solid #EAEAEC;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .purchase_total {\r\n" + "      margin: 0;\r\n" + "      text-align: left;\r\n"
					+ "      font-weight: bold;\r\n" + "      color: #333333;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .purchase_total--label {\r\n" + "      padding: 0 15px 0 0;\r\n" + "    }\r\n" + "    \r\n"
					+ "    body {\r\n" + "      background-color: #FFF;\r\n" + "      color: #333;\r\n" + "    }\r\n"
					+ "    \r\n" + "    p {\r\n" + "      color: #333;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .email-wrapper {\r\n" + "      width: 100%;\r\n" + "      margin: 0;\r\n"
					+ "      padding: 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .email-content {\r\n" + "      width: 100%;\r\n" + "      margin: 0;\r\n"
					+ "      padding: 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n" + "    }\r\n"
					+ "    /* Masthead ----------------------- */\r\n" + "    \r\n" + "    .email-masthead {\r\n"
					+ "      padding: 25px 0;\r\n" + "      text-align: center;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .email-masthead_logo {\r\n" + "      width: 94px;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .email-masthead_name {\r\n" + "      font-size: 16px;\r\n" + "      font-weight: bold;\r\n"
					+ "      color: black;\r\n" + "      text-decoration: none;\r\n"
					+ "      text-shadow: 0 1px 0 white;\r\n" + "    }\r\n"
					+ "    /* Body ------------------------------ */\r\n" + "    \r\n" + "    .email-body {\r\n"
					+ "      width: 100%;\r\n" + "      margin: 0;\r\n" + "      padding: 0;\r\n"
					+ "      -premailer-width: 100%;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "    }\r\n" + "    \r\n" + "    .email-body_inner {\r\n"
					+ "      width: 570px;\r\n" + "      margin: 0 auto;\r\n" + "      padding: 0;\r\n"
					+ "      -premailer-width: 570px;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "    }\r\n" + "    \r\n" + "    .email-footer {\r\n"
					+ "      width: 570px;\r\n" + "      margin: 0 auto;\r\n" + "      padding: 0;\r\n"
					+ "      -premailer-width: 570px;\r\n" + "      -premailer-cellpadding: 0;\r\n"
					+ "      -premailer-cellspacing: 0;\r\n" + "      text-align: center;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .email-footer p {\r\n" + "      color: #A8AAAF;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .body-action {\r\n" + "      width: 100%;\r\n" + "      margin: 30px auto;\r\n"
					+ "      padding: 0;\r\n" + "      -premailer-width: 100%;\r\n"
					+ "      -premailer-cellpadding: 0;\r\n" + "      -premailer-cellspacing: 0;\r\n"
					+ "      text-align: center;\r\n" + "    }\r\n" + "    \r\n" + "    .body-sub {\r\n"
					+ "      margin-top: 25px;\r\n" + "      padding-top: 25px;\r\n"
					+ "      border-top: 1px solid #EAEAEC;\r\n" + "    }\r\n" + "    \r\n" + "    .content-cell {\r\n"
					+ "      padding: 35px;\r\n" + "    }\r\n"
					+ "    /*Media Queries ------------------------------ */\r\n" + "    \r\n"
					+ "    @media only screen and (max-width: 600px) {\r\n" + "      .email-body_inner,\r\n"
					+ "      .email-footer {\r\n" + "        width: 100% !important;\r\n" + "      }\r\n" + "    }\r\n"
					+ "    \r\n" + "    @media (prefers-color-scheme: dark) {\r\n" + "      body {\r\n"
					+ "        background-color: #333333 !important;\r\n" + "        color: #FFF !important;\r\n"
					+ "      }\r\n" + "      p,\r\n" + "      ul,\r\n" + "      ol,\r\n" + "      blockquote,\r\n"
					+ "      h1,\r\n" + "      h2,\r\n" + "      h3 {\r\n" + "        color: #FFF !important;\r\n"
					+ "      }\r\n" + "      .attributes_content,\r\n" + "      .discount {\r\n"
					+ "        background-color: #222 !important;\r\n" + "      }\r\n"
					+ "      .email-masthead_name {\r\n" + "        text-shadow: none !important;\r\n" + "      \r\n"
					+ "      \r\n" + "      }\r\n" + "    }\r\n" + "    </style>\r\n" + "    <!--[if mso]>\r\n"
					+ "    <style type=\"text/css\">\r\n" + "      .f-fallback  {\r\n"
					+ "        font-family: Arial, sans-serif;\r\n" + "      }\r\n" + "    </style>\r\n"
					+ "  <![endif]-->\r\n" + "  </head>\r\n" + "  <body>\r\n"

					+ "    <table class=\"email-wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "      <tr>\r\n" + "        <td align=\"center\">\r\n"
					+ "          <table class=\"email-content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "            <tr>\r\n" + "              <td class=\"email-masthead\">\r\n"
					+ "                <img src=\"https://firebasestorage.googleapis.com/v0/b/swiftcoffeeproject.appspot.com/o/Library%2Fic_launcher.png?alt=media&token=7325ddf8-4e34-4d2d-8179-3e4e53f0f663\" alt=\"Smiley face\" >\r\n"
					+ "              </td>\r\n" + "              \r\n" + "            </tr>\r\n"
					+ "                   <tr>\r\n"
					+ "              <td class=\"email-body\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "                <table class=\"email-body_inner\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\r\n"
					+ "                  <!-- Body content -->\r\n" + "                  <tr>\r\n"
					+ "                    <td class=\"content-cell\">\r\n"
					+ "                      <div class=\"f-fallback\">\r\n" + "                        <h1>Hi "
					+ user.getFirstName() + ",</h1>\r\n"
					+ "                        <p>Thanks for using Smarty. Your account has been created on "
					+ user.getCountry().getCode() + " " + user.getPhone() + "</p>\r\n"
					+ "                        <p>Cheers,\r\n" + "                          <br>The Smarty Team</p>\r\n"
					+ "                        <!-- Sub copy -->\r\n" + "                       \r\n"
					+ "                      </div>\r\n" + "                    </td>\r\n"
					+ "                  </tr>\r\n" + "                </table>\r\n" + "              </td>\r\n"
					+ "            </tr>\r\n" + "              </table>\r\n" + "        </td>\r\n" + "      </tr>\r\n"
					+ "    </table>\r\n" + "  </body>\r\n" + "</html>";
			mail.setContent(html);

			emailService.sendSimpleMessage(mail);
//Sending Email to Customer

			toArrayC[0] = user.getEmail();
			String[] ccArrayC = {};
			// String[] toArray = { "bisma.ayaz@benchmatrix.ca" };
			Mail mailC = new Mail();
			mailC.setFrom("no-reply@memorynotfound.com");
			mailC.setToArray(toArrayC);
			mailC.setCcArray(ccArrayC);
			mailC.setSubject("Account Confirmation Email");
			mailC.setContent(html);

			emailService.sendSimpleMessage(mailC);

		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}

	public void sendCancelInvoice(CustomerOrder order, MetaLanguage language,
			ItemLanguageRepository itemLanguageRepository) throws MessagingException, IOException {

		try {
			DecimalFormat df = new DecimalFormat("0.##");
			String t;
			if (order.getAddress() != null)
				t = order.getAddress().getAddress();
			else
				t = "Takeaway";

			String[] toArray = { "usama.rafiq@benchmatrix.ca", "irfan.fazli@benchmatrix.com",
					"farzeen.ahmed@benchmatrix.ca", "rohail.khan@benchmatrix.ca" };
			String[] ccArray = { "bisma.ayaz@benchmatrix.ca" };

			Mail mail = new Mail();
			mail.setFrom("no-reply@memorynotfound.com");
			mail.setToArray(toArray);
			mail.setCcArray(ccArray);
			mail.setSubject("Order has been Cancelled");
			String a = "";
			for (OrderItem orderItem : order.getOrderItemList()) {

				ItemLanguage itemLanguage = itemLanguageRepository.findByItemAndLanguage(orderItem.getItem(), language);
				a = a + "            <tr class=\"item\">\r\n" + " <td>\r\n" + "                   "
						+ orderItem.getItem().getId() + "\r\n" + "                </td>\r\n"
						+ "                <td>\r\n" + "                   " + itemLanguage.getTitle() + "\r\n"
						+ "                </td>\r\n" + "                \r\n" + "                <td>\r\n"
						+ orderItem.getQuantity() + "\r\n" + "                </td>\r\n" + "<td>\r\n"
						+ orderItem.getPrice() + "\r\n" + "                </td>\r\n" + "            </tr>\r\n"
						+ "            \r\n";
			}
			String html = "<!doctype html>\r\n" + "<html>\r\n" + "<head>\r\n" + "    <meta charset=\"utf-8\">\r\n"
					+ "    <title>Smarty</title>\r\n" + "    \r\n" + "    <style>\r\n" + "    .invoice-box {\r\n"
					+ "        max-width: 800px;\r\n" + "        margin: auto;\r\n" + "        padding: 30px;\r\n"
					+ "        border: 1px solid #eee;\r\n" + "        box-shadow: 0 0 10px rgba(0, 0, 0, .15);\r\n"
					+ "        font-size: 16px;\r\n" + "        line-height: 24px;\r\n"
					+ "        font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\r\n"
					+ "        color: #555;\r\n" + "    }\r\n" + "    \r\n" + "    .invoice-box table {\r\n"
					+ "        width: 100%;\r\n" + "        line-height: inherit;\r\n" + "        text-align: left;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .invoice-box table td {\r\n" + "        padding: 5px;\r\n"
					+ "        vertical-align: top;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .invoice-box table tr td:nth-child(2) {\r\n" + "        text-align: right;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .invoice-box table tr.top table td {\r\n" + "        padding-bottom: 20px;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .invoice-box table tr.top table td.title {\r\n"
					+ "        font-size: 45px;\r\n" + "        line-height: 45px;\r\n" + "        color: #333;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .invoice-box table tr.information table td {\r\n"
					+ "        padding-bottom: 40px;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .invoice-box table tr.heading td {\r\n" + "        background: #eee;\r\n"
					+ "        border-bottom: 1px solid #ddd;\r\n" + "        font-weight: bold;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .invoice-box table tr.details td {\r\n" + "        padding-bottom: 20px;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .invoice-box table tr.item td{\r\n"
					+ "        border-bottom: 1px solid #eee;\r\n" + "    }\r\n" + "    \r\n"
					+ "    .invoice-box table tr.item.last td {\r\n" + "        border-bottom: none;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .invoice-box table tr.total td:nth-child(2) {\r\n"
					+ "        border-top: 2px solid #eee;\r\n" + "        font-weight: bold;\r\n" + "    }\r\n"
					+ "    \r\n" + "    @media only screen and (max-width: 600px) {\r\n"
					+ "        .invoice-box table tr.top table td {\r\n" + "            width: 100%;\r\n"
					+ "            display: block;\r\n" + "            text-align: center;\r\n" + "        }\r\n"
					+ "        \r\n" + "        .invoice-box table tr.information table td {\r\n"
					+ "            width: 100%;\r\n" + "            display: block;\r\n"
					+ "            text-align: center;\r\n" + "        }\r\n" + "    }\r\n" + "    \r\n"
					+ "    /** RTL **/\r\n" + "    .rtl {\r\n" + "        direction: rtl;\r\n"
					+ "        font-family: Tahoma, 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\r\n"
					+ "    }\r\n" + "    \r\n" + "    .rtl table {\r\n" + "        text-align: right;\r\n" + "    }\r\n"
					+ "    \r\n" + "    .rtl table tr td:nth-child(2) {\r\n" + "        text-align: left;\r\n"
					+ "    }\r\n" + "    </style>\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n"
					+ "    <div class=\"invoice-box\">\r\n" + "        <table cellpadding=\"0\" cellspacing=\"0\">\r\n"
					+ "            <tr class=\"top\">\r\n" + "                <td colspan=\"2\">\r\n"
					+ "                    <table>\r\n" + "                        <tr>\r\n"
					+ "                            <td class=\"title\">\r\n"
					+ "                                <img src=\"https://firebasestorage.googleapis.com/v0/b/swiftcoffeeproject.appspot.com/o/Library%2Fic_launcher.png?alt=media&token=7325ddf8-4e34-4d2d-8179-3e4e53f0f663\" style=\"width:100%; max-width:300px;\">\r\n"
					+ "                            </td>\r\n" + "                            \r\n"
					+ "                            <td>\r\n" + "                                Invoice #: "
					+ order.getOrderNumber() + "<br>\r\n" + "                                Created: "
					+ order.getTsServer() + "/<br>\r\n" + "                                Due: " + order.getTsServer()
					+ "\r\n" + "                            </td>\r\n" + "                        </tr>\r\n"
					+ "                    </table>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
					+ "            \r\n" + "            <tr class=\"information\">\r\n"
					+ "                <td colspan=\"2\">\r\n" + "                    <table>\r\n"
					+ "                        <tr>\r\n" + "                            <td>\r\n" + t
					+ "                            </td>\r\n" + "                            \r\n"
					+ "                            <td>\r\n" + order.getUser().getFirstName()
					+ "                               \r\n"

					+ "\r\n" + "                            </td>\r\n" + "<td>\r\n" + order.getUser().getPhone()
					+ "                               \r\n"

					+ "\r\n" + "                            </td>" + "                        </tr>\r\n"
					+ "                    </table>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
					+ "            \r\n" + "            <tr class=\"heading\">\r\n" + "                <td>\r\n"
					+ "                    Payment Method\r\n" + "                </td>\r\n" + "                \r\n"
					+ "            </tr>\r\n" + "            \r\n" + "            <tr class=\"details\">\r\n"
					+ "                <td>\r\n" + "                    Cash On Delivery\r\n"
					+ "                </td>\r\n" + "                \r\n" + "                <td>\r\n"
					+ "                   \r\n" + "                </td>\r\n" + "            </tr>\r\n"
					+ "            \r\n" + "            <tr class=\"heading\">\r\n" + "                <td>\r\n"
					+ "                    Item ID\r\n" + "                </td>\r\n" + "                <td>\r\n"
					+ "                    Item Name\r\n" + "                </td>\r\n" + "                \r\n"
					+ "                <td>\r\n" + "                    Quantity\r\n" + "                </td>\r\n"
					+ "  <td>\r\n" + "\r\n" + "                   Price\r\n" + "\r\n" + "  </td>\r\n"
					+ "            </tr>\r\n" + "            \r\n" +

					a + "            <tr class=\"total\">\r\n" + "                <td></td>\r\n"
					+ "                \r\n" + "                <td>\r\n" + "                   Actual Amount: "
					+ df.format(order.getActualAmount()) + "\r\n" + "                </td>\r\n"
					+ "            </tr>\r\n" + "<tr class=\"total\">\r\n" + "                <td></td>\r\n"
					+ "                \r\n" + "                <td>\r\n" + "                   Discounted Amount: "
					+ df.format(order.getDiscountedAmount()) + "\r\n" + "                </td>\r\n"
					+ "            </tr>\r\n" + "<tr class=\"total\">\r\n" + "                <td></td>\r\n"
					+ "                \r\n" + "                <td>\r\n" + "                    Amount Payable: "
					+ df.format(order.getTotalAmount()) + "\r\n" + "                </td>\r\n" + "            </tr>\r\n"
					+ " <tr>\r\n" + "                <td>Additional Instructions: </td>\r\n" + "                \r\n"
					+ "              <td>" + order.getInstructionComment() + "</td>\r\n" + " </tr> \r\n"
					+ "        </table>\r\n" + "    </div>\r\n" + "</body>\r\n" + "</html>";
			mail.setContent(html);

			emailService.sendSimpleMessage(mail);
		} catch (Exception e) {
			log.warn("error sending mail", e.getMessage());
		}
	}

}