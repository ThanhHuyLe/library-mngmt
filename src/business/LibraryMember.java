package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private List<CheckoutRecord> record = new ArrayList<CheckoutRecord>();
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add, List<CheckoutRecord> record) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.record = record;
	}


	public String getMemberId() {
		return memberId;
	}

	public List<CheckoutRecord> getRecord() {
		if (record == null) {
			record = new ArrayList<CheckoutRecord>();
		}
		return record;
	}

	public void setRecord(List<CheckoutRecord> record) {
		this.record = record;
	}

	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() +
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
