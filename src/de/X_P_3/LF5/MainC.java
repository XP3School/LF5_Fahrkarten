package de.X_P_3.LF5;

import de.X_P_3.LF5.TicketMachine.Ticket;
import de.X_P_3.LF5.TicketMachine.TicketMachine;
import de.X_P_3.money.MoneyElement;
import de.X_P_3.money.currencies.EURO;

public class MainC {
    public static TicketMachine ticketMachine = new TicketMachine();
    public static void main(String[] args){
        setup();
        if (selectTickets()){
            if (payMoney()){
                printTickets();
                if (checkForReturnMoney())
                    returnMoney();
                printFinalMessage();
            }
        }
    }

    private static void setup(){
        ticketMachine.setupBase(System.in);
        ticketMachine.tickets = Ticket.getNormalTickets();
        ticketMachine.moneyToReturn.setMoneyElements(EURO.Default);
        ticketMachine.paidMoney.setMoneyElements(EURO.Default);
    }

    private static boolean selectTickets(){
        return ticketMachine.selectTickets();
    }

    private static boolean payMoney(){
        return ticketMachine.payMoney();
    }

    private static void printTickets(){
        ticketMachine.waitingWhilePrintTickets();
    }

    private static boolean checkForReturnMoney(){
        return ticketMachine.paidMoney.value - ticketMachine.moneyToPay != 0;
    }

    private static void returnMoney(){
        ticketMachine.returnMoney();
    }

    private static void printFinalMessage(){
        ticketMachine.printFinalMessage();
    }
}
