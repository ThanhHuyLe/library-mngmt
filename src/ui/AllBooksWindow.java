package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AllBooksWindow extends Stage implements LibWindow {
		public static final AllBooksWindow INSTANCE = new AllBooksWindow();
	    private GridPane rootLayout;
		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}

		private AllBooksWindow() {}

		public void init() {
			try{
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(AllBooksWindow.class.getResource("/ui/showAllBooks.fxml"));
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
