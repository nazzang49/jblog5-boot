package com.cafe24.jblog.vo;

public class BlogVO {

	private String userName;
	private String userId;
	private String title;
	private String logo;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Override
	public String toString() {
		return "BlogVO [userName=" + userName + ", userId=" + userId + ", title=" + title + ", logo=" + logo + "]";
	}

}
