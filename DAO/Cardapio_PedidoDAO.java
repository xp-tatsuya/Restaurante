package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ConnectionFactory.ConnectionDatabase;
import Model.Cardapio_Pedido;

public class Cardapio_PedidoDAO {

    // CREATE - inserir um novo registro de Cardapio_Pedido
    public void create(Cardapio_Pedido cp) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "INSERT INTO Cardapio_Pedido (codeCardapio, codePedido, quantidade) VALUES (?, ?, ?)";
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, Integer.parseInt(cp.getCodeCardapio()));
            stmt.setInt(2, Integer.parseInt(cp.getCodePedido()));
            stmt.setInt(3, Integer.parseInt(cp.getQuantidade()));
            
            stmt.executeUpdate();
            System.out.println("Cardapio_Pedido cadastrado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar Cardapio_Pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // READ - ler todos os registros de Cardapio_Pedido
    public ArrayList<Cardapio_Pedido> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Cardapio_Pedido> lista = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Cardapio_Pedido";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Cardapio_Pedido cp = new Cardapio_Pedido();
                cp.setId(rs.getString("idCardapioPedido"));
                cp.setCodeCardapio(String.valueOf(rs.getInt("codeCardapio")));
                cp.setCodePedido(String.valueOf(rs.getInt("codePedido")));
                cp.setQuantidade(String.valueOf(rs.getInt("quantidade")));
                
                lista.add(cp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler registros de Cardapio_Pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return lista;
    }
    
    // UPDATE - atualizar os dados de um registro de Cardapio_Pedido
    public void update(Cardapio_Pedido cp) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Cardapio_Pedido SET codeCardapio = ?, codePedido = ?, quantidade = ? WHERE idCardapioPedido = ? OR codePedido = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, Integer.parseInt(cp.getCodeCardapio()));
            stmt.setInt(2, Integer.parseInt(cp.getCodePedido()));
            stmt.setInt(3, Integer.parseInt(cp.getQuantidade()));
            stmt.setString(4, cp.getId());
            stmt.setInt(5, Integer.parseInt(cp.getCodePedido()));
            
            stmt.executeUpdate();
            System.out.println("Cardapio_Pedido atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Cardapio_Pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um registro de Cardapio_Pedido
    public void delete(Cardapio_Pedido cp) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "DELETE FROM Cardapio_Pedido WHERE idCardapioPedido = ? OR codePedido = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, cp.getId());
            stmt.setInt(2, Integer.parseInt(cp.getCodePedido()));
            
            stmt.executeUpdate();
            System.out.println("Cardapio_Pedido deletado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Cardapio_Pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // SEARCH - buscar registros de Cardapio_Pedido por codeCardapio ou codePedido
    public ArrayList<Cardapio_Pedido> search(Cardapio_Pedido filtro) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Cardapio_Pedido> lista = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Cardapio_Pedido WHERE codeCardapio LIKE ? OR codePedido LIKE ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, "%" + filtro.getCodeCardapio() + "%");
            stmt.setString(2, "%" + filtro.getCodePedido() + "%");
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Cardapio_Pedido cp = new Cardapio_Pedido();
                cp.setId(rs.getString("idCardapioPedido"));
                cp.setCodeCardapio(String.valueOf(rs.getInt("codeCardapio")));
                cp.setCodePedido(String.valueOf(rs.getInt("codePedido")));
                cp.setQuantidade(String.valueOf(rs.getInt("quantidade")));
                
                lista.add(cp);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Cardapio_Pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return lista;
    }
}
