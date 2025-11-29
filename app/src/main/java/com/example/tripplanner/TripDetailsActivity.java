package com.example.tripplanner;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.List;

public class TripDetailsActivity extends AppCompatActivity {

    private TripDataSource tripDataSource;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        tripDataSource = new TripDataSource(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int tripId = getIntent().getIntExtra("trip_id", -1);
        if (tripId != -1) {
            trip = getTripById(tripId);
            if (trip != null) {
                populateTripDetails();

                SwitchMaterial goneSwitch = findViewById(R.id.gone_switch);
                goneSwitch.setChecked(trip.hasGone());

                goneSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        List<Trip> allTrips = tripDataSource.getTrips();
                        for (Trip t : allTrips) {
                            if (t.getId() == trip.getId()) {
                                t.setHasGone(isChecked);
                                break;
                            }
                        }
                        tripDataSource.saveTrips(allTrips);
                    }
                });
            }
        }
    }

    private void populateTripDetails() {
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(trip.getName());

        TextView tripDestination = findViewById(R.id.trip_destination);
        tripDestination.setText(trip.getDestination());

        TextView tripDate = findViewById(R.id.trip_date);
        tripDate.setText(trip.getDate());

        TextView tripDescription = findViewById(R.id.trip_description);
        tripDescription.setText(trip.getDescription());

        CheckBox riskAssessment = findViewById(R.id.risk_assessment);
        riskAssessment.setChecked(trip.isRequiresRiskAssessment());

        CheckBox allInclusive = findViewById(R.id.all_inclusive);
        allInclusive.setChecked(trip.isAllInclusive());

        TextView tripType = findViewById(R.id.trip_type);
        tripType.setText("Trip Type: " + trip.getTripType());

        TextView tripActivities = findViewById(R.id.trip_activities);
        tripActivities.setText(trip.getActivities());

        TextView tripAccommodations = findViewById(R.id.trip_accommodations);
        tripAccommodations.setText(trip.getAccommodations());

        TextView tripTransportation = findViewById(R.id.trip_transportation);
        tripTransportation.setText(trip.getTransportation());

        TextView tripCompanions = findViewById(R.id.trip_companions);
        tripCompanions.setText(trip.getCompanions());
    }

    private Trip getTripById(int id) {
        for (Trip trip : tripDataSource.getTrips()) {
            if (trip.getId() == id) {
                return trip;
            }
        }
        return null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
