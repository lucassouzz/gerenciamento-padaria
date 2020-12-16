package br.com.lapadocca.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.lapadocca.bd.Conexao;
import br.com.lapadocca.jdbc.JDBCProdutoDAO;
import br.com.lapadocca.modelo.Produto;

@Path("produto")
public class ProdutoRest extends UtilRest {

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String produtoParam) {

		try {

			// System.out.println(produtoParam);
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();

			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			boolean retorno = jdbcProduto.inserir(produto);
			String msg = "";

			/*
			 * System.out.println(produto.getIdcategoria());
			 * System.out.println(produto.getDescricao());
			 * System.out.println(produto.getQuantidade());
			 * System.out.println(produto.getPreco());
			 */

			if (retorno) {
				msg = "Produto cadastrado com sucesso!";
			} else {
				msg = "Erro ao cadastrar produto.";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String descricao) {

		try {

			List<JsonObject> listaProdutos = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);
			listaProdutos = jdbcProduto.buscarPorNome(descricao);
			conec.fecharConexao();

			String json = new Gson().toJson(listaProdutos);
			return this.buildResponse(json);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@DELETE
	@Path("/excluir/{idproduto}")
	@Consumes("application/*")
	public Response excluir(@PathParam("idproduto") int idproduto) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

			boolean retorno = jdbcProduto.deletar(idproduto);
			String msg = "";

			if (retorno) {
				msg = "Produto excluído com sucesso!";
			} else {
				msg = "Erro ao excluir o produto.";
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("idproduto") int idproduto) {
		try {
			Produto produto = new Produto();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

			produto = jdbcProduto.buscarPorId(idproduto);

			conec.fecharConexao();

			return this.buildResponse(produto);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String produtoParam) {
		try {
			Produto produto = new Gson().fromJson(produtoParam, Produto.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCProdutoDAO jdbcProduto = new JDBCProdutoDAO(conexao);

			boolean retorno = jdbcProduto.alterar(produto);
			String msg = "";
			if (retorno) {
				msg = "Produto alterado com sucesso!";
			} else {
				msg = "Erro ao alterar produto.";
			}

			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

}
