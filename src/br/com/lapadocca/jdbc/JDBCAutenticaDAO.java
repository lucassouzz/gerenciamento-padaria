package br.com.lapadocca.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ArmazDadosUsuPacote.ArmazDadosUsu;


public class JDBCAutenticaDAO {


	private Connection conexao;

	public JDBCAutenticaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	public Integer validar(ArmazDadosUsu ADU) {

		String comando = "SELECT * FROM usuario WHERE email ='" + ADU.getEmail() + "' AND senha = '"
				+ ADU.getSenha() + "'";
		//System.out.println(comando);
		Integer nivelPermissao = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);	
			if (rs.next()) {
				nivelPermissao= rs.getInt("nivelPermissao");
				return nivelPermissao;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return nivelPermissao;
	}
}
