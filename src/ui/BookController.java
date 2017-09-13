package ui;

import java.util.ArrayList;
import java.util.List;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
