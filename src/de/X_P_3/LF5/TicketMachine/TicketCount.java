package de.X_P_3.LF5.TicketMachine;

import java.util.ArrayList;

public class TicketCount {
    public Ticket ticket;
    public int count = 1;

    public static void addCount(Ticket ticket, ArrayList<TicketCount> ticketCountList) {
        for (TicketCount Item : ticketCountList) {
            if (Item.ticket == ticket) {
                Item.count++;
                return;
            }
        }
        TicketCount ticketCount = new TicketCount();
        ticketCount.ticket = ticket;
        ticketCountList.add(ticketCount);
    }
}
