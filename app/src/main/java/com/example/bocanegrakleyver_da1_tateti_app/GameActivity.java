package com.example.bocanegrakleyver_da1_tateti_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity {

    private boolean turnoJugador = true;
    private String simboloJugador = "X"; // Valor por defecto

    private String simboloMaquina = "O"; // Se define según lo que elija el jugador


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Recibir datos del intent
        String nombre = getIntent().getStringExtra("nombreJugador");
        String simbolo = getIntent().getStringExtra("simboloJugador");

        // Mostrar en pantalla
        TextView textNombre = findViewById(R.id.textNombre);
        TextView textSimbolo = findViewById(R.id.textSimbolo);

        textNombre.setText("Jugador: " + nombre);
        textSimbolo.setText("Símbolo: " + simbolo);

        // Guardar el símbolo real del jugador
        simboloJugador = simbolo;
        simboloMaquina = simboloJugador.equals("X") ? "O" : "X";


        // IDs de las 9 casillas
        int[] casillasId = {
                R.id.casilla0, R.id.casilla1, R.id.casilla2,
                R.id.casilla3, R.id.casilla4, R.id.casilla5,
                R.id.casilla6, R.id.casilla7, R.id.casilla8
        };

        // Asignar listeners a cada casilla
        for (int id : casillasId) {
            Button casilla = findViewById(id);
            casilla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (turnoJugador) {
                        casilla.setText(simboloJugador);
                        casilla.setEnabled(false);
                        turnoJugador = false;
                        verificarResultado();
                        turnoMaquina();
                    }
                }
            });
        }
    }

    private void turnoMaquina() {
        int[] casillasId = {
                R.id.casilla0, R.id.casilla1, R.id.casilla2,
                R.id.casilla3, R.id.casilla4, R.id.casilla5,
                R.id.casilla6, R.id.casilla7, R.id.casilla8
        };

        List<Button> casillasLibres = new ArrayList<>();

        for (int id : casillasId) {
            Button casilla = findViewById(id);
            if (casilla.getText().toString().equals("")) {
                casillasLibres.add(casilla);
            }
        }

        if (!casillasLibres.isEmpty()) {
            Random random = new Random();
            int indice = random.nextInt(casillasLibres.size());
            Button eleccion = casillasLibres.get(indice);

            eleccion.setText(simboloMaquina);
            eleccion.setEnabled(false);
            turnoJugador = true;
            verificarResultado();
        }
    }


    private void verificarResultado() {
        int[][] combinacionesGanadoras = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // filas
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columnas
                {0, 4, 8}, {2, 4, 6}             // diagonales
        };

        int[] casillasId = {
                R.id.casilla0, R.id.casilla1, R.id.casilla2,
                R.id.casilla3, R.id.casilla4, R.id.casilla5,
                R.id.casilla6, R.id.casilla7, R.id.casilla8
        };

        Button[] casillas = new Button[9];
        for (int i = 0; i < 9; i++) {
            casillas[i] = findViewById(casillasId[i]);
        }

        // Verificar ganador
        for (int[] combo : combinacionesGanadoras) {
            String a = casillas[combo[0]].getText().toString();
            String b = casillas[combo[1]].getText().toString();
            String c = casillas[combo[2]].getText().toString();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                String ganador = a.equals(simboloJugador) ? "¡Ganaste!" : "¡La máquina ganó!";
                mostrarResultado(ganador);
                return;
            }
        }

        // Verificar empate
        boolean empate = true;
        for (Button casilla : casillas) {
            if (casilla.getText().toString().equals("")) {
                empate = false;
                break;
            }
        }

        if (empate) {
            mostrarResultado("¡Empate!");
        }
    }

    private void mostrarResultado(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

        // Reiniciar el juego después de 2 segundos
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recreate(); // reinicia la actividad completa ✅
            }
        }, 2000);
    }



}
