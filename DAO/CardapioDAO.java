package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;

import ConnectionFactory.ConnectionDatabase;
import Controller.controllerCardapio;
import Model.Cardapio;

public class CardapioDAO {

    // CREATE - inserir um novo cardápio
	public void create(Cardapio cardapio) {
        String sql = "INSERT INTO Cardapio (nomeCardapio, descricao, categoria, precoUnitario, ativo) "
                   + "VALUES (?, ?, ?, ?, 1)";
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, cardapio.getNome());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setString(3, cardapio.getCategoria());
            stmt.setBigDecimal(4, new BigDecimal(cardapio.getPrecoUnitario()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar cardápio!", e);
        }
    }
    
    // READ - ler todos os cardápios
    public ArrayList<Cardapio> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Cardapio> cardapios = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Cardapio WHERE ativo = 1";
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
            String sql = "UPDATE Cardapio SET nomeCardapio = ?, descricao = ?, categoria = ?, precoUnitario = ? WHERE idCardapio = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, cardapio.getNome());
            stmt.setString(2, cardapio.getDescricao());
            stmt.setString(3, cardapio.getCategoria());
            stmt.setBigDecimal(4, new BigDecimal(cardapio.getPrecoUnitario()));
            stmt.setString(5, controllerCardapio.cardapio.getId());
            
            stmt.executeUpdate();
            System.out.println("Cardápio atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cardápio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um cardápio
    public void delete(String id) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE CARDAPIO SET ATIVO = 0 WHERE IDCARDAPIO = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            
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
            String sql = "SELECT nomeCardapio FROM Cardapio WHERE ativo = 1";
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
    
    public String getIdByNome(String nome) {
    	Connection con = ConnectionDatabase.getConnection();
    	PreparedStatement stmt = null;
    	ResultSet rs = null;
    	String id = null;
    	
        try {
            String sql = "SELECT idCardapio FROM Cardapio WHERE nomeCardapio = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getString("idCardapio");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ID do cardapio!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return id;
    }
    
    public String getPrecoUnitarioById(String id) {
        String preco = null;
        try (Connection con = ConnectionDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(
                 "SELECT precoUnitario FROM Cardapio WHERE idCardapio = ?")) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                preco = rs.getBigDecimal("precoUnitario").toPlainString();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar precoUnitario!", e);
        }
        return preco;
    }
    
}
