package org.itt.minhduc.domain;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "table_booking")
public class TableBooking extends AbstractAuditingEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    private String id;
	
	@Field("name_of_customer")
	private String nameOfCustomer;
	
	@Field("phone_number")
	private String phoneNumber;
	
	@Field("booking_status")
	private boolean bookingStatus;
	
	@Field("booking_time")
	private Instant bookingTime;
	

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	
	public Instant getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Instant bookingTime) {
		this.bookingTime = bookingTime;
	}

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
