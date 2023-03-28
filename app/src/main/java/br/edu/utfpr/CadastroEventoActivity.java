package br.edu.utfpr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    public static final String MODO = "MODO";
    public static final String EVENTO = "EVENTO";
    public static final String ESCOLA = "ESCOLA";
    public static final String COMIDA = "COMIDA";
    public static final String BEBIDA = "BEBIDA";
    //public static final String LEVARLANCHE = "LEVARLANCHE";
    public static final String DATA = "DATA";
    public static final String TIPOEVENTO = "TIPOEVENTO";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;
    private int modo;


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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        ActionBar actionBar = getActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (bundle != null) {
            modo = bundle.getInt(MODO, NOVO);
            if (modo == NOVO) {
                setTitle("Cadastrar novo evento");
            } else {
                String nomeEvento = bundle.getString(EVENTO);
                evento.setText(nomeEvento);

                String escolaEvento = bundle.getString(ESCOLA);
                escola.setText(escolaEvento);

                String dataEvento = bundle.getString(DATA);
                data.setText(dataEvento);

                boolean comida = bundle.getBoolean(COMIDA);
                boolean bebida = bundle.getBoolean(BEBIDA);


                checkBoxComida.setChecked(comida);
                checkBoxBebida.setChecked(bebida);


                String curso = bundle.getString(TIPOEVENTO);

                for (int pos = 0; 0 < spinner.getAdapter().getCount(); pos++) {

                    String valor = (String) spinner.getItemAtPosition(pos);

                    if (valor.equals(curso)) {
                        spinner.setSelection(pos);
                        break;
                    }
                }


                setTitle(getString(R.string.alterar_evento));
            }
        }

        setTitle(getString(R.string.evento));
        evento.requestFocus();
    }

    public void limpar() {
        escola.setText("");
        evento.setText("");
        data.setText("");
        checkBoxComida.setChecked(false);
        checkBoxBebida.setChecked(false);
        evento.requestFocus();

        Toast.makeText(this, "Valores deletados!", Toast.LENGTH_SHORT).show();
    }

    public void salvar() {

        if (escola.getText().equals("") || evento.getText().equals("") ||
                data.getText().equals("") || verificarcheckBoxLevar()) {

            Toast.makeText(this, "Não pode haver campos vazios!", Toast.LENGTH_SHORT).show();
        }
        String nomeEvento = evento.getText().toString();
        String escolaEvento = escola.getText().toString();
        String dataEvento = data.getText().toString();
        boolean comida = checkBoxComida.isChecked();
        boolean bebida = checkBoxBebida.isChecked();

        String tipoEvento = (String) spinner.getSelectedItem();

        Intent intent = new Intent();
        intent.putExtra(EVENTO, nomeEvento);
        intent.putExtra(ESCOLA, escolaEvento);
        intent.putExtra(TIPOEVENTO, tipoEvento);
        intent.putExtra(DATA, dataEvento);
        intent.putExtra(COMIDA, comida);
        intent.putExtra(BEBIDA, bebida);


        setResult(Activity.RESULT_OK, intent);

        finish();


    }

    public static void cadastrarEvento(AppCompatActivity activity) {

        Intent intent = new Intent(activity, CadastroEventoActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarEvento(AppCompatActivity activity, Evento evento) {

        Intent intent = new Intent(activity, CadastroEventoActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(EVENTO, evento.getEvento());
        intent.putExtra(ESCOLA, evento.getEscola());
        intent.putExtra(TIPOEVENTO, evento.getTipoEvento());
        intent.putExtra(DATA, evento.getData());
        intent.putExtra(COMIDA, evento.isComida());
        intent.putExtra(BEBIDA, evento.isBebida());

        activity.startActivityForResult(intent, ALTERAR);
    }

    public boolean verificarcheckBoxLevar() {
        if (!checkBoxBebida.isChecked() && !checkBoxComida.isChecked()) {
            return true;
        }

        return false;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_cadastro_evento,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.menuItemSalvarEvento:
                salvar();
                return true;
            case android.R.id.home:
            case R.id.menuItemCancelar:
                cancelar();
                return true;
            case R.id.menuItemLimparEvento:
                limpar();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}