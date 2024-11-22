package com.example.bookit;

public class Ticket {
    private String title;
    private int ticketId;               // from Tickets table
    private int userId;                 // from Tickets table
    private String showType;             // derived from Shows or Show_Timings (depending on your schema)
    private String city;                 // can be derived from location in Venues
    private String venue;                // from Venues table
    private String showDate;             // from Show_Timings table
    private String showTime;             // from Show_Timings table
    private int seatsCount;              // number of seats booked
    private String seatType;             // from Tickets table
    private double amount;                // PaymentAmount from Tickets table
    private String bookedDateTime;       // from Tickets table

    // Constructor
    public Ticket(int ticketId, int userId, String title, String showType, String city, String venue,
                  String showDate, String showTime, int seatsCount, String seatType,
                  double amount, String bookedDateTime) {
        this.ticketId = ticketId;
        this.title = title;
        this.userId = userId;
        this.showType = showType;
        this.city = city;
        this.venue = venue;
        this.showDate = showDate;
        this.showTime = showTime;
        this.seatsCount = seatsCount;
        this.seatType = seatType;
        this.amount = amount;
        this.bookedDateTime = bookedDateTime;
    }

    // Getters
    public int getTicketId() {
        return ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public String getShowType() {
        return showType;
    }

    public String getCity() {
        return city;
    }

    public String getTitle() {
        return title;
    }

    public String getVenue() {
        return venue;
    }

    public String getShowDate() {
        return showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getSeatsCount() {
        return String.valueOf(seatsCount);
    }

    public String getSeatType() {
        return seatType;
    }

    public String getAmount() {
        return String.valueOf(amount);
    }

    public String getBookedDateTime() {
        return bookedDateTime;
    }
}
