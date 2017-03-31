package arqrifa.org.arquitecturarifamobile.layout;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.datatypes.DTEncuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTMensajeError;
import arqrifa.org.arquitecturarifamobile.datatypes.DTPropuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTRespuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;
import arqrifa.org.arquitecturarifamobile.datatypes.DTVoto;

public class CuestionarioActivity extends AppCompatActivity implements View.OnClickListener {
    private DTEncuesta encuesta;
    private DTUsuario usuario;
    private DTVoto voto;
    private int indexPropuesta = 0;

    LinearLayout llRespuestas;
    TextView tvPropuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvPropuesta = (TextView)findViewById(R.id.tvPregunta);
        llRespuestas = (LinearLayout)findViewById(R.id.llRespuestas);


        encuesta = ((DTReunion) getIntent().getExtras().getSerializable("reunion")).getEncuesta();
        usuario = (DTUsuario) getIntent().getExtras().getSerializable("usuario");
        voto = new DTVoto(usuario, new ArrayList<DTRespuesta>());

        mostrarPropuesta();
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
    protected void mostrarPropuesta(){
            tvPropuesta.setText((indexPropuesta + 1) + ". " + encuesta.getPropuestas().get(indexPropuesta).getPregunta());

            llRespuestas.removeAllViews();

            for (DTRespuesta respuesta : encuesta.getPropuestas().get(indexPropuesta).getRespuestas()){
                final Button bt = new Button(this);
                bt.setText(respuesta.getRespuesta());
                bt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                bt.setOnClickListener(this);
                llRespuestas.addView(bt);
            }
            indexPropuesta++;
    }


    @Override
    public void onClick(View v) {
        if (indexPropuesta < encuesta.getPropuestas().size()) {
            int indexRespuesta = ((LinearLayout) v.getParent()).indexOfChild(v);
            DTRespuesta respuestaEscogida = encuesta.getPropuestas().get(indexPropuesta -1).getRespuestas().get(indexRespuesta);
            voto.getRespuestasEscogidas().add(respuestaEscogida);
            mostrarPropuesta();
        }
        else {
           // new PostVotoTask(this).execute(voto);
        }
    }

    class PostVotoTask extends AsyncTask<DTVoto, Void, Object> {

        private CuestionarioActivity cuestionarioActivity;

        public PostVotoTask(CuestionarioActivity activity) {
            cuestionarioActivity = activity;
        }

        @Override
        protected Object doInBackground(DTVoto... voto) {
            Object response = null;
            HttpURLConnection con = null;

            try {
                URL url = new URL("http://10.0.2.2:8080/ArquitecturaRifa/api/servicio/reunion/votar");
                con = (HttpURLConnection)url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");
                con.connect();

                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(new Gson().toJson(voto[0]));
                wr.flush();

                BufferedReader reader;
                if (con.getResponseCode() == HttpURLConnection.HTTP_CONFLICT){
                    reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTMensajeError.class);
                    reader.close();
                }
            } catch (Exception ex) {
                Toast.makeText(cuestionarioActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            finally {
                con.disconnect();
            }
            return response;
        }

        protected void onPostExecute(Object response) {
            Intent intent = new Intent(cuestionarioActivity, MainActivity.class);
            intent.putExtra("mensaje", "Votación exitosa!");
            startActivity(intent);
        }
    }
}
