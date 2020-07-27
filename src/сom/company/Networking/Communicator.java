package сom.company.Networking;

import java.io.InputStream;
import java.util.Scanner;

public class Communicator {
    private static Scanner scanner;

    public static String Read(){
        if(scanner.hasNext()){
            return scanner.nextLine();
        }else{
            InitReader(System.in);
        }

        return scanner.nextLine();
    }

    public static void InitReader(InputStream input){
        scanner = new Scanner(input);
    }

    public static void Write(String str){
        System.out.println(str);
    }

    public static void WriteStringArray(String[] strA){
        if(strA!=null) {
            for (String str: strA) {
                Write(str);
            }
        }
    }
}