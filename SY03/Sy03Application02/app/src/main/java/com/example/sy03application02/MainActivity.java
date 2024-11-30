package com.example.sy03application02;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showCustomDialog = findViewById(R.id.showCustomDialog);
        showCustomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomAlertDialog();
            }
        });
    }

    private void showCustomAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_custom_layout, (LinearLayout) findViewById(R.id.custom_dialog_container));
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(layout);

        final EditText username = layout.findViewById(R.id.username);
        final EditText password = layout.findViewById(R.id.password);
        Button cancel = layout.findViewById(R.id.cancel);
        Button signin = layout.findViewById(R.id.signin);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.create().cancel();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle sign in logic here
                builder.create().dismiss();
            }
        });

        builder.create().show();
    }
}