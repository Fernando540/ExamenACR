package buscaminas;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author nahomi
 */
public class Usuario {
    private String nombre;
    private String dificultad;
    private Date inicioT;
    private Date finT;
    private int isWinner;
    private Socket clientSocket;
    private BufferedReader br;
    private PrintWriter writer;

    public Usuario(String nombre, String dificultad, Date inicioT, Date finT, int isWinner) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.inicioT = inicioT;
        this.finT = finT;
        this.isWinner = isWinner;
    }

    public Usuario(String nombre, String dificultad, Date inicioT, Socket cl,BufferedReader br, PrintWriter writer) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.inicioT = inicioT;
        this.clientSocket=cl;
        this.br= br;
        this.writer=writer;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public Date getInicioT() {
        return inicioT;
    }

    public void setInicioT(Date inicioT) {
        this.inicioT = inicioT;
    }

    public Date getFinT() {
        return finT;
    }

    public void setFinT(Date finT) {
        this.finT = finT;
    }

    public int getIsWinner() {
        return isWinner;
    }
    
    public Socket getSocket() {
        return clientSocket;
    }
    
    public BufferedReader getBr() {
        return br;
    }
    
    public PrintWriter getWriter() {
        return writer;
    }

    public void setIsWinner(int isWinner) {
        this.isWinner = isWinner;
    }
    
    
    
}
