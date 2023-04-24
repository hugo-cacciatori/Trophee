
public class Sport {
    private int id;
    private String nom;

    public Sport(int id, String n){
        this.nom = n;
        this.id = id;
    }

    public String getNom(){
        return this.nom;
    }

    public int getID(){
        return this.id;
    }
}
