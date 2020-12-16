package br.com.lapadocca.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.lapadocca.modelo.Produto;

public interface ProdutoDAO {

	public boolean inserir(Produto produto);
	public List<JsonObject> buscarPorNome(String descricao);
	public boolean deletar(int idproduto);
	public Produto buscarPorId(int idproduto);
	public boolean alterar(Produto produto);

}
