package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Util.cpfValidator;

import ConnectionFactory.ConnectionDatabase;
import Controller.controllerFuncionario;
import Model.Funcionario;
import Util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class FuncionarioDAO {

    // CREATE - inserir um novo funcionário
    public void create(Funcionario funcionario) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO Funcionario (nomeFuncionario, senha, verificarAcesso, cpfFuncionario, emailFuncionario, telefoneFuncionario, generoFuncionario, enderecoFuncionario, dataNascFuncionario, cargo, salario, dataDeAdmissao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getSenha());
            stmt.setString(3, funcionario.getVerificarAcesso());
            stmt.setString(4, funcionario.getCpf());
            stmt.setString(5, funcionario.getEmail());
            stmt.setString(6, funcionario.getTelefone());
            stmt.setString(7, funcionario.getGenero());
            stmt.setString(8, funcionario.getEndereco());
            stmt.setString(9, funcionario.getDataNasc());
            stmt.setString(10, funcionario.getCargo());
            stmt.setString(11, funcionario.getSalario());
            stmt.setString(12, funcionario.getDataAdms());
            
            stmt.executeUpdate();
            System.out.println("Funcionário cadastrado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar funcionário!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // READ - ler todos os funcionários
    public ArrayList<Funcionario> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Funcionario WHERE ATIVO = 1";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getString("idFuncionario"));
                funcionario.setNome(rs.getString("nomeFuncionario"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setVerificarAcesso(rs.getString("verificarAcesso"));
                funcionario.setCpf(rs.getString("cpfFuncionario"));
                funcionario.setEmail(rs.getString("emailFuncionario"));
                funcionario.setTelefone(rs.getString("telefoneFuncionario"));
                funcionario.setGenero(rs.getString("generoFuncionario"));
                funcionario.setEndereco(rs.getString("enderecoFuncionario"));
                java.sql.Date dataNasc = rs.getDate("dataNascFuncionario");
                funcionario.setDataNasc(dataNasc.toLocalDate().toString());
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setSalario(rs.getString("salario"));
                java.sql.Date dataAdms = rs.getDate("dataDeAdmissao");
                funcionario.setDataAdms(dataAdms.toLocalDate().toString());
                
                
                
                funcionarios.add(funcionario);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler informações dos funcionários!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return funcionarios;
    }
    
    // UPDATE - atualizar os dados de um funcionário
    public void update(Funcionario funcionario) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Funcionario SET nomeFuncionario = ?, senha = ?, verificarAcesso = ?, cpfFuncionario = ?, emailFuncionario = ?, telefoneFuncionario = ?, generoFuncionario = ?, enderecoFuncionario = ?, dataNascFuncionario = ?, cargo = ?, salario = ?, dataDeAdmissao = ? WHERE idFuncionario = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getSenha());
            stmt.setString(3, funcionario.getVerificarAcesso());
            stmt.setString(4, funcionario.getCpf());
            stmt.setString(5, funcionario.getEmail());
            stmt.setString(6, funcionario.getTelefone());
            stmt.setString(7, funcionario.getGenero());
            stmt.setString(8, funcionario.getEndereco());
            stmt.setString(9, funcionario.getDataNasc());
            stmt.setString(10, funcionario.getCargo());
            stmt.setString(11, funcionario.getSalario());
            stmt.setString(12, funcionario.getDataAdms());
            stmt.setString(13, controllerFuncionario.funcionario.getId());

            
            stmt.executeUpdate();
            System.out.println("Funcionário atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um funcionário
    public void delete(String id) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE FUNCIONARIO SET ATIVO = 0 WHERE IDFUNCIONARIO = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            
            stmt.executeUpdate();
            System.out.println("Funcionário deletado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar funcionário!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // SEARCH - buscar funcionários por nome ou CPF
    public ArrayList<Funcionario> search(Funcionario funcionarioFiltro) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Funcionario WHERE cpfFuncionario LIKE ? OR nomeFuncionario LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + funcionarioFiltro.getCpf() + "%");
            stmt.setString(2, "%" + funcionarioFiltro.getNome() + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getString("idFuncionario"));
                funcionario.setNome(rs.getString("nomeFuncionario"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setVerificarAcesso(rs.getString("verificarAcesso"));
                funcionario.setCpf(rs.getString("cpfFuncionario"));
                funcionario.setEmail(rs.getString("emailFuncionario"));
                funcionario.setTelefone(rs.getString("telefoneFuncionario"));
                funcionario.setGenero(rs.getString("generoFuncionario"));
                funcionario.setEndereco(rs.getString("enderecoFuncionario"));
                funcionario.setDataNasc(rs.getString("dataNascFuncionario"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setSalario(rs.getString("salario"));
                funcionario.setDataAdms(rs.getString("dataDeAdmissao"));
                
                funcionarios.add(funcionario);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return funcionarios;
    }
    
    // Autenticar na Tela Login com validação de CPF
    public Funcionario autenticarUser(String cpf, String senha) {
    	if (!cpfValidator.isValid(cpf)) {
    	      Alerts.showAlert("Erro!!", "CPF inválido", "O CPF informado não é válido", AlertType.ERROR);
    	      return null;
    	  }
        
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Funcionario func = null;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM Funcionario WHERE cpfFuncionario = ? AND senha = ?");
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                func = new Funcionario();
                func.setId(rs.getString("idFuncionario"));
                func.setNome(rs.getString("nomeFuncionario"));
                func.setSenha(rs.getString("senha"));
                func.setVerificarAcesso(rs.getString("verificarAcesso"));
                func.setCpf(rs.getString("cpfFuncionario"));
                func.setEmail(rs.getString("emailFuncionario"));
                func.setTelefone(rs.getString("telefoneFuncionario"));
                func.setGenero(rs.getString("generoFuncionario"));
                func.setEndereco(rs.getString("enderecoFuncionario"));
                func.setDataNasc(rs.getString("dataNascFuncionario"));
                func.setCargo(rs.getString("cargo"));
                func.setSalario(rs.getString("salario"));
                func.setDataAdms(rs.getString("dataDeAdmissao"));
            }
            
        } catch (SQLException e) {
            Alerts.showAlert("Erro!!", "Erro de conexão", "Falha ao consultar informações no banco de dados", AlertType.ERROR);
            throw new RuntimeException("Erro de autenticação", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return func;
    }
    
    public Funcionario autenticarUserByNome(String nome, String senha) {
    	Connection con = ConnectionDatabase.getConnection();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	Funcionario func = null;
    	 try {
             stmt = con.prepareStatement("SELECT * FROM Funcionario WHERE nomeFuncionario = ? AND senha = ?");
             stmt.setString(1, nome);
             stmt.setString(2, senha);
             rs = stmt.executeQuery();
             
             if (rs.next()) {
                 func = new Funcionario();
                 func.setId(rs.getString("idFuncionario"));
                 func.setNome(rs.getString("nomeFuncionario"));
                 func.setSenha(rs.getString("senha"));
                 func.setVerificarAcesso(rs.getString("verificarAcesso"));
                 func.setCpf(rs.getString("cpfFuncionario"));
                 func.setEmail(rs.getString("emailFuncionario"));
                 func.setTelefone(rs.getString("telefoneFuncionario"));
                 func.setGenero(rs.getString("generoFuncionario"));
                 func.setEndereco(rs.getString("enderecoFuncionario"));
                 func.setDataNasc(rs.getString("dataNascFuncionario"));
                 func.setCargo(rs.getString("cargo"));
                 func.setSalario(rs.getString("salario"));
                 func.setDataAdms(rs.getString("dataDeAdmissao"));
             }
             
         } catch (SQLException e) {
             Alerts.showAlert("Erro!!", "Erro de conexão", "Falha ao consultar informações no banco de dados", AlertType.ERROR);
             throw new RuntimeException("Erro de autenticação", e);
         } finally {
             ConnectionDatabase.closeConnection(con, stmt, rs);
         }
    	 
    	 return func;
    }
    
    public ArrayList<String> readFuncionarioByNome() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> nomes = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT nomeFuncionario FROM Funcionario WHERE ATIVO = 1");
            rs = stmt.executeQuery();
            while (rs.next()) {
                nomes.add(rs.getString("nomeFuncionario"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler nomes de funcionários!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return nomes;
    }
    
    public String getIdByNome(String nome) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String id = null;
        try {
            String sql = "SELECT idFuncionario FROM Funcionario WHERE nomeFuncionario = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getString("idFuncionario");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ID do funcionário!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return id;
    }
    
    public String getNomeById(String id) {
        String nome = null;
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                 "SELECT nomeFuncionario FROM Funcionario WHERE idFuncionario = ?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nomeFuncionario");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar nomeFuncionario!", e);
        }
        return nome;
    }
    
}