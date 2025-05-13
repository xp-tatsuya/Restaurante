package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ConnectionFactory.ConnectionDatabase;
import Controller.controllerFornecedor;
import Model.Fornecedor;

public class FornecedorDAO {

    // CREATE - inserir um novo fornecedor
    public void create(Fornecedor fornecedor) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "INSERT INTO Fornecedor (nomeFornecedor, cnpj, telefone, endereco, ativo) VALUES (?, ?, ?, ?, 1)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEndereco());
            
            stmt.executeUpdate();
            System.out.println("Fornecedor cadastrado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar fornecedor!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // READ - ler todos os fornecedores
    public ArrayList<Fornecedor> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Fornecedor> fornecedores = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Fornecedor WHERE ATIVO = 1";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getString("idFornecedor")); // Convertendo id para String
                fornecedor.setNome(rs.getString("nomeFornecedor"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEndereco(rs.getString("endereco"));
                
                fornecedores.add(fornecedor);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler dados dos fornecedores!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return fornecedores;
    }
    
    // UPDATE - atualizar os dados de um fornecedor
    public void update(Fornecedor fornecedor) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Fornecedor SET nomeFornecedor = ?, cnpj = ?, telefone = ?, endereco = ? WHERE idFornecedor = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEndereco());
            stmt.setString(5, controllerFornecedor.fornecedor.getId());
            
            stmt.executeUpdate();
            System.out.println("Fornecedor atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar fornecedor!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um fornecedor
    public void delete(String id) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE FORNECEDOR SET ATIVO = 0 WHERE IDFORNECEDOR = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar fornecedor!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // SEARCH - buscar fornecedores por nome ou CNPJ
    public ArrayList<Fornecedor> search(Fornecedor fornecedorFiltro) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Fornecedor> fornecedores = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Fornecedor WHERE cnpj LIKE ? OR nomeFornecedor LIKE ? AND ATIVO = 1";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + fornecedorFiltro.getCnpj() + "%");
            stmt.setString(2, "%" + fornecedorFiltro.getNome() + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setId(rs.getString("idFornecedor"));
                fornecedor.setNome(rs.getString("nomeFornecedor"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEndereco(rs.getString("endereco"));
                
                fornecedores.add(fornecedor);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar fornecedor!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return fornecedores;
    }
    
    public ArrayList<String> readFornecedorByNome() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> nomes = new ArrayList<>();

        try {
            String sql = "SELECT nomeFornecedor FROM Fornecedor WHERE ATIVO = 1";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                nomes.add(rs.getString("nomeFornecedor"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler nomes de fornecedores!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return nomes;
    }
    
    public int getIdByNome(String nome) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT idFornecedor FROM Fornecedor WHERE nomeFornecedor = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idFornecedor");
            } else {
                throw new RuntimeException("Fornecedor não encontrado: " + nome);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ID do fornecedor", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
    }
    
    public String getNomebyId(String id) {
    	Connection con = ConnectionDatabase.getConnection();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	try {
    		String sql = "SELECT NOMEFORNECEDOR FROM FORNECEDOR WHERE IDFORNECEDOR = ?";
    		stmt = con.prepareStatement(sql);
    		stmt.setString(1, id);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			return rs.getString("NOMEFORNECEDOR");
    		}else {
    			throw new RuntimeException("Fornecedor não encontrado: " + id);
    		}
    	}catch(SQLException e) {
    		throw new RuntimeException("Erro");
    	}finally {
    		ConnectionDatabase.closeConnection(con, stmt, rs);
    	}
    }
    
}
