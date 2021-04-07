package br.com.lapadocca.modelo;

import java.io.Serializable;

public class Vendas implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int idvenda;
	private String data;
	private int idusuario;
	
	public int getIdvenda() {
		return idvenda;
	}
	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}
	
	
}
