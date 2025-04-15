package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ConnectionFactory.ConnectionDatabase;
import Model.Fornecedor;

public class FornecedorDAO {

    // CREATE - inserir um novo fornecedor
    public void create(Fornecedor fornecedor) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "INSERT INTO Fornecedor (nomeFornecedor, cnpj, telefone, endereco) VALUES (?, ?, ?, ?)";
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
            String sql = "SELECT * FROM Fornecedor";
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
            String sql = "UPDATE Fornecedor SET nomeFornecedor = ?, cnpj = ?, telefone = ?, endereco = ? WHERE idFornecedor = ? OR cnpj = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEndereco());
            stmt.setString(5, fornecedor.getId());
            stmt.setString(6, fornecedor.getCnpj());
            
            stmt.executeUpdate();
            System.out.println("Fornecedor atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar fornecedor!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um fornecedor
    public void delete(Fornecedor fornecedor) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "DELETE FROM Fornecedor WHERE idFornecedor = ? OR cnpj = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, fornecedor.getId());
            stmt.setString(2, fornecedor.getCnpj());
            
            stmt.executeUpdate();
            System.out.println("Fornecedor deletado com sucesso!!");
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
            String sql = "SELECT * FROM Fornecedor WHERE cnpj LIKE ? OR nomeFornecedor LIKE ?";
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
}
