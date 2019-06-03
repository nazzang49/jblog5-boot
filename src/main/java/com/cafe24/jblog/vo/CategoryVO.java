package com.cafe24.jblog.vo;

import java.sql.Date;

public class CategoryVO {

	private Long no;
	private String subject;
	private String description;
	private Date regDate;
	private String userId;
	//카테고리 내 총 게시물 수
	private Long postCount;
	
	public Long getPostCount() {
		return postCount;
	}
	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "CategoryVO [no=" + no + ", subject=" + subject + ", description=" + description + ", regDate=" + regDate
				+ ", userId=" + userId + ", postCount=" + postCount + "]";
	}
	
}
