package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import DAO.RegistroVendaDAO;
import Model.Produto;
import Model.RegistroVenda;
import Util.Alerts;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class controllerRegistroGarcons implements Initializable{

    @FXML
    private Button btAdicionar;

    @FXML
    private Button btAdm;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btExcluir;

    @FXML
    private Button btFinalizar;

    @FXML
    private TableColumn<String, RegistroVenda> columnIndice;

    @FXML
    private TableColumn<String, RegistroVenda> columnObs;

    @FXML
    private TableColumn<String, RegistroVenda> columnPreconUn;

    @FXML
    private TableColumn<String, RegistroVenda> columnProduto;

    @FXML
    private TableColumn<String, RegistroVenda> columnQuantidade;

    @FXML
    private TableColumn<String, RegistroVenda> columnTotalTabela;

    @FXML
    private Label labelDesconto;

    @FXML
    private Label labelTotal;

    @FXML
    private TableView<RegistroVenda> tablePedido;

    @FXML
    private TextField txtDesconto;

    @FXML
    private TextField txtFuncionario;

    @FXML
    private TextField txtMesa;

    @FXML
    private TextField txtObs;

    @FXML
    private TextField txtProduto;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private TextField txtSenha;

    
    
    RegistroVendaDAO registroVendaDAO = new RegistroVendaDAO();
    
    RegistroVenda registroVenda = new RegistroVenda();
    
    
    @FXML
    void ActionAdicionar(ActionEvent event) {

    }

    @FXML
    void ActionAdm(ActionEvent event) throws IOException {
    	Main.TelaLogin();
    }

    @FXML
    void ActionCancelar(ActionEvent event) {

    }

    @FXML
	void ActionExcluir(ActionEvent event) {

	}

    @FXML
    void ActionFinalizar(ActionEvent event) {

    }
    
    public void CarregarTableProduto(){
    	String numeroMesa = txtMesa.getText();
		columnIndice.setCellValueFactory(new PropertyValueFactory<>("numeroPedido"));
		columnProduto.setCellValueFactory(new PropertyValueFactory<>("nomeCardapio"));
		columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		columnPreconUn.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
		columnTotalTabela.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
		
        ObservableList<RegistroVenda> lista = FXCollections.observableArrayList(registroVendaDAO.read(numeroMesa));
        tablePedido.setItems(lista);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		CarregarTableProduto();

	}

}
