package br.edu.utfpr;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DependentesCadastradosActivity extends AppCompatActivity {

    private ListView viewDependentes;
    Context context;
    ArrayList<Dependente> listaDependente;
    CustomAdapter customAdapter;
    Dependente dependente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependentes_cadastrados);


        context=this;
        viewDependentes = findViewById(R.id.listViewDependentes);
        listaDependente = new ArrayList<Dependente>();
        popularListaDependente();

        customAdapter = new CustomAdapter(context,listaDependente);
        viewDependentes.setAdapter(customAdapter);
        viewDependentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               String msg = listaDependente.get(position).getNome() + ", " + listaDependente.get(position).getEscola() +
                        ", " + listaDependente.get(position).getIdade() + " anos, " + listaDependente.get(position).getSerie();
                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void popularListaDependente(){
        String[] nomes = getResources().getStringArray(R.array.nomes);
        String[] escolas = getResources().getStringArray(R.array.escolas);
        int[] idades = getResources().getIntArray(R.array.idades);
        String[] series = getResources().getStringArray(R.array.series);

        for(int i = 0; i<nomes.length; i++){
            dependente = new Dependente();
            dependente.setNome(nomes[i]);
            dependente.setEscola(escolas[i]);
            dependente.setIdade(idades[i]);
            dependente.setSerie(series[i]);
            dependente.setImagem(R.drawable.child);
            listaDependente.add(dependente);
        }


    }
}