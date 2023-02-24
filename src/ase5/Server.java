/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ase5;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author poupou
 */
public class Server extends Thread{
private final int port;
private int busy;
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_RESET = "\u001B[0m";
public static String[] arr;


public static ServerSocket server;
public Server(int port) throws IOException{
    this.port=port;
    this.busy=0;
    server= new ServerSocket(port);
}
public int getPort(){
    return port;
}
public int getBusy() {
    return busy;
    }

public void setBusy(int busy) {
    this.busy = busy;
}

public static String[] getArr() {
        return arr;
}

public static void setArr(String[] arr) {
        Server.arr = arr;
}







@Override
public void run(){
    try {
        Semaphore sem = new Semaphore(1);
        int cmpt=0;
        System.out.println("Server: "+port+"  est crée");
        Socket sbSocket = server.accept();
        System.out.println("Server" + port + " Connexion établie avec le broker");
        PrintWriter pw = new PrintWriter(sbSocket.getOutputStream(), true);
        
        //DataInputStream dis = new DataInputStream(sbSocket.getInputStream());
        //ObjectInputStream ois = new ObjectInputStream(sbSocket.getInputStream());
        
        
        Gson gson = new Gson();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(sbSocket.getInputStream()));
        String jsonObject;
        
        while((jsonObject=br.readLine())!=null){
            //System.out.println("Server : " + port + " --->" + jsonObject);
            Helper h = gson.fromJson(jsonObject, Helper.class);
            String mot = h.getMot();
            System.out.println("Server : " + port + " --->" + h.getNbr(mot));
            sem.acquire();
            Broker.somme=Broker.somme+h.getNbr(mot);
            sem.release();
        }
       
        

       
    } catch (IOException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InterruptedException ex) {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    
}
public static void main(String[] args){
    
}
}
