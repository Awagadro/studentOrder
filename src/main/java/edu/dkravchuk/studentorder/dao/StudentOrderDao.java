package edu.dkravchuk.studentorder.dao;

import java.util.List;

import edu.dkravchuk.studentorder.domain.StudentOrder;
import edu.dkravchuk.studentorder.exception.DaoException;

public interface StudentOrderDao {

	Long saveStudentOrder(StudentOrder so) throws DaoException;
	
	List <StudentOrder> getStudentOrders() throws DaoException;
}
