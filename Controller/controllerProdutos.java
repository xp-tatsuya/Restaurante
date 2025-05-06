package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import Model.Produto;
import Util.Alerts;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class controllerProdutos implements Initializable {

    @FXML private Button btAdicionar;
    @FXML private Button btCardapio;
    @FXML private Button btEditar;
    @FXML private Button btExcluir;
    @FXML private Button btFonecedor;
    @FXML private Button btFuncionario;
    @FXML private Button btHome;
    @FXML private Button btMesa;
    @FXML private Button btPedido;
    @FXML private Button btProduto;
    @FXML private Button btSair;

    @FXML private TableColumn<Produto, String> columnCategoria;
    @FXML private TableColumn<Produto, String> columnEstoque;
    @FXML private TableColumn<Produto, String> columnIndice;
    @FXML private TableColumn<Produto, String> columnMarca;
    @FXML private TableColumn<Produto, String> columnNome;
    @FXML private TableColumn<Produto, String> columnPrecoUN;
    @FXML private TableView<Produto> tableProdutos;

    @FXML private TextField txtPesquisa;
    @FXML private Label txtUser;

    private ObservableList<Produto> masterData = FXCollections.observableArrayList();
    
    private AutoCompletionBinding<String> acb;
    
    ProdutoDAO produtoDAO = new ProdutoDAO();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	CarregarTableProduto();
    }
    
    public void CarregarTableProduto() {
    	masterData.clear();
        masterData.addAll(produtoDAO.read());

        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        columnPrecoUN.setCellValueFactory(new PropertyValueFactory<>("precoUn"));
        columnEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));

        FilteredList<Produto> filteredData = new FilteredList<>(masterData, p -> true);

        txtPesquisa.textProperty().addListener((obs, oldValue, newValue) -> {
            String lower = newValue.toLowerCase().trim();
            filteredData.setPredicate(produto -> {
                if (lower.isEmpty()) {
                    return true;
                }
                return produto.getNome().toLowerCase().contains(lower);
            });
        });

        SortedList<Produto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableProdutos.comparatorProperty());

        tableProdutos.setItems(sortedData);
        
        if (acb != null) {
        	acb.dispose();
        }
        
        ArrayList<String> nomeProdutos = produtoDAO.readProdutoByNome();
        acb = TextFields.bindAutoCompletion(txtPesquisa, nomeProdutos);
    }

    public void nome(String nomeCompleto) {
        String[] partesNome = nomeCompleto.split(" ");
        String primeiroNome = partesNome[0];
        String ultimoNome = partesNome[partesNome.length - 1];
        txtUser.setText(primeiroNome + " " + ultimoNome);
    }

    @FXML
    void ActionAdicionar(ActionEvent event) throws IOException {
    	Main.TelaAddProduto();
    }
    	
    @FXML
    void ActionCardapio(ActionEvent event) throws IOException {
        Main.changeScreen("Cardapio", controllerLogin.funcionario.getNome(), 0);
    }

    @FXML
    void ActionEditar(ActionEvent event) {
    }

    @FXML
    void ActionExcluir(ActionEvent event) {
    	int i = tableProdutos.getSelectionModel().getSelectedIndex();
    	if(i == -1){
    		Alerts.showAlert("Informação", "Nenhum produto selecionado", "Selecione um produto para excluir!", Alert.AlertType.INFORMATION);
    	}else {
    		Produto produto = new Produto();
    		produto = tableProdutos.getItems().get(i);
    		Alert mensagemDeAviso = new Alert(Alert.AlertType.CONFIRMATION);
			mensagemDeAviso.setContentText("Tem certeza que deseja excluir o produto " + produto.getNome());
			
			Optional<ButtonType> resultado = mensagemDeAviso.showAndWait();

			if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
				produtoDAO.delete(produto.getId());
				Alert mensagemDeExcluir = new Alert(Alert.AlertType.INFORMATION);
				mensagemDeExcluir.setContentText("Produto excluido com sucesso!");
				mensagemDeExcluir.showAndWait();
				CarregarTableProduto();
			}
    	}
    }

    @FXML
    void ActionFornecedor(ActionEvent event) throws IOException {
        Main.changeScreen("Fornecedor", controllerLogin.funcionario.getNome(), 0);
    }

    @FXML
    void ActionFuncionario(ActionEvent event) throws IOException {
        Main.changeScreen("Funcionario", controllerLogin.funcionario.getNome(), 0);
    }

    @FXML
    void ActionHome(ActionEvent event) throws IOException {
    	PedidoDAO pedidoDAO = new PedidoDAO();
        Main.changeScreen("main", controllerLogin.funcionario.getNome(), pedidoDAO.getTotalVendasMes());
    }

    @FXML
    void ActionMesa(ActionEvent event) throws IOException {
        Main.changeScreen("Mesa", controllerLogin.funcionario.getNome(), 0);
    }

    @FXML
    void ActionPedido(ActionEvent event) throws IOException {
        Main.changeScreen("Pedido", controllerLogin.funcionario.getNome(), 0);
    }

    @FXML
    void ActionSair(ActionEvent event) throws IOException {
        Main.changeScreen("Login", null, 0);
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
    void OffMousePedido(MouseEvent event) {
        btPedido.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
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
    void OnMousePedido(MouseEvent event) {
        btPedido.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseSair(MouseEvent event) {
        btSair.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void actionProdutoClick(MouseEvent event) {
    }

    @FXML
    void actionProdutoType(KeyEvent event) {
    }
}
