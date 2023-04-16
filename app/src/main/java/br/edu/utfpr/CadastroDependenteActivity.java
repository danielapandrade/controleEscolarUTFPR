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

import br.edu.utfpr.model.Dependente;
import br.edu.utfpr.persistencia.DependenteDatabase;
import br.edu.utfpr.utils.UtilsGUI;


public class CadastroDependenteActivity extends AppCompatActivity {

    private Dependente dependente;
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


    public static final String ID      = "ID";

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


                dependente = new Dependente();
                setTitle("Cadastrar novo dependente");
            }
            if(modo == ALTERAR){

                setTitle(getString(R.string.alterar_dependente));

                int id = bundle.getInt(ID);

                DependenteDatabase database = DependenteDatabase.getDatabase(this);

                dependente = database.dependenteDao().queryForId(id);

                nome.setText(dependente.getNome());

                escola.setText(dependente.getEscola());

                idade.setText(String.valueOf(dependente.getIdade()));

                int tipo = dependente.getSerie();

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



    public static void cadastrarDependente(AppCompatActivity activity) {



        Intent intent = new Intent(activity, CadastroDependenteActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarDependente(AppCompatActivity activity, Dependente dependente, int requestCode) {

            Intent intent = new Intent(activity, CadastroDependenteActivity.class);

            intent.putExtra(MODO, ALTERAR);
            intent.putExtra(ID, dependente.getId());

            activity.startActivityForResult(intent, requestCode);




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


        String nomeDependente = UtilsGUI.validaCampoTexto(this,
               nome,
                R.string.nome_vazio);


        if (nomeDependente == null){
            return;
        }

        String escolaDependente = UtilsGUI.validaCampoTexto(this,
                nome,
                R.string.escola_vazia);

        String idadeDependente  = UtilsGUI.validaCampoTexto(this,
                idade,
                R.string.idade_vazia);

        if (idadeDependente  == null){
            return;
        }

        int idadeInteiro = Integer.parseInt(idadeDependente);

        if (idadeInteiro <= 0 || idadeInteiro > 18){
            UtilsGUI.avisoErro(this, R.string.idade_invalida);
            idade.requestFocus();
            return;
        }

        if(verificarSerieSelecionada()){
            UtilsGUI.avisoErro(this, R.string.nenhuma_serie);
            nome.requestFocus();
            return;
        }




        int serie = -1;

        /*if (nomeDependente.equals("") ||
                escolaDependente.equals("") || idadeDependente.equals("") ||
                verificarSerieSelecionada()) {

            Toast.makeText(this, "Não pode haver campos vazios!", Toast.LENGTH_SHORT).show();

            nome.requestFocus();
            return;

        }*/

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


        Intent intent = new Intent();

        dependente.setNome(nomeDependente);
        dependente.setEscola(escolaDependente);
        dependente.setIdade(idadeInteiro);
        dependente.setSerie(serie);


       DependenteDatabase database = DependenteDatabase.getDatabase(this);

        if (modo == NOVO) {

            database.dependenteDao().insert(dependente);

        } else {

            database.dependenteDao().update(dependente);
        }

        setResult(Activity.RESULT_OK);
        finish();

    }

    public boolean verificarSerieSelecionada() {
        if (!ensinoInfantil.isChecked() && !ensinoFundamental1.isChecked() &&
                !ensinoFundamental2.isChecked() && !ensinoMedio.isChecked()) {
            return true;
        }

        return false;
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

}