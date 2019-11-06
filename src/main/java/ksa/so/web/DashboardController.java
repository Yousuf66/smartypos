package ksa.so.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ksa.so.mail.EmailService;
import ksa.so.mail.Mail;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	
	private static Logger log = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
    private EmailService emailService;
	
	//@Override
	@RequestMapping(value = "/srs_das_001", method = RequestMethod.POST, consumes = "text/plain", produces={"application/json; charset=UTF-8"})
	public void signup(@RequestBody String param, HttpServletRequest req) throws Exception {
    //public void run(ApplicationArguments applicationArguments) throws Exception {
		try{
        log.info("Spring Mail - Sending Email Attachment Configuration Example");

        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo("marium.hashmi@benchmatrix.ca");
        mail.setSubject("Sending Email Attachment Configuration Example");
        mail.setContent("This tutorial demonstrates how to send an email with attachment using Spring Framework.");

        emailService.sendSimpleMessage(mail);
		}
		catch (Exception e){
			log.warn("error sending mail", e.getMessage());
		}
    }

}
