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

import java.util.ArrayList;
import java.util.List;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.app.ArquitecturaRifaApplication;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;
import arqrifa.org.arquitecturarifamobile.rest.HttpUrlConnectionClient;

public class MainActivity extends AppCompatActivity implements ReunionFragment.OnFragmentInteractionListener {
    private DTUsuario usuario;
    private RecyclerView rvCalendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArquitecturaRifaApplication application = ((ArquitecturaRifaApplication)getApplicationContext());
        usuario = application.getUsuario();

        initiateTabs();

        new ProximaReunionTask(this).execute(usuario.getGeneracion());
        new UltimaReunionTask(this).execute(usuario.getGeneracion());
        new CalendarioAdapeterTask(this).execute(usuario.getGeneracion());
    }

    private void initiateTabs() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TabHost tabs = (TabHost)findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tab_proxima");
        spec.setContent(R.id.tab_proxima);
        spec.setIndicator(getString(R.string.tab_soon));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tab_reciente");
        spec.setContent(R.id.tab_reciente);
        spec.setIndicator(getString(R.string.tab_recent));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tab_calendario");
        spec.setContent(R.id.tab_calendario);
        spec.setIndicator(getString(R.string.tab_calendar));
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                setTabTextColor(tabs);
            }
        });

        setTabTextColor(tabs);

        rvCalendario = (RecyclerView)findViewById(R.id.rvCalendario);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCalendario.setLayoutManager(llm);
    }

    public void setTabTextColor(TabHost tabHost){
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#b3ffffff"));
        }
        TextView tv = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#ffffff"));
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

    class ProximaReunionTask extends AsyncTask<Integer, Void, DTReunion> {

        private MainActivity mainActivity;

        public ProximaReunionTask(MainActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected DTReunion doInBackground(Integer... params) {
            DTReunion reunion = null;
            try {
                reunion = new HttpUrlConnectionClient().getProximaReunion(params[0]);
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            return reunion;
        }

        protected void onPostExecute(DTReunion response) {
            try {
                if (response == null) {
                    throw new Exception("No hay reuniones pendientes");
                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ReunionFragment fragment = ReunionFragment.newInstance(response);
                fragmentTransaction.add(R.id.tab_proxima, fragment);
                fragmentTransaction.commit();
            }catch(Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    class UltimaReunionTask extends AsyncTask<Integer, Void, DTReunion> {

        private MainActivity mainActivity;

        public UltimaReunionTask(MainActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected DTReunion doInBackground(Integer... params) {
            DTReunion reunion = null;
            try {
                reunion = new HttpUrlConnectionClient().getUltimaReunion(params[0]);
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            return reunion;
        }

        protected void onPostExecute(DTReunion response) {
            try {
                if (response == null) {
                    throw new Exception("No se han realizado reuniones");
                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ReunionFragment fragment = ReunionFragment.newInstance((DTReunion) response);
                fragmentTransaction.add(R.id.tab_reciente, fragment);
                fragmentTransaction.commit();
            }catch(Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    class CalendarioAdapeterTask extends AsyncTask<Integer, Void, List<DTReunion>> {

        private MainActivity mainActivity;

        public CalendarioAdapeterTask(MainActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected List<DTReunion> doInBackground(Integer... params) {
            List<DTReunion> reuniones = new ArrayList<>();
            try {
                reuniones = new HttpUrlConnectionClient().getReuniones(params[0]);
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            return reuniones;
        }

        protected void onPostExecute(List<DTReunion> reuniones) {
            rvCalendario.setAdapter(new CalendarioAdapter(reuniones, mainActivity));
        }
    }

}
