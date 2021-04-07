package br.com.lapadocca.modelo;

import java.io.Serializable;

public class VHP implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idprod;
	private int idvenda;
	private int qnt;
	
	public int getIdprod() {
		return idprod;
	}
	public void setIdprod(int idprod) {
		this.idprod = idprod;
	}
	public int getIdvenda() {
		return idvenda;
	}
	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}
	public int getQnt() {
		return qnt;
	}
	public void setQnt(int qnt) {
		this.qnt = qnt;
	}
}
