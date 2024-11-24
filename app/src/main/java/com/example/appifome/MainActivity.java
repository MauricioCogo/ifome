package com.example.appifome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button btlogar;
    TextView linkregistro;
    EditText edtlogin, edtsenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtlogin = findViewById(R.id.edtusr);
        edtsenha = findViewById(R.id.edtsenha);
        btlogar = findViewById(R.id.btlogar);

        btlogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EnviajsonpostLogineSenha().execute();
            }
        });

        linkregistro = findViewById(R.id.linkCriaConta);
        linkregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CadNovoUsuario.class);
                startActivity(i);
            }
        });
    }

    class EnviajsonpostLogineSenha extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... arg0) {
            try {
                String url = "http://192.168.1.6/PHP/ifome/consulta_login.php";
                JSONObject jsonValores = new JSONObject();
                jsonValores.put("nome", edtlogin.getText().toString());
                jsonValores.put("senha", edtsenha.getText().toString());
                conexaouniversal mandar = new conexaouniversal();
                System.out.println(jsonValores);
                return mandar.postJSONObject(url, jsonValores); // Retorna a mensagem para o onPostExecute
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String mensagem) {
            super.onPostExecute(mensagem);

            try {
                if (mensagem != null && mensagem.contains("SUCCESS")) {
                    JSONObject response = new JSONObject(mensagem);
                    JSONObject usuario = response.getJSONObject("usuario");

                    // Extraindo informações do usuário
                    String idString = usuario.getString("id");
                    String nome = usuario.getString("nome");
                    String senha = usuario.getString("senha");
                    String endereco = usuario.getString("endereço");
                    String telefone = usuario.getString("fone");
                    String cidade = usuario.getString("cidade");

                    // Enviando informações para a próxima tela
                    Intent i = new Intent(getApplicationContext(), TelaPrincipal.class);
                    i.putExtra("id", idString);
                    i.putExtra("nome", nome);
                    i.putExtra("senha", senha);
                    i.putExtra("endereco", endereco);
                    i.putExtra("telefone", telefone);
                    i.putExtra("cidade", cidade);
                    startActivity(i);
                } else if (mensagem != null && mensagem.contains("ERROR")) {
                    Toast.makeText(MainActivity.this, "Login incorreto!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao conectar com o servidor!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception ex) {
                Toast.makeText(MainActivity.this, "Erro inesperado: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
