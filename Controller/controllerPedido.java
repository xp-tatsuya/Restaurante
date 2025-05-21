package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import DAO.PedidoDAO;
import Model.Funcionario;
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
import javafx.stage.Stage;

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

    private void loadPendentes() {
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
    }

    @FXML void ActionEditar(ActionEvent event) throws IOException {
    }

    @FXML
    void ActionExcluir(ActionEvent event) {
        int idx = tablePedido.getSelectionModel().getSelectedIndex();
        if (idx == -1) {
            Alerts.showAlert(
                "Informação",
                "Nenhum pedido selecionado",
                "Selecione um pedido para excluir!",
                Alert.AlertType.INFORMATION
            );
            return;
        }

        Pedido sel = tablePedido.getItems().get(idx);

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("Tem certeza que deseja excluir o pedido " + sel.getId() + "?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            pedidoDAO.delete(sel);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setContentText("Pedido excluído com sucesso!");
            info.showAndWait();
            loadPendentes();
            loadConcluidos();
        }
    }

    @FXML void ActionCardapio(ActionEvent e) throws IOException { Main.changeScreen("Cardapio", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionFuncionario(ActionEvent e) throws IOException { Main.changeScreen("Funcionario", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionFornecedor(ActionEvent e) throws IOException { Main.changeScreen("Fornecedor", controllerLogin.funcionario.getNome(), 0); }
    
    @FXML void ActionHome(ActionEvent e) throws IOException      {
    	String acesso = controllerLogin.funcionario.getVerificarAcesso();
    	if(acesso.equals("2")) {
    		Alerts.showAlert("Erro", "Acesso negado!", "Apenas proprietarios podem acessar esta tela", AlertType.ERROR);
    	}else {
    		Main.changeScreen("main", controllerLogin.funcionario.getNome(), pedidoDAO.getTotalVendasMes());
    	}
    	 
    	
    	}
    
    @FXML void ActionMesa(ActionEvent e) throws IOException      { Main.changeScreen("Mesa", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionProduto(ActionEvent e) throws IOException   { Main.changeScreen("Produto", controllerLogin.funcionario.getNome(), 0); }
    @FXML void ActionSair(ActionEvent e) throws IOException      { Main.changeScreen("Login", null, 0); }
    
    @FXML
    void ActionFinalizar(ActionEvent event) {
        Pedido sel = tablePedido.getSelectionModel().getSelectedItem();
        if (sel != null) {
            sel.setCondicao("Concluído");
            pedidoDAO.update(sel);
            loadPendentes();
            loadConcluidos();
            
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Pedido Finalizado");
            info.setHeaderText(null);
            info.setContentText("O pedido ID " + sel.getId() + " foi finalizado com sucesso!");
            info.showAndWait();
        } else {
            new Alert(Alert.AlertType.WARNING, "Selecione um pedido para finalizar.").showAndWait();
        }
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
