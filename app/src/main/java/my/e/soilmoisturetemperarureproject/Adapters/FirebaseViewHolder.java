package my.e.soilmoisturetemperarureproject.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.e.soilmoisturetemperarureproject.R;

public class FirebaseViewHolder extends RecyclerView.ViewHolder {

    public TextView sensorName;
    public TextView sensorDescription;


    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        sensorName = itemView.findViewById(R.id.item_data_sensor_name);
        sensorDescription = itemView.findViewById(R.id.item_data_sensor_description);
    }
}
