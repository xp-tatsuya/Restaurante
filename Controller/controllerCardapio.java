package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.CardapioDAO;
import DAO.MesaDAO;
import Model.Cardapio;
import Model.Mesa;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class controllerCardapio implements Initializable {

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
    private TableColumn<Cardapio, String> columnCategoria;

    @FXML
    private TableColumn<Cardapio, String> columnDescricao;

    @FXML
    private TableColumn<Cardapio, String> columnIndice;

    @FXML
    private TableColumn<Cardapio, String> columnNome;

    @FXML
    private TableColumn<Cardapio, String> columnPrecoUN;

    @FXML
    private TableView<Cardapio> tableCardapio;

    @FXML
    private TextField txtPesquisa;

    @FXML
    public Label txtUser;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTableCardapio();
        
    }
    
    public void nome(String nomeCompleto) {
        String[] partesNome = nomeCompleto.split(" ");

        String primeiroNome = partesNome[0];
        String ultimoNome = partesNome[partesNome.length - 1];

        String nomeFormatado = primeiroNome + " " + ultimoNome;
        txtUser.setText(nomeFormatado);
    }

    @FXML
    void ActionAdicionar(ActionEvent event) {

    }

    @FXML
    void ActionCardapio(ActionEvent event) throws IOException {
    	Main.changeScreen("Cardapio", controllerLogin.funcionario.getNome());

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
    	Main.changeScreen("Fornecedor", controllerLogin.funcionario.getNome());
    }

    @FXML
    void ActionFuncionario(ActionEvent event) throws IOException {
    	Main.changeScreen("Funcionario", controllerLogin.funcionario.getNome());
    }

    @FXML
    void ActionHome(ActionEvent event) throws IOException {
    	Main.changeScreen("main", controllerLogin.funcionario.getNome());
    }

    @FXML
    void ActionMesa(ActionEvent event) throws IOException {
    	Main.changeScreen("Mesa", controllerLogin.funcionario.getNome());
    }

    @FXML
    void ActionPedido(ActionEvent event) throws IOException {
    	Main.changeScreen("Pedido", controllerLogin.funcionario.getNome());
    }

    @FXML
    void ActionProduto(ActionEvent event) throws IOException {
    	Main.changeScreen("Produto", controllerLogin.funcionario.getNome());
    }

    @FXML
    void ActionSair(ActionEvent event) throws IOException {
        Main.changeScreen("Login", null);
    }

	
	private ObservableList<Cardapio> ArrayCardapio;
    private void carregarTableCardapio() {
    	CardapioDAO cardapioDAO = new CardapioDAO();
    	ArrayCardapio = FXCollections.observableArrayList(cardapioDAO.read());
        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        columnPrecoUN.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
        
        tableCardapio.setItems(ArrayCardapio);
    }
}