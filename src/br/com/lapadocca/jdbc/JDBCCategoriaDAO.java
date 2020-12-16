package br.com.lapadocca.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.lapadocca.jdbcinterface.CategoriaDAO;
import br.com.lapadocca.modelo.Categoria;

//indicando que a classe implementa a interface CategoriaDAO
public class JDBCCategoriaDAO implements CategoriaDAO {

	private Connection conexao;

	public JDBCCategoriaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<Categoria> buscar() {

		// Criação da instrução Sql
		String comando = "SELECT * FROM categoria";

		// criação de uma lista para armazenar cada categoria encontrada
		List<Categoria> listCategorias = new ArrayList<Categoria>();

		// criação do objeto categoria com um valor null
		Categoria categoria = null;
		try {
			// uso da conexão do banco para prepará-lo para a instrução sql
			Statement stmt = conexao.createStatement();

			// execução da instrução criada previamente
			// e armazenamento do resultado no objeto rs
			ResultSet rs = stmt.executeQuery(comando);

			// enquanto houver proxima linha no resultado
			while (rs.next()) {

				// criacao de instancia da classe categoria
				categoria = new Categoria();

				// recebimento dos 2 dados encontrados no BD para cada linha encontrada
				int idcategoria = rs.getInt("idcategoria");
				String descricao = rs.getString("descricao");

				// setando no objeto categoria os valores encontrados
				categoria.setIdcategoria(idcategoria);
				categoria.setDescricao(descricao);

				// adicao na instância contida no objeto Categoria na lista de categorias
				listCategorias.add(categoria);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// retorna para quem chamou o método a lista criada;
		return listCategorias;
	}

	public boolean inserir(Categoria categoria) {

		String comando = "INSERT INTO categoria (idcategoria, descricao) VALUES (?,?)";

		PreparedStatement p;

		try {

			p = this.conexao.prepareStatement(comando);

			p.setInt(1, categoria.getIdcategoria());
			p.setString(2, categoria.getDescricao());
			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<JsonObject> buscarPorDescricao(String descricao) {

		String comando = "SELECT * FROM categoria ";

		if (!descricao.equals("")) {

			comando += "WHERE descricao LIKE '%" + descricao + "%' ";

		}

		comando += "ORDER BY categoria.descricao ASC";

		List<JsonObject> listaCategorias = new ArrayList<JsonObject>();
		JsonObject categoria = null;

		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {

				int idcategoria = rs.getInt("idcategoria");
				descricao = rs.getString("descricao");

				categoria = new JsonObject();
				categoria.addProperty("idcategoria", idcategoria);
				categoria.addProperty("descricao", descricao);

				listaCategorias.add(categoria);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaCategorias;
	}

	public boolean deletar(int idcategoria) {

		String comando = "DELETE FROM categoria WHERE idcategoria = ?";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, idcategoria);
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public Categoria buscarPorId(int idcategoria) {
		String comando = "SELECT * FROM categoria WHERE idcategoria = ?";
		Categoria categoria = new Categoria();
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, idcategoria);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				
				String descricao = rs.getString("descricao");
				
				categoria.setIdcategoria(idcategoria);
				categoria.setDescricao(descricao);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoria;
	}
	
	public boolean alterar(Categoria categoria) {
		
		String comando = "UPDATE categoria SET descricao=? WHERE idcategoria=?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, categoria.getDescricao());
			p.setInt(2, categoria.getIdcategoria());
			
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}// FIM
