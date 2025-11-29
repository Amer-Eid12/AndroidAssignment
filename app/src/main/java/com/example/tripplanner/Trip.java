package com.example.tripplanner;

public class Trip {
    private int id;
    private String name;
    private String destination;
    private String date;
    private boolean requiresRiskAssessment;
    private boolean isAllInclusive;
    private String tripType; 
    private String description;
    private String activities;
    private String accommodations;
    private String transportation;
    private String companions;
    private boolean hasGone;

    public Trip(int id, String name, String destination, String date, boolean requiresRiskAssessment, boolean isAllInclusive, String tripType, String description, String activities, String accommodations, String transportation, String companions, boolean hasGone) {
        this.id = id;
        this.name = name;
        this.destination = destination;
        this.date = date;
        this.requiresRiskAssessment = requiresRiskAssessment;
        this.isAllInclusive = isAllInclusive;
        this.tripType = tripType;
        this.description = description;
        this.activities = activities;
        this.accommodations = accommodations;
        this.transportation = transportation;
        this.companions = companions;
        this.hasGone = hasGone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isRequiresRiskAssessment() {
        return requiresRiskAssessment;
    }

    public void setRequiresRiskAssessment(boolean requiresRiskAssessment) {
        this.requiresRiskAssessment = requiresRiskAssessment;
    }

    public boolean isAllInclusive() {
        return isAllInclusive;
    }

    public void setAllInclusive(boolean allInclusive) {
        isAllInclusive = allInclusive;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(String accommodations) {
        this.accommodations = accommodations;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getCompanions() {
        return companions;
    }

    public void setCompanions(String companions) {
        this.companions = companions;
    }

    public boolean hasGone() {
        return hasGone;
    }

    public void setHasGone(boolean hasGone) {
        this.hasGone = hasGone;
    }
}
