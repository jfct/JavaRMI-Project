/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diretoria;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author JFCT
 */

// INTERFACE DOS SERVIÇOS POSSIVEIS
// DIRETORIA

public interface Services extends Remote{
    public long add(long a, long b) throws RemoteException;
    public boolean confirmaUser(String username, String password) throws RemoteException;
    public void logoutUser(String username) throws RemoteException;
    public String getRemoteInterface(String username) throws RemoteException;
    
    
}
