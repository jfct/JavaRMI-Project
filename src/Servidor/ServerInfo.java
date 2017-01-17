/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Utils.Stopwatch;
import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.StringTokenizer;
import java.rmi.server.RMISocketFactory;

/**
 *
 * @author JFCT
 */
public class ServerInfo {
    
    private final int MIN_PORT_NUMBER;
    private final int MAX_PORT_NUMBER;
    
    private InetAddress ip;
    public String nomeServico;

    private Integer port;
    private Boolean listening;
    private Boolean masterServer;
    private File workingDirectory;
    private File serverDirectory;
    
    private ServerSocket serverSocket;
    
    public ServerInfo(InetAddress ip, String port, File workingDirectory, File serverDirectory){
        this.MIN_PORT_NUMBER = 7000;
        this.MAX_PORT_NUMBER = 30000;
        
        this.ip = ip;
        this.port = Integer.parseInt(port);
        this.listening = true;
        this.masterServer = false;
        this.workingDirectory = workingDirectory;
        this.serverDirectory = serverDirectory;
    }
    
    public void listen() throws UnknownHostException{
        
        // Look for a master server
        PesquisaPrimario searchPrimary = new PesquisaPrimario();
        Stopwatch stopwatch = new Stopwatch();
        
        System.out.println("Looking for a master server.");
        while(true){
            searchPrimary.search();
            
            if(searchPrimary.getMasterServer()){
                System.out.println("Master server found. I am now a secondary server.\n");
                searchPrimary.interrupt();
                break;
            }
            else{
                if(stopwatch.getElapsed() > 15){
                    System.out.println("Master server not found. I am now the master server.\n");
                    masterServer = true;
                    searchPrimary.interrupt();
                    break;
                }
            }
        }
        
        // Começar o envio de Heartbeats
        System.out.println("<" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "> Sending out heartbeats.");
        new ServerHeartbeat(InetAddress.getLocalHost().getHostAddress(), port, masterServer, nomeServico).start();
        
        System.out.println("<" + InetAddress.getLocalHost().getHostAddress()+  ":" + port + "> Listening for client requests.");
        
        // Ciclo para não fechar o servidor enquanto "ouve"
        while(listening){
            try{

            } catch(Exception e){
                System.out.println("Exception -> Server.java listen(): " + e.getMessage());
            }
        }
        
        closeServer();
        System.out.println("Connection was closed!");
    }
    
    public Boolean createServer(){
        try{
            
            // Criar Servidor server PORT 7000
            if(checkTcpPort(port, MIN_PORT_NUMBER, MAX_PORT_NUMBER)){
                serverSocket = new ServerSocket(port, 200, ip);
            }
            // Criar servidor secundário 7000 < PORT <= 30000
            else{
                while(!checkTcpPort(port, MIN_PORT_NUMBER, MAX_PORT_NUMBER)){
                    port = port + 1;
                    serverDirectory = new File(workingDirectory.getName() + "\\" + port.toString());
                }

                serverSocket = new ServerSocket(port, 200, ip);
            }
            
            nomeServico = "GereArmazenamento" + (port - 7000);
            
            // Criar diretoria
            if(!workingDirectory.exists())
                workingDirectory.mkdir();
            
            // Criar diretoria do servidor
            if(!serverDirectory.exists())
                serverDirectory.mkdir();
            
            
            System.out.println("Server created successfully on port: " + port + ".");
            
            GestorArmazenamento gestor = new GestorArmazenamento(serverDirectory);
            
            Naming.bind(nomeServico, (Remote) gestor);
            
            
            return true;
            
        } catch(Exception e){
            System.out.println("Exception -> Server.java createServer(): " + e.getMessage());
        }
        
        return false;
    }
    
    public Boolean checkTcpPort(Integer port, Integer minPort, Integer maxPort){
        if (port < minPort || port > maxPort){
            throw new IllegalArgumentException("Exception -> Invalid start port: " + port);
        }

        ServerSocket ss = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            return true;
        } catch (IOException e){
        } finally {
            if (ss != null){
                try{
                    ss.close();
                } catch (IOException e){
                    /* should not be thrown */
                }
            }
        }

        return false;
    }
    
    public void closeServer(){
        try{
            
        } catch(Exception e){
            System.out.println("Exception -> Server.java closeServer(): " + e.getMessage());
        }
    }
    
    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }
    
}
