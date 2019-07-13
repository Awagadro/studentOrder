package edu.dkravchuk.studentorder.validator;

import edu.dkravchuk.studentorder.domain.StudentOrder;
import edu.dkravchuk.studentorder.domain.wedding.AnswerWedding;

public class WeddingValidator {
	public AnswerWedding checkWedding(StudentOrder so) {
		System.out.println("Wedding запущен");
		AnswerWedding ans = new AnswerWedding();
		ans.success = true;
		return ans;
	}
}
