package br.com.lapadocca.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;

import com.google.gson.JsonObject;

import br.com.lapadocca.jdbcinterface.VendasDAO;
import br.com.lapadocca.modelo.VHP;
import br.com.lapadocca.modelo.Vendas;

public class JDBCVendasDAO implements VendasDAO {

	private Connection conexao;

	// Estudar melhor esse método
	public JDBCVendasDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public List<JsonObject> buscar(String descricao) {
		String comando = "SELECT descricao, idproduto, preco FROM produto";

		List<JsonObject> listaProd = new ArrayList<JsonObject>();
		JsonObject produto = null;
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {

				descricao = rs.getString("descricao");
				int idproduto = rs.getInt("idproduto");
				float preco = rs.getFloat("preco");

				produto = new JsonObject();
				produto.addProperty("descricao", descricao);
				produto.addProperty("idproduto", idproduto);
				produto.addProperty("preco", preco);

				listaProd.add(produto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaProd;
	}
	
	public JsonObject buscarDadosProd(String idprod){
		
		String comando = "SELECT * FROM produto WHERE idproduto = '"+idprod+"'";
		JsonObject prod = null;
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while(rs.next()) {
				int quantidade = rs.getInt("quantidade");
				float preco = rs.getFloat("preco");
				
				prod = new JsonObject();
				prod.addProperty("quantidade", quantidade);
				prod.addProperty("preco", preco);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return prod;
		
	}
	
	public boolean salvarDadosVenda (VHP vhp) {
				
//		System.out.println(vhp.getIdprod());
//		System.out.println(vhp.getIdvenda());
//		System.out.println(vhp.getQnt());
		String comando = "INSERT INTO produto_has_vendas (produto_idproduto, vendas_idvendas, quantidade)"
				+ " VALUES (?,?,?)";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, vhp.getIdprod());
			p.setInt(2, vhp.getIdvenda());
			p.setInt(3, vhp.getQnt());
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}

	public JsonObject inserirRegVenda(Vendas vendas) {

		String comando = "INSERT INTO vendas (dataa, usuario_idusuario) VALUES (?, 1)";
		JsonObject prod = null;
		PreparedStatement p;

		try {

			p = this.conexao.prepareStatement(comando);
			p.setString(1, vendas.getData());
			p.execute();
			
			String comandoId = "SELECT last_insert_id()";
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comandoId);
			
			while(rs.next()) {
				int idInserido = rs.getInt("last_insert_id()");
				prod = new JsonObject();
				prod.addProperty("idReg", idInserido);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prod;

	}
	
	public List<JsonObject> bucarListaVHP() {
		
		String comando = "SELECT produto.idproduto, produto.descricao, produto.preco, produto_has_vendas.quantidade FROM produto INNER JOIN produto_has_vendas \r\n" + 
				"WHERE produto.idproduto = produto_has_vendas.produto_idproduto";
		
		List<JsonObject> listaVHP = new ArrayList<JsonObject>();
		JsonObject vhp = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while(rs.next()) {
				int idproduto = rs.getInt("produto.idproduto");
				String descricao = rs.getString("produto.descricao");
				float preco = rs.getFloat("produto.preco");
				int quantidade = rs.getInt("produto_has_vendas.quantidade");
				
				vhp = new JsonObject();
				vhp.addProperty("idproduto", idproduto);
				vhp.addProperty("descricao", descricao);
				vhp.addProperty("preco", preco);
				vhp.addProperty("quantidade", quantidade);
				
				listaVHP.add(vhp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaVHP;
	}


}
