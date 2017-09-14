package business;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import ui.Start;


public class AllMembersController implements Initializable { // notice this
    @FXML private TableColumn memberIDCol;
    @FXML private TableColumn firstNameCol;
    @FXML private TableColumn lastNameCol;
    @FXML private TableColumn telephoneCol;
    @FXML private TableColumn addressCol;
    @FXML private TableView statusTable;

	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		Start.primStage().show();
	}


	@FXML
	private void showAllMembersAction(ActionEvent event) throws LibrarySystemException {
		ControllerInterface c = new SystemController();
		List<LibraryMember> record;
		try {
			record = c.getAllMembers();
	        final ObservableList<LibraryMember> data = FXCollections.observableArrayList(record);
			memberIDCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("memberId"));
			firstNameCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("firstName"));
			lastNameCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("lastName"));
			telephoneCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("telephone"));
			addressCol.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>("address"));
	        statusTable.setItems(data);

		} catch (LibrarySystemException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 }

    @Override // and this
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}