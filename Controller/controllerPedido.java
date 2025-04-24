package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.MesaDAO;
import DAO.PedidoDAO;
import Model.Pedido;
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

public class controllerPedido implements Initializable{

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
    private TableColumn<Pedido, String> columnCondicao;

    @FXML
    private TableColumn<Pedido, String> columnCondicao1;

    @FXML
    private TableColumn<Pedido, String> columnData1;

    @FXML
    private TableColumn<Pedido, String> columnDesconto;

    @FXML
    private TableColumn<Pedido, String> columnDesconto1;

    @FXML
    private TableColumn<Pedido, String> columnIndice;

    @FXML
    private TableColumn<Pedido, String> columnIndice1;

    @FXML
    private TableColumn<Pedido, String> columnMesa;

    @FXML
    private TableColumn<Pedido, String> columnMesa1;

    @FXML
    private TableColumn<Pedido, String> columnNome;

    @FXML
    private TableColumn<Pedido, String> columnNome1;

    @FXML
    private TableColumn<Pedido, String> columnPrecoTotal;

    @FXML
    private TableColumn<Pedido, String> columnPrecoTotal1;

    @FXML
    private TableView<Pedido> tablePedido;

    @FXML
    private TableView<Pedido> tablePedido1;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Label txtUser;

    MesaDAO mesaDAO = new MesaDAO();
    PedidoDAO pedidoDAO = new PedidoDAO();
    
    private ObservableList<Pedido> ArrayPedidosPendentes;
    private ObservableList<Pedido> ArrayPedidosConcluidos;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	CarregarTabelaConcluidos();
    	CarregarTabelaPendentes();
    }
    
    public void nome(String nomeCompleto) {
        String[] partesNome = nomeCompleto.split(" ");

        String primeiroNome = partesNome[0];
        String ultimoNome = partesNome[partesNome.length - 1];

        String nomeFormatado = primeiroNome + " " + ultimoNome;
        txtUser.setText(nomeFormatado);
    }
    
    private void CarregarTabelaPendentes() {
        ArrayPedidosPendentes = FXCollections.observableArrayList(pedidoDAO.CarregarTablePendente());
        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        columnCondicao.setCellValueFactory(new PropertyValueFactory<>("condicao"));
        columnDesconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        columnPrecoTotal.setCellValueFactory(new PropertyValueFactory<>("precoTotal"));
        columnMesa.setCellValueFactory(new PropertyValueFactory<>("codeMesa"));
        tablePedido.setItems(ArrayPedidosPendentes);
    }
    
    private void CarregarTabelaConcluidos() {
        ArrayPedidosConcluidos = FXCollections.observableArrayList(pedidoDAO.CarregarTableConcluido());
        columnIndice1.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome1.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        columnCondicao1.setCellValueFactory(new PropertyValueFactory<>("condicao"));
        columnData1.setCellValueFactory(new PropertyValueFactory<>("dataPedido"));
        columnDesconto1.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        columnPrecoTotal1.setCellValueFactory(new PropertyValueFactory<>("precoTotal"));
        columnMesa1.setCellValueFactory(new PropertyValueFactory<>("codeMesa"));
        tablePedido1.setItems(ArrayPedidosConcluidos);
    }
    
    @FXML
    void ActionAdicionar(ActionEvent event) throws IOException {
    	Main.TelaAddPedido();
    }

    @FXML
    void ActionCardapio(ActionEvent event) throws IOException {
    	Main.changeScreen("Cardapio", controllerLogin.funcionario.getNome());
    }

    @FXML
    void ActionEditar(ActionEvent event) throws IOException {
    	Main.TelaAddPedido();
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
    void OffMouseFornecedor(MouseEvent event) {
    	btFonecedor.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
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
    void OffMouseMesa(MouseEvent event) {
    	btMesa.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
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
    void OnMouseHome(MouseEvent event) {
    	btHome.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseMesa(MouseEvent event) {
    	btMesa.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
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
