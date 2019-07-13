package edu.dkravchuk.studentorder.domain;

import java.time.LocalDate;

public class Child extends Person {

	public Child(String surName, String givenName, String patronymic, LocalDate dateOfBirth) {
		super(surName, givenName, patronymic, dateOfBirth);
	}

	private String certificateNumber;
	private LocalDate issueDate;
	private RegisterOffice registerOffice;

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public RegisterOffice getRegisterOffice() {
		return registerOffice;
	}

	public void setRegisterOffice(RegisterOffice registerOffice) {
		this.registerOffice = registerOffice;
	}

}
