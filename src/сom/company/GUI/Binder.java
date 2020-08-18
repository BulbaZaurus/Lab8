package сom.company.GUI;

import java.util.*;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import сom.company.Collection.command.*;
import сom.company.Networking.Client;

import static сom.company.GUI.LoginForm.currentLocale;
import static сom.company.GUI.MainForm.*;
import сom.company.Collection.Ticket;

/**
 * Класс,который отвечает за обработку событий
 */
public class Binder {

    private MainForm summoner;

    // ActionListener hideLoad= new ActionListener() {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         summoner.loading.setForeground(ImageMaster.darkestGray);
    //     }
    // };
    // Timer timer = new Timer(2000, hideLoad);

    public Binder(MainForm frm){
        this.summoner = frm;
    }

    public void buttonBehaviour(){

        summoner.log_out_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getNightMare()){
                    String[] btns = {"Try again","Yes","No","Banana"};
                    JOptionPane.showOptionDialog(null, "", "There's no escape from your nightmares...", 0, 
                        -1, ImageMaster.Iconize(ImageMaster.out), btns, "Try again");
                }
                else{
                    JOptionPane.showMessageDialog(null,logOut,"Are you freakin' serious?!", 0);
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException exception){
                        exception.printStackTrace();
                    }
                    unYellow();
                    clear();
                    summoner.setVisible(false);
                    LoginForm.StartLogin();
                }
            }
        });

        summoner.add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                add();
            }
        });

        summoner.update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        summoner.remove_by_id_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove_by_id();
            }
        });

        summoner.add_if_max_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add_if_max();
            }
        });

        summoner.remove_greater_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove_greater();
            }
        });

        summoner.filter_less_than_event.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               filter_event();
            }
        });

        summoner.print_field_comment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print_comment();
            }
        });

        summoner.print_price.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printPrice();
            }
        });

        summoner.refresh_butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //timer.start();
                //summoner.loading.setForeground(Color.red);
                refresh();
            }
        });

        summoner.help_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                help();
            }
        });

        summoner.info_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                info();
            }
        });

        summoner.dont_touch_button.addMouseListener(new MouseInputAdapter(){
            public void mouseClicked(MouseEvent e){
                if(getNightMare() && e.getClickCount() == 5){
                    summoner.dont_touch_button.setVisible(false);
                    summoner.dtb2.setVisible(true);
                }
            }
        });

        summoner.dont_touch_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!getNightMare()){
                    if(isYellow)
                        unYellow();
                    else
                        yellow();
                }
            }
        });

        summoner.dtb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                summoner.dtb2.setVisible(false);
                if(getNightMare())
                    summoner.dtb3.setVisible(true);
                else
                    summoner.dont_touch_button.setVisible(true);
            }
        });

        summoner.dtb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                summoner.dtb3.setVisible(false);
                summoner.dont_touch_button.setVisible(true);
                if(getNightMare())
                    JOptionPane.showMessageDialog(null, "Вы достигли комедии.\n          <Post iron>", "Easter Cock", 1, ImageMaster.Iconize(ImageMaster.nice));
                else
                    JOptionPane.showMessageDialog(null, "You are too smart.", "", 2);
            }
        });

        summoner.clear_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
               clear();
            }
        });

        summoner.execute_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path=null;
                try{
                    JFileChooser fileChooser=new JFileChooser();
                    setUpdateUI(fileChooser);
                    fileChooser.setDialogTitle("Choose the spell...");
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int result=fileChooser.showOpenDialog(summoner.container);
                    if(result==JFileChooser.APPROVE_OPTION)
                        path=fileChooser.getSelectedFile().toString();
                    if(path != null)
                        execute_script(path);
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        summoner.switcher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(summoner.switcher.isSelected()){
                    summoner.switcher.setText(nm);
                    summoner.switcher.setForeground(Color.magenta);
                    setNightMare(true);
                    darkenize();
                }
                else{
                    summoner.switcher.setText(norm);
                    summoner.switcher.setForeground(Color.lightGray);
                    setNightMare(false);
                    normalize();
                }
                for(Component comp : summoner.canvas.getComponents()) {
                    if(comp.getClass()==(new VisualTicket()).getClass())
                        ((VisualTicket)comp).reveal();
                }
                //summoner.revalidate();
            }
        });
        
        summoner.change_language_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((summoner.localeBox.getSelectedItem().toString().equals("język polski")))
                    LoginForm.currentLocale=new Locale("pl","PL");
                else if((summoner.localeBox.getSelectedItem().toString().equals("Русский язык")))
                    LoginForm.currentLocale=new Locale("ru","RU");
                else if((summoner.localeBox.getSelectedItem().toString().equals("македонски јазик")))
                    LoginForm.currentLocale=new Locale("mk","MK");
                else if((summoner.localeBox.getSelectedItem().toString().equals("español")))
                    LoginForm.currentLocale=new Locale("es","CR");
                summoner.setLanguage();
                refresh();
            }
        });
        summoner.scrollCanvas.getVerticalScrollBar().setUnitIncrement(8);
        summoner.scrollCanvas.getHorizontalScrollBar().setUnitIncrement(8);
    }

    public void add(){AddForm.main(summoner);}

    public void update_from_file(Long id){
        while (true) {
            try {
                ArrayList<Long> arrayID = new ArrayList<>();
                Client.Process(new CollectionCommand_Empty());
                setTickets(Client.getTreeSet());
                for (Ticket ticket : getTickets())
                    arrayID.add(ticket.getId());
                for (Ticket ticket : getTickets()){
                    if(ticket.getId()==id)
                        setTicketToRemove(ticket);
                }
                if (getTicketToRemove().getUser().getName().equals(LoginForm.getUser().getName()) & arrayID.contains(id))
                    UpdateIDForm.main(summoner);
                else
                    JOptionPane.showMessageDialog(null, id_not_found);
                break;
            } catch (Exception exception) {
                exception.printStackTrace();
                //JOptionPane.showMessageDialog(null, useID);
                break;
            }
        }
    }
    public void update(){
        setID(null);
        try {
            ArrayList<Long> arrayID = new ArrayList<>();
            Client.Process(new CollectionCommand_Empty());
            setTickets(Client.getTreeSet());
            for (Ticket ticket : getTickets())
                arrayID.add(ticket.getId());
            String result;
            try{
                result = String.valueOf(tableModel.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), table.convertColumnIndexToModel(0)));
            }
            catch(IndexOutOfBoundsException e){
                result = JOptionPane.showInputDialog(null,inputID);
            }
            setID(Long.parseLong(result));
            for (Ticket ticket : getTickets()){
                if(ticket.getId()==getID())
                    setTicketToRemove(ticket);
            }
            if (getTicketToRemove().getUser().getName().equals(LoginForm.getUser().getName()) & arrayID.contains(getID()))
                UpdateIDForm.main(summoner);
            else
                JOptionPane.showMessageDialog(null, id_not_found);
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(null, "NaN", "NaN", 2);
        }catch (Exception exception) {
            exception.printStackTrace();
            //JOptionPane.showMessageDialog(null, useID);
        }
    }

    public void remove_by_id_from_file(Long id){
        while (true) {
            try {
                ArrayList<Long> arrayID = new ArrayList<>();
                Client.Process(new CollectionCommand_Empty());
                setTickets(Client.getTreeSet());
                for (Ticket ticket : getTickets())
                    arrayID.add(ticket.getId());
                for (Ticket ticket : getTickets()){
                    if(ticket.getId()==id){
                        setTicketToRemove(ticket);
                        getTicketToRemove().setId(ticket.getId());
                    }
                }
                if (getTicketToRemove().getUser().getName().equals(LoginForm.getUser().getName()) & arrayID.contains(id)) {
                    Client.Process(new CollectionCommand_Remove_by_id(id));
                    //tableModel.removeRow(table.getSelectedRow());
                    refresh();
                    //JOptionPane.showMessageDialog(null, uspeh);
                } else
                    JOptionPane.showMessageDialog(null, id_not_found);
                break;
            } catch (Exception exception) {
                exception.printStackTrace();
                //JOptionPane.showMessageDialog(null, useID);
                break;
            }
        }
    }
    public void remove_by_id(){
        setID(null);
        setTicketToRemove(null);
        try {
            ArrayList<Long> arrayID = new ArrayList<>();
            Client.Process(new CollectionCommand_Empty());
            setTickets(Client.getTreeSet());
            for (Ticket ticket : getTickets())
                arrayID.add(ticket.getId());
            String result;
            try{
                result = String.valueOf(tableModel.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), table.convertColumnIndexToModel(0)));
            }
            catch(IndexOutOfBoundsException e){
                result = JOptionPane.showInputDialog(null,inputID);
            }
            setID(Long.parseLong(result));
            for (Ticket ticket : getTickets()){
                if(ticket.getId()==getID()){
                    setTicketToRemove(ticket);
                    getTicketToRemove().setId(ticket.getId());
                }
            }
            if (getTicketToRemove().getUser().getName().equals(LoginForm.getUser().getName()) & arrayID.contains(getID())) {
                Client.Process(new CollectionCommand_Remove_by_id(getID()));
                //tableModel.removeRow(table.getSelectedRow());
                refresh();
                //JOptionPane.showMessageDialog(null, uspeh);
            } else
                JOptionPane.showMessageDialog(null, id_not_found);
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(null, "NaN", "NaN", 2);
        } catch (Exception exception) {
            exception.printStackTrace();
            //JOptionPane.showMessageDialog(null, useID);
        }
    }

    public void add_if_max(){
        AddIfMaxForm.main(summoner);
    }

    public void remove_greater(){
        setPrice(null);
        try {
            String result;
            try{
                result = String.valueOf(tableModel.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), table.convertColumnIndexToModel(4)));
            }
            catch(IndexOutOfBoundsException e){
                result = JOptionPane.showInputDialog(null,inputPrice);
            }
            setPrice(Float.parseFloat(result));
            Client.Process(new CollectionCommand_Empty());
            TreeSet<Ticket> remove=Client.getTreeSet();
            boolean deleted = false;
            if(remove.size()!=0){
                for(Ticket ticket : remove){
                    setTicketToRemove(ticket);
                    if(getTicketToRemove().getPrice()>getPrice() & getTicketToRemove().getUser().getName().equals(LoginForm.getUser().getName())){
                        Client.Process(new CollectionCommand_Remove_greater(getTicketToRemove()));
                        deleted = true;
                    }
                    else
                        System.out.println("Кекс");
                }
                if(deleted)
                    refresh();
            }else
                JOptionPane.showMessageDialog(null,JonSnow);
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(null, "NaN", "NaN", 2);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void filter_event_from_file(Long ID){
        try {
            Client.Process(new CollectionCommand_Empty());
            TreeSet<Ticket> remove=Client.getTreeSet();
            TreeSet<Ticket> dantos=new TreeSet<>();
            Binder.clearTable();
            for(Ticket ticket:remove){
                if(ticket.getId()<ID)
                    dantos.add(ticket);
                else
                    System.out.println("Не достоин");
            }
            if(dantos.size()!=0){
                for(Ticket ticket2:dantos){
                    NumberFormat numberFormat=NumberFormat.getInstance(LoginForm.currentLocale);
                   // String price =numberFormat.format( ticket2.getPrice());
                    String X =numberFormat.format(ticket2.getCoordinates().getY());
                    String Y =numberFormat.format(ticket2.getCoordinates().getX());
                    NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(currentLocale);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                    try{
                        ticket2.setCreationDate(simpleDateFormat.parse(ticket2.getTime()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    ResourceBundle resourceBundle =ResourceBundle.getBundle("locale",LoginForm.currentLocale);
                    SimpleDateFormat sD=new SimpleDateFormat(resourceBundle.getString("date"));
                    tableModel.addRow(new Object[]{
                            ticket2.getId(), ticket2.getName(), X, Y,
                            currencyInstance.format(getPrice()), ticket2.getComment(), ticket2.getType(), ticket2.getEvent().getEventType(),
                            ticket2.getEvent().getName(), ticket2.getEvent().getId(), ticket2.getRefundable(), 
                            ticket2.getUser().getName(), sD.format(ticket2.getCreationDate())});
                }
            }else
                JOptionPane.showMessageDialog(null, Empty);
        } catch (Exception exception) {}
    }

    public void filter_event(){
        setID(null);
        try {
            String result;
            try{
                result = String.valueOf(tableModel.getValueAt(table.convertRowIndexToModel(table.getSelectedRow()), table.convertColumnIndexToModel(0)));
            }
            catch(IndexOutOfBoundsException e){
                result = JOptionPane.showInputDialog(null,inputID);
            }
            setID(Long.parseLong(result));
            Client.Process(new CollectionCommand_Empty());
            TreeSet<Ticket> remove=Client.getTreeSet();
            TreeSet<Ticket> dantos=new TreeSet<>();
            Binder.clearTable();
            for(Ticket ticket:remove){
                if(ticket.getId()<getID())
                    dantos.add(ticket);
                else
                    System.out.println("Не достоин");
            }
            if(dantos.size()!=0){
                for(Ticket ticket2:dantos){
                    NumberFormat numberFormat=NumberFormat.getInstance(LoginForm.currentLocale);
                    //String price =numberFormat.format( ticket2.getPrice());
                    String X =numberFormat.format(ticket2.getCoordinates().getX());
                    String Y =numberFormat.format(ticket2.getCoordinates().getY());
                    NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(currentLocale);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
                    try{
                        ticket2.setCreationDate(simpleDateFormat.parse(ticket2.getTime()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    ResourceBundle resourceBundle =ResourceBundle.getBundle("locale",LoginForm.currentLocale);
                    SimpleDateFormat sD=new SimpleDateFormat(resourceBundle.getString("date"));
                    tableModel.addRow(new Object[]{
                            ticket2.getId(), ticket2.getName(), X, Y,
                            currencyInstance.format(ticket2.getPrice()), ticket2.getComment(), ticket2.getType(), ticket2.getEvent().getEventType(),
                            ticket2.getEvent().getName(), ticket2.getEvent().getId(), ticket2.getRefundable(), 
                            ticket2.getUser().getName(), sD.format(ticket2.getCreationDate())});
                }
            }else
                JOptionPane.showMessageDialog(null, Empty);
        }catch(NumberFormatException e1){
            JOptionPane.showMessageDialog(null, "NaN", "NaN", 2);
        } catch (Exception exception) {}
    }

    public void print_comment(){
        Client.Process(new CollectionCommand_Empty());
        NavigableSet<Ticket> treereverse=Client.getTreeSet().descendingSet();
        if(treereverse.size()!=0){
            JOptionPane.showMessageDialog(null,remind);
            Binder.clearTable();
            for(Ticket ticket:treereverse){
                tableModel.addRow(new Object[]{
                    ticket.getId(), null, null, null,
                    null, ticket.getComment(), null, null,
                    null, ticket.getEvent().getId(), null, null, null});
            }
        }else
            JOptionPane.showMessageDialog(null, Empty);
    }

    public void printPrice(){
        Client.Process(new CollectionCommand_Empty());
        NavigableSet<Ticket> treereverse=Client.getTreeSet().descendingSet();
        if(treereverse.size()!=0){
            JOptionPane.showMessageDialog(null,remind);
            Binder.clearTable();
            for(Ticket ticket:treereverse){
                NumberFormat numberFormat=NumberFormat.getCurrencyInstance(LoginForm.currentLocale);
                String price =numberFormat.format( ticket.getPrice());
                tableModel.addRow(new Object[]{
                    ticket.getId(), null, null, null,
                    price, null,null, null,
                    null, ticket.getEvent().getId(), null, null, null});
            }
        }else                         
            JOptionPane.showMessageDialog(null, Empty);
    }

    public void help(){
        if(getNightMare()){
            JOptionPane.showMessageDialog(null, null, "No one will help you...", 0, ImageMaster.Iconize(ImageMaster.gus));
        }
        else{
            ResourceBundle resourceBundle=ResourceBundle.getBundle("locale",LoginForm.currentLocale);
            JOptionPane.showMessageDialog(null,resourceBundle.getString("help"), "Evil Wiki", 3);
        }
    }

    public void info(){
        JOptionPane.showMessageDialog(null, Size +getTickets().size()+" "+count, "Storage info", 1);
    }

    public void clear(){
        Binder.clearTable();
        summoner.canvas.blank();
    }

    public void execute_script(String path){
        try{
            Scanner scanner=new Scanner(new File(path));
            while (scanner.hasNext()){
                String line=scanner.nextLine(); // D:\try.txt
                String[] strings=line.split(" ");
                System.out.println(Arrays.toString(strings));
                switch (strings[0]){
                    case "help":
                        help();
                        break;
                    case "info":
                        System.out.println("Тут инфо");
                        info();
                        break;
                    case "show":
                        JOptionPane.showMessageDialog(null,evolution );
                        break;
                    case "add":
                        System.out.println("Тут комманда add");
                        if(strings.length>1)
                            System.out.println("должно быть только 1 аргумент");
                        add();
                        break;
                    case "update_id":
                        setID(null);
                        if(strings.length==2){
                            try {
                                String string=(String) strings[1] ;
                                setID(Long.parseLong(string));
                                System.out.println(getID());
                            }catch (Exception e){
                                e.printStackTrace();
                                System.out.println("Ошибка при чтении id");
                            }
                            update_from_file(getID());
                        }else
                            System.out.println("Аргументов должно быть ровно 2!");
                        break;
                    case "remove_by_id":
                        setID(null);
                        if(strings.length==2){
                            try {
                                String string=(String)strings[1];
                                setID(Long.parseLong(string));
                                System.out.println(getID());
                            }catch (Exception e){
                                e.printStackTrace();
                                System.out.println("Ошибка при удалении по id");
                            }
                            remove_by_id_from_file(getID());
                        }else
                            System.out.println("Аргументов должно быть ровно 2!");
                        break;
                    case "clear":
                        clear();
                        break;
                    case "execute_script":
                        if(strings.length==2){
                            String string=strings[1];
                            if(executed_scripts.contains(string)){
                                JOptionPane.showMessageDialog(null,You_shall_not_pass);
                                break;
                            }
                            try {
                                File dir1 = new File(strings[1]);
                                if(dir1.exists()){
                                    executed_scripts.add(string);
                                    execute_script(string);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }else
                            System.out.println("Аргументов должно быть ровно 2!");
                        break;
                    case "exit":
                        JOptionPane.showMessageDialog(null,goodbye,"GoodBye...",0);
                        System.exit(0);
                        break;
                    case "add_if_max":
                        add_if_max();
                        break;
                    case "remove_greater":
                        remove_greater();
                        break;
                    case "filter_less_than_event":
                        setID(null);
                        if(strings.length==2){
                            try {
                                String string=(String)strings[1];
                                setID(Long.parseLong(string));
                                System.out.println(getID());
                            }catch (Exception e){
                                e.printStackTrace();
                                System.out.println("Ошибка при удалении по id");
                            }
                            filter_event_from_file(getID());
                        }else
                            System.out.println("Аргументов должно быть ровно 2!");
                        break;
                    case "print_field_descending_comment":
                        print_comment();
                        break;
                    case "print_field_descending_price":
                        printPrice();
                        break;
                    default:
                        System.out.println("Я тебя не понимаю");
                        break;
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void refresh(){
        Client.Process(new CollectionCommand_Empty());
        clear();
        summoner.setDateAndADD();
    }

    public static void clearTable(){
        while(tableModel.getRowCount()>0)
            tableModel.removeRow(0);
    }

    public void yellow(){
        summoner.mainPanel.setBackground(Color.orange);
        summoner.cool.setVisible(true);
        JOptionPane.showMessageDialog(null, null, "Self-confidence", -1, ImageMaster.Iconize(ImageMaster.good));
        isYellow = true;
    }

    public void unYellow(){
        summoner.mainPanel.setBackground(Color.darkGray);
        summoner.cool.setVisible(false);
        isYellow = false;
    }

    public void normalize(){
        unYellow();
        summoner.mainPanel.setOpaque(true);
        table.setBackground(Color.darkGray);
        table.getTableHeader().setBackground(ImageMaster.darkestGray);
        summoner.barMenu.setBackground(ImageMaster.darkestGray);
        summoner.localeBox.setBackground(ImageMaster.darkestGray);

        summoner.add_button.setOpaque(true);
        summoner.help_button.setOpaque(true);
        summoner.update.setOpaque(true);
        summoner.info_button.setOpaque(true);
        summoner.clear_button.setOpaque(true);
        summoner.remove_by_id_button.setOpaque(true);
        summoner.add_if_max_button.setOpaque(true);
        summoner.remove_greater_button.setOpaque(true);
        summoner.filter_less_than_event.setOpaque(true);
        summoner.print_field_comment.setOpaque(true);
        summoner.print_price.setOpaque(true);
        summoner.execute_button.setOpaque(true);
        summoner.refresh_butt.setOpaque(true);

        summoner.add_button.setBorderPainted(true);
        summoner.help_button.setBorderPainted(true);
        summoner.update.setBorderPainted(true);
        summoner.info_button.setBorderPainted(true);
        summoner.clear_button.setBorderPainted(true);
        summoner.remove_by_id_button.setBorderPainted(true);
        summoner.add_if_max_button.setBorderPainted(true);
        summoner.remove_greater_button.setBorderPainted(true);
        summoner.filter_less_than_event.setBorderPainted(true);
        summoner.print_field_comment.setBorderPainted(true);
        summoner.print_price.setBorderPainted(true);
        summoner.execute_button.setBorderPainted(true);
        summoner.refresh_butt.setBorderPainted(true);

        summoner.add_button.setForeground(Color.darkGray);
        summoner.help_button.setForeground(Color.darkGray);
        summoner.update.setForeground(Color.darkGray);
        summoner.info_button.setForeground(Color.darkGray);
        summoner.clear_button.setForeground(Color.darkGray);
        summoner.remove_by_id_button.setForeground(Color.darkGray);
        summoner.add_if_max_button.setForeground(Color.darkGray);
        summoner.remove_greater_button.setForeground(Color.darkGray);
        summoner.filter_less_than_event.setForeground(Color.darkGray);
        summoner.print_field_comment.setForeground(Color.darkGray);
        summoner.print_price.setForeground(Color.darkGray);
        summoner.execute_button.setForeground(Color.darkGray);
        summoner.refresh_butt.setForeground(Color.darkGray);

        summoner.log_out_button.setForeground(Color.red);
        summoner.dont_touch_button.setForeground(Color.darkGray);
        summoner.dont_touch_button.setOpaque(true);
        summoner.change_language_button.setBackground(ImageMaster.darkestGray);
    }

    public void darkenize(){
        unYellow();
        summoner.mainPanel.setOpaque(false);
        table.setBackground(ImageMaster.violet);
        table.getTableHeader().setBackground(Color.black);
        summoner.barMenu.setBackground(Color.black);
        summoner.localeBox.setBackground(Color.black);

        summoner.add_button.setOpaque(false);
        summoner.help_button.setOpaque(false);
        summoner.update.setOpaque(false);
        summoner.info_button.setOpaque(false);
        summoner.clear_button.setOpaque(false);
        summoner.remove_by_id_button.setOpaque(false);
        summoner.add_if_max_button.setOpaque(false);
        summoner.remove_greater_button.setOpaque(false);
        summoner.filter_less_than_event.setOpaque(false);
        summoner.print_field_comment.setOpaque(false);
        summoner.print_price.setOpaque(false);
        summoner.execute_button.setOpaque(false);
        summoner.refresh_butt.setOpaque(false);

        summoner.add_button.setBorderPainted(false);
        summoner.help_button.setBorderPainted(false);
        summoner.update.setBorderPainted(false);
        summoner.info_button.setBorderPainted(false);
        summoner.clear_button.setBorderPainted(false);
        summoner.remove_by_id_button.setBorderPainted(false);
        summoner.add_if_max_button.setBorderPainted(false);
        summoner.remove_greater_button.setBorderPainted(false);
        summoner.filter_less_than_event.setBorderPainted(false);
        summoner.print_field_comment.setBorderPainted(false);
        summoner.print_price.setBorderPainted(false);
        summoner.execute_button.setBorderPainted(false);
        summoner.refresh_butt.setBorderPainted(false);

        summoner.add_button.setForeground(ImageMaster.darkGreen);
        summoner.help_button.setForeground(Color.darkGray);
        summoner.update.setForeground(ImageMaster.darkGreen);
        summoner.info_button.setForeground(Color.darkGray);
        summoner.clear_button.setForeground(ImageMaster.darkRed);
        summoner.remove_by_id_button.setForeground(ImageMaster.darkRed);
        summoner.add_if_max_button.setForeground(ImageMaster.darkGreen);
        summoner.remove_greater_button.setForeground(ImageMaster.darkRed);
        summoner.filter_less_than_event.setForeground(Color.darkGray);
        summoner.print_field_comment.setForeground(Color.darkGray);
        summoner.print_price.setForeground(Color.darkGray);
        summoner.execute_button.setForeground(new Color(101,0,119));
        summoner.refresh_butt.setForeground(Color.magenta);

        summoner.log_out_button.setForeground(Color.magenta);
        summoner.dont_touch_button.setForeground(ImageMaster.violet);
        summoner.dont_touch_button.setOpaque(false);
        summoner.change_language_button.setBackground(Color.black);
    }
}