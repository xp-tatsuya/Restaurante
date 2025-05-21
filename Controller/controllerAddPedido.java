// Controller/controllerAddPedido.java
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import org.controlsfx.control.textfield.TextFields;
import DAO.MesaDAO;
import DAO.FuncionarioDAO;
import DAO.PedidoDAO;
import Model.Pedido;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class controllerAddPedido implements Initializable {

    @FXML private Button btCancelar;
    @FXML private Button btSalvar;
    @FXML private DatePicker datePedido;
    @FXML private TextField txtFuncionario;
    @FXML private TextField txtCODEmesa;
    @FXML private TextField txtObs;
    @FXML private TextField txtDesconto;
    @FXML private TextField txtPrecoTotal;

    private FuncionarioDAO funcDAO = new FuncionarioDAO();
    private MesaDAO mesaDAO = new MesaDAO();
    private PedidoDAO pedidoDAO = new PedidoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	FuncionarioDAO funcDAO = new FuncionarioDAO();
        ArrayList<String> nomes = funcDAO.readFuncionarioByNome();
        TextFields.bindAutoCompletion(txtFuncionario, nomes);

        MesaDAO mesaDAO = new MesaDAO();
        ArrayList<String> mesaIds = mesaDAO.readMesaById();
        TextFields.bindAutoCompletion(txtCODEmesa, mesaIds);
    }

    @FXML
    void ActionCancelar(ActionEvent event) throws IOException {
    	limparCampos();
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionSalvar(ActionEvent event) {
        try {
            String nomeFunc = txtFuncionario.getText().trim();
            String idFunc = funcDAO.getIdByNome(nomeFunc);
            int idMesa = Integer.parseInt(txtCODEmesa.getText().trim());
            String data = datePedido.getValue().toString();
            String condicao = "Pendente";
            String obs = txtObs.getText().trim();
            String desconto = txtDesconto.getText().trim().isEmpty() ? "0.00" : txtDesconto.getText().trim();
            String total = txtPrecoTotal.getText().trim();

            Pedido p = new Pedido();
            p.setCodeFuncionario(String.valueOf(idFunc));
            p.setCodeMesa(String.valueOf(idMesa));
            p.setDataPedido(data);
            p.setCondicao(condicao);
            p.setObservacoes(obs);
            p.setDesconto(desconto);
            p.setPrecoTotal(total);
            pedidoDAO.create(p);
            
            limparCampos();
            Stage stage = (Stage) btCancelar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            //mostrar alerta de erro ao usuário
        }
    }
    
    private void limparCampos() {
    	datePedido.setValue(null);
        txtFuncionario.clear();
        txtCODEmesa.clear();
        txtObs.clear();
        txtDesconto.clear();
        txtPrecoTotal.clear();
    }
    
}
