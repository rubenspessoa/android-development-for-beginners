package com.rubenspessoa.shoppingcart;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rubenspessoa.shoppingcart.library.Produto;
import com.rubenspessoa.shoppingcart.library.ProdutoEmKg;
import com.rubenspessoa.shoppingcart.library.ProdutoEmUnidade;

import java.io.IOException;
/**
 * (Activity)Tela de cadastro de novos produtos.
 * @author Arthur Felipe, Joao Paulo Ribeiro, Rubens Pessoa, Victor Souto
 *
 */
public class CadastrarProduto extends Activity {
	EditText nome, preco,local,chave;
	Spinner tipo;
	Documento doc = Documento.getInstance(this);
	private  static final String[] tipos = { "Kg" ,"Und."};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_produto);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		tipo = (Spinner) findViewById(R.id.spinnerTipo);
		
		ArrayAdapter<String> medidas = new ArrayAdapter<String>( this ,android.R.layout.simple_spinner_dropdown_item , tipos);
		tipo.setAdapter(medidas);
		Button confirmar = (Button) findViewById(R.id.btConfirmar);
		confirmar.setOnClickListener(new OnClickListener() {
			
			
			Produto produto;
			@Override
			public void onClick(View v) {
				try {
					nome = (EditText) findViewById(R.id.edtxNome);
					String nomeProduto = nome.getText().toString();
					preco = (EditText) findViewById(R.id.edtxPreco);
					double precoProduto = Double.parseDouble(preco.getText().toString());
					local = (EditText) findViewById(R.id.edtxLocal);
					String localVenda = local.getText().toString();
					chave = (EditText) findViewById(R.id.edtxChave);
					switch(tipo.getSelectedItemPosition()){
						case 0:
							produto = new ProdutoEmKg(nomeProduto, localVenda, precoProduto);
							produto.addPalavrasChave(chave.getText().toString());
							break;
						case 1:
							produto = new ProdutoEmUnidade(nomeProduto,localVenda,precoProduto);
							produto.addPalavrasChave(chave.getText().toString());
							break;
					}
					
					
					MainActivity.gerencia.add(produto);
					
					try {
						doc.salvarConjunto(MainActivity.gerencia);
						Toast.makeText(getApplicationContext(), produto.getNome() + " adicionado!", Toast.LENGTH_SHORT).show();
					} catch (IOException e) {
						Log.d("Erro", e.getMessage());
					}
					
					Intent intent = new Intent(CadastrarProduto.this, MainActivity.class);
					startActivity(intent);
					CadastrarProduto.this.finish();
				
				} catch(NumberFormatException e){
					AlertDialog.Builder dialogo = new AlertDialog.Builder(CadastrarProduto.this);
					dialogo.setTitle("Ops!");
					dialogo.setMessage("� necessario todas informa��es.");
					dialogo.setNeutralButton("OK", null);
					dialogo.show();
				} catch(IllegalArgumentException e2){
					AlertDialog.Builder dialogo = new AlertDialog.Builder(CadastrarProduto.this);
					dialogo.setTitle("Ops!");
					dialogo.setMessage("J� existe um produto com este nome.");
					dialogo.setNeutralButton("OK", null);
					dialogo.show();
				}
				
			}
		});
		
	}

}
