/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

/**
 *
 * @author nahomi
 */
public class Usuario {
    private String nombre;
    private String dificultad;
    private String tiempo;
    private String fechaFin;
    private LocalDate inicioT;
    private LocalDate finT;
    private int isWinner;
    private Socket cl;
    private BufferedReader br;
    private PrintWriter writer;

    public Usuario(String tiempo, String nombre, String fecha) {
        this.nombre = nombre;
        this.fechaFin = fecha;
        this.tiempo = tiempo;
    }

    public Usuario(String nombre, String dificultad, LocalDate inicioT, Socket cl, BufferedReader br, PrintWriter writer) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.inicioT = inicioT;
        this.cl = cl;
        this.br = br;
        this.writer = writer;
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

    public LocalDate getInicioT() {
        return inicioT;
    }

    public void setInicioT(LocalDate inicioT) {
        this.inicioT = inicioT;
    }

    public LocalDate getFinT() {
        return finT;
    }

    public void setFinT(LocalDate finT) {
        this.finT = finT;
    }

    public int getIsWinner() {
        return isWinner;
    }

    public void setIsWinner(int isWinner) {
        this.isWinner = isWinner;
    }
    
    public Socket getSocket() {
        return cl;
    }
    public BufferedReader getBr() {
        return br;
    }
    public PrintWriter getWriter() {
        return writer;
    } 
        
    public String getTiempo() {
        return this.tiempo;
    }
    
    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
    
    public String getFechaFin() {
        return this.fechaFin;
    }
}
