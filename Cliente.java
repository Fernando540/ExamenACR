//package envia;

import java.net.*;
import java.io.*;
import javax.swing.JFileChooser;
import java.math.BigInteger;
import java.util.Scanner;


/**
 *inst
 * @author ferna
 */
public class Cliente {

    public static void main(String[] args) throws IOException {

        int pto = 1234;
        String host = "127.0.0.1";
        Socket cl = new Socket(host, pto);

        BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
        PrintWriter writer = new PrintWriter(cl.getOutputStream(), true);
        DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
        DataInputStream dis = new DataInputStream(cl.getInputStream());

        System.out.println("Escribe algo: ");
        Scanner sc = new Scanner(System.in);
        String ent = sc.nextLine();//sc.nextLine();
        System.out.println("Enviaste: "+ent);
        //BigInteger bigInt = BigInteger.valueOf(ent);      
        //byte[] b = bigInt.toByteArray();
        writer.write(ent);
        writer.flush();
        
        String svMsg = br.readLine();//sc.readLine();
        System.out.println("Server dice: "+svMsg);
        
        /*try {
            int input = Integer.parseInt(hola);
            System.out.println("Soy un entero: " + input);
        } catch (Exception e) {
            System.out.println(hola);
        }*/
    }//main
}//class
