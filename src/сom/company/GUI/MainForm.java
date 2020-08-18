package сom.company.GUI;

import сom.company.Collection.Ticket;
import static сom.company.GUI.LoginForm.currentLocale;
import сom.company.Networking.Client;
import static сom.company.Networking.Client.getUser;
import static сom.company.GUI.ImageMaster.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Является главным графическим окном,в котором происходит вся магия
 */
public class MainForm extends JFrame{
    static boolean[] isScannerFromSystem = new boolean[]{true};
    public static List<String> executed_scripts = new ArrayList<>();
    public static Scanner[] in = new Scanner[]{new Scanner(System.in)};
    private static Long ID;
    public static Long getID() {return ID;}
    public static void setID(Long id) {ID = id;}
    private static Float price;
    public static Float getPrice() {return price;}
    public static void setPrice(Float prc) {price = prc;}

    static  JPanel jPanel=new JPanel();

    

    private static TreeSet<Ticket> tickets;
    public static TreeSet<Ticket> getTickets() {return tickets;}
    public static void setTickets(TreeSet<Ticket> tickets) {MainForm.tickets = tickets;}
    private static Ticket ticketToRemove;
    public static void setTicketToRemove(Ticket ticketToRemove) {MainForm.ticketToRemove = ticketToRemove;}
    public static Ticket getTicketToRemove() {return ticketToRemove;}

    private Binder binder = new Binder(this);
    public Binder getBinder(){return this.binder;}

    public static boolean isYellow = false;
    private static boolean isNightMare = false;
    public static boolean getNightMare() {return isNightMare;}
    public static void setNightMare(boolean choice) {isNightMare = choice;}

    JToggleButton switcher = new JToggleButton("Mode: Normal");
    JMenuBar barMenu = new JMenuBar();
    JLabel filler = new JLabel();
    JLabel user = new JLabel("Current user: " + getUser().getName());
    JLabel cool = new JLabel(Iconize(ImageMaster.cool));
    JPanel bgPane = new JPanel(){
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);

            g.drawImage(ImageMaster.bgN, 0, 0, this);
        }
    };
    
    //JLabel loading = new JLabel("Loading...");

    JViewport Tviewport = new JViewport();
    JViewport Cviewport = new JViewport();
    JScrollPane scrollPane = new JScrollPane();
    JScrollPane scrollCanvas = new JScrollPane();
    JPanel buttonPanel = new JPanel();
    JPanel extraPanel = new JPanel();
    JPanel mainPanel = new JPanel()
    {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(isNightMare){
                g.drawImage(ImageMaster.bgN, 0, 0, this);
                g.drawImage(ImageMaster.B, 0, 0, this);
                g.drawImage(ImageMaster.P, getWidth()-ImageMaster.P.getWidth(), 0, this);
                g.drawImage(ImageMaster.horr, (getWidth()-ImageMaster.horr.getWidth())/2, 400, this);
            }
            repaint();
        }
    };
    CanvasPanel canvas = new CanvasPanel(this);

    public JButton add_button = new JButton();
    public JButton getAdd_button() {return add_button;}
    public JButton add_if_max_button = new JButton();
    public JButton help_button = new JButton();
    public JButton info_button = new JButton();
    public JButton clear_button = new JButton();
    public JButton refresh_butt =new JButton();
    public JButton update=new JButton();
    public JButton remove_greater_button = new JButton();
    public JButton remove_by_id_button = new JButton();
    public JButton execute_button=new JButton();
    public JButton filter_less_than_event = new JButton();
    public JButton print_field_comment=new JButton();
    public JButton print_price=new JButton();
    public JButton log_out_button = new JButton();
    public JButton dont_touch_button=new JButton();
    public JButton dtb2 = new JButton("Just stop!");
    public JButton dtb3 = new JButton("!!!");
    public JButton change_language_button = new JButton();

    String[] localeArray={"Русский язык","македонски јазик","język polski","español"};
    JComboBox<String> localeBox=new JComboBox<>(localeArray);

    private static String[] col = {"id","name","coordinatesX","coordinatesY","price","comment","type",
        "eventType","eventName","eventID","refundable","userName","creationDate"};
    public static DefaultTableModel tableModel = new DefaultTableModel(col,0);
    public static DefaultTableModel getTableModel() {return tableModel;}
    public static JTable table = new JTable(tableModel) {
        @Override
        public boolean isCellEditable(int row, int column) {return false;}
    };

    public static String id_not_found="Такой ID не найден или вы не имеете право на редактирование этого элемента";
    public static String useID="Для удаления эллемента коллекции испольуйте коллонку с id !";
    public static String inputID="Введите ID элемента";
    public static String uspeh="Успешно";
    public static String inputPrice="Введи price и жди!";
    public static String JonSnow="Пусто у нас.Нельзя удалить то,что не существует";
    public static String Empty ="Пусто у нас.Нельзя показать то,что не существует! Или можно...?";
    public static String remind="Внимание! Напоминаем о том,что фильтрация элементов происходит по цене ";
    public static String Size ="Размер коллекции клиента: ";
    public static String count ="штука";
    public static String evolution="Комманда show эволюционирировала в окошко с элементами ";
    public static String You_shall_not_pass="ТЫ НЕ ПРОЙДЕШЬ! \n"+"Кляти рекурсивный москаль !";
    public static String goodbye="Ну....как бы для этого есть крестик! \n"+"Аревуар ,амигос!";
    public static String logOut="Ну....как бы для этого есть крестик! \n"+"Аревуар ,амигос!";
    public static String nm;
    public static String norm;
    public static String currUs;

    public Container container = getContentPane();

    private MainForm()   {
        super("Evil Control Panel v0.666 alpha");
        table.setRowSorter(new TableRowSorter<TableModel>(tableModel));
        setLanguage();
        setLayoutManager();
        defineSizes();
        colorize();
        addComponentsToContainer();
        binder.buttonBehaviour();
    }  

    public static void setUpdateUI(JFileChooser chooser){
        ResourceBundle resourceBundle=ResourceBundle.getBundle("locale",currentLocale);
        UIManager.put("FileChooser.openButtonText", resourceBundle.getString("open"));
        UIManager.put("FileChooser.cancelButtonText", resourceBundle.getString("cancel"));
        UIManager.put("FileChooser.lookInLabelText", resourceBundle.getString("search_in"));
        UIManager.put("FileChooser.fileNameLabelText", resourceBundle.getString("file_name"));
        UIManager.put("FileChooser.filesOfTypeLabelText", resourceBundle.getString("file_type"));

        UIManager.put("FileChooser.saveButtonText", resourceBundle.getString("save"));
        UIManager.put("FileChooser.saveButtonToolTipText", resourceBundle.getString("save"));
        UIManager.put("FileChooser.openButtonText", resourceBundle.getString("open"));
        UIManager.put("FileChooser.openButtonToolTipText", resourceBundle.getString("open"));
        UIManager.put("FileChooser.cancelButtonText", resourceBundle.getString("cancel"));
        UIManager.put("FileChooser.cancelButtonToolTipText", resourceBundle.getString("cancel"));

        UIManager.put("FileChooser.lookInLabelText", resourceBundle.getString("directory"));
        UIManager.put("FileChooser.saveInLabelText", resourceBundle.getString("directory"));
        UIManager.put("FileChooser.fileNameLabelText", resourceBundle.getString("file_name"));
        UIManager.put("FileChooser.filesOfTypeLabelText", resourceBundle.getString("file_name"));

        UIManager.put("FileChooser.upFolderToolTipText", resourceBundle.getString("level_up"));
        UIManager.put("FileChooser.newFolderToolTipText", resourceBundle.getString("new_folder"));
        UIManager.put("FileChooser.listViewButtonToolTipText", resourceBundle.getString("spisok"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", resourceBundle.getString("table"));
        UIManager.put("FileChooser.fileNameHeaderText", resourceBundle.getString("imya"));
        UIManager.put("FileChooser.fileSizeHeaderText", resourceBundle.getString("your_size"));
        UIManager.put("FileChooser.fileTypeHeaderText", resourceBundle.getString("typesecond"));
        UIManager.put("FileChooser.fileDateHeaderText", resourceBundle.getString("reupdate"));
        UIManager.put("FileChooser.fileAttrHeaderText", resourceBundle.getString("attribute"));

        UIManager.put("FileChooser.acceptAllFileFilterText", resourceBundle.getString("all_files"));
        chooser.updateUI();
    }

    public void setLanguage(){
        ResourceBundle resourceBundle =ResourceBundle.getBundle("locale",currentLocale);
        AddForm.frame.getButt().setText(resourceBundle.getString("add_button"));
        AddIfMaxForm.frame.getButt().setText(resourceBundle.getString("add_if_max_button"));
        UpdateIDForm.frame.getButt().setText(resourceBundle.getString("update"));

        id_not_found=resourceBundle.getString("id_not_found");
        useID=resourceBundle.getString("useID");
        inputID=resourceBundle.getString("inputID");
        inputPrice=resourceBundle.getString("inputPrice");
        uspeh=resourceBundle.getString("uspeh");
        JonSnow=resourceBundle.getString("JonSnow");
        Empty=resourceBundle.getString("Empty");
        remind=resourceBundle.getString("remind");
        Size=resourceBundle.getString("Size");
        count=resourceBundle.getString("count");
        evolution=resourceBundle.getString("evolution");
        You_shall_not_pass=resourceBundle.getString("You_shall_not_pass");
        goodbye=resourceBundle.getString("goodbye");
        logOut=resourceBundle.getString("logOut");

        add_button.setText(resourceBundle.getString("add_button"));
        add_if_max_button.setText(resourceBundle.getString("add_if_max_button"));
        help_button.setText(resourceBundle.getString("help_button"));
        info_button.setText(resourceBundle.getString("info_button"));
        clear_button.setText(resourceBundle.getString("clear_button"));
        refresh_butt.setText(resourceBundle.getString("update_JTable"));
        update.setText(resourceBundle.getString("update"));
        remove_greater_button.setText(resourceBundle.getString("remove_greater_button"));
        remove_by_id_button.setText(resourceBundle.getString("remove_by_id_button"));
        execute_button.setText(resourceBundle.getString("execute_button"));
        filter_less_than_event.setText(resourceBundle.getString("filter_less_event"));
        print_field_comment.setText(resourceBundle.getString("print_field_comment"));
        print_price.setText(resourceBundle.getString("print_price"));
        log_out_button.setText(resourceBundle.getString("log_out_button"));
        dont_touch_button.setText(resourceBundle.getString("dont_touch_button"));
        change_language_button.setText(resourceBundle.getString("change"));

        nm = resourceBundle.getString("nm");
        norm = resourceBundle.getString("norm");
        currUs = resourceBundle.getString("currUs");
        switcher.setText(isNightMare ? nm : norm);
        user.setText(currUs+" "+getUser().getName());
        
    }


    public void setLayoutManager() {
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        extraPanel.setLayout(new GridLayout(1, 0, 0, 0));
        mainPanel.setLayout(new BorderLayout());
    }

    public void defineSizes(){
        buttonPanel.setPreferredSize(new Dimension(490, 150));
        scrollPane.setPreferredSize(new Dimension(900, 200));
        filler.setPreferredSize(new Dimension(50, 10));
        scrollCanvas.setPreferredSize(new Dimension(400, 280));
        
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(120);
        table.getColumnModel().getColumn(8).setPreferredWidth(120);
        table.getColumnModel().getColumn(9).setPreferredWidth(30);
        table.getColumnModel().getColumn(11).setPreferredWidth(120);
        table.getColumnModel().getColumn(12).setPreferredWidth(160);
    }

    public void colorize(){
        setBackground(Color.BLACK);
        mainPanel.setBackground(Color.darkGray);
        
        mainPanel.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10), new BevelBorder(1)));

        buttonPanel.setBackground(Color.darkGray);
        buttonPanel.setOpaque(false);

        add_button.setBackground(lightGreen);
        help_button.setBackground(Color.lightGray);
        update.setBackground(lightGreen);
        info_button.setBackground(Color.lightGray);
        clear_button.setBackground(Color.red);
        remove_by_id_button.setBackground(Color.red);
        add_if_max_button.setBackground(lightGreen);
        remove_greater_button.setBackground(Color.red);
        filter_less_than_event.setBackground(Color.lightGray);
        print_field_comment.setBackground(Color.lightGray);
        print_price.setBackground(Color.lightGray);
        execute_button.setBackground(Color.orange);

        //add_button.setBorder(new CompoundBorder(new LineBorder(darkestGray), new EmptyBorder(10, 30, 10, 30)));

        dont_touch_button.setBackground(Color.orange);
        
        log_out_button.setContentAreaFilled(false);
        log_out_button.setForeground(Color.red);

        dtb2.setFocusPainted(false);
        dtb2.setContentAreaFilled(false);
        //dtb2.setBorderPainted(false);
        dtb2.setBorder(new LineBorder(ImageMaster.violet));
        dtb2.setForeground(ImageMaster.violet);
        
        dtb3.setFocusPainted(false);
        dtb3.setContentAreaFilled(false);
        dtb3.setBorderPainted(false);
        dtb3.setForeground(Color.magenta);
        //extraPanel.setBackground(Color.darkGray);
        extraPanel.setOpaque(false);

        localeBox.setForeground(Color.lightGray);
        localeBox.setBackground(ImageMaster.darkestGray);
        localeBox.setFocusable(false);
        localeBox.setRenderer(new ComboRenderer(localeBox.getRenderer()));
        change_language_button.setBackground(ImageMaster.darkestGray);
        change_language_button.setForeground(Color.lightGray);
        change_language_button.setFocusPainted(false);
       
        barMenu.setBackground(darkestGray);
        user.setForeground(Color.lightGray);
        // loading.setOpaque(false);
        // loading.setForeground(darkestGray);
        refresh_butt.setBackground(Color.orange);
        refresh_butt.setFocusPainted(false);
        switcher.setFocusPainted(false);
        switcher.setBorderPainted(false);
        switcher.setForeground(Color.lightGray);
        switcher.setOpaque(false);
        switcher.setContentAreaFilled(false);

        Cviewport.setOpaque(false);
        scrollCanvas.setViewport(Cviewport);
        scrollCanvas.setOpaque(false);
        scrollCanvas.setBorder(new CompoundBorder(new EmptyBorder(0,0,30,0), new LineBorder(Color.black, 1)));

        table.setBackground(Color.darkGray);
        table.setForeground(Color.lightGray);
        table.getTableHeader().setBackground(darkestGray);
        table.getTableHeader().setForeground(Color.white);

        Tviewport.setOpaque(false);
        scrollPane.setViewport(Tviewport);
        scrollPane.setOpaque(false);
    }

public void addComponentsToContainer() {
    //setLayout(new BorderLayout());
    //container.add(bgPane);
    //setLayout(new BorderLayout());
    //bgPane.setOpaque(false);
    //setContentPane(bgPane);
    buttonPanel.add(add_button);
    buttonPanel.add(add_if_max_button);
    buttonPanel.add(update);

    buttonPanel.add(remove_greater_button);
    buttonPanel.add(remove_by_id_button);
    buttonPanel.add(clear_button);
    
    buttonPanel.add(execute_button);
    buttonPanel.add(filter_less_than_event);
    buttonPanel.add(print_field_comment);
    buttonPanel.add(print_price);
    
    buttonPanel.add(help_button);
    buttonPanel.add(info_button);
    
    buttonPanel.add(cool);
    cool.setVisible(false);
    
    extraPanel.add(dont_touch_button);
    for(int j = 0; j < 3; j++)
        extraPanel.add(new JLabel());
    extraPanel.add(dtb2);
    dtb2.setVisible(false);
    extraPanel.add(localeBox);
    extraPanel.add(change_language_button);

    Tviewport.setView(table);
    Cviewport.setView(canvas);
    mainPanel.add(scrollPane, BorderLayout.NORTH);
    mainPanel.add(scrollCanvas);
    mainPanel.add(buttonPanel, BorderLayout.WEST);
    mainPanel.add(extraPanel, BorderLayout.SOUTH);

    setContentPane(mainPanel);
    setJMenuBar(barMenu);
    barMenu.add(refresh_butt);
    barMenu.add(switcher);
    barMenu.add(dtb3);
    dtb3.setVisible(false);
    //barMenu.add(loading);
    barMenu.add(Box.createHorizontalGlue());
    barMenu.add(user);
    barMenu.add(filler);
    barMenu.add(log_out_button);
}

    public void setDateAndADD(){  //EEE, d MMM yyyy HH:mm:ss Z
        tickets = Client.getTreeSet();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        for(Ticket ticket:tickets){
            ticket.getPrice();//float
            ticket.getCoordinates().getY();//double
            ticket.getCoordinates().getX();//float
            NumberFormat numberFormat=NumberFormat.getInstance(currentLocale);
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(currentLocale);
            //String price =numberFormat.format( ticket.getPrice());
            String X =numberFormat.format(ticket.getCoordinates().getY());
            String Y =numberFormat.format(ticket.getCoordinates().getX());
            try{
                ticket.setCreationDate(simpleDateFormat.parse(ticket.getTime()));
            }catch (Exception e){
                e.printStackTrace();
            }
            ResourceBundle resourceBundle =ResourceBundle.getBundle("locale",currentLocale);
            SimpleDateFormat sD=new SimpleDateFormat(resourceBundle.getString("date"));
            canvas.addVisual(ticket);
            tableModel.addRow(new Object[]{
                    ticket.getId(),ticket.getName(),X,Y,
                    currencyInstance.format(ticket.getPrice()),ticket.getComment(),ticket.getType(),ticket.getEvent().getEventType(),
                    ticket.getEvent().getName(),ticket.getEvent().getId(),ticket.getRefundable(),
                    ticket.getUser().getName(),sD.format(ticket.getCreationDate())});
        }
    }

    // public static void main()  {
    //     tickets=Client.getTreeSet();
    //     frame=new MainForm();
    //     frame.setTitle("Main window");
    //     frame.setVisible(true);
    //     frame.setBounds(10, 10, 1020, 800);
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     //frame.setResizable(false);
    //     frame.setLocationRelativeTo(null);
        
    //     setDateAndADD();
    // }

    public static void main() {
        VisualTicket.loadResources();
        ImageMaster.loadResources();
        MainForm frame = new MainForm();
        //frame.setLanguage();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        frame.setSize(1100, 900);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDateAndADD();
    }
}

