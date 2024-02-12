package com.webapp.request;

public class UserRequest {

	private Long userId;

	private String userName;

	private String emailId;

	private String userPassword;

	private String mobileNumber;

	private String isActive;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "UserRequest [userId=" + userId + ", userName=" + userName + ", emailId=" + emailId + ", userPassword="
				+ userPassword + ", mobileNumber=" + mobileNumber + ", isActive=" + isActive + "]";
	}

}
