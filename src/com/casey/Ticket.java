package com.casey;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Muffin on 3/2/2015.
 */
public class Ticket {

    private int priority;
    private String reporter; // Stores person or dept that reported issue
    private String description;
    private Date dateReported;
    private Date resolutionDate;
    private String resolution;

    public int getTicketID() { return ticketID; }
    protected int getPriority() { return priority; }

    //STATIC Counter - accessible to all Ticket objects
    //If any Ticket object modifies the counter, all Ticket objects will have the modified value
    // private - only ticket objects have access
    private static int staticTicketIDCounter = 1;
    // The ID for each ticket = instance variable. Each ticket will have it's own ticketID variable
    protected int ticketID;

    public Ticket(int priority, String reporter, String description, Date dateReported, Date resolutionDate, String resolution) {
        this.priority = priority;
        this.reporter = reporter;
        this.description = description;
        this.dateReported = dateReported;
        this.ticketID = staticTicketIDCounter;
        staticTicketIDCounter++;
        this.resolutionDate = resolutionDate;
        this.resolution = resolution;
    }

    //Called automatically if a Ticket object is an argument to System.out.println
    public String toString() {
        return(" ID = " + this.ticketID + " Issue: " + this.description + " Priority: " + this.priority + " Reported by: " + this.reporter + " Reported on: " + this.dateReported + " Resolution Date: " + this.resolutionDate + " Resolution: " + this.resolution);
    }

}

