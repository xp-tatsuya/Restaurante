package application;
	
import java.io.IOException;
import java.sql.Connection;

import ConnectionFactory.ConnectionDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private static Stage stage;
	private static Scene Login;
	private static Scene main;
	
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			primaryStage.setTitle("Miyazaki - Login");
			
			Parent fxmlLogin = FXMLLoader.load(getClass().getResource("/View/ViewLogin.fxml"));
			Login = new Scene(fxmlLogin);
			
			primaryStage.setScene(Login);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeScreen(String tela) {
		if(tela.equals("main")) {
			stage.setScene(main);
			stage.centerOnScreen();
			stage.setTitle("Menu Principal");
		}
	}
	
	public static void TelaHome () throws IOException {
		FXMLLoader fxmlHome = new FXMLLoader();
		fxmlHome.setLocation(Main.class.getResource("/View/ViewHome.fxml"));
		Parent telaHome = fxmlHome.load();
		main = new Scene(telaHome);
		stage.setTitle("Miyazaki - Menu Principal");
		stage.setScene(main);
		stage.setResizable(false);
		stage.centerOnScreen();
		stage.show();
	}
	
	public static void main(String[] args) {
		Connection con = ConnectionDatabase.getConnection();
		ConnectionDatabase.closeConnection(con);
		launch(args);
	}
	
}
	