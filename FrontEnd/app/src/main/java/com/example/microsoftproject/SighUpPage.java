package com.example.microsoftproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsoftproject.entity.Person;
import com.example.microsoftproject.service.NetworkServiceClass;

public class SighUpPage extends AppCompatActivity {

    EditText RegisterUsername , RegisterPassword , RegisterPhoneNO;
    Button signUpButton;
    TextView Login;


    NetworkServiceClass networkService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigh_up_page);

        signUpButton =  findViewById(R.id.sign_button);
        RegisterPassword = findViewById(R.id.RegisterPassword);
        RegisterUsername = findViewById(R.id.RegisterUsername);
        RegisterPhoneNO =  findViewById(R.id.RegisterPhoneNO);
        Login = findViewById(R.id.LogIn_text);
        networkService = new NetworkServiceClass();





        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RegisterPhoneNO.getText().length() != 10 || RegisterPassword.getText().length() <= 6 || RegisterUsername.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "plz enter username & password", Toast.LENGTH_SHORT).show();
                } else {
                    String phone = RegisterPhoneNO.getText().toString();
                    String pass = RegisterPassword.getText().toString();
                    String Use = RegisterUsername.getText().toString();


                    networkService.userRegister(getApplicationContext(),new Person(Use , Long.valueOf(phone),pass));

                }
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}