import java.util.ArrayList;

public abstract class Participant {

    private int id;
    private int clubID;
    private String nom;
    private Double winrate;
    private Double wins;
    private int losses;
    private ArrayList<Event> historique;

    Participant(int id, String nom){
        this.id = id;
        this.nom = nom;
        this.historique = new ArrayList<Event>();
        this.winrate = 0.0;
        this.wins = 0.0;
        this.losses = 0;
    }
    Participant(){
    }

    //meth

    public void calcWinrate(){
        Double result;
        Double nbWins;
        int nbLosses;
        result = 0.0;
        nbWins = 0.0;
        nbLosses = 0;
        for(Event e:this.historique){
            if(e.getWinner() == this){
                nbWins++;
            }
            else{
                nbLosses++;
            }
        }
        if(this.historique.size()!=0){
            result = nbWins/this.historique.size()*100;
        }
        this.winrate = result;
        this.losses = nbLosses;
        this.wins = nbWins;
    }

    //getset

    public ArrayList<Event> getHistorique(){
        return this.historique;
    }

    public int getID(){
        return this.id;
    }

    public int getClubID(){
        return this.clubID;
    }

    public void setNom(String n){
        this.nom = n;
    }

    public String getNom(){
        return this.nom;
    }
    
    public Double getWins(){
        return this.wins;
    }

    public void setWins(Double w){
        this.wins = w;
    }

    public int getLosses(){
        return this.losses;
    }

	public void setLosses (int l){
        this.losses = l;
    }

    public void setWinrate(Double w){
        this.winrate = w;
    }

    public Double getWinrate(){
        return this.winrate;
    }
}
