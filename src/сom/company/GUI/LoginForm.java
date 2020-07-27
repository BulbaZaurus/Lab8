package сom.company.GUI;

import сom.company.Collection.User;
import сom.company.Collection.UserStatus;
import сom.company.Networking.Client;
import сom.company.Networking.LogIn;
import сom.company.Networking.Message;
import сom.company.Networking.Servermachine.Server;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static сom.company.Networking.Communicator.Write;
import static сom.company.Networking.Messenger.SendMessage;

public class LoginForm extends JFrame {
    static LoginForm frame = new LoginForm();

    public static LoginForm getFrame() {
        return frame;
    }

    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField userTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton registerButton = new JButton("REGISTER");
    JCheckBox showPassword = new JCheckBox("Show Password");




    LoginForm() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        buttonControl();

    }
    public void colorize(){
        if (userTextField.getText().equals("yyy"))
            userTextField.setBackground(new Color(150, 255, 150));
        else
            userTextField.setBackground(Color.WHITE);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        registerButton.setBounds(200, 300, 100, 30);
        passwordField.setEchoChar('*');


    }
    public void buttonControl(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = userTextField.getText();
                char[] pass = passwordField.getPassword();
                String password=new String(pass);
                User user=new User();
                user.setName(login);
                user.setPassword(password);
                if(Client.LoginUser(user)==true){
                    JOptionPane.showMessageDialog(null,"Вход систему успешно завершен");
                    MainForm.main();
                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException exception ){
                        exception.printStackTrace();
                    }
                    frame.setVisible(false);
                }
                else
                    JOptionPane.showMessageDialog(null,"Пользователь не распознан или сервер не доступен в данный момент"+
                            "\n Попробуйте снова");

            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = userTextField.getText();
                char[] pass = passwordField.getPassword();
                String password=new String(pass);
                User user=new User(login,password);
                if(Client.RegistrationUser(user)==true){
                    JOptionPane.showMessageDialog(null,"Регистрация завершенна");
                    MainForm.main();
                    try{
                        Thread.sleep(500);
                    }catch (InterruptedException exception ){
                        exception.printStackTrace();
                    }
                    frame.setVisible(false);

                }else {
                    JOptionPane.showMessageDialog(null,"Имя пользователя уже занято или сервер не доступен в данный момент"+
                            "\n Попробуйте снова");
                }
            }
        });
        showPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPassword.isSelected()){
                    passwordField.setEchoChar((char) 0);
                }else {
                    passwordField.setEchoChar('*');
                }
            }
        });
    }



    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(registerButton);
    }

    /*public static void main(String[] args) {
        LoginForm frame = new LoginForm();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize( 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

     */
    public static void StartLogin(){

        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setSize( 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        }

    }




