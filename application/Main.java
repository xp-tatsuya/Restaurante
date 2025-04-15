package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Stage stage;
    private static Scene login;
    private static Scene main;
    private static Scene registroGarcons;
    
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
    
	public static void changeScreen(String tela) throws IOException {
		switch (tela) {
		case "main":
			TelaHome();
			break;
		case "Login":
			stage.setScene(login);
			stage.setResizable(false);
			stage.centerOnScreen();
			break;
		}

	}

    public static void TelaHome() throws IOException {
        FXMLLoader fxmlHome = new FXMLLoader();
        fxmlHome.setLocation(Main.class.getResource("/View/ViewMain.fxml"));
        Parent telaHome = fxmlHome.load();
        main = new Scene(telaHome);
        
        stage.setTitle("Menu Principal");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void TelaRegistroGarcons() throws IOException {
        FXMLLoader fxmlRegistro = new FXMLLoader();
        fxmlRegistro.setLocation(Main.class.getResource("/View/ViewRegistroGarcons.fxml"));
        Parent telaRegistro = fxmlRegistro.load();
        registroGarcons = new Scene(telaRegistro);
        
        stage.setTitle("Registro de Garçons");
        stage.setScene(registroGarcons);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    public static void TelaPedido() throws IOException {
        FXMLLoader fxmlPedido = new FXMLLoader();
        fxmlPedido.setLocation(Main.class.getResource("/View/ViewPedido.fxml"));
        Parent telaPedido = fxmlPedido.load();
        main = new Scene(telaPedido);
        
        stage.setTitle("Relatorio de Pedidos");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    public static void TelaCardapio() throws IOException {
        FXMLLoader fxmlCardapio = new FXMLLoader();
        fxmlCardapio.setLocation(Main.class.getResource("/View/ViewCardapio.fxml"));
        Parent telaCardapio = fxmlCardapio.load();
        main = new Scene(telaCardapio);
        
        stage.setTitle("Relatorio de Cardápio");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    public static void TelaFuncionario() throws IOException {
        FXMLLoader fxmlFuncionario = new FXMLLoader();
        fxmlFuncionario.setLocation(Main.class.getResource("/View/ViewFuncionario.fxml"));
        Parent telaFuncionario = fxmlFuncionario.load();
        main = new Scene(telaFuncionario);
        
        stage.setTitle("Relatorio de Funcionário");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    public static void TelaFornecedor() throws IOException {
        FXMLLoader fxmlFornecedor = new FXMLLoader();
        fxmlFornecedor.setLocation(Main.class.getResource("/View/ViewFornecedor.fxml"));
        Parent telaFornecedor = fxmlFornecedor.load();
        main = new Scene(telaFornecedor);
        
        stage.setTitle("Relatorio de Fornecedor");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    public static void TelaProduto() throws IOException {
        FXMLLoader fxmlProduto = new FXMLLoader();
        fxmlProduto.setLocation(Main.class.getResource("/View/ViewProdutos.fxml"));
        Parent telaProduto = fxmlProduto.load();
        main = new Scene(telaProduto);
        
        stage.setTitle("Relatorio de Produto");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    public static void TelaMesa() throws IOException {
        FXMLLoader fxmlMesa = new FXMLLoader();
        fxmlMesa.setLocation(Main.class.getResource("/View/ViewMesa.fxml"));
        Parent telaMesa = fxmlMesa.load();
        main = new Scene(telaMesa);
        
        stage.setTitle("Relatorio de Mesa");
        stage.setScene(main);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
