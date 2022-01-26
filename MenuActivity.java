package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    Button btn_start;
    Button hist;
    TextView user;
    ImageButton quit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btn_start=(Button)findViewById(R.id.stargame);
        hist=(Button)findViewById(R.id.consulter);
        user=(TextView)findViewById(R.id.textViewUser);
        quit=(ImageButton)findViewById(R.id.quit);
        String name = getIntent().getStringExtra("username");
        user.setText(name.toString());


        btn_start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("usernameIntent",user.getText().toString());
                startActivity(intent);
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent2 = new Intent(getApplicationContext(), ConsulterActivity.class);
                intent2.putExtra("usernameIntent",user.getText().toString());
                startActivity(intent2);
            }
        });



    }
}