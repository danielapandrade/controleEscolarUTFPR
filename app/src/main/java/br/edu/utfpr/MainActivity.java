package br.edu.utfpr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void abrirDependentesCadastrados (View view){
        Intent intent = new Intent(this, DependentesCadastradosActivity.class);
        startActivity(intent);
    }

    public void abrirEventosCadastrados (View view){
        Intent intent = new Intent(this, EventosCadastradosActivity.class);
        startActivity(intent);
    }

    public void abrirSobre (View view){
        Intent intent = new Intent (this, SobreActivity.class);
        startActivity(intent);
    }


}