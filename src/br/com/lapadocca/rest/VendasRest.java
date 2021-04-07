package br.com.lapadocca.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.lapadocca.bd.Conexao;
import br.com.lapadocca.jdbc.JDBCVendasDAO;
import br.com.lapadocca.modelo.VHP;
import br.com.lapadocca.modelo.Vendas;

@Path("vendas")
public class VendasRest extends UtilRest {

	@GET
	@Path("/buscar")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(String descricao) {
		try {
			List<JsonObject> listProdutoAutocomplete = new ArrayList<JsonObject>();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCVendasDAO jdbcVendas = new JDBCVendasDAO(conexao);
			listProdutoAutocomplete = jdbcVendas.buscar(descricao);
			conec.fecharConexao();

			String json = new Gson().toJson(listProdutoAutocomplete);
			//System.out.println(json);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@GET
	@Path("/dadosProd")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarDadosProd(@QueryParam("idprod") String idprod) {
		try {
			JsonObject dadosProd = new JsonObject();

			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCVendasDAO jdbcVendas = new JDBCVendasDAO(conexao);
			dadosProd = jdbcVendas.buscarDadosProd(idprod);
			conec.fecharConexao();

			String json = new Gson().toJson(dadosProd);
			return this.buildResponse(json);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}

	@POST
	@Path("/inserirRegVenda")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserir(String dadosVendaParam) {
		try {
			Vendas vendas = new Gson().fromJson(dadosVendaParam, Vendas.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCVendasDAO jdbcVendas = new JDBCVendasDAO(conexao);
			JsonObject jsonIdInserido = new JsonObject();
			jsonIdInserido = jdbcVendas.inserirRegVenda(vendas);
			conec.fecharConexao();

			return this.buildResponse(jsonIdInserido);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}

	}
	
	@POST
	@Path("/salvarVenda")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvarDadosVenda(String dadosSalvarVenda) {
		try {
			VHP vhp = new Gson().fromJson(dadosSalvarVenda, VHP.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			System.out.println(vhp.getIdprod());
			System.out.println(vhp.getIdvenda());
			System.out.println(vhp.getQnt());
			
			JDBCVendasDAO jdbcVendas = new JDBCVendasDAO(conexao);
			
			boolean retorno = jdbcVendas.salvarDadosVenda(vhp);
			String msg = "";
			if (retorno) {
				msg= "Venda registra.";
			}else {
				msg= "Erro ao registar venda, entre em contato com o administrador.";
			}
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarLista")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarLista() {
		
		try {
			
			List<JsonObject> listaVHP = new ArrayList<JsonObject>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCVendasDAO jdbcVendas = new JDBCVendasDAO(conexao);
			listaVHP = jdbcVendas.bucarListaVHP();
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaVHP);
			//System.out.println(json);
			return this.buildResponse(json);
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
		
	}

}
