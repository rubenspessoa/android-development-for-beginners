package com.rubenspessoa.shoppingcart.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Gerencia a lista de todos os produtos cadastrados no aplicativo e as listas criadas pelo usuario.
 * @author Arthur Felipe, Joao Paulo Ribeiro, Rubens Pessoa, Victor Souto
 */

public class GerenciarListas implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8857382174325365137L;
	
	public ArrayList<com.rubenspessoa.shoppingcart.library.Produto> listaDeProdutos = new ArrayList<com.rubenspessoa.shoppingcart.library.Produto>();
	public ArrayList<com.rubenspessoa.shoppingcart.library.ListaDeCompras> listasDeCompras = new ArrayList<com.rubenspessoa.shoppingcart.library.ListaDeCompras>();

	public GerenciarListas(){
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade("Arroz Generico 1kg", "Supermercado Generico", 3.50));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade("Feijao Generico 1kg", "Supermercado Generico", 4.50));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade("Macarrao Generico 500g", "Supermercado Generico", 2.20));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade("Cafe Generico 500g", "Supermercado Generico", 4.00));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade("Manteiga Generica 300g", "Supermercado Generico", 2.00));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade("Acucar Generico 1kg", "Supermercado Generico", 1.50));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade("Refrigerante Coca-Cola 2L", "Supermercado Generico", 5.00));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmKg("Pao Frances Generico 1kg", "Supermercado Generico", 5.00));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmKg("Presunto Generico 1kg", "Supermercado Generico", 15.00));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmKg("Queijo Mussarela Generica 1kg", "Supermercado Generico", 22.00));
		listaDeProdutos.add(new com.rubenspessoa.shoppingcart.library.ProdutoEmKg("Carne Generica 1kg", "Supermercado Generico", 35.00));
	}
	
	/*
	 * PRODUTOS
	 */
	
	/**
	 * Adiciona um produto a lista de todos os produtos cadastrados.
	 * @param p produto
	 * @throws IllegalArgumentException O produto ja existe
	 */
	public void add(com.rubenspessoa.shoppingcart.library.Produto p) throws IllegalArgumentException {
		if (listaDeProdutos.size() > 0){
			for (int i = 0; i < listaDeProdutos.size(); i++) {
				if (listaDeProdutos.get(i).getNome().equals(p.getNome())){
					throw new IllegalArgumentException("O produto ja existe.");
				}
			}
		} 
		this.listaDeProdutos.add(p);
		
	}
	
	/**
	 * Deleta um produto da lista de todos os produtos cadastrados.
	 * @param index posicao do produto na lista de produtos.
	 */
	public void deleteProduto(int index){
		this.listaDeProdutos.remove(index);
	}
	/**
	 * 
	 * @return Os nomes de todos os produtos cadastrados.
	 */
	public String[] nomesProdutos(){
	
	Collections.sort(listaDeProdutos, new com.rubenspessoa.shoppingcart.library.ProdutoComparator());
	
    	String[] nomes = new String[listaDeProdutos.size()];
    	
    		for (int i = 0;i<listaDeProdutos.size();i++ ){
    			nomes[i] = listaDeProdutos.get(i).getNome();
    		}
    	
    	return nomes;
    }
	
	public String[] nomesProdutosInvertida(){
		
	Collections.sort(listaDeProdutos, new com.rubenspessoa.shoppingcart.library.ProdutoComparator());
	Collections.reverse(listaDeProdutos);
		
    	String[] nomes = new String[listaDeProdutos.size()];
    	
    		for (int i = 0;i<listaDeProdutos.size();i++ ){
    			nomes[i] = listaDeProdutos.get(i).getNome();
    		}
    	
    	return nomes;
    }
	
	public ArrayList<com.rubenspessoa.shoppingcart.library.Produto> getListaDeProdutos() {
		return listaDeProdutos;
	}


	/*
	 * LISTAS DE COMPRAS
	 */
	
	/**
	 * Adiciona um lista de compras a lista de todos as listas de compras.
	 * @param lista de compras
	 * @throws IllegalArgumentException Uma lista com este mesmo nome ja existe.
	 */
	
	public void add(com.rubenspessoa.shoppingcart.library.ListaDeCompras lista) throws IllegalArgumentException {
		if (listasDeCompras.size() > 0){
			for (int i = 0; i < listasDeCompras.size(); i++) {
				if (listasDeCompras.get(i).getNome().equals(lista.getNome())){
					throw new IllegalArgumentException("Uma lista com este mesmo nome ja existe.");
				}
			}
		}
		this.listasDeCompras.add(lista);

		
	}
	
	/**
	 * Deleta uma lista da lista de todas as listas de compras.
	 * @param index posicao da lista na lista de todas as listas de compras.
	 */
	
	public void deleteLista(int index){
		this.listasDeCompras.remove(index);
	}
	
	/**
	 * Retorna um Array de Strings com o nome de todas as Listas de Compras.
	 * @return nome de todas as listas de compras.
	 */
	
	public String[] nomesDasListas(){
	
	String[] nomes = new String[listasDeCompras.size()];
    	
		for (int i = 0;i < listasDeCompras.size();i++ ) {
    			nomes[i] = listasDeCompras.get(i).getNome();
    		}
    	
    	return nomes;
	}

	/**
	 * Retorna um ArrayList com todas as listas de compras.
	 * 
	 * @return ArrayList<ListaDeCompras>
	 */
	
	public ArrayList<com.rubenspessoa.shoppingcart.library.ListaDeCompras> getListasDeCompras() {
		return listasDeCompras;
	}
	
	/**
	 * Sugere uma Lista de Compras com base nos produtos com mais Eventos de Preco.
	 * @param nomeDaLista
	 * @return
	 */
	public com.rubenspessoa.shoppingcart.library.ListaDeCompras sugereListaDeCompras(String nomeDaLista) {
		
		com.rubenspessoa.shoppingcart.library.ListaDeCompras sugerida = new com.rubenspessoa.shoppingcart.library.ListaDeCompras(nomeDaLista);
		
		if (listasDeCompras.size() == 0) {
			for (com.rubenspessoa.shoppingcart.library.Produto produto : listaDeProdutos) {
				sugerida.add(produto);
			}
		} else {
			ArrayList<com.rubenspessoa.shoppingcart.library.Produto> copyListaDeProdutos = listaDeProdutos;
			Collections.sort(copyListaDeProdutos, new com.rubenspessoa.shoppingcart.library.ProdutoMaisCompradoComparator());
			Collections.reverse(copyListaDeProdutos);
			
			int maiorNumeroDeCompras = maiorNumeroDeCompras();
				
			for (com.rubenspessoa.shoppingcart.library.Produto produto : listaDeProdutos) {
				if (produto.quantasVezesFoiComprado() >= maiorNumeroDeCompras/2) {
					sugerida.add(produto);
				}
			}
		}
		
		return sugerida;
		
	}
	
	private int maiorNumeroDeCompras(){
		int maiorNumeroDeCompras = 0;
		
		for (com.rubenspessoa.shoppingcart.library.Produto produto : listaDeProdutos) {
			if (produto.quantasVezesFoiComprado() > maiorNumeroDeCompras) {
				maiorNumeroDeCompras = produto.quantasVezesFoiComprado();
			}
		}
		
		return maiorNumeroDeCompras;
	}
}
