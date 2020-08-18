package сom.company.Collection.command;


import сom.company.Collection.Controller;
import сom.company.Collection.Ticket;
import сom.company.Collection.User;
import сom.company.GUI.MainForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
/**
 * Комманда info
 */
public class CollectionCommand_Info extends JFrame implements  Serializable, CollectionCommand {
    private  static  final  long serialVersionUID = 10L;


    @Override
    public void Execute(TreeSet<Ticket> tickets, Scanner[] in,
                        String savePath, boolean[] isScannerFromSystem, List<String> executed_scripts, User user){

        System.out.println("Тип: TreeSet \n" +
               "Количество элементов: " + Controller.getTickets().size());
        JFrame frame=new JFrame("Help");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JButton button=new JButton("Нажми меня");
        frame.add(button);
        frame.setVisible(true);
        frame.setSize(400,350);
        frame.setLocationRelativeTo(null);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                JOptionPane.showMessageDialog(null,"Тип: TreeSet \n" +
                        "Количество элементов: " + Controller.getTickets().size());
            }
        });

    }
    }


