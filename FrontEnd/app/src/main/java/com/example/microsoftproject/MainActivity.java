package com.example.microsoftproject;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microsoftproject.entity.Person;
import com.example.microsoftproject.service.NetworkServiceClass;

public class MainActivity extends AppCompatActivity {

    Button Login_button;
    EditText LogInpassword , PhoneNo;
    TextView  sighUp;
    RecyclerView recyclerView;

    boolean flag ;

    SharedPreferences sharedPreferences_credentials;
    NetworkServiceClass networkService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences_credentials = getSharedPreferences(HomePage.SHARED_PREF_NAME , MODE_PRIVATE);
        checkLoginStatus();

        LogInpassword = findViewById(R.id.LogInpassword);
        Login_button = findViewById(R.id.Login_button);
        recyclerView = findViewById(R.id.recycler_view);
        sighUp = findViewById(R.id.SignUp_text);
        networkService = new NetworkServiceClass();
        PhoneNo =  findViewById(R.id.LogInPhoneNo);
        flag = false;





        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String sPhonNo = PhoneNo.getText().toString();

                if (PhoneNo.getText().length() == 0 || LogInpassword.getText().length() == 0) {
                    Toast.makeText(getApplicationContext(), "plz enter username & password", Toast.LENGTH_SHORT).show();
                }

                else {
                    String phone = PhoneNo.getText().toString();
                    String pass = LogInpassword.getText().toString();

                    networkService.login(getApplicationContext() , new Person(null , Long.valueOf(phone) , pass));

                }

            }
        });


        sighUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SighUpPage.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed () {


        if (flag == false) {
            flag = true;
            Toast.makeText(this, "Press again for exit", Toast.LENGTH_SHORT).show();
        } else super.onBackPressed();
    }

    void checkLoginStatus(){
        if (sharedPreferences_credentials.getString(HomePage.PHONE_KEY,null) != null){
            Intent intent = new Intent(getApplicationContext(),HomePage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
