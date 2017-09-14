package ui;

import business.Address;
import business.Author;
import business.Book;
import business.LibraryMember;
import business.LibrarySystemException;

public class Checker {

	public static void addressValidation(Address address) throws LibrarySystemException {
		if (address.getStreet().trim().equals("") || address.getCity().trim().equals("")
				|| address.getState().trim().equals("") || address.getZip().trim().equals(""))
			throw new LibrarySystemException("All field must be non-empty!");

	}

	public static void bookCopyValidation(String isbn, String copyNum) throws LibrarySystemException {
		if (isbn.trim().equals("") || copyNum.trim().equals(""))
			throw new LibrarySystemException("All field must be non-empty!");
		isbnRule(isbn);
		isNumberic(copyNum, "Number of copy must be numberic!");
	}

	public static void memberValidation(LibraryMember member) throws LibrarySystemException {
		if (member.getMemberId().trim().equals("") || member.getFirstName().trim().equals("")
				|| member.getLastName().trim().equals("") || member.getTelephone().trim().equals(""))
			throw new LibrarySystemException("All field must be non-empty!");
		zipRule(member.getAddress().getZip());
		isNumberic(member.getTelephone(), "Phone number must be numeric!");

	}

	public static void authorValidation(Author author) throws LibrarySystemException {
		if (author.getAuthorId().trim().equals("") || author.getFirstName().trim().equals("")
				|| author.getLastName().trim().equals("") || author.getTelephone().trim().equals("")  || author.getBio().trim().equals(""))
			throw new LibrarySystemException("All field must be non-empty!");
		isNumberic(author.getAuthorId(), "Author ID must be numeric!");
		zipRule(author.getAddress().getZip());
		isNumberic(author.getTelephone(), "Phone number must be numeric!");

	}


	public static void bookValidation(String isbn, String title, String maxCheckoutLength, String copyNum ) throws LibrarySystemException {
		if (isbn.trim().equals("") ||title.trim().equals("")||maxCheckoutLength.trim().equals("") ||copyNum.trim().equals(""))
			throw new LibrarySystemException("All field must be non-empty!");
		isbnRule(isbn);
		isNumberic(maxCheckoutLength, "Max checkout length must be numberic");
		isNumberic(copyNum, "Number of copies must be numberic!");
	}

	public static void zipRule(String s) throws LibrarySystemException {
		isNumberic(s, "Zip code must be numeric!");
		if (s.length() != 5) {
			throw new LibrarySystemException("Zip code length must be 5!");
		}
	}

	public static void isbnRule(String s) throws LibrarySystemException {
		//isNumberic(s, "ISBN must be numberic!");
		/*if (s.length() != 13 || s.length() != 10) {
			throw new LibrarySystemException("ISBN length must be 10 or 13!");
		}*/
	}

	public static void isNumberic(String s, String msg) throws LibrarySystemException {
		try {
			Integer.parseInt(s);
		} catch (IllegalArgumentException e) {
			throw new LibrarySystemException(msg);
		}
	}

	public static void emptyValidation(String s, String msg) throws LibrarySystemException {
		if (s.trim().equals("")) {
			throw new LibrarySystemException(s);
		}
	}
}
