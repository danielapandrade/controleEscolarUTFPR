package br.edu.utfpr;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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

import br.edu.utfpr.model.Dependente;
import br.edu.utfpr.persistencia.DependenteDatabase;
import br.edu.utfpr.utils.UtilsGUI;

public class DependentesCadastradosActivity extends AppCompatActivity {


    ArrayList<Dependente> listaDependentes;

    private ListView listViewDependentes;

    private int posicaoSelecionada = -1;
    private int requestCode;
    private int resultCode;
    private Intent data;
    private CustomAdapterDependente customAdapter;
    Context context;
    private ActionMode actionMode;
    private View viewSelecionada;

    private static final int REQUEST_NOVO_DEPENDENTE   = 1;
    private static final int REQUEST_ALTERAR_DEPENDENTE = 2;

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

            AdapterView.AdapterContextMenuInfo info;

            info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();

            Dependente dependente2 = (Dependente) listViewDependentes.getItemAtPosition(posicaoSelecionada);
            switch(menuItem.getItemId()){
                case R.id.menuItemAlterar:
                    alterarDependente(this, dependente2,REQUEST_ALTERAR_DEPENDENTE);
                    actionMode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    excluirDependente(dependente2);
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
                       Dependente dependente = (Dependente) parent.getItemAtPosition(position);

                        CadastroDependenteActivity.alterarDependente(DependentesCadastradosActivity.this,
                                dependente, REQUEST_ALTERAR_DEPENDENTE);

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
        registerForContextMenu(listViewDependentes);

        this.setTitle(getString(R.string.cadastrarDependente));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_dependentes,menu);
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

        DependenteDatabase database = DependenteDatabase.getDatabase(this);

        listaDependentes = (ArrayList<Dependente>) database.dependenteDao().queryAll();

        context=this;

        customAdapter = new CustomAdapterDependente(context,listaDependentes);
        listViewDependentes.setAdapter(customAdapter);

    }

    private void alterarDependente(ActionMode.Callback callback, Dependente dependente, int requestAlterarDependente){

        dependente = listaDependentes.get(posicaoSelecionada);

        CadastroDependenteActivity.alterarDependente(this, dependente, REQUEST_ALTERAR_DEPENDENTE);
    }

    public void adicionarDependente(View view){

        CadastroDependenteActivity.cadastrarDependente(this);
    }

    private void excluirDependente(final Dependente dependente){

        String mensagem = getString(R.string.deseja_realmente_apagar) + "\n" + dependente.getNome();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch(which){
                            case DialogInterface.BUTTON_POSITIVE:

                                DependenteDatabase database =
                                        DependenteDatabase.getDatabase(DependentesCadastradosActivity.this);

                                database.dependenteDao().delete(dependente);

                                listaDependentes.remove(dependente);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UtilsGUI.confirmaAcao(this, mensagem, listener);

        customAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if ((requestCode == REQUEST_NOVO_DEPENDENTE|| requestCode == REQUEST_ALTERAR_DEPENDENTE) &&
                resultCode == Activity.RESULT_OK){

            popularLista();
        }


            customAdapter.notifyDataSetChanged();
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