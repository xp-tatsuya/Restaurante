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
    private static Scene login, main, pedido, cardapio, funcionario, fornecedor, produto, mesa, registrarVenda;
    private static controllerMain controllerHome;
    private static controllerPedido controllerPedido;
    private static controllerCardapio controllerCardapio;
    private static controllerFuncionario controllerFuncionario;
    private static controllerFornecedor controllerFornecedor;
    private static controllerProdutos controllerProduto;
    private static controllerMesa controllerMesa;

    @Override
    public void start(Stage primaryStage) {
    	try {
    		stage = primaryStage;
        	primaryStage.setTitle("Registrar venda - Sushi's Miyazaki");
        	registrarVenda = new Scene(FXMLLoader.load(getClass().getResource("/View/ViewRegistroGarcons.fxml")));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Icons/restaurante.png")));
            stage.setScene(registrarVenda);
            stage.setResizable(false);
            stage.centerOnScreen();
            primaryStage.show();

            FXMLLoader fxmlHome = new FXMLLoader(Main.class.getResource("/View/ViewMain.fxml"));
            Parent homeRoot = fxmlHome.load();
            main = new Scene(homeRoot);
            controllerHome = fxmlHome.getController();

            FXMLLoader fxmlPedido = new FXMLLoader(Main.class.getResource("/View/ViewPedido.fxml"));
            Parent pedidoRoot = fxmlPedido.load();
            pedido = new Scene(pedidoRoot);
            controllerPedido = fxmlPedido.getController();

            FXMLLoader fxmlCardapio = new FXMLLoader(Main.class.getResource("/View/ViewCardapio.fxml"));
            Parent cardapioRoot = fxmlCardapio.load();
            cardapio = new Scene(cardapioRoot);
            controllerCardapio = fxmlCardapio.getController();

            FXMLLoader fxmlFuncionario = new FXMLLoader(Main.class.getResource("/View/ViewFuncionario.fxml"));
            Parent funcRoot = fxmlFuncionario.load();
            funcionario = new Scene(funcRoot);
            controllerFuncionario = fxmlFuncionario.getController();

            FXMLLoader fxmlFornecedor = new FXMLLoader(Main.class.getResource("/View/ViewFornecedor.fxml"));
            Parent fornRoot = fxmlFornecedor.load();
            fornecedor = new Scene(fornRoot);
            controllerFornecedor = fxmlFornecedor.getController();

            FXMLLoader fxmlProduto = new FXMLLoader(Main.class.getResource("/View/ViewProdutos.fxml"));
            Parent prodRoot = fxmlProduto.load();
            produto = new Scene(prodRoot);
            controllerProduto = fxmlProduto.getController();

            FXMLLoader fxmlMesa = new FXMLLoader(Main.class.getResource("/View/ViewMesa.fxml"));
            Parent mesaRoot = fxmlMesa.load();
            mesa = new Scene(mesaRoot);
            controllerMesa = fxmlMesa.getController();
            
            
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	
    }
    public static void carregarTelas() {
    	try {
    	
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    public static void changeScreen(String tela, String nome, double totalVendas) throws IOException {
        switch (tela) {
            case "main":
                controllerHome.nome(nome);
                controllerHome.totalVendas(totalVendas); //esse metodo tem que ser chamado depois de eu comer o cu de quem ta lendo
                stage.setTitle("Home - Sushi's Miyazaki");
                stage.setScene(main);
                break;
            case "Login":
            	stage.setTitle("Login - Sushi's Miyazaki");
                stage.setScene(login);
                break;
            case "Pedido":
                controllerPedido.nome(nome);
                controllerPedido.loadPendentes();
                stage.setTitle("Pedido - Sushi's Miyazaki");
                stage.setScene(pedido);
                break;
            case "Cardapio":
                controllerCardapio.nome(nome);
                stage.setTitle("Cardapio - Sushi's Miyazaki");
                stage.setScene(cardapio);
                break;
            case "Funcionario":
                controllerFuncionario.nome(nome);
                stage.setTitle("Funcionario - Sushi's Miyazaki");
                stage.setScene(funcionario);
                break;
            case "Fornecedor":
                controllerFornecedor.nome(nome);
                stage.setTitle("Fornecedor - Sushi's Miyazaki");
                stage.setScene(fornecedor);
                break;
            case "Produto":
                controllerProduto.nome(nome);
                stage.setTitle("Produto - Sushi's Miyazaki");
                stage.setScene(produto);
                break;
            case "Mesa":
                controllerMesa.nome(nome);
                stage.setTitle("Mesa - Sushi's Miyazaki");
                stage.setScene(mesa);
                break;
            case "Registrar":
            	stage.setTitle("Registrar venda - Sushi's Miyazaki");
            	stage.setScene(registrarVenda);
            	break;
        }
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    public static Stage showAddPedidoDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/ViewAddPedido.fxml"));
        Parent root = loader.load();
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Adicionar Pedido");
        dialog.setScene(new Scene(root));
        dialog.setResizable(false);
        dialog.centerOnScreen();
        dialog.show();
        return dialog;
    }

    public static void TelaLogin() throws IOException {
    	FXMLLoader fxmlLogin = new FXMLLoader();
    	fxmlLogin.setLocation(Main.class.getResource("/View/ViewLogin.fxml"));
    	Parent telaLogin = fxmlLogin.load();
    	login = new Scene(telaLogin);
    	
    	stage.setTitle("Login - Sushi's Miyazaki");
    	stage.setScene(login);
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
        cadPedido.setResizable(false);
        cadPedido.centerOnScreen();
        cadPedido.showAndWait();
    }

    private static Stage cadFornecedor;
    public static void TelaAddFornecedor() throws IOException {
        FXMLLoader fxmlAddFornecedor = new FXMLLoader();
        fxmlAddFornecedor.setLocation(Main.class.getResource("/View/ViewAddFornecedor.fxml"));
        Parent telaAddFornecedor = fxmlAddFornecedor.load();
        Scene scene3 = new Scene(telaAddFornecedor);

        cadFornecedor = new Stage();
        if(controllerFornecedor.fornecedor == null) {
        	cadFornecedor.setTitle("Registro de Fornecedor");
        }else {
        	cadFornecedor.setTitle("Atualizar Fornecedor");
        }
        cadFornecedor.initModality(Modality.WINDOW_MODAL);
        cadFornecedor.setScene(scene3);
        cadFornecedor.setResizable(false);
        cadFornecedor.centerOnScreen();
        cadFornecedor.showAndWait();
    }
    private static Stage cadFuncionario;	
    public static void TelaAddFuncionario() throws IOException {
        FXMLLoader fxmlAddFuncionario = new FXMLLoader();
        fxmlAddFuncionario.setLocation(Main.class.getResource("/View/ViewAddFuncionario.fxml"));
        Parent telaAddFuncionario = fxmlAddFuncionario.load();
        Scene scene3 = new Scene(telaAddFuncionario);
        
        cadFuncionario = new Stage();
        if(controllerFuncionario.funcionario == null) {
        	cadFuncionario.setTitle("Cadastro de Funcionario");
        }else {
        	cadFuncionario.setTitle("Atualizar Funcionario");
        }
        
        cadFuncionario.initModality(Modality.WINDOW_MODAL);
        cadFuncionario.setScene(scene3);
        cadFuncionario.setResizable(false);
        cadFuncionario.centerOnScreen();
        cadFuncionario.showAndWait();
    }
    
    private static Stage cadProduto;
    public static void TelaAddProduto() throws IOException {
        FXMLLoader fxmlAddProduto = new FXMLLoader();
        fxmlAddProduto.setLocation(Main.class.getResource("/View/ViewAddProduto.fxml"));
        Parent telaAddProduto = fxmlAddProduto.load();
        Scene scene3 = new Scene(telaAddProduto);

        cadProduto = new Stage();
        cadProduto.setTitle("Cadastro de produto");
        cadProduto.initModality(Modality.WINDOW_MODAL);
        cadProduto.setScene(scene3);
        cadProduto.setResizable(false);
        cadProduto.centerOnScreen();
        cadProduto.showAndWait();
    }
    
    private static Stage cadMesa;
    public static void TelaAddMesa() throws IOException {
        FXMLLoader fxmlAddMesa = new FXMLLoader();
        fxmlAddMesa.setLocation(Main.class.getResource("/View/ViewAddMesa.fxml"));
        Parent telaAddMesa = fxmlAddMesa.load();
        Scene scene3 = new Scene(telaAddMesa);

        cadMesa = new Stage();
        cadMesa.setTitle("Registro de Mesa");
        cadMesa.initModality(Modality.WINDOW_MODAL);
        cadMesa.setScene(scene3);
        cadMesa.setResizable(false);
        cadMesa.centerOnScreen();
        cadMesa.showAndWait();
    }
    
    private static Stage cadCardapio;
    public static void TelaAddCardapio() throws IOException {
        FXMLLoader fxmlAddCardapio = new FXMLLoader();
        fxmlAddCardapio.setLocation(Main.class.getResource("/View/ViewAddCardapio.fxml"));
        Parent telaAddCardapio = fxmlAddCardapio.load();
        Scene scene3 = new Scene(telaAddCardapio);

        cadCardapio = new Stage();
        if(controllerCardapio.cardapio == null) {
        	cadCardapio.setTitle("Registro de Produto");
        }else {
        	cadCardapio.setTitle("Atualizar Produto");
        }
        cadCardapio.initModality(Modality.WINDOW_MODAL);
        cadCardapio.setScene(scene3);
        cadCardapio.setResizable(false);
        cadCardapio.centerOnScreen();
        cadCardapio.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}