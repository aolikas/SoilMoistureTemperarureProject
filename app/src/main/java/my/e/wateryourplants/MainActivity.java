package my.e.wateryourplants;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

import android.os.Build;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import my.e.wateryourplants.Adapters.FirebaseViewHolder;
import my.e.wateryourplants.Auth.StartActivity;
import my.e.wateryourplants.Dialogs.SensorCreateDialog;
import my.e.wateryourplants.Model.UserData;
import my.e.wateryourplants.ShowDetails.SensorDataActivity;
import my.e.wateryourplants.ShowDetails.UserAccountActivity;

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private RecyclerView mRecyclerView;

    private FirebaseRecyclerAdapter<UserData, FirebaseViewHolder> mAdapter;

    private String condition;


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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String userId = mUser.getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("userSensors");
      //  readAllSensors();

        FirebaseRecyclerOptions<UserData> mOptions = new FirebaseRecyclerOptions.Builder<UserData>()
                .setQuery(mRef, UserData.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<UserData, FirebaseViewHolder>(mOptions) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseViewHolder firebaseViewHolder, int i, @NonNull UserData userData) {
                String key = getRef(i).getKey();
                firebaseViewHolder.sensorName.setText("" + userData.getUserSensorName());
                firebaseViewHolder.sensorDescription.setText(userData.getUserSensorDescription());
                firebaseViewHolder.sensorMoistureCondition.setText(userData.getUserSensorMoistureCondition());
                condition = userData.getUserSensorMoistureCondition();

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
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,
                        parent, false);
                return new FirebaseViewHolder(view);
            }

        };

        mAdapter.notifyDataSetChanged();
      //  mAdapter.startListening();
        mRecyclerView.setAdapter(mAdapter);

        addNotification();

    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.main_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     //   mRecyclerView.setHasFixedSize(true);
    }



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

    private void createNotification() {
        FirebaseMessaging.getInstance().subscribeToTopic("some")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if(!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });
    }

    private void notification(){

        String channelId = "notification";
        String title = "Watering App";
        String msg = "It's time for water";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "n", channelId, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setContentTitle(title)
                .setContentText(msg)
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setAutoCancel(true);


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(101, builder.build());
    }

    private void addNotification() {
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            notification();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAdapter != null) {
            mAdapter.stopListening();
        }
    }
}