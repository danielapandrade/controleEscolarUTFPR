package br.edu.utfpr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class CadastroDependenteActivity extends AppCompatActivity {

    private EditText nome;
    private EditText escola;
    private EditText idade;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_dependente);

        nome = findViewById(R.id.editTextNomeDependente);
        escola = findViewById(R.id.editTextEscola);
        idade = findViewById(R.id.editTextNumberIdade);
        radioGroup = findViewById(R.id.radioGroupSerie);

    }

    public void limpar (View view){
        nome.setText("");
        escola.setText("");
        idade.setText("");
        radioGroup.clearCheck();
        nome.requestFocus();
    }

    public void salvar (View view){
        if(nome.getText().equals("")||radioGroup.getCheckedRadioButtonId()!=0||
                escola.getText().equals("")||idade.getText().equals("")){

            Toast.makeText(this, "Não pode haver campos vazios!", Toast.LENGTH_SHORT).show();

        }
    }

}