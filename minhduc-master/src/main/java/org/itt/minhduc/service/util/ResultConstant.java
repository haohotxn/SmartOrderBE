package org.itt.minhduc.service.util;

public interface ResultConstant {

	/**
	 * 2xx Success This class of status codes indicates the action requested by
	 * the client was received, understood, accepted, and processed
	 * successfully.
	 */
	Integer CODE_SUCCESS = 200;
	String MESSAGE_SUCCESS = "success";
	
	/**
	 * 3xx Redirection This class of status code indicates the client must take
	 * additional action to complete the request.
	 */

	/**
	 * 4xx Client errors This class of status code is intended for situations in
	 * which the error seems to have been caused by the client.
	 */
	Integer CODE_INVALID_PARAM = 400001;
	String MESSAGE_INVALID_PARAM = "param is null or invalid";
	
	
	/**
	 * 5xx Server errors The server failed to fulfill an apparently valid
	 * request.
	 */
	Integer CODE_ERROR = 500;
	String MESSAGE_ERROR = "internal server error";
	
}
