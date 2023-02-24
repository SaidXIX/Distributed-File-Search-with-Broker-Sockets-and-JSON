/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ase5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client extends Thread {
public static int id=0;
public int ClientId() {return id++;}
public static String localhost= "127.0.0.1";
public static int BrokerPort=9809;

@Override 
public void run(){
    try {
        Socket bcSocket = new Socket(localhost,BrokerPort);
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(bcSocket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(bcSocket.getInputStream()));
        String question = br.readLine();
        String liste = br.readLine();
        System.out.println(question);
        System.out.println(liste);
        String choix = sc.nextLine();
        pw.println(choix);
        String question2 = br.readLine();
        System.out.println(question2);
        String mot = sc.nextLine();
        pw.println(mot);
       
        
    } catch (IOException ex) {
        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public static void main(String[] args){
    new Thread(new Client()).start();
}
}
