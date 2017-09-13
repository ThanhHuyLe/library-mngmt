package ui;

import business.Book;
import business.ControllerInterface;
import business.LibrarySystemException;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BookCopyController {

	@FXML
	private Text msg;
	@FXML
	private TextField isbn;

	@FXML
	private TextField copyNum;

	@FXML
	protected void addBookCopy(ActionEvent evt) {
		try {

			Checker.bookCopyValidation(isbn.getText(), copyNum.getText());
			ControllerInterface c = new SystemController();
			Book book = c.getBook(isbn.getText());
			if (book == null) {
				throw new LibrarySystemException("Book not found!");
			}
			for (int i = 0; i < Integer.parseInt(copyNum.getText()); i++) {
				book.addCopy();
			}
			msg.setFill(Color.GREEN);
			msg.setText(copyNum.getText() + " copies added successfully!");
			clear();
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
		copyNum.setText("");
	}
}
