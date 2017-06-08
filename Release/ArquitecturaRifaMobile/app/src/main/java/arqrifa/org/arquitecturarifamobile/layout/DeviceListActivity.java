package arqrifa.org.arquitecturarifamobile.layout;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;


import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.Set;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.datatypes.BluetoothCommandService;
import arqrifa.org.arquitecturarifamobile.datatypes.DTAsistencia;
import arqrifa.org.arquitecturarifamobile.datatypes.DTReunion;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;

public class DeviceListActivity extends AppCompatActivity {


    private static final int REQUEST_ENABLE_BT = 1;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_list);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Asigna resultado CANCELED por si el usuario vuelve para atras
        setResult(Activity.RESULT_CANCELED);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //Si el bluetooth está habilitado busca los dispositivos emparejados , sino primero
        // lo habilita y luego hace la búsqueda
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            showDevices();
        }
    }

    private void showDevices(){
        //Inicializa el command service
        if (mCommandService==null)
            setupCommand();

        //Adaptador para dispositivos emparejados
        pairedAdapter = new ArrayAdapter(this, R.layout.simple_list_item);
        ListView listAdapter = (ListView) findViewById(R.id.PairedDevicesList);
        listAdapter.setAdapter(pairedAdapter);
        listAdapter.setOnItemClickListener(DeviceClickListener);

        //Inicializa la lista de dispositivos emparejados
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }else{
            pairedAdapter.add(R.string.bt_no_devices);
            //TODO hacer que no se pueda clickear
        }

        // Adaptador para dispositivos sin emparejar, nuevos descubrimientos
        othersAdapter = new ArrayAdapter(this, R.layout.simple_list_item);
        ListView listPairedView = (ListView) findViewById(R.id.OtherDevicesList);
        listPairedView.setAdapter(othersAdapter);
        listPairedView.setOnItemClickListener(DeviceClickListener);

        // Registro para el evento de dispositivo encontrado
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Registro para el evento de busqueda finalizada
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Setea el evento de busqueda en el boton
        Button scanButton = (Button) findViewById(R.id.buttonBtSearch);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doDiscovery();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mCommandService != null) {
            if (mCommandService.getState() == BluetoothCommandService.STATE_NONE) {
                Toast.makeText(this,"Bluetooth is now on",Toast.LENGTH_LONG).show();
                mCommandService.start();
            }
        }
        showDevices();
    }

    private void doDiscovery() {

        //TODO agregar "escaneando"

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }

        mBluetoothAdapter.startDiscovery();
    }

    private void setupCommand() {
        // Inicializa el Bluetooth Command para crear conexiones
        mCommandService = new BluetoothCommandService(this, mHandler);
    }


    private OnItemClickListener DeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancelar la busqueda
            mBluetoothAdapter.cancelDiscovery();

            // Obtiene la MAC, ultimos 17 digitos del texto
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);


            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            //Intenta conectarse, si lo logra, guarda el dispositivo y devuelve CONNECTED
            try {
                mCommandService.connect(device);
                CONNECTED_DEVICE_ADDRESS = device.getAddress();
                CONNECTED_DEVICE_NAME = device.getName();
                setResult(Activity.RESULT_OK);
                finish();
            }catch(Exception ex){
                //todo mostrar mensaje de error
                Toast.makeText(v.getContext(),"da",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Cancelar la busqueda
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }

        // Desregistrar los eventos
        this.unregisterReceiver(mReceiver);
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Obtiene el dispositivo
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Si ya fue emparejado, no lo lista por que ya se muestra en la otra lista
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    othersAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //TODO set label "select" y terminar progress bar
                if (othersAdapter.getCount() == 0) {
                    othersAdapter.add(R.string.bt_no_devices);
                }
            }
        }
    };

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    setupCommand();
                } else {
                    Toast.makeText(this, R.string.bt_enable_error, Toast.LENGTH_SHORT).show();
                    finish();
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
            finish();
        } else if (item.getItemId() == R.id.action_cerrar_sesion){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}