package br.com.lapadocca.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.lapadocca.jdbcinterface.UsuarioDAO;
import br.com.lapadocca.modelo.Usuario;

public class JDBCUsuarioDAO implements UsuarioDAO {

	private Connection conexao;

	public JDBCUsuarioDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public boolean inserir(Usuario usuario) {

		String comando = "INSERT INTO usuario (email, senha) VALUES (?,?)";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);

			p.setString(1, usuario.getEmail());
			p.setString(2, usuario.getSenha());

			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<JsonObject> buscar(String email) {// email passado como parametro

		String comando = "SELECT * FROM usuario ";

		if (!email.equals("")) {
			// concatena no comando o WHERE buscando no nome do produto o texto da variavel
			// descricao
			comando += "WHERE usuario.email LIKE '%" + email + "%' ";
		}
		// finaliza o comando ordenando alfabeticamente por descricao/nome
		comando += "ORDER BY usuario.email";

		List<JsonObject> listaUsuarios = new ArrayList<JsonObject>();
		JsonObject usuario = null;

		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);

			while (rs.next()) {

				int idusuario = rs.getInt("idusuario");
				email = rs.getString("email");
				String senha = rs.getString("senha");

				usuario = new JsonObject();
				usuario.addProperty("idusuario", idusuario);
				usuario.addProperty("email", email);
				usuario.addProperty("senha", senha);

				listaUsuarios.add(usuario);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaUsuarios;
	}

	public boolean deletar(int idusuario) {
		String comando = "DELETE FROM usuario WHERE idusuario =?";
		PreparedStatement p;

		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, idusuario);
			p.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Usuario buscarPorId(int idusuario) {
		String comando = "SELECT * FROM usuario WHERE usuario.idusuario =?";
		Usuario usuario = new Usuario();

		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, idusuario);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {

				String email = rs.getString("email");
				String senha = rs.getString("senha");

				usuario.setIdusuario(idusuario);
				usuario.setEmail(email);
				usuario.setSenha(senha);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usuario;
	}

	public boolean alterar(Usuario usuario) {

		String comando = "UPDATE usuario SET email=?, senha=? WHERE idusuario=?";
		PreparedStatement p;
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1, usuario.getEmail());
			p.setString(2, usuario.getSenha());
			p.setInt(3, usuario.getIdusuario());
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}// fim
