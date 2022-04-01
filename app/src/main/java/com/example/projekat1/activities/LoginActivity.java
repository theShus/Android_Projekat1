package com.example.projekat1.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.projekat1.R;
import com.example.projekat1.fragments.ProfileFragment;

public class LoginActivity extends AppCompatActivity {


    EditText username;
    EditText password;
    Button loginBtn;
    public static final String REG_NAME_KEY = "usernameKey";
//    public static final String REG_PASS_KEY = "passwordKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        initview();
        initListeners();
    }

    private void initview(){
        username = findViewById(R.id.logUsername);
        password = findViewById(R.id.logPass);
        loginBtn = findViewById(R.id.loginBtn);
    }
    private void initListeners(){

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
//        String usernameStored = sharedPreferences.getString(REG_NAME_KEY, "");
//        String passwordStored = sharedPreferences.getString(REG_PASS_KEY, "");


        loginBtn.setOnClickListener(v ->{
            String userInput = username.getText().toString();
            String passInput = password.getText().toString();

            System.out.println( userInput + " - " + passInput);

//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////        transaction.replace(R.id.mainFrag, new FirstFragment());
//            transaction.add(R.id.mainFrag, new ProfileFragment());
//            transaction.commit();

            finish();

//            if (!userInput.equals("") && !passInput.equals("")){
//                sharedPreferences
//                        .edit()
//                        .putString(REG_NAME_KEY, userInput)
//                        .apply();


//            Intent intent = new Intent(this, ScreenActivity.class);
//            intent.putExtra(REG_NAME_KEY, userInput);
//            startActivity(intent);
//            finish();



//            }
//            else {
//                Toast.makeText(this, "Empty login parameter.", Toast.LENGTH_SHORT).show();
//            }
        });



    }
}