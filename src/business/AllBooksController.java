package business;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ui.Start;


public class AllBooksController implements Initializable { // notice this
    @FXML private TableColumn isbnCol;
    @FXML private TableColumn titleCol;
    @FXML private TableColumn maxDayCol;
    @FXML private TableColumn authorsCol;
    @FXML private TableColumn numberCopiesCol;
    @FXML private TableView statusTable;

	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		Start.primStage().show();
	}


	@FXML
	private void showAllBooksAction(ActionEvent event) throws LibrarySystemException {
		ControllerInterface c = new SystemController();
		List<BookInfo> record;
		try {
			record = c.getAllBooks();
	        final ObservableList<BookInfo> data = FXCollections.observableArrayList(record);
			isbnCol.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("isbn"));
			titleCol.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("title"));
			maxDayCol.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("maxDay"));
			numberCopiesCol.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("copies"));
			authorsCol.setCellValueFactory(new PropertyValueFactory<BookInfo, String>("authors"));
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