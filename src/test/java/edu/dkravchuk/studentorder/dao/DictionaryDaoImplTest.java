package edu.dkravchuk.studentorder.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.dkravchuk.studentorder.domain.CountryArea;
import edu.dkravchuk.studentorder.domain.PassportOffice;
import edu.dkravchuk.studentorder.domain.RegisterOffice;
import edu.dkravchuk.studentorder.domain.Street;
import edu.dkravchuk.studentorder.exception.DaoException;

public class DictionaryDaoImplTest {

	private static final Logger logger = LoggerFactory.getLogger(DictionaryDaoImplTest.class);

	@BeforeClass
	public static void startUp() throws Exception {
		DBInit.startUp();
	}

	@Test
	public void testStreet() throws DaoException {
		logger.debug("TEST");
		List<Street> d = new DictionaryDaoImpl().findStreets("про");
		Assert.assertTrue(d.size() == 2);
	}

	@Test
	public void testPassportOffice() throws DaoException {
		List<PassportOffice> po = new DictionaryDaoImpl().findPassportOffices("010020000000");
		Assert.assertTrue(po.size() == 2);
	}

	@Test
	public void testRegisterOffice() throws DaoException {
		List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffices("010010000000");
		Assert.assertTrue(ro.size() == 2);
	}

	@Test
	public void testArea() throws DaoException {
		List<CountryArea> ca1 = new DictionaryDaoImpl().findAreas("");
		Assert.assertTrue(ca1.size() == 2);
		List<CountryArea> ca2 = new DictionaryDaoImpl().findAreas("020000000000");
		Assert.assertTrue(ca2.size() == 2);
		List<CountryArea> ca3 = new DictionaryDaoImpl().findAreas("020010000000");
		Assert.assertTrue(ca3.size() == 2);
		List<CountryArea> ca4 = new DictionaryDaoImpl().findAreas("020010010000");
		Assert.assertTrue(ca4.size() == 2);
	}
}