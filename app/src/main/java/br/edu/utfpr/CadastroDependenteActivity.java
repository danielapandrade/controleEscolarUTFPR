package br.edu.utfpr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class CadastroDependenteActivity extends AppCompatActivity {

    private EditText nome;
    private EditText escola;
    private EditText idade;

    private RadioGroup radioGroup;

    private RadioButton ensinoInfantil;

    private RadioButton ensinoFundamental1;

    private RadioButton ensinoFundamental2;

    private RadioButton ensinoMedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_dependente);

        nome = findViewById(R.id.editTextNomeDependente);
        escola = findViewById(R.id.editTextEscola);
        idade = findViewById(R.id.editTextNumberIdade);
        radioGroup = findViewById(R.id.radioGroupSerie);

        ensinoInfantil = findViewById(R.id.radioButtonEnsinoInfantil);
        ensinoFundamental1 = findViewById(R.id.radioButtonEnsinfoFundamental1);
        ensinoFundamental2 = findViewById(R.id.radioButtonEnsinfoFundamental2);
        ensinoMedio = findViewById(R.id.radioButtonEnsinoMedio);

    }

    public void limpar(View view) {
        nome.setText("");
        escola.setText("");
        idade.setText("");
        radioGroup.clearCheck();
        nome.requestFocus();

        Toast.makeText(this, "Valores deletados!", Toast.LENGTH_SHORT).show();

    }

    public void salvar(View view) {
        if (nome.getText().equals("") ||
                escola.getText().equals("") || idade.getText().equals("") ||
                verificarSerieSelecionada()) {

            Toast.makeText(this, "Não pode haver campos vazios!", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean verificarSerieSelecionada() {
        if (!ensinoInfantil.isChecked() && !ensinoFundamental1.isChecked() &&
                !ensinoFundamental2.isChecked() && !ensinoMedio.isChecked()) {
            return true;
        }

        return false;
    }

}