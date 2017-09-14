package business;

import java.io.Serializable;
import java.time.LocalDate;

public class RecordEntry implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = -6456884726468548214L;
	private final String memberID;
	private final String isbn;
	private final LocalDate checkoutDate;
	private final LocalDate dueDate;
	public String status;

	public RecordEntry(String memberID, String isbn, LocalDate checkoutDate, LocalDate dueDate, String status) {
		this.memberID = memberID;
		this.isbn = isbn;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
		this.status = status;
	}

	public String getMemberID() {
		return memberID;
	}

	public String getIsbn() {
		return isbn;
	}

	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}