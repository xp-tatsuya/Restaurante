package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.CardapioDAO;
import Model.Cardapio;
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

public class controllerCardapio implements Initializable{

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
    private TableColumn<Cardapio, String> columnCategoria;

    @FXML
    private TableColumn<Cardapio, String> columnDescricao;

    @FXML
    private TableColumn<Cardapio, String> columnIndice;

    @FXML
    private TableColumn<Cardapio, String> columnNome;

    @FXML
    private TableColumn<Cardapio, String> columnPrecoUN;

    @FXML
    private TableView<Cardapio> tableCardapio;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private Label txtUser;

    private String nomeF;
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	carregarTableCardapio();
    }
    
    public void nome(String nomeCompleto) {
        String[] partesNome = nomeCompleto.split(" ");

        String primeiroNome = partesNome[0];
        String ultimoNome = partesNome[partesNome.length - 1];

        String nomeFormatado = primeiroNome + " " + ultimoNome;
        txtUser.setText(nomeFormatado);
        nomeF = nomeFormatado;
    }
    
	private ObservableList<Cardapio> ArrayCardapio;
    private void carregarTableCardapio() {
    	CardapioDAO cardapioDAO = new CardapioDAO();
    	ArrayCardapio = FXCollections.observableArrayList(cardapioDAO.read());
        columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        columnCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        columnPrecoUN.setCellValueFactory(new PropertyValueFactory<>("precoUnitario"));
        
        tableCardapio.setItems(ArrayCardapio);
    }

    
    @FXML
    void ActionAdicionar(ActionEvent event) throws IOException {
    	Main.TelaAddCardapio();
    	
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

    }

    @FXML
    void ActionFornecedor(ActionEvent event) throws IOException {
    	Main.changeScreen("Fornecedor", nomeF);
    }

    @FXML
    void ActionFuncionario(ActionEvent event) throws IOException {
    	Main.changeScreen("Funcionario", nomeF);
    }

    @FXML
    void ActionHome(ActionEvent event) throws IOException {
    	Main.changeScreen("main", nomeF);
    }

    @FXML
    void ActionMesa(ActionEvent event) throws IOException {
    	Main.changeScreen("Mesa", nomeF);
    }

    @FXML
    void ActionPedido(ActionEvent event) throws IOException {
    	Main.changeScreen("Pedido", nomeF);
    }

    @FXML
    void ActionProduto(ActionEvent event) throws IOException {
    	Main.changeScreen("Produto", nomeF);
    }

    @FXML
    void ActionSair(ActionEvent event) throws IOException {
    	Main.changeScreen("Sair", null);
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
