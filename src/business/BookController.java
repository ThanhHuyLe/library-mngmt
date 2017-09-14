package business;

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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import ui.Checker;
import ui.Start;

public class BookController {

	@FXML private Text msg;
	@FXML private TextField isbn;

	@FXML private TextField title;
	@FXML private TextField authors;
	@FXML private TextField maxCheckoutLength;

	@FXML private TextField copyNum;

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

}
