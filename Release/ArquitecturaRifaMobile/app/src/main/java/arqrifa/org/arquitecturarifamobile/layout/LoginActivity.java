package arqrifa.org.arquitecturarifamobile.layout;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import arqrifa.org.arquitecturarifamobile.R;
import arqrifa.org.arquitecturarifamobile.app.ArquitecturaRifaApplication;
import arqrifa.org.arquitecturarifamobile.datatypes.DTUsuario;
import arqrifa.org.arquitecturarifamobile.rest.HttpUrlConnectionClient;

public class LoginActivity extends AppCompatActivity {

    private EditText txtCi;
    private EditText txtPass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCi = (EditText) findViewById(R.id.txtCi);
        txtPass = (EditText) findViewById(R.id.txtPass);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void login(View v) {
        String ci = String.valueOf(txtCi.getText());
        String pass = String.valueOf(txtPass.getText());
        if(ci.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Ingrese su cédula y contraseña", Toast.LENGTH_SHORT).show();
        }
        else {
            new GetDataTask(this).execute(ci, pass);
            progressBar.setVisibility(View.VISIBLE);
        }

    }

    private class GetDataTask extends AsyncTask<String, Void, DTUsuario> {

        private LoginActivity mainActivity;

        public GetDataTask(LoginActivity activity) {
            mainActivity = activity;
        }

        @Override
        protected DTUsuario doInBackground(String... params) {
            DTUsuario usuario = null;
            try {
                usuario = new HttpUrlConnectionClient().login(params[0], params[1]);
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
            return usuario;
        }

        protected void onPostExecute(DTUsuario usuario) {
            mainActivity.progressBar.setVisibility(View.GONE);
            try {
                if (usuario == null){
                    throw new Exception(getString(R.string.login_invalid_credentials));
                }
                if (!usuario.getRol().equals(DTUsuario.ESTUDIANTE)) {
                    throw new Exception(getString(R.string.login_user_not_authorized));
                }

                ArquitecturaRifaApplication application = ((ArquitecturaRifaApplication)getApplicationContext());
                application.setUsuario(usuario);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception ex) {
                Toast.makeText(mainActivity, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
