/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Diretoria.Serializa;
import static Utils.Constantes.MULTICAST_ADDRESS;
import static Utils.Constantes.MULTICAST_PORT;
import Utils.Heartbeat;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author JFCT
 */
public class ServerHeartbeat extends Thread {
    public String ip;
    public Integer port;
    public Boolean masterServer;
    
    public String nomeServico;

    public ServerHeartbeat (String ip, Integer port, Boolean masterServer, String nomeServico){
        this.ip = ip;
        this.port = port;
        this.masterServer = masterServer;
        this.nomeServico = nomeServico;
    }
    
    @Override
    public void run(){
        try{
            InetAddress address = InetAddress.getByName(MULTICAST_ADDRESS);
            
            try(DatagramSocket serverSocket = new DatagramSocket()){
                while(true){
                    Heartbeat hbMessage = new Heartbeat(ip, port, masterServer, nomeServico);
                    
                    byte[] data = new Serializa().serializeObject(hbMessage);
                    
                    DatagramPacket packet = new DatagramPacket(data, data.length, address, MULTICAST_PORT);
                    
                    serverSocket.send(packet);
                    
                    Thread.sleep(5000);
                }
            } catch(InterruptedException e){
                System.out.println("Exception -> Server.java [Inner] run(): " + e.getMessage());
            }
        } catch(IOException e){
            System.out.println("Exception -> Server.java [Outer] run(): " + e.getMessage());
        }
    }
}
