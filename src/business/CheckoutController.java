package business;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
			List<CheckoutRecord> record = c.getBookAvailable(id, isbn);
	        final ObservableList<CheckoutRecord> data = FXCollections.observableArrayList(record);
			memberCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecord, String>("memberID"));
			isbnCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecord, String>("isbn"));
			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecord, String>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecord, String>("dueDate"));
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