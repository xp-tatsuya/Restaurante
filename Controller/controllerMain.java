package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.FuncionarioDAO;
import DAO.PedidoDAO;
import DAO.ProdutoDAO;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class controllerMain implements Initializable {

    @FXML
    private Button btCardapio;

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
    private TableColumn<Produto, String> columnDataFab;

    @FXML
    private TableColumn<Produto, String> columnDataVal;

    @FXML
    private TableColumn<Produto, String> columnEstoque;

    @FXML
    private TableColumn<Produto, String> columnIndice;

    @FXML
    private TableColumn<Produto, String> columnMarca;

    @FXML
    private TableColumn<Produto, String> columnNome;

    @FXML
    private TableColumn<Produto, String> columnCategoria2;

    @FXML
    private TableColumn<Produto, String> columnDataFab2;

    @FXML
    private TableColumn<Produto, String> columnDataVal2;

    @FXML
    private TableColumn<Produto, String> columnEstoque2;

    @FXML
    private TableColumn<Produto, String> columnIndice2;

    @FXML
    private TableColumn<Produto, String> columnMarca2;

    @FXML
    private TableColumn<Produto, String> columnNome2;

    @FXML
    private TableView<Produto> tableProdutoDataVal;

    @FXML
    private TableView<Produto> tableProdutoEstoque;

    @FXML
    private Label txtSalario;

    @FXML
    private Label txtUser;

    ProdutoDAO produtoDao = new ProdutoDAO();
    PedidoDAO pedidoDAO = new PedidoDAO();

    private ObservableList<Produto> ArrayProdutos;
    private ObservableList<Produto> ArrayProdutos2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String nomeCompleto = controllerLogin.funcionario.getNome();
        String[] partesNome = nomeCompleto.split(" ");

        String primeiroNome = partesNome[0];
        String ultimoNome = partesNome[partesNome.length - 1];

        String nomeFormatado = primeiroNome + " " + ultimoNome;
        txtUser.setText(nomeFormatado);

        double totalVendas = pedidoDAO.getTotalVendasMes();
        txtSalario.setText("R$ " + String.format("%,.2f", totalVendas));

        CarregarTableEstoqueAcabando();
        CarregarTableValidade();
    }

    @FXML
    void ActionCardapio(ActionEvent event) throws IOException {
    	Main.TelaCardapio();
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
    void ActionMesa(ActionEvent event) throws IOException {
    	Main.TelaMesa();
    }

    @FXML
    void ActionPedido(ActionEvent event) throws IOException {
    	Main.TelaPedido();
    }

    @FXML
    void ActionProduto(ActionEvent event) {}

    @FXML
    void ActionSair(ActionEvent event) throws IOException {
        Main.changeScreen("Login");
    }

    @FXML
    void OffMouseCardapio(MouseEvent event) {
        btCardapio.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseFornecedor(MouseEvent event) {
        btFonecedor.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseFuncionario(MouseEvent event) {
        btFuncionario.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseMesa(MouseEvent event) {
        btMesa.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMousePedido(MouseEvent event) {
        btPedido.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseProduto(MouseEvent event) {
        btProduto.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseSair(MouseEvent event) {
        btSair.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseCardapio(MouseEvent event) {
        btCardapio.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseFornecedor(MouseEvent event) {
        btFonecedor.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseFuncionario(MouseEvent event) {
        btFuncionario.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
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

    @FXML
    void columnDataFab(ActionEvent event) {}

    private void CarregarTableEstoqueAcabando() {
        ArrayProdutos = FXCollections.observableArrayList(produtoDao.EstoqueBaixo());
        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDataFab.setCellValueFactory(new PropertyValueFactory<>("dataFab"));
        columnDataVal.setCellValueFactory(new PropertyValueFactory<>("dataVal"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
        columnEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        tableProdutoEstoque.setItems(ArrayProdutos);
    }

    private void CarregarTableValidade() {
        ArrayProdutos2 = FXCollections.observableArrayList(produtoDao.BaixaVal());
        columnIndice2.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDataFab2.setCellValueFactory(new PropertyValueFactory<>("dataFab"));
        columnDataVal2.setCellValueFactory(new PropertyValueFactory<>("dataVal"));
        columnMarca2.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnCategoria2.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        columnEstoque2.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        tableProdutoDataVal.setItems(ArrayProdutos2);
    }
}
