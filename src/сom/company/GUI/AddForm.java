package сom.company.GUI;

import сom.company.Collection.*;
import сom.company.Collection.Event;
import сom.company.Collection.command.CollectionCommand_Add;
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
public class AddForm extends CreativeForm{

    public static AddForm frame = new AddForm();

    private AddForm(){
        super();
    }

    @Override
    void makeChange(){
        Client.getTreeSet().clear();
        Client.Process(new CollectionCommand_Add(getBufTicket()));
        summoner.getBinder().refresh();
    }
    
    @Override
    void makeHeader(){
        frame.setTitle("Ticket cooking");
    }

    public static void main(MainForm mainWin) {
        summoner = mainWin;
        frame.pack();
        frame.makeHeader();
        frame.setLocationRelativeTo(null);
        frame.setLocation(mainWin.buttonPanel.getLocationOnScreen());
        frame.setVisible(true);
    }
}
