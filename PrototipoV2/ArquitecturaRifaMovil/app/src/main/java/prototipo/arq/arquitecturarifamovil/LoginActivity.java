package prototipo.arq.arquitecturarifamovil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import prototipo.arq.datatypes.*;

public class LoginActivity extends AppCompatActivity {
    TextView txtUser;
    TextView txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtPass = (TextView) findViewById(R.id.txtPass);
    }

    public void login(View v) {


        String url = "http://10.0.2.2:8080/ArquitecturaRifa/api/servicio/login?";
        url += "ci=" + txtUser.getText();
        url += "&pass=" + txtPass.getText();
        new GetDataTask(this).execute(url);

    }

    private class GetDataTask extends AsyncTask<String, Void, String> {

        private Activity mainActivity;

        public GetDataTask(Activity activity) {
            mainActivity = activity;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String valor = reader.readLine();
                reader.close();
                con.disconnect();
                return valor;
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
            }
            return null;
        }

        protected void onPostExecute(String resultado) {
            if (mainActivity != null) {
                if (resultado == null) {
                    Toast.makeText(mainActivity, "Usuario/Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                } else if (resultado.contains("mensaje")) {
                    MensajeError me = new Gson().fromJson(resultado, MensajeError.class);
                    Toast.makeText(mainActivity, me.getMensaje(), Toast.LENGTH_SHORT).show();
                }
                else {
                    DTEstudiante estudiante = new Gson().fromJson(resultado, DTEstudiante.class);
                    /*Toast.makeText(mainActivity, estudiante.getApellido(), Toast.LENGTH_SHORT).show();*/

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("Estudiante", estudiante);
                    startActivity(intent);
                }
            }
        }
    }
}
