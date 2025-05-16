package Controller;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.CardapioDAO;
import Model.Cardapio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class controllerAddCardapio implements Initializable {

    @FXML private Button btCancelar;
    @FXML private Button btSalvar;
    @FXML private TextField txtNome;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtValorUn;
    @FXML private Label title;
    
    private final CardapioDAO cardapioDAO = new CardapioDAO();
    
    private Cardapio cardapio = new Cardapio();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	if(controllerCardapio.cardapio != null) {
    		title.setText("EDITAR PRATO");
    		cardapio = controllerCardapio.cardapio;
    		txtNome.setText(cardapio.getNome());
    		txtDescricao.setText(cardapio.getDescricao());
    		txtCategoria.setText(cardapio.getCategoria());
    		txtValorUn.setText(cardapio.getPrecoUnitario());
    	}
    }

    @FXML
    void ActionCancelar() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionSalvar() {
    	if(controllerCardapio.cardapio == null) {
    		try {
                String nome = txtNome.getText().trim();
                String desc = txtDescricao.getText().trim();
                String cat  = txtCategoria.getText().trim();
                String val  = txtValorUn.getText().trim();

                if (nome.isEmpty() || desc.isEmpty() || cat.isEmpty() || val.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
                    return;
                }

                Cardapio c = new Cardapio();
                c.setNome(nome);
                c.setDescricao(desc);
                c.setCategoria(cat);
                c.setPrecoUnitario(val);

                cardapioDAO.create(c);

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Sucesso");
                info.setHeaderText(null);
                info.setContentText("Prato cadastrado com sucesso!");
                info.showAndWait();

                Stage stage = (Stage) btSalvar.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar o cardápio.").showAndWait();
            }
    	}else {
       		try {
                String nome = txtNome.getText().trim();
                String desc = txtDescricao.getText().trim();
                String cat  = txtCategoria.getText().trim();
                String val  = txtValorUn.getText().trim();

                if (nome.isEmpty() || desc.isEmpty() || cat.isEmpty() || val.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
                    return;
                }

                Cardapio c = new Cardapio();
                c.setNome(nome);
                c.setDescricao(desc);
                c.setCategoria(cat);
                c.setPrecoUnitario(val);

                cardapioDAO.update(c);

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Sucesso");
                info.setHeaderText(null);
                info.setContentText("Prato cadastrado com sucesso!");
                info.showAndWait();

                Stage stage = (Stage) btSalvar.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar o cardápio.").showAndWait();
            }
    	}
        
    }
}
