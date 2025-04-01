package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ConnectionFactory.ConnectionDatabase;
import Model.Produto_Cardapio;

public class Produto_CardapioDAO {

    // CREATE - inserir um novo registro de Produto_Cardapio
    public void create(Produto_Cardapio produtoCardapio) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "INSERT INTO Produto_Cardapio (codeCardapio, codeProduto, quantidade) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, Integer.parseInt(produtoCardapio.getCodeCardapio()));
            stmt.setInt(2, Integer.parseInt(produtoCardapio.getCodeProduto()));
            stmt.setInt(3, Integer.parseInt(produtoCardapio.getQuantidade()));
            
            stmt.executeUpdate();
            System.out.println("Produto_Cardapio cadastrado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar Produto_Cardapio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // READ - ler todos os registros de Produto_Cardapio
    public ArrayList<Produto_Cardapio> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Produto_Cardapio> lista = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Produto_Cardapio";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Produto_Cardapio pc = new Produto_Cardapio();
                pc.setId(rs.getString("idProdutoCardapio"));
                pc.setCodeCardapio(String.valueOf(rs.getInt("codeCardapio")));
                pc.setCodeProduto(String.valueOf(rs.getInt("codeProduto")));
                pc.setQuantidade(String.valueOf(rs.getInt("quantidade")));
                
                lista.add(pc);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler registros de Produto_Cardapio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return lista;
    }
    
    // UPDATE - atualizar os dados de um registro de Produto_Cardapio
    public void update(Produto_Cardapio produtoCardapio) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Produto_Cardapio SET codeCardapio = ?, codeProduto = ?, quantidade = ? WHERE idProdutoCardapio = ? OR codeProduto = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, Integer.parseInt(produtoCardapio.getCodeCardapio()));
            stmt.setInt(2, Integer.parseInt(produtoCardapio.getCodeProduto()));
            stmt.setInt(3, Integer.parseInt(produtoCardapio.getQuantidade()));
            stmt.setString(4, produtoCardapio.getId());
            stmt.setInt(5, Integer.parseInt(produtoCardapio.getCodeProduto()));
            
            stmt.executeUpdate();
            System.out.println("Produto_Cardapio atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Produto_Cardapio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um registro de Produto_Cardapio
    public void delete(Produto_Cardapio produtoCardapio) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "DELETE FROM Produto_Cardapio WHERE idProdutoCardapio = ? OR codeProduto = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, produtoCardapio.getId());
            stmt.setInt(2, Integer.parseInt(produtoCardapio.getCodeProduto()));
            
            stmt.executeUpdate();
            System.out.println("Produto_Cardapio deletado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Produto_Cardapio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // SEARCH - buscar registros de Produto_Cardapio por codeCardapio ou codeProduto
    public ArrayList<Produto_Cardapio> search(Produto_Cardapio filtro) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Produto_Cardapio> lista = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Produto_Cardapio WHERE codeCardapio LIKE ? OR codeProduto LIKE ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, "%" + filtro.getCodeCardapio() + "%");
            stmt.setString(2, "%" + filtro.getCodeProduto() + "%");
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Produto_Cardapio pc = new Produto_Cardapio();
                pc.setId(rs.getString("idProdutoCardapio"));
                pc.setCodeCardapio(String.valueOf(rs.getInt("codeCardapio")));
                pc.setCodeProduto(String.valueOf(rs.getInt("codeProduto")));
                pc.setQuantidade(String.valueOf(rs.getInt("quantidade")));
                
                lista.add(pc);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Produto_Cardapio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return lista;
    }
}
