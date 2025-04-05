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
public class controllerLogin {

    @FXML
    private Button btEntrar;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private TextField txtUsuario;
    
    public static Funcionario funcionario = new Funcionario();
    
    @FXML
    void actionLogin(ActionEvent event) throws IOException {
    	
    	FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    	
    	funcionario = funcionarioDAO.autenticarUser(txtUsuario.getText(), txtSenha.getText());
    	System.out.println(funcionario.getNome());
    	
    	if(funcionario.getCpf() == null) {
    		Alerts.showAlert("Erro!!", "Erro de Login", "Verifique se as informaões estão corretas e tente novamente", AlertType.ERROR);
    	} else if(txtUsuario.getText().equals("") || txtSenha.getText().equals("")) {
    		Alerts.showAlert("Erro!!!", "Erro de Login", "Preencha as informações de login e senha para acessar!", AlertType.ERROR);
    	} else if (funcionario.getCpf().equals(txtUsuario.getText()) && funcionario.getSenha().equals(txtSenha.getText())) {
    		Alerts.showAlert("Login bem sucedido", "Seja Bem-Vindo " + funcionario.getNome(), "Agora que acesssou vai trabalha fih", AlertType.INFORMATION);
    		
    		txtUsuario.setText("");
    		txtSenha.setText("");
    		
    		Main.TelaHome();
    	} else {
    		Alerts.showAlert("Erro", "Erro '-'", "Erro", AlertType.ERROR);
    	}

    }
    }