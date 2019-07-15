package edu.dkravchuk.studentorder.dao;

import java.time.LocalDate;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.dkravchuk.studentorder.domain.Address;
import edu.dkravchuk.studentorder.domain.Adult;
import edu.dkravchuk.studentorder.domain.Child;
import edu.dkravchuk.studentorder.domain.PassportOffice;
import edu.dkravchuk.studentorder.domain.RegisterOffice;
import edu.dkravchuk.studentorder.domain.Street;
import edu.dkravchuk.studentorder.domain.StudentOrder;
import edu.dkravchuk.studentorder.domain.University;
import edu.dkravchuk.studentorder.exception.DaoException;

public class StudentOrderDaoImplTest {
	@BeforeClass
	public static void startUp() throws Exception {
		DBInit.startUp();
	}

	@Test
	public void saveStudentOrder() throws DaoException {
		StudentOrder so = buildStudentOrder(10);
		Long id = new StudentOrderDaoImpl().saveStudentOrder(so);

	}

	@Test(expected = DaoException.class)
	public void saveStudentOrderError() throws DaoException {
		StudentOrder so = buildStudentOrder(10);
		so.getHusband().setSurName(null);
		Long id = new StudentOrderDaoImpl().saveStudentOrder(so);

	}

	@Test
	public void getStudentOrder() throws DaoException {
		List<StudentOrder> list = new StudentOrderDaoImpl().getStudentOrders();

	}

	public StudentOrder buildStudentOrder(long id) {
		StudentOrder so = new StudentOrder();
		so.setStudentOrderId(id);
		so.setMarriageCertificateId("" + 123456000 + id);
		so.setMarriageDate(LocalDate.of(2016, 7, 4));
		RegisterOffice ro1 = new RegisterOffice(1L, "", "");
		so.setRegisterOffice(ro1);
		Street street = new Street(1L, "First street");
		Address address = new Address("195000", street, "12", "А", "142");

		// Муж
		Adult husband = new Adult("Петров", "Виктор", "Сергеевич", LocalDate.of(1997, 8, 24));
		husband.setPassportSeria("" + (1000 + id));
		husband.setPassportNumber("" + (100000 + id));
		husband.setIssueDate(LocalDate.of(2017, 9, 15));
		PassportOffice po1 = new PassportOffice(1L, "", "");
		husband.setPassportOffice(po1);
		husband.setStudentId("" + (100000 + id));// Remove
		husband.setStudentId("HH12345");
		husband.setAddress(address);
		husband.setUniversity(new University(2L, ""));
		so.setHusband(husband);

		// Жена
		Adult wife = new Adult("Петрова", "Вероника", "Алексеевна", LocalDate.of(1998, 8, 24));
		wife.setPassportSeria("" + (2000 + id));
		wife.setPassportNumber("" + (200000 + id));
		wife.setIssueDate(LocalDate.of(2018, 9, 15));
		PassportOffice po2 = new PassportOffice(2L, "", "");
		wife.setPassportOffice(po2);
		wife.setStudentId("" + (200000 + id));// Remove
		wife.setStudentId("WW12345");
		wife.setAddress(address);
		wife.setUniversity(new University(1L, ""));
		so.setWife(wife);

		// Ребенок1
		Child child1 = new Child("Петрова", "Ирина", "Викторовна", LocalDate.of(2018, 8, 29));
		child1.setCertificateNumber("" + (300000 + id));
		child1.setIssueDate(LocalDate.of(2018, 9, 7));
		child1.setRegisterOffice(so.getRegisterOffice());
		child1.setAddress(address);
		so.addChild(child1);

		// Ребенок2
		Child child2 = new Child("Петров", "Евгений", "Викторович", LocalDate.of(2018, 6, 29));
		child2.setCertificateNumber("" + (400000 + id));
		child2.setIssueDate(LocalDate.of(2018, 6, 30));
		child2.setRegisterOffice(so.getRegisterOffice());
		child2.setAddress(address);
		so.addChild(child2);

		return so;

	}
}
