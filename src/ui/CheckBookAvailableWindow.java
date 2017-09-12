package ui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CheckBookAvailableWindow extends Stage implements LibWindow {
		public static final CheckBookAvailableWindow INSTANCE = new CheckBookAvailableWindow();
	    private GridPane rootLayout;
		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}

		private CheckBookAvailableWindow() {}

		public void init() {
			try{
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(CheckBookAvailableWindow.class.getResource("/ui/checkBookAvailable.fxml"));
	            rootLayout = (GridPane) loader.load();

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		        setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}


