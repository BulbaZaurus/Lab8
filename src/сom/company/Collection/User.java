package сom.company.Collection;

import сom.company.Networking.Cryptography.Encrypt;

import java.io.Serializable;
import java.util.Scanner;

public  class User implements Serializable {
    private static final long serialVersionUID = 4345734752321L;
    private String name;
    private String password;
    private  int id;

    public User(String name,String password){
        this.name=name;
        this.password= Encrypt.EncryptMD(password);

    }
    public User(String name,String password,int id){
        this.name=name;
        this.password=password;
        this.id=id;
    }
    public User(){}

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString(){
        return "Нечто  по имени   " +name+ "  было пробуждено";
    }
    public static String inputName(){
        System.out.println("Введите имя");
        String name = new Scanner(System.in).nextLine();
        if (name.isEmpty()){
            name = inputName();
        }
        return name;
    }
    public static  String inputPassword(){
        System.out.println("Введите пароль");
        String password = new Scanner(System.in).nextLine();
        if (password.isEmpty()){
            password = inputPassword();
        }
        return password;
    }

}
