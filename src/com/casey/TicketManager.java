package com.casey;

import sun.awt.image.ImageWatched;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class TicketManager {
    // LinkedLists for ticketQueue and resolvedTickets
    LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();
    LinkedList<Ticket> resolvedTickets = new LinkedList<Ticket>();

    public static void main(String[] args) {
        SupportTicketGUI supportTicketGUI = new SupportTicketGUI();

    }

    // add ticket by priority
    protected void addTicketInPriorityOrder(Ticket newTicket) {

        LinkedList<Ticket> compareList = this.ticketQueue;
        //Logic: assume the list is either empty or sorted
        if (compareList.size() == 0) {      //Special case - if list is empty, add ticket and return
            compareList.add(newTicket);
            return;
        }
        //Tickets with the HIGHEST priority number go at the front of the list (e.g. 5 = server on fire)
        //Tickets with the LOWEST priority value of their priority number go at the end

        int newTicketPriority = newTicket.getPriority();

        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        compareList.add(newTicket);
        Collections.sort(compareList);
        this.ticketQueue = compareList;
    }

    // add resolved tickets
    protected void addResolvedTicket(Ticket resolvedTicket) {
        this.resolvedTickets.add(resolvedTicket);

    }
    // open ticket writer
    protected void openTicketWriter () {


        try {
            if (!ticketQueue.isEmpty()) {
                //bufferedWriter for new file
                BufferedWriter openTickets = new BufferedWriter(new FileWriter("open_tickets.txt"));
                //for ticket in queue, write line to file
                for (Ticket t : ticketQueue) {
                    openTickets.write(t.toString() + "\n");
                }
                openTickets.close();
                System.out.println("Open tickets file successfully written");
            }
            else {
                //pop-up dialog box
                JOptionPane.showMessageDialog(null, "There are no open tickets");
            }

        } catch (IOException ioe) {
            System.out.println("An IO Exception has occurred");
        }
    }
    // resolved ticker writer
    protected void resolvedTicketWriter () {
        try{
            if (!resolvedTickets.isEmpty()){
                // new date and format
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                // new file for name format
                File file = new File("resolved_tickets_as_of_" + dateFormat.format(date) + ".txt" );
                BufferedWriter resolvedTicks = new BufferedWriter (new FileWriter(file));
                //for ticket in resolved tickets, write to file with new line
                for (Ticket t : resolvedTickets) {
                    resolvedTicks.write(t + "\n");
                }
                resolvedTicks.close();
                System.out.println("Resolved Tickets successfully written");
            }
            else{
                //pop-up dialog box
                JOptionPane.showMessageDialog(null, "There are no resolved tickets");
            }

        }catch (IOException ioe) {
            System.out.println("An IO Exception has occurred");
        }
    }

}
