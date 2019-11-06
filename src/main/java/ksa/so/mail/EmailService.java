package ksa.so.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.itextpdf.text.DocumentException;

import ksa.so.domain.MetaLanguage;
import ksa.so.domain.MetaStatus;
import ksa.so.excel.ExcelGenerator;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.repositories.SalesReportRepository;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	MetaLanguageRepository languageRepository;

	@Autowired
	SalesReportRepository salesReportRepository;

	@Autowired
	MetaStatusRepository statusRepository;
	@Autowired
	private SpringTemplateEngine templateEngine;

	public void sendSimpleMessage(Mail mail) throws MessagingException, IOException, DocumentException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");

		emailSender.send(message);

	}

	public void sendRegistedUserMessage(Mail mail) throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");
		ByteArrayInputStream inputStream = new ExcelGenerator().generateExcelReportOfRegisteredUsers(language,
				salesReportRepository, status);

		helper.addAttachment("RegisteredUserReport", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		emailSender.send(message);

	}

	public void sendConsolidatedReport(Mail mail) throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");
		ByteArrayInputStream inputStream = new ExcelGenerator().generateExcelReportA(language, salesReportRepository, status);

		helper.addAttachment("RegisteredUserReport", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		emailSender.send(message);

	}
	public void sendItemSalesReport(Mail mail) throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");
		ByteArrayInputStream inputStream = new ExcelGenerator().generateExcelReportItemSales(language, salesReportRepository, status);

		helper.addAttachment("RegisteredUserReport", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		emailSender.send(message);

	}

	
	public void sendDailySalesReport(Mail mail) throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");
		ByteArrayInputStream inputStream = new ExcelGenerator().generateExcelReport(language, salesReportRepository,
				status);

		helper.addAttachment("DailySalesReport", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		emailSender.send(message);

	}

	public void sendCustomerFeedbackReport(Mail mail) throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");
		ByteArrayInputStream inputStream = new ExcelGenerator().generateExcelReportOfCustomerfeedback(language,
				salesReportRepository);

		helper.addAttachment("CustomerFeedbackReport", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		emailSender.send(message);

	}

	public void sendDiscountedAmountReport(Mail mail) throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");
		ByteArrayInputStream inputStream = new ExcelGenerator().generateDailyDiscountedAmountReport(language,
				salesReportRepository);

		helper.addAttachment("DiscountedAmountReport", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		emailSender.send(message);

	}
	

	public void sendReportB(Mail mail) throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");
		ByteArrayInputStream inputStream = new ExcelGenerator().generateReprtB(language,
				salesReportRepository, status);

		helper.addAttachment("RegisteredUserReport", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		emailSender.send(message);

	}
	
	public void sendReportE(Mail mail) throws MessagingException, IOException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setSubject(mail.getSubject());
		helper.setText(mail.getContent(), true);
		// helper.setTo(mail.getTo());
		helper.setTo(mail.getToArray());
		helper.setCc(mail.getCcArray());
		helper.setFrom(mail.getFrom());

		MetaLanguage language = languageRepository.findByCode("LANG0001");
		MetaStatus status = statusRepository.findByCode("STA104");
		ByteArrayInputStream inputStream = new ExcelGenerator().generateReprtE(language,
				salesReportRepository, status);

		helper.addAttachment("RegisteredUserReport", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		emailSender.send(message);

	}
	

}