/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diretoria;

import java.util.Calendar;

/**
 *
 * @author JFCT
 */
public class informacaoServidor {
    
    public String ip;

    public String nomeServico;
    public Integer port;
    public Boolean primario;
    public Boolean available;
    
    
    private Integer seconds;
    private Integer minutes;
    private Integer hours;
    private Integer started;
    private Integer failedHeartbeats;

    informacaoServidor(String ip, Integer port, Boolean primario, String nomeServico){
        this.ip = ip;
        this.port = port;
        this.primario = primario;
        this.nomeServico = nomeServico;
        
        this.available = true;
        this.seconds = Calendar.getInstance().get(Calendar.SECOND);
        this.minutes = Calendar.getInstance().get(Calendar.MINUTE);
        this.hours = Calendar.getInstance().get(Calendar.HOUR);
        this.started = seconds + (minutes * 60) + (hours * 60 * 60);
        this.failedHeartbeats = 0;
    }

    public String getIp(){
        return ip;
    }

    public Integer getPort(){
        return port;
    }
    
    public Boolean getPrimario(){
        return primario;
    }

    public Boolean getAvailable(){
        return available;
    }
    
    public Integer getStarted(){
        return started;
    }

    public Integer getFailedHeartbeats(){
        return failedHeartbeats;
    }

    public void setPrimario(Boolean primario){
        this.primario = primario;
    }

    public void setAvailable(Boolean available){
        this.available = available;
    }
    
    public void setStarted(Integer started){
        this.started = started;
    }

    public void setFailedHeartbeats(Integer heartbeats){
        this.failedHeartbeats = heartbeats;
    }
    
    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }
}
