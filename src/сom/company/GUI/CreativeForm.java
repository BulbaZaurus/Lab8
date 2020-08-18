package сom.company.GUI;

import сom.company.Collection.*;
import сom.company.Collection.Event;
import сom.company.Networking.Client;
import static сom.company.GUI.ImageMaster.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
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
 * Класс,который отвечает за создание элементов
 */
public abstract class CreativeForm extends JFrame implements ActionListener{
    private String name;
    private String coordX;
    private String coordY;
    private Float x;
    private Double y;
    private Float pirce;
    private Coordinates coordinates;
    private String comment;
    private String event_name;
    private Boolean refundableVal;
    private TicketType typeVal;
    private EventType eventTypeVal;
    private Date creationDate;
    private LocalDate date;
    private Event event1;
    private User user;
    private SimpleDateFormat simpleDateFormat;
    private String time;
    private Ticket bufTicket;
    public Ticket getBufTicket(){
        return bufTicket;
    }
    
    ResourceBundle resourceBundle;
    JPanel pane = new JPanel();
    Container container = getContentPane();
    JLabel nameLabel = new JLabel("Name");
    JLabel cordsXLabel = new JLabel("X coordinate");
    JLabel cordsYLabel = new JLabel("Y coordinate");
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
    JTextField EventNameField = new JTextField();
    String[] type = {"VIP", "USUAL", "BUDGETARY","CHEAP"};
    JComboBox<String> typeBox = new JComboBox<>(type);
    String[] eventType={"BASEBALL","THEATRE_PERFORMANCE","EXPOSITION"};
    JComboBox<String> eventTypeBox=new JComboBox<>(eventType);
    String[] refundable={"true","false"};
    JComboBox<String> refundableBox=new JComboBox<>(refundable);

    private JButton confirmButton = new JButton(" ");
    public JButton getButt() {return confirmButton;}

    HashMap<String, Boolean> Validator = new HashMap<String, Boolean>();
    protected static MainForm summoner;
    
    protected CreativeForm(){
        setLayoutManager();
        decorate();
        addComponentsToContainer();
        buttonBehaviour();

        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void setLayoutManager() {
        container.setLayout(new BorderLayout());
        pane.setLayout(new GridLayout(0,2,10,0));
    }

    private void decorate(){
        pane.setOpaque(false);
        pane.setBorder(new EmptyBorder(10, 20, 20, 20));
        container.setBackground(Color.darkGray);

        refundableBox.setFocusable(false);
        refundableBox.setRenderer(new ComboRenderer(refundableBox.getRenderer()));
        refundableBox.setForeground(Color.lightGray);
        refundableBox.setBackground(darkestGray);
        eventTypeBox.setFocusable(false);
        eventTypeBox.setRenderer(new ComboRenderer(eventTypeBox.getRenderer()));
        eventTypeBox.setForeground(Color.lightGray);
        eventTypeBox.setBackground(darkestGray);
        typeBox.setFocusable(false);
        typeBox.setRenderer(new ComboRenderer(typeBox.getRenderer()));
        typeBox.setForeground(Color.lightGray);
        typeBox.setBackground(darkestGray);
        
        refundableBox.setFont(fontS);
        eventTypeBox.setFont(fontS);
        typeBox.setFont(fontS);
        nameField.setFont(fontS);
        cordXField.setFont(fontS);
        cordYField.setFont(fontS);
        priceField.setFont(fontS);
        EventNameField.setFont(fontS);
        commentField.setFont(fontS);

        nameLabel.setFont(fontS);
        cordsXLabel.setFont(fontS);
        cordsYLabel.setFont(fontS);
        priceLabel.setFont(fontS);
        EventNameLabel.setFont(fontS);
        commentLabel.setFont(fontS);
        typeLabel.setFont(fontS);
        EventTypeLabel.setFont(fontS);
        refundableLabel.setFont(fontS);

        confirmButton.setFont(fontS);
        confirmButton.setFocusPainted(false);
        confirmButton.setBackground(lightGreen);

        nameLabel.setForeground(Color.lightGray);
        cordsXLabel.setForeground(Color.lightGray);
        cordsYLabel.setForeground(Color.lightGray);
        priceLabel.setForeground(Color.lightGray);
        EventNameLabel.setForeground(Color.lightGray);
        commentLabel.setForeground(Color.lightGray);
        typeLabel.setForeground(Color.lightGray);
        EventTypeLabel.setForeground(Color.lightGray);
        refundableLabel.setForeground(Color.lightGray);

        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        nameField.setBorder(new LineBorder(Color.lightGray, 2));
        cordXField.setBorder(new LineBorder(Color.lightGray, 2));
        cordYField.setBorder(new LineBorder(Color.lightGray, 2));
        priceField.setBorder(new LineBorder(Color.lightGray, 2));
        EventNameField.setBorder(new LineBorder(Color.lightGray, 2));
        commentField.setBorder(new LineBorder(Color.lightGray, 2));
    }

    private void addComponentsToContainer() {
        pane.add(priceLabel);
        pane.add(priceField);

        pane.add(nameLabel);
        pane.add(typeLabel);
        pane.add(nameField);
        pane.add(typeBox);

        pane.add(EventNameLabel);
        pane.add(EventTypeLabel);
        pane.add(EventNameField);
        pane.add(eventTypeBox);

        pane.add(commentLabel);
        pane.add(refundableLabel);
        pane.add(commentField);
        pane.add(refundableBox);

        pane.add(cordsXLabel);
        pane.add(cordsYLabel);
        pane.add(cordXField);
        pane.add(cordYField);

        container.add(confirmButton, BorderLayout.NORTH);
        container.add(pane);
    }

    private void buttonBehaviour(){
        nameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {colorize();}
            @Override
            public void removeUpdate(DocumentEvent e) {colorize();}
            @Override
            public void changedUpdate(DocumentEvent e) {colorize();}
            void colorize(){
                if (nameField.getText().isEmpty()){
                    nameField.setBackground(lightRed);
                    nameField.setToolTipText("Enter name");
                    Validator.replace("Name", false);
                }
                else{
                    nameField.setBackground(lightGreen);
                    Validator.replace("Name", true);
                    for (String a: Validator.keySet()){if (!Validator.get(a)) return;}
                }
            }
        });

        cordXField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { colorize();}
            @Override
            public void removeUpdate(DocumentEvent e) { colorize();}
            @Override
            public void changedUpdate(DocumentEvent e) { colorize();}
            void colorize(){
                try{
                    Float x=Float.parseFloat(cordXField.getText());
                    if(cordXField.getText().isEmpty()){
                        x=1.f/0;
                    }
                    else {
                        cordXField.setBackground(lightGreen);
                        Validator.replace("CoordX", true);
                        for (String a: Validator.keySet()){if (!Validator.get(a)) return;}
                    }
                }catch (Exception e){
                    cordXField.setBackground(lightRed);
                    cordXField.setToolTipText("Enter coordX");
                    Validator.replace("CoordX", false);
                    return;
                }
            }
        });

        cordYField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {colorize();}
            @Override
            public void removeUpdate(DocumentEvent e) {colorize();}
            @Override
            public void changedUpdate(DocumentEvent e) {colorize();}
            void colorize(){
                try{
                    Double y=Double.parseDouble(cordYField.getText());
                    if(cordYField.getText().isEmpty() || y<=-748){
                        y=1.d/0;
                    }
                    else {
                        cordYField.setBackground(lightGreen);
                        Validator.replace("CoordY", true);
                        for (String a: Validator.keySet()){if (!Validator.get(a)) return;}
                    }
                }catch (Exception e){
                    cordYField.setBackground(lightRed);
                    cordYField.setToolTipText("Enter coordY");
                    Validator.replace("CoordY", false);
                    return;
                }
            }
        });

        priceField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {colorize();}
            @Override
            public void removeUpdate(DocumentEvent e) { colorize();}
            @Override
            public void changedUpdate(DocumentEvent e) {colorize();}
            void colorize(){
                try{
                    float price = Float.parseFloat(priceField.getText());
                    if (priceField.getText().isEmpty() || price<=0){
                        price=1.f/0;
                    }
                    else{
                        priceField.setBackground(lightGreen);
                        Validator.replace("Price", true);
                        for (String a: Validator.keySet()){if (!Validator.get(a)) return;}
                    }
                }catch (Exception e){
                    priceField.setBackground(lightRed);
                    priceField.setToolTipText("Enter price");
                    Validator.replace("Price", false);
                    return;
                }
            }
        });

        commentField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {colorize();}
            @Override
            public void removeUpdate(DocumentEvent e) {colorize();}
            @Override
            public void changedUpdate(DocumentEvent e) {colorize();}
            void colorize(){
                if (commentField.getText().isEmpty()){
                    commentField.setBackground(lightRed);
                    commentField.setToolTipText("Enter comment");
                    Validator.replace("Comment", false);
                }
                else{
                    commentField.setBackground(lightGreen);
                    Validator.replace("Comment", true);
                    for (String a: Validator.keySet()){if (!Validator.get(a)) return;}
                }
            }
        });

        EventNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {colorize();}
            @Override
            public void removeUpdate(DocumentEvent documentEvent) {colorize();}
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {colorize();}
            void colorize(){
                if (EventNameField.getText().isEmpty()){
                    EventNameField.setBackground(lightRed);
                    EventNameField.setToolTipText("Enter Event Name");
                    Validator.replace("Event Name", false);
                }
                else{
                    EventNameField.setBackground(lightGreen);
                    Validator.replace("Event Name", true);
                    for (String a: Validator.keySet()){if (!Validator.get(a)) return;}
                }
            }
        });
        confirmButton.addActionListener(this);
        typeBox.addActionListener(this);
        eventTypeBox.addActionListener(this);
        refundableBox.addActionListener(this);
    }



    @Override
    public void actionPerformed(ActionEvent event){
        if(event.getSource()==confirmButton && (nameField.getText().isEmpty() || cordXField.getText().isEmpty() ||
                cordYField.getText().isEmpty() || EventNameField.getText().isEmpty() || priceField.getText().isEmpty() || 
                commentField.getText().isEmpty())){
            resourceBundle = ResourceBundle.getBundle("locale",currentLocale);
            JOptionPane.showMessageDialog(null,resourceBundle.getString("emptyField"));
        }else {
            try{
                if(event.getSource()==confirmButton){
                    Validator.replace("Type",true);
                    Validator.replace("EventType",true);
                    Validator.replace("Refundable",true);
                    for(String a:Validator.keySet()){
                        if(!Validator.get(a))
                            return;
                    }
                    name = nameField.getText();
                    coordX = cordXField.getText();
                    coordY = cordYField.getText();
                    x = Float.parseFloat(coordX);
                    y = Double.parseDouble(coordY);
                    pirce = Float.parseFloat(priceField.getText());
                    coordinates = new Coordinates(x,y);
                    comment = commentField.getText();
                    event_name = EventNameField.getText();
                    refundableVal = Boolean.parseBoolean((String) refundableBox.getSelectedItem());
                    typeVal = TicketType.valueOf((String) typeBox.getSelectedItem());
                    eventTypeVal = EventType.valueOf((String) eventTypeBox.getSelectedItem());
                    creationDate = new Date();
                    date = LocalDate.now();
                    event1 = new Event(event_name,eventTypeVal,date);
                    user = Client.getUser();
                    simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                    time = simpleDateFormat.format(creationDate);
                    bufTicket = new Ticket(name,coordinates,pirce,comment,refundableVal,typeVal,event1,time,user);
                    bufTicket.setTime(time);

                    this.makeChange();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    abstract void makeChange();
    
    abstract void makeHeader();
}
