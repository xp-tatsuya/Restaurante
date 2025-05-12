package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ConnectionFactory.ConnectionDatabase;
import Controller.controllerMesa;
import Model.Mesa;

public class MesaDAO {

    // CREATE - inserir uma nova mesa
    public void create(Mesa mesa) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "INSERT INTO Mesa (capacidade, condicao) VALUES (?, ?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(mesa.getCapacidade()));
            stmt.setString(2, mesa.getCondicao());
            
            stmt.executeUpdate();
            System.out.println("Mesa cadastrada com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar mesa!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // READ - ler todas as mesas
    public ArrayList<Mesa> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Mesa> mesas = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Mesa WHERE ATIVO = 1";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setId(rs.getString("idMesa"));
                mesa.setCapacidade(String.valueOf(rs.getInt("capacidade")));
                mesa.setCondicao(rs.getString("condicao"));
                
                mesas.add(mesa);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler informações das mesas!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return mesas;
    }
    
    // UPDATE - atualizar os dados de uma mesa
    public void update(Mesa mesa) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Mesa SET capacidade = ?, condicao = ? WHERE idMesa = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(mesa.getCapacidade()));
            stmt.setString(2, mesa.getCondicao());
            stmt.setString(3, controllerMesa.mesa.getId());
            
            stmt.executeUpdate();
            System.out.println("Mesa atualizada com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar mesa!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover uma mesa
    public void delete(String id) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE MESA SET ATIVO = 0 WHERE IDMESA = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, id);
            
            stmt.executeUpdate();
            System.out.println("Mesa deletada com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar mesa!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // SEARCH - buscar mesas por capacidade ou condição
    public ArrayList<Mesa> search(Mesa mesaFiltro) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Mesa> mesas = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Mesa WHERE IDMESA LIKE ? OR condicao LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + mesaFiltro.getId() + "%");
            stmt.setString(2, "%" + mesaFiltro.getCondicao() + "%");
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Mesa mesa = new Mesa();
                mesa.setId(rs.getString("idMesa"));
                mesa.setCapacidade(String.valueOf(rs.getInt("capacidade")));
                mesa.setCondicao(rs.getString("condicao"));
                
                mesas.add(mesa);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar mesa!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return mesas;
    }
    
    public ArrayList<String> readMesaByCapacidade() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> capacidades = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT capacidade FROM Mesa";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                capacidades.add(String.valueOf(rs.getInt("capacidade")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler capacidades das mesas!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return capacidades;
    }
    
    public ArrayList<String> readMesaById() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<String> ids = new ArrayList<>();
        try {
            String sql = "SELECT idMesa FROM Mesa WHERE ATIVO = 1";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ids.add(rs.getString("idMesa"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler IDs de mesas!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        return ids;
    }
    
}
