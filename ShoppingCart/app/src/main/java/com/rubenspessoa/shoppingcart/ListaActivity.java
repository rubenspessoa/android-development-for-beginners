package com.rubenspessoa.shoppingcart;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rubenspessoa.shoppingcart.library.ListaDeCompras;
import com.rubenspessoa.shoppingcart.library.Produto;
import com.rubenspessoa.shoppingcart.library.ProdutoEmKg;
import com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade;

import java.io.IOException;

/**
 * (Activity)Lista de compras sendo utilizada no momento
 * @author Arthur Felipe, Joao Paulo Ribeiro, Rubens Pessoa, Victor Souto
 *
 */
public class ListaActivity extends Activity {
	//Nomes dos produtos que comp�em a lista.
	Documento doc = Documento.getInstance(this);
	ListaDeCompras listaCompra;
	
	String nomeLista;
	int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista);
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		try {
		
		Intent intent = getIntent();
		nomeLista = intent.getExtras().getString("nome");
		
		
		for (int i = 0; i < MainActivity.gerencia.getListasDeCompras().size(); i++) {
			
			if (MainActivity.gerencia.getListasDeCompras().get(i).getNome().equals(nomeLista)){
				listaCompra = MainActivity.gerencia.getListasDeCompras().get(i);
				if (listaCompra.getNomeProdutos().length == 0){
					addProdutos();
				}
			}
			
		}
		
		setTitle(nomeLista);
		
		
		ListView lista = (ListView) findViewById(R.id.listProdutos);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1){
			String nomeProduto;
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// Recupera o produto selecionado de acordo com a sua posi��o no ListView
				nomeProduto = listaCompra.getNomeProdutos()[position];
				Produto p = null;
				for (int i = 0; i < listaCompra.getMapaDeProdutos().size(); i++) {
					if (listaCompra.getNomeProdutos()[i].equals(nomeProduto)){
						p = listaCompra.getProdutos()[i];
					}	
				}
				
		//		String valorProduto = String.format("%.2f",listaCompra.getValorProdutos()[position]);
				// Se o ConvertView for diferente de null o layout ja foi "inflado"
				View v = convertView;
					if(v==null) {
					// "Inflando" o layout do item caso o memso ainda nao tenha sido feito
					LayoutInflater inflater = getLayoutInflater();
					v = (View) inflater.inflate(R.layout.item_produto, null);
				}
				
				// Recuperando o checkbox
	            CheckBox chc = (CheckBox) v.findViewById(R.id.chcproduto);
	 
	            // Definindo um "valor" para o checkbox
	            chc.setTag(nomeProduto);
	 
	            /*
	             *  Definindo uma a��o ao clicar no checkbox. Aqui poderiamos inserior o metodo para alterar
	             * o valor do produto.
	             */
	            chc.setOnClickListener(new View.OnClickListener() {
	            	
		            @Override
		            public void onClick(View v) {
		                CheckBox chk = (CheckBox) v;
		                String produto = (String) chk.getTag();
		                if(chk.isChecked()) {
		                	Toast.makeText(getApplicationContext(), produto + " coletado!", Toast.LENGTH_SHORT).show();
		                } else {
		                    Toast.makeText(getApplicationContext(), produto + " removido!", Toast.LENGTH_SHORT).show();
		                    
		                }
		            }
	            });
	            
	            
	            TextView txt = (TextView) v.findViewById(R.id.txtproduto);
	            if (p.getClass() == ProdutoEmKg.class){
	            	txt.setText(nomeProduto + " - " + String.format("%.3f", listaCompra.getMapaDeProdutos().get(p)) + " kgs");
	    		} else if (p.getClass() == ProdutoEmUnidade.class){
	    			txt.setText(nomeProduto + " - " + String.format("%.0f", listaCompra.getMapaDeProdutos().get(p)) + " und.");
	    		}
	            //txt.setText(nomeProduto + " - " + listaCompra.getMapaDeProdutos().get(p));
	            txt.setTag(nomeProduto);
	            TextView txt2 = (TextView) v.findViewById(R.id.txtpreco);
	            txt2.setText("R$ " +String.format("%.2f",p.calculaValor(listaCompra.getMapaDeProdutos().get(p))));
	            txt.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						TextView tx =(TextView) v;
						String nome = (String) tx.getTag();
						openContextMenu(v);
						Toast.makeText(getApplicationContext(), nome + " selecionado", Toast.LENGTH_SHORT).show();
					}
				});
	            
	            return v;
            }
 
            @Override
            public long getItemId(int position) {
                return position;
            }
 
            @Override
            public int getCount() {
                return listaCompra.getNomeProdutos().length;
            }
        };// Fim ArrayAdapter
        
        lista.setAdapter(adapter);
        registerForContextMenu(lista);
        Button finalizar = (Button) findViewById(R.id.btn_Finalizar);
        finalizar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {	
					doc.salvarConjunto(MainActivity.gerencia);
					Toast.makeText(getApplicationContext(), "Compra finalizada!", Toast.LENGTH_SHORT).show();
					onBackPressed();
				} catch (IOException e) {
					Log.d("Erro", e.getMessage());
				}
			}
		});
        
		} catch(Exception e){
			
		}
	}
	/**
	 * Atualiza o preco do Produto selecionado
	 * @param nome do produto
	 */
	private void dialogAtualizar(String nome) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_atualizar_preco);
		dialog.setTitle("Atualizar pre�o");
		
		for (int i = 0; i < MainActivity.gerencia.nomesProdutos().length; i++) {
			if (MainActivity.gerencia.nomesProdutos()[i].equals(nome)){
				index = i;
			}	
		}
		
		Button btn = (Button) dialog.findViewById(R.id.btn_Confirmar);
		btn.setOnClickListener(new OnClickListener() {
			
			EditText preco = (EditText) dialog.findViewById(R.id.preco);
			EditText local = (EditText) dialog.findViewById(R.id.localDeVenda);
			
			@Override
			public void onClick(View arg0) {
				try {
					double precoAtual = Double.parseDouble(preco.getText().toString());
					String estabelecimento = local.getText().toString();
					double quantidade = listaCompra.getMapaDeProdutos().get(MainActivity.gerencia.getListaDeProdutos().get(index));
					MainActivity.gerencia.getListaDeProdutos().get(index).addEventoDePreco(quantidade,precoAtual, estabelecimento);
					try {	
						doc.salvarConjunto(MainActivity.gerencia);
						Toast.makeText(getApplicationContext(), "Produto atualizado!", Toast.LENGTH_SHORT).show();
						setContentView(R.layout.activity_lista);
						onStart();
					} catch (IOException e) {
						Log.d("Erro", e.getMessage());
					}
					
					dialog.dismiss();
				} catch(IllegalArgumentException e){
					AlertDialog.Builder dialogo = new AlertDialog.Builder(ListaActivity.this);
					dialogo.setTitle("Ops!");
					dialogo.setMessage("Necessario entrar com as informa��es");
					dialogo.setNeutralButton("OK", null);
					dialogo.show();
				}
			}
		});
		
		final Button cancelar = (Button) dialog.findViewById(R.id.btn_Cancelar);
		cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//finaliza o dialog
				dialog.dismiss();
			}
		});
		//exibe o dialog	
		dialog.show();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
	      	case R.id.excluirLista:
	      		for (int i = 0; i < MainActivity.gerencia.getListasDeCompras().size(); i++) {
	    			if (MainActivity.gerencia.getListasDeCompras().get(i).getNome().equals(nomeLista)){
	    				MainActivity.gerencia.deleteLista(i);
	    				try {
							doc.salvarConjunto(MainActivity.gerencia);
							Toast.makeText(getApplicationContext(), "Lista excluida", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(ListaActivity.this, MainActivity.class);
		    				startActivity(intent);
						} catch (IOException e) {
							Log.d("Erro", e.getMessage());
						}
	    				
	    			}
	    			
	    		}
	      	case R.id.addNaLista:
	      		addProdutos();
	      		break;
		} 
	    	
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.context_menu_lista, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		int itemPosition = contextMenuInfo.position;
		
		
		switch (item.getItemId()) {
		case R.id.remover_produto:
			if (listaCompra.getMapaDeProdutos().size() == 1){
				Toast.makeText(getApplicationContext(), "Unico produto desta lista, voce pode adicionar outros ou excluir a lista.", Toast.LENGTH_LONG).show();
			} else {
				for (int i = 0; i < MainActivity.gerencia.listaDeProdutos.size(); i++) {
					if (MainActivity.gerencia.listaDeProdutos.get(i).getNome().equals(listaCompra.getNomeProdutos()[itemPosition])){
						listaCompra.getMapaDeProdutos().remove(MainActivity.gerencia.listaDeProdutos.get(i));
						try {
							Toast.makeText(getApplicationContext(), "removido!", Toast.LENGTH_SHORT).show();
							doc.salvarConjunto(MainActivity.gerencia);
						} catch (IOException e) {
							Log.d("Erro", e.getMessage());
						}
						onStart();
						break;
						
					}
				}
				
			}
			break;
		case R.id.atualizar_preco:
			String nome = listaCompra.getNomeProdutos()[itemPosition];
			dialogAtualizar(nome);
		}
		
		return super.onContextItemSelected(item);
	}
	
	/*
	 *  Adiciona Produto
	 */
	
	public void addProdutos(){
		setContentView(R.layout.selecionar_produto);
		ListView list = (ListView) findViewById(R.id.listItens);
		setTitle("Adicionar itens a lista");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1){
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// Recupera o produto selecionado de acordo com a sua posi��o no ListView
				final String nomeProduto = MainActivity.gerencia.nomesProdutos()[position];
				// Se o ConvertView for diferente de null o layout ja foi "inflado"
				View v = convertView;
					if(v==null) {
					// "Inflando" o layout do item caso o memso ainda nao tenha sido feito
					LayoutInflater inflater = getLayoutInflater();
					v = (View) inflater.inflate(R.layout.produto_add, null);
				}
				
				Button chc = (Button) v.findViewById(R.id.btAdd);
				chc.setTag(nomeProduto);
				
	 
	            /*
	             *  Definindo uma a��o ao clicar no checkbox. Aqui poderiamos inserior o metodo para alterar
	             * o valor do produto.
	             */
	            chc.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	
		               	for (int i = 0;i < MainActivity.gerencia.getListaDeProdutos().size();i++){
		               		Button chk = (Button) v;
			                String produto = (String) chk.getTag();
		                	Produto pAdicionado = MainActivity.gerencia.getListaDeProdutos().get(i);
		                	
		                	if(pAdicionado.getNome().equals(produto)){
			                	addQuantidade(nomeProduto);
			                }
		                
		                	
		                }
		            }
	            });
	                
	            TextView txt = (TextView) v.findViewById(R.id.txtproduto);
	            txt.setText(nomeProduto);
	            TextView txt2 = (TextView) v.findViewById(R.id.txtpreco);
	            txt2.setText(" ");
	            return v;
			}
			 @Override
	            public long getItemId(int position) {
	                return position;
	            }
	 
	            @Override
	            public int getCount() {
	                return MainActivity.gerencia.getListaDeProdutos().size();
	            }
		};
		
		list.setAdapter(adapter);
	}
	/*
	 * Dialogo de quantidade de produtos que devem ser adicionados ao carrinho
	 */
	Produto p;
	public void addQuantidade(String nome){
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_quantidade);
		dialog.setTitle("Determinar quantidade de " + nome);
		
		for (int i = 0; i < MainActivity.gerencia.nomesProdutos().length; i++) {
			if (MainActivity.gerencia.nomesProdutos()[i].equals(nome)){
				p = MainActivity.gerencia.getListaDeProdutos().get(i);
			}	
		}
		
		TextView txt = (TextView) dialog.findViewById(R.id.txtmedida);
		if (p.getClass() == ProdutoEmKg.class){
			txt.setText("(Kg)");
		} else if (p.getClass() == ProdutoEmUnidade.class){
			txt.setText("(Und)");
		}
		
		
		Button confirmar = (Button) dialog.findViewById(R.id.confirmar);
		confirmar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
	    		EditText quantidade = (EditText) dialog.findViewById(R.id.quantidade);
	    		double quant = Double.parseDouble(quantidade.getText().toString());
	    		Toast.makeText(getApplicationContext(), p.getNome() +" adicionado", Toast.LENGTH_SHORT).show();
	    		listaCompra.add(p,quant);
	    		
	    		} catch(Exception e){
	    			Log.e("Erro", "Linha 273");
	    		}
	    		
	    		try {	
					doc.salvarConjunto(MainActivity.gerencia);
					Toast.makeText(getApplicationContext(), "Lista atualizada!", Toast.LENGTH_SHORT).show();
					setContentView(R.layout.activity_lista);
					onStart();
					dialog.dismiss();
				} catch (IOException e) {
					Log.d("Erro", e.getMessage());
				}
			}
		});
		dialog.show();
		
	}
}
