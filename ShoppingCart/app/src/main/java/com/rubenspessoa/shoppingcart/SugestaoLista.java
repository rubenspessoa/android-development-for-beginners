package com.rubenspessoa.shoppingcart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rubenspessoa.shoppingcart.library.ListaDeCompras;

import java.io.IOException;

public class SugestaoLista extends Activity {
    ListaDeCompras listaSugerida;
    EditText nome;
    Documento doc = Documento.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugestao_lista);
    }

    @Override
    protected void onStart() {
        try {

            ListView lista = (ListView) findViewById(R.id.lista);
            Button aceitar = (Button) findViewById(R.id.btn_Confirmar);
            nome = (EditText) findViewById(R.id.nomeLista);
            listaSugerida = MainActivity.gerencia.sugereListaDeCompras("lista sugerida");
            //listaSugerida = MainActivity.gerencia.getListaSugeridaDeProdutos("nome", 8);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaSugerida.getNomeProdutos());
            lista.setAdapter(adapter);
            aceitar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    listaSugerida.setNome(nome.getText().toString());
                    MainActivity.gerencia.add(listaSugerida);
                    try {
                        doc.salvarConjunto(MainActivity.gerencia);
                        Toast.makeText(getApplicationContext(), nome.getText().toString() + " adicionada!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.d("Erro", e.getMessage());
                    }

                    onBackPressed();
                }
            });

            Button cancelar = (Button) findViewById(R.id.btn_Cancelar);
            cancelar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } catch (Exception e) {

        }
        super.onStart();
    }

}
