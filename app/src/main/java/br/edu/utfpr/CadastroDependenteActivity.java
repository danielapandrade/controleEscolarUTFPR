package br.edu.utfpr;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class CadastroDependenteActivity extends AppCompatActivity {

    private EditText nome;
    private EditText escola;
    private EditText idade;

    public static final String MODO = "MODO";
    public static final String NOME = "NOME";
    public static final String ESCOLA = "ESCOLA";
    public static final String IDADE = "IDADE";
    public static final String SERIE = "SERIE";

    public static final int NOVO = 1;
    public static final int ALTERAR = 2;
    private int modo;

    private RadioGroup radioGroupSerie;

    private RadioButton ensinoInfantil;

    private RadioButton ensinoFundamental1;

    private RadioButton ensinoFundamental2;

    private RadioButton ensinoMedio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_dependente);

        ActionBar actionBar = getActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        nome = findViewById(R.id.editTextNomeDependente);
        escola = findViewById(R.id.editTextEscola);
        idade = findViewById(R.id.editTextNumberIdade);
        radioGroupSerie = findViewById(R.id.radioGroupSerie);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            modo = bundle.getInt(MODO, NOVO);

            if (modo == NOVO) {
                setTitle("Cadastrar novo dependente");
            } else {
                String nomeDependente = bundle.getString(NOME);
                nome.setText(nomeDependente);

                String escolaDependente = bundle.getString(ESCOLA);
                escola.setText(escolaDependente);

                int idadeDependente = bundle.getInt(IDADE);
                idade.setText(String.valueOf(idadeDependente));
                //String tipo = bundle.getString(SERIE);

                int tipo = bundle.getInt(SERIE);

                RadioButton button;
                switch (tipo) {
                    case Dependente.ENSINOINFANTIL:
                        button = findViewById(R.id.radioButtonEnsinoInfantil);
                        button.setChecked(true);
                        break;

                    case Dependente.ENSINOFUNDAMENTAL1:
                        button = findViewById(R.id.radioButtonEnsinfoFundamental1);
                        button.setChecked(true);
                        break;

                    case Dependente.ENSINOFUNDAMENTAL2:
                        button = findViewById(R.id.radioButtonEnsinfoFundamental2);
                        button.setChecked(true);
                        break;

                    case Dependente.ENSINOMEDIO:
                        button = findViewById(R.id.radioButtonEnsinoMedio);
                        button.setChecked(true);
                        break;

                }


                setTitle(getString(R.string.alterar_dependente));


            }

            nome.requestFocus();
        }

        ensinoInfantil = findViewById(R.id.radioButtonEnsinoInfantil);
        ensinoFundamental1 = findViewById(R.id.radioButtonEnsinfoFundamental1);
        ensinoFundamental2 = findViewById(R.id.radioButtonEnsinfoFundamental2);
        ensinoMedio = findViewById(R.id.radioButtonEnsinoMedio);


        setTitle(getString(R.string.dependente));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_cadastro_dependentes,menu);
        return true;
    }

    private void cancelar(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menu_item_salvar_dependente:
                salvarDependente();
                return true;
            case android.R.id.home:
            case R.id.menu_item_cancelar:
                cancelar();
                return true;
            case R.id.menuItemLimpar:
                limpar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void cadastrarDependente(AppCompatActivity activity) {

        Intent intent = new Intent(activity, CadastroDependenteActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarDependente(AppCompatActivity activity, Dependente dependente) {

        Intent intent = new Intent(activity, CadastroDependenteActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(NOME, dependente.getNome());
        intent.putExtra(IDADE, dependente.getIdade());
        intent.putExtra(ESCOLA, dependente.getEscola());
        intent.putExtra(SERIE, dependente.getSerie());


        activity.startActivityForResult(intent, ALTERAR);
    }

    public void limpar() {
        nome.setText("");
        escola.setText("");
        idade.setText("");
        radioGroupSerie.clearCheck();
        nome.requestFocus();

        Toast.makeText(this, "Valores deletados!", Toast.LENGTH_SHORT).show();

    }

    public void salvarDependente() {
        String nomeDependente = nome.getText().toString();
        String idadeDependente = idade.getText().toString();
        String escolaDependente = escola.getText().toString();
        int serie = -1;

        if (nomeDependente.equals("") ||
                escolaDependente.equals("") || idadeDependente.equals("") ||
                verificarSerieSelecionada()) {

            Toast.makeText(this, "Não pode haver campos vazios!", Toast.LENGTH_SHORT).show();

            nome.requestFocus();
            return;

        }

        switch (radioGroupSerie.getCheckedRadioButtonId()) {

            case R.id.radioButtonEnsinoInfantil:
                serie = Dependente.ENSINOINFANTIL;
                break;

            case R.id.radioButtonEnsinfoFundamental1:
                serie = Dependente.ENSINOFUNDAMENTAL1;
                break;

            case R.id.radioButtonEnsinfoFundamental2:
                serie = Dependente.ENSINOFUNDAMENTAL2;
                break;

            case R.id.radioButtonEnsinoMedio:
                serie = Dependente.ENSINOMEDIO;
                break;


        }


        int idadeInteiro = Integer.parseInt(idadeDependente);
        Intent intent = new Intent();
        intent.putExtra(NOME, nomeDependente);
        intent.putExtra(ESCOLA, escolaDependente);
        intent.putExtra(IDADE, idadeInteiro);
        intent.putExtra(SERIE, serie);
        setResult(Activity.RESULT_OK, intent);

        finish();

    }

    public boolean verificarSerieSelecionada() {
        if (!ensinoInfantil.isChecked() && !ensinoFundamental1.isChecked() &&
                !ensinoFundamental2.isChecked() && !ensinoMedio.isChecked()) {
            return true;
        }

        return false;
    }


}