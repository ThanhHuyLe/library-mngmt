package ui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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


