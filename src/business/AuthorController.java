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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import ui.Start;

public class AuthorController implements Initializable{

	@FXML
	private TableColumn<Author, String> autherIdCol;
	@FXML
	private TableColumn<Author, String> fnameCol;
	@FXML
	private TableColumn<Author, String> lnameCol;
	@FXML
	private TableColumn<Author, String> telephoneCol;
	@FXML
	private TableView<Author> authorTable;

	@FXML
	protected void back(ActionEvent e) {
		Start.hideAllWindows();
		Start.primStage().show();
	}

	@FXML
	public void getAuthorList(ActionEvent evt) throws LibrarySystemException {

		ControllerInterface c = new SystemController();
		List<Author> authors = c.allAuthors();
		System.out.println(authors.toString());
		final ObservableList<Author> data = FXCollections.observableArrayList(authors);
		System.out.println("data=" + data.size());
		for (Author author : data) {
			System.out.println(author.getFirstName());
		}
		// authorTable = new TableView<Author>();
		/*
		 * autherIdCol = new TableColumn<Author, String>("Author 1 ID"); fnameCol = new
		 * TableColumn<Author, String>("Firstname"); lnameCol = new TableColumn<Author,
		 * String>("Lastname"); telephoneCol = new TableColumn<Author,
		 * String>("Telephone");
		 */
		// TODO Auto-generated method stub
		autherIdCol.setCellValueFactory(new PropertyValueFactory<Author, String>("authorId"));
		fnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("firstName"));
		lnameCol.setCellValueFactory(new PropertyValueFactory<Author, String>("lasttName"));
		telephoneCol.setCellValueFactory(new PropertyValueFactory<Author, String>("telephone"));

		authorTable.setItems(data);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
