//package envia;

import java.net.*;
import java.io.*;
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
        writer.write(ent);
        writer.flush();
        String svMsg = br.readLine();//sc.readLine();
        System.out.println("Server dice: "+svMsg);

        int[][] tablero=null;
        switch(ent){
            case "1":
                tablero= new int[9][9];
                break;
            case "2":
                tablero= new int[16][16];
                break;
            case "3":
                tablero= new int[16][30];
                break;
        }

        for(int i=0;i<tablero.length;i++){
            for(int j=0;j<tablero[0].length;j++){
                int a = Integer.parseInt(br.readLine());//sc.readLine();
                tablero[i][j]=a;
                if(a!=-1)
                    System.out.print(a+" ");
                else
                    System.out.print("# ");
            }
            System.out.println("");
        }
        
        
        
        /*try {
            int input = Integer.parseInt(hola);
            System.out.println("Soy un entero: " + input);
        } catch (Exception e) {
            System.out.println(hola);
        }*/
    }//main
}//class
