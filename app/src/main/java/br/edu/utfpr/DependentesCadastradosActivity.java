package br.edu.utfpr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DependentesCadastradosActivity extends AppCompatActivity {


    ArrayList<Dependente> listaDependentes;
    //private ArrayAdapter<Dependente> listaAdapter;
    private ListView listViewDependentes;
    private Dependente dependente;
    private int posicaoSelecionada = -1;
    private int requestCode;
    private int resultCode;
    private Intent data;
    private CustomAdapterDependente customAdapter;
    Context context;
    private ActionMode actionMode;
    private View viewSelecionada;

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

            MenuInflater inflate = actionMode.getMenuInflater();
            inflate.inflate(R.menu.menu_alterar_excluir_item, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.menuItemAlterar:
                    alterarDependente();
                    actionMode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    excluirDependente();
                    actionMode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            viewSelecionada = null;

            listViewDependentes.setEnabled(true);
        }
    };

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

        listViewDependentes.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewDependentes.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        if (actionMode != null){
                            return false;
                        }

                        posicaoSelecionada = position;

                        view.setBackgroundColor(Color.LTGRAY);

                        viewSelecionada = view;

                        listViewDependentes.setEnabled(false);

                        actionMode = startActionMode(actionModeCallback);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemNovoDependente:
                CadastroDependenteActivity.cadastrarDependente(this);
                return true;

            case R.id.menuItemCancelar:
                cancelar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void excluirDependente(){

        listaDependentes.remove(posicaoSelecionada);
        customAdapter.notifyDataSetChanged();
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

    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelar();
    }

}