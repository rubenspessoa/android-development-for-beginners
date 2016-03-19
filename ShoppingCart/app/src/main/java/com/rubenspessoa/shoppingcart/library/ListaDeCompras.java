package com.rubenspessoa.shoppingcart.library;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Classe que cria e edita uma Lista de Compras
 * @author Arthur Felipe, Joao Paulo Ribeiro, Rubens Pessoa, Victor Souto
 *
 */
public class ListaDeCompras implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6696930340942160978L;
	
	private String nome;
	private LinkedHashMap<com.rubenspessoa.shoppingcart.library.Produto, Double> mapaDeProdutos = new LinkedHashMap<com.rubenspessoa.shoppingcart.library.Produto, Double>();
	private double valorDaListaDeProdutos;

	public ListaDeCompras (String nome) {
		this.nome = nome;
	}
	
	/**
	 * Adiciona um produto a lista de compras sem informarmos a quantidade. A quantidade padrao e 1.
	 * @param produto
	 */
	public void add(com.rubenspessoa.shoppingcart.library.Produto produto){
		mapaDeProdutos.put(produto, 1.0);
		updateValorDaLista();
	}
	
	/**
	 * Adiciona um produto a lista de compras informando a quantidade desejada como parametro.
	 * @param produto
	 * @param qtd
	 */
	public void add(com.rubenspessoa.shoppingcart.library.Produto produto, double qtd){
		mapaDeProdutos.put(produto, qtd);
		updateValorDaLista();
	}
	
	/**
	 * Remove um produto da lista de compras
	 * @param indice
	 */
	
	public void removerProduto(int indice){
		mapaDeProdutos.remove(indice);
		updateValorDaLista();
	}
	
	/**
	 * @return Retorna o valor total da lista de compras
	 */
	private void updateValorDaLista(){
		double valorTotal = 0;
		
		for (com.rubenspessoa.shoppingcart.library.Produto produto : mapaDeProdutos.keySet()) {
			valorTotal += produto.getValor() * mapaDeProdutos.get(produto); // valor total eh igual ao valor do produto vezes a quantidade de produtos na lista.
		}
		
		this.valorDaListaDeProdutos = valorTotal;
	}
	
	/**
	 * @return o preco total da lista de compras.
	 */
	public double getValorDaLista(){
		return valorDaListaDeProdutos;
	}

	/**
	 * @return o mapa de Produtos. Onde estao mapeados os Produtos e suas quantidades.
	 */
	public LinkedHashMap<com.rubenspessoa.shoppingcart.library.Produto, Double> getMapaDeProdutos() {
		return mapaDeProdutos;
	}
	
	/**
	 * @return o nome da Lista de Compras.
	 */
	public String getNome(){
		return this.nome;
	}
	
	/**
	 * @return Array de string com os nomes de todos os produtos na lista de compras
	 */
	public String[] getNomeProdutos(){
		
		String[] nomeProdutos = new String[mapaDeProdutos.size()];
		Object[] teste = mapaDeProdutos.keySet().toArray();
		com.rubenspessoa.shoppingcart.library.Produto[] produtos = new com.rubenspessoa.shoppingcart.library.Produto[mapaDeProdutos.keySet().size()];
				
		for(int i = 0; i < mapaDeProdutos.keySet().size(); i++){
			produtos[i] = (com.rubenspessoa.shoppingcart.library.Produto) teste[i];
		}
		
		for (int i = 0; i < nomeProdutos.length; i++) {
			nomeProdutos[i] = produtos[i].getNome();
		}
		
		return nomeProdutos ;
	}
	
	/**
	 * 
	 * @return um Array de double com todos os precos dos produtos da lista de compras.
	 */
	public double[] getValorProdutos(){
		
		double[] valorProdutos = new double[mapaDeProdutos.size()];
		Object[] teste = mapaDeProdutos.keySet().toArray();
		com.rubenspessoa.shoppingcart.library.Produto[] produtos = new com.rubenspessoa.shoppingcart.library.Produto[mapaDeProdutos.values().size()];
				
		for(int i = 0; i < mapaDeProdutos.values().size(); i++){
			produtos[i] = (com.rubenspessoa.shoppingcart.library.Produto) teste[i];
		}
				
		for (int i = 0; i < valorProdutos.length; i++) {
			valorProdutos[i] = produtos[i].getValor();
		}
		
		return valorProdutos;
	}
	
	/**
	 * @return um Array de Produto com todos os produtos presentes no mapaDeProdutos da Lista de Compras.
	 */
	public com.rubenspessoa.shoppingcart.library.Produto[] getProdutos(){
		
		Object[] teste = mapaDeProdutos.keySet().toArray();
		com.rubenspessoa.shoppingcart.library.Produto[] produtos = new com.rubenspessoa.shoppingcart.library.Produto[mapaDeProdutos.keySet().size()];
				
		for(int i = 0; i < mapaDeProdutos.keySet().size(); i++){
			produtos[i] = (com.rubenspessoa.shoppingcart.library.Produto) teste[i];
		}
		
		return produtos;
	}

	public void setNome(String string) {
		this.nome = string;
	}
	
}
