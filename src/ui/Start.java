package ui;

import java.util.Collections;
import java.util.List;

import business.ControllerInterface;
import business.SystemController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Start extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	private static Stage primStage = null;
	public static Stage primStage() {
		return primStage;
	}

	public static class Colors {
		static Color green = Color.web("#034220");
		static Color red = Color.FIREBRICK;
	}

	private static Stage[] allWindows = {
		LoginWindow.INSTANCE,
		AllMembersWindow.INSTANCE,
		AllBooksWindow.INSTANCE,
		AddBookWindow.INSTANCE,
		AddMemberWindow.INSTANCE,
		AddCopyWindow.INSTANCE,
		CheckBookAvailableWindow.INSTANCE,
		CheckBookAvailableWindow.INSTANCE,
		CheckMemberRecordWindow.INSTANCE
	};

	public static void hideAllWindows() {
		primStage.hide();
		for(Stage st: allWindows) {
			st.hide();
		}
	}


	@Override
	public void start(Stage primaryStage) {
		primStage = primaryStage;
		primaryStage.setTitle("Main Page");

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		MenuBar mainMenu = new MenuBar();
		VBox imageHolder = new VBox();
		Image image = new Image("ui/library.jpg", 600, 450, false, false);

        // simply displays in ImageView the image as is
        ImageView iv = new ImageView();
        iv.setImage(image);
        imageHolder.getChildren().add(iv);
        imageHolder.setAlignment(Pos.CENTER);
        HBox splashBox = new HBox();
        Label splashLabel = new Label("The Library System");
        splashLabel.setFont(Font.font("Trajan Pro", FontWeight.BOLD, 30));
        splashBox.getChildren().add(splashLabel);
        splashBox.setAlignment(Pos.CENTER);

		topContainer.getChildren().add(mainMenu);
		topContainer.getChildren().add(splashBox);
		topContainer.getChildren().add(imageHolder);

		Menu authenticationMenu = new Menu("Authentication");
		MenuItem logIn = new MenuItem("Login");
		authenticationMenu.getItems().addAll(logIn);

		Menu addMenu = new Menu("Add");
		MenuItem book = new MenuItem("Book");
		MenuItem member = new MenuItem("Member");
		MenuItem copyOfBook = new MenuItem("Copy of Book");
		addMenu.getItems().addAll(book, member, copyOfBook);

		Menu showMenu = new Menu("Show");
		MenuItem bookStatus = new MenuItem("Book Status");
		MenuItem allBooks = new MenuItem("All Books");
		MenuItem allMembers = new MenuItem("All Members");
		showMenu.getItems().addAll(bookStatus, allBooks, allMembers);

		Menu checkMenu = new Menu("Check");
		MenuItem bookAvailable = new MenuItem("Book Available");
		MenuItem memberRecord = new MenuItem("Member Record");
		checkMenu.getItems().addAll(bookAvailable, memberRecord);

		Menu authorMenu = new Menu("Author");

		logIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	hideAllWindows();
    			if(!LoginWindow.INSTANCE.isInitialized()) {
    				LoginWindow.INSTANCE.init();
    			}
    			LoginWindow.INSTANCE.clear();
    			LoginWindow.INSTANCE.show();
            }
        });

		book.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	hideAllWindows();
    			if(!AddBookWindow.INSTANCE.isInitialized()) {
    				AddBookWindow.INSTANCE.init();
    			}
    			AddBookWindow.INSTANCE.show();
            }
        });

		member.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	hideAllWindows();
    			if(!AddMemberWindow.INSTANCE.isInitialized()) {
    				AddMemberWindow.INSTANCE.init();
    			}
    			AddMemberWindow.INSTANCE.show();
            }
        });

		copyOfBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	hideAllWindows();
    			if(!AddCopyWindow.INSTANCE.isInitialized()) {
    				AddCopyWindow.INSTANCE.init();
    			}
    			AddCopyWindow.INSTANCE.show();
            }
        });

		bookStatus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	hideAllWindows();
    			if(!ShowBookStatusWindow.INSTANCE.isInitialized()) {
    				ShowBookStatusWindow.INSTANCE.init();
    			}
    			ShowBookStatusWindow.INSTANCE.show();
            }
        });


		allBooks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				hideAllWindows();
				if(!AllBooksWindow.INSTANCE.isInitialized()) {
					AllBooksWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				List<String> ids = ci.allBookIds();
				Collections.sort(ids);
				StringBuilder sb = new StringBuilder();
				for(String s: ids) {
					sb.append(s + "\n");
				}
				AllBooksWindow.INSTANCE.setData(sb.toString());
				AllBooksWindow.INSTANCE.show();
            }
		});

		allMembers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
				hideAllWindows();
				if(!AllMembersWindow.INSTANCE.isInitialized()) {
					AllMembersWindow.INSTANCE.init();
				}
				ControllerInterface ci = new SystemController();
				List<String> ids = ci.allMemberIds();
				Collections.sort(ids);
				//System.out.println(ids);
				StringBuilder sb = new StringBuilder();
				for(String s: ids) {
					sb.append(s + "\n");
				}
				//System.out.println(sb.toString());
				AllMembersWindow.INSTANCE.setData(sb.toString());
				AllMembersWindow.INSTANCE.show();
            }
		});

		bookAvailable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	hideAllWindows();
    			if(!CheckBookAvailableWindow.INSTANCE.isInitialized()) {
    				CheckBookAvailableWindow.INSTANCE.init();
    			}
    			CheckBookAvailableWindow.INSTANCE.show();
            }
        });

		memberRecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	hideAllWindows();
    			if(!CheckMemberRecordWindow.INSTANCE.isInitialized()) {
    				CheckMemberRecordWindow.INSTANCE.init();
    			}
    			CheckMemberRecordWindow.INSTANCE.show();
            }
        });

		mainMenu.getMenus().addAll(authenticationMenu, addMenu, showMenu, checkMenu, authorMenu);
		Scene scene = new Scene(topContainer, 700, 550);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		primaryStage.show();
	}

}
