package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import DAO.CardapioDAO;
import DAO.Cardapio_PedidoDAO;
import DAO.FuncionarioDAO;
import DAO.MesaDAO;
import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import DAO.RegistroVendaDAO;
import Model.Cardapio_Pedido;
import Model.Mesa;
import Model.Pedido;
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
import javafx.scene.control.Alert.AlertType;
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
    	MesaDAO mesaDAO = new MesaDAO();
    	Mesa mesa = new Mesa();
    	Pedido pedido = new Pedido();
    	PedidoDAO pedidoDAO = new PedidoDAO();
    	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    	Cardapio_Pedido cp = new Cardapio_Pedido();
    	
    	mesa.setId(txtMesa.getText());
    	Mesa resultado = mesaDAO.verifycondicao(mesa);
    	System.out.println(resultado.getCondicao());
    	
    	if(resultado.getCondicao().equals("Livre")) {
    		
    		
    	
    		pedido.setCodeFuncionario(funcionarioDAO.getIdByNome(txtFuncionario.getText()));
    		pedido.setCodeMesa(resultado.getId());
    		pedido.setCondicao("Pendente");
    		
    		if(txtDesconto.getText() == null || txtDesconto.getText().trim().isEmpty()) {
    			pedido.setDesconto("0");
    		}else {
    			pedido.setDesconto(txtDesconto.getText());
    		}
    		
    		pedidoDAO.create(pedido);
    		
    		String idPedido = pedidoDAO.getIdByMesa(resultado.getId());
    		
    		CardapioDAO cardapioDAO = new CardapioDAO();
    		String idCardapio = cardapioDAO.getIdByNome(txtProduto.getText());
    		System.out.println(idPedido);
    		
    		
    		cp.setCodePedido(idPedido);
    		cp.setCodeCardapio(idCardapio);
    		cp.setObservacao(txtObs.getText());
    		cp.setQuantidade(txtQuantidade.getText());
    		
    		Cardapio_PedidoDAO cpDAO = new Cardapio_PedidoDAO();
    		cpDAO.create(cp);
    		
    		resultado.setCondicao("Ocupada");
    		mesaDAO.update(resultado);

    	}else {
    		Pedido pedidoByMesa = pedidoDAO.getByMesa(mesa);
    		System.out.println(pedidoByMesa.getCodeMesa());
    	}
    	CarregarTableProduto();
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
    	int i = tablePedido.getSelectionModel().getSelectedIndex();
    	if(i == -1) {
    		Alerts.showAlert("Informação", "Nenhum produto selecionado", "Selecione um produto para excluir!", Alert.AlertType.INFORMATION);
    	}else {
    		registroVenda = tablePedido.getItems().get(i);
    		System.out.println(registroVenda.getIdCardapioPedido());
    		
    		Alert mensagemDeAviso = new Alert(Alert.AlertType.CONFIRMATION);
			mensagemDeAviso.setContentText("Tem certeza que deseja excluir este produto? ");
			
			Optional<ButtonType> resultado = mensagemDeAviso.showAndWait();

			if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
				registroVendaDAO.delete(registroVenda);
				Alert mensagemDeExcluir = new Alert(Alert.AlertType.INFORMATION);
				mensagemDeExcluir.setContentText("Mesa excluida com sucesso!");
				mensagemDeExcluir.showAndWait();
				CarregarTableProduto();
    	}
    	}
	}

    @FXML
    void ActionFinalizar(ActionEvent event) {

    }
    
    public void CarregarTableProduto(){
    	String numeroMesa = txtMesa.getText();
		columnIndice.setCellValueFactory(new PropertyValueFactory<>("numeroPedido"));
		columnProduto.setCellValueFactory(new PropertyValueFactory<>("nomeCardapio"));
		columnObs.setCellValueFactory(new PropertyValueFactory<>("observacao"));
		columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		columnPreconUn.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
		columnTotalTabela.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
		
        ObservableList<RegistroVenda> lista = FXCollections.observableArrayList(registroVendaDAO.read(numeroMesa));
        tablePedido.setItems(lista);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		ArrayList<String> nomes = funcionarioDAO.readFuncionarioByNome();
        TextFields.bindAutoCompletion(txtFuncionario, nomes);
        
        MesaDAO mesaDAO = new MesaDAO();
        ArrayList<String> mesaIds = mesaDAO.readMesaById();
        TextFields.bindAutoCompletion(txtMesa, mesaIds);
        
        CardapioDAO cardapioDAO = new CardapioDAO();
        ArrayList<String> produtos = cardapioDAO.readCardapioByNome();
        TextFields.bindAutoCompletion(txtProduto, produtos);
		CarregarTableProduto();

	}

}
