package business;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.Checker;
import ui.SelectAuthorsWindow;
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

	public static List<Author> authorList = new ArrayList<>();
	public static ObservableList<Author> selectedAuthors;

	@FXML
	protected void addBook(ActionEvent evt) {
		try {

			Checker.bookValidation(isbn.getText(), title.getText(), maxCheckoutLength.getText(), copyNum.getText());
			ControllerInterface c = new SystemController();
			if (c.getBook(isbn.getText().trim()) != null) {
				throw new LibrarySystemException("ISBN must be unique!s");
			}
			if (authorList.isEmpty()) {
				throw new LibrarySystemException("Please select authors!");
			}
			Book book = new Book(isbn.getText(), title.getText(), Integer.parseInt(maxCheckoutLength.getText()),
					authorList);
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
			authorList.clear();
			String authorsName = "";
			if (!SelectAuthorsWindow.INSTANCE.isInitialized()) {
				SelectAuthorsWindow.INSTANCE.init();
			}

			SelectAuthorsWindow.INSTANCE.showAndWait();
			Author a;
			// for (Author a : selectedAuthors) {
			if (selectedAuthors == null)
				throw new LibrarySystemException("please select author!");
			for (int i = 0; i < selectedAuthors.size(); i++) {
				a = selectedAuthors.get(i);
				authorsName += a.getFirstName() + " " + a.getLastName();
				if (i < selectedAuthors.size() - 1) {
					authorsName += ", ";
				}
				authorList.add(a);
			}

			authors.setText(authorsName);
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
		copyNum.setText("");
	}

}
