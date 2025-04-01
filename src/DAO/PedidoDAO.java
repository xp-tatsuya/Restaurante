package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;
import ConnectionFactory.ConnectionDatabase;
import Model.Pedido;

public class PedidoDAO {

    // CREATE - inserir um novo pedido
    public void create(Pedido pedido) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "INSERT INTO Pedido (codeFuncionario, codeMesa, dataPedido, condicao, observacoes, desconto, precoTotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, Integer.parseInt(pedido.getCodeFuncionario()));
            stmt.setInt(2, Integer.parseInt(pedido.getCodeMesa()));
            stmt.setDate(3, java.sql.Date.valueOf(pedido.getDataPedido()));
            stmt.setString(4, pedido.getCondicao());
            stmt.setString(5, pedido.getObservacoes());
            stmt.setBigDecimal(6, new BigDecimal(pedido.getDesconto()));
            stmt.setBigDecimal(7, new BigDecimal(pedido.getPrecoTotal()));
            
            stmt.executeUpdate();
            System.out.println("Pedido cadastrado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // READ - ler todos os pedidos
    public ArrayList<Pedido> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Pedido";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getString("idPedido"));
                pedido.setCodeFuncionario(String.valueOf(rs.getInt("codeFuncionario")));
                pedido.setCodeMesa(String.valueOf(rs.getInt("codeMesa")));
                pedido.setDataPedido(rs.getDate("dataPedido").toString());
                pedido.setCondicao(rs.getString("condicao"));
                pedido.setObservacoes(rs.getString("observacoes"));
                
                // Se o campo desconto for nulo, atribui null; caso contrário, converte para String
                BigDecimal desconto = rs.getBigDecimal("desconto");
                pedido.setDesconto((desconto == null) ? null : desconto.toPlainString());
                
                pedido.setPrecoTotal(rs.getBigDecimal("precoTotal").toPlainString());
                
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler os pedidos!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return pedidos;
    }
    
    // UPDATE - atualizar os dados de um pedido
    public void update(Pedido pedido) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Pedido SET codeFuncionario = ?, codeMesa = ?, dataPedido = ?, condicao = ?, observacoes = ?, desconto = ?, precoTotal = ? WHERE idPedido = ? OR codeFuncionario = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, Integer.parseInt(pedido.getCodeFuncionario()));
            stmt.setInt(2, Integer.parseInt(pedido.getCodeMesa()));
            stmt.setDate(3, java.sql.Date.valueOf(pedido.getDataPedido()));
            stmt.setString(4, pedido.getCondicao());
            stmt.setString(5, pedido.getObservacoes());
            stmt.setBigDecimal(6, new BigDecimal(pedido.getDesconto()));
            stmt.setBigDecimal(7, new BigDecimal(pedido.getPrecoTotal()));
            stmt.setString(8, pedido.getId());
            stmt.setInt(9, Integer.parseInt(pedido.getCodeFuncionario()));
            
            stmt.executeUpdate();
            System.out.println("Pedido atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um pedido
    public void delete(Pedido pedido) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "DELETE FROM Pedido WHERE idPedido = ? OR codeFuncionario = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, pedido.getId());
            stmt.setInt(2, Integer.parseInt(pedido.getCodeFuncionario()));
            
            stmt.executeUpdate();
            System.out.println("Pedido deletado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // SEARCH - buscar pedidos por condição ou codeFuncionario
    public ArrayList<Pedido> search(Pedido pedidoFiltro) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Pedido> pedidos = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Pedido WHERE condicao LIKE ? OR codeFuncionario LIKE ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, "%" + pedidoFiltro.getCondicao() + "%");
            stmt.setString(2, "%" + pedidoFiltro.getCodeFuncionario() + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getString("idPedido"));
                pedido.setCodeFuncionario(String.valueOf(rs.getInt("codeFuncionario")));
                pedido.setCodeMesa(String.valueOf(rs.getInt("codeMesa")));
                pedido.setDataPedido(rs.getDate("dataPedido").toString());
                pedido.setCondicao(rs.getString("condicao"));
                pedido.setObservacoes(rs.getString("observacoes"));
                
                BigDecimal desconto = rs.getBigDecimal("desconto");
                pedido.setDesconto((desconto == null) ? null : desconto.toPlainString());
                
                pedido.setPrecoTotal(rs.getBigDecimal("precoTotal").toPlainString());
                
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return pedidos;
    }
}
