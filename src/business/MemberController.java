package business;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.Checker;
import ui.Start;

public class MemberController {

	@FXML
	private Text msg;

	@FXML
	private TextField memberId;
	@FXML
	private TextField fname;
	@FXML
	private TextField lname;
	@FXML
	private TextField tel;
	@FXML

	private TextField street;
	@FXML
	private TextField city;
	@FXML
	private TextField state;

	@FXML
	private TextField zip;

	@FXML
	protected void addMember(ActionEvent evt) {
		try {

			Address add = new Address(street.getText(), city.getText(), state.getText(), zip.getText());
			List<RecordEntry> record = new ArrayList<RecordEntry>();
			Checker.addressValidation(add);
			LibraryMember member = new LibraryMember(memberId.getText(), fname.getText(), lname.getText(),
					tel.getText(), add, record);
			Checker.memberValidation(member);
			ControllerInterface c = new SystemController();
			c.addMember(member);
			clear();
			msg.setFill(Color.GREEN);
			msg.setText("New library member added successfully!");
		} catch (LibrarySystemException e) {
			msg.setFill(Color.RED);
			msg.setText(e.getMessage());
		}
	}

	@FXML
	protected void resetMember(ActionEvent evt) {
		clear();
	}

	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		Start.primStage().show();
	}

	private void clear() {
		memberId.setText("");
		fname.setText("");
		lname.setText("");
		tel.setText("");
		street.setText("");
		city.setText("");
		state.setText("");
		zip.setText("");
		msg.setText("");
	}
}