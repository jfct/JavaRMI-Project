/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author JFCT
 */
public class Login implements Serializable{
    private String username;
    private String password;
    private String message;
    private Boolean loggedIn;
    private Boolean loggedOut;
    private String ip;
    private Integer port;

    public static final String LOGIN = "LOGIN";
    public static final String EXIT = "EXIT";
    
    public Login(){
        this.username = null;
        this.password = null;
        this.message = null;
        this.loggedIn = false;
        this.loggedOut = false;
        this.ip = null;
        this.port = null;
        
        getLogin();
        
    }

    public void getLogin(){
        ArrayList input;
        Scanner scanner = new Scanner(System.in);

        do{
            input = new ArrayList();
                        
            System.out.print("Username: ");
            input.add(scanner.nextLine());
            System.out.print("Password: ");
            input.add(scanner.nextLine());
            
        } while(input.size() < 2);
        
        this.username = input.get(0).toString();
        this.password = input.get(1).toString();
        
    }
    
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getMessage(){
        return message;
    }

    public Boolean getLoggedIn(){
        return loggedIn;
    }

    public Boolean getLoggedOut(){
        return loggedOut;
    }

    public String getIp(){
        return ip;
    }

    public Integer getPort(){
        return port;
    }

    public void setMessage(String message){
        this.message = message;
    }
    
    public void setLoggedIn(Boolean loggedIn){
        this.loggedIn = loggedIn;
    }

    public void setLoggedOut(Boolean loggedOut){
        this.loggedOut = loggedOut;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setPort(Integer port){
        this.port = port;
    }
}
