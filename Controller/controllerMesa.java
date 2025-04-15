package Controller;

import java.io.IOException;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class controllerMesa {

    @FXML
    private Button btAdicionar;

    @FXML
    private Button btCardapio;

    @FXML
    private Button btEditar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btFinalizar;

    @FXML
    private Button btFonecedor;

    @FXML
    private Button btFuncionario;

    @FXML
    private Button btHome;

    @FXML
    private Button btMesa;

    @FXML
    private Button btPedido;

    @FXML
    private Button btProduto;

    @FXML
    private Button btSair;

    @FXML
    private TableColumn<?, ?> columnCapacidade;

    @FXML
    private TableColumn<?, ?> columnCondicao;

    @FXML
    private TableColumn<?, ?> columnIndice;

    @FXML
    private TableView<?> tableMesa;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Label txtUser;

    @FXML
    void ActionAdicionar(ActionEvent event) {

    }

    @FXML
    void ActionCardapio(ActionEvent event) throws IOException {
    	Main.TelaCardapio();
    }

    @FXML
    void ActionEditar(ActionEvent event) {

    }

    @FXML
    void ActionExcluir(ActionEvent event) {

    }

    @FXML
    void ActionFinalizar(ActionEvent event) {

    }

    @FXML
    void ActionFornecedor(ActionEvent event) throws IOException {
    	Main.TelaFornecedor();
    }

    @FXML
    void ActionFuncionario(ActionEvent event) throws IOException {
    	Main.TelaFuncionario();
    }

    @FXML
    void ActionHome(ActionEvent event) throws IOException {
    	Main.TelaHome();
    }

    @FXML
    void ActionMesa(ActionEvent event) throws IOException {
    	Main.TelaMesa();
    }

    @FXML
    void ActionPedido(ActionEvent event) throws IOException {
    	Main.TelaPedido();
    }

    @FXML
    void ActionProduto(ActionEvent event) throws IOException {
    	Main.TelaProduto();
    }

    @FXML
    void ActionSair(ActionEvent event) throws IOException {
        Main.changeScreen("Login");
    }

}
