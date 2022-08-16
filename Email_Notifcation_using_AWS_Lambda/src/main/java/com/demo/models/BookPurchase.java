package com.demo.models;

import java.time.LocalDateTime;
import java.util.Date;

public class BookPurchase {
	private int purchaseId;
	
	private String PaymentId ;
	private int bookId;
	private String name;
	private String emailId;

	private LocalDateTime date;
	
	public String getPaymentId() {
		return PaymentId;
	}
	public void setPaymentId(String paymentId) {
		this.PaymentId =paymentId;

	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "BookPurchase [purchaseId=" + purchaseId + ", PaymentId=" + PaymentId + ", bookId=" + bookId + ", name="
				+ name + ", emailId=" + emailId + ", date=" + date + "]";
	}
	

}
