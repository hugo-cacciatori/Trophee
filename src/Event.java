import java.sql.Date;
import java.util.ArrayList;

public class Event {
    //Attrib
    private int id;
    private String nom;
    private Date dateStart;
    private Date dateEnd;
    private int clubID;
	private ArrayList<Participant> participants;
	private int sportID;
	private String description;
	private Participant winner;
    private String score;
	private boolean isOpen;

    public Event(int id, String n, Date ds, Date de, int clubID,  int sportID, String des) {
        this.id = id;
        this.nom = n;
        this.dateStart = ds;
        this.dateEnd = de;
        this.clubID = clubID;
        this.sportID = sportID;
        this.description = des;
        this.isOpen = true;
        this.participants = new ArrayList<Participant>();
    }

    public void setResults(Participant p){
        this.winner = p;
        this.isOpen = true;
    }

    //GETTER
    
    public ArrayList getParticipants(){
        return this.participants;
    }
    
    public String getNom(){
        return this.nom;
    }
    
    public Date getDateStart(){
        return this.dateStart;
    }
    public Date getDateEnd(){
        return this.dateEnd;
    }

    public Participant getWinner(){
        return this.winner;
    }
	
	public void close(){
		this.isOpen = false;
	}
}
