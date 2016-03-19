package com.rubenspessoa.shoppingcart;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;

import java.io.IOException;

/**
 * Tela inicial, visualiza as listas de listas de compras criadas pelo usuario.
 *
 * @author Arthur Felipe, Joao Paulo Ribeiro, Rubens Pessoa, Victor Souto
 */

public class MainActivity extends Activity {
    com.rubenspessoa.shoppingcart.Documento doc = com.rubenspessoa.shoppingcart.Documento.getInstance(this);
    static com.rubenspessoa.shoppingcart.library.GerenciarListas gerencia;
    String FILENAME = "conjunto.txt";
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onStart() {
        super.onStart();


        try {
            gerencia = doc.carregarDocumento();
        } catch (Exception e) {
            //Log.d("OPs", e.getMessage());
        }
        if (gerencia == null) {
            gerencia = new com.rubenspessoa.shoppingcart.library.GerenciarListas();
        }

        String[] nomesDasListas = gerencia.nomesDasListas();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomesDasListas);

        lista = (ListView) findViewById(R.id.listListasDeCompras);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent(MainActivity.this, com.rubenspessoa.shoppingcart.ListaActivity.class);

                for (int i = 0; i < gerencia.getListasDeCompras().size(); i++) {
                    String str = lista.getItemAtPosition(arg2).toString();
                    String comparador = gerencia.getListasDeCompras().get(i).getNome();

                    if (str.equals(comparador)) {
                        intent.putExtra("nome", gerencia.getListasDeCompras().get(i).getNome());
                        startActivity(intent);
                    }
                }

            }

        });

        Button adicionar = (Button) findViewById(R.id.buttonAdicionar);
        adicionar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ExibeDialog();

            }
        });

    }

    private void ExibeDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_lista);

        dialog.setTitle("Criar nova lista");


        final Button confirmar = (Button) dialog.findViewById(R.id.btn_Confirmar);
        confirmar.setOnClickListener(new View.OnClickListener() {
            EditText nomeLista;

            @Override
            public void onClick(View arg0) {
                try {
                    nomeLista = (EditText) dialog.findViewById(R.id.nomeLista);
                    gerencia.add(new com.rubenspessoa.shoppingcart.library.ListaDeCompras(nomeLista.getText().toString()));

                    try {
                        doc.salvarConjunto(MainActivity.gerencia);
                        Toast.makeText(getApplicationContext(), nomeLista.getText().toString() + " adicionado!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.d("Erro", e.getMessage());
                    }

                    onStart();
                    dialog.dismiss();
                } catch (IllegalArgumentException e) {
                    AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                    dialogo.setTitle("Ops!");
                    dialogo.setMessage("Lista j� existente.");
                    dialogo.setNeutralButton("OK", null);
                    dialogo.show();
                }

            }
        });

        final Button cancelar = (Button) dialog.findViewById(R.id.btn_Cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adicionar_produto:
                Intent intent = new Intent(MainActivity.this, com.rubenspessoa.shoppingcart.CadastrarProduto.class);
                startActivity(intent);
                break;
            case R.id.lista_de_produto:
                Intent intent2 = new Intent(MainActivity.this, com.rubenspessoa.shoppingcart.ListaDeProdutos.class);
                startActivity(intent2);
                break;
            case R.id.sugerir_lista:
                Intent intent3 = new Intent(MainActivity.this, com.rubenspessoa.shoppingcart.SugestaoLista.class);
                startActivity(intent3);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
