package ui;
import java.io.IOException;

import business.ControllerInterface;
import business.SystemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddCopyWindow extends Stage implements LibWindow {
		public static final AddCopyWindow INSTANCE = new AddCopyWindow();
	    private GridPane rootLayout;
		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}

		private AddCopyWindow() {}

		public void init() {
			try{
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AddCopyWindow.class.getResource("/ui/addCopy.fxml"));
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


