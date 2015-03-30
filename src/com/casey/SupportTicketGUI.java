package com.casey;

import java.awt.event.*;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.MouseEvent;

/**
 * Created by casey on 3/24/15.
 */
public class SupportTicketGUI extends JFrame{
    private JPanel rootPanel;
    private JLabel priorityLabel;
    private JButton addButton;
    private JList ticketList;
    private JButton resolveButton;
    private JComboBox severityComboBox;
    private JTextField resolveTicketField;
    private JTextField descriptionField;
    private JList resolvedList;
    private JLabel commentLabel;
    private JLabel resolutionLabel;
    private JTextField reporterField;
    private JSpinner dateSpinner;
    private JLabel resDateLabel;
    private JButton saveButton;
    private JLabel selectedTicket;
    private JLabel dateLabel;
    private JLabel resDate;

    DefaultListModel<Ticket> supportTicketModel;
    DefaultListModel<Ticket> resolvedTicketLModel;
    TicketManager mgr = new TicketManager();

    public SupportTicketGUI() {
        //setup
        super("Support Ticket");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(1300, 500));

        //support and resolved ticket model
        supportTicketModel = new DefaultListModel<Ticket>();
        resolvedTicketLModel = new DefaultListModel<Ticket>();
        ticketList.setModel(supportTicketModel);
        resolvedList.setModel(resolvedTicketLModel);

        //variables for priority ComboBox
        final int TRIVIAL = 1;
        final int CONCERNING = 2;
        final int IMPORTANT = 3;
        final int CRITICAL = 4;
        final int CATASTROPHIC = 5;
        severityComboBox.addItem(TRIVIAL);
        severityComboBox.addItem(CONCERNING);
        severityComboBox.addItem(IMPORTANT);
        severityComboBox.addItem(CRITICAL);
        severityComboBox.addItem(CATASTROPHIC);


        //Listeners
        severityComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        reporterField.addKeyListener(new KeyAdapter(){
        });
        resolveTicketField.addKeyListener(new KeyAdapter() {
        });
        descriptionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }
        });
        //add button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new variables
                Date d = new Date();
                String date = d.toString();
                dateLabel.setText(date);

                // new variables
                String reporter = reporterField.getText();
                String description = descriptionField.getText();
                int priority = (Integer) severityComboBox.getSelectedItem();
                String resolution = "";
                String resDate = null;

                //new ticket and add ticket to ticket list
                Ticket newTicket = new Ticket(priority, reporter, description, date, resDate, resolution);
                mgr.addTicketInPriorityOrder(newTicket);
                SupportTicketGUI.this.supportTicketModel.addElement(newTicket);


            }
        });

        //need to finish resolved tickets method
        resolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //resolution date
                Date d = new Date();
                String resolveDate = d.toString();
                resDate.setText(resolveDate);

                // new variables for resolved ticket
                String rDate = resDate.getText();
                String resolution = resolveTicketField.getText();
                String date = dateLabel.getText();
                String reporter = reporterField.getText();
                String description = descriptionField.getText();
                int priority = (Integer) severityComboBox.getSelectedItem();

                // make new ticket with new variables, add ticket to resolved, and remove from support ticket list
                Ticket resolvedTicket = new Ticket(priority, reporter, description, date, rDate, resolution);
                mgr.addResolvedTicket(resolvedTicket);
                SupportTicketGUI.this.resolvedTicketLModel.addElement(resolvedTicket);
                SupportTicketGUI.this.supportTicketModel.removeElement(resolvedTicket);
            }
        });
        // save button writes both lists to files
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mgr.openTicketWriter();
                mgr.resolvedTicketWriter();
            }
        });

        //
        ticketList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JList ticList = (JList) e.getSource();
                if (e.getClickCount() == 2) {
                    int index = ticList.locationToIndex(e.getPoint());
                    Object o = ticList.getModel().getElementAt(index);
                    Ticket t = (Ticket) o;
                    selectedTicket.setText(t.toList());
                    dateLabel.setText(t.getDateReported());
                    reporterField.setText(t.getReporter());
                    descriptionField.setText(t.getDescription());
                    severityComboBox.setSelectedItem(t.getPriority());

                }
            }
        });
    }
}
