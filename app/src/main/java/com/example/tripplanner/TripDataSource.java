package com.example.tripplanner;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TripDataSource {

    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();

    public TripDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences("trip_prefs", Context.MODE_PRIVATE);
    }

    public void saveTrips(List<Trip> trips) {
        String json = gson.toJson(trips);
        sharedPreferences.edit().putString("trips", json).apply();
    }

    public List<Trip> getTrips() {
        String json = sharedPreferences.getString("trips", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Trip>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>();
        }
    }

    public void addTrip(Trip trip) {
        List<Trip> trips = getTrips();
        trips.add(trip);
        saveTrips(trips);
    }

    public void deleteTrip(Trip trip) {
        List<Trip> trips = getTrips();
        Trip tripToRemove = null;
        for (Trip t : trips) {
            if (t.getId() == trip.getId()) {
                tripToRemove = t;
                break;
            }
        }
        if (tripToRemove != null) {
            trips.remove(tripToRemove);
            saveTrips(trips);
        }
    }
}
