package br.edu.utfpr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class DependentesCadastradosActivity extends AppCompatActivity {

    private ListView listaDependentes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dependentes_cadastrados);
        listaDependentes = findViewById(R.id.listViewDependentes);
    }
}