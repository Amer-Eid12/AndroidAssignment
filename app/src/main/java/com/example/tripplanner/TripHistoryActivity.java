package com.example.tripplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripHistoryActivity extends AppCompatActivity {

    private TripDataSource tripDataSource;
    private TripAdapter adapter;
    private TextView noHistoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trip History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tripDataSource = new TripDataSource(this);

        RecyclerView recyclerView = findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noHistoryTextView = findViewById(R.id.no_history_text);

        List<Trip> completedTrips = getCompletedTrips();
        adapter = new TripAdapter(completedTrips, this);
        recyclerView.setAdapter(adapter);

        if (completedTrips.isEmpty()) {
            noHistoryTextView.setVisibility(View.VISIBLE);
        } else {
            noHistoryTextView.setVisibility(View.GONE);
        }
    }

    private List<Trip> getCompletedTrips() {
        List<Trip> completedTrips = new ArrayList<>();
        for (Trip trip : tripDataSource.getTrips()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date tripDate = sdf.parse(trip.getDate());
                Date currentDate = new Date();

                if (trip.hasGone() || tripDate.before(currentDate)) {
                    completedTrips.add(trip);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return completedTrips;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
