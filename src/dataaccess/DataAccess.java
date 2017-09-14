package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.LibraryMember;

public interface DataAccess {
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, Author> readAuthorMap();
	public void updateMember(LibraryMember member);
	public void saveNewMember(LibraryMember member);
	public void updateBook(Book book);
	public void saveNewBook(Book book);
	public void saveAuthor(Author author);
	public void updateAuthor(Author author);
}
