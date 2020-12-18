package my.e.soilmoisturetemperarureproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import my.e.soilmoisturetemperarureproject.Model.UserData;
import my.e.soilmoisturetemperarureproject.R;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<UserData, RecyclerViewAdapter.myViewHolder> {

    public RecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<UserData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i, @NonNull UserData userData) {

        myViewHolder.sensorName.setText(userData.getUserSensorName());
        myViewHolder.sensorDescription.setText(userData.getUserSensorDescription());
        myViewHolder.sensorMoistureCondition.setText(userData.getUserSensorMoistureCondition());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView sensorName;
        private TextView sensorDescription;
        private TextView sensorMoistureCondition;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorName = itemView.findViewById(R.id.item_data_sensor_name);
            sensorDescription = itemView.findViewById(R.id.item_data_sensor_description);
            sensorMoistureCondition = itemView.findViewById(R.id.item_data_sensor_moisture_condition);
        }
    }
}
