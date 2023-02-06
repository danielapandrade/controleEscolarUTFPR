package br.edu.utfpr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton cadastroDependente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadastroDependente = findViewById(R.id.imageButtonCadastrarDependente);
        cadastroDependente.setOnClickListener(v->abrirCadastroDependente());

    }

    public void abrirCadastroDependente(){
        Intent intent = new Intent(this, CadastroDependenteActivity.class);
        startActivity(intent);
    }
}