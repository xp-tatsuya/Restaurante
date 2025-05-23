package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import DAO.PedidoDAO;
import DAO.RegistroVendaDAO;
import Model.Pedido;
import Util.Alerts;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class controllerPedido implements Initializable {

    @FXML private Button btAdicionar;
    @FXML private Button btCardapio;
    @FXML private Button btEditar;
    @FXML private Button btExcluir;
    @FXML private Button btFonecedor;
    @FXML private Button btFuncionario;
    @FXML private Button btHome;
    @FXML private Button btMesa;
    @FXML private Button btProduto;
    @FXML private Button btSair;
    @FXML private Button btFinalizar;

    @FXML private TableColumn<Pedido, String> columnIndice;
    @FXML private TableColumn<Pedido, String> columnNome;
    @FXML private TableColumn<Pedido, String> columnCondicao;
    @FXML private TableColumn<Pedido, String> columnDesconto;
    @FXML private TableColumn<Pedido, String> columnPrecoTotal;
    @FXML private TableColumn<Pedido, String> columnMesa;
    @FXML private TableView<Pedido> tablePedido;

    @FXML private TableColumn<Pedido, String> columnIndice1;
    @FXML private TableColumn<Pedido, String> columnNome1;
    @FXML private TableColumn<Pedido, String> columnCondicao1;
    @FXML private TableColumn<Pedido, String> columnData1;
    @FXML private TableColumn<Pedido, String> columnDesconto1;
    @FXML private TableColumn<Pedido, String> columnPrecoTotal1;
    @FXML private TableColumn<Pedido, String> columnMesa1;
    @FXML private TableView<Pedido> tablePedido1;

    @FXML private Label txtUser;

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private RegistroVendaDAO rvDAO = new RegistroVendaDAO();
    private ObservableList<Pedido> pendentes, concluidos;
    public static Pedido pedido = new Pedido();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPendentes();
        loadConcluidos();
    }

    public void nome(String nomeCompleto) {
        String[] parts = nomeCompleto.split(" ");
        txtUser.setText(parts[0] + " " + parts[parts.length - 1]);
    }

    public void loadPendentes() {
        pendentes = FXCollections.observableArrayList(pedidoDAO.CarregarTablePendente());
        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        columnCondicao.setCellValueFactory(new PropertyValueFactory<>("condicao"));
        columnDesconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        columnPrecoTotal.setCellValueFactory(new PropertyValueFactory<>("precoTotal"));
        columnMesa.setCellValueFactory(new PropertyValueFactory<>("codeMesa"));
        tablePedido.setItems(pendentes);
    }

    private void loadConcluidos() {
        concluidos = FXCollections.observableArrayList(pedidoDAO.CarregarTableConcluido());
        columnIndice1.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome1.setCellValueFactory(new PropertyValueFactory<>("observacoes"));
        columnCondicao1.setCellValueFactory(new PropertyValueFactory<>("condicao"));
        columnData1.setCellValueFactory(new PropertyValueFactory<>("dataPedido"));
        columnDesconto1.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        columnPrecoTotal1.setCellValueFactory(new PropertyValueFactory<>("precoTotal"));
        columnMesa1.setCellValueFactory(new PropertyValueFactory<>("codeMesa"));
        tablePedido1.setItems(concluidos);
    }

    @FXML
    void ActionAdicionar(ActionEvent event) throws IOException {
        pedido = null;
        Main.TelaAddPedido();
        loadPendentes();
        loadConcluidos();
    }

    @FXML
    void ActionEditar(ActionEvent event) throws IOException {
        int i = tablePedido.getSelectionModel().getSelectedIndex();
        if (i == -1) {
            Alerts.showAlert("Erro!", "Falha ao tentar editar", "Selecione um pedido para editar!", AlertType.ERROR);
            return;
        }
        pedido = tablePedido.getItems().get(i);
        Main.TelaEditRegistroGarcon();
        loadPendentes();
        loadConcluidos();
    }

    @FXML
    void ActionExcluir(ActionEvent event) {
        // Verifica seleção em pendentes
        if (!tablePedido.getSelectionModel().isEmpty()) {
            excluirPedido(tablePedido, pendentes);
        }
        // Senão, verifica seleção em concluídos
        else if (!tablePedido1.getSelectionModel().isEmpty()) {
            excluirPedido(tablePedido1, concluidos);
        }
        else {
            Alerts.showAlert("Informação", "Nenhum pedido selecionado",
                             "Selecione um pedido para excluir!", AlertType.INFORMATION);
        }
    }

    private void excluirPedido(TableView<Pedido> tabela, ObservableList<Pedido> lista) {
        Pedido sel = tabela.getSelectionModel().getSelectedItem();
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Confirmação");
        confirm.setHeaderText(null);
        confirm.setContentText("Deseja excluir o pedido " + sel.getId() + " e todos os itens associados?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            rvDAO.deleteByPedido(sel.getId());
            pedidoDAO.delete(sel);
            loadPendentes();
            loadConcluidos();
            new Alert(AlertType.INFORMATION, "Pedido " + sel.getId() + " excluído com sucesso.").showAndWait();
        }
    }

    @FXML
    void ActionFinalizar(ActionEvent event) {
        Pedido sel = tablePedido.getSelectionModel().getSelectedItem();
        if (sel != null) {
            sel.setCondicao("Concluído");
            pedidoDAO.update(sel);
            loadPendentes();
            loadConcluidos();
            new Alert(AlertType.INFORMATION,
                      "Pedido ID " + sel.getId() + " finalizado com sucesso!").showAndWait();
        } else {
            new Alert(AlertType.WARNING, "Selecione um pedido para finalizar.").showAndWait();
        }
    }

    @FXML void ActionCardapio(ActionEvent e) throws IOException    { Main.changeScreen("Cardapio", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionFuncionario(ActionEvent e) throws IOException { Main.changeScreen("Funcionario", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionFornecedor(ActionEvent e) throws IOException { Main.changeScreen("Fornecedor", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionHome(ActionEvent e) throws IOException       { Main.changeScreen("main", controllerLogin.funcionario.getNome(), pedidoDAO.getTotalVendasMes()); }
    @FXML void ActionMesa(ActionEvent e) throws IOException       { Main.changeScreen("Mesa", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionProduto(ActionEvent e) throws IOException    { Main.changeScreen("Produto", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionSair(ActionEvent e) throws IOException       { Main.changeScreen("Login", null, 0); }

    @FXML void OffMouseCardapio(MouseEvent e)   { btCardapio.setStyle("-fx-background-color: #000; -fx-background-radius: 25;"); }
    @FXML void OnMouseCardapio(MouseEvent e)    { btCardapio.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OffMouseFornecedor(MouseEvent e){ btFonecedor.setStyle("-fx-background-color: #000; -fx-background-radius: 25;"); }
    @FXML void OnMouseFornecedor(MouseEvent e) { btFonecedor.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OffMouseFuncionario(MouseEvent e){ btFuncionario.setStyle("-fx-background-color: #000; -fx-background-radius: 25;"); }
    @FXML void OnMouseFuncionario(MouseEvent e) { btFuncionario.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OffMouseHome(MouseEvent e)       { btHome.setStyle("-fx-background-color: #000; -fx-background-radius: 25;"); }
    @FXML void OnMouseHome(MouseEvent e)        { btHome.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OffMouseMesa(MouseEvent e)       { btMesa.setStyle("-fx-background-color: #000; -fx-background-radius: 25;"); }
    @FXML void OnMouseMesa(MouseEvent e)        { btMesa.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OffMouseProduto(MouseEvent e)    { btProduto.setStyle("-fx-background-color: #000; -fx-background-radius: 25;"); }
    @FXML void OnMouseProduto(MouseEvent e)     { btProduto.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OffMouseSair(MouseEvent e)       { btSair.setStyle("-fx-background-color: #000; -fx-background-radius: 25;"); }
    @FXML void OnMouseSair(MouseEvent e)        { btSair.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
}