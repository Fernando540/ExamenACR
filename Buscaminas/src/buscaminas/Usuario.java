/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

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

    public Usuario(String nombre, String dificultad, Date inicioT, Date finT, int isWinner) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.inicioT = inicioT;
        this.finT = finT;
        this.isWinner = isWinner;
    }

    public Usuario(String nombre, String dificultad, Date inicioT) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.inicioT = inicioT;
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

    public void setIsWinner(int isWinner) {
        this.isWinner = isWinner;
    }
    
    
    
}