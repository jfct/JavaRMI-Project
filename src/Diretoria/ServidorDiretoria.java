/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diretoria;

import Servidor.Server;
import static java.lang.System.exit;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteRef;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JFCT
 */
public class ServidorDiretoria {
    
    
    
    ServidorDiretoria() {
        try{
            
            LocateRegistry.createRegistry(1099);
            GestorDiretoria diretoria = new GestorDiretoria();
            
            if(!bindService(diretoria)){
                return;
            }
            
        }catch(RemoteException r){
            
            r.getMessage();
            System.out.println("Registry in port (1099) already exists");
            System.out.println("Server closing...");
            
            exit(0);
            
        }catch(Exception e){
            
            e.getMessage();
            System.out.println("Ocorreu um erro! A fechar...");
            
        }
        
        System.out.println("Servidor Diretoria Criado");
        
        
        System.out.println("À espera de heartbeats...");
        
    }
    
    public static boolean bindService(GestorDiretoria dir){
        try{
            Naming.bind("GereServices", (Remote) dir);
        }catch(AlreadyBoundException e){
            return false;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServidorDiretoria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ServidorDiretoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public static void main(String[] args){
               
        System.out.println("A criar Servidor Diretoria...");
        new ServidorDiretoria();
        
    }
    
}
