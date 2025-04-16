package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.MesaDAO;
import Model.Mesa;
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

public class controllerMesa implements Initializable{


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

    @FXML
    private TableColumn<Mesa, String> columnCapacidade;

    @FXML
    private TableColumn<Mesa, String> columnCondicao;

    @FXML
    private TableColumn<Mesa, String> columnIndice;

    @FXML
    private TableView<Mesa> tableMesa;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Label txtUser;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	String nomeCompleto = controllerLogin.funcionario.getNome();
        String[] partesNome = nomeCompleto.split(" ");

        String primeiroNome = partesNome[0];
        String ultimoNome = partesNome[partesNome.length - 1];

        String nomeFormatado = primeiroNome + " " + ultimoNome;
        txtUser.setText(nomeFormatado);
        carregarTableMesa();
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


	private ObservableList<Mesa> ArrayMesas;
    private void carregarTableMesa() {
    	MesaDAO mesaDAO =  new MesaDAO();
        ArrayMesas = FXCollections.observableArrayList(mesaDAO.read());
        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
        columnCondicao.setCellValueFactory(new PropertyValueFactory<>("condicao"));
        
        tableMesa.setItems(ArrayMesas);
    }
}
