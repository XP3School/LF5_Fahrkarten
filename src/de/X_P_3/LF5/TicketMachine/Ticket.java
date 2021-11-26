package de.X_P_3.LF5.TicketMachine;

import java.util.ArrayList;

public class Ticket {
    public double Coast;
    public String Name;

    public Ticket(double _Coast, String _Name) {
        Coast = _Coast;
        Name = _Name;
    }

    public static ArrayList<Ticket> getNormalTickets(){
        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket(1.75, "one-time ticket"));
        tickets.add(new Ticket(4, "day ticket"));
        return tickets;
    }
}
