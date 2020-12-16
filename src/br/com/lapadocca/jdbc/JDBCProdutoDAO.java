package br.com.lapadocca.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.lapadocca.jdbcinterface.ProdutoDAO;
import br.com.lapadocca.modelo.Produto;

public class JDBCProdutoDAO implements ProdutoDAO {

	private Connection conexao;

	public JDBCProdutoDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean inserir(Produto produto) {

		String comando = "INSERT INTO produto (descricao, quantidade, preco, categoria_idcategoria) VALUES (?,?,?,?)";
		PreparedStatement p;

		try {

			p = this.conexao.prepareStatement(comando);

			p.setString(1, produto.getDescricao());
			p.setInt(2, produto.getQuantidade());
			p.setFloat(3, produto.getPreco());
			p.setInt(4, produto.getIdcategoria());

			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<JsonObject> buscarPorNome(String descricao) {

		String comando = "SELECT produto.*, categoria.descricao AS categorias FROM produto INNER JOIN"
				+ " categoria ON produto.categoria_idcategoria = categoria.idcategoria ";

		if (!descricao.equals("")) {
			// concatena no comando o WHERE buscando no nome do produto o texto da variavel
			// descricao
			comando += "WHERE produto.descricao LIKE '%" + descricao + "%' ";
		}
		// finaliza o comando ordenando alfabeticamente por descricao/nome
		comando += "ORDER BY produto.descricao";

		List<JsonObject> listaProdutos = new ArrayList<JsonObject>();
		JsonObject produto = null;

		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {

				int idproduto = rs.getInt("idproduto");
				descricao = rs.getString("descricao");
				int quantidade = rs.getInt("quantidade");
				float preco = rs.getFloat("preco");

				produto = new JsonObject();
				produto.addProperty("idproduto", idproduto);
				produto.addProperty("descricao", descricao);
				produto.addProperty("quantidade", quantidade);
				produto.addProperty("preco", preco);

				listaProdutos.add(produto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaProdutos;
	}

	public boolean deletar(int idproduto) {
		String comando = "DELETE FROM produto WHERE idproduto = ?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, idproduto);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Produto buscarPorId(int idproduto) {
		
		String comando = "SELECT * FROM produto WHERE produto.idproduto = ?";
		Produto produto = new Produto();
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, idproduto);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				
				String descricao = rs.getString("descricao");
				int quantidade = rs.getInt("quantidade");
				float preco = rs.getFloat("preco");
				int categoria_idcategoria = rs.getInt("categoria_idcategoria");
				
				produto.setIdproduto(idproduto);
				produto.setDescricao(descricao);
				produto.setQuantidade(quantidade);
				produto.setPreco(preco);
				produto.setIdcategoria(categoria_idcategoria);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produto;
	}
	
	public boolean alterar(Produto produto) {
		String comando="UPDATE produto SET categoria_idcategoria=?, descricao=?, quantidade=?, preco=? WHERE idproduto=?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, produto.getIdcategoria());
			p.setString(2, produto.getDescricao());
			p.setInt(3, produto.getQuantidade());
			p.setFloat(4, produto.getPreco());
			p.setInt(5, produto.getIdproduto());
			p.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
