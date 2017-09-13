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
	private TableColumn autherIdCol;
	@FXML
	private TableColumn fnameCol;
	@FXML
	private TableColumn lnameCol;
	@FXML
	private TableColumn telTable;
	@FXML
	private TableView authorTable;

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

	@FXML
	private void getAuthorList() throws LibrarySystemException {
		try {
			ControllerInterface c = new SystemController();
			List<Author> authors = c.allAuthors();
			System.out.println(authors.size());
			final ObservableList<Author> data = FXCollections.observableArrayList(authors);
			autherIdCol.setCellValueFactory(new PropertyValueFactory<Author, String>("authorId"));
			fnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
			lnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("lasttName"));
			telTable.setCellValueFactory(new PropertyValueFactory<Author, String>("telephone"));

			authorTable.setItems(data);
			msg.setText("");
		} catch (Exception e) {
			msg.setText(e.getMessage());
		}
	}
}
