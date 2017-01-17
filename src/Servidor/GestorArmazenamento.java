/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Diretoria.Services;
import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author JFCT
 */
public class GestorArmazenamento extends UnicastRemoteObject implements ServicosArmazenamento {
 
    private final File serverDirectory;
    
    public GestorArmazenamento(File serverDirectory) throws RemoteException{
        super();    
        
        this.serverDirectory = serverDirectory;
        
    }

    @Override
    public String listaFicheiros() throws RemoteException {
        System.out.println("<> Pedido do cliente para lista de ficheiros ");
        
        return "LISTA FICHEIROS";
    }
    
    @Override
    public ArrayList mostraFicheiros() throws RemoteException {
       
        System.out.println("<Pedido " + serverDirectory +" > Cliente pediu acesso aos ficheiros");
        
        ArrayList lista = new ArrayList<>();
        File file = new File("");
        File dir = new File(file.getAbsolutePath()+"\\"+serverDirectory);
        
        File[] directoryFiles = dir.listFiles();

        for(File f : directoryFiles){
            DecimalFormat df = new DecimalFormat("0.00");
            
            if(f.isFile()){
                float sizeKb = 1024.0f;
                float sizeMb = sizeKb * sizeKb;
                float sizeGb = sizeMb * sizeKb;
                String size;
                
                if(f.length() < sizeMb)
                    size = df.format(f.length() / sizeKb) + " KB";
                else if(f.length() < sizeGb)
                    size = df.format(f.length() / sizeMb) + " MB";
                else
                    size = df.format(f.length() / sizeGb) + " GB";
                
                lista.add(f.getName() + " " + size);
            }
        }
    
        return lista;
        
    }
    
}
