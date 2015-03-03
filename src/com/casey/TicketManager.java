package com.casey;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TicketManager {

    public static void main(String[] args) {
        // LinkedLists for ticketQueue and resolvedTickets
        LinkedList<Ticket> ticketQueue = new LinkedList<Ticket>();
        LinkedList<String> resolvedTickets = new LinkedList<String>();
//        openTicketReader(ticketQueue);

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("1. Enter Ticket\n2. Delete by ID\n3. Delete by issue\n4. Search by name or issue\n5. Display All Tickets\n6. Quit");
            int task = Integer.parseInt(scan.nextLine());

            if (task == 1) {
                //Call addTickets, which will let us enter any number of new tickets
                addTickets(ticketQueue);
            } else if (task == 2) {
                //delete a ticket by ID
                deleteTicket(ticketQueue, resolvedTickets);
            } else if (task == 3){
                searchTicket(ticketQueue);
            } else if (task == 4) {
                searchTicket(ticketQueue);
            } else if (task == 6) {
                //Quit. future prototyping may want to save all tickets to a file.
                System.out.println("Saving open tickets to file..");
                openTicketWriter(ticketQueue);
                System.out.println("Saving resolved tickets to file..");
                resolvedTicketWriter(resolvedTickets);
                System.out.println("Quitting program");

                break;
            } else {
                //Default will br print all tickets
                printAllTickets(ticketQueue);
            }
        }
        scan.close();
    }

    protected static void deleteTicket(LinkedList<Ticket> ticketQueue, LinkedList<String> resolvedTickets) {
        printAllTickets(ticketQueue);

        if (ticketQueue.size() == 0) {      //no tickets!
            System.out.println("No tickets to delete!\n");
            return;
        }
        while (true) {
            Scanner deleteScanner = new Scanner(System.in);
            int deleteID = deleteScanner.nextInt();

            System.out.println("Enter the ID of ticket to delete: ");
            try {
                if (deleteID >= 0) {
                } else {
                    System.out.println("Please enter a positive number. ");
                    continue;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a positive number. ");
            }
            //Loop over all tickets. delete the one with this ticket ID
            boolean found = false;
            for (Ticket ticket : ticketQueue) {
                if (ticket.getTicketID() == deleteID) {
                    found = true;

                    Date resolutionDate = new Date();
                    System.out.println("Enter the resolution: ");
                    String resolution = deleteScanner.nextLine();
                    String resolvedTicket = (ticket + " Resolution Date: " + resolutionDate + " Resolution: " + resolution);
                    resolvedTickets.add(resolvedTicket);

                    ticketQueue.remove(ticket);
                    System.out.println(String.format("Ticket %d deleted", deleteID));

                    return;
                }
            }
            if (!found) {
                System.out.println("Ticket ID not found, no ticket deleted");
                return;
            }
            deleteScanner.close();
            printAllTickets(ticketQueue); // print updated list
        }
    }

    //Moved the adding ticket code to a method
    protected static void addTickets(LinkedList<Ticket> ticketQueue) {
        Scanner sc = new Scanner(System.in);
        // Ask for some ticket info, create tickets, store in ticketQueue
        String description;
        String reporter;
        // for now, all tickets are created today..
        Date dateReported = new Date(); // Default constructor creates date with current date/time
        int priority;
        Date resolutionDate = null;
        String resolution = null;

        boolean moreProblems = true;
        while (moreProblems) {
            //input statements
            System.out.println("Enter problem: ");
            description = sc.nextLine();
            System.out.println("Who reported the issue?: ");
            reporter = sc.nextLine();
            System.out.println("Enter the priority of : " + description);
            priority = Integer.parseInt(sc.nextLine());

            // create new ticket and add to ticket list
            Ticket t = new Ticket(priority, reporter, description, dateReported, resolutionDate, resolution);
            addTicketInPriorityOrder(ticketQueue, t);

            // print out all of the currently stored tickets after a new ticket is entered
            printAllTickets(ticketQueue);

            //option to add more tickets
            System.out.println("More tickets?: ");
            String more = sc.nextLine();
            if (more.equalsIgnoreCase("N")) {
                moreProblems = false;
            }
        }
    }
    // add ticket method
    protected static void addTicketInPriorityOrder(LinkedList<Ticket> tickets, Ticket newTicket) {

        //Logic: assume the list is either empty or sorted
        if (tickets.size() == 0) {      //Special case - if list is empty, add ticket and return
            tickets.add(newTicket);
            return;
        }
        //Tickets with the HIGHEST priority number go at the front of the list (e.g. 5 = server on fire)
        //Tickets with the LOWEST priority value of their priority number go at the end

        int newTicketPriority = newTicket.getPriority();

        for (int x = 0; x < tickets.size(); x++) {      //Use a regular loop so we know which element we are looking at

            //if newTicket is higher or equal priority than the this element, add it in front of this one and return
            if (newTicketPriority >= tickets.get(x).getPriority()) {
                tickets.add(x, newTicket);
                return;
            }
        }
        //Will only get here if the ticket is not added in the loop
        //If that happens, it must be lower priority than all other tickets. So, add to the end.
        tickets.addLast(newTicket);
    }
    //search ticket method
    public static void searchTicket(LinkedList<Ticket> ticketQueue) {
        LinkedList<String> searchResults = new LinkedList<String>();
        System.out.println("Enter a word to search for: ");
        Scanner searchScanner = new Scanner(System.in);
        String searchInput = searchScanner.nextLine();
        for(Ticket s : ticketQueue) {
            String ticketToString = s.toString();
            String[] splitString = ticketToString.split("\\s");
            for(Object word : splitString) {
                if (word.equals(searchInput)) {
                    searchResults.add(ticketToString);
                } else {
                    continue;
                }
            }
        }
        for(String s : searchResults) {
            System.out.println(s + "/n");
        }
        searchScanner.close();
        System.out.println("To delete ticket, choose option 2 and enter the ID");
    }

    protected static void printAllTickets(LinkedList<Ticket> tickets) {
        System.out.println(" ----- All tickets ----- ");

        for (Ticket t : tickets) {
            System.out.println(t);
        }
    }
    protected static void openTicketWriter (LinkedList<Ticket> ticketQueue) {
        try{
            BufferedWriter openTickets = new BufferedWriter(new FileWriter("open_tickets.txt"));
            for (Ticket t : ticketQueue) {
                openTickets.write(t.toString() + "\n");
            }
            openTickets.close();
            System.out.println("Open tickets file successfully written");
        } catch (IOException ioe){
            System.out.println("An IO Exception has occurred");
        }
    }
    protected static void resolvedTicketWriter (LinkedList<String> resolvedTickets) {
        try{
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
            File file = new File("resolved_tickets_as_of_" + dateFormat.format(date) + ".txt" );
            BufferedWriter resolvedTicks = new BufferedWriter (new FileWriter(file));

            for (String t : resolvedTickets) {
                resolvedTicks.write(t + "\n");
            }
            resolvedTicks.close();
            System.out.println("Resolved Tickets successfully written");
        }catch (IOException ioe) {
            System.out.println("An IO Exception has occurred");
        }
    }
    protected static void openTicketReader (LinkedList<Ticket> ticketQueue) {
        try{
            BufferedReader openTicketImport = new BufferedReader(new FileReader("open_tickets.txt"));
            String line = openTicketImport.readLine();
            while (line != null) {
                String ticketToString = openTicketImport.toString();
                String[] splitString = ticketToString.split("\\s");
                for(Object word : splitString) {
                    System.out.println(word);
                }
            }
            openTicketImport.close();
        }catch (IOException ioe) {
            System.out.println("An IO Exception has occurred");
        }
    }
}
