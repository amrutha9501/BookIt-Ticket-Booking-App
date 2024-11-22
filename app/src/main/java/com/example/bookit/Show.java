package com.example.bookit;


public class Show {
    private int timing_id;
    private String date;
    private String time;
    private String theatreName;
    private String city;
//    private String title;


    // Constructor
    public Show(int timing_id, String date, String time, String theatreName, String city) {
        this.timing_id = timing_id;
        this.date = date;
        this.time = time;
        this.theatreName = theatreName;
        this.city = city;
    }

    // Getters
    public int getTiming_id(){ return timing_id;}

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Show{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", theatreName='" + theatreName + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
