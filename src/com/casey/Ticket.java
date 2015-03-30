package com.casey;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Muffin on 3/2/2015.
 */
public class Ticket implements Comparable<Ticket>{

    private int priority;
    private String reporter; // Stores person or dept that reported issue
    private String description;
    private String dateReported;
    private String resolutionDate;
    private String resolution;

    public int getTicketID() { return ticketID; }
    protected int getPriority() { return priority; }

    //STATIC Counter - accessible to all Ticket objects
    //If any Ticket object modifies the counter, all Ticket objects will have the modified value
    // private - only ticket objects have access
    private static int staticTicketIDCounter = 1;
    // The ID for each ticket = instance variable. Each ticket will have it's own ticketID variable
    protected int ticketID;

    public Ticket(int priority, String reporter, String description, String dateReported, String resolutionDate, String resolution) {
        this.priority = priority;
        this.reporter = reporter;
        this.description = description;
        this.dateReported = dateReported;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
        this.resolutionDate = resolutionDate;
        this.resolution = resolution;
    }

    public int compareTo(Ticket anotherTicket) {
        if (this.priority < anotherTicket.getPriority()) {
            return 1;
        } else if (this.priority < anotherTicket.getPriority()) {
            return -1;
        } else {
            return 0;
        }
    }
    //Called automatically if a Ticket object is an argument to System.out.println
    public String toString() {
        return(" ID = " + this.ticketID + "\n Issue: " + this.description + "\n Priority: " + this.priority + "\n Reported by: " + this.reporter + "\n Reported on: " + this.dateReported + "\n Resolution Date: " + this.resolutionDate + "\n Resolution: " + this.resolution);
    }

}

