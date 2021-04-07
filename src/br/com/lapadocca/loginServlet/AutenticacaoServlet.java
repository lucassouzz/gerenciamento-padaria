package br.com.lapadocca.loginServlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;

import ArmazDadosUsuPacote.ArmazDadosUsu;
import br.com.lapadocca.bd.Conexao;
import br.com.lapadocca.jdbc.JDBCAutenticaDAO;

public class AutenticacaoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArmazDadosUsu ADU = new ArmazDadosUsu();

		ADU.setEmail(request.getParameter("txtemail"));
		String textDecode = new String(Base64.decodeBase64(request.getParameter("txtsenha")));
		String senmd5 = "";
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(request.getParameter("txtsenha").getBytes()));
		senmd5 = hash.toString(16);
		
		ADU.setSenha(senmd5);

		Conexao conec = new Conexao();
		Connection conexao = conec.abrirConexao();
		JDBCAutenticaDAO jdbcAutentica = new JDBCAutenticaDAO(conexao);
		Integer retorno = jdbcAutentica.validar(ADU);
		
		ADU.setNivelPermissao(retorno);
		
		if (retorno!=null) {
			HttpSession sessao = request.getSession();
			// System.out.println(sessao);
			sessao.setAttribute("login", request.getParameter("txtemail"));
			// System.out.println(sessao.getAttribute("login"));

			response.sendRedirect("pages/index.html");
		}else {
			response.sendRedirect("erro.html");
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);

	}
}
