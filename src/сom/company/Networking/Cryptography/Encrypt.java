package сom.company.Networking.Cryptography;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    //MD2 или MD5
    public static String EncryptMD(String input)
    {
        try {
            // getInstance() method is called with algorithm MD2
            MessageDigest md = MessageDigest.getInstance("MD2");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            //BigInteger что это?
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            //что за сигнатура ?
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String EncryptSHA(String string){
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("SHA-512");
            byte[] data=messageDigest.digest(string.getBytes());
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<data.length;i++){
                sb.append(String.format("%02x",data[i]));
            }
            String pass=sb.toString();
            return pass;

        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }
}
