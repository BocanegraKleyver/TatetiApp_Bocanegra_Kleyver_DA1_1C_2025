package com.example.bocanegrakleyver_da1_tateti_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private StatsManager statsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        statsManager = new StatsManager(this);

        Button btnNuevoJuego = findViewById(R.id.btn_nuevo_juego);
        Button btnContinuar = findViewById(R.id.btn_continuar);
        Button btnSalir = findViewById(R.id.btn_salir);

        btnNuevoJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreActual = statsManager.obtenerNombre();
                String dificultadActual = statsManager.obtenerDificultad();
                if (!nombreActual.isEmpty()) {
                    mostrarDialogoConfirmacionNuevoJuego(nombreActual, dificultadActual);
                } else {
                    irANuevoJuego();
                }
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreActual = statsManager.obtenerNombre();
                String simboloActual = statsManager.obtenerSimbolo();
                String dificultadActual = statsManager.obtenerDificultad();

                if (!nombreActual.isEmpty()) {
                    mostrarDialogoConfirmacionContinuar(nombreActual, simboloActual, dificultadActual);
                } else {
                    mostrarMensajeSinJugador();
                }
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSalir();
            }
        });
    }

    private void mostrarDialogoConfirmacionNuevoJuego(String nombre, String dificultad) {
        int ganados = statsManager.obtenerGanados();
        int perdidos = statsManager.obtenerPerdidos();
        int empatados = statsManager.obtenerEmpatados();

        String mensaje = "Jugador: " + nombre +
                "\nDificultad: " + dificultad +
                "\nGanados: " + ganados +
                " | Perdidos: " + perdidos +
                " | Empates: " + empatados +
                "\n\n¿Deseás borrar estas estadísticas y comenzar un nuevo juego?";

        new AlertDialog.Builder(this)
                .setTitle("Nuevo Juego")
                .setMessage(mensaje)
                .setPositiveButton("Sí, borrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        statsManager.resetearTodo();
                        irANuevoJuego();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void mostrarDialogoConfirmacionContinuar(String nombre, String simbolo, String dificultad) {
        int ganados = statsManager.obtenerGanados();
        int perdidos = statsManager.obtenerPerdidos();
        int empatados = statsManager.obtenerEmpatados();

        String mensaje = "Jugador: " + nombre +
                "\nSímbolo: " + simbolo +
                "\nDificultad: " + dificultad +
                "\nGanados: " + ganados +
                " | Perdidos: " + perdidos +
                " | Empates: " + empatados +
                "\n\n¿Deseás continuar esta partida?";

        new AlertDialog.Builder(this)
                .setTitle("Continuar partida")
                .setMessage(mensaje)
                .setPositiveButton("Sí, continuar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeActivity.this, GameActivity.class);
                        intent.putExtra("nombreJugador", nombre);
                        intent.putExtra("simboloJugador", simbolo);
                        intent.putExtra("dificultad", dificultad);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void mostrarDialogoSalir() {
        new AlertDialog.Builder(this)
                .setTitle("Salir de la aplicación")
                .setMessage("¿Estás seguro que querés salir?")
                .setPositiveButton("Sí, salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity(); // Cierra toda la app
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void irANuevoJuego() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void mostrarMensajeSinJugador() {
        new AlertDialog.Builder(this)
                .setTitle("Sin partida")
                .setMessage("No hay partida anterior. Empezá un Nuevo Juego.")
                .setPositiveButton("OK", null)
                .show();
    }
}
