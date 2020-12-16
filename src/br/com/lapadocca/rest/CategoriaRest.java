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
import br.com.lapadocca.jdbc.JDBCCategoriaDAO;
import br.com.lapadocca.modelo.Categoria;

@Path("categoria")
public class CategoriaRest extends UtilRest {

	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar() {

		try {
			List<Categoria> listaCategorias = new ArrayList<Categoria>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			listaCategorias = jdbcCategoria.buscar();

			conec.fecharConexao();

			return this.buildResponse(listaCategorias);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}

	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String categoriaParam) {

		try {

			Categoria categoria = new Gson().fromJson(categoriaParam, Categoria.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);

			String msg = "";

			boolean retorno = jdbcCategoria.inserir(categoria);

			if (retorno) {
				msg = "Categoria cadastrada com sucesso!";
			} else {
				msg = "Erro ao cadastrar categoria.";
			}

			conec.fecharConexao();

			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@GET
	@Path("/buscarCategoria")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorNome(@QueryParam("valorBusca") String descricao) {
		try {

			List<JsonObject> listaCategorias = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);

			listaCategorias = jdbcCategoria.buscarPorDescricao(descricao);

			conec.fecharConexao();

			String json = new Gson().toJson(listaCategorias);
			return this.buildResponse(json);

		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@DELETE
	@Path("/excluir/{idcategoria}")
	@Consumes("application/*")
	public Response excluir(@PathParam("idcategoria") int idcategoria) {

		try {

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);

			boolean retorno = jdbcCategoria.deletar(idcategoria);

			String msg = "";
			if (retorno) {
				msg = "Categoria excluída com sucesso!";
			} else {
				msg = "Erro ao excluir categoria.";
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
	public Response buscarPorId(@QueryParam("idcategoria") int idcategoria) {
		try {

			Categoria categoria = new Categoria();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);

			categoria = jdbcCategoria.buscarPorId(idcategoria);

			conec.fecharConexao();

			return this.buildResponse(categoria);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String categoriaParam) {
		try {
			Categoria categoria = new Gson().fromJson(categoriaParam, Categoria.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCCategoriaDAO jdbcCategoria = new JDBCCategoriaDAO(conexao);
			
			boolean retorno = jdbcCategoria.alterar(categoria);
			
			String msg = "";
			
			if (retorno) {
				msg="Categoria alterada com sucesso!";
			} else {
				msg="Erro ao alterar categoria!";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

}// fim
