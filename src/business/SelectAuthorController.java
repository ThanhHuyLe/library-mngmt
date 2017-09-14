package business;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import ui.SelectAuthorsWindow;
import ui.Start;

public class SelectAuthorController implements Initializable { // notice this
	@FXML
	private Button checkBtn;
	@FXML
	private TextField memberIDText;
	@FXML
	private TextField isbnText;
	@FXML
	private TableColumn<Author, String> authorIdCol;
	@FXML
	private TableColumn<Author, String> firstnameCol;
	@FXML
	private TableColumn<Author, String> lastnameCol;
	@FXML
	private TableColumn<Author, String> bioCol;
	@FXML
	private TableView<Author> authorsTable;

	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		Start.primStage().show();
	}

	@FXML
	public void selectAuthor(ActionEvent event) throws LibrarySystemException {
		try {
			ControllerInterface c = new SystemController();
			List<Author> authors = c.allAuthors();
			final ObservableList<Author> data = FXCollections.observableArrayList(authors);
			authorIdCol.setCellValueFactory(new PropertyValueFactory<Author, String>("authorId"));
			firstnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
			lastnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("lastName"));
			bioCol.setCellValueFactory(new PropertyValueFactory<Author, String>("bio"));

			authorsTable.setItems(data);
		} catch (Exception e) {
			// statusCheckout.setText(e.getMessage());
		}
	}

	public void passAuthors(ActionEvent event) throws LibrarySystemException {
		SelectAuthorsWindow.INSTANCE.hide();
	}

	@Override // and this
	public void initialize(URL url, ResourceBundle rb) {
		try {
			ControllerInterface c = new SystemController();
			List<Author> authors = c.allAuthors();
			final ObservableList<Author> data = FXCollections.observableArrayList(authors);
			authorIdCol.setCellValueFactory(new PropertyValueFactory<Author, String>("authorId"));
			firstnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
			lastnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("lastName"));
			bioCol.setCellValueFactory(new PropertyValueFactory<Author, String>("bio"));
			// authorsTable.getSelectionModel().setCellSelectionEnabled(true);
			authorsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			authorsTable.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					BookController.selectedAuthors = authorsTable.getSelectionModel().getSelectedItems();
				}
			});
			authorsTable.setItems(data);
		} catch (Exception e) {
			// statusCheckout.setText(e.getMessage());
		}
	}

}