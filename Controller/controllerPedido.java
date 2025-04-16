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

public class controllerPedido implements Initializable {

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

    // Colunas para pedidos pendentes (tablePedido)
    @FXML
    private TableColumn<Pedido, String> columnIndice;    
    @FXML
    private TableColumn<Pedido, String> columnNome;
    @FXML
    private TableColumn<Pedido, String> columnCondicao;
    @FXML
    private TableColumn<Pedido, String> columnDesconto;
    @FXML
    private TableColumn<Pedido, String> columnPrecoTotal;
    @FXML
    private TableColumn<Pedido, String> columnMesa;

    // Colunas para pedidos concluídos (tablePedido1)
    @FXML
    private TableColumn<Pedido, String> columnIndice1;    
    @FXML
    private TableColumn<Pedido, String> columnNome1;
    @FXML
    private TableColumn<Pedido, String> columnCondicao1;
    @FXML
    private TableColumn<Pedido, String> columnData1;
    @FXML
    private TableColumn<Pedido, String> columnDesconto1;
    @FXML
    private TableColumn<Pedido, String> columnPrecoTotal1;
    @FXML
    private TableColumn<Pedido, String> columnMesa1;

    @FXML
    private TableView<Pedido> tablePedido;   // Tabela para pedidos pendentes
    @FXML
    private TableView<Pedido> tablePedido1;  // Tabela para pedidos concluídos

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
        String nomeCompleto = controllerLogin.funcionario.getNome();
        String[] partesNome = nomeCompleto.split(" ");
        String nomeFormatado = partesNome[0] + " " + partesNome[partesNome.length - 1];
        txtUser.setText(nomeFormatado);
        
        CarregarTabelaPendentes();
        CarregarTabelaConcluidos();
    }

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
}
