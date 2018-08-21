package com.bridgelabz.eurekaUserService.rabbitmq;

import com.bridgelabz.eurekaUserService.dto.MailModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :Producer to receive mail
 */
public interface Producer 
	{
		void produceMail(MailModel model);

	}