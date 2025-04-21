package com.example.bocanegrakleyver_da1_tateti_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextName = findViewById(R.id.editTextName);
        RadioGroup radioGroup = findViewById(R.id.radioGroupSymbol);
        RadioButton radioX = findViewById(R.id.radioX);
        RadioButton radioO = findViewById(R.id.radioO);
        Button buttonStart = findViewById(R.id.buttonStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextName.getText().toString().trim();
                if (nombre.isEmpty()) {
                    nombre = "Extraño";
                }

                String simbolo;
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == R.id.radioO) {
                    simbolo = "O";
                } else {
                    simbolo = "X"; // por defecto
                }

                // Intent para ir a GameActivity
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("nombreJugador", nombre);
                intent.putExtra("simboloJugador", simbolo);
                startActivity(intent);
            }
        });
    }
}
