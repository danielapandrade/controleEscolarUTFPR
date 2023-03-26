package br.edu.utfpr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CadastroAtividadeActivity extends AppCompatActivity {

    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_atividade);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipo_evento,
                android.R.layout.simple_spinner_item);

        spinner = (Spinner) findViewById(R.id.spinnerDisciplinaAtividade);
        spinner.setAdapter(adapter);
    }
}