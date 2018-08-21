package com.bridgelabz.eurekaUserService.utility.email;

import javax.mail.MessagingException;

import com.bridgelabz.eurekaUserService.dto.MailModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :EmailService class
 */
public interface EmailServices {
	
	//public void sendEmail(String to, String subject, String body) throws MessagingException;
	void sendMail(MailModel mail) throws MessagingException;
}