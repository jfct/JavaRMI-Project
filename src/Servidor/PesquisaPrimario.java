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
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author JFCT
 */
public class PesquisaPrimario extends Thread {
      private Boolean masterServer;

    public PesquisaPrimario(){
        this.masterServer = false;
        
        System.out.println("\nCriada Thread de Pesquisa");
        
    }

    public Boolean getMasterServer(){
        return masterServer;
    }

    public void search(){
        try{
            InetAddress address = InetAddress.getByName(MULTICAST_ADDRESS);
            
            byte[] data = new byte[1024];
            
            // Join the multicast group 225.15.15.15 on port 7000
            try(MulticastSocket clientSocket = new MulticastSocket(MULTICAST_PORT)){
                clientSocket.joinGroup(address);
                clientSocket.setSoTimeout(100);
                
                // Receive heartbeat packet
                DatagramPacket msgPacket = new DatagramPacket(data, data.length);
                clientSocket.receive(msgPacket);

                Heartbeat hbMessage = new Serializa().deserializeHeartbeat(data);

                // Handle the received heartbeat
                masterServer = hbMessage.getPrimario();
            }
        } catch (IOException e){
        }
    }
}
