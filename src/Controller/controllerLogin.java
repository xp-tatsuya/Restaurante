package Controller;

import java.io.IOException;

import DAO.FuncionarioDAO;
import Model.Funcionario;
import Util.Alerts;
import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class controllerLogin {

	 @FXML
	    private Button btLogin;

	    @FXML
	    private PasswordField txtPassword;

	    @FXML
	    private TextField txtUser;
	    
	    public static Funcionario funcionario = new Funcionario();

	    @FXML
	    void actionLogin(ActionEvent event) throws IOException {
	    	
	        if(txtUser.getText().isEmpty() || txtPassword.getText().isEmpty()){
	            Alerts.showAlert("Erro!!!", "Erro de Login", "Preencha as informações de login e senha para acessar!", AlertType.ERROR);
	            return;
	        }
	        
	        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	        Funcionario funcionario = funcionarioDAO.autenticarUser(txtUser.getText(), txtPassword.getText());
	        
	        if(funcionario == null || funcionario.getCpf() == null) {
	            Alerts.showAlert("Erro!!", "Erro de Login", "Verifique se as informações estão corretas e tente novamente", AlertType.ERROR);
	        }
	        
	        else if (funcionario.getCpf().equals(txtUser.getText()) && funcionario.getSenha().equals(txtPassword.getText())) {
	            Alerts.showAlert("Login bem sucedido", "Seja Bem-Vindo " + funcionario.getNome(), "Login realizado com sucesso", AlertType.INFORMATION);
	            
	            txtUser.setText("");
	            txtPassword.setText("");
	            
	            Main.TelaHome();
	        } else {
	            Alerts.showAlert("Erro", "Erro de Login", "Dados inválidos", AlertType.ERROR);
	        }
	    }


}
