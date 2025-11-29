package com.example.tripplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TripDataSource tripDataSource;
    private TripAdapter adapter;
    private List<Trip> trips;
    private TextView noTripsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tripDataSource = new TripDataSource(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noTripsTextView = findViewById(R.id.no_trips_text);

        trips = new ArrayList<>();
        adapter = new TripAdapter(trips, this);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                startActivity(intent);
            }
        });

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTrips(newText);
                return true;
            }
        });
    }

    private void filterTrips(String text) {
        List<Trip> filteredList = new ArrayList<>();
        for (Trip trip : trips) {
            if (trip.getName().toLowerCase().contains(text.toLowerCase()) || trip.getDestination().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(trip);
            }
        }
        adapter.updateTrips(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Trip> allTrips = tripDataSource.getTrips();
        trips.clear();
        for (Trip trip : allTrips) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date tripDate = sdf.parse(trip.getDate());
                Date currentDate = new Date();

                if (!trip.hasGone() && (tripDate.after(currentDate) || tripDate.equals(currentDate))) {
                    trips.add(trip);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        adapter.updateTrips(trips);

        if (trips.isEmpty()) {
            noTripsTextView.setVisibility(View.VISIBLE);
        } else {
            noTripsTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_history) {
            Intent intent = new Intent(this, TripHistoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
