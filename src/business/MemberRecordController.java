package business;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
			List<CheckoutRecord> record = c.getMemberRecord(id);
	        final ObservableList<CheckoutRecord> data = FXCollections.observableArrayList(record);
			isbnCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecord, String>("isbn"));
			checkoutDateCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecord, String>("checkoutDate"));
			dueDateCol.setCellValueFactory(new PropertyValueFactory<CheckoutRecord, String>("dueDate"));
	        statusTable.setItems(data);

	        //Print console
	        System.out.println("Assume that fine is 2 cent/day");
	        System.out.println("Member ID: "+id);
	        System.out.println("ISBN                    Checkout Date                    Due Date          Paid Date          Fine amount");
	        for (CheckoutRecord rec: record) {
	        	System.out.print(rec.getIsbn() + "                "+ rec.getCheckoutDate());
	        	System.out.print("                       "+rec.getDueDate());
	        	long paidDate = rec.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS);
	        	if (paidDate < 0)
	        		paidDate = 0;
	        	System.out.println("           "+paidDate+"                  "+paidDate+" cents");
	        	rec.setPaidDate(paidDate);
	        	rec.setFineAmount(paidDate*2);
	        }
		} catch (Exception e) {
			statusCheckout.setText(e.getMessage());
		}
	 }

    @Override // and this
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}