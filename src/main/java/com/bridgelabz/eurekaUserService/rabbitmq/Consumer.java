package com.bridgelabz.eurekaUserService.rabbitmq;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.eurekaUserService.dto.MailModel;
import com.bridgelabz.eurekaUserService.utility.email.EmailServices;

/**
 * @author Chaitra Ankolekar
 * Purpose :Consumer class to send mail
 */
@Service
public class Consumer 
{
	@Autowired
	EmailServices emailService;
	@RabbitListener(queues="${jsa.rabbitmq.queue}")
	public void recivedMessage(MailModel mail) throws MessagingException 
	{
		System.out.println("Cjwsuiyukikuytyt");

		emailService.sendMail(mail);
		System.out.println("mail= "+mail);
	}

}