package com.bridgelabz.eurekaUserService.services;

import java.util.List;

import javax.mail.MessagingException;

import com.bridgelabz.eurekaUserService.dto.ForgotPasswordModel;
import com.bridgelabz.eurekaUserService.exception.LoginExceptionHandler;
import com.bridgelabz.eurekaUserService.exception.ToDoException;
import com.bridgelabz.eurekaUserService.exception.UserExceptionHandler;
import com.bridgelabz.eurekaUserService.model.User;

/**
 * @author Chaitra Ankolekar
 * Purpose :NoteService to perform CRUD operation on user
 */
public interface UserService{
	
	public void login(String emailId, String password) throws ToDoException ;

	void registerUser(User user) throws UserExceptionHandler, MessagingException, ToDoException;

	void activateAc(String jwt) throws LoginExceptionHandler, UserExceptionHandler, ToDoException;

	void forgotPassword(String emailId)
			throws MessagingException, LoginExceptionHandler, UserExceptionHandler, ToDoException;

	void resetPassword(ForgotPasswordModel fp, String tokenJwt) throws ToDoException;

	public List<User> getUsers();
}