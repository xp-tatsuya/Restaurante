package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.FornecedorDAO;
import Model.Fornecedor;
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
import javafx.scene.input.MouseEvent;

public class controllerFornecedor implements Initializable{

    @FXML
    private Button btAdicionar;

    @FXML
    private Button btCardapio;

    @FXML
    private Button btEditar;

    @FXML
    private Button btExcluir;

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
    private TableColumn<Fornecedor, String> columnCNPJ;

    @FXML
    private TableColumn<Fornecedor, String> columnEndereco;

    @FXML
    private TableColumn<Fornecedor, String> columnIndice;

    @FXML
    private TableColumn<Fornecedor, String> columnNome;

    @FXML
    private TableColumn<Fornecedor, String> columnTelefone;

    @FXML
    private TableView<Fornecedor> tableFornecedor;

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
    
	private ObservableList<Fornecedor> ArrayFornecedores;
    private void carregarTableFornecedor() {
    	FornecedorDAO fornecedorDAO =  new FornecedorDAO();
    	ArrayFornecedores = FXCollections.observableArrayList(fornecedorDAO.read());
        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        columnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        columnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        tableFornecedor.setItems(ArrayFornecedores);
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

    @FXML
    void OffMouseCardapio(MouseEvent event) {
    	btCardapio.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseFuncionario(MouseEvent event) {
    	btFuncionario.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseHome(MouseEvent event) {
    	btHome.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMousePedido(MouseEvent event) {
    	btPedido.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseSair(MouseEvent event) {
    	btSair.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }
    
    @FXML
    void OffMouseMesa(MouseEvent event) {
    	btMesa.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }
    
    @FXML
    void OffMouseProduto(MouseEvent event) {
    	btProduto.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseCardapio(MouseEvent event) {
    	btCardapio.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseFuncionario(MouseEvent event) {
    	btFuncionario.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseHome(MouseEvent event) {
    	btHome.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseMesa(MouseEvent event) {
    	btMesa.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMousePedido(MouseEvent event) {
    	btPedido.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseProduto(MouseEvent event) {
    	btProduto.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseSair(MouseEvent event) {
    	btSair.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

}
