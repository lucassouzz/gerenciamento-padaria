package br.com.lapadocca.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;

public interface VendasDAO {

	public List<JsonObject> buscar(String descricao);
	
}
