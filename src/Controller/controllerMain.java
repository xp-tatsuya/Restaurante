package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.ProdutoDAO;
import Model.Produto;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class controllerMain implements Initializable{

    @FXML
    private Button btCardapio;

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
    private TableColumn<Produto, String> columnCategoria;

    @FXML
    private TableColumn<Produto, String> columnDataFab;

    @FXML
    private TableColumn<Produto, String> columnDataVal;

    @FXML
    private TableColumn<Produto, String> columnEstoque;

    @FXML
    private TableColumn<Produto, String> columnIndice;

    @FXML
    private TableColumn<Produto, String> columnMarca;

    @FXML
    private TableColumn<Produto, String> columnNome;

    @FXML
    private TableView<Produto> tableProdutoDataVal;

    @FXML
    private TableView<Produto> tableProdutoEstoque;

    @FXML
    private Label txtSalario;

    @FXML
    private Label txtUser;
    
    ProdutoDAO produtoDao = new ProdutoDAO();
    
    private ObservableList<Produto> ArrayProdutos;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	CarregarTableEstoqueAcabando();
    }

    @FXML
    void ActionCardapio(ActionEvent event) {

    }

    @FXML
    void ActionFornecedor(ActionEvent event) {

    }

    @FXML
    void ActionFuncionario(ActionEvent event) {

    }

    @FXML
    void ActionMesa(ActionEvent event) {

    }

    @FXML
    void ActionPedido(ActionEvent event) {

    }

    @FXML
    void ActionProduto(ActionEvent event) {

    }

    @FXML
    void ActionSair(ActionEvent event) throws IOException {
    	Main.changeScreen("Login");
    }

    @FXML
    void OffMouseCardapio(MouseEvent event) {
    	btCardapio.setStyle("-fx-background-color:   #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseFornecedor(MouseEvent event) {
    	btFonecedor.setStyle("-fx-background-color:   #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseFuncionario(MouseEvent event) {
    	btFuncionario.setStyle("-fx-background-color:   #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseMesa(MouseEvent event) {
    	btMesa.setStyle("-fx-background-color:   #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMousePedido(MouseEvent event) {
    	btPedido.setStyle("-fx-background-color:   #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseProduto(MouseEvent event) {
    	btProduto.setStyle("-fx-background-color:   #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OffMouseSair(MouseEvent event) {
    	btSair.setStyle("-fx-background-color:   #000000; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseCardapio(MouseEvent event) {
    	btCardapio.setStyle("-fx-background-color:  #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseFornecedor(MouseEvent event) {
    	btFonecedor.setStyle("-fx-background-color:  #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseFuncionario(MouseEvent event) {
    	btFuncionario.setStyle("-fx-background-color:  #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseMesa(MouseEvent event) {
    	btMesa.setStyle("-fx-background-color:  #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMousePedido(MouseEvent event) {
    	btPedido.setStyle("-fx-background-color:  #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseProduto(MouseEvent event) {
    	btProduto.setStyle("-fx-background-color:  #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void OnMouseSair(MouseEvent event) {
    	btSair.setStyle("-fx-background-color:  #A71D1D; -fx-background-radius: 25;");
    }

    @FXML
    void columnDataFab(ActionEvent event) {

    }
    
    private void CarregarTableEstoqueAcabando() {
    	ArrayProdutos = FXCollections.observableArrayList(produtoDao.EstoqueBaixo());
    	columnIndice.setCellValueFactory(new PropertyValueFactory<>("id"));
    	columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	columnDataFab.setCellValueFactory(new PropertyValueFactory<>("dataFab"));
    	columnDataVal.setCellValueFactory(new PropertyValueFactory<>("dataVal"));
    	columnMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
    	columnCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
    	columnEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
    	tableProdutoEstoque.setItems(ArrayProdutos);
    }

}
