package Controller;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import DAO.CardapioDAO;
import DAO.PedidoDAO;
import Model.Cardapio;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controllerCardapio implements Initializable {

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
	private TableColumn<Cardapio, String> columnIndice;
	@FXML
	private TableColumn<Cardapio, String> columnNome;
	@FXML
	private TableColumn<Cardapio, String> columnDescricao;
	@FXML
	private TableColumn<Cardapio, String> columnCategoria;
	@FXML
	private TableColumn<Cardapio, String> columnPrecoUN;
	@FXML
	private TableView<Cardapio> tableCardapio;

	@FXML
	private TextField txtPesquisa;
	@FXML
	private Label txtUser;

	private ObservableList<Cardapio> masterData = FXCollections.observableArrayList();
	private String nomeF;
	
	CardapioDAO cardapioDAO = new CardapioDAO();
	
	Cardapio cardapio = new Cardapio();

	private AutoCompletionBinding<String> acb;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CarregarTableCardapio();
	}
	
	public void CarregarTableCardapio() {
		masterData.clear();
		masterData.addAll(cardapioDAO.read());

		columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		columnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		columnPrecoUN.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));

		FilteredList<Cardapio> filteredData = new FilteredList<>(masterData, p -> true);
		txtPesquisa.textProperty().addListener((obs, oldValue, newValue) -> {
			String lower = newValue.toLowerCase().trim();
			filteredData.setPredicate(cardapio -> {
				if (lower.isEmpty())
					return true;
				return cardapio.getNome().toLowerCase().contains(lower)
						|| cardapio.getCategoria().toLowerCase().contains(lower);
			});
		});

		SortedList<Cardapio> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableCardapio.comparatorProperty());
		tableCardapio.setItems(sortedData);

		if(acb != null) {
			acb.dispose();
		}
		ArrayList<String> nomes = cardapioDAO.readCardapioByNome();
		acb = TextFields.bindAutoCompletion(txtPesquisa, nomes);
	}

	public void nome(String nomeCompleto) {
		String[] partesNome = nomeCompleto.split(" ");
		String primeiroNome = partesNome[0];
		String ultimoNome = partesNome[partesNome.length - 1];
		txtUser.setText(primeiroNome + " " + ultimoNome);
		nomeF = primeiroNome + " " + ultimoNome;
	}

	@FXML
	void ActionAdicionar(ActionEvent event) throws IOException {
		Main.showAddCardapioDialog();
		CarregarTableCardapio();
	}

	@FXML
	void ActionCardapio(ActionEvent event) {
	}

	@FXML
	void ActionEditar(ActionEvent event) throws IOException {
		Main.TelaAddCardapio();
	}

	@FXML
	void ActionExcluir(ActionEvent event) {
		int i = tableCardapio.getSelectionModel().getSelectedIndex();
		if (i == -1) {
			Alerts.showAlert("Informação", "Nenhum prato selecionado", "Selecione um prato para excluir!", Alert.AlertType.INFORMATION);
		} else {
			cardapio = tableCardapio.getItems().get(i);
			Alert mensagemDeAviso = new Alert(Alert.AlertType.CONFIRMATION);
			mensagemDeAviso.setContentText("Tem certeza que deseja excluir o prato " + cardapio.getNome());
			
			Optional<ButtonType> resultado = mensagemDeAviso.showAndWait();

			if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
				cardapioDAO.delete(cardapio.getId());
				Alert mensagemDeExcluir = new Alert(Alert.AlertType.INFORMATION);
				mensagemDeExcluir.setContentText("Prato excluido com sucesso!");
				mensagemDeExcluir.showAndWait();
				CarregarTableCardapio();
			}
			
			}
		}

	@FXML
	void ActionFornecedor(ActionEvent event) throws IOException {
		Main.changeScreen("Fornecedor", nomeF, 0);
	}

	@FXML
	void ActionFuncionario(ActionEvent event) throws IOException {
		Main.changeScreen("Funcionario", nomeF, 0);
	}

	@FXML
	void ActionHome(ActionEvent event) throws IOException {
		PedidoDAO pedidoDAO = new PedidoDAO();
		Main.changeScreen("main", nomeF, pedidoDAO.getTotalVendasMes());
	}

	@FXML
	void ActionMesa(ActionEvent event) throws IOException {
		Main.changeScreen("Mesa", nomeF, 0);
	}

	@FXML
	void ActionPedido(ActionEvent event) throws IOException {
		Main.changeScreen("Pedido", nomeF, 0);
	}

	@FXML
	void ActionProduto(ActionEvent event) throws IOException {
		Main.changeScreen("Produto", nomeF, 0);
	}

	@FXML
	void ActionSair(ActionEvent event) throws IOException {
		Main.changeScreen("Sair", null, 0);
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
	void OffMouseProduto(MouseEvent event) {
		btProduto.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
	}

	@FXML
	void OffMouseSair(MouseEvent event) {
		btSair.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
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
	void OnMouseProduto(MouseEvent event) {
		btProduto.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
	}

	@FXML
	void OnMouseSair(MouseEvent event) {
		btSair.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
	}
}