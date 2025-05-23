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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controllerAddFornecedor implements Initializable {

    @FXML private Button btCancelar;
    @FXML private Button btSalvar;
    @FXML private TextField txtNome;
    @FXML private TextField txtCNPJ;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;
    @FXML private Label title;
    
    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private Fornecedor fornecedor = new Fornecedor();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(controllerFornecedor.fornecedor != null) {
        	title.setText("EDITAR FORNECEDOR");
        	fornecedor = controllerFornecedor.fornecedor;
        	txtNome.setText(fornecedor.getNome());
        	txtCNPJ.setText(fornecedor.getCnpj());
        	txtTelefone.setText(fornecedor.getTelefone());
        	txtEndereco.setText(fornecedor.getEndereco());
        }
    }

    @FXML
    void ActionCancelar() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionSalvar() {
    	if(controllerFornecedor.fornecedor == null) {
    		String nome      = txtNome.getText().trim();
            String rawCnpj   = txtCNPJ.getText().trim();
            String rawTel    = txtTelefone.getText().trim();
            String endereco  = txtEndereco.getText().trim();

            // Validação de campos obrigatórios
            if (nome.isEmpty() || rawCnpj.isEmpty() || rawTel.isEmpty() || endereco.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
                return;
            }

            // Remove formatação e valida CNPJ
            String cnpjDigits = rawCnpj.replaceAll("\\D", "");
            if (!cnpjValidator.isValid(cnpjDigits)) {
                new Alert(Alert.AlertType.WARNING, "CNPJ inválido.").showAndWait();
                return;
            }

            // Remove formatação e valida telefone
            String telDigits = rawTel.replaceAll("\\D", "");
            if (!telefoneValidator.isValid(telDigits)) {
                new Alert(Alert.AlertType.WARNING,
                    "Telefone inválido. Deve ter 11 dígitos, DDD válido e iniciar com 9.")
                  .showAndWait();
                return;
            }

            try {
                // Prepara objeto Fornecedor com dados "puros" (só dígitos)
                Fornecedor f = new Fornecedor();
                f.setNome(nome);
                f.setCnpj(cnpjDigits);
                f.setTelefone(telDigits);
                f.setEndereco(endereco);

                // Grava no banco
                fornecedorDAO.create(f);

                new Alert(Alert.AlertType.INFORMATION,
                    "Fornecedor cadastrado com sucesso!")
                  .showAndWait();

                // Fecha janela
                Stage stage = (Stage) btSalvar.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,
                    "Erro ao salvar fornecedor.").showAndWait();
            }
    	}else {
    		String nome      = txtNome.getText().trim();
            String rawCnpj   = txtCNPJ.getText().trim();
            String rawTel    = txtTelefone.getText().trim();
            String endereco  = txtEndereco.getText().trim();

            // Validação de campos obrigatórios
            if (nome.isEmpty() || rawCnpj.isEmpty() || rawTel.isEmpty() || endereco.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
                return;
            }

            // Remove formatação e valida CNPJ
            String cnpjDigits = rawCnpj.replaceAll("\\D", "");
            if (!cnpjValidator.isValid(cnpjDigits)) {
                new Alert(Alert.AlertType.WARNING, "CNPJ inválido.").showAndWait();
                return;
            }

            // Remove formatação e valida telefone
            String telDigits = rawTel.replaceAll("\\D", "");
            if (!telefoneValidator.isValid(telDigits)) {
                new Alert(Alert.AlertType.WARNING,
                    "Telefone inválido. Deve ter 11 dígitos, DDD válido e iniciar com 9.")
                  .showAndWait();
                return;
            }

            try {
                // Prepara objeto Fornecedor com dados "puros" (só dígitos)
                Fornecedor f = new Fornecedor();
                f.setNome(nome);
                f.setCnpj(cnpjDigits);
                f.setTelefone(telDigits);
                f.setEndereco(endereco);

                // Grava no banco
                fornecedorDAO.update(f);

                new Alert(Alert.AlertType.INFORMATION,
                    "Fornecedor Atualizado com sucesso!")
                  .showAndWait();

                // Fecha janela
                Stage stage = (Stage) btSalvar.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,
                    "Erro ao Atualizar o fornecedor.").showAndWait();
            }
    	}
        
    }
}