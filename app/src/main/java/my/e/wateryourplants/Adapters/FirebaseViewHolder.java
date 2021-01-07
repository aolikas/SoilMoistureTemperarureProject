package my.e.wateryourplants.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.e.wateryourplants.R;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView sensorName;
    public TextView sensorDescription;
    public TextView sensorMoistureCondition;
    public View view;


    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        sensorName = itemView.findViewById(R.id.item_data_sensor_name);
        sensorDescription = itemView.findViewById(R.id.item_data_sensor_description);
        sensorMoistureCondition = itemView.findViewById(R.id.item_data_sensor_moisture_condition);
        view = itemView;
    }
}
