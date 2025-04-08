package application;
	
import java.io.IOException;
import java.sql.Connection;

import ConnectionFactory.ConnectionDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private static Stage stage;
	private static Scene login;
	private static Scene main;
	
	@Override
    public void start(Stage primaryStage) {
        try {
            
        	stage = primaryStage;
        	primaryStage.setTitle("Login");
        	
        	Parent fxmlLogin = FXMLLoader.load(getClass().getResource("/View/ViewLogin.fxml"));
        	login = new Scene(fxmlLogin);
        	
        	stage.getIcons().add(new Image(getClass().getResourceAsStream("/Icons/restaurante.png")));
        	
            primaryStage.setScene(login);
            stage.setResizable(false);
        	stage.centerOnScreen();
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void changeScreen(String tela) {
    	
    	if (tela.equals("main")) {
    		stage.setScene(main);
    		stage.centerOnScreen();
    		stage.setTitle("Menu Principal");
    	} else if(tela.equals("login")); {
    		stage.setScene(login);
    		stage.centerOnScreen();
    		stage.setTitle("Login");
    	}
    	
    }
    
    public static void TelaHome() throws IOException {
    	
    	FXMLLoader fxmlHome = new FXMLLoader();
    	fxmlHome.setLocation(Main.class.getResource("/View/ViewMain.fxml"));
    	Parent TelaHome = fxmlHome.load();
    	main = new Scene(TelaHome);
    	
    	stage.setTitle("Menu Principal");
    	
    	stage.setScene(main);
    	stage.setResizable(false);
    	stage.centerOnScreen();
    	stage.show();
    	
    }
	
	public static void main(String[] args) {
		launch(args);
	}
}

