package my.e.soilmoisturetemperarureproject.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import my.e.soilmoisturetemperarureproject.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnRegistration;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etEmail = findViewById(R.id.et_reg_email);
        etPassword = findViewById(R.id.et_reg_password);
        btnRegistration = findViewById(R.id.btn_registration);

        auth = FirebaseAuth.getInstance();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = etEmail.getText().toString();
                String txtPassword = etPassword.getText().toString();

                if(TextUtils.isEmpty(txtEmail) || TextUtils.isEmpty(txtPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Please fill all", Toast.LENGTH_SHORT).show();
                } else if(txtPassword.length() < 6) {
                    Toast.makeText(RegistrationActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(txtEmail, txtPassword);
                }
            }

            private void registerUser(String txtEmail, String txtPassword) {
                auth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(RegistrationActivity.this, "Registration is successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }

    public static class MainActivity extends AppCompatActivity {

        private Button btnLogout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

          /*  btnLogout = findViewById(R.id.btn_logout);

        //    btnLogout.setOnClickListener(new View.OnClickListener() {
          //      @Override
           //     public void onClick(View view) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                    finish();
                }
            });

            FirebaseDatabase.getInstance().getReference().child("Knowledge").child("Android").setValue("llla");
        }
    }*/
        }
    }
}