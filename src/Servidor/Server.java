/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;


import Diretoria.GestorDiretoria;
import static Utils.Constantes.MULTICAST_PORT;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author JFCT
 */

// SERVIDOR

public class Server {
    
    
     public static void main(String[] args) throws UnknownHostException{
        // Check for correct argument syntax
        if(args.length != 1){
            System.out.println("Syntax: java port directory");
            return;
        }
        
        InetAddress localhost;
        
        try{
            localhost = InetAddress.getLocalHost();
        } catch(IOException e){
            System.out.println("Exception -> StorageService.java main(): " + e.getMessage());
            return;
        }
        
        // File.separator 
        ServerInfo server = new ServerInfo(localhost, MULTICAST_PORT.toString() , new File(args[0]), new File(args[0] + File.separator + MULTICAST_PORT.toString()));
        
        // Close the program, if it fails to create the server on specified port
        if(!server.createServer())
            return;
        
        server.listen();
    }
    
}
