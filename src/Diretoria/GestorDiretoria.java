/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diretoria;


import Diretoria.Services;
import Utils.Constantes;
import static Utils.Constantes.SEM_SERVIDORES;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JFCT
 */

// STUB build

public class GestorDiretoria extends UnicastRemoteObject implements Services{
    
    private static final long serialVersionUID = 1L;
    public static final  int ListeningPort = 7000;
    public static final String ListeningAddressGroup = "225.15.15.15";
    
    
    
    // Lista com utilizadores que estão guardados no ficheiro txt
    ArrayList<Users> userList = new ArrayList<>();
    List<informacaoServidor> servidores = new ArrayList<>();
    
    
    public void LoadUsers(){
    
        File logins;
        File file = new File("");
        logins = new File(file.getAbsolutePath()+"\\logins.txt");
        
        try(BufferedReader br = new BufferedReader(new FileReader(logins))){
            for(String line; (line = br.readLine()) != null; ) {
                String[] loginInfo = line.split(" ");
                                
                System.out.println(loginInfo);
                
                userList.add(new Users(loginInfo[0], loginInfo[1]));

            }
        } catch(IOException e){
            System.out.println("Exception -> LoadUsers(): " + e.getMessage());
        }
        
        
        for(int i = 0; i < userList.size(); i++){
            System.out.println(userList.get(i).username);
        }
        
    }
    

    public GestorDiretoria() throws RemoteException{
        
        super();
        LoadUsers();
        
        // Cria processo(thread) que vai tratar dos heartbeats
        new processoHeartbeats(servidores).start();
        
    }
    
    @Override
    public long add(long a, long b) throws RemoteException{
        
        System.out.println("Parametro A: "+a);
        System.out.println("Parametro B: "+b);
        return a+b;
        
    }
    
    @Override
    public boolean confirmaUser(String username, String password) throws RemoteException{
           
        for(int i = 0; i < userList.size(); i++){     
            if( userList.get(i).getUsername().compareTo(username) == 0 && userList.get(i).getPassword().compareTo(password) == 0 && !(userList.get(i).isOcupado()) ){     
                // Atribuir servidor ao cliente
                userList.get(i).ocupado = true;
                return true;
            }   
        }
        return false;
        
    }
    
    @Override
    public String getRemoteInterface(String username) {
        
        for(int i = 0; i < servidores.size(); i++){
            if(servidores.get(i).getAvailable()){
                // Mete o servidor como ocupado
                servidores.get(i).setAvailable(false);
                // Atribui nome para utilizador saber a qual serviço está ligado
                for(int a = 0; a < userList.size(); a++){
                    userList.get(a).setServidor(servidores.get(i).getNomeServico());
                }
                
                return (servidores.get(i).getIp()+"/"+servidores.get(i).getNomeServico());
            }
        }
        
        return SEM_SERVIDORES;
        
    }
    
    @Override
    public void logoutUser(String username) throws RemoteException{
        
        for(int i = 0; i < userList.size(); i++){
            
            if(userList.get(i).getUsername().compareTo(username) == 0){
                userList.get(i).setOcupado(false);
                
                for(int a = 0; a< servidores.size() ; a++){
                    if(userList.get(i).getServidor().compareTo(servidores.get(a).getNomeServico()) == 0){
                        servidores.get(a).setAvailable(true);
                    }
                    
                }
                
            }
            
        }
        
    }
    
}
