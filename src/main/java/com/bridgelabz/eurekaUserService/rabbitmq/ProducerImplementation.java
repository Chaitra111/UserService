package com.bridgelabz.eurekaUserService.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bridgelabz.eurekaUserService.dto.MailModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :ProducerImplementation
 */
@Service
public class ProducerImplementation implements Producer {

	@Autowired 
	private AmqpTemplate amqpTemplate;
	
	@Value("${jsa.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${jsa.rabbitmq.routingkey}")
	private String routingKey;
	
	@Override
	public void produceMail(MailModel mail){
		System.out.println("jwsuiyukikuytyt");
		amqpTemplate.convertAndSend(exchange, routingKey, mail);
		System.out.println("Email sent");
	}
}