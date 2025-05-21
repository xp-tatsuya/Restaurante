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
	
	public List<RegistroVenda> read(String numeroMesa){
		Connection con = ConnectionDatabase.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<RegistroVenda> lista = new ArrayList<>();
		
		try {
			stmt = con.prepareStatement("SELECT * FROM RegistroVendas WHERE Numero_Mesa = ?");
			stmt.setString(1, numeroMesa );
			rs = stmt.executeQuery();
			
			
			while(rs.next()) {
				RegistroVenda rv = new RegistroVenda(
						rs.getString("Numero_Pedido"),
                rs.getString("Nome_Funcionario"),
                rs.getString("Numero_Mesa"),
                rs.getString("Codigo_Cardapio"),
                rs.getString("Nome_Cardapio"),
                rs.getString("Quantidade"),
                rs.getString("Preco_Unitario"),
                rs.getString("Desconto"),
                rs.getString("Preco_Total")
                );
				
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
}
