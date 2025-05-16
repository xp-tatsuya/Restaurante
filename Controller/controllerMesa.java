package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import DAO.MesaDAO;
import DAO.PedidoDAO;
import Model.Mesa;
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

public class controllerMesa implements Initializable {

    @FXML private Button btAdicionar;
    @FXML private Button btCardapio;
    @FXML private Button btEditar;
    @FXML private Button btExcluir;
    @FXML private Button btPesquisar;
    @FXML private Button btFonecedor;
    @FXML private Button btFuncionario;
    @FXML private Button btHome;
    @FXML private Button btMesa;
    @FXML private Button btPedido;
    @FXML private Button btProduto;
    @FXML private Button btSair;

    @FXML private TableColumn<Mesa, String> columnIndice;
    @FXML private TableColumn<Mesa, String> columnCapacidade;
    @FXML private TableColumn<Mesa, String> columnCondicao;
    @FXML private TableView<Mesa> tableMesa;

    @FXML private TextField txtPesquisa;
    @FXML private Label txtUser;

    private ObservableList<Mesa> masterData = FXCollections.observableArrayList();

    public static Mesa mesa = new Mesa();
    
    private MesaDAO mesaDAO = new MesaDAO();
    
    private PedidoDAO pedidoDAO = new PedidoDAO();
    
    private AutoCompletionBinding<String> acb;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CarregarTableMesa();
        mesa = null;
    }
    
    public void CarregarTableMesa() {
    	masterData.clear();
        masterData.addAll(mesaDAO.read());

        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        columnCondicao.setCellValueFactory(new PropertyValueFactory<>("condicao"));

        FilteredList<Mesa> filteredData = new FilteredList<>(masterData, p -> true);
        txtPesquisa.textProperty().addListener((obs, oldValue, newValue) -> {
            String lower = newValue.toLowerCase().trim();
            filteredData.setPredicate(mesa -> {
                if (lower.isEmpty()) return true;
                return mesa.getId().toLowerCase().contains(lower)
                    || mesa.getCondicao().toLowerCase().contains(lower);
            });
        });

        SortedList<Mesa> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableMesa.comparatorProperty());
        tableMesa.setItems(sortedData);

        if (acb != null) {
        	acb.dispose();
        }
        ArrayList<String> id = mesaDAO.readMesaById();
        acb = TextFields.bindAutoCompletion(txtPesquisa, id);
    }

    public void nome(String nomeCompleto) {
        String[] partesNome = nomeCompleto.split(" ");
        String primeiroNome = partesNome[0];
        String ultimoNome = partesNome[partesNome.length - 1];
        txtUser.setText(primeiroNome + " " + ultimoNome);
    }

    @FXML
    void ActionAdicionar(ActionEvent event) throws IOException {
    	Main.TelaAddMesa();
    	CarregarTableMesa();
    }

    @FXML
    void ActionCardapio(ActionEvent event) throws IOException {
        Main.changeScreen("Cardapio", controllerLogin.funcionario.getNome(), 0);
    }
    

    @FXML
    void ActionEditar(ActionEvent event) throws IOException {
    	int i = tableMesa.getSelectionModel().getSelectedIndex();
    	if(i == -1) {
    		Alerts.showAlert("Erro!", "Falha ao tentar editar", "Erro! Selecione um mesa para editar!", AlertType.ERROR);
    	}else {
    		mesa = tableMesa.getItems().get(i);
    		Main.TelaAddMesa();
    	} 
    	CarregarTableMesa();
    	
    }

    @FXML
    void ActionExcluir(ActionEvent event) {
    	int i = tableMesa.getSelectionModel().getSelectedIndex();
    	if(i == -1) {
    		Alerts.showAlert("Informação", "Nenhuma mesa selecionado", "Selecione uma mesa para excluir!", Alert.AlertType.INFORMATION);
    	}else {
    		mesa = tableMesa.getItems().get(i);
    		
    		Alert mensagemDeAviso = new Alert(Alert.AlertType.CONFIRMATION);
			mensagemDeAviso.setContentText("Tem certeza que deseja excluir a mesa " + mesa.getId());
			
			Optional<ButtonType> resultado = mensagemDeAviso.showAndWait();

			if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
				mesaDAO.delete(mesa.getId());
				Alert mensagemDeExcluir = new Alert(Alert.AlertType.INFORMATION);
				mensagemDeExcluir.setContentText("Mesa excluida com sucesso!");
				mensagemDeExcluir.showAndWait();
				CarregarTableMesa();
			}
		}
	}

    @FXML
    void ActionPesquisar(ActionEvent event) {
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
    	String acesso = controllerLogin.funcionario.getVerificarAcesso();
    	if(acesso.equals("2")) {
    		Alerts.showAlert("Erro", "Acesso negado!", "Apenas proprietarios podem acessar esta tela", AlertType.ERROR);
    	}else {
    		Main.changeScreen("main", controllerLogin.funcionario.getNome(), pedidoDAO.getTotalVendasMes());
    	}
        
    }

    @FXML
    void ActionPedido(ActionEvent event) throws IOException {
        Main.changeScreen("Pedido", controllerLogin.funcionario.getNome(), 0);
    }

    @FXML
    void ActionProduto(ActionEvent event) throws IOException {
        Main.changeScreen("Produto", controllerLogin.funcionario.getNome(), 0);
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
    void OnMouseHome(MouseEvent event) {
        btHome.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
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