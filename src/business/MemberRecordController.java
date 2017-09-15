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


public class MemberRecordController implements Initializable { // notice this
    @FXML private Button checkBtn;
    @FXML private TextField memberIDText;
    @FXML private TextField isbnText;
    @FXML private TableColumn memberCol;
    @FXML private TableColumn isbnCol;
    @FXML private TableColumn checkoutDateCol;
    @FXML private TableColumn dueDateCol;
    @FXML private TableView statusTable;
	@FXML private Text statusCheckout;

	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		Start.primStage().show();
	}


	@FXML
	private void printRecordAction(ActionEvent event) throws LibrarySystemException {
		try {
			ControllerInterface c = new SystemController();
			String id = memberIDText.getText();
			List<RecordEntry> record = c.getMemberRecord(id);
	        final ObservableList<RecordEntry> data = FXCollections.observableArrayList(record);
			isbnCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("isbn"));
			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("dueDate"));
	        statusTable.setItems(data);
	        statusCheckout.setText("");

	        //Print console
	        System.out.println("Member ID: "+id);
	        System.out.println("ISBN                    Checkout Date                    Due Date");
	        for (RecordEntry rec: record) {
	        	System.out.println(rec.getIsbn() + "                "+ rec.getCheckoutDate() + "                       "+rec.getDueDate());
	        }
		} catch (Exception e) {
			statusCheckout.setText(e.getMessage());
		}
	 }

	@FXML
	protected void reset(ActionEvent evt) {
		clear();
	}


    @Override // and this
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}