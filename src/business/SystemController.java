package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Book;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import ui.Start;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	@FXML
	private Button checkBtn;
	@FXML
	private TextField memberIDText;
	@FXML
	private TextField isbnText;
	@FXML
	private TableColumn memberCol;
	@FXML
	private TableColumn isbnCol;
	@FXML
	private TableColumn checkoutDateCol;
	@FXML
	private TableColumn dueDateCol;
	@FXML
	private TableView BookStatusTable;
	@FXML
	private Text statusCheckout;

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
	public List<RecordEntry> getBookAvailable(String id, String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		if (!retval.contains(id))
			throw new LibrarySystemException("Member ID is not found");
		retval.addAll(da.readBooksMap().keySet());
		LibraryMember member = da.readMemberMap().get(id);
		List<RecordEntry> record = member.getRecord();
		if (!retval.contains(isbn))
			throw new LibrarySystemException("The book with isbn " + isbn + " is not available");
		Book book = da.readBooksMap().get(isbn);
		boolean memberBorrow = false;
		for (RecordEntry entry : record) {
			if (entry.getIsbn().equals(isbn))
				memberBorrow = true;
		}
		if (!book.isAvailable() || memberBorrow)
			throw new LibrarySystemException("The book with isbn " + isbn + " is not available");

		RecordEntry newEntry = new RecordEntry(id, isbn, LocalDate.now(),
				LocalDate.now().plusDays(book.getMaxCheckoutLength()));
		record.add(newEntry);
		member.setRecord(record);
		da.updateMember(member);

		BookCopy copy = book.getNextAvailableCopy();
		copy.changeAvailability();
		book.updateCopies(copy);
		da.updateBook(book);
		return record;
	}

	@Override
	public List<RecordEntry> getMemberRecord(String id) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(da.readMemberMap().keySet());
		if (!retval.contains(id))
			throw new LibrarySystemException("Member ID is not found");
		LibraryMember member = da.readMemberMap().get(id);
		return member.getRecord();
	}

	@Override
	public List<RecordEntry> getBookStatus(String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<String>();
		retval.addAll(da.readBooksMap().keySet());
		if (!retval.contains(isbn))
			throw new LibrarySystemException("The book with isbn " + isbn + " is not available");
		retval.removeAll(da.readBooksMap().keySet());
		retval.addAll(da.readMemberMap().keySet());
		List<RecordEntry> record = new ArrayList<RecordEntry>();
		for (String id : retval) {
			LibraryMember member = da.readMemberMap().get(id);
			List<RecordEntry> temp = member.getRecord();
			for (RecordEntry rec : temp) {
				if (rec.getIsbn().equals(isbn)) {
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
