package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.math.BigDecimal;
import ConnectionFactory.ConnectionDatabase;
import Model.Produto;

public class ProdutoDAO {

    // CREATE - inserir um novo produto
    public void create(Produto produto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "INSERT INTO Produto (codeFornecedor, nomeProduto, dataFabricacao, dataValidade, marca, categoria, precoUnitario, estoque) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, Integer.parseInt(produto.getCodeFornecedor()));
            stmt.setString(2, produto.getNome());
            stmt.setDate(3, java.sql.Date.valueOf(produto.getDataFab()));
            stmt.setDate(4, java.sql.Date.valueOf(produto.getDataVal()));
            stmt.setString(5, produto.getMarca());
            stmt.setString(6, produto.getCategoria());
            stmt.setBigDecimal(7, new BigDecimal(produto.getPrecoUn()));
            stmt.setInt(8, Integer.parseInt(produto.getEstoque()));
            
            stmt.executeUpdate();
            System.out.println("Produto cadastrado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar produto!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // READ - ler todos os produtos
    public ArrayList<Produto> read() {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Produto> produtos = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Produto";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getString("idProduto"));
                produto.setCodeFornecedor(String.valueOf(rs.getInt("codeFornecedor")));
                produto.setNome(rs.getString("nomeProduto"));
                produto.setDataFab(rs.getDate("dataFabricacao").toString());
                produto.setDataVal(rs.getDate("dataValidade").toString());
                produto.setMarca(rs.getString("marca"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setPrecoUn(rs.getBigDecimal("precoUnitario").toPlainString());
                produto.setEstoque(String.valueOf(rs.getInt("estoque")));
                // precoTotal não está no banco; pode ser calculado se necessário
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao ler os produtos!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return produtos;
    }
    
    // UPDATE - atualizar os dados de um produto
    public void update(Produto produto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "UPDATE Produto SET codeFornecedor = ?, nomeProduto = ?, dataFabricacao = ?, dataValidade = ?, marca = ?, categoria = ?, precoUnitario = ?, estoque = ? WHERE idProduto = ? OR codeFornecedor = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setInt(1, Integer.parseInt(produto.getCodeFornecedor()));
            stmt.setString(2, produto.getNome());
            stmt.setDate(3, java.sql.Date.valueOf(produto.getDataFab()));
            stmt.setDate(4, java.sql.Date.valueOf(produto.getDataVal()));
            stmt.setString(5, produto.getMarca());
            stmt.setString(6, produto.getCategoria());
            stmt.setBigDecimal(7, new BigDecimal(produto.getPrecoUn()));
            stmt.setInt(8, Integer.parseInt(produto.getEstoque()));
            stmt.setString(9, produto.getId());
            stmt.setInt(10, Integer.parseInt(produto.getCodeFornecedor()));
            
            stmt.executeUpdate();
            System.out.println("Produto atualizado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // DELETE - remover um produto
    public void delete(Produto produto) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        
        try {
            String sql = "DELETE FROM Produto WHERE idProduto = ? OR codeFornecedor = ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, produto.getId());
            stmt.setInt(2, Integer.parseInt(produto.getCodeFornecedor()));
            
            stmt.executeUpdate();
            System.out.println("Produto deletado com sucesso!!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt);
        }
    }
    
    // SEARCH - buscar produtos por nome ou codeFornecedor
    public ArrayList<Produto> search(Produto produtoFiltro) {
        Connection con = ConnectionDatabase.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Produto> produtos = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Produto WHERE nomeProduto LIKE ? OR codeFornecedor LIKE ?";
            stmt = con.prepareStatement(sql);
            
            stmt.setString(1, "%" + produtoFiltro.getNome() + "%");
            stmt.setString(2, "%" + produtoFiltro.getCodeFornecedor() + "%");
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getString("idProduto"));
                produto.setCodeFornecedor(String.valueOf(rs.getInt("codeFornecedor")));
                produto.setNome(rs.getString("nomeProduto"));
                produto.setDataFab(rs.getDate("dataFabricacao").toString());
                produto.setDataVal(rs.getDate("dataValidade").toString());
                produto.setMarca(rs.getString("marca"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setPrecoUn(rs.getBigDecimal("precoUnitario").toPlainString());
                produto.setEstoque(String.valueOf(rs.getInt("estoque")));
                
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto!", e);
        } finally {
            ConnectionDatabase.closeConnection(con, stmt, rs);
        }
        
        return produtos;
    }
}
