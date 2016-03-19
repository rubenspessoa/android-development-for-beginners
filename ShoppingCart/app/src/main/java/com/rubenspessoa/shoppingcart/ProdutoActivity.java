package com.rubenspessoa.shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.rubenspessoa.shoppingcart.library.Produto;

/**
 * (Activity) Tela que mostra a situacao atual de um produto
 *
 * @author Arthur Felipe, Joao Paulo Ribeiro, Rubens Pessoa, Victor Souto
 */
public class ProdutoActivity extends Activity {
    TextView preco, local, data, melhorPreco, melhorLocal, melhorData, palavrasChave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Produto p = MainActivity.gerencia.getListaDeProdutos().get(intent.getExtras().getInt("index"));
        setTitle(p.getNome());
        preco = (TextView) findViewById(R.id.preco);
        preco.setText(String.format("%.2f", p.getValor()));
        local = (TextView) findViewById(R.id.local);
        local.setText(p.getEstabelecimento());
        data = (TextView) findViewById(R.id.data);
        data.setText(p.getEventosDePreco().getLast().getData().toLocaleString());
        melhorPreco = (TextView) findViewById(R.id.melhorPreco);
        melhorPreco.setText(String.format("%.2f", p.melhorEventoDePreco().getValorPago()));
        melhorLocal = (TextView) findViewById(R.id.melhorLocal);
        melhorLocal.setText(p.melhorEventoDePreco().getEstabelecimento());
        melhorData = (TextView) findViewById(R.id.melhorData);
        melhorData.setText(p.melhorEventoDePreco().getData().toLocaleString());
        palavrasChave = (TextView) findViewById(R.id.palavrasChave);

        String palavras = "";
        for (int i = 0; i < p.getPalavrasChave().size(); i++) {
            if (p.getPalavrasChave().size() - 1 == i) {
                palavras += p.getPalavrasChave().get(i);
            } else {
                palavras += p.getPalavrasChave().get(i) + ", ";
            }
        }
        palavrasChave.setText(palavras);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.produto, menu);
        return true;
    }

}
