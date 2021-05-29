package com.example.week15;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputName;
    private Button loginBtn;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName=findViewById(R.id.inputName);
        loginBtn =findViewById(R.id.loginBtn);
        db = FirebaseDatabase.getInstance();
        loginBtn.setOnClickListener(
                (v)->{

                    if(inputName.getText().toString().trim().length()==0){

                        Toast.makeText(this,"Ponga un nombre valido", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        Intent intent = new Intent(this,ContactActivity.class);
                        intent.putExtra("name", inputName.getText().toString());
                        startActivity(intent);

                    }

                }
        );
    }
}