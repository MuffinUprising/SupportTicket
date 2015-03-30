package com.casey;

import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

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
    private JSpinner resDateSpinner;
    private JLabel resDateLabel;
    private JButton saveButton;
    DefaultListModel<Ticket> supportTicketModel;
    TicketManager mgr = new TicketManager();

    public SupportTicketGUI() {
        //setup
        super("Support Ticket");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(new Dimension(1000, 500));

        //support ticket model
        supportTicketModel = new DefaultListModel<Ticket>();
        ticketList.setModel(supportTicketModel);

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
        //dateSpinner model
        dateSpinner.setModel(new SpinnerDateModel());

        //Listeners
        resolveTicketField.addKeyListener(new KeyAdapter() {
        });
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
                String reporter = reporterField.getText();
                String date = dateSpinner.getModel().getValue().toString();
                String description = descriptionField.getText();
                int priority = (Integer) severityComboBox.getSelectedItem();
                String resolution = "";
                String resDate = resDateSpinner.getModel().getValue().toString();

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
            //TODO write resolved method
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mgr.openTicketWriter();
                mgr.resolvedTicketWriter();
            }
        });
    }
}
