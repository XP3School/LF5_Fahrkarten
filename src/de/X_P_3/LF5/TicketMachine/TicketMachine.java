package de.X_P_3.LF5.TicketMachine;

import de.X_P_3.console.in.Input;
import de.X_P_3.console.in.ValidObjectTypes;
import de.X_P_3.console.in.iRepeatValue;
import de.X_P_3.money.MoneyCalculator;
import de.X_P_3.money.MoneyContainer;
import de.X_P_3.money.MoneyElement;
import de.X_P_3.variable.variable;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TicketMachine {
    public Boolean _breakF = Boolean.FALSE;
    public ArrayList<Ticket> tickets = new ArrayList<>();
    public ArrayList<TicketCount> boughtTickets = new ArrayList<>();
    public Boolean _breakE = Boolean.FALSE;
    public Console console = System.console();
    public int anzTickets = 0;
    public boolean consoleFound = console != null;
    public MoneyContainer paidMoney = new MoneyContainer(), moneyToReturn = new MoneyContainer();
    public double moneyToPay;
    public Scanner scanner;

    public void setupBase(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public boolean selectTickets(){
        int c1 = 1;
        StringBuilder stringBuilder = new StringBuilder();
        for (Ticket Item : tickets) {
            stringBuilder.append("[").append(c1).append("] ");
            stringBuilder.append(Item.Name).append(": ").append(Item.Coast).append("\n");
            c1++;
        }
        stringBuilder.append("enter ticket-id to add ticket to card");

        iRepeatValue isTicket = (Value, _break) ->
        {
            if (consoleFound)
                clearConsole();
            if (variable.isFormattedLike(Value, ValidObjectTypes.Int))
                return tickets.size() >= Integer.parseInt(Value) && Integer.parseInt(Value) != 0;
            else if (Objects.equals(Value, "-")) {
                _breakE = Boolean.TRUE;
                _break.set(true);
            }
            return false;
        };

        int ticket = -1;
        StringBuilder repMessage = new StringBuilder();
        if (consoleFound)
            clearConsole();
        while (!_breakE) {
            if (ticket != -1) {
                TicketCount.addCount(tickets.get(ticket), boughtTickets);
                repMessage = new StringBuilder();
                repMessage.append("bought tickets");
                for (TicketCount Item : boughtTickets) {
                    repMessage
                            .append("\n\t")
                            .append(Item.count)
                            .append(" × ")
                            .append(Item.ticket.Name);
                }
                anzTickets = 0;
                moneyToPay = 0;
                for (TicketCount Item : boughtTickets) {
                    anzTickets += Item.count;
                    moneyToPay += Item.ticket.Coast * Item.count;
                }
                repMessage
                        .append("\ncurrently you hav to pay ")
                        .append(moneyToPay)
                        .append(" Euro\nto pay enter \"-\"");
            }

            ticket = Integer.parseInt(Input.repeatInputWMessageVO(
                    isTicket,
                    scanner,
                    repMessage + "\nplease select a ticket from\n" + stringBuilder,
                    "please enter a ticket-id from the list",
                    System.out,
                    "0").replace(',', '.')) - 1;

        }

        if (anzTickets == 0) {
            System.out.println("cancel");
            return false;
        }
        return true;
    }

    public boolean payMoney() {
        double droppedCoin;
        while (paidMoney.value <= moneyToPay) {
            System.out.print("to pay: " + (moneyToPay - paidMoney.value) + " Euro");

            iRepeatValue isCoin = (Value, _break) -> {
                if (consoleFound)
                    clearConsole();
                if (variable.isFormattedLike(Value, ValidObjectTypes.Double))
                    return isCorrectCoin(Double.parseDouble(Value.replace(',', '.')));
                else if (Objects.equals(Value, "-")) {
                    _breakF = (true);
                    _break.set(true);
                }
                else if (Objects.equals(Value, "?")) {
                    System.out.println("accepted money: ");
                    for (MoneyElement Item: paidMoney.moneyElements) {
                        System.out.println("\t - " + Item.name);
                    }
                }
                return false;
            };
            droppedCoin = Double.parseDouble(Input.repeatInputWMessageVO(
                    isCoin,
                    scanner,
                    "enter (\"?\" to see the possible money)\n \"-\" to cancel: ",
                    "Please enter a correct Coin or banknote.",
                    System.out,
                    "0").replace(',', '.'));
            if (_breakF) {
                if (paidMoney.value == 0)
                    System.out.println("\ncancel");
                else {
                    System.out.println("\ncancel and return " + paidMoney + ": ");
                    for (MoneyElement Item : paidMoney.moneyElements) {
                        if (Item.count > 0)
                            System.out.println("\t" + Item.count + " × " + Item.name);
                    }
                }
                return false;
            } else if (isCorrectCoin(droppedCoin)) {
                MoneyElement moneyElement = MoneyElement.findElementByValue(paidMoney.moneyElements, droppedCoin);
                assert moneyElement != null;
                moneyElement.count+= 1;
                paidMoney.calculateValue();
            }
        }
        return true;
    }

    public void waitingWhilePrintTickets() {
        int textLength;
        for (int c = 1; c <= anzTickets; c++) {
            textLength = ("ticket " + c + "/" + anzTickets + " gets printed").length();
            if (consoleFound)
                clearConsole();
            System.out.print("\nticket " +
                    c +
                    "/" +
                    anzTickets +
                    " gets printed\n" +
                    "_".repeat(textLength) +
                    "\n" +
                    " ".repeat(textLength) +
                    "\n" +
                    "-".repeat(textLength) +
                    "\b".repeat(textLength * 2));
            for (int i = 0; i < textLength; i++) {
                System.out.print("=");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("\n\n");
        }
    }

    public void returnMoney() {
        moneyToReturn.addCalculated(MoneyCalculator.addMoneyAndCalculate(paidMoney.value - moneyToPay, moneyToReturn.moneyElements));
        System.out.println("you had to pay " + moneyToPay + " EURO\nyou inserted " + paidMoney.value + " EURO\nthe return amount of " + moneyToReturn.value + " EURO");

        if (moneyToReturn.getCountInRange(0.01, 2) > 0 && moneyToReturn.getCountInRange(1, 50) == 0)
            System.out.println("gets ejected in the following coins:");
        else if (moneyToReturn.getCountInRange(0.01, 2) == 0 && moneyToReturn.getCountInRange(1, 50) > 0)
            System.out.println("gets ejected in the following banknotes:");
        else if (moneyToReturn.getCountInRange(0.01, 2) > 0 && moneyToReturn.getCountInRange(1, 50) > 0)
            System.out.println("gets ejected in the following banknotes & coins:");

        for (MoneyElement Item : moneyToReturn.moneyElements) {
            if (Item.count > 0)
                System.out.println("\t" + Item.count + " × " + Item.name);
        }
    }

    public void printFinalMessage(){
        System.out.println("""
                                
                Don't forget to have your ticket devalued before you start your journey!
                We wish you a pleasant journey.""");
    }

    private void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Boolean isCorrectCoin(double Coin) {
        return MoneyElement.findElementByValue(paidMoney.moneyElements, Coin) != null;
    }
}

