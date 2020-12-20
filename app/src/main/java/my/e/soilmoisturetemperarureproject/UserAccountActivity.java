package my.e.soilmoisturetemperarureproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import my.e.soilmoisturetemperarureproject.Model.UserData;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtUserId;
    private EditText etUserName, etUserEmail;
    private Button btnSave, btnDelete;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        initWidgets();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        String userId = user.getUid();
        mRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(userId);

        retrieveUserData(userId);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);



    }

    private void retrieveUserData(String id) {

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    UserData userData = snapshot.getValue(UserData.class) ;
                    assert userData != null;
                    etUserName.setText(userData.getUserName());
                    etUserEmail.setText(userData.getUserEmail());
                    txtUserId.setText(id);
                } else {
                    Toast.makeText(UserAccountActivity.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserAccountActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initWidgets() {
        txtUserId = findViewById(R.id.user_account_id);
        etUserName = findViewById(R.id.user_account_et_name);
        etUserEmail = findViewById(R.id.user_account_et_email);
        btnSave = findViewById(R.id.user_account_btn_save);
        btnDelete = findViewById(R.id.user_account_btn_delete_user);
    }

    @Override
    public void onClick(View view) {

    }
}