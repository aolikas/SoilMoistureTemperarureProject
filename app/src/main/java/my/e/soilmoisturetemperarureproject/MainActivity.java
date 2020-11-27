package my.e.soilmoisturetemperarureproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import my.e.soilmoisturetemperarureproject.Adapters.RecyclerViewAdapter;
import my.e.soilmoisturetemperarureproject.Adapters.SensorAdapter;
import my.e.soilmoisturetemperarureproject.Model.SensorsData;

import static my.e.soilmoisturetemperarureproject.AppNotification.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    private Button btnLogout;

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
        mSensorList = new ArrayList<>();
        notificationManager = NotificationManagerCompat.from(this);

        clearAll();
        getDataFromFirebase();




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

}