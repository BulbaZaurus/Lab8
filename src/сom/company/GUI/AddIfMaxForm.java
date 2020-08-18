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
import java.util.TreeSet;

import static сom.company.GUI.LoginForm.currentLocale;

public class AddIfMaxForm extends CreativeForm {

    public static AddIfMaxForm frame = new AddIfMaxForm();

    private AddIfMaxForm(){
        super();
    }

    @Override
    void makeChange(){
        TreeSet<Ticket> treeSetForMax=Client.getTreeSet();
        resourceBundle = ResourceBundle.getBundle("locale",currentLocale);
        if(treeSetForMax.size()!=0){
            if(getBufTicket().compareTo(treeSetForMax.last())>0){
                Client.getTreeSet().clear();
                Client.Process(new CollectionCommand_Add(getBufTicket()));
                summoner.getBinder().refresh();
            }
            else JOptionPane.showMessageDialog(null,resourceBundle.getString("MoreThan")+" "+ treeSetForMax.last().getPrice());
        }
        else JOptionPane.showMessageDialog(null,resourceBundle.getString("Empty"));
    }
    
    @Override
    void makeHeader(){
        frame.setTitle("Maximal ticket cooking");
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
