package com.example.bookit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> ticketList;

    public TicketAdapter(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order, parent, false); // Change to your CardView layout
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.headerTextView.setText(ticket.getTitle());
        holder.idTextView.setText(String.valueOf(ticket.getTicketId()));
        holder.showTypeTextView.setText(ticket.getShowType()); 
        holder.locationTextView.setText(ticket.getVenue() + ", " + ticket.getCity());
        holder.bookedDatetimeTextView.setText(ticket.getBookedDateTime());
        holder.totalAmountTextView.setText(ticket.getAmount());
        holder.seatTextView.setText(ticket.getSeatsCount() + " " + ticket.getSeatType());
        holder.showDateTime.setText(ticket.getShowTime() + ", " + ticket.getShowDate());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView headerTextView, idTextView, showTypeTextView, locationTextView, bookedDatetimeTextView, totalAmountTextView, seatTextView, showDateTime;

        TicketViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            headerTextView = itemView.findViewById(R.id.header1);
            idTextView = itemView.findViewById(R.id.id);
            showTypeTextView = itemView.findViewById(R.id.showType);
            locationTextView = itemView.findViewById(R.id.location);
            bookedDatetimeTextView = itemView.findViewById(R.id.bookedDatetime);
            totalAmountTextView = itemView.findViewById(R.id.totalAmount);
            seatTextView = itemView.findViewById(R.id.seat);
            showDateTime = itemView.findViewById(R.id.datetime);

        }
    }
}
