package com.blog.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Blog")
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bid;
	@Column(length = 1000)
	private String description;
	
	

	@ManyToOne
	private User user;

	
	private Date date;
	public int getBid() {
		return bid;
	}

	public Blog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Blog(int bid, String description, User user,Date date) {
		super();
		this.bid = bid;
		this.description = description;
		this.user = user;
		this.date=date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
