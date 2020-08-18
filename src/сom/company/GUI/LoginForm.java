package сom.company.GUI;

import сom.company.Collection.User;
import сom.company.Collection.command.CollectionCommand_Empty;
import сom.company.Networking.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Отвечает за регистрацию и авторизацию пользователя
 */
public class LoginForm extends JFrame {
    public static LoginForm frame = new LoginForm();
    static User user;
    public static Locale currentLocale = new Locale("ru","RU");
    // public static Locale getCurrentLocale() {return currentLocale;}
    // public static void setCurrentLocale(Locale currentLocale) {LoginForm.currentLocale = currentLocale;}

    String[] localeArray = {"Русский язык","македонски јазик","język polski","español"};
    JComboBox<String> localeBox = new JComboBox<>(localeArray);
    public static User getUser() {return user;}
    //public static String userLab = "ИМЯ ПОЛЬЗОВАТЕЛЯ";



    //public static LoginForm getFrame() {
        //return frame;
    //}
    //ResourceBundle resourceBundle = ResourceBundle.getBundle("locale_pl",Locale.getDefault());
    Container container = getContentPane();

    JPanel pane = new JPanel();
    JPanel butPane = new JPanel();
    JLabel userLabel = new JLabel();
    JLabel passwordLabel = new JLabel();
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton();
    JButton registerButton = new JButton();
    JCheckBox showPassword = new JCheckBox();
    JButton change = new JButton();

    JMenuBar barMenu = new JMenuBar();


    static  public String emptyField = "Одно или несколько полей пусты";
    public static void setEmptyField(String emptyField) {LoginForm.emptyField = emptyField;}

    static public String completeLogin = "Вход систему успешно завершен";
    public static void setCompleteLogin(String completeLogin) {LoginForm.completeLogin = completeLogin;}

    static  public String completeRegistration = "Регистрация завершена";
    public static void setCompleteRegistration(String completeRegistration) {LoginForm.completeRegistration = completeRegistration;}

    public static void setLoginFormError(String loginFormError) {LoginFormError = loginFormError;}
    static public String LoginFormError = "Пользователь не распознан или сервер недоступен в данный момент";
    static public String RegistrFormError = "Имя пользователя уже занято или сервер недоступен в данный момент";

    private LoginForm() {
        super("Dark Authorization");
        setLayoutManager();
        defineSizes();
        //setLocationAndSize();
        colorize();
        addComponentsToContainer();
        buttonControl();
        //setSize(340, 295);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setTitleWithLocale(){
        ResourceBundle resourceBundle = ResourceBundle.getBundle("locale",currentLocale);
        userLabel.setText(resourceBundle.getString("userLabel"));
        passwordLabel.setText(resourceBundle.getString("passwordLabel"));
        showPassword.setText(resourceBundle.getString("showPassword"));
        loginButton.setText(resourceBundle.getString("loginButton"));
        registerButton.setText(resourceBundle.getString("registerButton"));
        change.setText(resourceBundle.getString("change"));
        emptyField = resourceBundle.getString("emptyField");
        completeLogin = resourceBundle.getString("completeLogin");
        completeRegistration = resourceBundle.getString("completeRegistration");
        LoginFormError = resourceBundle.getString("LoginFormError");
        RegistrFormError = resourceBundle.getString("RegistrFormError");
    }

    public void setLayoutManager() {
        container.setLayout(new BorderLayout());
        pane.setLayout(new GridLayout(0,1,0,5));
        butPane.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
    }

    public void setLocationAndSize() {
        userLabel.setBounds(40, 30, 100, 30);
        passwordLabel.setBounds(40, 100, 100, 30);
        userTextField.setBounds(140, 30, 150, 30);
        passwordField.setBounds(140, 100, 150, 30);
        showPassword.setBounds(136, 130, 150, 30);
        loginButton.setBounds(40, 180, 100, 30);
        registerButton.setBounds(190, 180, 100, 30);
    }

    public void defineSizes(){
        //userTextField.setSz
        // userTextField.setPreferredSize(new Dimension(180, 30));
        // passwordField.setPreferredSize(new Dimension(180, 30));
        loginButton.setPreferredSize(new Dimension(130,30));
        registerButton.setPreferredSize(new Dimension(130,30));
    }
    
    public void colorize(){
        barMenu.setBackground(ImageMaster.darkestGray);
        container.setBackground(Color.darkGray);
        localeBox.setForeground(Color.lightGray);
        localeBox.setBackground(ImageMaster.darkestGray);
        localeBox.setFocusable(false);
        localeBox.setRenderer(new ComboRenderer(localeBox.getRenderer()));
        change.setContentAreaFilled(false);
        change.setForeground(Color.lightGray);
        change.setFocusPainted(false);
        userLabel.setForeground(Color.lightGray);
        passwordLabel.setForeground(Color.lightGray);
        showPassword.setForeground(Color.lightGray);
        showPassword.setFocusPainted(false);
        showPassword.setContentAreaFilled(false);
        showPassword.setOpaque(false);
        passwordField.setEchoChar('*');
        loginButton.setBackground(Color.lightGray);
        registerButton.setBackground(Color.lightGray);
        userTextField.setBorder(new LineBorder(Color.lightGray, 2));
        passwordField.setBorder(new LineBorder(Color.lightGray, 2));
        userTextField.setBackground(Color.white);
        passwordField.setBackground(Color.white);
        userTextField.setFont(ImageMaster.font);
        passwordField.setFont(ImageMaster.font);
        pane.setOpaque(false);
        pane.setBorder(new EmptyBorder(10, 40, 0, 40));
        butPane.setOpaque(false);

        passwordLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        userLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        showPassword.setVerticalAlignment(SwingConstants.TOP);
    }

    public void addComponentsToContainer() {
        setJMenuBar(barMenu);
        barMenu.add(localeBox);
        barMenu.add(change);
        pane.add(userLabel);
        pane.add(userTextField);
        pane.add(passwordLabel);
        pane.add(passwordField);
        pane.add(showPassword);
        butPane.add(loginButton);
        butPane.add(registerButton);
        container.add(butPane, BorderLayout.SOUTH);
        container.add(pane);
    }

    public void buttonControl(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userTextField.getText().isEmpty() || passwordField.getPassword().length==0)
                    JOptionPane.showMessageDialog(null,emptyField);
                else{
                    String login = userTextField.getText();
                    char[] pass = passwordField.getPassword();
                    String password = new String(pass);
                    user = new User();
                    user.setName(login);
                    user.setPassword(password);
                    if(Client.LoginUser(user)){
                        Client.Process(new CollectionCommand_Empty());
                        Client.setUser(user);
                        SwingUtilities.invokeLater(()->MainForm.main());
                        try{
                            Thread.sleep(500);
                        }catch (InterruptedException exception ){
                            exception.printStackTrace();
                        }
                        frame.setVisible(false);
                    }
                    else
                        JOptionPane.showMessageDialog(null,LoginFormError);
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(userTextField.getText().isEmpty() | passwordField.getPassword().length==0)
                    JOptionPane.showMessageDialog(null,emptyField);
                else{
                    String login = userTextField.getText();
                    char[] pass = passwordField.getPassword();
                    String password = new String(pass);
                    user = new User();
                    user.setName(login);
                    user.setPassword(password);
                    if(Client.RegistrationUser(user)==true){
                        JOptionPane.showMessageDialog(null,completeRegistration);
                        Client.setUser(user);
                        Client.Process(new CollectionCommand_Empty());
                        SwingUtilities.invokeLater(()->MainForm.main());
                        try{
                            Thread.sleep(500);
                        }catch (InterruptedException exception ){
                            exception.printStackTrace();
                        }
                        frame.setVisible(false);
                    }
                    else
                        JOptionPane.showMessageDialog(null,RegistrFormError);
                }
            }
        });
        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPassword.isSelected())
                    passwordField.setEchoChar((char) 0);
                else 
                    passwordField.setEchoChar('*');
            }
        });
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((localeBox.getSelectedItem().toString().equals("język polski")))
                    currentLocale = new Locale("pl","PL");
                else if((localeBox.getSelectedItem().toString().equals("Русский язык")))
                    currentLocale = new Locale("ru","RU");
                else if((localeBox.getSelectedItem().toString().equals("македонски јазик")))
                    currentLocale = new Locale("mk","MK");
                else if((localeBox.getSelectedItem().toString().equals("español")))
                    currentLocale = new Locale("es","CR");
                System.out.println(currentLocale);
                setTitleWithLocale();
            }
        });
    }

    public static void StartLogin(){

        frame.passwordField.setText("");
        frame.userTextField.setText("");
        frame.setTitleWithLocale();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        }
    }




