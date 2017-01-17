/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diretoria;

import Utils.Heartbeat;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author JFCT
 */
public class Serializa {
    
    public <T> byte[] serializeObject(T object){
        try{
            byte[] obj;
            
            try (ByteArrayOutputStream bArrayOut = new ByteArrayOutputStream(1024)) {
                
                try (ObjectOutputStream oos = new ObjectOutputStream(bArrayOut)) {
                    oos.writeObject(object);
                    obj = bArrayOut.toByteArray();
                }
                
            }
            
            return obj;
            
        } catch(IOException e){
            System.out.println("IOException dentro da serialização do objecto");
            System.out.println(e.getMessage());
        } catch(IOError e){
            System.out.println("IOError dentro da serialização do objecto");
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
        public Heartbeat deserializeHeartbeat(byte[] data){
        try{
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Heartbeat hbMessage = (Heartbeat) ois.readObject();
            
            return hbMessage;
        } catch(IOException | ClassNotFoundException e){
            System.out.println("Exception -> Serializer.java deserializeHeartbeatMessage(): " + e.getMessage());
        }
        
        return null;
        
    }
        
}
