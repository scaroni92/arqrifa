package arqrifa.org.arquitecturarifamobile.layout;

import android.content.Intent;
import android.graphics.Color;
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
import arqrifa.org.arquitecturarifamobile.datatypes.DTVoto;

public class EncuestaActivity extends AppCompatActivity {
    DTReunion reunion;

    TextView textViewTitulo, textViewDuracion;
    LinearLayout linearLayoutPropuestas;
    Button buttonCompletarCuestionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        reunion = (DTReunion) getIntent().getExtras().get("reunion");

        initialize();
        showEncuestaInfo();
        toggleCompletarCuestionario();
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
                tvRespuesta.setText(" - " + respuesta.getRespuesta());
                linearLayoutPropuestas.addView(tvRespuesta);
            }
        }
    }

    private void toggleCompletarCuestionario() {
        if(reunion.isVotacion()){
            buttonCompletarCuestionario.setVisibility(View.VISIBLE);
        } else {
            buttonCompletarCuestionario.setVisibility(View.INVISIBLE);
        }
    }


    public void btnCompletarCuestionarioClick(View v) {
       new VerificarVotacionTask(this).execute();
    }

    private class VerificarVotacionTask extends AsyncTask<String, Void, Object> {

        private EncuestaActivity encuestaActivity;

        public VerificarVotacionTask(EncuestaActivity activity) {
            encuestaActivity = activity;
        }

        @Override
        protected Object doInBackground(String... params) {
            Object response = null;
            try {
                ArquitecturaRifaApplication application = ((ArquitecturaRifaApplication)getApplicationContext());
                DTUsuario usuario = application.getUsuario();

                URL url = new URL(getResources().getString(R.string.net_services_address)+"encuestas/voto?ci="+usuario.getCi()+"&reunionId=" + reunion.getId());
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.connect();

                BufferedReader reader;
                if (con.getResponseCode() == HttpURLConnection.HTTP_CONFLICT){
                    reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTMensajeError.class);
                } else {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTVoto.class);
                }
                reader.close();

                con.disconnect();
            } catch (Exception ex) {
                response = new DTMensajeError(ex.getMessage());
            }
            return response;
        }

        protected void onPostExecute(Object response) {
            try {
                if (response instanceof DTMensajeError) {
                    throw new Exception(((DTMensajeError)response).getMensaje());
                }
                if (response != null) {
                    throw new Exception("Tu votación ya ha sido enviada");
                }
                Intent intent = new Intent(EncuestaActivity.this, CuestionarioActivity.class);
                intent.putExtra("reunion", reunion);
                startActivity(intent);
               // finish();
            } catch (Exception ex) {
                Toast.makeText(encuestaActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
