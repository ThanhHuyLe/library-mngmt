package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;

	public List<String> allMemberIds();

	public List<String> allBookIds();

	public List<RecordEntry> getBookAvailable(String id, String isbn) throws LibrarySystemException;

	public void addMember(LibraryMember member);

	public void addBook(Book book);

	public Book getBook(String isbn);

	public List<Author> allAuthors();

	public LibraryMember getMember(String memberId);

	public List<RecordEntry> getMemberRecord(String id) throws LibrarySystemException;

	public List<RecordEntry> getBookStatus(String isbn) throws LibrarySystemException;

	public List<LibraryMember> getAllMembers() throws LibrarySystemException;

	public List<BookInfo> getAllBooks() throws LibrarySystemException;
}
