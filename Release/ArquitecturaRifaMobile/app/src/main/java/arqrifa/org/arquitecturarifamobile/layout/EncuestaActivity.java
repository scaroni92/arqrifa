package arqrifa.org.arquitecturarifamobile.layout;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.datatypes.DTEncuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTPropuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTRespuesta;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;

public class EncuestaActivity extends AppCompatActivity {
    DTEncuesta encuesta;
    TextView tvTitulo, tvDuracion;
    LinearLayout llPropuestas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        encuesta = ((DTReunion) getIntent().getExtras().get("reunion")).getEncuesta();

        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        tvDuracion = (TextView)findViewById(R.id.tvDuracion);
        llPropuestas = (LinearLayout)findViewById(R.id.llPropuestas);


        cargarDatos();

    }

    private void cargarDatos() {

        tvTitulo.setText(encuesta.getTitulo());
        tvDuracion.setText("Duración: " + encuesta.getDuracion());

        TextView tvPregunta, tvRespuesta;

        for (DTPropuesta propuesta : encuesta.getPropuestas()) {
            tvPregunta = new TextView(this);
            tvPregunta.setTextColor(Color.parseColor("#43a047"));
            tvPregunta.setText(propuesta.getPregunta());
            llPropuestas.addView(tvPregunta);

            for(DTRespuesta respuesta : propuesta.getRespuestas() ){
                tvRespuesta = new TextView(this);
                tvRespuesta.setText(" - " + respuesta.getRespuesta());
                llPropuestas.addView(tvRespuesta);
            }

        }
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
}
