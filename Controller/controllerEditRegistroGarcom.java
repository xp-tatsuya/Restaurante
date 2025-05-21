package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.CardapioDAO;
import DAO.Cardapio_PedidoDAO;
import DAO.FuncionarioDAO;
import DAO.MesaDAO;
import DAO.RegistroVendaDAO;
import Model.Cardapio_Pedido;
import Model.RegistroVenda;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class controllerEditRegistroGarcom implements Initializable {

    @FXML private TextField txtFuncionario;
    @FXML private TextField txtSenha;
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
    private RegistroVendaDAO registroVendaDAO = new RegistroVendaDAO();
    private Cardapio_PedidoDAO cpDAO = new Cardapio_PedidoDAO();
    private CardapioDAO cardDAO = new CardapioDAO();

    private String pedidoId;  // idPedido que estamos editando

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1) Carrega o pedido selecionado (de controllerPedido.pedido)
        if (controllerPedido.pedido != null) {
            pedidoId = controllerPedido.pedido.getId();
            // Preenche campos
            FuncionarioDAO fdao = new FuncionarioDAO();
            String nomeFunc = fdao.readFuncionarioByNome()
                                 .stream()
                                 .filter(n -> fdao.getIdByNome(n).equals(controllerPedido.pedido.getCodeFuncionario()))
                                 .findFirst().orElse("");
            txtFuncionario.setText(nomeFunc);
            txtMesa.setText(controllerPedido.pedido.getCodeMesa());
            txtDesconto.setText(controllerPedido.pedido.getDesconto());
        }

        // 2) Configura colunas da tabela
        columnProduto     .setCellValueFactory(new PropertyValueFactory<>("nomeCardapio"));
        columnObservacao  .setCellValueFactory(new PropertyValueFactory<>("observacao"));
        columnQuantidade  .setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        columnPreconUn    .setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        columnTotalTabela .setCellValueFactory(new PropertyValueFactory<>("valorTotal"));

        // 3) Carrega itens pendentes do banco
        itens.setAll(registroVendaDAO.read(txtMesa.getText()));
        tablePedido.setItems(itens);
    }

    @FXML
    void ActionAdicionar(ActionEvent event) {
        // cria um registro temporário e adiciona à lista
        String nome = txtProduto.getText().trim();
        String idCard = cardDAO.getIdByNome(nome);
        if (idCard == null) {
            new Alert(Alert.AlertType.ERROR, "Produto não encontrado").showAndWait();
            return;
        }
        RegistroVenda nv = new RegistroVenda();
        nv.setIdCardapioPedido("");             // será gerado no finalize
        nv.setNumeroMesa(txtMesa.getText().trim());
        nv.setNomeCardapio(nome);
        nv.setObservacao(txtObservacao.getText().trim());
        nv.setQuantidade(txtQuantidade.getText().trim());
        // recupera preço unitário diretamente do Cardápio
        nv.setValorUnitario(cardDAO.read()
            .stream()
            .filter(c -> c.getId().equals(idCard))
            .findFirst().get()
            .getPrecoUnitario()
        );
        // calcula total
        double qt = Double.parseDouble(nv.getQuantidade());
        double pu = Double.parseDouble(nv.getValorUnitario());
        nv.setValorTotal(String.format("%.2f", qt * pu));
        itens.add(nv);
        tablePedido.refresh();
        // limpa campos de produto
        txtProduto.clear();
        txtQuantidade.clear();
        txtObservacao.clear();
    }

    @FXML
    void ActionExcluir(ActionEvent event) {
        var sel = tablePedido.getSelectionModel().getSelectedItem();
        if (sel != null) {
            itens.remove(sel);
        }
    }

    @FXML
    void ActionFinalizar(ActionEvent event) {
        try {
            // 1) elimina todos os registros antigos desse pedido
            registroVendaDAO.deleteByPedido(pedidoId);
            // 2) insere cada item atual
            for (RegistroVenda rv : itens) {
                Cardapio_Pedido cp = new Cardapio_Pedido();
                cp.setCodePedido(pedidoId);
                cp.setCodeCardapio(cardDAO.getIdByNome(rv.getNomeCardapio()));
                cp.setObservacao(rv.getObservacao());
                cp.setQuantidade(rv.getQuantidade());
                cpDAO.create(cp);
            }
            // 3) fecha a janela
            Stage stage = (Stage) btFinalizar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar alterações: " + e.getMessage()).showAndWait();
        }
    }

    @FXML
    void ActionCancelar(ActionEvent event) {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
}