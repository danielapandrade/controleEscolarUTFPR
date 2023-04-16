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

import br.edu.utfpr.model.Evento;
import br.edu.utfpr.persistencia.EventoDatabase;
import br.edu.utfpr.utils.UtilsGUI;

public class EventosCadastradosActivity extends AppCompatActivity {

    private ListView listViewEventosCadastrados;

    private ArrayList<Evento> listaDeEventos;

    private CustomAdapterEvento customAdapter;

    private int posicaoSelecionada = -1;

    Context context;

    private ActionMode actionMode;

    private View viewSelecionada;

    private static final int REQUEST_NOVO_EVENTO = 1;
    private static final int REQUEST_ALTERAR_EVENTO = 2;

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

            Evento eventoObj = (Evento) listViewEventosCadastrados.getItemAtPosition(posicaoSelecionada);
            switch (menuItem.getItemId()) {
                case R.id.menuItemAlterar:
                    alterarEvento(this, eventoObj, REQUEST_ALTERAR_EVENTO);
                    actionMode.finish();
                    return true;

                case R.id.menuItemExcluir:
                    excluirEvento(eventoObj);
                    actionMode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (viewSelecionada != null) {
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
                        Evento eventoObj = (Evento) parent.getItemAtPosition(position);

                        CadastroEventoActivity.alterarEvento(EventosCadastradosActivity.this,
                                eventoObj, REQUEST_ALTERAR_EVENTO);

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

                        if (actionMode != null) {
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

        EventoDatabase database = EventoDatabase.getDatabase(this);

        listaDeEventos = (ArrayList<Evento>) database.eventoDAO().queryAll();

        context = this;

        customAdapter = new CustomAdapterEvento(context, listaDeEventos);
        listViewEventosCadastrados.setAdapter(customAdapter);

    }

    private void alterarEvento(ActionMode.Callback callback, Evento eventoObj, int requestAlterarEvento) {

        eventoObj = listaDeEventos.get(posicaoSelecionada);

        CadastroEventoActivity.alterarEvento(this, eventoObj, REQUEST_ALTERAR_EVENTO);
    }

    public void adicionarEvento(View view) {
        CadastroEventoActivity.cadastrarEvento(this);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if ((requestCode == REQUEST_NOVO_EVENTO || requestCode == REQUEST_ALTERAR_EVENTO) &&
                resultCode == Activity.RESULT_OK) {

            popularLista();
        }


        customAdapter.notifyDataSetChanged();
    }

    private void excluirEvento(final Evento eventoObj) {

        String mensagem = getString(R.string.deseja_realmente_apagar) + "\n" + eventoObj.getEvento();

        DialogInterface.OnClickListener listener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                EventoDatabase database =
                                        EventoDatabase.getDatabase(EventosCadastradosActivity.this);

                                database.eventoDAO().delete(eventoObj);

                                listaDeEventos.remove(eventoObj);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

        UtilsGUI.confirmaAcao(this, mensagem, listener);

        customAdapter.notifyDataSetChanged();

        listaDeEventos.remove(posicaoSelecionada);
        customAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cadastro_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuItemAddEvento:
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