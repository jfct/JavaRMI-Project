/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.Serializable;

/**
 *
 * @author JFCT
 */
public class Heartbeat implements Serializable{
    private String ip;
    private Integer port;
    private String message;
    private Boolean primario;
    
    private String nomeServico;



    public Heartbeat(String ip, Integer port, Boolean primario, String nomeServico){
        
        this.ip = ip;
        this.port = port;
        this.message = "";
        this.primario = primario;
        this.nomeServico = nomeServico;
        
    }

    public String getIp(){
        return ip;
    }

    public Integer getPort(){
        return port;
    }

    public String getMessage(){
        return message;
    }

    public Boolean getPrimario(){
        return primario;
    }
    
    public void setIp(String ip){
        this.ip = ip;
    }

    public void setPort(Integer port){
        this.port = port;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setPrimario(Boolean primario){
        this.primario = primario;
    }
    
    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }
}
