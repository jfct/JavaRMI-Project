/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author JFCT
 */
public interface ServicosArmazenamento extends Remote {
    //public long download(long a, long b) throws RemoteException;
    public String listaFicheiros() throws RemoteException;
    public ArrayList mostraFicheiros() throws RemoteException;
}
