import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.xml.crypto.Data;

public class Club {

    private int id;
    private Double winrate;
    private Double wins;
    private int losses;
    private String adresse;
    private String tel;
    private String mail;
    private User owner;
    private String nom;
    private String description;
    private Sport sport;
    private int userTag;
    private int sportID;
    private ImageIcon profilePic;

    public Club(int id, String n, String a, String t, String m, String d, int sportID, int userTag){
        this.id = id;
        this.nom = n;
        this.adresse = a;
        this.tel = t;
        this.mail = m;
        this.description = d;
        this.sportID = sportID;
        this.userTag = userTag;
        this.winrate = 0.0;
        this.wins = 0.0;
        this.losses = 0;
        this.sport = DatabaseManager.getInstance().getSport(sportID);
    }

    //METH

    // public void calcWinrate(){
    //     Double result = 0.0;
    //     Double nbWins = 0.0;
    //     int nbLosses = 0;
    //     for(Event e:this.historique){
    //         if(e.getWinner().getClub() == this){
    //             nbWins++;
    //         }
    //         else{
    //             nbLosses++;
    //         }
    //     }
    //     if(this.historique.size()!=0){
    //         result = nbWins/this.historique.size()*100;
    //     }
    //     this.winrate = result;
    //     this.losses = nbLosses;
    //     this.wins = nbWins;
    // }
  
    //GETSET

    public int getID(){
        return this.id;
    }

    public String getAdresse(){
        return this.adresse;
    }
    public void setAdresse(String a){
        this.adresse = a;
    }

    public Sport getSport(){
        return this.sport;
    }

    public int getSportID(){
        return this.sportID;
    }

    public void setSport(Sport s){
        this.sport = s;
    }
    
    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTel() {
        return this.tel;
    }
    
    public void setTel(String tel) {
        this.tel = tel;
    }
    
    public ImageIcon getProfilePic(){
        return this.profilePic;
    }

    public void setProfilePic(ImageIcon p){
        this.profilePic = p;
    }

    public void setWinrate(Double w){
        this.winrate = w;
    }

    public void setWins(Double w){
        this.wins = w;
    }

    public void setLosses(int l){
        this.losses = l;
    }

    public Double getWinrate(){
        return this.winrate;
    }
    public Double getWins(){
        return this.wins;
    }
    public int getLosses(){
        return this.losses;
    }

}
