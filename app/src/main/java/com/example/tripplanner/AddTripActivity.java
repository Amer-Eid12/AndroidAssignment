package com.example.tripplanner;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Calendar;
import java.util.List;

public class AddTripActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText destinationEditText;
    private EditText dateEditText;
    private CheckBox riskAssessmentCheckBox;
    private CheckBox allInclusiveCheckBox;
    private RadioGroup tripTypeRadioGroup;
    private EditText descriptionEditText;
    private EditText activitiesEditText;
    private EditText accommodationsEditText;
    private EditText transportationEditText;
    private EditText companionsEditText;
    private TripDataSource tripDataSource;
    private Trip tripToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tripDataSource = new TripDataSource(this);

        nameEditText = findViewById(R.id.trip_name);
        destinationEditText = findViewById(R.id.trip_destination);
        dateEditText = findViewById(R.id.trip_date);
        riskAssessmentCheckBox = findViewById(R.id.risk_assessment);
        allInclusiveCheckBox = findViewById(R.id.all_inclusive);
        tripTypeRadioGroup = findViewById(R.id.trip_type_group);
        descriptionEditText = findViewById(R.id.trip_description);
        activitiesEditText = findViewById(R.id.trip_activities);
        accommodationsEditText = findViewById(R.id.trip_accommodations);
        transportationEditText = findViewById(R.id.trip_transportation);
        companionsEditText = findViewById(R.id.trip_companions);

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        int tripId = getIntent().getIntExtra("trip_id", -1);
        if (tripId != -1) {
            getSupportActionBar().setTitle("Edit Trip");
            tripToEdit = getTripById(tripId);
            if (tripToEdit != null) {
                nameEditText.setText(tripToEdit.getName());
                destinationEditText.setText(tripToEdit.getDestination());
                dateEditText.setText(tripToEdit.getDate());
                riskAssessmentCheckBox.setChecked(tripToEdit.isRequiresRiskAssessment());
                allInclusiveCheckBox.setChecked(tripToEdit.isAllInclusive());
                if (tripToEdit.getTripType() != null) {
                    if (tripToEdit.getTripType().equals("Family")) {
                        tripTypeRadioGroup.check(R.id.family_trip);
                    } else {
                        tripTypeRadioGroup.check(R.id.friends_trip);
                    }
                }
                descriptionEditText.setText(tripToEdit.getDescription());
                activitiesEditText.setText(tripToEdit.getActivities());
                accommodationsEditText.setText(tripToEdit.getAccommodations());
                transportationEditText.setText(tripToEdit.getTransportation());
                companionsEditText.setText(tripToEdit.getCompanions());
            }
        } else {
            getSupportActionBar().setTitle("Add Trip");
        }

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrip();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private Trip getTripById(int id) {
        for (Trip trip : tripDataSource.getTrips()) {
            if (trip.getId() == id) {
                return trip;
            }
        }
        return null;
    }

    private void saveTrip() {
        String name = nameEditText.getText().toString();
        String destination = destinationEditText.getText().toString();
        String date = dateEditText.getText().toString();
        boolean requiresRiskAssessment = riskAssessmentCheckBox.isChecked();
        boolean isAllInclusive = allInclusiveCheckBox.isChecked();
        int selectedTripTypeId = tripTypeRadioGroup.getCheckedRadioButtonId();
        String tripType = "";
        if (selectedTripTypeId != -1) {
            RadioButton selectedTripTypeRadioButton = findViewById(selectedTripTypeId);
            tripType = selectedTripTypeRadioButton.getText().toString();
        }
        String description = descriptionEditText.getText().toString();
        String activities = activitiesEditText.getText().toString();
        String accommodations = accommodationsEditText.getText().toString();
        String transportation = transportationEditText.getText().toString();
        String companions = companionsEditText.getText().toString();

        if (tripToEdit != null) {
            List<Trip> allTrips = tripDataSource.getTrips();
            for (Trip trip : allTrips) {
                if (trip.getId() == tripToEdit.getId()) {
                    trip.setName(name);
                    trip.setDestination(destination);
                    trip.setDate(date);
                    trip.setRequiresRiskAssessment(requiresRiskAssessment);
                    trip.setAllInclusive(isAllInclusive);
                    trip.setTripType(tripType);
                    trip.setDescription(description);
                    trip.setActivities(activities);
                    trip.setAccommodations(accommodations);
                    trip.setTransportation(transportation);
                    trip.setCompanions(companions);
                    break;
                }
            }
            tripDataSource.saveTrips(allTrips);
        } else {
            int id = (int) (System.currentTimeMillis() / 1000);
            Trip trip = new Trip(id, name, destination, date, requiresRiskAssessment, isAllInclusive, tripType, description, activities, accommodations, transportation, companions, false);
            tripDataSource.addTrip(trip);
        }

        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
