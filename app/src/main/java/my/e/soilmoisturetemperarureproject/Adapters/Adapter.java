package my.e.soilmoisturetemperarureproject.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.e.soilmoisturetemperarureproject.R;

public class Adapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView sensorName;
        private TextView sensorDescription;
        private TextView sensorMoistureCondition;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorName = itemView.findViewById(R.id.item_data_sensor_name);
            sensorDescription = itemView.findViewById(R.id.item_data_sensor_description);
            sensorMoistureCondition = itemView.findViewById(R.id.item_data_sensor_moisture_condition);
        }
    }
}
