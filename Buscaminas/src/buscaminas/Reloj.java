/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

/**
 *
 * @author ferna
 */
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author D07114915
 */
public class Reloj extends Thread {

    private Thread thread = null;
    private String hour, min, seg, sTime;
    private int h, m, s, tot;
    private Label lab;

    /*public static void main(String[] args) {
        Reloj t = new Reloj();
        t.startTimer(00);
    }*/
    public Reloj(Label lab) {
        h = 0;
        m = 0;
        s = 0;
        tot = 0;
        hour ="00";
        min = "00";
        seg = "00";
        sTime = "00:00:00";
        this.lab=lab;
    }

    public void startTimer() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void stopTimer() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    public void setTime() {
        if (s == 59) {
            this.s=0;
            if (m == 59) {
                this.m=0;
                this.h++;
            } else {
                this.m++;
            }
        } else {
            this.s++;
        }
        if(this.s<10){
            seg=("0"+this.s);
        }else{
            seg=(Integer.toString(this.s));
        }
        if(this.m<10){
            min=("0"+this.m);
        }else{
            min=(Integer.toString(this.m));
        }
        if(this.h<10){
            hour=("0"+this.h);
        }else{
            hour=(Integer.toString(this.h));
        }
        sTime=hour+":"+min+":"+seg;
        //System.out.println(sTime);
        /*Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lab.setText(sTime);
            }
        });*/
        
        Platform.runLater(()->lab.setText(sTime));
        
    }

    public String getTime() {
        return sTime;
    }
    
    public int getTotTime() {
        return tot;
    }

    @Override
    public void run() {
        try {
            while (!thread.isInterrupted()) {
                sleep(1000);
                this.tot++;
                setTime();
                
            }
        } catch (Exception e) {
        }

    }
}//end of class
