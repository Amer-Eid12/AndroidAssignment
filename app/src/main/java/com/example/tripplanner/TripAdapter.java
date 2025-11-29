package com.example.tripplanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> trips;
    private Context context;
    private TripDataSource tripDataSource;

    public TripAdapter(List<Trip> trips, Context context) {
        this.trips = trips;
        this.context = context;
        this.tripDataSource = new TripDataSource(context);
    }

    public void updateTrips(List<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.nameTextView.setText(trip.getName());
        holder.destinationTextView.setText(trip.getDestination());
        holder.dateTextView.setText(trip.getDate());

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date tripDate = sdf.parse(trip.getDate());
            Date currentDate = new Date();

            if (trip.hasGone()) {
                holder.timeRemainingTextView.setText("Trip has ended");
                holder.statusTextView.setText("Completed");
                holder.statusTextView.setTextColor(Color.GREEN);
            } else {
                long diffInMillies = tripDate.getTime() - currentDate.getTime();
                long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (diffInDays < 0) {
                    holder.timeRemainingTextView.setText("Trip has ended");
                    holder.statusTextView.setText("Date has passed");
                    holder.statusTextView.setTextColor(Color.RED);
                } else {
                    holder.timeRemainingTextView.setText(diffInDays + " days remaining");
                    holder.statusTextView.setText("Pending");
                    holder.statusTextView.setTextColor(Color.BLUE);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            holder.timeRemainingTextView.setText("Invalid date format");
            holder.statusTextView.setText("");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TripDetailsActivity.class);
                intent.putExtra("trip_id", trip.getId());
                context.startActivity(intent);
            }
        });

        holder.optionsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v, trip, position);
            }
        });
    }

    private void showPopupMenu(View view, Trip trip, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.menu.trip_options_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_edit) {
                    Intent intent = new Intent(context, AddTripActivity.class);
                    intent.putExtra("trip_id", trip.getId());
                    context.startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    tripDataSource.deleteTrip(trip);
                    trips.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, trips.size());
                    return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView destinationTextView;
        TextView dateTextView;
        TextView timeRemainingTextView;
        TextView statusTextView;
        ImageView optionsMenu;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.trip_name);
            destinationTextView = itemView.findViewById(R.id.trip_destination);
            dateTextView = itemView.findViewById(R.id.trip_date);
            timeRemainingTextView = itemView.findViewById(R.id.time_remaining);
            statusTextView = itemView.findViewById(R.id.trip_status);
            optionsMenu = itemView.findViewById(R.id.options_menu);
        }
    }
}
