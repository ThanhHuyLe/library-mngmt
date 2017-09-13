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


public class CheckoutController implements Initializable { // notice this
    @FXML private Button checkBtn;
    @FXML private TextField memberIDText;
    @FXML private TextField isbnText;
    @FXML private TableColumn memberCol;
    @FXML private TableColumn isbnCol;
    @FXML private TableColumn checkoutDateCol;
    @FXML private TableColumn dueDateCol;
    @FXML private TableView BookStatusTable;
	@FXML private Text statusCheckout;

	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		Start.primStage().show();
	}


	@FXML
	private void checkoutBookAction(ActionEvent event) throws LibrarySystemException {
		try {
			ControllerInterface c = new SystemController();
			String id = memberIDText.getText();
			String isbn = isbnText.getText();
			List<RecordEntry> record = c.getBookAvailable(id, isbn);
	        final ObservableList<RecordEntry> data = FXCollections.observableArrayList(record);
			memberCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("memberID"));
			isbnCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("isbn"));
			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("dueDate"));

	        BookStatusTable.setItems(data);
	        statusCheckout.setText("");
		} catch (Exception e) {
			statusCheckout.setText(e.getMessage());
		}
	 }

    @Override // and this
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}