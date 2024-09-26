package com.example.aplicacionsumativa;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplicacionsumativa.models.Sismos;
import com.example.aplicacionsumativa.models.SismosService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private SismosService sismosService;
    private List<Sismos> SismosRespond;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button botonActualizar = findViewById(R.id.RfhsBtn);



        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast cargaToast = new Toast(MainActivity.this);
                cargaToast.setText("Recuperando Datos");
                cargaToast.show();
                ActualizarSismos();

            }
        });

        ActualizarSismos();


    }

    private void ActualizarSismos(){

        ListView lvSismos = findViewById(R.id.lista);
        lvSismos.setVisibility(View.INVISIBLE);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.gael.cloud/general/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sismosService = retrofit.create(SismosService.class);

        Call<List<Sismos>> callSismo = sismosService.getSismo();
        callSismo.enqueue(new Callback<List<Sismos>>() {
            @Override
            public void onResponse(Call<List<Sismos>> call, Response<List<Sismos>> response) {
                if (response.isSuccessful()){
                    SismosRespond = response.body();
                    List<String> listaSismos = new ArrayList<>();

                    // Medidor de gravedad, escala de gravedad creacion propia
                    // no referencia una escala de gravdeda oficial.
                    float gravedad;
                    String tipoGravedad = "";
                    for (Sismos sismos : SismosRespond){
                        //Implementar un medidor de gravedad
                        gravedad = sismos.getMagnitud();
                        if (gravedad > 8){
                            tipoGravedad = "Gravedad Alta";

                        } else if (gravedad < 3) {
                            tipoGravedad = "Gravedad Baja";

                        }else if (gravedad > 3 && gravedad < 8){
                            tipoGravedad = "Gravedad Media";
                        }

                        listaSismos.add("Fecha: "+sismos.getFecha()
                                +"\nProfundidad: "
                                + sismos.getProfundidad()
                                + "\nMagnitud: "
                                + sismos.getMagnitud()
                                + "\nRefGeografica:"
                                + sismos.getRefGeografica()
                                + "\nFechaUpdate: "
                                + sismos.getFechaUpdate()
                                + "\nGravedad del Sismo: "
                                + tipoGravedad
                                + "\n");

                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                            getApplicationContext(),
                            R.layout.fila_sismos,
                            R.id.linea_fecha,
                            listaSismos

                    );

                    lvSismos.setAdapter(adaptador);
                    lvSismos.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast toast = new Toast(MainActivity.this);
                    toast.setText("Datos Actualizados");
                    toast.show();
                }else {
                    Log.d("Retrofit", "Conexion correcta,sin respuesta");
                }
            }

            @Override
            public void onFailure(Call<List<Sismos>> call, Throwable throwable) {
                Toast falloToast = new Toast(MainActivity.this);
                falloToast.setText("Fallo de conexion");

            }
        });
    }
}