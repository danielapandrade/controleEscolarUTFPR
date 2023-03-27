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

public class EventosCadastradosActivity extends AppCompatActivity {

    private ListView listViewEventosCadastrados;

    private ArrayList<Evento> listaDeEventos;

    private CustomAdapterEvento customAdapter;

    private int posicaoSelecionada = -1;

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
                    alterarEvento();
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

            listViewEventosCadastrados.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_cadastrados);
        listViewEventosCadastrados = findViewById(R.id.listViewEventosCadastrados);

        listViewEventosCadastrados.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {

                        posicaoSelecionada = position;
                        alterarEvento();
                    }
                });

        listViewEventosCadastrados.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

       listViewEventosCadastrados.setOnItemLongClickListener(
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

                       listViewEventosCadastrados.setEnabled(false);

                        actionMode = startActionMode(actionModeCallback);

                        return true;
                    }
                });

        popularLista();
        this.setTitle(getString(R.string.CadastrarEvento));
    }

    private void popularLista() {

        context = this;
        listaDeEventos = new ArrayList<>();
        customAdapter = new CustomAdapterEvento(context,listaDeEventos);
        listViewEventosCadastrados.setAdapter(customAdapter);

       /* listaDeEventos = new ArrayList<>();

        listaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                listaDeEventos);

        listViewEventosCadastrados.setAdapter(listaAdapter);*/
    }

    private void alterarEvento() {

        Evento evento = listaDeEventos.get(posicaoSelecionada);

        CadastroEventoActivity.alterarEvento(this, evento);
    }

    public void adicionarEvento(View view) {
        CadastroEventoActivity.cadastrarEvento(this);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            Bundle bundle = data.getExtras();

            String nomeEvento = bundle.getString(CadastroEventoActivity.EVENTO);
            String dataEvento = bundle.getString(CadastroEventoActivity.DATA);
            String escola = bundle.getString(CadastroEventoActivity.ESCOLA);
            boolean comida = bundle.getBoolean(CadastroEventoActivity.COMIDA);
            boolean bebida = bundle.getBoolean(CadastroEventoActivity.BEBIDA);
            String tipoEvento = bundle.getString(CadastroEventoActivity.TIPOEVENTO);


            if (requestCode == CadastroEventoActivity.ALTERAR) {

                Evento evento = listaDeEventos.get(posicaoSelecionada);

                evento.setEvento(nomeEvento);
                evento.setData(dataEvento);
                evento.setEscola(escola);
                evento.setTipoEvento(tipoEvento);
                evento.setBebida(bebida);
                evento.setComida(comida);

                posicaoSelecionada = -1;

            } else {

                Evento evento = new Evento();

                evento.setEvento(nomeEvento);
                evento.setData(dataEvento);
                evento.setEscola(escola);
                evento.setTipoEvento(tipoEvento);
                evento.setBebida(bebida);
                evento.setComida(comida);

                listaDeEventos.add(evento);

            }

            customAdapter.notifyDataSetChanged();
        }
    }

    private void excluirDependente(){

        listaDeEventos.remove(posicaoSelecionada);
        customAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro_eventos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemNovoEvento:
               CadastroEventoActivity.cadastrarEvento(this);
                return true;

            case R.id.menuItemCancelarEvento:
                cancelar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cancelar() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}