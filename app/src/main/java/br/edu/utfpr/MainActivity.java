package br.edu.utfpr;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton cadastroDependente;

    private ImageButton cadastroEvento;

    private ImageButton dependenteCadastrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadastroDependente = findViewById(R.id.imageButtonCadastrarDependente);
        cadastroDependente.setOnClickListener(v->abrirCadastroDependente());

        cadastroEvento = findViewById(R.id.imageButtonCadastrarEvento);
        cadastroEvento.setOnClickListener(v->abrirCadastroEvento());

       dependenteCadastrado = findViewById(R.id.imageButtonDependenteCadastrado);
       dependenteCadastrado.setOnClickListener(v->abrirDependentesCadastrados());


    }

    public void abrirCadastroDependente(){
        Intent intent = new Intent(this, CadastroDependenteActivity.class);
        startActivity(intent);
    }

    public void abrirCadastroEvento(){
        Intent intent = new Intent(this, CadastroEventoActivity.class);
        startActivity(intent);
    }

    public void abrirDependentesCadastrados (){
        Intent intent = new Intent(this, DependentesCadastradosActivity.class);
        startActivity(intent);
    }
}