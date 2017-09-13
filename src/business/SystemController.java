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
		try {
			String id = memberIDText.getText();
			String isbn = isbnText.getText();
			DataAccess da = new DataAccessFacade();
			List<String> retval = new ArrayList<>();
			retval.addAll(da.readMemberMap().keySet());
			if (!retval.contains(id))
				throw new LibrarySystemException("Member ID is not found");
			retval.addAll(da.readBooksMap().keySet());
			if (!retval.contains(isbn))
				throw new LibrarySystemException("The book is not available");
			Book book = da.readBooksMap().get(isbn);
			if (!book.isAvailable())
				throw new LibrarySystemException("The book is not available");
			RecordEntry newEntry = new RecordEntry(id, isbn, LocalDate.now(),
					LocalDate.now().plusDays(book.getMaxCheckoutLength()));
			final ObservableList<RecordEntry> data = FXCollections.observableArrayList(newEntry);
			memberCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("memberID"));
			isbnCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("isbn"));
			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("dueDate"));

			BookStatusTable.setItems(data);
			statusCheckout.setText("");
		} catch (Exception e) {
			statusCheckout.setText(e.getMessage());
		}
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

	}

	@Override
	public Book getBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		Book book = da.readBooksMap().get(isbn);
		return book;
	}
}
