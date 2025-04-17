package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.MesaDAO;
import DAO.ProdutoDAO;
import Model.Mesa;
import Model.Produto;
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

public class controllerProdutos implements Initializable  {


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
    private TableColumn<Produto, String> columnCategoria;

    @FXML
    private TableColumn<Produto, String> columnEstoque;

    @FXML
    private TableColumn<Produto, String> columnIndice;

    @FXML
    private TableColumn<Produto, String> columnMarca;

    @FXML
    private TableColumn<Produto, String> columnNome;

    @FXML
    private TableColumn<Produto, String> columnPrecoUN;

    @FXML
    private TableView<Produto> tableProdutos;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Label txtUser;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarTableFornecedor();
        
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

	
	private ObservableList<Produto> ArrayProdutos;
    private void carregarTableFornecedor() {
    	ProdutoDAO produtoDAO = new ProdutoDAO(); 
    	ArrayProdutos = FXCollections.observableArrayList(produtoDAO.read());
        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        columnPrecoUN.setCellValueFactory(new PropertyValueFactory<>("precoUn"));
        columnEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        
        tableProdutos.setItems(ArrayProdutos);
    }
	
}
