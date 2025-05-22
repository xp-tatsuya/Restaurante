package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

import DAO.CardapioDAO;
import DAO.Cardapio_PedidoDAO;
import DAO.FuncionarioDAO;
import DAO.MesaDAO;
import DAO.ProdutoDAO;
import DAO.RegistroVendaDAO;
import Model.Cardapio_Pedido;
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

    private String pedidoId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// Configura autocomplete para txtFuncionario
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ArrayList<String> nomesFuncionarios = funcionarioDAO.readFuncionarioByNome();
        TextFields.bindAutoCompletion(txtFuncionario, nomesFuncionarios);

        // Configura autocomplete para txtMesa
        MesaDAO mesaDAO = new MesaDAO();
        ArrayList<String> mesaIds = mesaDAO.readMesaById();
        TextFields.bindAutoCompletion(txtMesa, mesaIds);

        // Configura autocomplete para txtProduto com nomes de produtos e itens do cardápio
        ArrayList<String> produtosCardapio = cardDAO.readCardapioByNome();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        ArrayList<String> nomesProdutos = produtoDAO.readProdutoByNome();

        // Combina as duas listas
        Set<String> sugestoesProdutos = new LinkedHashSet<>();
        sugestoesProdutos.addAll(produtosCardapio);
        sugestoesProdutos.addAll(nomesProdutos);

        // Vincula a lista combinada ao campo txtProduto
        TextFields.bindAutoCompletion(txtProduto, sugestoesProdutos);

        CarregarTableProduto();

        // Carregar dados se houver pedido
        if (controllerPedido.pedido != null) {
            pedidoId = controllerPedido.pedido.getId();

            String nome = funcionarioDAO.readFuncionarioByNome().stream()
                .filter(n -> funcionarioDAO.getIdByNome(n).equals(controllerPedido.pedido.getCodeFuncionario()))
                .findFirst().orElse("");
            txtFuncionario.setText(nome);
            txtMesa.setText(controllerPedido.pedido.getCodeMesa());
            txtDesconto.setText(controllerPedido.pedido.getDesconto());
        }
    }

    private void CarregarTableProduto() {
        columnProduto.setCellValueFactory(new PropertyValueFactory<>("nomeCardapio"));
        columnObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));
        columnQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        columnPreconUn.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        columnTotalTabela.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        itens.setAll(rvDAO.read(txtMesa.getText()));
        tablePedido.setItems(itens);
    }

    @FXML
    void ActionAdicionar(ActionEvent event) {
        String nome = txtProduto.getText().trim();
        String idCard = cardDAO.getIdByNome(nome);
        if (idCard == null) {
            new Alert(Alert.AlertType.ERROR, "Produto não encontrado").showAndWait();
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
            rvDAO.deleteByPedido(pedidoId);
            itens.forEach(rv -> {
                Cardapio_Pedido cp = new Cardapio_Pedido();
                cp.setCodePedido(pedidoId);
                cp.setCodeCardapio(cardDAO.getIdByNome(rv.getNomeCardapio()));
                cp.setObservacao(rv.getObservacao());
                cp.setQuantidade(rv.getQuantidade());
                cpDAO.create(cp);
            });
            closeWindow();
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
