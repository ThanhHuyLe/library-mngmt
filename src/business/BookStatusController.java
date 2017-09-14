package business;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import ui.Start;


public class BookStatusController implements Initializable { // notice this
    @FXML private Button checkBtn;
    @FXML private TextField isbnText;
    @FXML private TextField bookISBNText;
    @FXML private TextField titleText;
    @FXML private TextField copyNumbersText;
    @FXML private TableColumn memberCol;
    @FXML private TableColumn dueDateCol;
    @FXML private TableColumn statusCol;
    @FXML private TableView BookStatusTable;
	@FXML private Text statusCheckout;

	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		Start.primStage().show();
	}


	@FXML
	private void showBookStatusAction(ActionEvent event) throws LibrarySystemException {
		try {
			ControllerInterface c = new SystemController();
			String isbn = isbnText.getText();
			List<RecordEntry> record = c.getBookStatus(isbn);
			DataAccess da = new DataAccessFacade();
			Book book = da.readBooksMap().get(isbn);
			bookISBNText.setText(isbn);
			titleText.setText(book.getTitle());
			copyNumbersText.setText(Integer.toString(book.getCopyNums().size()));
	        final ObservableList<RecordEntry> data = FXCollections.observableArrayList(record);
			memberCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("memberID"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("dueDate"));
			statusCol.setCellValueFactory(new PropertyValueFactory<RecordEntry, String>("status"));
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