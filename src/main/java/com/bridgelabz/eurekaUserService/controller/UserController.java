package com.bridgelabz.eurekaUserService.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.eurekaUserService.dto.ForgotPasswordModel;
import com.bridgelabz.eurekaUserService.dto.Response;
import com.bridgelabz.eurekaUserService.exception.LoginExceptionHandler;
import com.bridgelabz.eurekaUserService.exception.ToDoException;
import com.bridgelabz.eurekaUserService.exception.UserExceptionHandler;
import com.bridgelabz.eurekaUserService.model.User;
import com.bridgelabz.eurekaUserService.services.UserService;
import com.bridgelabz.eurekaUserService.utility.Utility;
/**
 * @author Chaitra Ankolekar
 * Purpose :UserController with API
 */

@RestController
@RequestMapping(value="/users")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService ;
	
	Utility utility=new Utility();
	
	/**
	 * purpose:method to login
	 * @param checkUser
	 * @return response
	 * @throws ToDoException 
	 * @throws LoginExceptionHandling
	 */
	@RequestMapping(value = "/loginuser", method = RequestMethod.POST)
	public ResponseEntity<Response> login(@RequestBody User checkUser,HttpServletResponse resp)throws UserExceptionHandler, LoginExceptionHandler, ToDoException {
	
		logger.info("Logging User : {}", checkUser);
		System.out.println(checkUser.getEmailId());
		userService.login(checkUser.getEmailId(), checkUser.getPassword());
		String jwtToken = utility.createTokens(checkUser);
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Login successfull");
		resp.setHeader("token", jwtToken);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * purpose:method to register
	 * @param checkUser
	 * @return response
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException 
	 */
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public ResponseEntity<Response> register(@RequestBody User checkUser)		
			throws UserExceptionHandler, MessagingException, ToDoException {

		logger.info("Register user : {}", checkUser);
		userService.registerUser(checkUser);
		Response response = new Response();
		response.setMessage("Registration Successfull");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * @param token
	 * @return response
	 * @throws UserExceptionHandler
	 * @throws LoginExceptionHandler
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/activateacnt", method = RequestMethod.GET)
	public ResponseEntity<Response> activateAc(HttpServletRequest request) throws UserExceptionHandler, LoginExceptionHandler, ToDoException {
		
		logger.info("check the user activation"); 
		String token=request.getHeader("Token");
		System.out.println("tokens:"+token);
		userService.activateAc(token);
		Response response = new Response();
		response.setMessage("account activated successfully");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
		
	/**
	 * method to send link if user forgot the password
	 * @param CheckUser
	 * @return
	 * @throws MessagingException
	 * @throws UserExceptionHandler
	 * @throws LoginExceptionHandler 
	 * @throws ToDoException 
	 */
	@RequestMapping(value = "/forgotpwd/{email}", method = RequestMethod.GET)
	public ResponseEntity<Response> forgotPassword(@PathVariable String email)
			throws MessagingException, UserExceptionHandler, LoginExceptionHandler, ToDoException {

		logger.info("Reset the password");
		userService.forgotPassword(email);
		Response responseDTO = new Response();
		responseDTO.setMessage("send the user mailid to reset password");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	/**
	 * method to reset the password
	 * @param model
	 * @param 
	 * @return response
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
	public ResponseEntity<Response> resetPassword(@RequestBody ForgotPasswordModel model,HttpServletRequest req)
			throws ToDoException {
		String token=req.getHeader("Token");       
		userService.resetPassword(model, token);
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("password changed successfully!!!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public List<User> getAllUser()throws ToDoException {
		List<User> user=userService.getUsers();
		return user;
	}
}