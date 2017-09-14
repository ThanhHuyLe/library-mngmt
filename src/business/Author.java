package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final public class Author extends Person implements Serializable {
	private String authorId;
	private String bio;
	private List<Book> books;

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public String getAuthorId() {
		return authorId;
	}

	public String getBio() {
		return bio;
	}

	public Author(String authorId, String f, String l, String t, Address a, String bio) {
		super(f, l, t, a);
		this.authorId = authorId;
		this.bio = bio;
		books = new ArrayList<Book>();
	}

	void addBook(Book book) {
		books.add(book);
	}
	private static final long serialVersionUID = 7508481940058530471L;

}
