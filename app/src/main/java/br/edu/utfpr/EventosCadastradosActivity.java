package br.edu.utfpr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EventosCadastradosActivity extends AppCompatActivity {

    private ListView listViewEventosCadastrados;
    private ArrayAdapter<Evento> listaAdapter;
    private ArrayList<Evento> listaDeEventos;

    private CustomAdapterEvento customAdapter;

    private int posicaoSelecionada = -1;

    Context context;

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

        listViewEventosCadastrados.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {

                        posicaoSelecionada = position;
                        alterarEvento();
                        return true;
                    }
                });

        popularLista();
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

            listaAdapter.notifyDataSetChanged();
        }
    }

    public void cancelar(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}