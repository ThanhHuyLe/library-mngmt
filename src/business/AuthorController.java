package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.AddAuthorWindow;
import ui.Checker;
import ui.SelectAuthorsWindow;
import ui.Start;

public class AuthorController {
	@FXML
	private Text msg;

	@FXML
	private TextField authorId;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private TextField telephone;
	@FXML

	private TextField street;
	@FXML
	private TextField city;
	@FXML
	private TextField state;

	@FXML
	private TextField zip;

	@FXML
	private TextArea bio;

	@FXML
	protected void addAuthor(ActionEvent evt) {
		try {

			Address add = new Address(street.getText(), city.getText(), state.getText(), zip.getText());
			Checker.addressValidation(add);
			Author author = new Author(authorId.getText().trim(), firstname.getText(), lastname.getText(),
					telephone.getText(), add, bio.getText());
			ControllerInterface c = new SystemController();
			if (c.getAuthor(author.getAuthorId()) != null) {
				throw new LibrarySystemException("Author ID must be unique!");
			}
			Checker.authorValidation(author);
			c.addAuthor(author);
			clear();
			msg.setFill(Color.GREEN);
			msg.setText("New author added successfully!");
			SelectAuthorsWindow.INSTANCE.setAlwaysOnTop(true);
			AddAuthorWindow.INSTANCE.hide();
		} catch (LibrarySystemException e) {
			// msg.setFill(Color.RED);
			msg.setText(e.getMessage());
		}
	}

	@FXML
	protected void resetAuthor(ActionEvent evt) {
		clear();
	}

	private void clear() {
		authorId.setText("");
		firstname.setText("");
		lastname.setText("");
		telephone.setText("");
		street.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
		bio.setText("");
		msg.setText("");
	}
}
