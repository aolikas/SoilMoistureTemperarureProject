package my.e.soilmoisturetemperarureproject;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import my.e.soilmoisturetemperarureproject.Adapters.RecyclerViewAdapter;
import my.e.soilmoisturetemperarureproject.Auth.StartActivity;
import my.e.soilmoisturetemperarureproject.Model.SensorsData;

import static my.e.soilmoisturetemperarureproject.AppNotification.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private DatabaseReference mRef;
    private ArrayList<SensorsData> mSensorList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.main_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);

        mRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user =auth.getCurrentUser();
        mSensorList = new ArrayList<>();
        notificationManager = NotificationManagerCompat.from(this);

        clearAll();
        getDataFromFirebase();

    }

    private void getDataFromFirebase() {

        Query query = mRef.child("SoilSensors");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(mSensorList != null) {
                    mSensorList.clear();
                }
                mSensorList = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SensorsData  sensorsData = new SensorsData();
                    sensorsData.setDate(dataSnapshot.child("Date").getValue().toString());
                    sensorsData.setHumidityCondition(dataSnapshot.child("Condition").getValue().toString());
                    mSensorList.add(sensorsData);
                    findResultAndSendNotification("Dry", mSensorList);
                }
                mAdapter = new RecyclerViewAdapter(getApplicationContext(), mSensorList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   public void findResultAndSendNotification(String search, List<SensorsData> list) {
        for(SensorsData data : list) {
            if(data.getHumidityCondition().contains(search)) {
                Toast.makeText(this, "HeYYYYY", Toast.LENGTH_SHORT).show();

                Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.plant_icon2)
                        .setContentTitle("Title")
                        .setContentText("Watering")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(1,notification);


            }

        }
   }



    private void clearAll() {
        if(mSensorList != null) {
            mSensorList.clear();
            if(mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }

        mSensorList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:

                auth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = (new Intent(MainActivity.this, StartActivity.class));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.delete_account:

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are your sure?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this,
                                            "Account Deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent = (new Intent(MainActivity.this, StartActivity.class));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this,
                                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;

                default:
                return super.onOptionsItemSelected(item);
        }
    }
}