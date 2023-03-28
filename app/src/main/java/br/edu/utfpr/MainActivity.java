package br.edu.utfpr;

import static br.edu.utfpr.R.id.menuItemCadastrarDependente;
import static br.edu.utfpr.R.id.menuItemCadastrarEvento;
import static br.edu.utfpr.R.id.menuItemSobre;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal_opcoes,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case menuItemCadastrarDependente:
                Intent intent = new Intent(this, DependentesCadastradosActivity.class);
                startActivity(intent);
                return true;

            case menuItemCadastrarEvento:
                Intent intentEvento = new Intent(this, EventosCadastradosActivity.class);
                startActivity(intentEvento);
                return true;

            case menuItemSobre:
                Intent intentSobre = new Intent (this, SobreActivity.class);
                startActivity(intentSobre);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}