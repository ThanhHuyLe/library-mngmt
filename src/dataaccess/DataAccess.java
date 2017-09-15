package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.LibraryMember;
import business.LibrarySystemException;

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
	public LibraryMember searchMember(String memberId) throws LibrarySystemException;
	public Book searchBook(String isbn) throws LibrarySystemException;
	boolean checkBorrow(LibraryMember member, Book book) throws LibrarySystemException;
}
