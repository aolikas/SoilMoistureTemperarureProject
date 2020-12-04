package my.e.soilmoisturetemperarureproject.Auth;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import my.e.soilmoisturetemperarureproject.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private TextView txtForgotPassword, txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogin = findViewById(R.id.btn_start_login);
        txtForgotPassword = findViewById(R.id.txt_start_forgot_password);
        txtRegister = findViewById(R.id.txt_start_register);

        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_login:
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
                break;
            case R.id.txt_start_register:
                startActivity(new Intent(StartActivity.this, RegistrationActivity.class));
                break;
            case R.id.txt_start_forgot_password:
                startActivity(new Intent(StartActivity.this, ResetPasswordActivity.class));
                break;
        }
    }
}