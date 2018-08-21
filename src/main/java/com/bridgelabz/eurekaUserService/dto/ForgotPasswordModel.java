package com.bridgelabz.eurekaUserService.dto;

/**
 * @author Chaitra Ankolekar
 * Purpose :pojo class
 */
public class ForgotPasswordModel
{
	private String newPassword;
	private String confirmPassword;

	/**
	 * @return String
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return String
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}