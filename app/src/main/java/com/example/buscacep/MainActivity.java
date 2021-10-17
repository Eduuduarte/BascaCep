package com.example.buscacep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private EditText textCep;
    private TextView textView;
    private Button buttonBuscar;
    private Retrofit retrofit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textCep = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        buttonBuscar = findViewById(R.id.buttonBuscar);


        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamarEndereço();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.postmon.com.br/v1/cep/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



    }

    private void chamarEndereço() {
        String cep = textCep.getText().toString();

        PostmonService service = retrofit.create(PostmonService.class);

        Call<Endereço> call = service.getEndereco(cep);

        call.enqueue(new Callback<Endereço>() {
            @Override
            public void onResponse(Call<Endereço> call, Response<Endereço> response) {
                if ( response.isSuccessful()){
                    Endereço endereço = response.body();

                    String strEndereço = "Cidade: " + endereço.getCidade() + "\n" +
                            "Bairro: " + endereço.getBairro() + "\n" +
                            "Logradouro: " + endereço.getLogradouro();

                    textView.setText(strEndereço);
                }
            }

            @Override
            public void onFailure(Call<Endereço> call, Throwable t) {
                Toast.makeText(MainActivity.this, "não foi possível realizar a requisição", Toast.LENGTH_LONG).show();
            }
        });


    }


}