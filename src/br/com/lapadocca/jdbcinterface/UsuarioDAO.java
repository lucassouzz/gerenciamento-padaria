package br.com.lapadocca.jdbcinterface;

import java.util.List;

import com.google.gson.JsonObject;
import br.com.lapadocca.modelo.Usuario;

public interface UsuarioDAO {

	public boolean inserir(Usuario usuario);

	public List<JsonObject> buscar(String email);

	public Usuario buscarPorId(int idusuario);

	public boolean alterar(Usuario usuario);

}
