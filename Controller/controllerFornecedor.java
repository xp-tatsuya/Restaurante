package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import DAO.FornecedorDAO;
import DAO.PedidoDAO;
import Model.Fornecedor;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controllerFornecedor implements Initializable {

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

    @FXML private TableColumn<Fornecedor, String> columnIndice;
    @FXML private TableColumn<Fornecedor, String> columnNome;
    @FXML private TableColumn<Fornecedor, String> columnCNPJ;
    @FXML private TableColumn<Fornecedor, String> columnTelefone;
    @FXML private TableColumn<Fornecedor, String> columnEndereco;
    @FXML private TableView<Fornecedor> tableFornecedor;

    @FXML private TextField txtPesquisa;
    @FXML private Label txtUser;

    private ObservableList<Fornecedor> masterData = FXCollections.observableArrayList();
    
    private  FornecedorDAO fornecedorDAO = new FornecedorDAO();
    
    public static Fornecedor fornecedor = new Fornecedor();
    
    private AutoCompletionBinding<String> acb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CarregarTableFornecedor();
    }

    private void CarregarTableFornecedor() {
    	masterData.clear();
        masterData.setAll(fornecedorDAO.read());

        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        columnTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        columnEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        FilteredList<Fornecedor> filteredData = new FilteredList<>(masterData, f -> true);
        txtPesquisa.textProperty().addListener((obs, oldVal, newVal) -> {
            String lower = newVal.toLowerCase().trim();
            filteredData.setPredicate(fornecedor -> {
                if (lower.isEmpty()) return true;
                return fornecedor.getNome().toLowerCase().contains(lower)
                    || fornecedor.getCnpj().toLowerCase().contains(lower);
            });
        });

        SortedList<Fornecedor> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableFornecedor.comparatorProperty());
        tableFornecedor.setItems(sortedData);

        if(acb != null) {
        	acb.dispose();
        }
        
        ArrayList<String> nomes = fornecedorDAO.readFornecedorByNome();
        acb = TextFields.bindAutoCompletion(txtPesquisa, nomes);
    }

    public void nome(String nomeCompleto) {
        String[] partes = nomeCompleto.split(" ");
        String primeiro = partes[0];
        String ultimo = partes[partes.length - 1];
        txtUser.setText(primeiro + " " + ultimo);
    }

    @FXML
    void ActionAdicionar(ActionEvent event) throws IOException {
    	fornecedor = null;
    	Main.TelaAddFornecedor();
    	CarregarTableFornecedor();
    }

    @FXML
    void ActionSalvarFornecedor(ActionEvent event) throws IOException {
    }

    @FXML
    void ActionCardapio(ActionEvent event) throws IOException {
        Main.changeScreen("Cardapio", controllerLogin.funcionario.getNome(), 0);
    }

    @FXML void ActionEditar(ActionEvent event) throws IOException { 
    	int i = tableFornecedor.getSelectionModel().getSelectedIndex();
    	if(i == -1) {
    		Alerts.showAlert("Erro!", "Falha ao tentar editar", "Erro! Selecione um fornecedor para editar!", AlertType.ERROR);
    	}else {
    		fornecedor = tableFornecedor.getItems().get(i);
    		Main.TelaAddFornecedor();
    	}
    	CarregarTableFornecedor();
    }

    @FXML void ActionExcluir(ActionEvent event) {
    	int i = tableFornecedor.getSelectionModel().getSelectedIndex();
    	if(i == -1) {
    		Alerts.showAlert("Informação", "Nenhum fornecedor selecionado", "Selecione um fornecedor para excluir!", Alert.AlertType.INFORMATION);
    	}else {
    		fornecedor = tableFornecedor.getItems().get(i);
			Alert mensagemDeAviso = new Alert(Alert.AlertType.CONFIRMATION);
			mensagemDeAviso.setContentText("Tem certeza que deseja excluir o fornecedor " + fornecedor.getNome());
			
			Optional<ButtonType> resultado = mensagemDeAviso.showAndWait();

			if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
				fornecedorDAO.delete(fornecedor.getId());
				Alert mensagemDeExcluir = new Alert(Alert.AlertType.INFORMATION);
				mensagemDeExcluir.setContentText("Fornecedor excluido com sucesso!");
				mensagemDeExcluir.showAndWait();
				CarregarTableFornecedor();
			}
    	}
    }

    @FXML
    void ActionFornecedor(ActionEvent event) throws IOException {
        Main.changeScreen("Fornecedor", controllerLogin.funcionario.getNome(), 0);
    }
    @FXML void ActionFuncionario(ActionEvent event) throws IOException {
        Main.changeScreen("Funcionario", controllerLogin.funcionario.getNome(), 0);
    }
    @FXML
    void ActionHome(ActionEvent event) throws IOException {
        PedidoDAO pedidoDao = new PedidoDAO();
        Main.changeScreen("main", controllerLogin.funcionario.getNome(), pedidoDao.getTotalVendasMes());
    }
    @FXML void ActionMesa(ActionEvent event) throws IOException {
        Main.changeScreen("Mesa", controllerLogin.funcionario.getNome(), 0);
    }
    @FXML void ActionPedido(ActionEvent event) throws IOException {
        Main.changeScreen("Pedido", controllerLogin.funcionario.getNome(), 0);
    }
    @FXML void ActionProduto(ActionEvent event) throws IOException {
        Main.changeScreen("Produto", controllerLogin.funcionario.getNome(), 0);
    }
    @FXML void ActionSair(ActionEvent event) throws IOException {
        Main.changeScreen("Login", null, 0);
    }

    @FXML void OffMouseCardapio(MouseEvent event) { btCardapio.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;"); }
    @FXML void OffMouseFuncionario(MouseEvent event) { btFuncionario.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;"); }
    @FXML void OffMouseHome(MouseEvent event)      { btHome.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;"); }
    @FXML void OffMousePedido(MouseEvent event)    { btPedido.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;"); }
    @FXML void OffMouseSair(MouseEvent event)      { btSair.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;"); }
    @FXML void OffMouseMesa(MouseEvent event)      { btMesa.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;"); }
    @FXML void OffMouseProduto(MouseEvent event)   { btProduto.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;"); }

    @FXML void OnMouseCardapio(MouseEvent event)   { btCardapio.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OnMouseFuncionario(MouseEvent event){ btFuncionario.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OnMouseHome(MouseEvent event)       { btHome.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OnMouseMesa(MouseEvent event)       { btMesa.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OnMousePedido(MouseEvent event)     { btPedido.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OnMouseProduto(MouseEvent event)    { btProduto.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
    @FXML void OnMouseSair(MouseEvent event)       { btSair.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;"); }
}
