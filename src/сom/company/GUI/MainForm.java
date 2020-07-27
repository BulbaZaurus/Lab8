package сom.company.GUI;

import sun.java2d.cmm.Profile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame{
    private  static  JFrame frame;
    JButton add_button = new JButton("ADD");
    JButton add_if_min_button = new JButton("ADD_IF_MIN");
    JButton help_button = new JButton("HELP");
    JButton info_button = new JButton("INFO");
    JButton clear_button = new JButton("CLEAR");
    JButton remove_greater_button = new JButton("REMOVE_GREATER");
    JButton remove_lower_button = new JButton("REMOVE LOWER");
    JButton count_greater_than_type_button = new JButton("COUNT_GREATER_THAN_TYPE");
    JButton log_out_button = new JButton("LOG OUT");
    JLabel user = new JLabel();
    String[] col = {"id","name","coordinatesX","coordinatesY","price","comment","type","eventType","eventName","eventID","refundable","userName"
            ,"creationDate"};
    DefaultTableModel tableModel = new DefaultTableModel(col, 0);
    JTable table = new JTable(tableModel) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    JScrollPane scrollPane = new JScrollPane(table);

    Container container = getContentPane();
    MainForm()   {
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableModel);
        table.setRowSorter(sorter);
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        columnSize();
        buttonBehaviour();
//        hd = new HouseDrawer(5, 5);
//        hd.setBounds(400, 700, 100, 100);
//        this.add(hd);

    }
    public void buttonBehaviour(){
        log_out_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Разлогиниваюсь");
                try {
                    Thread.sleep(500);
                }catch (InterruptedException exception){
                    exception.printStackTrace();
                }
                frame.setVisible(false);
                LoginForm.StartLogin();
            }
        });

    }
    public void columnSize(){
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(8).setPreferredWidth(100);
        table.getColumnModel().getColumn(12).setPreferredWidth(120);
    }
    public void setLayoutManager() {
        container.setLayout(null);
    }
    public void setLocationAndSize() {

        add_button.setBounds(40, 20, 100,33);
        add_if_min_button.setBounds(160, 20, 150,33);
        help_button.setBounds(330,20,100,33);
        info_button.setBounds(450,20,100,33);
        clear_button.setBounds(570,20,100,33);
        remove_greater_button.setBounds(40,73, 170,33);
        remove_lower_button.setBounds(230, 73, 170,33);
        count_greater_than_type_button.setBounds(420,73,250,33);
        log_out_button.setBounds(840, 20, 100,33);
        scrollPane.setBounds(60, 200, 900, 200);

    }
    public void addComponentsToContainer() {
        container.add(add_button);
        container.add(add_if_min_button);
        container.add(help_button);
        container.add(info_button);
        container.add(clear_button);
        container.add(remove_greater_button);
        container.add(remove_lower_button);
        container.add(count_greater_than_type_button);
        container.add(log_out_button);
        container.add(scrollPane);

//        container.add(tablePanel);

    }

    public static void main() {
        frame=new MainForm();
        frame.setTitle("Main window");
        frame.setVisible(true);
        frame.setBounds(10, 10, 1020, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }
}
