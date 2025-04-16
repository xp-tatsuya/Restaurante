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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		carregarTableFornecedor();
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
