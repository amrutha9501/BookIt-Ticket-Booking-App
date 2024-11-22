package com.example.bookit;

public class ShowTiming {
    private int timingId;
    private int showId;
    private int venueId;
    private String showCity;
    private String showVenue;
    private String showDate;
    private String showTime;
    private String title;
    private int vipSeats;
    private int platinumSeats;
    private int goldSeats;
    private int silverSeats;
    private int specialSeats;

    // Default constructor
    public ShowTiming() {
        this.vipSeats = 50;
        this.platinumSeats = 50;
        this.goldSeats = 50;
        this.silverSeats = 50;
        this.specialSeats = 50;
    }

    // Parameterized constructor
    public ShowTiming(int showId, int venueId, String showDate, String showTime) {
        this.showId = showId;
        this.venueId = venueId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.title = "title";
        this.vipSeats = 50;
        this.platinumSeats = 50;
        this.goldSeats = 50;
        this.silverSeats = 50;
        this.specialSeats = 50;
    }

    // Getters and Setters

    public int getTimingId() {
        return timingId;
    }

    public void setTimingId(int timingId) {
        this.timingId = timingId;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public String getShowTitle() {
        return title;
    }

    public void setShowTitle(String title) {
        this.title = title;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public int getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(int vipSeats) {
        this.vipSeats = vipSeats;
    }

    public int getPlatinumSeats() {
        return platinumSeats;
    }

    public void setPlatinumSeats(int platinumSeats) {
        this.platinumSeats = platinumSeats;
    }

    public int getGoldSeats() {
        return goldSeats;
    }

    public void setGoldSeats(int goldSeats) {
        this.goldSeats = goldSeats;
    }

    public int getSilverSeats() {
        return silverSeats;
    }

    public void setSilverSeats(int silverSeats) {
        this.silverSeats = silverSeats;
    }

    public int getSpecialSeats() {
        return specialSeats;
    }

    public void setSpecialSeats(int specialSeats) {
        this.specialSeats = specialSeats;
    }


    public String getShowCity() {
        return showCity;
    }

    public void setShowCity(String showCity) {
        this.showCity = showCity;
    }

    public String getShowVenue() {
        return showVenue;
    }

    public void setShowVenue(String showVenue) {
        this.showVenue = showVenue;
    }


    @Override
    public String toString() {
        return "ShowTiming{" +
                "timingId=" + timingId +
                ", showId=" + showId +
                ", venueId=" + venueId +
                ", showDate='" + showDate + '\'' +
                ", showTime='" + showTime + '\'' +
                ", vipSeats=" + vipSeats +
                ", platinumSeats=" + platinumSeats +
                ", goldSeats=" + goldSeats +
                ", silverSeats=" + silverSeats +
                ", specialSeats=" + specialSeats +
                '}';
    }
}
