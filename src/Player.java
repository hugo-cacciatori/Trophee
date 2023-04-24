import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Player extends Participant{
    private String tel;
    private String age;
    private String mail;
    private String sexe;
    private boolean sexBool;

    public Player(int id, String nom, String a, boolean x, String t, String m){
        super(id, nom);
        this.age = a;
        this.sexBool = x;
        updateSex();
        this.tel = t;
        this.mail = m;
    }

    public Player() 
    {
    }

    //Methodes


    public void updateSex() {
        if(this.sexBool == true){
            this.sexe = "Homme";
        }
        else{
            this.sexe = "Femme";
        }
    }

    //GETSET

    public boolean getSexBool(){
        return this.sexBool;
    }
    public void setSexBool(boolean sb){
        this.sexBool = sb;
    }

    public String getAge(){
        return this.age;
    }
    public String getTel(){
        return this.tel;
    }
    public String getMail(){
        return this.mail;
    }
    public String getSex(){
        return this.sexe;
    }

    public void setMail(String newMail){
        this.mail = newMail;
    }
    public void setTel(String newTel){
        this.tel = newTel;
    }
    public void setAge(String newAge){
        this.age = newAge;
    }

    
    
}
