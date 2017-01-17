/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diretoria;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JFCT
 */
public class VerificaHeartbeat extends TimerTask {
    private Integer seconds;
    private Integer minutes;
    private Integer hours;
    private Integer current;
    private List<informacaoServidor> servers;
    
    VerificaHeartbeat(List<informacaoServidor> servers){
        this.servers = servers;
    }
    
    @Override
    public void run(){
        this.seconds = Calendar.getInstance().get(Calendar.SECOND);
        this.minutes = Calendar.getInstance().get(Calendar.MINUTE);
        this.hours = Calendar.getInstance().get(Calendar.HOUR);
        this.current = seconds + (minutes * 60) + (hours * 60 * 60);

        if(!servers.isEmpty()){
           //System.out.println("[TIME]: <" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "> Checking Heartbeats.\n");
            
            for(int i = 0; i < servers.size(); i++){
                this.seconds = Calendar.getInstance().get(Calendar.SECOND);
                this.minutes = Calendar.getInstance().get(Calendar.MINUTE);
                this.hours = Calendar.getInstance().get(Calendar.HOUR);
                this.current = seconds + (minutes * 60) + (hours * 60 * 60);
        
                // Se falhar os 3 ticks, remover o serviço
                if(servers.get(i).getFailedHeartbeats() >= 3){
                    try {
                        Naming.unbind(servers.get(i).getNomeServico());
                    } catch (RemoteException ex) {
                        Logger.getLogger(VerificaHeartbeat.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NotBoundException ex) {
                        System.out.println("ERRO - Serviço não está ligado à diretoria");
                    } catch (MalformedURLException ex) {
                        System.out.println("ERRO - URL Mal formado");
                    }
                    
                    System.out.println("<" + servers.get(i).getIp() + ":" + servers.get(i).getPort() + "> Storage server connection dropped and was removed(Unbind).");
                    

                    // Remove servidor, se for um servidor master, então dar esse papel ao próximo em fila
                    if(servers.get(i).getPrimario()&& servers.size() > 1){
                        servers.remove(i);
                        servers.get(0).setPrimario(true);
                        System.out.println("<" + servers.get(0).getIp() + ":" + servers.get(0).getPort() + "> Was set as the new master server.");
                    }
                    // Else just remove the dropped server
                    else{
                        servers.remove(i);
                        System.out.println("Storage server list is now empty.");
                        continue;
                    }
                }

                // Se falhar o heartbeat no tempo (5000ms) incrementar o contador de falhas
                if(current - servers.get(i).getStarted() > 5){
                   System.out.println("<" + servers.get(i).getIp() + ":" + servers.get(i).getPort() + "> Failed a heartbeat, current failed: " + (servers.get(i).getFailedHeartbeats() + 1) + ".");
                   servers.get(i).setFailedHeartbeats(servers.get(i).getFailedHeartbeats() + 1);
                }
            }
        }
    }
    
}
