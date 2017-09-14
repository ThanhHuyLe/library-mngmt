package business;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.AddAuthorsWindow;
import ui.Checker;
import ui.Start;

public class BookController implements Initializable{

	@FXML private Text msg;
	@FXML private TextField isbn;

	@FXML private TextField title;
	@FXML private TextField authors;
	@FXML private TextField maxCheckoutLength;

	@FXML private TextField copyNum;

	@FXML private TableColumn authorIdCol;
	@FXML private TableColumn fnameCol;
	@FXML private TableColumn lnameCol;
	@FXML private TableColumn telephoneCol;
	@FXML private TableView authorTable;

	@FXML
	private void getAuthorList(ActionEvent evt) throws LibrarySystemException {

		ControllerInterface c = new SystemController();
		List<Author> authors = c.allAuthors();
		final ObservableList<Author> data = FXCollections.observableArrayList(authors);
		authorIdCol.setCellValueFactory(new PropertyValueFactory<Author, String>("authorId"));
		fnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
		lnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("lasttName"));
		telephoneCol.setCellValueFactory(new PropertyValueFactory<Author, String>("telephone"));

		authorTable.setItems(data);
	}

	@FXML
	protected void addBook(ActionEvent evt) {
		try {

			List<Author> authorList = new ArrayList<Author>();
			String authorIds = authors.getText();
			authorIds = authorIds.trim() + ",";
			DataAccess da = new DataAccessFacade();
			Set<String> retval = da.readAuthorMap().keySet();
			while (authorIds.indexOf(',')!=0) {
				String authorId = authorIds.substring(0, authorIds.indexOf(','));
				if (authorIds.indexOf(',')!=authorIds.length()-1) {
					authorIds = authorIds.substring(authorIds.indexOf(',')+1, authorIds.length());
				} else authorIds = ",";
				if (!retval.contains(authorId))
					throw new LibrarySystemException("Author ID does not exist.");
				authorList.add(da.readAuthorMap().get(authorId));
			}

			Checker.bookValidation(isbn.getText(), title.getText(), maxCheckoutLength.getText(), copyNum.getText());
			ControllerInterface c = new SystemController();
			if (c.getBook(isbn.getText().trim()) != null) {
				throw new LibrarySystemException("ISBN must be unique!");
			}

			Book book = new Book(isbn.getText(), title.getText(), Integer.parseInt(maxCheckoutLength.getText()),
					authorList);

			for (int i = 0; i < Integer.parseInt(copyNum.getText()); i++) {
				book.addCopy();
			}
			c.addBook(book);
			clear();
			msg.setFill(Color.GREEN);
			msg.setText("New book is added successfully!");
		} catch (LibrarySystemException ex) {
			msg.setFill(Color.RED);
			msg.setText(ex.getMessage());
		}
	}

	@FXML
	protected void back(ActionEvent evt) {
		Start.hideAllWindows();
		Start.primStage().show();
	}

	@FXML
	protected void resetBook(ActionEvent evt) {
		clear();
	}

	private void clear() {
		isbn.setText("");
		title.setText("");
		maxCheckoutLength.setText("");
		authors.setText("");
		copyNum.setText("");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
