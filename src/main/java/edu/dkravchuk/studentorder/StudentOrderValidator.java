package edu.dkravchuk.studentorder;

import java.util.LinkedList;
import java.util.List;

import edu.dkravchuk.studentorder.domain.StudentOrder;
import edu.dkravchuk.studentorder.domain.children.AnswerChildren;
import edu.dkravchuk.studentorder.domain.register.AnswerCityRegister;
import edu.dkravchuk.studentorder.domain.student.AnswerStudent;
import edu.dkravchuk.studentorder.domain.wedding.AnswerWedding;
import edu.dkravchuk.studentorder.exception.CityRegisterException;
import edu.dkravchuk.studentorder.mail.MailSender;
import edu.dkravchuk.studentorder.validator.ChildrenValidator;
import edu.dkravchuk.studentorder.validator.CityRegisterValidator;
import edu.dkravchuk.studentorder.validator.StudentValidator;
import edu.dkravchuk.studentorder.validator.WeddingValidator;

public class StudentOrderValidator {
	private CityRegisterValidator cityRegisterVal;
	private WeddingValidator weddingVal;
	private ChildrenValidator childrenVal;
	private StudentValidator studentVal;
	private MailSender mailSender;

	public StudentOrderValidator() {
		cityRegisterVal = new CityRegisterValidator();
		weddingVal = new WeddingValidator();
		childrenVal = new ChildrenValidator();
		studentVal = new StudentValidator();
		mailSender = new MailSender();
	}

	public static void main(String[] args) throws CityRegisterException {

		StudentOrderValidator sov = new StudentOrderValidator();
		sov.checkAll();

	}

	public void checkAll() throws CityRegisterException {
		List<StudentOrder> soList = readStudentOrders();

		for (StudentOrder so : soList) {
			System.out.println();
			checkOneOrder(so);
		}

	}

	public List<StudentOrder> readStudentOrders() {
		List<StudentOrder> soList = new LinkedList<>();
		for (int c = 0; c < 5; c++) {
			StudentOrder so = SaveStudentOrder.buildStudentOrder(c);
			soList.add(so);
		}

		return soList;
	}

	public void checkOneOrder(StudentOrder so) throws CityRegisterException {
		AnswerCityRegister cityAnswer = checkCityRegister(so);
		// AnswerWedding wedAnswer = checkWedding(so);
		// AnswerChildren childAnswer = checkChildren(so);
		// AnswerStudent studentAnswer = checkStudent(so);
		// sendMail(so);
	}

	public void sendMail(StudentOrder so) {
		mailSender.sendMail(so);
		;
	}

	public AnswerStudent checkStudent(StudentOrder so) {
		return studentVal.checkStudent(so);
	}

	public AnswerChildren checkChildren(StudentOrder so) {
		return childrenVal.checkChildren(so);
	}

	public AnswerWedding checkWedding(StudentOrder so) {
		return weddingVal.checkWedding(so);
	}

	public AnswerCityRegister checkCityRegister(StudentOrder so) {
		return cityRegisterVal.checkCityRegister(so);
	}

}
