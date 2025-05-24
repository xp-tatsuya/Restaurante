package Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.controlsfx.control.textfield.TextFields;

import DAO.CardapioDAO;
import DAO.Cardapio_PedidoDAO;
import DAO.FuncionarioDAO;
import DAO.MesaDAO;
import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import DAO.RegistroVendaDAO;
import Model.Cardapio;
import Model.Cardapio_Pedido;
import Model.Mesa;
import Model.Pedido;
import Model.Produto;
import Model.RegistroVenda;
import Util.Alerts;
import application.Main;
import javafx.beans.property.ReadOnlyStringWrapper;
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
    private TableColumn<RegistroVenda, String> columnIndice;

    @FXML
    private TableColumn<RegistroVenda, String> columnObs;

    @FXML
    private TableColumn<RegistroVenda, String> columnPreconUn;

    @FXML
    private TableColumn<RegistroVenda, String> columnProduto;

    @FXML
    private TableColumn<RegistroVenda, String> columnQuantidade;

    @FXML
    private TableColumn<RegistroVenda, String> columnTotalTabela;

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

    
    private  ObservableList<RegistroVenda> listaTemp = FXCollections.observableArrayList();
    
    private String subTotal = "0";
    
    double descontoAcumulado = 0;
    
    @FXML
    void ActionAdicionar(ActionEvent event) {
    	String nomeProduto = txtProduto.getText();
    	String quantidade = txtQuantidade.getText();
    	String observacao = txtObs.getText();
    	
    	Cardapio cardapio = new CardapioDAO().getByNome(nomeProduto);
    	RegistroVenda item = new RegistroVenda();
    	
    	item.setCodigoCardapio(cardapio.getId());
    	item.setNomeCardapio(cardapio.getNome());
    	item.setValorUnitario(cardapio.getPrecoUnitario());
    	item.setQuantidade(quantidade);
    	item.setObservacao(observacao);
    	
    	listaTemp.add(item);
    	atualizarValores();
    	
    	txtProduto.clear();
    	txtQuantidade.clear();
    	txtObs.clear();
    	txtDesconto.clear();
    }

    @FXML
    void ActionAdm(ActionEvent event) throws IOException {
    	Main.TelaLogin();
    }

    @FXML
    void ActionCancelar(ActionEvent event) {
    	limparTudo();
    }

    @FXML
	void ActionExcluir(ActionEvent event) {
    	RegistroVenda item = tablePedido.getSelectionModel().getSelectedItem();
    	if(item != null) {
    		listaTemp.remove(item);
    		atualizarValores();
    	}else {
    		Alerts.showAlert("Informação", "Nenhum produto selecionado", "Selecione um produto para excluir!", Alert.AlertType.INFORMATION);
    	}
	}

    @FXML
    void ActionFinalizar(ActionEvent event) {

    }
    
    
    private void atualizarValores() {
    	   double subtotalDouble = 0;
    	    for (RegistroVenda i : listaTemp) {
    	        try {
    	            double preco = Double.parseDouble(i.getValorUnitario());
    	            int qtd = Integer.parseInt(i.getQuantidade());
    	            subtotalDouble += preco * qtd;
    	        } catch (Exception ignored) {}
    	    }
    	    subTotal = String.format("%.2f", subtotalDouble);

    	    double desconto = 0;
    	    try {
    	        desconto = Double.parseDouble(txtDesconto.getText());
    	        if (desconto > subtotalDouble) {
    	            System.out.println("Desconto não pode ser maior que o subtotal.");
    	            desconto = 0;
    	            txtDesconto.setText("0");
    	        }
    	    } catch (NumberFormatException ignored) {}
    	    
    	    
    	    descontoAcumulado += desconto;
    	    double total = subtotalDouble - descontoAcumulado;


    	    labelDesconto.setText(String.format("%.2f", descontoAcumulado));
    	    labelTotal.setText(String.format("%.2f", total));
    }
    
    private void limparTudo() {
        txtFuncionario.clear();
        txtSenha.clear();
        txtMesa.clear();
        txtDesconto.clear();
        txtProduto.clear();
        txtQuantidade.clear();
        txtObs.clear();
        listaTemp.clear();
        descontoAcumulado = 0;
        atualizarValores();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		CardapioDAO cardapioDAO = new CardapioDAO();
	  	// Configura autocomplete para txtFuncionario
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ArrayList<String> nomesFuncionarios = funcionarioDAO.readFuncionarioByNome();
        TextFields.bindAutoCompletion(txtFuncionario, nomesFuncionarios);

        // Configura autocomplete para txtMesa
        MesaDAO mesaDAO = new MesaDAO();
        ArrayList<String> mesaIds = mesaDAO.readMesaById();
        TextFields.bindAutoCompletion(txtMesa, mesaIds);

        // Configura autocomplete para txtProduto com nomes de produtos e itens do cardápio
        ArrayList<String> produtosCardapio = cardapioDAO.readCardapioByNome();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        ArrayList<String> nomesProdutos = produtoDAO.readProdutoByNome();

        // Combina as duas listas
        Set<String> sugestoesProdutos = new LinkedHashSet<>();
        sugestoesProdutos.addAll(produtosCardapio);
        sugestoesProdutos.addAll(nomesProdutos);

        // Vincula a lista combinada ao campo txtProduto
        TextFields.bindAutoCompletion(txtProduto, sugestoesProdutos);
        
        columnProduto.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNomeCardapio()));
        columnObs.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getObservacao()));
        columnQuantidade.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getQuantidade()));
        columnPreconUn.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getValorUnitario()));
        columnTotalTabela.setCellValueFactory(c -> {
        	try {
        		double preco = Double.parseDouble(c.getValue().getValorUnitario());
        		int qtd = Integer.parseInt(c.getValue().getQuantidade());
        		return new ReadOnlyStringWrapper(String.format("%.2f", preco * qtd));
        	}catch(Exception e) {
        		return new ReadOnlyStringWrapper("0.00");
        	}
        });
        
        tablePedido.setItems(listaTemp);
	}
}

