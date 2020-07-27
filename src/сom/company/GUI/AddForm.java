package сom.company.GUI;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AddForm extends JFrame implements ActionListener{
    Container container = getContentPane();
    JLabel nameLabel = new JLabel("Name");
    JLabel cordsXLabel = new JLabel("CoordinatesX");
    JLabel cordsYLabel = new JLabel("CoordinatesY");
    JLabel priceLabel = new JLabel("Price");
    JLabel commentLabel = new JLabel("Comment");
    JLabel typeLabel = new JLabel("Type");
    JLabel EventTypeLabel = new JLabel("Event Type");
    JLabel EventNameLabel = new JLabel("Event Name");
    JLabel refundableLabel = new JLabel("Refundable");

    JTextField nameField = new JTextField();
    JTextField cordXField = new JTextField();
    JTextField cordYField = new JTextField();
    JTextField priceField = new JTextField();
    JTextField commentField = new JTextField();
    String[] type = {"VIP", "USUAL", "BUDGETARY","CHEAP"};
    JComboBox typeBox = new JComboBox(type);
    String[] eventType={"BASEBALL","THEATRE_PERFORMANCE","EXPOSITION"};
    JComboBox eventTypeBox=new JComboBox(eventType);
    JTextField EventNameField = new JTextField();
    JTextField refundableField = new JTextField();
    JButton addButton = new JButton("ADD");
    HashMap<String, Boolean> Validator = new HashMap<String, Boolean>();
    AddForm(){
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }
    public void setLocationAndSize() {
        nameLabel.setBounds(30, 50, 290, 20);
        nameField.setBounds(30, 75, 290, 30);

        cordsXLabel.setBounds(30, 120, 290, 20);
        cordXField.setBounds(30, 145, 290, 30);

        cordsYLabel.setBounds(30, 190, 290, 20);
        cordYField.setBounds(30, 215, 290, 30);

        priceLabel.setBounds(390, 50, 290, 20);
        priceField.setBounds(390, 75, 290, 30);

        commentLabel.setBounds(390, 120, 290, 20);
        commentField.setBounds(390, 145, 290, 30);

        typeLabel.setBounds(390, 190, 290, 20);
        typeBox.setBounds(390,215,290,30);

        EventTypeLabel.setBounds(390, 260, 290, 20);
        eventTypeBox.setBounds(390, 285, 290, 30);

        EventNameLabel.setBounds(30,260,290,20);
        EventNameField.setBounds(30,280,290,20);

        refundableLabel.setBounds(30,310,290,20);
        refundableField.setBounds(30,330,290,30);
        addButton.setBounds(255, 400, 210, 80);
        addButton.setBackground(new Color(255,99,71));
    }



    public void addComponentsToContainer() {
        container.add(nameLabel);
        container.add(cordsXLabel);
        container.add(cordsYLabel);
        container.add(priceLabel);
        container.add(commentLabel);
        container.add(typeLabel);
        container.add(typeLabel);
        container.add(EventTypeLabel);
        container.add( EventNameLabel);
        container.add(refundableLabel);

        container.add(nameField);
        container.add(cordXField);
        container.add(cordYField);
        container.add(priceField);
        container.add(commentField);
        container.add(typeBox);
        container.add(eventTypeBox);
        container.add(EventNameField);
        container.add(refundableField);

        container.add(addButton);
    }
    public void addActionEvent(){
        addButton.addActionListener(this);

        nameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                colorize();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                colorize();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                colorize();
            }
            void colorize(){
                if (nameField.getText().isEmpty())
                {
                    nameField.setBackground(new Color(255,99,71));
                    nameField.setToolTipText("Enter name");
                    Validator.replace("Name", false);
                    addButton.setBackground(new Color(255,99,71));
                }
                else
                {
                    nameField.setBackground(new Color(150, 255, 150));
                    Validator.replace("Name", true);

                    for (String a: Validator.keySet())
                    {
                        if (!Validator.get(a)) return;
                    }
                    addButton.setBackground(new Color(150, 255, 150));
                }
            }
        });

        cordXField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { colorize();
            }
            @Override
            public void removeUpdate(DocumentEvent e) { colorize();
            }
            @Override
            public void changedUpdate(DocumentEvent e) { colorize();
            }
            void colorize(){

                if(cordXField.getText().isEmpty()){
                    cordXField.setBackground(new Color(255,99,71));
                    cordXField.setToolTipText("Enter coordX");
                    Validator.replace("CoordX", false);
                    addButton.setBackground(new Color(255,99,71));
                    return;
                }
                else {
                    cordXField.setBackground(new Color(150, 255, 150));
                    Validator.replace("CoordX", true);

                    for (String a: Validator.keySet())
                    {
                        if (!Validator.get(a)) return;
                    }
                    addButton.setBackground(new Color(150, 255, 150));
                }

            }
        });

        cordYField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {colorize();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {colorize();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {colorize();
            }

            void colorize(){
                try{
                    Double y=Double.parseDouble(cordYField.getText());
                    if(cordYField.getText().isEmpty() || y<=-748){
                        y=1.d/0;
                    }
                    else {
                        cordYField.setBackground(new Color(150, 255, 150));
                        Validator.replace("CoordY", true);

                        for (String a: Validator.keySet())
                        {
                            if (!Validator.get(a)) return;
                        }
                        addButton.setBackground(new Color(150, 255, 150));
                    }
                }catch (Exception e){
                    cordYField.setBackground(new Color(255,99,71));
                    cordYField.setToolTipText("Enter coordY");
                    Validator.replace("CoordY", false);
                    addButton.setBackground(new Color(255,99,71));
                    return;
                }


            }
        });

        priceField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { colorize();

            }

            @Override
            public void removeUpdate(DocumentEvent e) { colorize();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                colorize();
            }
            void colorize(){
                if (priceField.getText().isEmpty())
                {
                    priceField.setBackground(new Color(255,99,71));
                    priceField.setToolTipText("Enter price");
                    Validator.replace("Price", false);
                    addButton.setBackground(new Color(255,99,71));
                }
                else
                {
                    priceField.setBackground(new Color(150, 255, 150));
                    Validator.replace("Price", true);

                    for (String a: Validator.keySet())
                    {
                        if (!Validator.get(a)) return;
                    }
                    addButton.setBackground(new Color(150, 255, 150));
                }
            }
        });

        commentField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {colorize();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {colorize();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {colorize();

            }

            void colorize(){
                if (commentField.getText().isEmpty())
                {
                    commentField.setBackground(new Color(255,99,71));
                    commentField.setToolTipText("Enter comment");
                    Validator.replace("Comment", false);
                    addButton.setBackground(new Color(255,99,71));
                }
                else
                {
                    commentField.setBackground(new Color(150, 255, 150));
                    Validator.replace("Comment", true);

                    for (String a: Validator.keySet())
                    {
                        if (!Validator.get(a)) return;
                    }
                    addButton.setBackground(new Color(150, 255, 150));
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event){

    }

    public static void main(String[] args) {
        AddForm frame=new AddForm();
        frame.setTitle("Add Window");
        frame.setVisible(true);
        frame.setBounds(10, 10, 720, 560);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
    }

}
