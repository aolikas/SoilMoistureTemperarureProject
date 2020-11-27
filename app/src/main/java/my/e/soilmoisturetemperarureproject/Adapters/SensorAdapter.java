package my.e.soilmoisturetemperarureproject.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import my.e.soilmoisturetemperarureproject.Model.SensorsData;
import my.e.soilmoisturetemperarureproject.R;

public class SensorAdapter extends FirestoreRecyclerAdapter<SensorsData, SensorAdapter.SensorHolder> {


     /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public SensorAdapter(FirestoreRecyclerOptions<SensorsData> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(SensorHolder sensorHolder, int i, SensorsData sensorsData) {
        sensorHolder.date.setText(sensorsData.getDate());
        sensorHolder.condition.setText(sensorsData.getHumidityCondition());

    }

    @NonNull
    @Override
    public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data, parent, false);
        return  new SensorHolder(view);
    }

    class SensorHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView date;
        private TextView condition;
        private TextView temperature;

        public SensorHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            date = itemView.findViewById(R.id.item_date);
            condition = itemView.findViewById(R.id.item_humidity);
            temperature = itemView.findViewById(R.id.item_temperature);
        }

    }
}
