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
    private static Scene login, main, pedido, cardapio, funcionario, fornecedor, produto, mesa;
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
            primaryStage.setTitle("Login");
            login = new Scene(FXMLLoader.load(getClass().getResource("/View/ViewLogin.fxml")));

            FXMLLoader fxmlHome = new FXMLLoader(getClass().getResource("/View/ViewMain.fxml"));
            Parent homeRoot = fxmlHome.load();
            main = new Scene(homeRoot);
            controllerHome = fxmlHome.getController();

            FXMLLoader fxmlPedido = new FXMLLoader(getClass().getResource("/View/ViewPedido.fxml"));
            Parent pedidoRoot = fxmlPedido.load();
            pedido = new Scene(pedidoRoot);
            controllerPedido = fxmlPedido.getController();

            FXMLLoader fxmlCardapio = new FXMLLoader(getClass().getResource("/View/ViewCardapio.fxml"));
            Parent cardapioRoot = fxmlCardapio.load();
            cardapio = new Scene(cardapioRoot);
            controllerCardapio = fxmlCardapio.getController();

            FXMLLoader fxmlFuncionario = new FXMLLoader(getClass().getResource("/View/ViewFuncionario.fxml"));
            Parent funcRoot = fxmlFuncionario.load();
            funcionario = new Scene(funcRoot);
            controllerFuncionario = fxmlFuncionario.getController();

            FXMLLoader fxmlFornecedor = new FXMLLoader(getClass().getResource("/View/ViewFornecedor.fxml"));
            Parent fornRoot = fxmlFornecedor.load();
            fornecedor = new Scene(fornRoot);
            controllerFornecedor = fxmlFornecedor.getController();

            FXMLLoader fxmlProduto = new FXMLLoader(getClass().getResource("/View/ViewProdutos.fxml"));
            Parent prodRoot = fxmlProduto.load();
            produto = new Scene(prodRoot);
            controllerProduto = fxmlProduto.getController();

            FXMLLoader fxmlMesa = new FXMLLoader(getClass().getResource("/View/ViewMesa.fxml"));
            Parent mesaRoot = fxmlMesa.load();
            mesa = new Scene(mesaRoot);
            controllerMesa = fxmlMesa.getController();

            stage.getIcons().add(new Image(getClass().getResourceAsStream("/Icons/restaurante.png")));
            stage.setScene(login);
            stage.setResizable(false);
            stage.centerOnScreen();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeScreen(String tela, String nome, double dindin) throws IOException {
        switch (tela) {
            case "main":
                controllerHome.nome(nome);
                controllerHome.seila(dindin);
                stage.setScene(main);
                break;
            case "Login":
                stage.setScene(login);
                break;
            case "Pedido":
                controllerPedido.nome(nome);
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
    
    public static Stage showAddCardapioDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/ViewAddCardapio.fxml"));
        Parent root = loader.load();
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Adicionar Cardápio");
        dialog.setScene(new Scene(root));
        dialog.setResizable(false);
        dialog.centerOnScreen();
        dialog.show();
        return dialog;
    }
    
    public static Stage showAddFornecedorDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/ViewAddFornecedor.fxml"));
        Parent root = loader.load();
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Adicionar Fornecedor");
        dialog.setScene(new Scene(root));
        dialog.setResizable(false);
        dialog.centerOnScreen();
        dialog.show();
        return dialog;
    }
    
    public static Stage showAddProdutoDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/ViewAddProduto.fxml"));
        Parent root = loader.load();
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Adicionar Produto");
        dialog.setScene(new Scene(root));
        dialog.setResizable(false);
        dialog.centerOnScreen();
        dialog.show();
        return dialog;
    }
    
    public static Stage showAddMesaDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/View/ViewAddMesa.fxml"));
        Parent root = loader.load();
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Adicionar Mesa");
        dialog.setScene(new Scene(root));
        dialog.setResizable(false);
        dialog.centerOnScreen();
        dialog.show();
        return dialog;
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
        cadFornecedor.setTitle("Registro de Fornecedor");
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
        cadFuncionario.setTitle("Cadastro de Funcionario");
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
        cadCardapio.setTitle("Registro de Produto");
        cadCardapio.initModality(Modality.WINDOW_MODAL);
        cadCardapio.setScene(scene3);
        cadCardapio.setResizable(false);
        cadCardapio.centerOnScreen();
        cadCardapio.showAndWait();
    }
    
    public static void TelaRegistroGarcons() throws IOException {
        FXMLLoader fxmlRegistro = new FXMLLoader();
        fxmlRegistro.setLocation(Main.class.getResource("/View/ViewRegistroGarcons.fxml"));
        Parent telaRegistro = fxmlRegistro.load();
        Scene registroGarcons = new Scene(telaRegistro);

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
        pedido = new Scene(telaPedido);

        stage.setTitle("Relatório de Pedidos");
        stage.setScene(pedido);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void TelaCardapio() throws IOException {
        FXMLLoader fxmlCardapio = new FXMLLoader();
        fxmlCardapio.setLocation(Main.class.getResource("/View/ViewCardapio.fxml"));
        Parent telaCardapio = fxmlCardapio.load();
        cardapio = new Scene(telaCardapio);

        stage.setTitle("Relatório de Cardápio");
        stage.setScene(cardapio);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void TelaFuncionario() throws IOException {
        FXMLLoader fxmlFuncionario = new FXMLLoader();
        fxmlFuncionario.setLocation(Main.class.getResource("/View/ViewFuncionario.fxml"));
        Parent telaFuncionario = fxmlFuncionario.load();
        funcionario = new Scene(telaFuncionario);

        stage.setTitle("Relatório de Funcionário");
        stage.setScene(funcionario);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void TelaFornecedor() throws IOException {
        FXMLLoader fxmlFornecedor = new FXMLLoader();
        fxmlFornecedor.setLocation(Main.class.getResource("/View/ViewFornecedor.fxml"));
        Parent telaFornecedor = fxmlFornecedor.load();
        fornecedor = new Scene(telaFornecedor);

        stage.setTitle("Relatório de Fornecedor");
        stage.setScene(fornecedor);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void TelaProduto() throws IOException {
        FXMLLoader fxmlProduto = new FXMLLoader();
        fxmlProduto.setLocation(Main.class.getResource("/View/ViewProdutos.fxml"));
        Parent telaProduto = fxmlProduto.load();
        produto = new Scene(telaProduto);

        stage.setTitle("Relatório de Produto");
        stage.setScene(produto);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void TelaMesa() throws IOException {
        FXMLLoader fxmlMesa = new FXMLLoader();
        fxmlMesa.setLocation(Main.class.getResource("/View/ViewMesa.fxml"));
        Parent telaMesa = fxmlMesa.load();
        mesa = new Scene(telaMesa);

        stage.setTitle("Relatório de Mesa");
        stage.setScene(mesa);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
