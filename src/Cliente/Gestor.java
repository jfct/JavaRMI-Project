/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;


import Diretoria.Services;
import Servidor.ServicosArmazenamento;
import Utils.Constantes;
import static Utils.Constantes.SEM_SERVIDORES;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import sun.security.krb5.Config;

/**
 *
 * @author JFCT
 */

// CLIENTE

public class Gestor {

    
    public static void main(String[] args){
        Login login;    
        String resposta;
        String ip;
        Services services;
        ServicosArmazenamento gestor;
        
        ArrayList input = new ArrayList();
        ArrayList ficheiros = new ArrayList();
        Scanner scanner = new Scanner(System.in);

        
        try{
            
            login = new Login();
            ip = args[0];
            int opcao = 0;
            
            services = (Services) Naming.lookup("rmi://"+ip+"/GereServices");
            
            while(!(services.confirmaUser(login.getUsername(), login.getPassword()))){
                System.out.println("< Login Errado > Tente Novamente");
                login.getLogin();
            }
            
            resposta = services.getRemoteInterface(login.getUsername());
            
            if(resposta.compareTo(SEM_SERVIDORES) == 0){
                System.out.println(resposta);
            }else{
                
                try{
                    gestor = (ServicosArmazenamento) Naming.lookup("rmi://"+resposta);                    

		do {
                    System.out.println("\n");
                    System.out.println("  Ligado a "+ resposta);
                    System.out.println("    1 - Mostra Ficheiros");
                    System.out.println("    2 - Logout");
                    System.out.println("    3 - Exit");
                    System.out.print("> ");
                    opcao = scanner.nextInt();
                    
                    switch (opcao) {
			case 1:
                            ficheiros = gestor.mostraFicheiros();
                            Mostra(ficheiros);
                            
                            break;
			case 2:
                            services.logoutUser(login.getUsername());
                            login.getLogin();
                            while(!(services.confirmaUser(login.getUsername(), login.getPassword()))){
                                System.out.println("< Login Errado > Tente Novamente");
                                login.getLogin();
                            }
                            
                            break;
			case 3:
                            services.logoutUser(login.getUsername());
                            System.exit(0);
                            
                            break;
			default:
                            System.out.println("Opção Inválida!");
                            
                            break;
			}
		} while (opcao != 3);

                }catch(Exception e){
                    System.out.println("ERRO - Não foi possivel ligar ao serviço");
                }

            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    // Mostra ficheiros da diretoria
    public static void Mostra(ArrayList lista){
        
        System.out.println("\n");
        
        for(int i = 0; i < lista.size(); i++){
            System.out.println("  >" + lista.get(i));
        }
        
    }
    
}
