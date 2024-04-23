//Student.java (under domain)
package com.example.StudentCrud.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String studentname;
	private String itemname;
	private Long contactnumber;
    @Column(name = "lost_item") // Specify the column name in the database
	private boolean lostItem;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Student(Long id, String studentname, String itemname, Long contactnumber,boolean lostItem) {
		super();
		this.id = id;
		this.studentname = studentname;
		this.itemname = itemname;
		this.contactnumber = contactnumber;
		this.lostItem=lostItem;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStudentname() {
		return studentname;
	}
	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public Long getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(Long contactnumber) {
		this.contactnumber = contactnumber;
	}
    public boolean isLostItem() {
		return lostItem;
	}
	public void setLostItem(boolean lostItem) {
		this.lostItem = lostItem;
	}
	
	
}