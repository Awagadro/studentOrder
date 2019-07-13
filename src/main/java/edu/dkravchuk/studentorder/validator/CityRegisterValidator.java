package edu.dkravchuk.studentorder.validator;

import edu.dkravchuk.studentorder.domain.Child;
import edu.dkravchuk.studentorder.domain.Person;
import edu.dkravchuk.studentorder.domain.StudentOrder;
import edu.dkravchuk.studentorder.domain.register.AnswerCityRegister;
import edu.dkravchuk.studentorder.domain.register.AnswerCityRegisterItem;
import edu.dkravchuk.studentorder.domain.register.CityRegisterResponse;
import edu.dkravchuk.studentorder.exception.CityRegisterException;
import edu.dkravchuk.studentorder.exception.TransportException;
import edu.dkravchuk.studentorder.validator.register.CityRegisterChecker;
import edu.dkravchuk.studentorder.validator.register.FakeCityRegisterChecker;

public class CityRegisterValidator {
	private static final String IN_CODE = "NO_GRN";

	private CityRegisterChecker personChecker;

	public CityRegisterValidator() {
		personChecker = new FakeCityRegisterChecker();
		// personChecker = new RealCityRegisterChecker();
	}

	public AnswerCityRegister checkCityRegister(StudentOrder so) {
		AnswerCityRegister ans = new AnswerCityRegister();

		ans.addItem(checkPerson(so.getHusband()));
		ans.addItem(checkPerson(so.getWife()));

		for (Child child : so.getChildren()) {
			ans.addItem(checkPerson(child));
		}

		return ans;
	}

	private AnswerCityRegisterItem checkPerson(Person person) {
		AnswerCityRegisterItem.CityStatus status = null;
		AnswerCityRegisterItem.CityError error = null;
		try {
			CityRegisterResponse tmp = personChecker.checkPerson(person);
			status = tmp.isExisting() ? AnswerCityRegisterItem.CityStatus.YES : AnswerCityRegisterItem.CityStatus.NO;
		} catch (CityRegisterException ex) {
			ex.printStackTrace(System.out);
			status = AnswerCityRegisterItem.CityStatus.ERROR;
			error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
		} catch (TransportException ex) {
			ex.printStackTrace(System.out);
			status = AnswerCityRegisterItem.CityStatus.ERROR;
			error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			status = AnswerCityRegisterItem.CityStatus.ERROR;
			error = new AnswerCityRegisterItem.CityError(IN_CODE, ex.getMessage());
		}

		AnswerCityRegisterItem ans = new AnswerCityRegisterItem(status, person, error);
		return ans;

	}
}
