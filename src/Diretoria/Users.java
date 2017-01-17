/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Diretoria;

/**
 *
 * @author JFCT
 */
public class Users {
    protected String username;
    protected String password;
    protected String servidor;
    protected boolean ocupado;
    

    
    Users(String _username,String _password){
        this.password=_password;
        this.username=_username;
        this.ocupado = false;
    }
    public String getUsername(){
        return this.username;
    }
    public  String getPassword(){
        return this.password;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
    
    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
}
