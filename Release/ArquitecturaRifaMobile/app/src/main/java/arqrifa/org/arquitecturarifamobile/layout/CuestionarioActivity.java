package arqrifa.org.arquitecturarifamobile.layout;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.app.ArquitecturaRifaApplication;
import arqrifa.org.arquitecturarifamobile.datatypes.DTEncuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTMensajeError;
import arqrifa.org.arquitecturarifamobile.datatypes.DTPropuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTRespuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;
import arqrifa.org.arquitecturarifamobile.datatypes.DTVotacion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTVotacion;
import arqrifa.org.arquitecturarifamobile.rest.HttpUrlConnectionClient;

public class CuestionarioActivity extends AppCompatActivity implements View.OnClickListener {

    private DTVotacion voto;

    LinearLayout llRespuestas;
    TextView tvTituloCuestionario;
    Button btnConfirmarVotacion;

    ListIterator<DTPropuesta> iterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvTituloCuestionario = (TextView)findViewById(R.id.tvTituloCuestionario);
        llRespuestas = (LinearLayout)findViewById(R.id.llRespuestas);
        btnConfirmarVotacion = (Button)findViewById(R.id.btnConfirmarVotacion);


        DTReunion reunion =  ((DTReunion) getIntent().getExtras().getSerializable("reunion"));
        ArquitecturaRifaApplication application = ((ArquitecturaRifaApplication)getApplicationContext());
        DTUsuario usuario = application.getUsuario();
        voto = new DTVotacion(usuario, reunion, new ArrayList<DTRespuesta>());

        iterator = reunion.getEncuesta().getPropuestas().listIterator();

        mostrarSiguientePropuesta();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_asistencia) {
            Intent intent = new Intent(this, AsistenciaActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_cerrar_sesion){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        agregarVoto((DTRespuesta)v.getTag());

        if (iterator.hasNext()) {
            mostrarSiguientePropuesta();
        } else {
            mostrarBtnConfirmarVotacion();
        }
    }

    protected void mostrarSiguientePropuesta(){

        DTPropuesta propuesta = iterator.next();
        tvTituloCuestionario.setText(propuesta.getPregunta());
        llRespuestas.removeAllViews();

        for (DTRespuesta respuesta : propuesta.getRespuestas()){
            Button bt = new Button(this);
            bt.setTag(respuesta);
            bt.setText(respuesta.getRespuesta());
            bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            bt.setOnClickListener(this);
            llRespuestas.addView(bt);
        }
    }


    private void agregarVoto(DTRespuesta respuesta) {
        voto.getRespuestasEscogidas().add(respuesta);
    }

        private void mostrarBtnConfirmarVotacion() {
        tvTituloCuestionario.setText("Cuestionario completado");
        llRespuestas.removeAllViews();
        btnConfirmarVotacion.setVisibility(View.VISIBLE);
    }

    public void btnConfirmarVotacionClick(View v) {
        new enviarVotacionTask(this).execute(voto);
    }

    class enviarVotacionTask extends AsyncTask<DTVotacion, Void, Object> {

        private CuestionarioActivity cuestionarioActivity;

        public enviarVotacionTask(CuestionarioActivity activity) {
            cuestionarioActivity = activity;
        }

        @Override
        protected Void doInBackground(DTVotacion... params) {
            try {
                new HttpUrlConnectionClient().postVotacion(params[0]);
            } catch (Exception ex) {
                //TODO: esta linea da error
                // Toast.makeText(cuestionarioActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        protected void onPostExecute(Object resp) {
            Intent intent = new Intent(CuestionarioActivity.this, EncuestaActivity.class);
            intent.putExtra("reunion", voto.getReunion());
            startActivity(intent);
            finish();
        }
    }
}
