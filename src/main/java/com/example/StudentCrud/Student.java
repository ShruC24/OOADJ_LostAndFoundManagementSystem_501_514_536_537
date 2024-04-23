package com.example.StudentCrud;

public class Student {
    private String studentName;
    private String itemName;
    private Long contactNumber;
    private boolean lostItem;

    public Student(String studentName, String itemName, Long contactNumber, boolean lostItem) {
        this.studentName = studentName;
        this.itemName = itemName;
        this.contactNumber = contactNumber;
        this.lostItem = lostItem;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getItemName() {
        return itemName;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public boolean isLostItem() {
        return lostItem;
    }
}