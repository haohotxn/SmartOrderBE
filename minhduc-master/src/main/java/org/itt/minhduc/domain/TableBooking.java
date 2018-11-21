package org.itt.minhduc.domain;

import java.io.Serializable;

public class TableBooking extends AbstractAuditingEntity implements Serializable{
	
	private String nameOfCustomer;
	
	private String phoneNumber;
	
	private boolean bookingStatus;
	
	


	public boolean isBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(boolean bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getNameOfCustomer() {
		return nameOfCustomer;
	}

	public void setNameOfCustomer(String nameOfCustomer) {
		this.nameOfCustomer = nameOfCustomer;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
