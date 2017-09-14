package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 *
 */
final public class BookInfo implements Serializable {

	private static final long serialVersionUID = 6110690276685962829L;
	private String copies;
	private String authors;
	private String isbn;
	private String title;
	private String maxDay;
	public BookInfo(String isbn, String title, String maxDay, String authors, String copies) {
		this.setIsbn(isbn);
		this.setTitle(title);
		this.setMaxDay(maxDay);
		this.setAuthors(authors);
		this.setCopies(copies);

	}
	public String getCopies() {
		return copies;
	}
	public void setCopies(String copies) {
		this.copies = copies;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMaxDay() {
		return maxDay;
	}
	public void setMaxDay(String maxDay) {
		this.maxDay = maxDay;
	}

}
