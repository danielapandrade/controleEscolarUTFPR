package br.edu.utfpr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Dependente> listaDependente;
    LayoutInflater layoutInflater;
    Dependente dependente;

    @Override
    public int getCount() {
        return listaDependente.size();
    }

    @Override
    public Object getItem(int i) {
        return listaDependente.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public CustomAdapter(Context context, ArrayList<Dependente> dependentes) {
        this.context = context;
        this.listaDependente = dependentes;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {

        View dependentesCadastradosView = view;
        if (dependentesCadastradosView == null) {
            dependentesCadastradosView = layoutInflater.inflate(R.layout.layout_dependentes_cadastrados, null, true);
        }

        ImageView iconeImagem = dependentesCadastradosView.findViewById(R.id.imageView);
        TextView nome = dependentesCadastradosView.findViewById(R.id.textViewDependenteCadastrado);
        TextView escola = dependentesCadastradosView.findViewById(R.id.textViewEscolaDependenteCadastrado);
        TextView idade = dependentesCadastradosView.findViewById(R.id.textViewIdadeDependenteCadastrado);
        TextView serie = dependentesCadastradosView.findViewById(R.id.textViewSerieDependenteCadastrado);

        dependente = listaDependente.get(posicao);

        iconeImagem.setImageResource(dependente.getImagem());
        nome.setText(dependente.getNome());
        escola.setText(dependente.getEscola());
        idade.setText(Integer.toString(dependente.getIdade()));
        serie.setText(dependente.getSerie());


        return dependentesCadastradosView;
    }
}
