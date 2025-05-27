// Controller/controllerAddProduto.java
package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import DAO.ProdutoDAO;
import DAO.FornecedorDAO;
import Model.Produto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

public class controllerAddProduto implements Initializable {

    @FXML private Button btCancelar;
    @FXML private Button btSalvar;
    @FXML private TextField txtNome;
    @FXML private TextField txtCategoria;
    @FXML private DatePicker dateFab;
    @FXML private DatePicker dateVal;
    @FXML private TextField txtMarca;
    @FXML private TextField txtFornecedor;
    @FXML private TextField txtPrecoUn;
    @FXML private TextField txtEstoque;
    @FXML private Label title;
    
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();
    
    Produto produto = new Produto();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> fornecedores = fornecedorDAO.readFornecedorByNome();
        TextFields.bindAutoCompletion(txtFornecedor, fornecedores);
        if(controllerProdutos.produto != null) {
        	title.setText("EDITAR PRODUTO");
        	produto = controllerProdutos.produto;
        	txtNome.setText(produto.getNome());
        	txtCategoria.setText(produto.getCategoria());
        	txtMarca.setText(produto.getMarca());
        	String nomeFornecedor = fornecedorDAO.getNomebyId(produto.getCodeFornecedor());
        	txtFornecedor.setText(nomeFornecedor);
        	txtPrecoUn.setText(produto.getPrecoUn());
        	txtEstoque.setText(produto.getEstoque());
        	LocalDate dateFabr = LocalDate.parse(produto.getDataFab());
        	dateFab.setValue(dateFabr);
        	LocalDate dateVali = LocalDate.parse(produto.getDataVal());
        	dateVal.setValue(dateVali);
        }
    }

    @FXML
    void ActionCancelar() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void ActionSalvar() {
    	if(controllerProdutos.produto == null) {
            // Validação básica de campos
            String nome       = txtNome.getText().trim();
            String categoria  = txtCategoria.getText().trim();
            String marca      = txtMarca.getText().trim();
            String fornecedor = txtFornecedor.getText().trim();
            String precoUn    = txtPrecoUn.getText().trim();
            String estoque    = txtEstoque.getText().trim();

            if (nome.isEmpty() || categoria.isEmpty() || marca.isEmpty() || fornecedor.isEmpty() ||
                dateFab.getValue() == null || dateVal.getValue() == null ||
                precoUn.isEmpty() || estoque.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
                return;
            }

            try {
                int idFornecedor = fornecedorDAO.getIdByNome(fornecedor);

                Produto p = new Produto();
                p.setNome(nome);
                p.setCategoria(categoria);
                p.setMarca(marca);
                p.setCodeFornecedor(String.valueOf(idFornecedor));
                p.setDataFab(dateFab.getValue().toString());
                p.setDataVal(dateVal.getValue().toString());
                p.setPrecoUn(precoUn);
                p.setEstoque(estoque);

                produtoDAO.create(p);
                new Alert(Alert.AlertType.INFORMATION, "Produto cadastrado com sucesso!").showAndWait();

                Stage stage = (Stage) btSalvar.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar produto. Verifique os dados e tente novamente.").showAndWait();
            }
    	}else {
    		 String nome = txtNome.getText().trim();
    	        String categoria = txtCategoria.getText().trim();
    	        String marca = txtMarca.getText().trim();
    	        String fornecedor = txtFornecedor.getText().trim();
    	        String precoUn = txtPrecoUn.getText().trim();
    	        String estoque = txtEstoque.getText().trim();

    	        if (nome.isEmpty() || categoria.isEmpty() || marca.isEmpty() || fornecedor.isEmpty() ||
    	            dateFab.getValue() == null || dateVal.getValue() == null ||
    	            precoUn.isEmpty() || estoque.isEmpty()) {
    	            new Alert(Alert.AlertType.WARNING, "Preencha todos os campos.").showAndWait();
    	            return;
    	        }

    	        try {
    	            int idFornecedor = fornecedorDAO.getIdByNome(fornecedor);

    	            // Verificação de nulo CRÍTICA
    	            if (controllerProdutos.produto != null) {
    	                controllerProdutos.produto.setNome(nome);
    	                controllerProdutos.produto.setCategoria(categoria);
    	                controllerProdutos.produto.setMarca(marca);
    	                controllerProdutos.produto.setCodeFornecedor(String.valueOf(idFornecedor));
    	                controllerProdutos.produto.setDataFab(dateFab.getValue().toString());
    	                controllerProdutos.produto.setDataVal(dateVal.getValue().toString());
    	                controllerProdutos.produto.setPrecoUn(precoUn);
    	                controllerProdutos.produto.setEstoque(estoque);

    	                produtoDAO.update(controllerProdutos.produto);
    	                new Alert(Alert.AlertType.INFORMATION, "Produto atualizado com sucesso!").showAndWait();

    	                Stage stage = (Stage) btSalvar.getScene().getWindow();
    	                stage.close();
    	            } else {
    	                new Alert(Alert.AlertType.ERROR, "Erro: Produto não selecionado para edição.").showAndWait();
    	            }

    	        } catch (Exception e) {
    	            e.printStackTrace();
    	            new Alert(Alert.AlertType.ERROR, "Erro ao salvar produto. Verifique os dados e tente novamente.").showAndWait();
            }
        }

    }
}
