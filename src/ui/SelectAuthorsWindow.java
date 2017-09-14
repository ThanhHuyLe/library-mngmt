package ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SelectAuthorsWindow extends Stage implements LibWindow {
		public static final SelectAuthorsWindow INSTANCE = new SelectAuthorsWindow();
	    private GridPane rootLayout;

		private boolean isInitialized = false;
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}
		
		private SelectAuthorsWindow() {}

		public void init() {
			try{
    			FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(SelectAuthorsWindow.class.getResource("/ui/SelectAuthors.fxml"));
	            rootLayout = (GridPane) loader.load();
	            setAlwaysOnTop(true);
	            // Show the scene containing the root layout.
	            Scene scene = new Scene(rootLayout);
	            scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		        setScene(scene);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}


