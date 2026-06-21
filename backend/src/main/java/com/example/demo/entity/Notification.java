package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    private String title;

    @Column(length = 1000)
    private String message;

    private Boolean isRead = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Notification() {
    	
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}