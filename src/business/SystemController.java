package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	@Override
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	@Override
	public List<CheckoutRecord> getBookAvailable(String id, String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(id);
		Book book = da.searchBook(isbn);
		boolean memberBorrow = da.checkBorrow(member, book);
		List<CheckoutRecord> record = member.getRecord();
		if (!memberBorrow) {
			CheckoutEntry entry = new CheckoutEntry(LocalDate.now(),
					LocalDate.now().plusDays(book.getMaxCheckoutLength()));
			CheckoutRecord rec = new CheckoutRecord(id, isbn, entry.checkoutDate, entry.dueDate, 0, 0, "");
			record.add(rec);
		}
		else throw new LibrarySystemException("Member with ID "+id+" has borrowed this book already");
		member.setRecord(record);
		da.updateMember(member);
		BookCopy copy = book.getNextAvailableCopy();
		copy.changeAvailability();
		book.updateCopies(copy);
		da.updateBook(book);
		return record;
	}

	@Override
	public List<CheckoutRecord> getMemberRecord(String id) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.searchMember(id);
		return member.getRecord();
	}

	@Override
	public List<CheckoutRecord> getBookStatus(String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(da.readBooksMap().keySet());
		if (!retval.contains(isbn))
			throw new LibrarySystemException("The book with isbn " + isbn + " does not exist");
		retval.removeAll(da.readBooksMap().keySet());
		retval.addAll(da.readMemberMap().keySet());
		List<CheckoutRecord> record = new ArrayList<CheckoutRecord>();
		for (String id : retval) {
			LibraryMember member = da.readMemberMap().get(id);
			List<CheckoutRecord> temp = member.getRecord();
			for (CheckoutRecord rec : temp) {
				if (rec.getIsbn().equals(isbn)) {
					if (rec.getDueDate().isBefore(LocalDate.now()))
						rec.setStatus("Overdue");
					else rec.setStatus("Not overdue");
					record.add(rec);
				}
			}
		}
		return record;
	}

	@Override
	public List<LibraryMember> getAllMembers() throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(da.readMemberMap().keySet());
		List<LibraryMember> result = new ArrayList<LibraryMember>();
		for (String id : retval) {
			result.add(da.readMemberMap().get(id));
		}
		return result;
	}

	@Override
	public List<BookInfo> getAllBooks() throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(da.readBooksMap().keySet());
		List<BookInfo> result = new ArrayList<BookInfo>();
		for (String isbn : retval) {
			Book book = da.readBooksMap().get(isbn);
			result.add(new BookInfo(book.getIsbn(), book.getTitle(), Integer.toString(book.getMaxCheckoutLength()),
					book.authorsToString(), Integer.toString(book.getCopyNums().size())));
		}
		return result;
	}

	@Override
	public void addMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);

	}

	@Override
	public void addBook(Book book) {
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(book);
		for (Author author : book.getAuthors()) {
			da.updateAuthor(author);
		}
	}

	@Override
	public Book getBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		Book book = da.readBooksMap().get(isbn);
		return book;
	}

	@Override
	public List<Author> allAuthors() {
		DataAccess da = new DataAccessFacade();
		List<Author> authors = new ArrayList<Author>(da.readAuthorMap().values());
		return authors;
	}

	@Override
	public LibraryMember getMember(String memberId) {
		DataAccess da = new DataAccessFacade();
		LibraryMember member = da.readMemberMap().get(memberId);
		return member;
	}

	@Override
	public void addAuthor(Author author) {
		DataAccess da = new DataAccessFacade();
		da.saveAuthor(author);

	}

	@Override
	public Author getAuthor(String authorId) {
		DataAccess da = new DataAccessFacade();
		Author author = da.readAuthorMap().get(authorId);
		return author;
	}

	@Override
	public void addCopy(Book book, int num) {
		DataAccess da = new DataAccessFacade();
		for (int i = 0; i <num; i++) {
			book.addCopy();
		}
		da.updateBook(book);
	}
}
