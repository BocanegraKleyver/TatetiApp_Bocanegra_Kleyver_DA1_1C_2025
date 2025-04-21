package com.example.bocanegrakleyver_da1_tateti_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ModeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection);

        Button btnVsCPU = findViewById(R.id.btn_vs_cpu);
        Button btnVsNetwork = findViewById(R.id.btn_vs_network);

        btnVsCPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir al modo local (contra máquina)
                Intent intent = new Intent(ModeSelectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnVsNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ir al modo en red (todavía no creado)
                Intent intent = new Intent(ModeSelectionActivity.this, NetworkGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
