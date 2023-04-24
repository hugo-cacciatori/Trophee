import java.sql.ResultSet;
import java.sql.SQLException;

public class Team extends Participant{
    private int capitaineID;
    private Sport sport;
    private boolean available;
    private int clubID;

    Team(int id, String nom, int clubID){
        super(id, nom);
        this.clubID = clubID;
        this.capitaineID = 0;
        setAvailability();
    }

    Team(int id, String nom, int clubID, int capitaineID){
        super(id, nom);
        this.capitaineID = capitaineID;
        this.clubID = clubID;
        setAvailability();
    }

    public void setAvailability(){
        ResultSet rs = DatabaseManager.getInstance().getTeamPlayers(this.getID());
        int count = 0;
        try {
            while(rs.next()){
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(count<2){
            this.available = false;
            DatabaseManager.getInstance().setTeamUnavailable(this.getID());
        }
        else{
            this.available = true;
            DatabaseManager.getInstance().setTeamAvailable(this.getID());
        }
    }

    

    //GETSET

    public void setSport(Sport s){
        this.sport =s;
    }

    public boolean isAvailable(){
        return this.available;
    }

    public Sport getSport(){
        return this.sport;
    }
    public void setCapitaineID(int p){
        this.capitaineID = p;
    }
    public int getCapitaineID(){
        return this.capitaineID;
    }

}
