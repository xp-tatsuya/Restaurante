	package application;
	
	import java.io.IOException;
	
	import Controller.*;
	import javafx.application.Application;
	import javafx.fxml.FXMLLoader;
	import javafx.scene.Parent;
	import javafx.scene.Scene;
	import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

	public class Main extends Application {
		
		
	    private static Stage stage;
	    private static Scene login;
	    private static Scene main;
	    private static Scene pedido;
	    private static Scene cardapio;
	    private static Scene funcionario;
	    private static Scene fornecedor;
	    private static Scene registroGarcons;
	    private static Scene produto;
	    private static Scene mesa;
	    
	    private static controllerMain controllerHome;
	    private static controllerPedido controllerpedido;
	    private static controllerCardapio controllerCardapio;
	    private static controllerFuncionario controllerFuncionario;
	    private static controllerFornecedor controllerFornecedor;
	    private static controllerProdutos controllerProduto;
	    private static controllerMesa controllerMesa;
	    
	    @Override
	    public void start(Stage primaryStage) {
	        try {
	            stage = primaryStage;
	            primaryStage.setTitle("Login");
	            
	            Parent fxmlLogin = FXMLLoader.load(getClass().getResource("/View/ViewLogin.fxml"));
	            login = new Scene(fxmlLogin);
	            
	            FXMLLoader fxmlHome = new FXMLLoader(getClass().getResource("/View/ViewMain.fxml"));
	            Parent fxmlHomeParent = fxmlHome.load();
	            main = new Scene(fxmlHomeParent);
	            controllerHome = fxmlHome.getController();
	            
		        FXMLLoader fxmlPedido = new FXMLLoader();
		        fxmlPedido.setLocation(Main.class.getResource("/View/ViewPedido.fxml"));
		        Parent telaPedido = fxmlPedido.load();
		        pedido = new Scene(telaPedido);
		        controllerpedido = fxmlPedido.getController();
		        
	            FXMLLoader fxmlCardapio = new FXMLLoader(getClass().getResource("/View/ViewCardapio.fxml"));
	            Parent fxmlCardapioParent = fxmlCardapio.load();
	            cardapio = new Scene(fxmlCardapioParent);
	            controllerCardapio = fxmlCardapio.getController();
	            
	            FXMLLoader fxmlFuncionario = new FXMLLoader(getClass().getResource("/View/ViewFuncionario.fxml"));
	            Parent fxmlFuncionarioParent = fxmlFuncionario.load();
	            funcionario = new Scene(fxmlFuncionarioParent);
	            controllerFuncionario = fxmlFuncionario.getController();
	            
	            FXMLLoader fxmlFornecedor = new FXMLLoader(getClass().getResource("/View/ViewFornecedor.fxml"));
	            Parent fxmlFornecedorParent = fxmlFornecedor.load();
	            fornecedor = new Scene(fxmlFornecedorParent);
	            controllerFornecedor = fxmlFornecedor.getController();
	            
	            FXMLLoader fxmlProduto = new FXMLLoader(getClass().getResource("/View/ViewProdutos.fxml"));
	            Parent fxmlProdutoParent = fxmlProduto.load();
	            produto = new Scene(fxmlProdutoParent);
	            controllerProduto = fxmlProduto.getController();
	            
	            FXMLLoader fxmlMesa = new FXMLLoader(getClass().getResource("/View/ViewMesa.fxml"));
	            Parent fxmlMesaParent = fxmlMesa.load();
	            mesa = new Scene(fxmlMesaParent);
	            controllerMesa = fxmlMesa.getController();
	            
	            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Icons/restaurante.png")));
	            
	            primaryStage.setScene(login);
	            stage.setResizable(false);
	            stage.centerOnScreen();
	            primaryStage.show();
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
		public static void changeScreen(String tela, String nome) throws IOException {
			switch (tela) {
			case "main":
				controllerHome.nome(nome);
				stage.setScene(main);
				break;
			case "Login":
				stage.setScene(login);
				stage.setResizable(false);
				stage.centerOnScreen();
				break;
			case "Pedido":
				controllerpedido.nome(nome);
				stage.setScene(pedido);
				break;
			case "Cardapio":
				controllerCardapio.nome(nome);
				stage.setScene(cardapio);
				break;
			case "Funcionario":
				controllerFuncionario.nome(nome);
				stage.setScene(funcionario);
				break;
			case "Fornecedor":
				controllerFornecedor.nome(nome);
				stage.setScene(fornecedor);
				break;
			case "Produto":
				controllerProduto.nome(nome);
				stage.setScene(produto);
				break;
			case "Mesa":
				controllerMesa.nome(nome);
				stage.setScene(mesa);
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
	
	    private static Stage cadPedido;
	    public static void TelaAddPedido() throws IOException {
	        FXMLLoader fxmlAddPedido = new FXMLLoader();
	        fxmlAddPedido.setLocation(Main.class.getResource("/View/ViewAddPedido.fxml"));
	        Parent telaAddPedido = fxmlAddPedido.load();
	    	Scene scene2 = new Scene(telaAddPedido);
	    	
	    	cadPedido = new Stage();
	    	cadPedido.setTitle("Registro de Garçons");
	    	cadPedido.initModality(Modality.WINDOW_MODAL);
	    	cadPedido.setScene(scene2);
	    	cadPedido.centerOnScreen();
	    	cadPedido.showAndWait();   	
	    }
	    
	    private static Stage cadCardapio;
	    public static void TelaAddCardapio() throws IOException {
	        FXMLLoader fxmlAddCardapio = new FXMLLoader();
	        fxmlAddCardapio.setLocation(Main.class.getResource("/View/ViewAddCardapio.fxml"));
	        Parent telaAddCardapio = fxmlAddCardapio.load();
	    	Scene scene3 = new Scene(telaAddCardapio);
	    	
	    	cadCardapio = new Stage();
	    	cadCardapio.setTitle("Registro de Garçons");
	    	cadCardapio.initModality(Modality.WINDOW_MODAL);
	    	cadCardapio.setScene(scene3);
	    	cadCardapio.centerOnScreen();
	    	cadCardapio.showAndWait();   	
	    }
	    
	    public static void TelaRegistroGarcons() throws IOException {
	        FXMLLoader fxmlRegistro = new FXMLLoader();
	        fxmlRegistro.setLocation(Main.class.getResource("/View/ViewRegistroGarcons.fxml"));
	        Parent telaRegistro = fxmlRegistro.load();
	        main = new Scene(telaRegistro);
	        
	        stage.setTitle("Registro de Garçons");
	        stage.setScene(main);
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
