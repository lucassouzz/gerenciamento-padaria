package br.com.lapadocca.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

import br.com.lapadocca.modelo.Categoria;

public interface CategoriaDAO {

	public List<Categoria> buscar();
	public List<JsonObject> buscarPorDescricao(String descricao);
	public boolean deletar(int idcategoria);
	public Categoria buscarPorId(int idcategoria);
	public boolean alterar(Categoria categoria);
}
