package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Util.cpfValidator;
import Util.emailValidator;
import Util.telefoneValidator;
import DAO.FuncionarioDAO;
import Model.Funcionario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class controllerAddFuncionario implements Initializable {

    @FXML private Button btCancelar;
    @FXML private Button btSalvar;
    @FXML private ChoiceBox<String> choiceGenero;
    @FXML private DatePicker dateNasc;
    @FXML private DatePicker dateAdmiss;
    @FXML private TextField txtNome;
    @FXML private TextField txtSenha;
    @FXML private TextField txtCPF;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtCargo;
    @FXML private TextField txtSalario;
    @FXML private Label title;
    
    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    private Funcionario funcionario = new Funcionario();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceGenero.setItems(FXCollections.observableArrayList(
            "Masculino", "Feminino", "Outro"
        ));
        
        if(controllerFuncionario.funcionario != null) {
        	title.setText("EDITAR FUNCIONARIO");
        	funcionario = controllerFuncionario.funcionario;
        	txtNome.setText(funcionario.getNome());
        	txtSenha.setText(funcionario.getSenha());
        	txtCPF.setText(funcionario.getCpf());
        	txtEmail.setText(funcionario.getEmail());
        	txtTelefone.setText(funcionario.getTelefone());
        	txtEndereco.setText(funcionario.getEndereco());
        	txtCargo.setText(funcionario.getCargo());
        	txtSalario.setText(funcionario.getSalario());
        	choiceGenero.setValue(funcionario.getGenero());
        	LocalDate dataNasc = LocalDate.parse(funcionario.getDataNasc());
        	dateNasc.setValue(dataNasc);
        	LocalDate dataAdms = LocalDate.parse(funcionario.getDataAdms());
        	dateAdmiss.setValue(dataAdms);
        	
        }
    }

    @FXML
    void ActionCancelar() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionSalvar() {
    	if(controllerFuncionario.funcionario == null) {
            if (!validarCampos()) return;

            Funcionario f = new Funcionario();
            f.setNome(txtNome.getText().trim());
            f.setSenha(txtSenha.getText());
            f.setVerificarAcesso("3");
            f.setCpf(txtCPF.getText().replaceAll("\\D", ""));
            f.setEmail(txtEmail.getText().trim());
            f.setTelefone(txtTelefone.getText().replaceAll("\\D", ""));
            f.setGenero(choiceGenero.getValue());
            f.setEndereco(txtEndereco.getText().trim());
            f.setDataNasc(dateNasc.getValue().toString());
            f.setCargo(txtCargo.getText().trim());
            f.setSalario(txtSalario.getText().trim());
            f.setDataAdms(dateAdmiss.getValue().toString());

            funcionarioDAO.create(f);

            new Alert(Alert.AlertType.INFORMATION,
                      "Funcionário cadastrado com sucesso!",
                      ButtonType.OK)
                .showAndWait();

            closeWindow();
    	}else {
            if (!validarCampos()) return;

            Funcionario f = new Funcionario();
            f.setNome(txtNome.getText().trim());
            f.setSenha(txtSenha.getText());
            f.setVerificarAcesso("3");
            f.setCpf(txtCPF.getText().replaceAll("\\D", ""));
            f.setEmail(txtEmail.getText().trim());
            f.setTelefone(txtTelefone.getText().replaceAll("\\D", ""));
            f.setGenero(choiceGenero.getValue());
            f.setEndereco(txtEndereco.getText().trim());
            f.setDataNasc(dateNasc.getValue().toString());
            f.setCargo(txtCargo.getText().trim());
            f.setSalario(txtSalario.getText().trim());
            f.setDataAdms(dateAdmiss.getValue().toString());

            funcionarioDAO.update(f);

            new Alert(Alert.AlertType.INFORMATION,
                      "Funcionário cadastrado com sucesso!",
                      ButtonType.OK)
                .showAndWait();

            closeWindow();
    	}

    }
//
    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            showError("Nome obrigatório");
            return false;
        }
        if (txtSenha.getText().isEmpty()) {
            showError("Senha obrigatória");
            return false;
        }
        String rawCpf = txtCPF.getText().replaceAll("\\D", "");
        if (!cpfValidator.isValid(rawCpf)) {
            showError("CPF inválido");
            return false;
        }
        if (!emailValidator.isValid(txtEmail.getText().trim())) {
            showError("E-mail inválido");
            return false;
        }
        String rawTel = txtTelefone.getText().replaceAll("\\D", "");
        if (!telefoneValidator.isValid(rawTel)) {
            showError("Telefone inválido");
            return false;
        }
        if (choiceGenero.getValue() == null) {
            showError("Selecione o gênero");
            return false;
        }
        if (dateNasc.getValue() == null) {
            showError("Data de nascimento obrigatória");
            return false;
        }
        if (dateAdmiss.getValue() == null) {
            showError("Data de admissão obrigatória");
            return false;
        }
        if (txtCargo.getText().trim().isEmpty()) {
            showError("Cargo obrigatório");
            return false;
        }
        try {
            Double.parseDouble(txtSalario.getText().trim());
        } catch (NumberFormatException e) {
            showError("Salário inválido");
            return false;
        }
        return true;
    }

    private void showError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
}