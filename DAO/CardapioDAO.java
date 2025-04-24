package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;

import ConnectionFactory.ConnectionDatabase;
import Model.Cardapio;

public class CardapioDAO {

    // CREATE - inserir um novo cardápio
    public void create(Cardapio cardapio) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "INSERT INTO Cardapio (nomeCardapio, descricao, categoria, precoUnitario) VALUES (?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cardapio.getNome());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setString(3, cardapio.getCategoria());
            stmt.setBigDecimal(4, new BigDecimal(cardapio.getPrecoUnitario()));
            
            stmt.executeUpdate();
            System.out.println("Cardápio cadastrado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar cardápio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // READ - ler todos os cardápios
    public ArrayList<Cardapio> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Cardapio> cardapios = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Cardapio";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cardapio cardapio = new Cardapio();
                cardapio.setId(rs.getString("idCardapio"));
                cardapio.setNome(rs.getString("nomeCardapio"));
                cardapio.setDescricao(rs.getString("descricao"));
                cardapio.setCategoria(rs.getString("categoria"));
                cardapio.setPrecoUnitario(rs.getBigDecimal("precoUnitario").toPlainString());
                
                cardapios.add(cardapio);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler os cardápios!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return cardapios;
    }
    
    // UPDATE - atualizar os dados de um cardápio
    public void update(Cardapio cardapio) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Cardapio SET nomeCardapio = ?, descricao = ?, categoria = ?, precoUnitario = ? WHERE idCardapio = ? OR nomeCardapio = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cardapio.getNome());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setString(3, cardapio.getCategoria());
            stmt.setBigDecimal(4, new BigDecimal(cardapio.getPrecoUnitario()));
            stmt.setString(5, cardapio.getId());
            stmt.setString(6, cardapio.getNome());
            
            stmt.executeUpdate();
            System.out.println("Cardápio atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cardápio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um cardápio
    public void delete(Cardapio cardapio) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "DELETE FROM Cardapio WHERE idCardapio = ? OR nomeCardapio = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cardapio.getId());
            stmt.setString(2, cardapio.getNome());
            
            stmt.executeUpdate();
            System.out.println("Cardápio deletado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cardápio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // SEARCH - buscar cardápios por nome ou categoria
    public ArrayList<Cardapio> search(Cardapio cardapioFiltro) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Cardapio> cardapios = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Cardapio WHERE nomeCardapio LIKE ? OR categoria LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + cardapioFiltro.getNome() + "%");
            stmt.setString(2, "%" + cardapioFiltro.getCategoria() + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Cardapio cardapio = new Cardapio();
                cardapio.setId(rs.getString("idCardapio"));
                cardapio.setNome(rs.getString("nomeCardapio"));
                cardapio.setDescricao(rs.getString("descricao"));
                cardapio.setCategoria(rs.getString("categoria"));
                cardapio.setPrecoUnitario(rs.getBigDecimal("precoUnitario").toPlainString());
                
                cardapios.add(cardapio);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cardápio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return cardapios;
    }
    
    public ArrayList<String> readCardapioByNome() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> nomes = new ArrayList<>();
        try {
            String sql = "SELECT nomeCardapio FROM Cardapio";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                nomes.add(rs.getString("nomeCardapio"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler nomes de cardápios!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return nomes;
    }
    
}
