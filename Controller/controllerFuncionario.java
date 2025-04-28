package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.controlsfx.control.textfield.TextFields;
import DAO.FuncionarioDAO;
import Model.Funcionario;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

public class controllerFuncionario implements Initializable {

    @FXML private Button btAdicionar;
    @FXML private Button btCardapio;
    @FXML private Button btEditar;
    @FXML private Button btExcluir;
    @FXML private Button btFonecedor;
    @FXML private Button btHome;
    @FXML private Button btMesa;
    @FXML private Button btPedido;
    @FXML private Button btProduto;
    @FXML private Button btSair;

    @FXML private TableColumn<Funcionario, String> columnIndice;
    @FXML private TableColumn<Funcionario, String> columnNome;
    @FXML private TableColumn<Funcionario, String> columnCPF;
    @FXML private TableColumn<Funcionario, String> columnEmail;
    @FXML private TableColumn<Funcionario, String> columnCargo;
    @FXML private TableView<Funcionario> tableFuncionario;

    @FXML private TextField txtPesquisa;
    @FXML private Label txtUser;

    private ObservableList<Funcionario> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        masterData.addAll(funcionarioDAO.read());

        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));

        FilteredList<Funcionario> filteredData = new FilteredList<>(masterData, p -> true);
        txtPesquisa.textProperty().addListener((obs, oldValue, newValue) -> {
            String lower = newValue.toLowerCase().trim();
            filteredData.setPredicate(funcionario -> {
                if (lower.isEmpty()) return true;
                return funcionario.getNome().toLowerCase().contains(lower)
                    || funcionario.getCpf().toLowerCase().contains(lower);
            });
        });

        SortedList<Funcionario> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableFuncionario.comparatorProperty());
        tableFuncionario.setItems(sortedData);

        ArrayList<String> nomes = funcionarioDAO.readFuncionarioByNome();
        TextFields.bindAutoCompletion(txtPesquisa, nomes);
    }

    public void nome(String nomeCompleto) {
        String[] partes = nomeCompleto.split(" ");
        String primeiro = partes[0];
        String ultimo = partes[partes.length - 1];
        txtUser.setText(primeiro + " " + ultimo);
    }

    @FXML
    void ActionAdicionar(ActionEvent event) throws IOException {
    	Main.TelaAddFuncionario();
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
    void OffMouseFornecedor(MouseEvent event) {
        btFonecedor.setStyle("-fx-background-color: #000000; -fx-background-radius: 25;");
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
    void OnMouseCardapio(MouseEvent event) {
        btCardapio.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
    }
    
    @FXML
    void OnMouseFornecedor(MouseEvent event) {
        btFonecedor.setStyle("-fx-background-color: #A71D1D; -fx-background-radius: 25;");
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