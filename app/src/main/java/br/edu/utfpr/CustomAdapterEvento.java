package br.edu.utfpr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterEvento extends BaseAdapter {

    Context context;
    ArrayList<Evento> listaEvento;
    LayoutInflater layoutInflater;
    Evento evento;

    @Override
    public int getCount() {
        return listaEvento.size();
    }

    @Override
    public Object getItem(int i) {
        return listaEvento.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public CustomAdapterEvento(Context context, ArrayList<Evento> eventos) {
        this.context = context;
        this.listaEvento = eventos;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View eventosCadastradosView = view;
        if (eventosCadastradosView == null) {
            eventosCadastradosView = layoutInflater.inflate(R.layout.layout_eventos_cadastrados, null, true);
        }

        evento = listaEvento.get(posicao);

        TextView nomeEvento = eventosCadastradosView.findViewById(R.id.textViewEventoCadastrado);
        TextView escolaEvento = eventosCadastradosView.findViewById(R.id.escolaEventoCadastrado);
        TextView dataEvento = eventosCadastradosView.findViewById(R.id.textViewDataEventoCadastrado);
        TextView comida = eventosCadastradosView.findViewById(R.id.textViewLevarComida);
        TextView  bebida = eventosCadastradosView.findViewById(R.id.textViewLevarBebida);
        TextView  tipoEvento = eventosCadastradosView.findViewById(R.id.textViewTipoEventoCadastrado);

        nomeEvento.setText(evento.getEvento());
        escolaEvento.setText(evento.getEscola());
        dataEvento.setText(evento.getData());
        if(evento.isComida())
            comida.setText("Levar comida\n");
        if(evento.isBebida())
            bebida.setText("Levar bebida\n");
        tipoEvento.setText(evento.getTipoEvento());

        return eventosCadastradosView;
    }
}
