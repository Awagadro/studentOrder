package edu.dkravchuk.studentorder.domain;

import java.time.LocalDate;

public class Adult extends Person {
	public Adult(String surName, String givenName, String patronymic, LocalDate dateOfBirth) {
		super(surName, givenName, patronymic, dateOfBirth);
	}

	public Adult() {
		super();
	}

	private String passportSeria;
	private String passportNumber;

	private LocalDate issueDate;
	private PassportOffice passportOffice;

	private University university;
	private String studentId;

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getPassportSeria() {
		return passportSeria;
	}

	public void setPassportSeria(String passportSeria) {
		this.passportSeria = passportSeria;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public PassportOffice getPassportOffice() {
		return passportOffice;
	}

	public void setPassportOffice(PassportOffice passportOffice) {
		this.passportOffice = passportOffice;
	}

}
