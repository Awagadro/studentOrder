package edu.dkravchuk.studentorder.dao;

import java.util.List;

import edu.dkravchuk.studentorder.domain.CountryArea;
import edu.dkravchuk.studentorder.domain.PassportOffice;
import edu.dkravchuk.studentorder.domain.RegisterOffice;
import edu.dkravchuk.studentorder.domain.Street;
import edu.dkravchuk.studentorder.exception.DaoException;

public interface DictionaryDao {
	List<Street> findStreets(String pattern) throws DaoException;

	List<PassportOffice> findPassportOffices(String areaId) throws DaoException;

	List<RegisterOffice> findRegisterOffices(String areaId) throws DaoException;
	
	List<CountryArea> findAreas(String areaId) throws DaoException;
}
