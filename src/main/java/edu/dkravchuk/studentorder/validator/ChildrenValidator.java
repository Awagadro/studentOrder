package edu.dkravchuk.studentorder.validator;

import edu.dkravchuk.studentorder.domain.StudentOrder;
import edu.dkravchuk.studentorder.domain.children.AnswerChildren;

public class ChildrenValidator {
	public AnswerChildren checkChildren(StudentOrder so) {
		System.out.println("Children check is running");
		AnswerChildren ans = new AnswerChildren();
		ans.success = true;
		return ans;
	}
}
