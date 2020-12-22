package my.e.soilmoisturetemperarureproject;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import my.e.soilmoisturetemperarureproject.Adapters.FirebaseViewHolder;
import my.e.soilmoisturetemperarureproject.Auth.StartActivity;
import my.e.soilmoisturetemperarureproject.Dialogs.SensorCreateDialog;
import my.e.soilmoisturetemperarureproject.Model.UserData;
import my.e.soilmoisturetemperarureproject.ShowDetails.SensorDataActivity;
import my.e.soilmoisturetemperarureproject.ShowDetails.UserAccountActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerOptions<UserData> mOptions;
    private FirebaseRecyclerAdapter<UserData, FirebaseViewHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateSensorDialog();
            }
        });

        initRecyclerView();
        readAllSensors();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.main_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void readAllSensors() {
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String userId = mUser.getUid();

        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("userSensors");
        //mRef.keepSynced(true);

        mOptions = new FirebaseRecyclerOptions.Builder<UserData>().setQuery(mRef, UserData.class).build();
        mAdapter = new FirebaseRecyclerAdapter<UserData, FirebaseViewHolder>(mOptions) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull UserData userData) {

                String key = getRef(i).getKey();

                firebaseViewHolder.sensorName.setText(String.format("%s", userData.getUserSensorName()));
                firebaseViewHolder.sensorDescription.setText(userData.getUserSensorDescription());
                firebaseViewHolder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SensorDataActivity.class);
                        intent.putExtra("key", key);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
                return new FirebaseViewHolder(view);
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    /*
    public void findResultAndSendNotification(String search, List<SensorsData> list) {
        for (SensorsData data : list) {
            if (data.getHumidityCondition().contains(search)) {
                Toast.makeText(this, "HeYYYYY", Toast.LENGTH_SHORT).show();

                Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.plant_icon2)
                        .setContentTitle("Title")
                        .setContentText("Watering")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(1, notification);


            }

        }
    }
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                logOutUser();
                return true;

            case R.id.show_account_info:
                startActivity(new Intent(MainActivity.this, UserAccountActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCreateSensorDialog() {
        SensorCreateDialog sensorCreateDialog = new SensorCreateDialog();
        sensorCreateDialog.show(getSupportFragmentManager(), "sensor dialog");
    }


    private void logOutUser() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
        Intent intent = (new Intent(MainActivity.this, StartActivity.class));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.stopListening();
    }
}