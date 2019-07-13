package edu.dkravchuk.studentorder.validator.register;

import edu.dkravchuk.studentorder.domain.Person;
import edu.dkravchuk.studentorder.domain.register.CityRegisterResponse;
import edu.dkravchuk.studentorder.exception.CityRegisterException;
import edu.dkravchuk.studentorder.exception.TransportException;

public class RealCityRegisterChecker implements CityRegisterChecker {
	public CityRegisterResponse checkPerson(Person person) throws CityRegisterException, TransportException {

		return null;
	}
}
