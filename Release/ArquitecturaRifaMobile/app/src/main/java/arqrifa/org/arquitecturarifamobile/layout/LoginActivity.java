package arqrifa.org.arquitecturarifamobile.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.app.ArquitecturaRifaApplication;
import arqrifa.org.arquitecturarifamobile.datatypes.DTMensajeError;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;

public class LoginActivity extends AppCompatActivity {

    EditText txtCi;
    EditText txtPass;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCi = (EditText) findViewById(R.id.txtCi);
        txtPass = (EditText) findViewById(R.id.txtPass);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void login(View v) {
        String params = "ci="+ txtCi.getText()+"&pass=" + txtPass.getText();
        new GetDataTask(this).execute(params);
        progressBar.setVisibility(View.VISIBLE);
    }

    private class GetDataTask extends AsyncTask<String, Void, Object> {

        private LoginActivity mainActivity;

        public GetDataTask(LoginActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected Object doInBackground(String... params) {
            Object response = null;
            try {
                URL url = new URL(getResources().getString(R.string.net_services_address)+"usuarios/login?" + params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.connect();

                BufferedReader reader;
                if (con.getResponseCode() == HttpURLConnection.HTTP_CONFLICT){
                    reader = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTMensajeError.class);
                } else {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    response = new Gson().fromJson(reader.readLine(),DTUsuario.class);
                }
                reader.close();

                con.disconnect();
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            return response;
        }

        protected void onPostExecute(Object response) {
            mainActivity.progressBar.setVisibility(View.GONE);
            DTUsuario usuario;
            try {
                if (response == null){
                    throw new Exception("Usuario o contraseña incorrectos");
                }

                if (response instanceof DTMensajeError) {
                    throw new Exception(((DTMensajeError)response).getMensaje());
                }

                usuario = (DTUsuario)response;

                if (!usuario.getRol().equals(DTUsuario.ESTUDIANTE)) {
                    throw new Exception("Solo los estudiantes tienen acceso a la aplicación");
                }

                ArquitecturaRifaApplication application = ((ArquitecturaRifaApplication)getApplicationContext());
                application.setUsuario(usuario);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
