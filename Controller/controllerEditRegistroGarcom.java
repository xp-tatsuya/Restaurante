package Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

import DAO.CardapioDAO;
import DAO.Cardapio_PedidoDAO;
import DAO.FuncionarioDAO;
import DAO.MesaDAO;
import DAO.PedidoDAO;
import DAO.ProdutoDAO;
import DAO.RegistroVendaDAO;
import Model.Cardapio_Pedido;
import Model.Pedido;
import Model.RegistroVenda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

public class controllerEditRegistroGarcom implements Initializable {

    @FXML private TextField txtFuncionario;
    @FXML private TextField txtMesa;
    @FXML private TextField txtDesconto;
    @FXML private TextField txtProduto;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtObservacao;

    @FXML private Button btAdicionar;
    @FXML private Button btExcluir;
    @FXML private Button btFinalizar;
    @FXML private Button btCancelar;
    
    @FXML private Label labelDesconto;
    @FXML private Label labelTotal;
    @FXML private Label labelSubTotal;
    
    @FXML private TableView<RegistroVenda> tablePedido;
    @FXML private TableColumn<RegistroVenda, String> columnProduto;
    @FXML private TableColumn<RegistroVenda, String> columnObservacao;
    @FXML private TableColumn<RegistroVenda, String> columnQuantidade;
    @FXML private TableColumn<RegistroVenda, String> columnPreconUn;
    @FXML private TableColumn<RegistroVenda, String> columnTotalTabela;

    private ObservableList<RegistroVenda> itens = FXCollections.observableArrayList();
    private RegistroVendaDAO rvDAO = new RegistroVendaDAO();
    private Cardapio_PedidoDAO cpDAO = new Cardapio_PedidoDAO();
    private CardapioDAO cardDAO = new CardapioDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();

    private String pedidoId;
    private Pedido pedido; 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
        FuncionarioDAO fdao = new FuncionarioDAO();
        TextFields.bindAutoCompletion(txtFuncionario, fdao.readFuncionarioByNome());

        MesaDAO mdao = new MesaDAO();
        TextFields.bindAutoCompletion(txtMesa, mdao.readMesaById());

        // Auto-complete produto/cardápio
        Set<String> sugestoes = new LinkedHashSet<>();
        sugestoes.addAll(cardDAO.readCardapioByNome());
        sugestoes.addAll(new ProdutoDAO().readProdutoByNome());
        TextFields.bindAutoCompletion(txtProduto, sugestoes);

        // configura colunas
        columnProduto   .setCellValueFactory(new PropertyValueFactory<>("nomeCardapio"));
        columnObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        columnPreconUn  .setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
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
        itens.setAll(rvDAO.read(txtMesa.getText()));
        tablePedido.setItems(itens);
    }
    
    private void atualizarTotais() {
    	BigDecimal subtotal = BigDecimal.ZERO;
    	
    	for(RegistroVenda item : itens) {
    		subtotal = subtotal.add(new BigDecimal(item.getValorTotal()));
    		BigDecimal desconto = txtDesconto.getText().trim().isEmpty() ? BigDecimal.ZERO : new BigDecimal(txtDesconto.getText().replace(",", ".")); // Substitui vírgula por ponto
            BigDecimal total = subtotal.subtract(desconto);

            labelSubTotal.setText("R$ " + subtotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            labelDesconto.setText("R$ " + desconto.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            labelTotal.setText("R$ " + total.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    	}
    }

    @FXML
    void ActionAdicionar(ActionEvent event) {
        // valida quantidade
        if (txtQuantidade.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Informe a quantidade antes de adicionar.").showAndWait();
            return;
        }

        String nome = txtProduto.getText().trim();
        String idCard = cardDAO.getIdByNome(nome);
        if (idCard == null) {
            new Alert(Alert.AlertType.ERROR, "Produto ou Cardápio não encontrado.").showAndWait();
            return;
        }

        RegistroVenda rv = new RegistroVenda();
        rv.setNumeroMesa(txtMesa.getText());
        rv.setNomeCardapio(nome);
        rv.setObservacao(txtObservacao.getText());
        rv.setQuantidade(txtQuantidade.getText());

        String pu = cardDAO.getPrecoUnitarioById(idCard);
        rv.setValorUnitario(pu);
        double total = Double.parseDouble(rv.getQuantidade()) * Double.parseDouble(pu);
        rv.setValorTotal(String.format("%.2f", total));
        itens.add(rv);

        txtProduto.clear();
        txtQuantidade.clear();
        txtObservacao.clear();
    }

    @FXML
    void ActionExcluir(ActionEvent event) {
        RegistroVenda sel = tablePedido.getSelectionModel().getSelectedItem();
        if (sel != null) {
            itens.remove(sel);
        }
    }

    @FXML
    void ActionFinalizar(ActionEvent event) {
        try {
            // 1) Atualiza os dados do pedido (funcionário + mesa + desconto)
            String funcId = new FuncionarioDAO().getIdByNome(txtFuncionario.getText().trim());
            pedido.setCodeFuncionario(funcId != null ? funcId : pedido.getCodeFuncionario());

            // **Atualiza a mesa**
            pedido.setCodeMesa(txtMesa.getText().trim());

            // Desconto (se houver)
            String discText = txtDesconto.getText().trim();
            BigDecimal desconto = discText.isEmpty()
                ? BigDecimal.ZERO
                : new BigDecimal(discText);
            pedido.setDesconto(desconto.toPlainString());

            // Salva alterações no Pedido
            pedidoDAO.update(pedido);

            // 2) Recria todos os itens
            rvDAO.deleteByPedido(pedidoId);
            for (RegistroVenda rv : itens) {
                Cardapio_Pedido cp = new Cardapio_Pedido();
                cp.setCodePedido(pedidoId);
                cp.setCodeCardapio(cardDAO.getIdByNome(rv.getNomeCardapio()));
                cp.setObservacao(rv.getObservacao());
                cp.setQuantidade(rv.getQuantidade());
                cpDAO.create(cp);
            }

            closeWindow();
        } catch (NumberFormatException nfe) {
            new Alert(Alert.AlertType.ERROR, "Desconto inválido.").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar: " + e.getMessage()).showAndWait();
        }
    }


    @FXML
    void ActionCancelar(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
}