package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AuthorsWindow extends Stage implements LibWindow {
	public static final AuthorsWindow INSTANCE = new AuthorsWindow();
    private GridPane rootLayout;
	private boolean isInitialized = false;
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private AuthorsWindow() {}

	public void init() {
		try{
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AuthorsWindow.class.getResource("/ui/AuthorList.fxml"));
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
