package com.example.tic_tac_toegame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    Button friend, online;
    TextView title;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        friend=findViewById(R.id.Friend);
        online=findViewById(R.id.Online);
        title=findViewById(R.id.Title);

        title.animate().alpha(1f).setDuration(1000).start();


        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TicTacToe.class);
                intent.putExtra("message", "Hello from MainActivity!");
                startActivity(intent);
            }
        });


        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TicTacToe_Online.class);
                intent.putExtra("message", "Hello from MainActivity!");
                startActivity(intent);
            }
        });
    }
}