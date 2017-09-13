package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<RecordEntry> record = new ArrayList<RecordEntry>();
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add, List<RecordEntry> record) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.record = record;
	}


	public String getMemberId() {
		return memberId;
	}

	public List<RecordEntry> getRecord() {
		if (record == null) {
			record = new ArrayList<RecordEntry>();
		}
		return record;
	}

	public void setRecord(List<RecordEntry> record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() +
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
