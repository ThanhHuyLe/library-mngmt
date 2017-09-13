package ui;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import business.Book;
import business.BookCopy;
import business.ControllerInterface;
import business.LibraryMember;
import business.LibrarySystemException;
import business.RecordEntry;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckoutBookWindow extends Stage implements LibWindow {
		public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();
	    private GridPane rootLayout;

		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}

		private CheckoutBookWindow() {}

		public void init() {
			try{
    			FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(CheckoutBookWindow.class.getResource("/ui/checkBookAvailable.fxml"));
	            rootLayout = (GridPane) loader.load();

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		        setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}


