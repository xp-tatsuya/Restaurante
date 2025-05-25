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

import DAO.*;
import Model.*;
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

public class controllerRegistroGarcons implements Initializable {

    @FXML private Button btAdicionar;
    @FXML private Button btAdm;
    @FXML private Button btCancelar;
    @FXML private Button btExcluir;
    @FXML private Button btFinalizar;

    @FXML private TableColumn<RegistroVenda, String> columnIndice;
    @FXML private TableColumn<RegistroVenda, String> columnObs;
    @FXML private TableColumn<RegistroVenda, String> columnPreconUn;
    @FXML private TableColumn<RegistroVenda, String> columnProduto;
    @FXML private TableColumn<RegistroVenda, String> columnQuantidade;
    @FXML private TableColumn<RegistroVenda, String> columnTotalTabela;

    @FXML private Label labelDesconto;
    @FXML private Label labelTotal;
    @FXML private Label labelSubTotal;
    

    @FXML private TableView<RegistroVenda> tablePedido;

    @FXML private TextField txtDesconto;
    @FXML private TextField txtFuncionario;
    @FXML private TextField txtMesa;
    @FXML private TextField txtObs;
    @FXML private TextField txtProduto;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtSenha;

    private ObservableList<RegistroVenda> itensPedido = FXCollections.observableArrayList();
    private PedidoDAO pedidoDAO = new PedidoDAO();
    private CardapioDAO cardapioDAO = new CardapioDAO();
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private MesaDAO mesaDAO = new MesaDAO();
    private Cardapio_PedidoDAO cardapioPedidoDAO = new Cardapio_PedidoDAO();
    private Funcionario funcionario = new Funcionario();
    private Mesa mesa = new Mesa();
    
    private Pedido pedidoAtual = null;
    private String funcionarioResponsavelId = null;
    @FXML
    void ActionAdicionar(ActionEvent event) {
    	if(!validarFuncionario()) {
    		return;
    	}
    	
    	if(!verificarMesa()) {
    		return;
    	}
    	
    	String numeroMesa = txtMesa.getText().trim();
    	mesa.setId(numeroMesa);
    	pedidoAtual = pedidoDAO.getByMesa(mesa);
    	if (pedidoAtual != null && !pedidoAtual.getCodeFuncionario().equals(funcionario.getId())) {
            Alerts.showAlert("Erro", "Mesa em uso", "Apenas o funcionário que iniciou o pedido pode adicionar itens.", AlertType.ERROR);
            return;
    	}
        txtFuncionario.setDisable(true);
        txtSenha.setDisable(true);
        txtMesa.setDisable(true);
        
        String nomeProduto = txtProduto.getText().trim();
        String quantidadeText = txtQuantidade.getText().trim();
        String obs = txtObs.getText().trim();
        
        if(nomeProduto.isEmpty() || quantidadeText.isEmpty()) {
        	Alerts.showAlert("Erro", "Campos obrigatórios", "Preencha Produto e Quantidade.", AlertType.WARNING);
            return;
        }
        
        Cardapio cardapio = cardapioDAO.getByNome(nomeProduto);
        if (cardapio == null) {
            Alerts.showAlert("Erro", "Produto não encontrado", "O produto informado não existe no cardápio.", AlertType.ERROR);
            return;
        }
        BigDecimal precoUnitario = new BigDecimal(cardapio.getPrecoUnitario());
        int quantidade = Integer.parseInt(quantidadeText);
        BigDecimal totalItem = precoUnitario.multiply(new BigDecimal(quantidade));
        
        RegistroVenda item = new RegistroVenda();
        item.setNomeCardapio(nomeProduto);
        item.setObservacao(obs);
        item.setQuantidade(quantidadeText);
        item.setValorUnitario(precoUnitario.toString());
        item.setValorTotal(totalItem.toString());

        // Adicionar à lista e atualizar tabela
        itensPedido.add(item);
        tablePedido.setItems(itensPedido);

        // Recalcular totais
        atualizarTotais();

        // Limpar campos
        txtProduto.clear();
        txtQuantidade.clear();
        txtObs.clear();
    }

    @FXML
    void ActionAdm(ActionEvent event) throws IOException {
        Main.TelaLogin();
    }

    @FXML
    void ActionCancelar(ActionEvent event) {
        limparTela();
    }

    @FXML
    void ActionExcluir(ActionEvent event) {
        RegistroVenda item = tablePedido.getSelectionModel().getSelectedItem();
        if(item != null) {
        	itensPedido.remove(item);
            tablePedido.setItems(itensPedido);
            atualizarTotais();
        }else {
        	Alerts.showAlert("Informação", "Nenhum item selecionado", "Selecione um item para excluir.", AlertType.INFORMATION);
        }
    }

    @FXML
    void ActionFinalizar(ActionEvent event) {
        String descontoText = txtDesconto.getText().trim();
        String numeroMesa = txtMesa.getText().trim();
        
        BigDecimal desconto = BigDecimal.ZERO;
        if (!descontoText.isEmpty()) {
            try {
                desconto = new BigDecimal(descontoText.replace(",", ".")); // Substitui vírgula por ponto
                if (desconto.compareTo(BigDecimal.ZERO) < 0) {
                    Alerts.showAlert("Erro", "Desconto inválido", "Desconto deve ser um valor não negativo.", AlertType.ERROR);
                    return;
                }
            } catch (NumberFormatException e) {
                Alerts.showAlert("Erro", "Desconto inválido", "Desconto deve ser um número.", AlertType.ERROR);
                return;
            }
        }
        
        mesa.setId(numeroMesa);
        pedidoAtual = pedidoDAO.getByMesa(mesa);
        if(pedidoAtual == null) {
            pedidoAtual = new Pedido();
            pedidoAtual.setCodeFuncionario(funcionario.getId());
            pedidoAtual.setCodeMesa(numeroMesa);
            pedidoAtual.setCondicao("Pendente");
            pedidoAtual.setObservacoes("Pedido registrado pelo garçom.");
            pedidoAtual.setDesconto(descontoText.isEmpty() ? "0.00" : descontoText.replace(",", ".")); // Garante que nunca seja vazio
            pedidoAtual.setPrecoTotal(labelTotal.getText().replace("R$ ", "").trim());

            pedidoDAO.create(pedidoAtual);
        }else {
            pedidoAtual.setDesconto(desconto.toString());
            pedidoAtual.setPrecoTotal(labelTotal.getText().replace("R$ ", "").trim());
            pedidoDAO.update(pedidoAtual);
        }
        
        for(RegistroVenda item : itensPedido) {
        	Cardapio cardapio = cardapioDAO.getByNome(item.getNomeCardapio());
        	if(cardapio != null) {
        		Cardapio_Pedido cardapioPedido = new Cardapio_Pedido();
                cardapioPedido.setCodeCardapio(cardapio.getId());
                cardapioPedido.setCodePedido(pedidoAtual.getId()); // Use o ID do pedido
                cardapioPedido.setQuantidade(item.getQuantidade());
                cardapioPedido.setObservacao(item.getObservacao());
                cardapioPedidoDAO.create(cardapioPedido);
        	}
        }
        
        Alerts.showAlert("Sucesso", "Pedido registrado", "Pedido registrado com sucesso!", AlertType.INFORMATION);

        // Limpar tela ou fechar
        limparTela(); // Ou Main.changeScreen("TelaAnterior", null, 0);
        
    }
    
    private void limparTela() {
    	 txtFuncionario.clear();
         txtSenha.clear();
         txtMesa.clear();
         txtDesconto.clear();
         txtProduto.clear();
         txtQuantidade.clear();
         txtObs.clear();
         itensPedido.clear();
         tablePedido.getItems().clear();
         labelSubTotal.setText("R$ 0,00");
         labelDesconto.setText("R$ 0,00");
         labelTotal.setText("R$ 0,00");
         pedidoAtual = null;
         funcionario = null;
         funcionarioResponsavelId = null;
         txtFuncionario.setDisable(false);
         txtSenha.setDisable(false);
         txtMesa.setDisable(false);
    }
    
    private void atualizarTotais() {
    	BigDecimal subtotal = BigDecimal.ZERO;
    	
    	for(RegistroVenda item : itensPedido) {
    		subtotal = subtotal.add(new BigDecimal(item.getValorTotal()));
    		BigDecimal desconto = txtDesconto.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtDesconto.getText().replace(",", ".")); // Substitui vírgula por ponto
            BigDecimal total = subtotal.subtract(desconto);

            labelSubTotal.setText("R$ " + subtotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            labelDesconto.setText("R$ " + desconto.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            labelTotal.setText("R$ " + total.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	}
    }
    
    private boolean verificarMesa() {
    	String numeroMesa = txtMesa.getText().trim();
    	
    	if(numeroMesa.isEmpty()) {
    		Alerts.showAlert("Erro", "Credenciais", "Informe o numero na mesa.", AlertType.WARNING);
            return false;
    	}
    	
    	mesa = mesaDAO.verifycondicao(new Mesa(numeroMesa, null, null));
        if (mesa == null) {
            Alerts.showAlert("Erro", "Mesa inválida", "Mesa não encontrada.", AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private boolean validarFuncionario() {
    	String nomeFuncionario = txtFuncionario.getText().trim();
    	String senhaFuncionario = txtSenha.getText().trim();
    	
    	if (nomeFuncionario.isEmpty() || senhaFuncionario.isEmpty()) {
            Alerts.showAlert("Erro", "Credenciais", "Informe o funcionário e a senha.", AlertType.WARNING);
            return false;
        }

        funcionario = funcionarioDAO.autenticarUserByNome(nomeFuncionario, senhaFuncionario);
        if (funcionario == null) {
            Alerts.showAlert("Erro", "Credenciais inválidas", "Funcionário não encontrado ou senha incorreta.", AlertType.ERROR);
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        CardapioDAO cardapioDAO = new CardapioDAO();

        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ArrayList<String> nomesFuncionarios = funcionarioDAO.readFuncionarioByNome();
        TextFields.bindAutoCompletion(txtFuncionario, nomesFuncionarios);


        MesaDAO mesaDAO = new MesaDAO();
        ArrayList<String> mesaIds = mesaDAO.readMesaById();
        TextFields.bindAutoCompletion(txtMesa, mesaIds);


        ArrayList<String> produtosCardapio = cardapioDAO.readCardapioByNome();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        ArrayList<String> nomesProdutos = produtoDAO.readProdutoByNome();


        Set<String> sugestoesProdutos = new LinkedHashSet<>();
        sugestoesProdutos.addAll(produtosCardapio);
        sugestoesProdutos.addAll(nomesProdutos);


        TextFields.bindAutoCompletion(txtProduto, sugestoesProdutos);


        columnProduto.setCellValueFactory(new PropertyValueFactory<>("nomeCardapio"));
        columnObs.setCellValueFactory(new PropertyValueFactory<>("observacao"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        columnPreconUn.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        columnTotalTabela.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));


        tablePedido.setItems(itensPedido);
    }
}