import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private String nom;
    private int tag;

    public User(int tag, String n){
        this.nom = n;
        this.tag = tag;
    }

    // public String[] clubListToString(){
    //     String[] s = new String[this.clubList.size()];
    //     int i = 0;
    //     for (Club c:this.clubList){
    //         s[i] = c.getNom();
    //         i++;
    //     }

    //     return s;
    // }

    
    //GETSET

    public int getTag(){
        return this.tag;
    }

    public String getNom(){
        return this.nom;
    }

    public void setNom(String s){
        this.nom = s;
    }
}
