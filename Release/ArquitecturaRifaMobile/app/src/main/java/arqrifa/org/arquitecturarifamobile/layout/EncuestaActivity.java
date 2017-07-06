package arqrifa.org.arquitecturarifamobile.layout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import java.net.HttpURLConnection;
import java.net.URL;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.app.ArquitecturaRifaApplication;
import arqrifa.org.arquitecturarifamobile.datatypes.DTMensajeError;
import arqrifa.org.arquitecturarifamobile.datatypes.DTPropuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTRespuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;
import arqrifa.org.arquitecturarifamobile.datatypes.DTVotacion;
import arqrifa.org.arquitecturarifamobile.rest.HttpUrlConnectionClient;

public class EncuestaActivity extends AppCompatActivity {

    private DTUsuario usuario;
    private DTReunion reunion;

    private TextView textViewTitulo, textViewDuracion;
    private LinearLayout linearLayoutPropuestas;
    private Button buttonCompletarCuestionario, btnVotacionExitosa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        ArquitecturaRifaApplication application = ((ArquitecturaRifaApplication)getApplicationContext());
        usuario = application.getUsuario();
        reunion = (DTReunion) getIntent().getExtras().get("reunion");

        initialize();
        showEncuestaInfo();
        new VerificarVotacionTask(this).execute();

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

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textViewTitulo = (TextView)findViewById(R.id.tvTitulo);
        textViewDuracion = (TextView)findViewById(R.id.tvDuracion);
        linearLayoutPropuestas = (LinearLayout)findViewById(R.id.llPropuestas);
        buttonCompletarCuestionario = (Button)findViewById(R.id.btnCompletarCuestionario);
        buttonCompletarCuestionario.setVisibility(View.GONE);
        btnVotacionExitosa = (Button)findViewById(R.id.btnVotacionExitosa);
        btnVotacionExitosa.setVisibility(View.GONE);
    }

    private void showEncuestaInfo() {
        textViewTitulo.setText(reunion.getEncuesta().getTitulo());
        textViewDuracion.setText("Duración: " + reunion.getEncuesta().getDuracion() + " min");

        TextView tvPregunta, tvRespuesta;
        for (DTPropuesta propuesta : reunion.getEncuesta().getPropuestas()) {
            tvPregunta = new TextView(this);
            tvPregunta.setTextColor(Color.parseColor("#43a047"));
            tvPregunta.setText(propuesta.getPregunta());
            linearLayoutPropuestas.addView(tvPregunta);

            for(DTRespuesta respuesta : propuesta.getRespuestas() ){
                tvRespuesta = new TextView(this);
                tvRespuesta.setText("  " + respuesta.getRespuesta());
                linearLayoutPropuestas.addView(tvRespuesta);
            }
        }
    }



    public void btnCompletarCuestionarioClick(View v) {
        Intent intent = new Intent(EncuestaActivity.this, CuestionarioActivity.class);
        intent.putExtra("reunion", reunion);
        startActivity(intent);
        finish();
    }

    private class VerificarVotacionTask extends AsyncTask<String, Void, DTVotacion> {

        private EncuestaActivity encuestaActivity;

        public VerificarVotacionTask(EncuestaActivity activity) {
            encuestaActivity = activity;
        }

        @Override
        protected DTVotacion doInBackground(String... params) {
            DTVotacion votacion = null;
            try {
                votacion = new HttpUrlConnectionClient().getVotacion(usuario.getCi(), reunion.getId());
            } catch (Exception ex) {
                Toast.makeText(encuestaActivity, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
            return votacion;
        }

        protected void onPostExecute(DTVotacion votacion) {
            try {
                if (votacion == null){
                    if (reunion.isVotacion()){
                        buttonCompletarCuestionario.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    btnVotacionExitosa.setVisibility(View.VISIBLE);
                }
            } catch (Exception ex) {
                Toast.makeText(encuestaActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
