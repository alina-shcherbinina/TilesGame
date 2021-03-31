package com.example.tilesgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;
    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myView = findViewById(R.id.myTiles);
        button = findViewById(R.id.solve);

        //todo: simplify
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.callOnClick();
            }
        });
    }
}