package com.rubenspessoa.shoppingcart.library;

import java.util.Comparator;

public class ProdutoComparator implements Comparator<Produto> {

		@Override
		public int compare(Produto p1, Produto p2) {
			return (p1.getNome().compareToIgnoreCase(p2.getNome()));
		}
}

