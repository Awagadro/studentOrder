package edu.dkravchuk.studentorder.validator;

import edu.dkravchuk.studentorder.domain.StudentOrder;
import edu.dkravchuk.studentorder.domain.student.AnswerStudent;

public class StudentValidator {
	public AnswerStudent checkStudent(StudentOrder so) {
		System.out.println("Студенты проверяются");
		AnswerStudent ans = new AnswerStudent();
		ans.success = true;
		return ans;
	}
}
