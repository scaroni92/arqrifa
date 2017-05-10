package arqrifa.org.arquitecturarifamobile.layout;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.datatypes.DTMensajeError;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;

public class MainActivity extends AppCompatActivity implements ReunionFragment.OnFragmentInteractionListener {
    private DTUsuario usuario;
    private RecyclerView rvCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        usuario = (DTUsuario) getIntent().getExtras().get("usuario");

        new GetReunionesTask(this).execute("gen=" + usuario.getGeneracion());
        new GetProximaReunionTask(this).execute("gen=" + usuario.getGeneracion());
        new GetUltimaReunionTask(this).execute("gen=" + usuario.getGeneracion());

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabHost tabs = (TabHost)findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Próxima");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Reciente");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Calend...");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        //Se cambia el color de texto de las tabs
        for(int i=0;i<tabs.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.WHITE);
        }

        rvCalendario = (RecyclerView)findViewById(R.id.rvCalendario);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCalendario.setLayoutManager(llm);
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    class GetReunionesTask extends AsyncTask<String, Void, Object> {

        private MainActivity mainActivity;

        public GetReunionesTask(MainActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected Object doInBackground(String... params) {
            Object response = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/ArquitecturaRifa/api/reuniones?"+ params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.connect();

                BufferedReader reader;
                if (con.getResponseCode() == HttpURLConnection.HTTP_CONFLICT){
                    reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTMensajeError.class);
                } else {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String res = reader.readLine();
                    response = new Gson().fromJson(res,DTReunion[].class);
                }
                reader.close();

                con.disconnect();
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return response;
        }

        protected void onPostExecute(Object response) {
            try {
                if(response instanceof DTReunion[]){
                    rvCalendario.setAdapter(new CalendarioAdapter(Arrays.asList((DTReunion[])response), mainActivity));
                }
            }catch(Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    class GetProximaReunionTask extends AsyncTask<String, Void, Object> {

        private MainActivity mainActivity;

        public GetProximaReunionTask(MainActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected Object doInBackground(String... params) {
            Object response = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/ArquitecturaRifa/api/reuniones/siguiente?"+ params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.connect();

                BufferedReader reader;
                if (con.getResponseCode() == HttpURLConnection.HTTP_CONFLICT){
                    reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTMensajeError.class);
                } else {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String res = reader.readLine();
                    response = new Gson().fromJson(res,DTReunion.class);
                }
                reader.close();

                con.disconnect();
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return response;
        }

        protected void onPostExecute(Object response) {
            try {
                if(response instanceof DTReunion){
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ReunionFragment fragment = ReunionFragment.newInstance((DTReunion) response);
                    fragmentTransaction.add(R.id.tab1, fragment);
                    fragmentTransaction.commit();
                }
            }catch(Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    class GetUltimaReunionTask extends AsyncTask<String, Void, Object> {

        private MainActivity mainActivity;

        public GetUltimaReunionTask(MainActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected Object doInBackground(String... params) {
            Object response = null;
            try {
                URL url = new URL("http://10.0.2.2:8080/ArquitecturaRifa/api/reuniones/ultima?"+ params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.connect();

                BufferedReader reader;
                if (con.getResponseCode() == HttpURLConnection.HTTP_CONFLICT){
                    reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTMensajeError.class);
                } else {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    String res = reader.readLine();
                    response = new Gson().fromJson(res,DTReunion.class);
                }
                reader.close();

                con.disconnect();
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return response;
        }

        protected void onPostExecute(Object response) {
            try {
                if(response instanceof DTReunion){
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ReunionFragment fragment = ReunionFragment.newInstance((DTReunion) response);
                    fragmentTransaction.add(R.id.tab2, fragment);
                    fragmentTransaction.commit();
                }
            }catch(Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }
}
