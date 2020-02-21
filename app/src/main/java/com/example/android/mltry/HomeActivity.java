package com.example.android.mltry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void displaySolve(View v) {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(), Usersolve.class));
    }

    public void displayPlay(View v) {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(), Userplay.class));
    }
}
