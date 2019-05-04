package com.example.semana1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI()
    {
        final TextView wTextView = (TextView) findViewById(R.id.text_view);
        wTextView.setText(R.string.app_name);

        Button wButtom = (Button) findViewById(R.id.button_cambiar);
        wButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui va el codigo
                EditText wEditTExt = (EditText) findViewById(R.id.edit_text);
                String wNombre = wEditTExt.getText().toString();
                wTextView.setText(wNombre);
                Toast.makeText(getApplicationContext(),"Hola",Toast.LENGTH_SHORT).show();
            }
        });

        Button wNButtom = (Button) findViewById(R.id.button_nuevo);
        wNButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui va el codigo
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), NewActivity.class);
                startActivity(intent);
            }
        });
    }

}
