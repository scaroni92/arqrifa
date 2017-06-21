package arqrifa.org.arquitecturarifamobile.layout;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.datatypes.BluetoothCommandService;
import arqrifa.org.arquitecturarifamobile.datatypes.DTAsistencia;
import arqrifa.org.arquitecturarifamobile.datatypes.DTMensajeError;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;

public class AsistenciaActivity extends AppCompatActivity  implements ReunionFragment.OnFragmentInteractionListener{

    private static final int REQUEST_CONNECT_DEVICE = 1;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    private static String CONNECTED_DEVICE_NAME = "";
    private static String CONNECTED_DEVICE_ADDRESS ="";

    // Key names received from the BluetoothCommandService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = null;
    private BluetoothAdapter mBluetoothAdapter;
    private ArrayAdapter othersAdapter;

    private ArrayAdapter pairedAdapter;

    public static BluetoothCommandService mCommandService;

    private DTReunion reunion;
    private boolean accionMarcar;

    //Layout
    private Button btnTieneAsistencia;
    private Button btnMarcarAsistencia;
    private ProgressBar progressBar;

    public void setReunionActiva (DTReunion pReunion){
        reunion = pReunion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnTieneAsistencia = (Button)findViewById(R.id.btnTieneAsistencia);
        btnTieneAsistencia.setVisibility(View.GONE);
        btnMarcarAsistencia = (Button)findViewById(R.id.btnMarcarAsistencia);
        btnMarcarAsistencia.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        accionMarcar = false;

        String params = "gen="+ MainActivity.usuario.getGeneracion();
        new GetReunionTask(this).execute(params);
        progressBar.setVisibility(View.VISIBLE);

    }

    public void controlarAsistencia(){
        progressBar.setVisibility(View.GONE);
        boolean found =false;
        for (DTUsuario part :reunion.getParticipantes()){
            if(part.getCi() == MainActivity.usuario.getCi()){
                found=true;
                break;
            }
        }

        if (found){
            showTieneAsistencia();
        }else{
            if(accionMarcar)
                Toast.makeText(this,"Hubo un error al marcar la asistencia, por favor intentelo nuevamente.",Toast.LENGTH_SHORT).show();
            else
                conectarBluetooth();
        }
    }

    private void showTieneAsistencia(){

        showReunion();
        btnTieneAsistencia.setVisibility(View.VISIBLE);
        btnMarcarAsistencia.setVisibility(View.GONE);
    }

    private void showReunion(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ReunionFragment fragment = ReunionFragment.newInstance(reunion);
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

    private void conectarBluetooth(){
    //El dispositivo soporta bluetooth?
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) { //si soporta
            if(CONNECTED_DEVICE_NAME.equals("")) {
                Intent intent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(intent,REQUEST_CONNECT_DEVICE);
            }
        }else{
            Toast.makeText(this,"Bluetooth no soportado",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCommandService != null)
            mCommandService.stop();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    showReunion();
                    btnMarcarAsistencia.setVisibility(View.VISIBLE);
                }else{
                    finish();
                }
                break;
            default:
                finish();
                break;
        }
    }

    public void marcarAsistencia(View v){
        progressBar.setVisibility(View.VISIBLE);
        mCommandService = DeviceListActivity.mCommandService;
        mCommandService.write(String.valueOf(MainActivity.usuario.getCi()));
        accionMarcar = true;
        SystemClock.sleep(2000);
        String params = "gen="+ MainActivity.usuario.getGeneracion();
        new GetReunionTask(this).execute(params);
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE: // TODO : Si falla mostrar mensaje, si conecta mostrar marcar asistencia
                    switch (msg.arg1) {
                        case BluetoothCommandService.STATE_CONNECTED:
                            //mTitle.setText(R.string.title_connected_to);
                            //mTitle.append(mConnectedDeviceName);
                            break;
                        case BluetoothCommandService.STATE_CONNECTING:
                            // mTitle.setText(R.string.title_connecting);
                            break;
                        case BluetoothCommandService.STATE_LISTEN:
                        case BluetoothCommandService.STATE_NONE:
                            // mTitle.setText(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


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
            finish();
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


    private class GetReunionTask extends AsyncTask<String, Void, Object> {

        private AsistenciaActivity asistenciaActivity;

        public GetReunionTask(AsistenciaActivity activity) {
            asistenciaActivity = activity;
        }

        @Override
        protected Object doInBackground(String... params) {
            Object response = null;
            try {
                URL url = new URL(getResources().getString(R.string.net_services_address)+"reuniones/actual?" + params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.connect();

                BufferedReader reader;
                if (con.getResponseCode() == HttpURLConnection.HTTP_CONFLICT){
                    reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTMensajeError.class);
                } else {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTReunion.class);
                }
                reader.close();

                con.disconnect();
            } catch (Exception ex) {
                response = new DTMensajeError("Error de conexión con el servidor");
            }
            return response;
        }

        protected void onPostExecute(Object response) {
            asistenciaActivity.progressBar.setVisibility(View.GONE);
            DTReunion reunion;
            try {
                if (response == null){
                    throw new Exception("No se encontró una reunión en progreso.");
                }

                if (response instanceof DTMensajeError) {
                    throw new Exception(((DTMensajeError)response).getMensaje());
                }

                reunion = (DTReunion) response;

                if (!reunion.getEstado().equals(DTReunion.LISTADO)) {
                    throw new Exception("El pasaje de lista todavia no está habilitado.");
                }

                asistenciaActivity.setReunionActiva(reunion);
                asistenciaActivity.controlarAsistencia();

            }catch (Exception ex) {
                Toast.makeText(asistenciaActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
                asistenciaActivity.finish();
            }

        }
    }

}
