package com.rubenspessoa.shoppingcart.library;

import java.util.Comparator;

public class ProdutoMaisCompradoComparator implements Comparator<Produto> {

	@Override
	public int compare(Produto lhs, Produto rhs) {
		return (lhs.compareTo(rhs));
	}


}