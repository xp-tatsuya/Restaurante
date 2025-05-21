package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ConnectionFactory.ConnectionDatabase;
import Model.RegistroVenda;

public class RegistroVendaDAO {
	
	public ArrayList<RegistroVenda> read(String idCardapioPedido){
		Connection con = ConnectionDatabase.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<RegistroVenda> lista = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement("SELECT * FROM RegistroVendas WHERE Numero_Mesa = ? AND condicao = 'Pendente'");
			stmt.setString(1, idCardapioPedido);
			rs = stmt.executeQuery();
			
			
			while(rs.next()) {
				RegistroVenda rv = new RegistroVenda();
				rv.setIdCardapioPedido(rs.getString("IdCardapioPedido"));
				rv.setNumeroPedido(rs.getString("Numero_Pedido"));
                rv.setNomeFuncionario(rs.getString("Nome_Funcionario"));
                rv.setNumeroMesa(rs.getString("Numero_Mesa"));;
                rv.setCodigoCardapio(rs.getString("Codigo_Cardapio"));;
                rv.setNomeCardapio(rs.getString("Nome_Cardapio"));;
                rv.setObservacao(rs.getString("observacao"));;
                rv.setQuantidade(rs.getString("Quantidade"));;
                rv.setValorUnitario(rs.getString("Preco_Unitario"));
                rv.setDesconto(rs.getString("Desconto"));
                rv.setValorTotal(rs.getString("Preco_Total"));
				
				lista.add(rv);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionDatabase.closeConnection(con, stmt, rs);
		}
		
		return lista;
		
	}
	
	public void delete(RegistroVenda registroVenda) {
		Connection con = ConnectionDatabase.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("DELETE FROM Cardapio_Pedido WHERE idCardapioPedido = ?");
			stmt.setString(1, registroVenda.getIdCardapioPedido());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionDatabase.closeConnection(con, stmt);
		}
	}
	
	public void deleteByPedido(String idPedido) {
	    Connection con = ConnectionDatabase.getConnection();
	    try (PreparedStatement stmt = con.prepareStatement(
	              "DELETE FROM Cardapio_Pedido WHERE codePedido = ?")) {
	        stmt.setString(1, idPedido);
	        stmt.executeUpdate();
	    } catch(SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        ConnectionDatabase.closeConnection(con);
	    }
	}

	
}