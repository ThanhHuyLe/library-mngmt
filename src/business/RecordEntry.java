package business;

import java.time.LocalDate;

public class RecordEntry {
	private final String memberID;
	private final String isbn;
	private final LocalDate checkoutDate;
	private final LocalDate dueDate;

	public RecordEntry(String memberID, String isbn, LocalDate checkoutDate, LocalDate dueDate) {
		this.memberID = memberID;
		this.isbn = isbn;
		this.checkoutDate = checkoutDate;
		this.dueDate = dueDate;
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
}