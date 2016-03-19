package com.rubenspessoa.shoppingcart.library;

import java.io.Serializable;

public class ProdutoEmKg extends Produto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1094665976608781148L;
	
	private double quantidadeComprada;
	
	public ProdutoEmKg(String nome, String estabelecimento, double preco) {
		super(nome, estabelecimento, preco);
		this.quantidadeComprada = 1;
		addEventoDePreco(quantidadeComprada, preco, estabelecimento);
	}

	@Override
	public double calculaValor(double pesoDesejado) {
		return pesoDesejado * super.getValor();
	}

	@Override
	public double calculaValorDaUnidadeDeMedida(double quantidade, double preco) {
		return preco/quantidade;
	}

	@Override
	public void addEventoDePreco(double quantidade, double valor, String estabelecimento) {
		this.quantidadeComprada = quantidade;
		super.setEstabelecimento(estabelecimento);
		super.setValor(calculaValorDaUnidadeDeMedida(quantidade, valor));
		super.getEventosDePreco().add(new EventoDePreco(calculaValorDaUnidadeDeMedida(quantidade, valor), estabelecimento));
	}

	@Override
	public int compareTo(Produto another) {
		if (this.quantasVezesFoiComprado() > another.quantasVezesFoiComprado()) {
			return 1;
		} else if (this.quantasVezesFoiComprado() == another.quantasVezesFoiComprado()) {
			return 0;
		} else {
			return -1;
		}
	}

}
