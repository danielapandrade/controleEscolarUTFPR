package br.edu.utfpr;

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

import androidx.appcompat.app.AppCompatActivity;

import br.edu.utfpr.model.Evento;
import br.edu.utfpr.persistencia.EventoDatabase;
import br.edu.utfpr.utils.UtilsGUI;

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

    public static final String DATA = "DATA";
    public static final String TIPOEVENTO = "TIPOEVENTO";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;
    private int modo;

    private Evento eventoObj;

    public static final String ID = "ID";

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
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (bundle != null) {
            modo = bundle.getInt(MODO, NOVO);
            if (modo == NOVO) {
                eventoObj = new Evento();
                setTitle("Cadastrar novo evento");
            }
            if (modo == ALTERAR) {

                setTitle(getString(R.string.alterar_evento));

                int id = bundle.getInt(ID);

                EventoDatabase database = EventoDatabase.getDatabase(this);

                eventoObj = database.eventoDAO().queryForId(id);


                evento.setText(eventoObj.getEvento());


                escola.setText(eventoObj.getEscola());


                data.setText(eventoObj.getData());


                checkBoxComida.setChecked(eventoObj.isComida());
                checkBoxBebida.setChecked(eventoObj.isBebida());


                String tipoEvento = eventoObj.getTipoEvento();


                for (int pos = 0; 0 < spinner.getAdapter().getCount(); pos++) {

                    Object valor = spinner.getItemAtPosition(pos);

                    if (valor.toString().equals(tipoEvento)) {
                        spinner.setSelection(pos);
                        break;
                    }
                }

                eventoObj.setTipoEvento(eventoObj.getTipoEvento());


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

        String nomeEvento = UtilsGUI.validaCampoTexto(this,
                evento,
                R.string.campoEvento);
        if (nomeEvento == null) {
            return;
        }
        String escolaEvento = UtilsGUI.validaCampoTexto(this,
                escola,
                R.string.escola_vazia);

        if (escolaEvento == null) {
            return;
        }
        String dataEvento = UtilsGUI.validaCampoTexto(this,
                data,
                R.string.data_vazia);

        if (dataEvento == null) {
            return;
        }
        if (verificarcheckBoxLevar()) {
            UtilsGUI.avisoErro(this, R.string.levar_lanche);
            evento.requestFocus();
            return;
        }
        boolean comida = checkBoxComida.isChecked();
        boolean bebida = checkBoxBebida.isChecked();

        Object tipoEvento = spinner.getSelectedItem();

        Intent intent = new Intent();

        eventoObj.setEvento(nomeEvento);
        eventoObj.setEscola(escolaEvento);
        eventoObj.setTipoEvento(tipoEvento.toString());
        eventoObj.setData(dataEvento);
        eventoObj.setBebida(bebida);
        eventoObj.setComida(comida);

        EventoDatabase database = EventoDatabase.getDatabase(this);

        if (modo == NOVO) {

            database.eventoDAO().insert(eventoObj);

        } else {

            database.eventoDAO().update(eventoObj);
        }

        setResult(Activity.RESULT_OK);
        finish();


    }

    public static void cadastrarEvento(AppCompatActivity activity) {

        Intent intent = new Intent(activity, CadastroEventoActivity.class);

        intent.putExtra(MODO, NOVO);

        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarEvento(AppCompatActivity activity, Evento eventoObj, int requestCode) {

        Intent intent = new Intent(activity, CadastroEventoActivity.class);

        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, eventoObj.getId());

        activity.startActivityForResult(intent, requestCode);
    }

    public boolean verificarcheckBoxLevar() {
        if (!checkBoxBebida.isChecked() && !checkBoxComida.isChecked()) {
            return true;
        }

        return false;
    }

    private void cancelar() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        cancelar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tela_cadastro_evento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

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