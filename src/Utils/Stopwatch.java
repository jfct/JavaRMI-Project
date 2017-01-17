/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author JFCT
 */
public class Stopwatch {
    private final long started;

    public Stopwatch(){
        this.started = (System.currentTimeMillis() / 1000);
    }

    public long getElapsed(){
        long elapsed = (System.currentTimeMillis() / 1000);
        
        return elapsed - started;
    }
    
}
