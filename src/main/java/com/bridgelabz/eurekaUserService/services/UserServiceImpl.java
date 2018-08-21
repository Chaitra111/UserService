package com.bridgelabz.eurekaUserService.services;

import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.eurekaUserService.dto.ForgotPasswordModel;
import com.bridgelabz.eurekaUserService.dto.MailModel;
import com.bridgelabz.eurekaUserService.exception.LoginExceptionHandler;
import com.bridgelabz.eurekaUserService.exception.ToDoException;
import com.bridgelabz.eurekaUserService.exception.UserExceptionHandler;
import com.bridgelabz.eurekaUserService.model.User;
import com.bridgelabz.eurekaUserService.rabbitmq.Producer;
import com.bridgelabz.eurekaUserService.repository.UserRepository;
import com.bridgelabz.eurekaUserService.utility.Messages;
import com.bridgelabz.eurekaUserService.utility.PreConditions;
import com.bridgelabz.eurekaUserService.utility.Utility;
import com.bridgelabz.eurekaUserService.utility.email.EmailServices;

import io.jsonwebtoken.Claims;

/**
 * @author Chaitra Ankolekar
 * Purpose :Implementation for all the api's in services
 */

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	EmailServices emailService;
	@Autowired
	Producer producer;
	
	@Autowired
	Messages messages;

	@Autowired
	MailModel mailDto;

	public static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	Utility utility = new Utility();
	ForgotPasswordModel password = new ForgotPasswordModel();
	User users = new User();

	/**
	 * purpose : method to login in to page
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws ToDoException 
	 * @throws UserExceptionHandler 
	 * @throws LoginExceptionHandling
	 */
	@Override
	public void login(String emailId, String password) throws ToDoException  {
	
		Optional<User> user = repo.findByemailId(emailId);
		Optional<User> u=repo.findById(emailId);
		System.out.println(u);
		System.out.println(emailId);
		
		//PreConditions.isPresentInDb(user.isPresent(), messages.get("12"));
		if (encoder.encode(user.get().getPassword()).equals(password)) {
			logger.info("looged in sucessfully!! continue ");
		}
	}

	/**
	 * purpose:method to register in to a page
	 * @param user
	 * @return boolean
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException 
	 */
	@Override
	public void registerUser(User user) throws UserExceptionHandler, MessagingException, ToDoException {
	
		Optional<User> checkUser = repo.findById(user.getPassword());
		PreConditions.isPresentInDb(!checkUser.isPresent(), messages.get("7"));
		user.setEmailId(user.getEmailId());
		user.setUserName(user.getUserName());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setPhoneNumber(user.getPhoneNumber());
		user.setActivate("false");
		repo.save(user);
		repo.findById(user.getEmailId());
		String token = utility.createTokens(user);
		System.out.println(token);
		mailDto.setToMailAddress(user.getEmailId());
		System.out.println(user.getEmailId());
		mailDto.setSubject("Hi " + user.getUserName());
		mailDto.setBody("Activation link:" +messages.get("6") + token);
		// emailService.sendMail(mailDto);
		producer.produceMail(mailDto);
		logger.info("mail is sent to emailid");
	}

	/**
	 * method to activate the account
	 * @param jwt
	 * @throws UserExceptionHandler 
	 * @throws ToDoException 
	 */
	@Override		
	public void activateAc(String jwt) throws LoginExceptionHandler, UserExceptionHandler, ToDoException {

		Claims claims = utility.parseJwt(jwt);
		System.out.println("------------");
		System.out.println(claims.getIssuer());
		Optional<User> user = repo.findById(claims.getIssuer());
		//System.out.println(claims.getIssuer());
		System.out.println(claims.getId());
	    System.out.println(user);
		user.get().setActivate("true");
		repo.save(user.get());
	}
	/**
	 * @param fp
	 * @param tokenJwt
	 * @throws ToDoException
	 */
	/*@Override
	public void resetPassword(ForgotPasswordModel fp, String tokenJwt) throws ToDoException {

		logger.info("set the password");
		System.out.println(tokenJwt);
		if (!fp.getNewPassword().equals(fp.getNewPassword())) {
			throw new ToDoException(messages.get("10"));
		}
		Claims claims = utility.parseJwt(tokenJwt);
		Optional<User> checkUser = repo.findById(claims.getId());
		System.out.println(claims.getId());
		System.out.println(checkUser);
		//User user = checkUser.get();
		checkUser.get().setPassword(encoder.encode(fp.getConfirmPassword()));
		repo.save(checkUser.get());
		logger.info("password is set");
}*/@Override
	public void resetPassword(ForgotPasswordModel f, String tokenJwt) throws ToDoException {
		System.out.println(tokenJwt);
		if (!f.getNewPassword().equals(f.getNewPassword())) {
			throw new ToDoException("passwords mismatch");
		}
		Claims claims = utility.parseJwt(tokenJwt);

		Optional<User> checkUser = repo.findById(claims.getId());
		System.out.println(claims.getId());
		User user = checkUser.get();
		user.setPassword(encoder.encode(f.getConfirmPassword()));
		repo.save(user);
	}

	/**
	 * @param emailId
	 * @throws MessagingException
	 * @throws UserExceptionHandler 
	 * @throws LoginExceptionHandler 
	 * @throws ToDoException 
	 */
	@Override
	public void forgotPassword(String emailId) throws MessagingException, LoginExceptionHandler, UserExceptionHandler, ToDoException {
					
		String jwtToken = utility.createToken(emailId);
		Optional<User> optionalUser = repo.findByemailId(emailId);
		PreConditions.isPresentInDb(optionalUser.isPresent(), messages.get("12"));
		mailDto.setToMailAddress(emailId);
		mailDto.setSubject("recover your password");
		mailDto.setBody("recover your password by clicking :" + messages.get("8") + jwtToken);
		producer.produceMail(mailDto);
		logger.info("check mail to reset the password");
	}
	
	@Override
	public List<User> getUsers(){
		
		List<User> user=repo.findAll();
		return user;
	}
}