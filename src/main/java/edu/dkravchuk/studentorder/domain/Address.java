package edu.dkravchuk.studentorder.domain;

public class Address {
	private String postCode;
	private Street street;
	private String buildung;
	private String extension;
	private String apartment;

	public Address(String postCode, Street street, String buildung, String extension, String apartment) {
		super();
		this.postCode = postCode;
		this.street = street;
		this.buildung = buildung;
		this.extension = extension;
		this.apartment = apartment;
	}

	public Address() {
		// TODO Auto-generated constructor stub
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public String getBuildung() {
		return buildung;
	}

	public void setBuildung(String buildung) {
		this.buildung = buildung;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	@Override
	public String toString() {
		return "Address [postCode=" + postCode + ", street=" + street + ", buildung=" + buildung + ", extension="
				+ extension + ", apartment=" + apartment + "]";
	}

}
