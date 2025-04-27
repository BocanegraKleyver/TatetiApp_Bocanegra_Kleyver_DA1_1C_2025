package com.example.bocanegrakleyver_da1_tateti_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private StatsManager statsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statsManager = new StatsManager(this);

        EditText editTextName = findViewById(R.id.editTextName);
        RadioGroup radioGroupSymbol = findViewById(R.id.radioGroupSymbol);
        RadioGroup radioGroupDificultad = findViewById(R.id.radioGroupDificultad);
        RadioGroup radioGroupTurno = findViewById(R.id.radioGroupTurno);
        Button buttonStart = findViewById(R.id.buttonStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextName.getText().toString().trim();

                if (nombre.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor ingresá un nombre válido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Capturar símbolo elegido
                String simbolo;
                int selectedSymbolId = radioGroupSymbol.getCheckedRadioButtonId();

                if (selectedSymbolId == R.id.radioO) {
                    simbolo = "O";
                } else {
                    simbolo = "X"; // Por defecto
                }

                // Capturar dificultad elegida
                String dificultad;
                int selectedDificultadId = radioGroupDificultad.getCheckedRadioButtonId();

                if (selectedDificultadId == R.id.radioNormal) {
                    dificultad = "Normal";
                } else if (selectedDificultadId == R.id.radioDificil) {
                    dificultad = "Dificil";
                } else {
                    dificultad = "Facil"; // Por defecto
                }

                // Capturar turno inicial elegido
                String turnoInicial;
                int selectedTurnoId = radioGroupTurno.getCheckedRadioButtonId();

                if (selectedTurnoId == R.id.radioCPU) {
                    turnoInicial = "CPU";
                } else {
                    turnoInicial = "Jugador"; // Por defecto
                }

                // Guardar en SharedPreferences
                statsManager.guardarNombre(nombre);
                statsManager.guardarSimbolo(simbolo);
                statsManager.guardarDificultad(dificultad);
                // (no hace falta guardar el turno inicial, solo lo enviamos)

                // Ir a GameActivity
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("nombreJugador", nombre);
                intent.putExtra("simboloJugador", simbolo);
                intent.putExtra("dificultad", dificultad);
                intent.putExtra("turnoInicial", turnoInicial);
                startActivity(intent);
            }
        });
    }
}
