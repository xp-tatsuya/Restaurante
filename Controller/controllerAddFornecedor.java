// Controller/controllerAddFornecedor.java
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import DAO.FornecedorDAO;
import Model.Fornecedor;
import Util.cnpjValidator;
import Util.telefoneValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class controllerAddFornecedor implements Initializable {

    @FXML private Button btCancelar;
    @FXML private Button btSalvar;
    @FXML private TextField txtNome;
    @FXML private TextField txtCNPJ;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;

    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void ActionCancelar() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionSalvar() {
        String nome = txtNome.getText().trim();
        String cnpj = txtCNPJ.getText().trim();
        String tel  = txtTelefone.getText().trim();
        String end  = txtEndereco.getText().trim();

        if (nome.isEmpty() || cnpj.isEmpty() || tel.isEmpty() || end.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
            return;
        }
        if (!cnpjValidator.isValid(cnpj)) {
            new Alert(Alert.AlertType.WARNING, "CNPJ inválido.").showAndWait();
            return;
        }
        if (!telefoneValidator.validarTelefone(tel)) {
            new Alert(Alert.AlertType.WARNING, "Telefone inválido. Deve ter 11 dígitos, DDD válido e iniciar com 9.").showAndWait();
            return;
        }

        try {
            Fornecedor f = new Fornecedor();
            f.setNome(nome);
            f.setCnpj(cnpj);
            f.setTelefone(tel);
            f.setEndereco(end);

            fornecedorDAO.create(f);

            new Alert(Alert.AlertType.INFORMATION, "Fornecedor cadastrado com sucesso!").showAndWait();

            Stage stage = (Stage) btSalvar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao salvar fornecedor.").showAndWait();
        }
    }
}
