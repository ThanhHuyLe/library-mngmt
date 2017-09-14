package ui;

import java.util.Collections;
import java.util.List;

import business.ControllerInterface;
import business.LibraryMember;
import business.LibrarySystemException;
import business.SystemController;
import dataaccess.Auth;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


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

	private boolean isMenuCreated= false;

	private static Stage[] allWindows = {
		LoginWindow.INSTANCE,
		AllBooksWindow.INSTANCE,
		AllBooksWindow.INSTANCE,
		AddBookWindow.INSTANCE,
		AddMemberWindow.INSTANCE,
		AddAuthorWindow.INSTANCE,
		AddCopyWindow.INSTANCE,
		CheckoutBookWindow.INSTANCE,
		CheckoutBookWindow.INSTANCE,
		CheckMemberRecordWindow.INSTANCE,
		SelectAuthorsWindow.INSTANCE
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
		primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent window) {
				if (SystemController.currentAuth != null && !isMenuCreated) {
					mainMenu.getMenus().clear();
					if (SystemController.currentAuth == Auth.ADMIN || SystemController.currentAuth == Auth.BOTH) {
						Menu addMenu = new Menu("Add");
						MenuItem book = new MenuItem("Book");
						MenuItem member = new MenuItem("Member");
						MenuItem copyOfBook = new MenuItem("Copy of Book");
						// MenuItem author = new MenuItem("Author");
						addMenu.getItems().addAll(book, member, copyOfBook);
						mainMenu.getMenus().add(addMenu);
						book.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								hideAllWindows();
								if (!AddBookWindow.INSTANCE.isInitialized()) {
									AddBookWindow.INSTANCE.init();
								}
								AddBookWindow.INSTANCE.show();
							}
						});

						member.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								hideAllWindows();
								if (!AddMemberWindow.INSTANCE.isInitialized()) {
									AddMemberWindow.INSTANCE.init();
								}
								AddMemberWindow.INSTANCE.show();
							}
						});

						/*
						 * author.setOnAction(new EventHandler<ActionEvent>() {
						 *
						 * @Override public void handle(ActionEvent e) {
						 * hideAllWindows();
						 * if(!AddAuthorsWindow.INSTANCE.isInitialized()) {
						 * AddAuthorsWindow.INSTANCE.init(); }
						 * AddAuthorsWindow.INSTANCE.show(); } });
						 */

						copyOfBook.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								hideAllWindows();
								if (!AddCopyWindow.INSTANCE.isInitialized()) {
									AddCopyWindow.INSTANCE.init();
								}
								AddCopyWindow.INSTANCE.show();
							}
						});
					}
					Menu showMenu = new Menu("Show");
					MenuItem bookStatus = new MenuItem("Book Status");
					MenuItem allBooks = new MenuItem("All Books");
					MenuItem allMembers = new MenuItem("All Members");
					showMenu.getItems().addAll(bookStatus, allBooks, allMembers);

					Menu checkMenu = new Menu("Implement");
					if (SystemController.currentAuth.equals(Auth.LIBRARIAN)
							|| SystemController.currentAuth.equals(Auth.BOTH)) {
						MenuItem bookAvailable = new MenuItem("Checkout Book");
						checkMenu.getItems().add(bookAvailable);
						bookAvailable.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								hideAllWindows();
								if (!CheckoutBookWindow.INSTANCE.isInitialized()) {
									CheckoutBookWindow.INSTANCE.init();
								}
								CheckoutBookWindow.INSTANCE.show();
							}
						});
					}
					MenuItem memberRecord = new MenuItem("Print Member Record");
					checkMenu.getItems().add(memberRecord);
					Menu logout = new Menu("Logout");
					MenuItem logoutMenu = new MenuItem("Logout");
					logout.getItems().addAll(logoutMenu);

					bookStatus.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							hideAllWindows();
							if (!ShowBookStatusWindow.INSTANCE.isInitialized()) {
								ShowBookStatusWindow.INSTANCE.init();
							}
							ShowBookStatusWindow.INSTANCE.show();
						}
					});

					allBooks.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							hideAllWindows();
							if (!AllBooksWindow.INSTANCE.isInitialized()) {
								AllBooksWindow.INSTANCE.init();
							}

							AllBooksWindow.INSTANCE.show();
						}
					});

					allMembers.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							hideAllWindows();
							if (!AllMembersWindow.INSTANCE.isInitialized()) {
								AllMembersWindow.INSTANCE.init();
							}

							AllMembersWindow.INSTANCE.show();
						}
					});

					memberRecord.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							hideAllWindows();
							if (!CheckMemberRecordWindow.INSTANCE.isInitialized()) {
								CheckMemberRecordWindow.INSTANCE.init();
							}
							CheckMemberRecordWindow.INSTANCE.show();
						}
					});
					logoutMenu.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							hideAllWindows();
							mainMenu.getMenus().clear();
							mainMenu.getMenus().add(authenticationMenu);
							SystemController.currentAuth = null;
							isMenuCreated = false;
							primaryStage.show();
						}
					});
					mainMenu.getMenus().addAll(showMenu, checkMenu, logout);
					isMenuCreated = true;
				}
			}
			});


		mainMenu.getMenus().add(authenticationMenu);
		Scene scene = new Scene(topContainer, 700, 550);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		primaryStage.show();
	}

}
