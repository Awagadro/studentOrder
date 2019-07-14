package edu.dkravchuk.studentorder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.dkravchuk.studentorder.config.Config;
import edu.dkravchuk.studentorder.domain.Address;
import edu.dkravchuk.studentorder.domain.Adult;
import edu.dkravchuk.studentorder.domain.Child;
import edu.dkravchuk.studentorder.domain.PassportOffice;
import edu.dkravchuk.studentorder.domain.Person;
import edu.dkravchuk.studentorder.domain.RegisterOffice;
import edu.dkravchuk.studentorder.domain.Street;
import edu.dkravchuk.studentorder.domain.StudentOrder;
import edu.dkravchuk.studentorder.domain.StudentOrderStatus;
import edu.dkravchuk.studentorder.domain.University;
import edu.dkravchuk.studentorder.exception.DaoException;

public class StudentOrderDaoImpl implements StudentOrderDao {

	private static final String INSERT_ODER = "INSERT INTO public.jc_student_order("
			+ " student_order_status, student_order_date, "
			+ "h_sur_name, h_given_name, h_patronymic, h_date_of_birth, h_passport_seria, h_passport_number, h_passport_date, h_passport_office_id, h_post_index, h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, "
			+ "w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_seria, w_passport_number, w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number, "
			+ "certificate_id, register_office_id, marriage_date)"
			+ "	VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String INSERT_CHILD = "INSERT INTO public.jc_student_child("
			+ "	student_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth, c_certificate_number, c_certificate_date, c_register_office_id, c_post_index, c_street_code, c_building, c_extension, c_apartment)\r\n"
			+ "	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private static final String SELECT_ORDERS = "SELECT so.*, ro.r_office_area_id, ro.r_office_name, "
			+ "po_h.p_office_area_id as h_p_office_area_id, " + "po_h.p_office_name as h_p_office_name, "
			+ "po_w.p_office_area_id as w_p_office_area_id, " + "po_w.p_office_name as w_p_office_name "
			+ "FROM jc_student_order so "
			+ "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id "
			+ "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id "
			+ "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id "
			+ "WHERE student_order_status = ? ORDER BY student_order_date LIMIT ?";

	private static final String SELECT_CHILD = "SELECT soc.*, ro.r_office_area_id, ro.r_office_name "
			+ "FROM jc_student_child soc "
			+ "INNER JOIN jc_register_office ro ON ro.r_office_id = soc.c_register_office_id "
			+ "WHERE soc.student_order_id IN ";

	private static final String SELECT_ORDERS_FULL = "SELECT so.*, ro.r_office_area_id, ro.r_office_name, "
			+ "po_h.p_office_area_id as h_p_office_area_id, " + "po_h.p_office_name as h_p_office_name, "
			+ "po_w.p_office_area_id as w_p_office_area_id, " + "po_w.p_office_name as w_p_office_name, "
			+ "soc.*, ro_c.r_office_area_id, ro_c.r_office_name " + "FROM jc_student_order so "
			+ "INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id "
			+ "INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id "
			+ "INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id "
			+ "INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id "
			+ "INNER JOIN jc_register_office ro_c ON ro.r_office_id = soc.c_register_office_id "
			+ "WHERE student_order_status = ? ORDER BY so.student_order_id LIMIT ?";

	private Connection getConnection() throws SQLException {
		return ConnectionBuilder.getConnection();

	}

	@Override
	public Long saveStudentOrder(StudentOrder so) throws DaoException {
		Long result = -1L;

		try (Connection con = getConnection();
				PreparedStatement stmt = con.prepareStatement(INSERT_ODER, new String[] { "student_order_id" })) {

			con.setAutoCommit(false);
			try {
				// Header
				stmt.setInt(1, StudentOrderStatus.START.ordinal());
				stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
				// Husband - Wife
				setParamsForAdult(stmt, 3, so.getHusband());
				setParamsForAdult(stmt, 18, so.getWife());
				// Marriage
				stmt.setString(33, so.getMarriageCertificateId());
				stmt.setLong(34, so.getRegisterOffice().getOfficeId());
				stmt.setDate(35, java.sql.Date.valueOf(so.getMarriageDate()));

				stmt.executeUpdate();

				ResultSet gkRs = stmt.getGeneratedKeys();
				if (gkRs.next()) {
					result = gkRs.getLong(1);
				}

				saveChildren(con, so, result);

				con.commit();

			} catch (SQLException e) {
				con.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return result;
	}

	private void saveChildren(Connection con, StudentOrder so, Long soId) throws SQLException {
		try (PreparedStatement stmt = con.prepareStatement(INSERT_CHILD)) {
			for (Child child : so.getChildren()) {
				stmt.setLong(1, soId);
				setParamsForChild(stmt, child);
				stmt.addBatch();
			}
			stmt.executeBatch();
		}

	}

	private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException {
		setParamsForPerson(stmt, 2, child);
		stmt.setString(6, child.getCertificateNumber());
		stmt.setDate(7, java.sql.Date.valueOf(child.getIssueDate()));
		stmt.setLong(8, child.getRegisterOffice().getOfficeId());
		setParamsForAddress(stmt, 9, child);

	}

	private void setParamsForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
		setParamsForPerson(stmt, start, adult);
		stmt.setString(start + 4, adult.getPassportSeria());
		stmt.setString(start + 5, adult.getPassportNumber());
		stmt.setDate(start + 6, java.sql.Date.valueOf(adult.getIssueDate()));
		stmt.setLong(start + 7, adult.getPassportOffice().getOfficeId());
		setParamsForAddress(stmt, start + 8, adult);
		stmt.setLong(start + 13, adult.getUniversity().getUnivercityId());
		stmt.setString(start + 14, adult.getStudentId());
	}

	private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
		stmt.setString(start, person.getSurName());
		stmt.setString(start + 1, person.getGivenName());
		stmt.setString(start + 2, person.getPatronymic());
		stmt.setDate(start + 3, java.sql.Date.valueOf(person.getDateOfBirth()));

	}

	private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
		Address address = person.getAddress();
		stmt.setString(start, address.getPostCode());
		stmt.setLong(start + 1, address.getStreet().getStreetCode());
		stmt.setString(start + 2, address.getBuildung());
		stmt.setString(start + 3, address.getExtension());
		stmt.setString(start + 4, address.getApartment());
	}

	@Override
	public List<StudentOrder> getStudentOrders() throws DaoException {
		return getStudentOrdersOneSelect();
		// return getStudentOrdersTwoSelect();

	}

	private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
		List<StudentOrder> result = new LinkedList<StudentOrder>();

		try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS_FULL)) {

			Map<Long, StudentOrder> maps = new HashMap<>();

			stmt.setInt(1, StudentOrderStatus.START.ordinal());
			int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
			stmt.setInt(2, limit);

			ResultSet rs = stmt.executeQuery();
			int counter = 0;
			while (rs.next()) {
				Long soId = rs.getLong("student_order_id");
				if (!maps.containsKey(soId)) {
					StudentOrder so = getFullStudentOrder(rs);

					result.add(so);
					maps.put(soId, so);
				}
				StudentOrder so = maps.get(soId);
				so.addChild(fillChild(rs));
				counter++;
			}
			if (counter >= limit) {
				result.remove(result.size() - 1);
			}

			rs.close();
		} catch (SQLException ex) {
			throw new DaoException(ex);
		}

		return result;
	}

	private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
		List<StudentOrder> result = new LinkedList<StudentOrder>();

		try (Connection con = getConnection(); PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS)) {

			stmt.setInt(1, StudentOrderStatus.START.ordinal());
			stmt.setInt(2, Integer.parseInt(Config.getProperty(Config.DB_LIMIT)));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				StudentOrder so = getFullStudentOrder(rs);

				result.add(so);
			}

			findChildren(con, result);
			rs.close();
		} catch (SQLException e) {
			throw new DaoException(e);
		}
		return result;
	}

	private StudentOrder getFullStudentOrder(ResultSet rs) throws SQLException {
		StudentOrder so = new StudentOrder();
		fillStudentOrder(rs, so);
		fillMarriage(rs, so);

		so.setHusband(fillAdult(rs, "h_"));
		so.setWife(fillAdult(rs, "w_"));
		return so;
	}

	private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException {
		so.setStudentOrderId(rs.getLong("student_order_id"));
		so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
		so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));

	}

	private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException {
		so.setMarriageCertificateId(rs.getString("certificate_id"));
		so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());
		Long roId = rs.getLong("register_office_id");
		String areaId = rs.getString("r_office_area_id");
		String name = rs.getString("r_office_name");
		RegisterOffice ro = new RegisterOffice(roId, areaId, name);
		so.setRegisterOffice(ro);
	}

	private Adult fillAdult(ResultSet rs, String pref) throws SQLException {
		Adult adult = new Adult();
		adult.setSurName(rs.getString(pref + "sur_name"));
		adult.setGivenName(rs.getString(pref + "given_name"));
		adult.setPatronymic(rs.getString(pref + "patronymic"));
		adult.setDateOfBirth(rs.getDate(pref + "date_of_birth").toLocalDate());
		adult.setPassportSeria(rs.getString(pref + "passport_seria"));
		adult.setPassportNumber(rs.getString(pref + "passport_number"));
		adult.setIssueDate(rs.getDate(pref + "passport_date").toLocalDate());

		Long poId = rs.getLong(pref + "passport_office_id");
		String poArea = rs.getString(pref + "p_office_area_id");
		String poName = rs.getString(pref + "p_office_name");
		PassportOffice po = new PassportOffice(poId, poArea, poName);
		adult.setPassportOffice(po);

		Address address = new Address();
		Street st = new Street(rs.getLong(pref + "street_code"), "");
		address.setStreet(st);
		address.setPostCode(rs.getString(pref + "post_index"));
		address.setBuildung(rs.getString(pref + "building"));
		address.setExtension(rs.getString(pref + "extension"));
		address.setApartment(rs.getString(pref + "apartment"));
		adult.setAddress(address);

		University university = new University(rs.getLong(pref + "university_id"), "");
		adult.setUniversity(university);
		adult.setStudentId(rs.getString(pref + "student_number"));

		return adult;
	}

	private void findChildren(Connection con, List<StudentOrder> result) throws SQLException {
		String cl = "("
				+ result.stream().map(so -> String.valueOf(so.getStudentOrderId())).collect(Collectors.joining(","))
				+ ")";

		Map<Long, StudentOrder> maps = result.stream()
				.collect(Collectors.toMap(so -> so.getStudentOrderId(), so -> so));

		try (PreparedStatement stmt = con.prepareStatement(SELECT_CHILD + cl)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Child ch = fillChild(rs);
				StudentOrder so = maps.get(rs.getLong("student_order_id"));
				so.addChild(ch);
			}
		}

	}

	private Child fillChild(ResultSet rs) throws SQLException {
		String surName = rs.getString("c_sur_name");
		String givenName = rs.getString("c_given_name");
		String patronymic = rs.getString("c_patronymic");
		LocalDate dateOfBirth = rs.getDate("c_date_of_birth").toLocalDate();

		Child child = new Child(surName, givenName, patronymic, dateOfBirth);

		child.setCertificateNumber(rs.getString("c_certificate_number"));
		child.setIssueDate(rs.getDate("c_certificate_date").toLocalDate());

		Long roId = rs.getLong("c_register_office_id");
		String roArea = rs.getString("r_office_area_id");
		String roName = rs.getString("r_office_name");
		RegisterOffice ro = new RegisterOffice(roId, roArea, roName);
		child.setRegisterOffice(ro);

		Address address = new Address();
		Street st = new Street(rs.getLong("c_street_code"), "");
		address.setStreet(st);
		address.setPostCode(rs.getString("c_post_index"));
		address.setBuildung(rs.getString("c_building"));
		address.setExtension(rs.getString("c_extension"));
		address.setApartment(rs.getString("c_apartment"));
		child.setAddress(address);

		return child;
	}
}
