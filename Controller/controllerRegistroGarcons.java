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

	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    
    RegistroVendaDAO registroVendaDAO = new RegistroVendaDAO();
    
    RegistroVenda registroVenda = new RegistroVenda();
    
    PedidoDAO pedidoDAO = new PedidoDAO();
    
    Pedido pedido = new Pedido();
    
    Mesa mesa = new Mesa();
    
    MesaDAO mesaDAO = new MesaDAO();
    
    Cardapio cardapio = new Cardapio();
    CardapioDAO cardapioDAO = new CardapioDAO();
    
	Cardapio_Pedido cp = new Cardapio_Pedido();
    Cardapio_PedidoDAO cpDAO = new Cardapio_PedidoDAO();
    
    private ObservableList<RegistroVenda> itens = FXCollections.observableArrayList();
    
    private String pedidoId;
    
    @FXML
    void ActionAdicionar(ActionEvent event) {
        // valida quantidade
        if (txtQuantidade.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Informe a quantidade antes de adicionar.").showAndWait();
            return;
        }

        String nome = txtProduto.getText().trim();
        String idCard = cardapioDAO.getIdByNome(nome);
        if (idCard == null) {
            new Alert(Alert.AlertType.ERROR, "Produto ou Cardápio não encontrado.").showAndWait();
            return;
        }

        RegistroVenda rv = new RegistroVenda();
        rv.setNumeroMesa(txtMesa.getText());
        rv.setNomeCardapio(nome);
        rv.setObservacao(txtObs.getText());
        rv.setQuantidade(txtQuantidade.getText());

        String pu = cardapioDAO.getPrecoUnitarioById(idCard);
        rv.setValorUnitario(pu);
        double total = Double.parseDouble(rv.getQuantidade()) * Double.parseDouble(pu);
        rv.setValorTotal(String.format("%.2f", total));
        itens.add(rv);

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

    }

    @FXML
	void ActionExcluir(ActionEvent event) {
    	int i = tablePedido.getSelectionModel().getSelectedIndex();
    	if(i == -1) {
    		Alerts.showAlert("Informação", "Nenhum produto selecionado", "Selecione um produto para excluir!", Alert.AlertType.INFORMATION);
    	}else {
    		registroVenda = tablePedido.getItems().get(i);
    		
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
    	 try {
             // 1) Atualiza os dados do pedido (funcionário + desconto)
             String funcId = new FuncionarioDAO().getIdByNome(txtFuncionario.getText().trim());
             pedido.setCodeFuncionario(funcId != null ? funcId : pedido.getCodeFuncionario());

             String discText = txtDesconto.getText();
             BigDecimal desconto = discText.isEmpty()
                 ? BigDecimal.ZERO
                 : new BigDecimal(discText);
             pedido.setDesconto(desconto.toPlainString());

             // salva
             pedidoDAO.update(pedido);

             // 2) recria todos os itens
             registroVendaDAO.deleteByPedido(pedidoId);
             for (RegistroVenda rv : itens) {
                 Cardapio_Pedido cp = new Cardapio_Pedido();
                 cp.setCodePedido(pedidoId);
                 cp.setCodeCardapio(cardapioDAO.getIdByNome(rv.getNomeCardapio()));
                 cp.setObservacao(rv.getObservacao());
                 cp.setQuantidade(rv.getQuantidade());
                 cpDAO.create(cp);
             }

         } catch (NumberFormatException nfe) {
             new Alert(Alert.AlertType.ERROR, "Desconto inválido.").showAndWait();
         } catch (Exception e) {
             e.printStackTrace();
         }
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
    
    public void valores() {
    	String idPedido = pedido.getPrecoTotal();
    	labelTotal.setText(pedido.getPrecoTotal());
    	labelDesconto.setText(pedido.getDesconto());
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		FuncionarioDAO fdao = new FuncionarioDAO();
        TextFields.bindAutoCompletion(txtFuncionario, fdao.readFuncionarioByNome());

        MesaDAO mdao = new MesaDAO();
        TextFields.bindAutoCompletion(txtMesa, mdao.readMesaById());

        // Auto-complete produto/cardápio
        Set<String> sugestoes = new LinkedHashSet<>();
        sugestoes.addAll(cardapioDAO.readCardapioByNome());
        sugestoes.addAll(new ProdutoDAO().readProdutoByNome());
        TextFields.bindAutoCompletion(txtProduto, sugestoes);

        // configura colunas
        columnProduto.setCellValueFactory(new PropertyValueFactory<>("nomeCardapio"));
        columnObs.setCellValueFactory(new PropertyValueFactory<>("observacao"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        columnPreconUn.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        columnTotalTabela.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        // se vier para editar, carrega dados
        if (controllerPedido.pedido != null) {
            pedido = controllerPedido.pedido;
            pedidoId = pedido.getId();
            txtFuncionario.setText(
                fdao.readFuncionarioByNome().stream()
                    .filter(n -> fdao.getIdByNome(n).equals(pedido.getCodeFuncionario()))
                    .findFirst().orElse("")
            );
            txtMesa.setText(pedido.getCodeMesa());
            txtDesconto.setText(pedido.getDesconto());

            recarregarItens();
        }
	}
	
	private void recarregarItens() {
        itens.setAll(registroVendaDAO.read(txtMesa.getText()));
        tablePedido.setItems(itens);
    }

}
