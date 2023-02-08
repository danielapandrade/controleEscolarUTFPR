package br.edu.utfpr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton cadastroDependente;

    private ImageButton cadastroEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadastroDependente = findViewById(R.id.imageButtonCadastrarDependente);
        cadastroDependente.setOnClickListener(v->abrirCadastroDependente());

        cadastroEvento = findViewById(R.id.imageButtonCadastrarEvento);
        cadastroEvento.setOnClickListener(v->abrirCadastroEvento());

    }

    public void abrirCadastroDependente(){
        Intent intent = new Intent(this, CadastroDependenteActivity.class);
        startActivity(intent);
    }

    public void abrirCadastroEvento(){
        Intent intent = new Intent(this, CadastroEventoActivity.class);
        startActivity(intent);
    }
}