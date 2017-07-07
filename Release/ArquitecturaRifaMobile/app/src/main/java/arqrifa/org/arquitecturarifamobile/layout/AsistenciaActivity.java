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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.app.ArquitecturaRifaApplication;
import arqrifa.org.arquitecturarifamobile.datatypes.BluetoothCommandService;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;
import arqrifa.org.arquitecturarifamobile.rest.HttpUrlConnectionClient;

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

    private DTUsuario usuario;
    private DTReunion reunion;
    private boolean accionMarcar;

    //Layout
    private Button btnTieneAsistencia;
    private Button btnMarcarAsistencia;

    public void setReunionActiva (DTReunion pReunion){
        reunion = pReunion;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia);

        ArquitecturaRifaApplication application = ((ArquitecturaRifaApplication)getApplicationContext());
        usuario = application.getUsuario();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnTieneAsistencia = (Button)findViewById(R.id.btnTieneAsistencia);
        btnTieneAsistencia.setVisibility(View.GONE);
        btnMarcarAsistencia = (Button)findViewById(R.id.btnMarcarAsistencia);
        btnMarcarAsistencia.setVisibility(View.GONE);
        accionMarcar = false;

        new ReunionActualTask(this).execute(usuario.getGeneracion());
    }

    public void controlarAsistencia(){
        boolean isParticipante =false;
        for (DTUsuario part : reunion.getParticipantes()){
            if(part.getCi() == usuario.getCi()){
                isParticipante = true;
                break;
            }
        }

        if (isParticipante) {
            showActionMarcarAsistencia(isParticipante);
        } else {
            if(accionMarcar)
                Toast.makeText(this,"Error al marcar asistencia, vuelva a intentarlo", Toast.LENGTH_LONG).show();
            else
                conectarBluetooth();
        }
    }

    private void showActionMarcarAsistencia(boolean isParticipante){
        if(isParticipante){
            btnTieneAsistencia.setVisibility(View.VISIBLE);
            btnMarcarAsistencia.setVisibility(View.GONE);
        } else {
            btnMarcarAsistencia.setVisibility(View.VISIBLE);
            btnTieneAsistencia.setVisibility(View.GONE);
        }
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

        if (!reunion.getEstado().equals(DTReunion.LISTADO)) {
            Toast.makeText(this, "La lista no ha sido habilitada aún", Toast.LENGTH_SHORT).show();
            return;
        }

        mCommandService = DeviceListActivity.mCommandService;
        mCommandService.write(String.valueOf(usuario.getCi()));
        accionMarcar = true;

        Intent intent = new Intent(AsistenciaActivity.this, SplashAsistenciaActivity.class);
        startActivity(intent);
        new ReunionActualTask(this).execute(usuario.getGeneracion());
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


    private class ReunionActualTask extends AsyncTask<Integer, Void, DTReunion> {

        public static final int TIEMPO_ESPERA = 4000;
        private AsistenciaActivity asistenciaActivity;

        public ReunionActualTask(AsistenciaActivity activity) {
            asistenciaActivity = activity;
        }

        @Override
        protected DTReunion doInBackground(Integer... params) {
            DTReunion reunion = null;
            try {
                // Si se está marcando asistencia se espera a que el servidor termine de procesar
                if (accionMarcar){
                    SystemClock.sleep(TIEMPO_ESPERA);
                }
                reunion = new HttpUrlConnectionClient().getReunionActual(params[0]);
            } catch (Exception ex) {
                Toast.makeText(asistenciaActivity, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
            return reunion;
        }

        protected void onPostExecute(DTReunion reunion) {
            try {
                if(reunion != null){
                    asistenciaActivity.setReunionActiva(reunion);
                    asistenciaActivity.showReunion();
                    if(!(reunion.getEstado().equals("Pendiente") || reunion.getEstado().equals("Finalizada"))){
                        asistenciaActivity.controlarAsistencia();
                        getSupportActionBar().setTitle("Reunión en progreso");
                    } else {
                        getSupportActionBar().setTitle("Reunión actual");
                    }
                }
                else {
                    getSupportActionBar().setTitle("Hoy no hay reunión");
                }

            }catch (Exception ex) {
                Toast.makeText(asistenciaActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

}
