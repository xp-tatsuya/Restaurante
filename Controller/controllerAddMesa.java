package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DAO.MesaDAO;
import Model.Mesa;
import Util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class controllerAddMesa implements Initializable {

    @FXML private Button btCancelar;
    @FXML private Button btSalvar;
    @FXML private ChoiceBox<String> choiceCondicao;
    @FXML private TextField txtCapacidade;
    @FXML private Label title;

    private final MesaDAO mesaDAO = new MesaDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceCondicao.getItems().addAll("Livre", "Reservada");
        choiceCondicao.setValue("Livre");
        
        //Verficação para validar a  edição
        if (controllerMesa.mesa != null) {
            title.setText("EDITAR CLIENTE");
            Mesa mesaEditar = new Mesa();
        	mesaEditar = controllerMesa.mesa;
			txtCapacidade.setText(mesaEditar.getCapacidade());
			choiceCondicao.setValue(mesaEditar.getCondicao());       
        }
    }

    @FXML
    void ActionCancelar(javafx.event.ActionEvent event) {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionSalvar(javafx.event.ActionEvent event) {
        String capText = txtCapacidade.getText().trim();
        String cond = choiceCondicao.getValue();
        // Validação de campos
        if (capText.isEmpty()) {
            showError("Erro de Validação", "Campo 'Capacidade' não pode ficar vazio.");
            return;
        }
        int capacidade;
        try {
            capacidade = Integer.parseInt(capText);
            if (capacidade <= 0) {
                showError("Erro de Validação", "Capacidade deve ser um número positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Erro de Validação", "Capacidade inválida. Informe um número inteiro.");
            return;
        }
        if (cond == null || cond.isEmpty()) {
            showError("Erro de Validação", "Selecione uma condição para a mesa.");
            return;
        }
        // Cria objeto e salva
        Mesa m = new Mesa();
        
        m.setCapacidade(String.valueOf(capacidade));
        m.setCondicao(cond);
        
        //Verficação se está criando ou editando
		if(controllerMesa.mesa == null) {
	        try {
	            mesaDAO.create(m);
	            Alerts.showAlert("Sucesso", "Mesa cadastrado!", "A mesa foi cadastrada com sucesso!", AlertType.INFORMATION);
	        } catch (Exception e) {
	            showError("Erro ao Salvar", "Ocorreu um erro ao cadastrar a mesa.");
	            e.printStackTrace();
	            return;
	        }
	        Stage stage = (Stage) btSalvar.getScene().getWindow();
	        stage.close();
	    	    
		}else if(controllerMesa.mesa != null) {
			try {
				mesaDAO.update(m);
				Alerts.showAlert("Sucesso", "Mesa editado!", "A mesa foi editada com sucesso!", AlertType.INFORMATION);
			} catch (Exception e) {
				showError("Erro ao Salvar", "Ocorreu um erro ao cadastrar a mesa.");
				e.printStackTrace();
				return;
			}
			Stage stage = (Stage) btSalvar.getScene().getWindow();
			stage.close();
		}

		}
		
    private void showError(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}