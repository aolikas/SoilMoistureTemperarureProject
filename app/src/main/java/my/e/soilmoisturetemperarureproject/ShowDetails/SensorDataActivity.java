package my.e.soilmoisturetemperarureproject.ShowDetails;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import my.e.soilmoisturetemperarureproject.Model.UserData;
import my.e.soilmoisturetemperarureproject.R;

public class SensorDataActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtSensorId;
    private EditText etSensorName, etSensorDescription;
    private Button btnCopy, btnSave, btnDelete;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;


    private ClipboardManager mClipboardManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_data);
        initWidget();

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String userId = mUser.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(userId).child("userSensors");

        mClipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        getSensorData();

        btnCopy.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        copySensorId();
    }

    private void getSensorData() {
        String key = getIntent().getStringExtra("key");
        mRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    UserData userData = snapshot.getValue(UserData.class);
                    assert userData != null;
                    etSensorName.setText(userData.getUserSensorName());
                    etSensorDescription.setText(userData.getUserSensorDescription());
                    txtSensorId.setText(key);
                } else {
                    Toast.makeText(SensorDataActivity.this, "Sensor does not exist", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SensorDataActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void copySensorId() {

                String sensorId = txtSensorId.getText().toString();
                ClipData mClipData = ClipData.newPlainText("sensorId", sensorId);
                mClipboardManager.setPrimaryClip(mClipData);

        Toast.makeText(this,
                "Sensor Id copied to Clipboard.",
                Toast.LENGTH_SHORT).show();
    }

    private void saveNewSensorData() {

    }

    private void initWidget() {
        etSensorName = findViewById(R.id.sensor_data_name);
        etSensorDescription = findViewById(R.id.sensor_data_description);
        txtSensorId = findViewById(R.id.sensor_data_id);
        btnCopy = findViewById(R.id.sensor_data_btn_copy);
        btnSave = findViewById(R.id.sensor_data_btn_save);
        btnDelete = findViewById(R.id.sensor_data_btn_delete);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sensor_data_btn_copy:
                copySensorId();
            case R.id.sensor_data_btn_save:
            case R.id.sensor_data_btn_delete:
        }

    }
}
