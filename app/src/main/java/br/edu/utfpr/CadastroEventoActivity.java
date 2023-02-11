package br.edu.utfpr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastroEventoActivity extends AppCompatActivity {

    private Spinner spinner;

    private EditText evento;

    private EditText escola;

    private EditText data;

    private CheckBox checkBoxComida;

    private CheckBox checkBoxBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        evento = findViewById(R.id.editTextEvento);
        escola = findViewById(R.id.editTextEscolaEvento);
        data = findViewById(R.id.editTextDataEvento);
        checkBoxComida = findViewById(R.id.checkBoxComida);
        checkBoxBebida = findViewById(R.id.checkBoxBebida);


        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.tipo_evento,
                android.R.layout.simple_spinner_item);

        spinner = (Spinner) findViewById(R.id.spinnerEvento);
        spinner.setAdapter(adapter);
    }

    public void limpar(View view) {
        escola.setText("");
        evento.setText("");
        data.setText("");
        checkBoxComida.setChecked(false);
        checkBoxBebida.setChecked(false);
        evento.requestFocus();

        Toast.makeText(this, "Valores deletados!", Toast.LENGTH_SHORT).show();
    }

    public void salvar(View view) {
        if (escola.getText().equals("") || evento.getText().equals("") ||
                data.getText().equals("") || verificarcheckBoxLevar()) {

            Toast.makeText(this, "Não pode haver campos vazios!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean verificarcheckBoxLevar() {
        if (!checkBoxBebida.isChecked() && !checkBoxComida.isChecked()) {
            return true;

        }

        return false;
    }


}