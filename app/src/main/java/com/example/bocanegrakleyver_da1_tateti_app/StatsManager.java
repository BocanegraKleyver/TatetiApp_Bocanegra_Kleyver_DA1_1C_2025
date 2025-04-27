package com.example.bocanegrakleyver_da1_tateti_app;

import android.content.Context;
import android.content.SharedPreferences;

public class StatsManager {
    private static final String PREFS_NAME = "TatetiStats";
    private static final String KEY_NOMBRE = "nombreJugador";
    private static final String KEY_GANADOS = "partidasGanadas";
    private static final String KEY_PERDIDOS = "partidasPerdidas";
    private static final String KEY_EMPATADOS = "partidasEmpatadas";
    private static final String KEY_SIMBOLO = "simboloJugador";
    private static final String KEY_DIFICULTAD = "dificultadJugador";

    private SharedPreferences prefs;

    public StatsManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void guardarNombre(String nombre) {
        prefs.edit().putString(KEY_NOMBRE, nombre).apply();
    }

    public String obtenerNombre() {
        return prefs.getString(KEY_NOMBRE, "");
    }

    public void guardarSimbolo(String simbolo) {
        prefs.edit().putString(KEY_SIMBOLO, simbolo).apply();
    }

    public String obtenerSimbolo() {
        return prefs.getString(KEY_SIMBOLO, "X"); // Default
    }

    public void guardarDificultad(String dificultad) {
        prefs.edit().putString(KEY_DIFICULTAD, dificultad).apply();
    }

    public String obtenerDificultad() {
        return prefs.getString(KEY_DIFICULTAD, "Facil"); // Default
    }

    public void sumarGanado() {
        int ganados = prefs.getInt(KEY_GANADOS, 0);
        prefs.edit().putInt(KEY_GANADOS, ganados + 1).apply();
    }

    public void sumarPerdido() {
        int perdidos = prefs.getInt(KEY_PERDIDOS, 0);
        prefs.edit().putInt(KEY_PERDIDOS, perdidos + 1).apply();
    }

    public void sumarEmpatado() {
        int empatados = prefs.getInt(KEY_EMPATADOS, 0);
        prefs.edit().putInt(KEY_EMPATADOS, empatados + 1).apply();
    }

    public int obtenerGanados() {
        return prefs.getInt(KEY_GANADOS, 0);
    }

    public int obtenerPerdidos() {
        return prefs.getInt(KEY_PERDIDOS, 0);
    }

    public int obtenerEmpatados() {
        return prefs.getInt(KEY_EMPATADOS, 0);
    }

    public void resetearTodo() {
        prefs.edit().clear().apply();
    }
}
