/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ase5;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.org.apache.bcel.internal.generic.GOTO;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;




public class Broker extends Thread{
    private static String[] NomDuDossier;
    private static String[] NomDuFichier;
    private static String dossier;
    private static final int port = 9809;
    public static String localhost= "127.0.0.1";
    public static int somme=0;
    
    
public int search(String x, String[] array){
    for(int i=0; i<array.length;i++){
        if(x.equalsIgnoreCase(array[i])){
            return i;
        }
    }
    return -1;
}

    public String[]  split(File file) throws FileNotFoundException, IOException{
    List<String> lines = new ArrayList<>();
    String line;
    String part1="";
    String part2="";
    
    BufferedReader br = new BufferedReader(new FileReader(file));
        while((line=br.readLine())!=null){
            lines.add(line);
        }
        
        String[] array = lines.toArray(new String[lines.size()]);
        
        for(int i=0; i<array.length/2;i++){
            part1=part1+" "+array[i];
        }
        for(int j=array.length/2; j<array.length;j++){
            part2=part2+" "+array[j];
        }
        String[] parts={part1,part2};
return parts;
}
 
@Override
public void run(){
    
    
        try {
            
            Random random = new Random();
            ServerSocket brokerServerSocket = new ServerSocket(port);
            System.out.println("Veuillez entrer le path du fichier...");
            Scanner sc = new Scanner (System.in);
            String dossier = sc.nextLine();
            File path = new File(dossier);
            NomDuDossier=path.list();
            for(int j=0;j<NomDuDossier.length;j++){
                System.out.println(NomDuDossier[j]);
            }
            while(true){
                System.out.println("En Attente de la connexion des clients");
                Socket bcSocket = brokerServerSocket.accept();
                System.out.println("Connexion établie avec le client... ");
                BufferedReader br = new BufferedReader(new InputStreamReader(bcSocket.getInputStream()));
                PrintWriter pw = new PrintWriter(bcSocket.getOutputStream(), true);
                String question = "Quel dossier voulez vous traiter?";
                pw.println(question);
                pw.println(Arrays.toString(NomDuDossier));
                String choix = br.readLine();
                int DossierIndex = search(choix,NomDuDossier);
                if(DossierIndex==-1){
                    System.err.println("Ce dossier n'éxiste pas");
                }
                else{
                    System.out.println("Le client a choisi " + NomDuDossier[DossierIndex]);
                    String question2 = "Veuillez saisir le mot à rechercher ...";
                    pw.println(question2);
                    String mot = br.readLine();
                    System.out.println("Le mot à rechercher est: " + mot);
                    System.out.println("Lancement des serveurs...");
                    
                    //Selectionner juste les fichiers txt
                    File directory = new File(dossier + "\\" +NomDuDossier[DossierIndex]);
                    File[] FilePath = directory.listFiles((d, name) -> name.endsWith(".txt"));
                    //////////////////////////////////////////////////////////
                    
                    //Liste des servers
                    Server[] servers = new Server[FilePath.length];
                    ///////////////////////////////////////////////////
                    
                    //List des objet JSON
                    List<String> gsonList = new ArrayList<>();
                    /////////////////////////////////////////////////////////
                    
                    //Instanciation d'un Objet GSON
                    Gson gson = new Gson();
                    /////////////////////////////////////////////////////

                    //Liste des Sockets
                    Socket[] bsSocket = new Socket[FilePath.length];
                    ///////////////////////////////////////////
                    for(int x= 0; x<FilePath.length; x++){
                        servers[x]=new Server(random.nextInt(9000));
                        servers[x].start();
                        System.out.println(servers[x].getPort());
                        bsSocket[x]=new Socket(localhost, servers[x].getPort());
                        //Split des fichiers
                        String[] parts = split(FilePath[x]);
                        for(int j=0; j<parts.length;j++){
                            String filepath = FilePath[x].toString();
                            Helper h = new Helper(filepath,j+1, parts[j],mot);
                            String jsonFormat = gson.toJson(h);
                            gsonList.add(jsonFormat);
                        }  
                    }
                    PrintWriter p;
                    //ObjectOutputStream oos;
                    String[] Jsonarray = gsonList.toArray(new String[gsonList.size()]);
                    
                    System.out.println(bsSocket.length);
                    System.out.println(Jsonarray.length);
                    
                    for(int i=0;i<(bsSocket.length)*2; i++){
                        int mod = i%bsSocket.length;
                        p = new PrintWriter(bsSocket[mod].getOutputStream(), true);
                        p.println(Jsonarray[i]);
                    }
                    System.out.println(Broker.somme);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     
}
public static void main(String[] args){
new Thread(new Broker()).start();
}
}


