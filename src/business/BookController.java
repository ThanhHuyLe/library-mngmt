package business;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.AuthorsWindow;
import ui.Checker;
import ui.Start;

public class BookController {

	@FXML
	private Text msg;
	@FXML
	private TextField isbn;

	@FXML
	private TextField title;
	@FXML
	private TextField authors;
	@FXML
	private TextField maxCheckoutLength;

	@FXML
	private TextField copyNum;

	@FXML
	private TableColumn<Author, String> autherIdCol;
	@FXML
	private TableColumn<Author, String> fnameCol;
	@FXML
	private TableColumn<Author, String> lnameCol;
	@FXML
	private TableColumn<Author, String> telephoneCol;
	@FXML
	private TableView<Author> authorTable;

	@FXML
	private void getAuthorList() throws LibrarySystemException {
		try {

			ControllerInterface c = new SystemController();
			List<Author> authors = c.allAuthors();
			System.out.println(authors.size());
			final ObservableList<Author> data = FXCollections.observableArrayList(authors);
			System.out.println("data="+data.size());
			authorTable = new TableView<Author>();
			autherIdCol = new TableColumn<Author, String>("Author ID");
			fnameCol = new TableColumn<Author, String>("Firstname");
			lnameCol = new TableColumn<Author, String>("Lastname");
			telephoneCol = new TableColumn<Author, String>("Telephone");

			/*autherIdCol.setCellValueFactory(new PropertyValueFactory<Author, String>("authorId"));
			fnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
			lnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("lasttName"));
			telephoneCol.setCellValueFactory(new PropertyValueFactory<Author, String>("telephone"));
*/
			authorTable.setItems(data);
			msg.setText("");
		} catch (Exception e) {
			msg.setText(e.getMessage());
		}
	}

	@FXML
	protected void addBook(ActionEvent evt) {
		try {

			List<Author> authors = new ArrayList<Author>();
			Checker.bookValidation(isbn.getText(), title.getText(), maxCheckoutLength.getText(), copyNum.getText());
			Book book = new Book(isbn.getText(), title.getText(), Integer.parseInt(maxCheckoutLength.getText()),
					authors);
			ControllerInterface c = new SystemController();
			for (int i = 0; i < Integer.parseInt(copyNum.getText()); i++) {
				book.addCopy();
			}
			c.addBook(book);
			clear();
			msg.setFill(Color.GREEN);
			msg.setText("New book added successfully!");
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
	protected void selectAuthor(ActionEvent evt) {
		try {
			getAuthorList();
			Start.hideAllWindows();
			if (!AuthorsWindow.INSTANCE.isInitialized()) {
				AuthorsWindow.INSTANCE.init();
			}
			AuthorsWindow.INSTANCE.show();
		} catch (LibrarySystemException e) {
			msg.setText(e.getMessage());
		}

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
	}

}