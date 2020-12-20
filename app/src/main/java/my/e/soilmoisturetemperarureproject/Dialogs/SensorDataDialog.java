package my.e.soilmoisturetemperarureproject.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import my.e.soilmoisturetemperarureproject.R;

public class SensorDataDialog extends AppCompatActivity {

    TextView txtName, txtDescription, txtId;
    DatabaseReference mRef;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sensor_data);
        txtName = findViewById(R.id.dialog_sensor_data_name);
        txtDescription = findViewById(R.id.dialog_sensor_data_description);
        txtId = findViewById(R.id.dialog_sensor_data_id);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String userId = mUser.getUid();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("userSensors");

        String key = getIntent().getStringExtra("key");

        mRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userSensorName").getValue().toString();
                String description = snapshot.child("userSensorDescription").getValue().toString();

                txtId.setText(key);
                txtName.setText(name);
                txtDescription.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
