package com.example.appifome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

public class TelaPrincipal extends AppCompatActivity {

    private RadioGroup radioGroupType;
    private RadioButton rbPizza, rbBebida;
    private LinearLayout pizzaFields, bebidaFields, addressField;
    private EditText edtPizzaSize, edtPizzaFlavor, edtBebidaDescription, edtAddress;
    private CheckBox cbEntrega;
    private Button btnSubmit;

    private usuario user = new usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        radioGroupType = findViewById(R.id.radioGroupType);
        rbPizza = findViewById(R.id.rbPizza);
        rbBebida = findViewById(R.id.rbBebida);
        pizzaFields = findViewById(R.id.pizzaFields);
        bebidaFields = findViewById(R.id.bebidaFields);
        addressField = findViewById(R.id.addressField);
        edtPizzaSize = findViewById(R.id.edtPizzaSize);
        edtPizzaFlavor = findViewById(R.id.edtPizzaFlavor);
        edtBebidaDescription = findViewById(R.id.edtBebidaDescription);
        edtAddress = findViewById(R.id.edtAddress);
        cbEntrega = findViewById(R.id.cbEntrega);
        btnSubmit = findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        user.setId(Integer.parseInt(id));
        String nome = intent.getStringExtra("nome");
        user.setNome(nome);
        String senha = intent.getStringExtra("senha");
        user.setSenha(senha);
        String endereco = intent.getStringExtra("endereco");
        user.setEndereco(endereco);
        String telefone = intent.getStringExtra("telefone");
        user.setFone(telefone);
        String cidade = intent.getStringExtra("cidade");
        user.setCidade(cidade);

        radioGroupType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbPizza) {
                pizzaFields.setVisibility(View.VISIBLE);
                bebidaFields.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbBebida) {
                bebidaFields.setVisibility(View.VISIBLE);
                pizzaFields.setVisibility(View.GONE);
            }
        });

        cbEntrega.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                addressField.setVisibility(View.VISIBLE);
            } else {
                addressField.setVisibility(View.GONE);
            }
        });

        btnSubmit.setOnClickListener(v -> handleSubmit());
    }

    private void handleSubmit() {
        int id_usuario = user.getId();

        boolean pizza = false;
        String sabor = edtPizzaFlavor.getText().toString();
        String tamanho = edtPizzaSize.getText().toString();

        boolean bebida = false;
        String desc = edtBebidaDescription.getText().toString();

        boolean tele = false;
        String endereco = edtAddress.getText().toString();

        if (rbPizza.isChecked()) {
            pizza = true;
            bebida = false;
            desc = "";
            if (tamanho.isEmpty() || sabor.isEmpty()) {
                Alert("Alerta","Por favor, preencha o tamanho e o sabor da pizza.");
                return;
            }
        } else if (rbBebida.isChecked()) {
            bebida = true;
            pizza = false;
            sabor = "";
            tamanho = "";
            if (desc.isEmpty()) {
                Alert("Alerta","Por favor, preencha a descrição da bebida.");
                return;
            }
        }else{
            Alert("Alerta","Por favor, selecione pizza ou bebida!");
            return;
        }

        if (cbEntrega.isChecked()) {
            tele = true;
            if (endereco.isEmpty()) {
                Alert("Alerta","Por favor, preencha o endereço de entrega.");
                return;
            }
        } else {
            tele = false;
            endereco = "";
        }

        new SubmitOrderTask().execute(id_usuario, pizza, tamanho, sabor, bebida, desc, tele, endereco);
    }


    private class SubmitOrderTask extends AsyncTask<Object, Void, String> {
        @Override
        protected String doInBackground(Object... params) {
            try {
                int id_usuario = (int) params[0];
                boolean pizza = (boolean) params[1];
                String tamanho = (String) params[2];
                String sabor = (String) params[3];
                boolean bebida = (boolean) params[4];
                String desc = (String) params[5];
                boolean tele = (boolean) params[6];
                String endereco = (String) params[7];

                String url = "http://200.132.172.207/PHP/cadastra_pedido.php";

                JSONObject jsonValores = new JSONObject();
                jsonValores.put("id_usuario", id_usuario);
                jsonValores.put("pizza", pizza);
                jsonValores.put("tamanho", tamanho);
                jsonValores.put("sabor", sabor);
                jsonValores.put("bebida", bebida);
                jsonValores.put("desc_bebida", desc);
                jsonValores.put("tele", tele);
                jsonValores.put("endereco", endereco);

                conexaouniversal mandar = new conexaouniversal();
                String mensagem = mandar.postJSONObject(url, jsonValores);

                System.out.println(jsonValores);
                if (mensagem.contains("SUCCESS")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(TelaPrincipal.this);
                            builder.setTitle("Pedido Realizado");
                            builder.setMessage("Seu pedido foi realizado com sucesso!");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    edtPizzaSize.setText("");
                                    edtPizzaFlavor.setText("");
                                    edtBebidaDescription.setText("");
                                    edtAddress.setText("");
                                    cbEntrega.setChecked(false);
                                    radioGroupType.clearCheck();
                                    pizzaFields.setVisibility(View.GONE);
                                    bebidaFields.setVisibility(View.GONE);
                                    addressField.setVisibility(View.GONE);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                }
                else if (mensagem.contains("ERROR")) {

                    Alert("Error", "Não foi possivel realizar o pedido! Veja se está tudo certo e tente novamente");

                }else{
                    Alert("Error", "Não foi possivel realizar o pedido! Verifique a conexão com a internet!");
                }

                return mensagem;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.contains("ERROR")) {
                Toast.makeText(TelaPrincipal.this, "Login incorreto!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(TelaPrincipal.this, "Erro ao conectar com o servidor!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void Alert(String title, String body){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(TelaPrincipal.this);
                builder.setTitle(title);
                builder.setMessage(body);

                builder.setPositiveButton("OK", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}