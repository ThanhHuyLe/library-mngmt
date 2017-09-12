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
    @FXML private Button checkBtn;
    @FXML private TextField memberIDText;
    @FXML private TextField isbnText;
    @FXML private TableColumn memberCol;
    @FXML private TableColumn isbnCol;
    @FXML private TableColumn checkoutDateCol;
    @FXML private TableColumn dueDateCol;
    @FXML private TableView bookStatusTable;
	@FXML private Text statusCheckout;

    public class BookStatus {
    	private final String memberID;
    	private final String isbn;
    	private final LocalDate checkoutDate;
    	private final LocalDate dueDate;

    	public BookStatus(String memberID, String isbn, LocalDate checkoutDate, LocalDate dueDate) {
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

	@Override
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
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

	@SuppressWarnings("unchecked")
	@FXML
	private void checkoutBookAction(ActionEvent event) throws LibrarySystemException {
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
			final ObservableList<BookStatus> data = FXCollections.observableArrayList(
				    new BookStatus(id, isbn, LocalDate.now(), LocalDate.now().plusDays(book.getMaxCheckoutLength()))
			);
			memberCol.setCellValueFactory(
	                new PropertyValueFactory<BookStatus, String>("memberID"));

	        isbnCol.setCellValueFactory(
	                new PropertyValueFactory<BookStatus, String>("isbn"));

	        checkoutDateCol.setCellValueFactory(
	                new PropertyValueFactory<BookStatus, String>("checkoutDate"));

	        dueDateCol.setCellValueFactory(
	                new PropertyValueFactory<BookStatus, String>("dueDate"));

	        bookStatusTable.setItems(data);
	        statusCheckout.setText("");
		} catch (Exception e) {
			statusCheckout.setText(e.getMessage());
		}
	 }
}
