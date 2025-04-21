package com.example.bocanegrakleyver_da1_tateti_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NetworkGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_game);

        TextView mensajeEstado = findViewById(R.id.textEstadoConexion);

        if (hayConexion()) {
            Toast.makeText(this, "Conectado a una red", Toast.LENGTH_SHORT).show();
            mensajeEstado.setText("✔ Estás conectado a una red.");
        } else {
            Toast.makeText(this, "No hay conexión disponible", Toast.LENGTH_LONG).show();
            mensajeEstado.setText("❌ No estás conectado a Internet.");
        }
    }

    private boolean hayConexion() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            );
        }
        return false;
    }
}
