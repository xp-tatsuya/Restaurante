package Controller;

import java.io.IOException;

import DAO.FuncionarioDAO;
import Model.Funcionario;
import Util.Alerts;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class controllerLogin {

    @FXML
    private Button btLogin;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;
    
    static Funcionario funcionario = new Funcionario();
    
    @FXML
    void PressEnter(KeyEvent event) throws IOException {
    	if(event.getCode() == KeyCode.ENTER) {
    		Login();
    	}
    }

    @FXML
    void actionLogin(ActionEvent event) throws IOException {
    	Login();
    }
    
    private void Login() throws IOException {
        if (txtUser.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            Alerts.showAlert("Erro!!!", "Erro de Login", "Preencha as informações de login e senha para acessar!", AlertType.ERROR);
            return;
        }
        
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionario = funcionarioDAO.autenticarUser(txtUser.getText(), txtPassword.getText());
        
        if (funcionario == null || funcionario.getCpf() == null) {
            Alerts.showAlert("Erro!!", "Erro de Login", "Verifique se as informações estão corretas e tente novamente", AlertType.ERROR);
        }
        else if (funcionario.getCpf().equals(txtUser.getText()) && funcionario.getSenha().equals(txtPassword.getText())) {
            Alerts.showAlert("Login bem sucedido", "Seja Bem-Vindo " + funcionario.getNome(), "Login realizado com sucesso", AlertType.INFORMATION);
            
            txtUser.setText("");
            txtPassword.setText("");
            
            String acesso = funcionario.getVerificarAcesso();
            if (acesso.equals("1") || acesso.equals("2")) {
                Main.TelaHome();
            } else if (acesso.equals("3")) {
                Main.TelaRegistroGarcons();
            } else {
                Alerts.showAlert("Erro", "Erro de Login", "Acesso não reconhecido!", AlertType.ERROR);
            }
        } else {
            Alerts.showAlert("Erro", "Erro de Login", "Dados inválidos", AlertType.ERROR);
        }
    }

}