package ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddAuthorWindow extends Stage implements LibWindow {
	public static final AddAuthorWindow INSTANCE = new AddAuthorWindow();
    private GridPane rootLayout;
	private boolean isInitialized = false;
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private AddAuthorWindow() {}

	public void init() {
		try{
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AddAuthorWindow.class.getResource("/ui/addAuthor.fxml"));
            rootLayout = (GridPane) loader.load();
            setAlwaysOnTop(true);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
	        setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
