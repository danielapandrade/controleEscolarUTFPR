package br.edu.utfpr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DependentesCadastradosActivity extends AppCompatActivity {


    ArrayList<Dependente> listaDependentes;
    private ArrayAdapter<Dependente> listaAdapter;
    private ListView listViewDependentes;
    private Dependente dependente;
    private int posicaoSelecionada = -1;
    private int requestCode;
    private int resultCode;
    private Intent data;
    private CustomAdapterDependente customAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependentes_cadastrados);

        listViewDependentes = findViewById(R.id.listViewEventosCadastrados);


        listViewDependentes.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {

                        posicaoSelecionada = position;
                        alterarDependente();

                    }
                });

        listViewDependentes.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        posicaoSelecionada = position;
                        alterarDependente();
                        return true;
                    }
                });

        popularLista();

        this.setTitle(getString(R.string.cadastrarDependente));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_dependentes,menu);
        return true;
    }

    private void popularLista(){
        context=this;
        listaDependentes = new ArrayList<>();
        customAdapter = new CustomAdapterDependente(context,listaDependentes);
        listViewDependentes.setAdapter(customAdapter);

    }

    private void alterarDependente(){

        Dependente dependente = listaDependentes.get(posicaoSelecionada);

        CadastroDependenteActivity.alterarDependente(this, dependente);
    }

    public void adicionarDependente(View view){

        CadastroDependenteActivity.cadastrarDependente(this);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            String nome = bundle.getString(CadastroDependenteActivity.NOME);
            String escola = bundle.getString(CadastroDependenteActivity.ESCOLA);
            int idade = bundle.getInt(CadastroDependenteActivity.IDADE);
            int serie = bundle.getInt(CadastroDependenteActivity.SERIE);


            if (requestCode == CadastroDependenteActivity.ALTERAR) {

                Dependente dependente = listaDependentes.get(posicaoSelecionada);
                dependente.setNome(nome);
                dependente.setIdade(idade);
                dependente.setEscola(escola);
                dependente.setSerie(serie);
                posicaoSelecionada = -1;

            } else {
               Dependente dependente = new Dependente(nome, escola, idade, serie);

                listaDependentes.add(dependente);
            }

            customAdapter.notifyDataSetChanged();
        }
    }

    public void cancelar(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}