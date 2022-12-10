package com.example.upisaver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton add;
    boolean b = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.addTrans);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity();
                if(b) {
                   // add.setImageResource(R.drawable.img_1);
                    add.setBackgroundResource(R.drawable.img_1);
                    b=false;
                }else{
                    add.setBackgroundResource(R.drawable.img);
                    b=true;
                }
            }
        });
    }
}