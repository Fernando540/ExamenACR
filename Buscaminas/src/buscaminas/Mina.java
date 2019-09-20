/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

/**
 *
 * @author nahomi
 */
public class Mina {
    private int posicionX;
    private int posicionY;
    private String tipo;
    private int tipoInt;

    public Mina(int posicionX, int posicionY, String tipo, int tipoInt) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.tipo = tipo;
        this.tipoInt = tipoInt;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTipoInt() {
        return tipoInt;
    }

    public void setTipoInt(int tipoInt) {
        this.tipoInt = tipoInt;
    }
    
    
}
