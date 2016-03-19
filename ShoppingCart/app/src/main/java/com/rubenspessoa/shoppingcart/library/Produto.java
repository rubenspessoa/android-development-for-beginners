package com.rubenspessoa.shoppingcart.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe que cria e edita um produto.
 * @author Arthur Felipe, Joao Paulo Ribeiro, Rubens Pessoa, Victor Souto
 *
 */
public abstract class Produto implements Serializable, Calculavel, Comparable<Produto> {
    
        /**
         * 
         */
        private static final long serialVersionUID = -2017858648084823895L;
        private String nome, estabelecimento;
		private double valor;
        private LinkedList<EventoDePreco> eventosDePreco = new LinkedList<EventoDePreco>();
        private ArrayList<String> palavrasChave = new ArrayList<String>();
 
        public Produto (String nome, String estabelecimento, double valor) {
                this.nome = nome;
                this.estabelecimento = estabelecimento;
                this.valor = valor;
                String[] criaPalavrasChave = nome.split(" ");
                for ( int i = 0; i < criaPalavrasChave.length; i++) {
                		if (!criaPalavrasChave.equals(""))
                			palavrasChave.add(criaPalavrasChave[i]);
                }
        }
        
        /**
         * Adiciona um evento de preco ao produto
         * @param valor do produto
         * @param estabelecimento local onde foi encontrado
         */
        
		public abstract void addEventoDePreco(double quantidade, double valor, String estabelecimento);
		
		/**
		 * Adiciona palavras-chave ao Produto.
		 * O formato de entrada e uma String com todas as palavras separadas por virgulas. Internamente, o metodo os separada e adiciona ao Array de Palavras Chave.
		 * @param palavra
		 */
		public void addPalavrasChave(String palavra){
			String[] teste = palavra.trim().split(",");
			for (int i = 0; i < teste.length; i++) {
				addPalavraChave(teste[i]);
			}
		}
		
		private void addPalavraChave(String palavra) {
			palavrasChave.add(palavra);
		}
		
		/**
		 * @return ArrayList com todas as Palavras-chave.
		 */
		public ArrayList<String> getPalavrasChave(){
			return palavrasChave;
		}
		
		/**
		 * 
		 * @return Nome do Produto.
		 */
		public String getNome() {
			return nome;
		}
		
		/**
		 * 
		 * @return Estabelecimento em que o produto foi comprado pela ultima vez.
		 */
        public String getEstabelecimento() {
			return estabelecimento;
		}
		
        /**
         * 
         * @return o ultimo valor pago pela unidade do produto.
         */
        public double getValor() {
        		return valor;
        }
        
        /**
         * 
         * @return um LinkedList com todos os eventos de preco pertinentes ao produto.
         */
        public LinkedList<EventoDePreco> getEventosDePreco() {
			return eventosDePreco;
		}
        
        /**
         * Atualiza o estabelecimento para o ultimo comprado.
         * @param estabelecimento
         */
        public void setEstabelecimento(String estabelecimento) {
			this.estabelecimento = estabelecimento;
		}

        /**
         * Atualiza o valor para o ultimo pago pela unidade do produto.
         * @param valor
         */
		public void setValor(double valor) {
			this.valor = valor;
		}
		
		/**
		 * 
		 * @return quantidade de vezes em que o produto foi comprado.
		 */

		public int quantasVezesFoiComprado(){
        		return eventosDePreco.size();
        }
		
		/**
		 * 
		 * @return o melhor evento de preco do produto. Criterio: Menor valor pago.
		 */
		public EventoDePreco melhorEventoDePreco(){
			EventoDePreco melhorEvento = eventosDePreco.getFirst();
			double melhorPreco = eventosDePreco.getFirst().getValorPago();
			for (EventoDePreco evento : eventosDePreco) {
				if (evento.getValorPago() < melhorPreco){
					melhorEvento = evento;
					melhorPreco = evento.getValorPago();
				}
			}
			
			return melhorEvento;
		}

		public void setNome(String string) {
			this.nome = string;
		}
}