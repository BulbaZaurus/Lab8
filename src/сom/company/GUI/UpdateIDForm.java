package сom.company.GUI;

import сom.company.Collection.*;
import сom.company.Collection.Event;
import сom.company.Collection.command.CollectionCommand_Update_id;
import сom.company.Networking.Client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import static сom.company.GUI.LoginForm.currentLocale;
/**
 * Класс,который отвечает за реалиацию команды add
 */
public class UpdateIDForm extends CreativeForm {

    public static UpdateIDForm frame = new UpdateIDForm();

    private UpdateIDForm(){
        super();
    }

    private void fillPrevValues(){
        nameField.setText(MainForm.getTicketToRemove().getName());
        cordXField.setText(String.valueOf(MainForm.getTicketToRemove().getCoordinates().getX()));
        cordYField.setText(String.valueOf(MainForm.getTicketToRemove().getCoordinates().getY()));
        priceField.setText(String.valueOf(MainForm.getTicketToRemove().getPrice()));
        EventNameField.setText(MainForm.getTicketToRemove().getEvent().getName());
        commentField.setText(MainForm.getTicketToRemove().getComment());

        refundableBox.setSelectedItem(MainForm.getTicketToRemove().getRefundable());
        eventTypeBox.setSelectedItem(MainForm.getTicketToRemove().getEvent().getEventType());
        typeBox.setSelectedItem(MainForm.getTicketToRemove().getType());
    }

    @Override
    void makeChange(){
        long id=MainForm.getID();
        getBufTicket().setId(id);
        Client.getTreeSet().clear();
        Client.Process(new CollectionCommand_Update_id(getBufTicket()));
        dispose();
        summoner.getBinder().refresh();
    }
    
    @Override
    void makeHeader(){
        frame.setTitle("Ticket #"+MainForm.getID()+" recooking");
    }

    public static void main(MainForm mainWin) {
        summoner = mainWin;
        frame.pack();
        frame.fillPrevValues();
        frame.makeHeader();
        frame.setLocationRelativeTo(null);
        frame.setLocation(mainWin.buttonPanel.getLocationOnScreen());
        frame.setVisible(true);
    }
}
