package my.e.soilmoisturetemperarureproject.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import my.e.soilmoisturetemperarureproject.MainActivity;
import my.e.soilmoisturetemperarureproject.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnReset;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etEmail = findViewById(R.id.et_reset_email);
        btnReset = findViewById(R.id.btn_reset);
        progressBar = findViewById(R.id.prg_bar_reset);

        auth =  FirebaseAuth.getInstance();
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String txtEmail = etEmail.getText().toString();
                resetUserPassword(txtEmail);
            }
        });
    }

    private void resetUserPassword(String email) {
       auth.sendPasswordResetEmail(email)
               .addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       progressBar.setVisibility(View.GONE);
                       if(task.isSuccessful()) {
                           Toast.makeText(ResetPasswordActivity.this, "Password send to your email",
                                   Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(ResetPasswordActivity.this,
                                   task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });
    }
}