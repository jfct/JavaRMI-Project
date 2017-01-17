/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diretoria;

import static Utils.Constantes.MULTICAST_ADDRESS;
import static Utils.Constantes.MULTICAST_PORT;
import Utils.Heartbeat;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author JFCT
 */
public class processoHeartbeats extends Thread {
 
    List<informacaoServidor> servidores = new ArrayList<>();
    
    
    public processoHeartbeats(List<informacaoServidor> servidores){
        this.servidores = servidores;
    }
    
    
    public void verificaHB(Heartbeat hb) {
        
        informacaoServidor servidorQueEnvia = new informacaoServidor(hb.getIp(), hb.getPort(), hb.getPrimario(), hb.getNomeServico());
        
        if(verificaServidor(servidorQueEnvia.getIp(), servidorQueEnvia.getPort())) {
            for(informacaoServidor s : servidores) {
                if(s.getIp().compareTo(servidorQueEnvia.getIp()) == 0 && s.getPort().compareTo(servidorQueEnvia.getPort()) == 0) {
                    System.out.println("<" + s.getIp() + ":" + s.getPort() + "> : Esta vivo...");
                    s.setStarted(servidorQueEnvia.getStarted());
                }
            }
        } else {
            System.out.println("<" + hb.getIp() + ":" + hb.getPort() + "> Added to active storage server list.");
            servidores.add(servidorQueEnvia);
        } 
    }
    
    public Boolean verificaServidor(String ip, Integer port){
        for(informacaoServidor s : servidores){
            if(s.getIp().compareTo(ip) == 0 && s.getPort().compareTo(port) == 0)
                return true;
        }
        
        return false;
    }

    @Override
    public void run() {
        
        System.out.println("Servidor vai escutar HB dos servidores de Armazenamento....");
        
        try {
            InetAddress addr = InetAddress.getByName(MULTICAST_ADDRESS);
            byte[] data = new byte[1024];
            
            try (MulticastSocket multicastSocket = new MulticastSocket(MULTICAST_PORT)) {
                multicastSocket.joinGroup(addr);
                
                new Timer().schedule(new VerificaHeartbeat(servidores), 0 , 5000);
                
                while(true) {
                    DatagramPacket datagramPacket = new DatagramPacket(data, data.length);
                    multicastSocket.receive(datagramPacket);
                    
                    Heartbeat hb = new Serializa().deserializeHeartbeat(data);
                    
                    verificaHB(hb);
                    
                    String[] lista = Naming.list(InetAddress.getLocalHost().getHostAddress());
                    
                    System.out.println(lista.length);

                }                
                
            }
        } catch (IOException e) {
             System.out.println("Exception -> HeartbeatClient.java() run(): " + e.getMessage());
        }
    }
    
}
