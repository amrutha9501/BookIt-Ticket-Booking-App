package com.example.bookit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "ticket_booking.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // login methods
    public int authenticateUser(String userId, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{userId, password});
        int user_id = -1;
//        boolean isValidUser = cursor.getCount() > 0;
        if (cursor != null && cursor.moveToFirst()) {
            user_id = cursor.getInt(cursor.getColumnIndex("user_id"));
        }
        cursor.close();
        db.close();


        return user_id;
    }


    // home page & event page methods
    public Event getEventByID(int showId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM shows WHERE show_id = ?", new String[]{String.valueOf(showId)});
        Event event = null;


        if (cursor != null && cursor.moveToFirst()) {
            // Get the data from the cursor
//            int showId = cursor.getInt(cursor.getColumnIndex("show_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            double rating = cursor.getDouble(cursor.getColumnIndex("rating"));
            String genre = cursor.getString(cursor.getColumnIndex("genre"));
            String language = cursor.getString(cursor.getColumnIndex("language"));
            String url = cursor.getString(cursor.getColumnIndex("image_url"));
            int category_id = cursor.getInt(cursor.getColumnIndex("category_id"));
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String cast = cursor.getString(cursor.getColumnIndex("cast"));
            String duration = cursor.getString(cursor.getColumnIndex("duration"));


            event = new Event(showId, title, rating, genre, language, url, category_id, description, cast, duration);


        }
        cursor.close();
        db.close();
        return event;
    }

    // Method to get user details by ID
    public User getUserDetails(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        // Query to select user based on user_id
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT * FROM users WHERE user_id = ?", new String[]{String.valueOf(id)});

            // Iterate through the result and fetch user details
            if (cursor.moveToFirst()) {
                // Use getColumnIndexOrThrow() for better safety against column errors
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password")); // Password field
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));

                // Create a new User object with fetched data
                user = new User(id, username, email, password, phone);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error while fetching user details", e);
        } finally {
            if (cursor != null) {
                cursor.close(); // Ensure the cursor is closed after usage
            }
        }

        return user;
    }


    // history methods

    public List<Ticket> getTicketsByUserId(int userId) {
        List<Ticket> ticketList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();


        String query = "SELECT t.ticket_id, t.user_id, s.title, c.category_name, v.location, v.venue_name, " +
                "                strftime('%d-%m-%Y', st.show_date) AS show_date, " +
                "                strftime('%H:%M', st.show_time) AS show_time, " +
                "                t.seat_type, t.number_of_seats, t.PaymentAmount," +
                "                strftime('%d-%m-%Y %H:%M', t.BookingDateTime) AS BookingDateTime " +
                "                FROM tickets t        " +
                "                JOIN Show_Timings st ON t.timing_id = st.timing_id " +
                "                JOIN Shows s ON st.show_id = s.show_id " +
                "                JOIN Venues v ON st.venue_id = v.venue_id" +
                "                JOIN Categories c ON c.category_id = s.category_id" +
                "                WHERE t.user_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int ticketId = cursor.getInt(cursor.getColumnIndex("ticket_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String showType = cursor.getString(cursor.getColumnIndex("category_name"));
                String city = cursor.getString(cursor.getColumnIndex("location"));
                String venue = cursor.getString(cursor.getColumnIndex("venue_name"));

                // Retrieve formatted date and time directly
                String showDate = cursor.getString(cursor.getColumnIndex("show_date")); // Already formatted
                String showTime = cursor.getString(cursor.getColumnIndex("show_time")); // Already formatted

                int seatsCount = cursor.getInt(cursor.getColumnIndex("number_of_seats"));
                String seatType = cursor.getString(cursor.getColumnIndex("seat_type"));
                double amount = cursor.getDouble(cursor.getColumnIndex("PaymentAmount"));

                // Retrieve formatted BookingDateTime
                String bookedDateTime = cursor.getString(cursor.getColumnIndex("BookingDateTime")); // Already formatted

                // Create Ticket object and add to the list
                Ticket ticket = new Ticket(ticketId, userId, title, showType, city, venue,
                        showDate, showTime, seatsCount, seatType, amount, bookedDateTime);
                ticketList.add(ticket);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ticketList;
    }



    // eventlist methods

    public ArrayList<Event> getAllShows(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM shows WHERE category_id = ?", new String[]{String.valueOf(id)});
        ArrayList<Event> rowList = new ArrayList<>();


        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get the data from the cursor
                int showId = cursor.getInt(cursor.getColumnIndex("show_id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                double rating = cursor.getDouble(cursor.getColumnIndex("rating"));
                String genre = cursor.getString(cursor.getColumnIndex("genre"));
                String language = cursor.getString(cursor.getColumnIndex("language"));
                String url = cursor.getString(cursor.getColumnIndex("image_url"));
                // Create an object of Event class and add it to the list
                Event row = new Event(showId, title, rating, genre, language, url);
                row.setCategoryId(id);
                rowList.add(row);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return rowList;
    }

    public ArrayList<Event> filterData(ArrayList<String> selectedLocations,
                                       ArrayList<String> selectedGenres,
                                       ArrayList<String> selectedLanguages,
                                       String selectedRating,
                                       int category_id) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM shows WHERE 1=1 AND category_id = ?");

        if (!selectedLocations.isEmpty()) {
            queryBuilder.append(" AND (SELECT DISTINCT show_id FROM show_timings WHERE venue_id  IN(SELECT venue_id FROM venues WHERE location IN (").append(joinWithQuotes(selectedLocations)).append(")))");
        }

        if (!selectedGenres.isEmpty()) {
            queryBuilder.append(" AND genre IN (").append(joinWithQuotes(selectedGenres)).append(")");
        }

        if (!selectedLanguages.isEmpty()) {
            queryBuilder.append(" AND language IN (").append(joinWithQuotes(selectedLanguages)).append(")");
        }

        if (selectedRating != null && !selectedRating.isEmpty()) {
            selectedRating = selectedRating.substring(0, 1);
            queryBuilder.append(" AND rating >= ").append(selectedRating);

        }

        String query = queryBuilder.toString();
        Log.d("query", query);


        // Execute the query
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(category_id)});


        ArrayList<Event> rowList = new ArrayList<>();
        Log.d("Cursor Count", "Count: " + cursor.getCount());


        while (cursor.moveToNext()) {

            // Get the data from the cursor
            int showId = cursor.getInt(cursor.getColumnIndex("show_id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            double rating = cursor.getDouble(cursor.getColumnIndex("rating"));
            String genre = cursor.getString(cursor.getColumnIndex("genre"));
            String language = cursor.getString(cursor.getColumnIndex("language"));
            String url = cursor.getString(cursor.getColumnIndex("image_url"));
            // Create an object of Event class and add it to the list
            Event row = new Event(showId, title, rating, genre, language, url);
            rowList.add(row);
        }

        cursor.close();
        db.close();

        return rowList;

    }


    private String joinWithQuotes(ArrayList<String> values) {
        return values.stream()
                .map(value -> "'" + value.replace("'", "''") + "'") // Escape single quotes
                .collect(Collectors.joining(", "));
    }


    // showlist methods

    public String getShowName(int showId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT title FROM show_timings s1, shows s2 WHERE s1.show_id = ? AND s1.show_id = s2.show_id", new String[]{String.valueOf(showId)});
        String title = new String();
        if (cursor != null && cursor.moveToFirst()) {
            title = cursor.getString(cursor.getColumnIndex("title"));
        }

        return title;
    }

    public ArrayList<String> getCities(int showId) {
        ArrayList<String> cities = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT location FROM venues WHERE venue_id IN (select venue_id FROM show_timings WHERE show_id = ?)", new String[]{String.valueOf(showId)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String city = cursor.getString(cursor.getColumnIndex("location"));
                cities.add(city);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
        }
        return cities;
    }


    public ArrayList<Show> getShowTimings(int show_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT s.timing_id, v.venue_name, v.location, strftime('%d-%m-%Y', s.show_date) AS show_date, strftime('%H:%M', s.show_time) AS show_time " +
                "FROM show_timings s, venues v " +
                "WHERE s.show_id = ? AND s.venue_id = v.venue_id " +
                "ORDER BY s.show_date", new String[]{String.valueOf(show_id)});
        ArrayList<Show> rowList = new ArrayList<>();

        Log.d("Cursor Count", "Count: " + cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get the data from the cursor
                int timingId = cursor.getInt(cursor.getColumnIndex("timing_id"));
                String date = cursor.getString(cursor.getColumnIndex("show_date"));
                String time = cursor.getString(cursor.getColumnIndex("show_time"));
                String theatre = cursor.getString(cursor.getColumnIndex("venue_name"));
                String city = cursor.getString(cursor.getColumnIndex("location"));

                Show row = new Show(timingId, date, time, theatre, city);
                rowList.add(row);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return rowList;
    }

    public ArrayList<Show> getShowTimings(int show_id, ArrayList<String> selectedLocations) {
        StringBuilder queryBuilder = new StringBuilder("SELECT s.timing_id, v.venue_name, v.location, s.show_date, strftime('%H:%M', s.show_time) AS show_time " +
                "FROM show_timings s, venues v " +
                "WHERE 1=1 AND s.venue_id = v.venue_id AND s.show_id = ?");
        if (!selectedLocations.isEmpty()) {
            queryBuilder.append(" AND s.timing_id IN " +
                    "(SELECT timing_id " +
                    "FROM show_timings " +
                    "WHERE venue_id IN " +
                    "(SELECT venue_id " +
                    "FROM venues " +
                    "WHERE location IN (").append(joinWithQuotes(selectedLocations)).append(")))");
        }
        queryBuilder.append(" ORDER BY s.show_date");

        String query = queryBuilder.toString();
        Log.d("query", query);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(show_id)});
        ArrayList<Show> rowList = new ArrayList<>();

        Log.d("Cursor Count", "Count: " + cursor.getCount());

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Get the data from the cursor
                int timingId = cursor.getInt(cursor.getColumnIndex("timing_id"));
                String date = cursor.getString(cursor.getColumnIndex("show_date"));
                String time = cursor.getString(cursor.getColumnIndex("show_time"));
                String theatre = cursor.getString(cursor.getColumnIndex("venue_name"));
                String city = cursor.getString(cursor.getColumnIndex("location"));

                Show row = new Show(timingId, date, time, theatre, city);
                rowList.add(row);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
        }

        return rowList;
    }


    public ShowTiming getShowTimingByID(int showTimingID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM show_timings s1 " +
                "JOIN shows s2 ON s1.show_id = s2.show_id " +
                "JOIN venues v ON s1.venue_id = v.venue_id " +
                "WHERE s1.timing_id = ?;", new String[]{String.valueOf(showTimingID)});
        ShowTiming showTiming  = null;


        if (cursor != null && cursor.moveToFirst()) {
            // Get the data from the cursor

            int timingId = cursor.getInt(cursor.getColumnIndex("timing_id"));
            int showId = cursor.getInt(cursor.getColumnIndex("show_id"));
            int venueId = cursor.getInt(cursor.getColumnIndex("venue_id"));
            String showDate = cursor.getString(cursor.getColumnIndex("show_date"));
            String showTime = cursor.getString(cursor.getColumnIndex("show_time"));
            int vipSeats = cursor.getInt(cursor.getColumnIndex("VIP"));
            int platinumSeats = cursor.getInt(cursor.getColumnIndex("PLATINUM"));
            int goldSeats = cursor.getInt(cursor.getColumnIndex("GOLD"));
            int silverSeats = cursor.getInt(cursor.getColumnIndex("SILVER"));
            int specialSeats = cursor.getInt(cursor.getColumnIndex("SPEACIAL"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String city = cursor.getString(cursor.getColumnIndex("location"));
            String venue = cursor.getString(cursor.getColumnIndex("venue_name"));

            showTiming = new ShowTiming(showId, venueId, showDate, showTime);

            showTiming.setTimingId(timingId);
            showTiming.setVipSeats(vipSeats);
            showTiming.setPlatinumSeats(platinumSeats);
            showTiming.setGoldSeats(goldSeats);
            showTiming.setSilverSeats(silverSeats);
            showTiming.setSpecialSeats(specialSeats);
            showTiming.setShowTitle(title);
            showTiming.setShowCity(city);
            showTiming.setShowVenue(venue);

        }

        Log.d("", showTiming.toString());

        cursor.close();
        db.close();


        return showTiming;
    }


    public int confirmTicket(int userID, int timingID, String bookingDateTime, double paymentAmount, String seatType, int multiplier) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Define the values to insert
        ContentValues values = new ContentValues();
        values.put("user_id", userID);
        values.put("timing_id", timingID);
        values.put("BookingDateTime", bookingDateTime);
        values.put("Status", "Booked");  // Default status is 'Booked'
        values.put("PaymentAmount", paymentAmount);
        values.put("seat_type", seatType);
        values.put("number_of_seats", multiplier);

        // Insert the record into the 'tickets' table
        long result = db.insert("tickets", null, values);
        db.close();

        // Check if the insert was successful
        if (result == -1) {
            // Insert failed

            return -1;
        } else {
            // Insert successful
            subtractSeats(timingID, seatType, multiplier);
            return (int)result;
        }

    }



    public void subtractSeats(int timingID, String seatType, int numberOfSeats) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // First, fetch the current seat count for the given timingID and seatType
        String query = "SELECT " + seatType + " FROM show_timings WHERE timing_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(timingID)});

        if (cursor.moveToFirst()) {
            // Get the current number of seats
            int currentSeats = cursor.getInt(0);

            // Calculate the new seat count after subtraction
            int updatedSeats = currentSeats - numberOfSeats;

            // Update the seat count in the database
            ContentValues values = new ContentValues();
            values.put(seatType, updatedSeats);

            // Perform the update
            int result = db.update("show_timings", values, "timing_id = ?", new String[]{String.valueOf(timingID)});

//            if (result > 0) {
//                // Update successful
//                Toast.makeText(context, "Seats updated successfully!", Toast.LENGTH_SHORT).show();
//            } else {
//                // Update failed
//                Toast.makeText(context, "Failed to update seats.", Toast.LENGTH_SHORT).show();
//            }
        }

        // Close the cursor and database
        cursor.close();
        db.close();
    }





}
