package my.e.soilmoisturetemperarureproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

import my.e.soilmoisturetemperarureproject.Model.Data;
import my.e.soilmoisturetemperarureproject.Model.SensorsData;
import my.e.soilmoisturetemperarureproject.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Data> sensorsList;
    private OnItemClickListener listener;


    public RecyclerViewAdapter(Context mContext, ArrayList<Data> data) {
        this.mContext = mContext;
        this.sensorsList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.name.setText(String.valueOf(sensorsList.get(position)));

        holder.condition.setText(sensorsList.get(position).getHumidityCondition());
        holder.description.setText(sensorsList.get(position).getSensorDescription());
        holder.temperature.setText(String.valueOf(sensorsList.get(position).getTemperature()));

        //holder..setText(String.valueOf(sensorsList.get(position).getHumidity()));
    }

    @Override
    public int getItemCount() {
        return sensorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         private final TextView name;
         private final TextView condition;
         private final TextView description;
         private final TextView temperature;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            condition = itemView.findViewById(R.id.item_humidity);
            temperature = itemView.findViewById(R.id.item_temperature);
            description = itemView.findViewById(R.id.item_description);
        }
        }

        public interface OnItemClickListener {
        void onItemClick(DataSnapshot snapshot, int position);
        }
        public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
        }
    }
