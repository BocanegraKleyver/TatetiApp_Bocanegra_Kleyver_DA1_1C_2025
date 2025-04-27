package com.example.bocanegrakleyver_da1_tateti_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private boolean turnoJugador = true;
    private String simboloJugador = "X";
    private String simboloMaquina = "O";
    private StatsManager statsManager;
    private String nombreJugador;
    private String dificultad = "Facil";
    private String turnoInicial = "Jugador";
    private TextView textStats;
    private TextView textDificultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        statsManager = new StatsManager(this);

        // Recibir datos
        nombreJugador = getIntent().getStringExtra("nombreJugador");
        simboloJugador = getIntent().getStringExtra("simboloJugador");
        dificultad = getIntent().getStringExtra("dificultad");
        turnoInicial = getIntent().getStringExtra("turnoInicial");

        // Mostrar en pantalla
        TextView textNombre = findViewById(R.id.textNombre);
        TextView textSimbolo = findViewById(R.id.textSimbolo);
        textStats = findViewById(R.id.textStats);
        textDificultad = findViewById(R.id.textDificultad);

        textNombre.setText("Jugador: " + nombreJugador);
        textSimbolo.setText("Símbolo: " + simboloJugador);
        textDificultad.setText("Dificultad: " + dificultad);
        actualizarStats();

        simboloMaquina = simboloJugador.equals("X") ? "O" : "X";

        configurarTablero();

        Button btnRestart = findViewById(R.id.buttonRestart);
        btnRestart.setOnClickListener(v -> recreate());

        Button btnMenu = findViewById(R.id.buttonMenu);
        btnMenu.setOnClickListener(v -> volverAlMenu());

        // Si la CPU debe empezar
        if (turnoInicial != null && turnoInicial.equals("CPU")) {
            turnoJugador = false;
            new android.os.Handler().postDelayed(this::turnoMaquina, 300);
        }
    }

    private void configurarTablero() {
        int[] casillasId = {
                R.id.casilla0, R.id.casilla1, R.id.casilla2,
                R.id.casilla3, R.id.casilla4, R.id.casilla5,
                R.id.casilla6, R.id.casilla7, R.id.casilla8
        };

        for (int id : casillasId) {
            Button casilla = findViewById(id);
            casilla.setText("");
            casilla.setEnabled(true);
            casilla.setOnClickListener(v -> {
                if (turnoJugador) {
                    casilla.setText(simboloJugador);
                    casilla.setEnabled(false);
                    turnoJugador = false;
                    boolean hayGanador = verificarResultado();
                    if (!hayGanador) {
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
        Button[] casillas = new Button[9];

        for (int i = 0; i < casillasId.length; i++) {
            casillas[i] = findViewById(casillasId[i]);
            if (casillas[i].getText().toString().equals("")) {
                casillasLibres.add(casillas[i]);
            }
        }

        Button eleccion = null;
        Random random = new Random();

        if (dificultad.equals("Facil")) {
            int indice = random.nextInt(casillasLibres.size());
            eleccion = casillasLibres.get(indice);
        } else if (dificultad.equals("Normal")) {
            Button centro = findViewById(R.id.casilla4);
            if (centro.getText().toString().equals("")) {
                eleccion = centro;
            } else {
                int indice = random.nextInt(casillasLibres.size());
                eleccion = casillasLibres.get(indice);
            }
        } else if (dificultad.equals("Dificil")) {
            eleccion = buscarMovimientoInteligente(casillas, simboloMaquina);
            if (eleccion == null) {
                eleccion = buscarMovimientoInteligente(casillas, simboloJugador);
            }
            if (eleccion == null) {
                Button centro = findViewById(R.id.casilla4);
                if (centro.getText().toString().equals("")) {
                    eleccion = centro;
                }
            }
            if (eleccion == null && !casillasLibres.isEmpty()) {
                int indice = random.nextInt(casillasLibres.size());
                eleccion = casillasLibres.get(indice);
            }
        }

        if (eleccion != null) {
            eleccion.setText(simboloMaquina);
            eleccion.setEnabled(false);
            turnoJugador = true;
            verificarResultado();
        }
    }

    private Button buscarMovimientoInteligente(Button[] casillas, String simbolo) {
        int[][] combinacionesGanadoras = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combo : combinacionesGanadoras) {
            String a = casillas[combo[0]].getText().toString();
            String b = casillas[combo[1]].getText().toString();
            String c = casillas[combo[2]].getText().toString();

            if (a.equals(simbolo) && b.equals(simbolo) && c.equals("")) {
                return casillas[combo[2]];
            }
            if (a.equals(simbolo) && c.equals(simbolo) && b.equals("")) {
                return casillas[combo[1]];
            }
            if (b.equals(simbolo) && c.equals(simbolo) && a.equals("")) {
                return casillas[combo[0]];
            }
        }
        return null;
    }

    private boolean verificarResultado() {
        int[][] combinacionesGanadoras = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
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

        for (int[] combo : combinacionesGanadoras) {
            String a = casillas[combo[0]].getText().toString();
            String b = casillas[combo[1]].getText().toString();
            String c = casillas[combo[2]].getText().toString();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                if (a.equals(simboloJugador)) {
                    statsManager.sumarGanado();
                    mostrarDialogoResultado("¡Ganaste!");
                } else {
                    statsManager.sumarPerdido();
                    mostrarDialogoResultado("¡La máquina ganó!");
                }
                return true;
            }
        }

        boolean empate = true;
        for (Button casilla : casillas) {
            if (casilla.getText().toString().equals("")) {
                empate = false;
                break;
            }
        }

        if (empate) {
            statsManager.sumarEmpatado();
            mostrarDialogoResultado("¡Empate!");
            return true;
        }

        return false;
    }

    private void mostrarDialogoResultado(String mensaje) {
        actualizarStats();
        new AlertDialog.Builder(this)
                .setTitle(mensaje)
                .setMessage(nombreJugador + "\nGanados: " + statsManager.obtenerGanados()
                        + "\nPerdidos: " + statsManager.obtenerPerdidos()
                        + "\nEmpates: " + statsManager.obtenerEmpatados())
                .setPositiveButton("Jugar de nuevo", (dialog, which) -> recreate())
                .setNegativeButton("Volver al menú", (dialog, which) -> volverAlMenu())
                .setCancelable(false)
                .show();
    }

    private void actualizarStats() {
        String texto = "Ganados: " + statsManager.obtenerGanados()
                + " | Perdidos: " + statsManager.obtenerPerdidos()
                + " | Empates: " + statsManager.obtenerEmpatados();
        textStats.setText(texto);
    }

    private void volverAlMenu() {
        Intent intent = new Intent(GameActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
