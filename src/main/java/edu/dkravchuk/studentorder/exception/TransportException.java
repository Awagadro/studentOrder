package edu.dkravchuk.studentorder.exception;

public class TransportException extends Exception {

	public TransportException() {
	}

	public TransportException(String messege, Throwable cause) {
		super(messege, cause);
	}

	public TransportException(String messege) {
		super(messege);
	}

}
