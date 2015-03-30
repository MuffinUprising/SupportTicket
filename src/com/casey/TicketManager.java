package com.casey;

import sun.awt.image.ImageWatched;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

public class TicketManager {
    // LinkedLists for ticketQueue and resolvedTickets
    LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();
    LinkedList<String> resolvedTickets = new LinkedList<String>();

    public static void main(String[] args) {
        SupportTicketGUI supportTicketGUI = new SupportTicketGUI();

    }
//        openTicketReader(ticketQueue);

    //getters
    public LinkedList<String> getResolvedTicketList(){ return this.resolvedTickets; }

    // add ticket method
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

        for (int x = 0; x < compareList.size(); x++) {      //Use a regular loop so we know which element we are looking at

            //if newTicket is higher or equal priority than the this element, add it in front of this one and return
            if (newTicketPriority >= compareList.get(x).getPriority()) {
                compareList.add(x, newTicket);
                return;
            }
        }
        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        compareList.addLast(newTicket);
        this.ticketQueue = compareList;
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
                for (String t : resolvedTickets) {
                    resolvedTicks.write(t + "\n");
                }
                resolvedTicks.close();
                System.out.println("Resolved Tickets successfully written");
            }
            else{
                JOptionPane.showMessageDialog(null, "There are no resolved tickets");
            }

        }catch (IOException ioe) {
            System.out.println("An IO Exception has occurred");
        }
    }
    // open ticket file reader
    protected void openTicketReader () {
        try{
            // new fileReader
            BufferedReader openTicketImport = new BufferedReader(new FileReader("open_tickets.txt"));
            String line = openTicketImport.readLine();
            //while there are lines, split
            while (line != null) {
                String ticketToString = openTicketImport.toString();
                String[] splitString = ticketToString.split("\\s");
                for(Object word : splitString) {
//                    System.out.println(word);
                    //TODO figure out how to reimplement the data back into ticket form.
                }
            }
            openTicketImport.close();
        }catch (IOException ioe) {
            System.out.println("An IO Exception has occurred");
        }
    }
}
