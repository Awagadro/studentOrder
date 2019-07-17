package edu.dkravchuk.studentorder.domain;

public class University {
	private Long universityId;
	private String universityName;

	public University() {
	}

	public University(Long univercityId, String univercityName) {
		this.universityId = univercityId;
		this.universityName = univercityName;
	}

	public Long getUnivercityId() {
		return universityId;
	}

	public void setUnivercityId(Long univercityId) {
		this.universityId = univercityId;
	}

	public String getUnivercityName() {
		return universityName;
	}

	public void setUnivercityName(String univercityName) {
		this.universityName = univercityName;
	}

	@Override
	public String toString() {
		return "University [universityId=" + universityId + ", universityName=" + universityName + "]";
	}

}
