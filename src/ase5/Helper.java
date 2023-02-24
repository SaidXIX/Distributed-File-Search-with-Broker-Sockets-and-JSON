/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ase5;

/**
 *
 * @author poupou
 */
public class Helper {
    private String filepath;
    private int part;
    private String content;
    private String mot;
    public int nbc=0;
   
    
    public Helper(String filepath, int part, String content, String mot){
        this.filepath=filepath;
        this.part=part;
        this.content=content;
        this.mot=mot;
    }

    public String getFilepath() {
        return filepath;
    }

    public int getPart() {
        return part;
    }

    public String getContent() {
        return content;
    }

    public String getMot() {
        return mot;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMot(String mot) {
        this.mot = mot;
    }

    public int getNbc() {
        return nbc;
    }
    
    public int getNbr(String mot){
        String s = this.getContent();
        String arr[] = s.split(" ");
        for(int i=0;i<arr.length; i++){
            if(mot.equalsIgnoreCase(arr[i])){
                nbc++;
            }
        }
        
        return nbc;
    }
    
    
}
